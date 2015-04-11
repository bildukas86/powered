package Extensions.Events.Phoenix.Events;

import Extensions.Events.Phoenix.EventManager;
import Extensions.Events.Phoenix.Engines.Event;
import Extensions.Events.Phoenix.Engines.EventTeam;

import java.util.Map;

import javolution.util.FastMap;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.model.L2Spawn;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.AbstractNpcInfo.NpcInfo;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class Battlefield extends Event
{
	protected EventState eventState;
	protected int winnerTeam;
	private Core task = new Core();
	private FastMap<Integer, L2Spawn> bases = new FastMap<>();
	private FastMap<Integer, Integer> owners = new FastMap<>();
	
	private enum EventState
	{
		START,
		FIGHT,
		END,
		TELEPORT,
		INACTIVE
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
						spawnBases();
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
						unspawnBases();
						setStatus(EventState.INACTIVE);
						
						if (winnerTeam == 0)
							EventManager.getInstance().end("The event ended in a tie! both teams had " + teams.get(1).getScore() + " points!");
						else
						{
							giveReward(getPlayersOfTeam(winnerTeam), getInt("rewardId"), getInt("rewardAmmount"));
							EventManager.getInstance().end("Congratulation! The " + teams.get(winnerTeam).getName() + " team won the event with " + teams.get(winnerTeam).getScore() + " points!");
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
	
	public Battlefield()
	{
		super();
		eventId = 14;
		createNewTeam(1, "Blue", getColor("Blue"), getPosition("Blue", 1));
		createNewTeam(2, "Red", getColor("Red"), getPosition("Red", 1));
	}
	
	@Override
	public void endEvent()
	{
		winnerTeam = players.head().getNext().getValue()[0];
		
		setStatus(EventState.END);
		schedule(1);
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
	
	@Override
	public void onDie(L2PcInstance victim, L2Character killer)
	{
		super.onDie(victim, killer);
		addToResurrector(victim);
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
		sb.append("<html><body><table width=300><tr><td><center>Event phase</td></tr>" +
				"<tr><td><center>" + getString("eventName") + " - " + clock.getTime() + "</td></tr>" +
				"<tr><td><center><font color=" + teams.get(1).getHexaColor() + ">" + teams.get(1).getScore() + "</font> - <font color=" + teams.get(2).getHexaColor() + ">" + teams.get(2).getScore() + "</font></td></tr>" +
				"</table><br><table width=300>");
		
		int i = 0;
		for (EventTeam team : teams.values())
		{
			i++;
			sb.append("<tr><td><font color=" + team.getHexaColor() + ">" + team.getName() + "</font> team</td><td></td><td></td><td></td></tr>");
			for (L2PcInstance p : getPlayersOfTeam(i))
				sb.append("<tr><td>" + p.getName() + "</td><td>lvl " + p.getLevel() + "</td><td>" + p.getTemplate().getClassName() + "</td><td>" + getScore(p) + "</td></tr>");
		}
		// @formatter:on
		sb.append("</table><br></body></html>");
		html.setHtml(sb.toString());
		player.sendPacket(html);
	}
	
	@Override
	public void start()
	{
		setStatus(EventState.START);
		schedule(1);
	}
	
	protected void spawnBases()
	{
		for (int i = 1; i <= getInt("numOfBases"); i++)
		{
			bases.put(i, spawnNPC(getPosition("Base", i)[0], getPosition("Base", i)[1], getPosition("Base", i)[2], getInt("baseNpcId")));
			bases.get(i).getLastSpawn().setTitle("- Neutral -");
			owners.put(i, 0);
		}
	}
	
	protected void unspawnBases()
	{
		for (L2Spawn base : bases.values())
			unspawnNPC(base);
	}
	
	@Override
	public void reset()
	{
		super.reset();
		bases.clear();
		owners.clear();
	}
	
	@Override
	protected void clockTick()
	{
		for (int owner : owners.values())
			if (owner != 0)
				teams.get(owner).increaseScore(1);
	}
	
	@Override
	public void useCapture(L2PcInstance player, L2Npc base)
	{
		if (base.getNpcId() != getInt("baseNpcId"))
			return;
		
		for (Map.Entry<Integer, L2Spawn> baseSpawn : bases.entrySet())
		{
			if (baseSpawn.getValue().getLastSpawn().getObjectId() == base.getObjectId())
			{
				if (owners.get(baseSpawn.getKey()) == getTeam(player))
					return;
				
				owners.getEntry(baseSpawn.getKey()).setValue(getTeam(player));
				baseSpawn.getValue().getLastSpawn().setTitle("- " + teams.get(getTeam(player)).getName() + " -");
				for (L2PcInstance p : getPlayerList())
					p.sendPacket(new NpcInfo(baseSpawn.getValue().getLastSpawn(), p));
				
				announce(getPlayerList(), "The " + teams.get(getTeam(player)).getName() + " team captured a base!");
				increasePlayersScore(player);
			}
		}
	}
	
	@Override
	protected String getStartingMsg()
	{
		return "Capture the flags by using the Capture skill on them!";
	}
	
	@Override
	protected String getScorebar()
	{
		return teams.get(1).getName() + ": " + teams.get(1).getScore() + "  " + teams.get(2).getName() + ": " + teams.get(2).getScore() + "  Time: " + clock.getTime();
	}
	
	protected void removeSkill()
	{
		for (L2PcInstance player : getPlayerList())
			player.removeSkill(SkillTable.getInstance().getInfo(getInt("captureSkillId"), 1), false);
	}
	
	protected void giveSkill()
	{
		for (L2PcInstance player : getPlayerList())
		{
			player.addSkill(SkillTable.getInstance().getInfo(getInt("captureSkillId"), 1), false);
			player.sendSkillList();
		}
	}
}