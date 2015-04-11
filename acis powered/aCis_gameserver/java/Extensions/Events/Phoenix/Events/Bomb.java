package Extensions.Events.Phoenix.Events;

import Extensions.Events.Phoenix.EventManager;
import Extensions.Events.Phoenix.Engines.Event;
import Extensions.Events.Phoenix.Engines.EventStats;
import Extensions.Events.Phoenix.Engines.EventTeam;
import javolution.util.FastList;
import javolution.util.FastMap;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.L2Spawn;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.network.serverpackets.AbstractNpcInfo.NpcInfo;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillLaunched;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class Bomb extends Event
{
	protected FastMap<L2Spawn, L2PcInstance> bombs = new FastMap<>();
	protected EventState eventState;
	private Core task = new Core();
	private Bomber bomber = new Bomber();
	
	private enum EventState
	{
		START,
		FIGHT,
		END,
		TELEPORT,
		INACTIVE
	}
	
	protected class Bomber implements Runnable
	{
		@Override
		public void run()
		{
			explode(bombs.head().getNext().getKey());
			bombs.remove(bombs.head().getNext().getKey());
		}
	}
	
	protected class Core implements Runnable
	{
		@Override
		public void run()
		{
			try
			{
				switch (eventState)
				{
					case START:
						divideIntoTeams(2);
						preparePlayers();
						teleportToTeamPos();
						createPartyOfTeam(1);
						createPartyOfTeam(2);
						forceSitAll();
						unequip();
						giveSkill();
						setStatus(EventState.FIGHT);
						schedule(20000);
						break;
					
					case FIGHT:
						forceStandAll();
						sendMsg();
						setStatus(EventState.END);
						clock.startClock(getInt("matchTime"));
						break;
					
					case END:
						clock.setTime(0);
						if (winnerTeam == 0)
							winnerTeam = getWinnerTeam();
						
						removeSkill();
						setStatus(EventState.INACTIVE);
						
						if (winnerTeam == 0)
							EventManager.getInstance().end("The event ended in a tie! both teams had " + teams.get(1).getScore() + " kills!");
						else
						{
							giveReward(getPlayersOfTeam(winnerTeam), getInt("rewardId"), getInt("rewardAmmount"));
							EventManager.getInstance().end("Congratulation! The " + teams.get(winnerTeam).getName() + " team won the event with " + teams.get(winnerTeam).getScore() + " kills!");
						}
						break;
				}
			}
			catch (Throwable e)
			{
				e.printStackTrace();
				EventManager.getInstance().end("Error! Event ended.");
			}
		}
	}
	
	public Bomb()
	{
		super();
		eventId = 12;
		createNewTeam(1, "Blue", getColor("Blue"), getPosition("Blue", 1));
		createNewTeam(2, "Red", getColor("Red"), getPosition("Red", 1));
	}
	
	@Override
	public void dropBomb(L2PcInstance player)
	{
		bombs.put(spawnNPC(player.getX(), player.getY(), player.getZ(), getInt("bombNpcId")), player);
		bombs.tail().getPrevious().getKey().getLastSpawn().setTitle((getTeam(player) == 1 ? "Blue" : "Red"));
		bombs.tail().getPrevious().getKey().getLastSpawn().broadcastStatusUpdate();
		
		for (L2PcInstance p : getPlayerList())
			p.sendPacket(new NpcInfo(bombs.tail().getPrevious().getKey().getLastSpawn(), p));
		
		tpm.scheduleGeneral(bomber, 3000);
	}
	
	@Override
	public void endEvent()
	{
		winnerTeam = players.head().getNext().getValue()[0];
		
		setStatus(EventState.END);
		clock.setTime(0);
	}
	
	protected void explode(L2Spawn bomb)
	{
		FastList<L2Object> victims = new FastList<>();
		
		for (L2PcInstance player : getPlayerList())
		{
			if (player == null)
				continue;
			
			if (player.isInvul())
				continue;
			
			if (getTeam(bombs.get(bomb)) != getTeam(player) && Math.sqrt(player.getPlanDistanceSq(bomb.getLastSpawn().getX(), bomb.getLastSpawn().getY())) <= getInt("bombRadius"))
			{
				player.doDie(bomb.getLastSpawn());
				increasePlayersScore(bombs.get(bomb));
				EventStats.getInstance().tempTable.get(player.getObjectId())[2] = EventStats.getInstance().tempTable.get(player.getObjectId())[2] + 1;
				addToResurrector(player);
				
				victims.add(player);
				
				if (getTeam(player) == 1)
					teams.get(2).increaseScore();
				if (getTeam(player) == 2)
					teams.get(1).increaseScore();
			}
			if (victims.size() != 0)
			{
				bomb.getLastSpawn().broadcastPacket(new MagicSkillUse(bomb.getLastSpawn(), (L2PcInstance) victims.head().getNext().getValue(), 18, 1, 0, 0));
				bomb.getLastSpawn().broadcastPacket(new MagicSkillLaunched(bomb.getLastSpawn(), 18, 1, victims.toArray(new L2Object[victims.size()])));
				victims.clear();
			}
		}
		unspawnNPC(bomb);
	}
	
	@Override
	public int getWinnerTeam()
	{
		if (teams.get(1).getScore() > teams.get(2).getScore())
			return 1;
		if (teams.get(2).getScore() > teams.get(1).getScore())
			return 2;
		return 0;
	}
	
	protected void giveSkill()
	{
		for (L2PcInstance player : getPlayerList())
			player.addSkill(SkillTable.getInstance().getInfo(getInt("bombSkillId"), 1), false);
	}
	
	@Override
	public void onLogout(L2PcInstance player)
	{
		player.removeSkill(SkillTable.getInstance().getInfo(getInt("bombSkillId"), 1), false);
	}
	
	@Override
	public boolean onUseMagic(L2Skill skill)
	{
		if (skill.getId() == getInt("bombSkillId"))
			return true;
		
		return false;
	}
	
	protected void removeSkill()
	{
		for (L2PcInstance player : getPlayerList())
			player.removeSkill(SkillTable.getInstance().getInfo(getInt("bombSkillId"), 1), false);
	}
	
	@Override
	protected void schedule(int time)
	{
		tpm.scheduleGeneral(task, time);
	}
	
	protected void setStatus(EventState s)
	{
		eventState = s;
	}
	
	@Override
	public void showHtml(L2PcInstance player, int obj)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(obj);
		StringBuilder sb = new StringBuilder();
		// @formatter:off
		sb.append("<html><body><table width=270><tr><td width=200>Event Engine </td>" +
				"<td><a action=\"bypass -h eventstats 1\">Statistics</a></td>" +
				"</tr></table><br><center>" +
				"<table width=270 bgcolor=5A5A5A>" +
				"<tr>" +
				"<td width=70>Running</td>" +
				"<td width=130><center>" + getString("eventName") + "</td>" +
				"<td width=70>Time: " + clock.getTime() + "</td></tr></table>" +
				"<table width=270><tr>" +
				"<td><center><font color=" + teams.get(1).getHexaColor() + ">" + teams.get(1).getScore() + "</font> - <font color=" + teams.get(2).getHexaColor() + ">" + teams.get(2).getScore() + "</font></td></tr></table>" +
				"<table width=270><tr>" +
				"<td><center><font color=" + teams.get(1).getHexaColor() + ">" + teams.get(1).getScore() + "</font> - <font color=" + teams.get(2).getHexaColor() + ">" + teams.get(2).getScore() + "</font></td></tr></table>" +
				"<br><table width=270>");
		// @formatter:on
		int i = 0;
		for (EventTeam team : teams.values())
		{
			i++;
			sb.append("<tr><td><font color=" + team.getHexaColor() + ">" + team.getName() + "</font> team</td><td></td><td></td><td></td></tr>");
			for (L2PcInstance p : getPlayersOfTeam(i))
				sb.append("<tr><td>" + p.getName() + "</td><td>lvl " + p.getLevel() + "</td><td>" + p.getTemplate().getClassName() + "</td><td>" + getScore(p) + "</td></tr>");
		}
		
		sb.append("</table></body></html>");
		html.setHtml(sb.toString());
		player.sendPacket(html);
	}
	
	@Override
	public void start()
	{
		setStatus(EventState.START);
		schedule(1);
	}
	
	@Override
	public boolean onUseItem(L2PcInstance player, ItemInstance item)
	{
		return false;
	}
	
	@Override
	protected String getStartingMsg()
	{
		return "Kill your enemies by using Bomb skill near them!";
	}
	
	@Override
	protected String getScorebar()
	{
		return teams.get(1).getName() + ": " + teams.get(1).getScore() + "  " + teams.get(2).getName() + ": " + teams.get(2).getScore() + "  Time: " + clock.getTime();
	}
}