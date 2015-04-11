package Extensions.Events.Phoenix.Events;

import Extensions.Events.Phoenix.EventManager;
import Extensions.Events.Phoenix.Engines.Event;

import java.util.Map;

import javolution.util.FastList;
import javolution.util.FastMap;

import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.L2Spawn;
import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.util.Rnd;

public class Russian extends Event
{
	protected EventState eventState;
	protected int round;
	protected FastMap<Integer, L2Spawn> russians = new FastMap<>();
	protected FastMap<Integer, FastList<L2PcInstance>> choses = new FastMap<>();
	private Core task = new Core();
	
	private enum EventState
	{
		START,
		CHOOSE,
		CHECK,
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
						spawnRussians();
						setStatus(EventState.CHOOSE);
						schedule(30000);
						break;
					
					case CHOOSE:
						if (round == 0)
							forceStandAll();
						
						sendMsg();
						round++;
						setStatus(EventState.CHECK);
						schedule(getInt("roundTime") * 1000);
						break;
					
					case CHECK:
						removeAfkers();
						killRandomRussian();
						
						if (countOfPositiveStatus() != 0)
						{
							if (russians.size() != 1)
							{
								for (L2PcInstance player : getPlayersWithStatus(1))
								{
									setStatus(player, 0);
									increasePlayersScore(player);
									player.getAppearance().setNameColor(255, 255, 255);
									player.broadcastUserInfo();
								}
								
								for (FastList<L2PcInstance> chose : choses.values())
									chose.reset();
								
								setStatus(EventState.CHOOSE);
								schedule(getInt("roundTime") * 1000);
							}
							else
							{
								for (L2PcInstance player : getPlayersWithStatus(1))
									giveReward(player, getInt("rewardId"), getInt("rewardAmmount"));
								
								unspawnRussians();
								EventManager.getInstance().end("Congratulation! " + countOfPositiveStatus() + " players survived the event!");
							}
						}
						else
						{
							unspawnRussians();
							EventManager.getInstance().end("Unfortunately no one survived the event!");
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
	
	public Russian()
	{
		super();
		eventId = 11;
		createNewTeam(1, "All", getColor("All"), getPosition("All", 1));
	}
	
	@Override
	public boolean canAttack(L2PcInstance player, L2Object target)
	{
		return false;
	}
	
	@Override
	public void endEvent()
	{
		L2PcInstance winner = players.head().getNext().getKey();
		giveReward(winner, getInt("rewardId"), getInt("rewardAmmount"));
		unspawnRussians();
		EventManager.getInstance().end("Congratulations! " + winner.getName() + " was the only person survived the event!");
	}
	
	protected void killRandomRussian()
	{
		FastList<Integer> ids = new FastList<>();
		for (int id : russians.keySet())
			ids.add(id);
		int russnum = ids.get(Rnd.get(ids.size()));
		L2Spawn russian = russians.get(russnum);
		unspawnNPC(russian);
		announce(getPlayerList(), "The #" + russnum + " russian died.");
		
		for (L2PcInstance victim : choses.get(russnum))
		{
			setStatus(victim, -1);
			victim.stopAllEffects();
			victim.doDie(russian.getLastSpawn());
			victim.sendMessage("Your russian died!");
			victim.getAppearance().setNameColor(255, 255, 255);
		}
		russians.remove(russnum);
	}
	
	@Override
	public void onLogout(L2PcInstance player)
	{
		super.onLogout(player);
		
		for (FastList<L2PcInstance> list : choses.values())
			if (list.contains(player))
				list.remove(player);
	}
	
	@Override
	public boolean onTalkNpc(L2Npc npc, L2PcInstance player)
	{
		if (npc.getNpcId() != getInt("russianNpcId"))
			return false;
		
		if (getStatus(player) != 0)
			return true;
		
		for (Map.Entry<Integer, L2Spawn> russian : russians.entrySet())
		{
			if (russian.getValue().getLastSpawn().getObjectId() == npc.getObjectId())
			{
				choses.get(russian.getKey()).add(player);
				player.getAppearance().setNameColor(0, 255, 0);
				player.broadcastUserInfo();
				setStatus(player, 1);
			}
		}
		
		return true;
	}
	
	protected void removeAfkers()
	{
		for (L2PcInstance player : getPlayerList())
		{
			if (getStatus(player) == 0)
			{
				player.sendMessage("Timeout!");
				player.stopAllEffects();
				player.doDie(null);
				setStatus(player, -1);
			}
		}
	}
	
	@Override
	public void reset()
	{
		super.reset();
		round = 0;
		russians.clear();
		choses.clear();
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
		
		sb.append("<html><body><table width=270><tr><td width=200>Event Engine </td><td><a action=\"bypass -h eventstats 1\">Statistics</a></td></tr></table><br><center><table width=270 bgcolor=5A5A5A><tr><td width=70>Running</td><td width=130><center>" + getString("eventName") + "</td><td width=70>Time: " + clock.getTime() + "</td></tr></table><table width=270><tr><td><center>Russians left: " + russians.size() + "</td></tr></table><br><table width=270>");
		
		for (L2PcInstance p : getPlayersOfTeam(1))
			sb.append("<tr><td>" + p.getName() + "</td><td>lvl " + p.getLevel() + "</td><td>" + p.getTemplate().getClassName() + "</td><td>" + (getStatus(p) == 1 ? "Dead" : "Alive") + "</td></tr>");
		
		sb.append("</table></body></html>");
		html.setHtml(sb.toString());
		player.sendPacket(html);
	}
	
	protected void spawnRussians()
	{
		for (int i = 1; i <= getInt("numberOfRussians"); i++)
		{
			int[] pos = getPosition("Russian", i);
			russians.put(i, spawnNPC(pos[0], pos[1], pos[2], getInt("russianNpcId")));
			choses.put(i, new FastList<L2PcInstance>());
			russians.get(i).getLastSpawn().setTitle("--" + i + "--");
		}
	}
	
	@Override
	public void start()
	{
		setStatus(EventState.START);
		schedule(1);
	}
	
	@Override
	protected String getStartingMsg()
	{
		return "Choose a new chest!";
	}
	
	@Override
	protected String getScorebar()
	{
		return "Chests remaining: " + russians.size();
	}
	
	protected void unspawnRussians()
	{
		for (L2Spawn russian : russians.values())
			unspawnNPC(russian);
	}
}