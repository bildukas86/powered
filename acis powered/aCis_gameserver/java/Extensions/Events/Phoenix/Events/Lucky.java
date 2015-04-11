package Extensions.Events.Phoenix.Events;

import Extensions.Events.Phoenix.EventManager;
import Extensions.Events.Phoenix.Engines.Event;
import Extensions.Events.Phoenix.Engines.EventStats;
import javolution.util.FastList;

import net.sf.l2j.gameserver.datatables.SpawnTable;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.L2Spawn;
import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.util.Rnd;

public class Lucky extends Event
{
	protected EventState eventState;
	protected FastList<L2Spawn> chests = new FastList<>();
	private Core task = new Core();
	
	private enum EventState
	{
		START,
		FIGHT,
		END,
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
						divideIntoTeams(1);
						preparePlayers();
						teleportToTeamPos();
						forceSitAll();
						unequip();
						setStatus(EventState.FIGHT);
						schedule(30000);
						break;
					
					case FIGHT:
						forceStandAll();
						sendMsg();
						int[] coor = getPosition("Chests", 1);
						for (int i = 0; i < getInt("numberOfChests"); i++)
							chests.add(spawnNPC(coor[0] + (Rnd.get(coor[3] * 2) - coor[3]), coor[1] + (Rnd.get(coor[3] * 2) - coor[3]), coor[2], getInt("chestNpcId")));
						setStatus(EventState.END);
						clock.startClock(getInt("matchTime"));
						break;
					
					case END:
						clock.setTime(0);
						unSpawnChests();
						L2PcInstance winner = getPlayerWithMaxScore();
						giveReward(winner, getInt("rewardId"), getInt("rewardAmmount"));
						setStatus(EventState.INACTIVE);
						EventManager.getInstance().end("Congratulation! " + winner.getName() + " won the event with " + getScore(winner) + " opened chests!");
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
	
	public Lucky()
	{
		super();
		eventId = 5;
		createNewTeam(1, "All", getColor("All"), getPosition("All", 1));
	}
	
	@Override
	public void endEvent()
	{
		setStatus(EventState.END);
		clock.setTime(0);
	}
	
	@Override
	public boolean onTalkNpc(L2Npc npc, L2PcInstance player)
	{
		if (npc.getNpcId() != getInt("chestNpcId"))
			return false;
		
		if (Rnd.get(3) == 0)
		{
			player.sendPacket(new CreatureSay(npc.getObjectId(), 0, "Chest", "BoOoOoMm!"));
			player.stopAllEffects();
			player.doDie(npc);
			EventStats.getInstance().tempTable.get(player.getObjectId())[2] = EventStats.getInstance().tempTable.get(player.getObjectId())[2] + 1;
			addToResurrector(player);
		}
		else
		{
			npc.doDie(player);
			increasePlayersScore(player);
			if (getBoolean("rewardOnOpen"))
			{
				int rnd = Rnd.get(100);
				if (rnd < ITEMS.length - 1)
					player.addItem("Lucky", ITEMS[rnd], 1, player, true);
			}
		}
		
		npc.deleteMe();
		npc.getSpawn().stopRespawn();
		SpawnTable.getInstance().deleteSpawn(npc.getSpawn(), true);
		chests.remove(npc.getSpawn());
		
		if (chests.size() == 0)
			clock.setTime(0);
		
		return true;
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
	public boolean onUseMagic(L2Skill skill)
	{
		return false;
	}
	
	@Override
	public void showHtml(L2PcInstance player, int obj)
	{
		if (players.size() > 0)
		{
			NpcHtmlMessage html = new NpcHtmlMessage(obj);
			StringBuilder sb = new StringBuilder();
			sb.append("<html><body><table width=270><tr><td width=200>Event Engine </td><td><a action=\"bypass -h eventstats 1\">Statistics</a></td></tr></table><br><center><table width=270 bgcolor=5A5A5A><tr><td width=70>Running</td><td width=130><center>" + getString("eventName") + "</td><td width=70>Time: " + clock.getTime() + "</td></tr></table><table width=270><tr><td><center>" + getPlayerWithMaxScore().getName() + " - " + getScore(getPlayerWithMaxScore()) + "</td></tr></table><br><table width=270>");
			
			for (L2PcInstance p : getPlayersOfTeam(1))
				sb.append("<tr><td>" + p.getName() + "</td><td>lvl " + p.getLevel() + "</td><td>" + p.getTemplate().getClassName() + "</td><td>" + getScore(p) + "</td></tr>");
			
			sb.append("</table></body></html>");
			html.setHtml(sb.toString());
			player.sendPacket(html);
		}
	}
	
	@Override
	public void start()
	{
		setStatus(EventState.START);
		schedule(1);
	}
	
	protected void unSpawnChests()
	{
		for (L2Spawn s : chests)
		{
			if (s == null)
			{
				chests.remove(s);
				continue;
			}
			
			s.getLastSpawn().deleteMe();
			s.stopRespawn();
			SpawnTable.getInstance().deleteSpawn(s, true);
			chests.remove(s);
		}
	}
	
	@Override
	public boolean onUseItem(L2PcInstance player, ItemInstance item)
	{
		return false;
	}
	
	@Override
	public boolean canAttack(L2PcInstance player, L2Object target)
	{
		return false;
	}
	
	@Override
	protected String getStartingMsg()
	{
		return "Open as much chests as possible!";
	}
	
	@Override
	protected String getScorebar()
	{
		return "Max: " + getScore(getPlayerWithMaxScore()) + "  Time: " + clock.getTime() + "";
	}
}