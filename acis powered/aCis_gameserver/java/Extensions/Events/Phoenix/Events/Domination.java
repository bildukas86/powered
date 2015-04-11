package Extensions.Events.Phoenix.Events;

import Extensions.Events.Phoenix.EventManager;
import Extensions.Events.Phoenix.Engines.Event;
import Extensions.Events.Phoenix.Engines.EventTeam;
import javolution.util.FastList;

import net.sf.l2j.gameserver.datatables.SpawnTable;
import net.sf.l2j.gameserver.model.L2Spawn;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class Domination extends Event
{
	protected EventState eventState;
	private Core task = new Core();
	private FastList<L2Spawn> zones = new FastList<>();
	
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
						divideIntoTeams(2);
						preparePlayers();
						teleportToTeamPos();
						createPartyOfTeam(1);
						createPartyOfTeam(2);
						forceSitAll();
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
						
						unSpawnZones();
						setStatus(EventState.INACTIVE);
						
						if (winnerTeam == 0)
							EventManager.getInstance().end("The event ended in a tie! both teams had " + teams.get(1).getScore() + " domination points!");
						else
						{
							giveReward(getPlayersOfTeam(winnerTeam), getInt("rewardId"), getInt("rewardAmmount"));
							EventManager.getInstance().end("Congratulation! The " + teams.get(winnerTeam).getName() + " team won the event with " + teams.get(winnerTeam).getScore() + " domination points!");
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
	
	public Domination()
	{
		super();
		eventId = 2;
		createNewTeam(1, "Blue", getColor("Blue"), getPosition("Blue", 1));
		createNewTeam(2, "Red", getColor("Red"), getPosition("Red", 1));
	}
	
	@Override
	protected void clockTick()
	{
		int team1 = 0;
		int team2 = 0;
		
		for (L2PcInstance player : getPlayerList())
		{
			switch (getTeam(player))
			{
				case 1:
					if (Math.sqrt(player.getPlanDistanceSq(zones.getFirst().getLastSpawn().getX(), zones.getFirst().getLastSpawn().getY())) <= getInt("zoneRadius"))
						team1++;
					break;
				
				case 2:
					if (Math.sqrt(player.getPlanDistanceSq(zones.getFirst().getLastSpawn().getX(), zones.getFirst().getLastSpawn().getY())) <= getInt("zoneRadius"))
						team2++;
					break;
			}
		}
		
		if (team1 > team2)
		{
			for (L2PcInstance player : getPlayersOfTeam(1))
				increasePlayersScore(player);
			teams.get(1).increaseScore();
		}
		
		if (team2 > team1)
		{
			for (L2PcInstance player : getPlayersOfTeam(2))
				increasePlayersScore(player);
			teams.get(2).increaseScore();
		}
	}
	
	@Override
	public void endEvent()
	{
		setStatus(EventState.END);
		clock.setTime(0);
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
		sb.append("<html><body>" +
				"<table width=270><tr>" +
				"<td width=200>Event Engine </td>" +
				"<td><a action=\"bypass -h eventstats 1\">Statistics</a></td>" +
				"</tr></table><br><center>" +
				"<table width=270 bgcolor=5A5A5A><tr>" +
				"<td width=70>Running</td>" +
				"<td width=130><center>" + getString("eventName") + "</td>" +
				"<td width=70>Time: " + clock.getTime() + "</td>" +
				"</tr></table>" +
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
		int[] npcpos = getPosition("Zone", 1);
		zones.add(spawnNPC(npcpos[0], npcpos[1], npcpos[2], getInt("zoneNpcId")));
		setStatus(EventState.START);
		schedule(1);
	}
	
	@Override
	protected String getStartingMsg()
	{
		return "Get as many team members near the Zone as possible to score!";
	}
	
	@Override
	protected String getScorebar()
	{
		return teams.get(1).getName() + ": " + teams.get(1).getScore() + "  " + teams.get(2).getName() + ": " + teams.get(2).getScore() + "  Time: " + clock.getTime();
	}
	
	protected void unSpawnZones()
	{
		for (L2Spawn s : zones)
		{
			s.getLastSpawn().deleteMe();
			s.stopRespawn();
			SpawnTable.getInstance().deleteSpawn(s, true);
			zones.remove(s);
		}
	}
}