package Extensions.Events.Phoenix.Events;

import Extensions.Events.Phoenix.EventManager;
import Extensions.Events.Phoenix.Engines.Event;

import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class LMS extends Event
{
	protected EventState eventState;
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
						InvisAll();
						setStatus(EventState.FIGHT);
						schedule(20000);
						break;
					
					case FIGHT:
						unInvisAll();
						sendMsg();
						setStatus(EventState.END);
						clock.startClock(getInt("matchTime"));
						break;
					
					case END:
						clock.setTime(0);
						setStatus(EventState.INACTIVE);
						
						if (getPlayersWithStatus(0).size() != 1)
							EventManager.getInstance().end("The event ended in a tie! there are " + getPlayersWithStatus(0).size() + " players still standing!");
						else
						{
							L2PcInstance winner = getPlayersWithStatus(0).get(0);
							giveReward(winner, getInt("rewardId"), getInt("rewardAmmount"));
							if (winner.getAppearance().getSex())
								EventManager.getInstance().end(winner.getName() + " is the Last Woman Standing!");
							else
								EventManager.getInstance().end(winner.getName() + " is the Last Man Standing!");
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
	
	public LMS()
	{
		super();
		eventId = 4;
		createNewTeam(1, "All", getColor("All"), getPosition("All", 1));
	}
	
	@Override
	public void endEvent()
	{
		setStatus(EventState.END);
		clock.setTime(0);
	}
	
	@Override
	public void onKill(L2Character victim, L2PcInstance killer)
	{
		super.onKill(victim, killer);
		increasePlayersScore(killer);
		setStatus((L2PcInstance) victim, 1);
		if (getPlayersWithStatus(0).size() == 1)
		{
			setStatus(EventState.END);
			clock.setTime(0);
		}
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
		sb.append("<html><body><table width=270><tr><td width=200>Event Engine </td><td><a action=\"bypass -h eventstats 1\">Statistics</a></td></tr></table><br><center><table width=270 bgcolor=5A5A5A><tr><td width=70>Running</td><td width=130><center>" + getString("eventName") + "</td><td width=70>Time: " + clock.getTime() + "</td></tr></table><table width=270><tr><td><center>Players left: " + getPlayersWithStatus(0).size() + "</td></tr></table><br><table width=270>");
		
		for (L2PcInstance p : getPlayersOfTeam(1))
			sb.append("<tr><td>" + p.getName() + "</td><td>lvl " + p.getLevel() + "</td><td>" + p.getTemplate().getClassName() + "</td><td>" + (getStatus(p) == 1 ? "Dead" : "Alive") + "</td></tr>");
		
		sb.append("</table></body></html>");
		html.setHtml(sb.toString());
		player.sendPacket(html);
	}
	
	@Override
	public boolean onSay(int type, L2PcInstance player, String text)
	{
		return false;
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
		return "Be the last to survive!";
	}
	
	@Override
	protected String getScorebar()
	{
		return "Players: " + getPlayersWithStatus(0).size() + "  Time: " + clock.getTime();
	}
}