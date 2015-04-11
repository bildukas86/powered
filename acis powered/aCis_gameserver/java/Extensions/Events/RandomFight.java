package Extensions.Events;

import java.util.Vector;
import net.sf.l2j.Config;
import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.olympiad.OlympiadManager;
import net.sf.l2j.gameserver.util.Broadcast;
import net.sf.l2j.util.Rnd;

public class RandomFight
{
	public static enum State
	{
		INACTIVE,
		REGISTER,
		LOADING,
		FIGHT;
		
		private State()
		{
		}
	}
	
	public static State state = State.INACTIVE;
	public static Vector<L2PcInstance> players = new Vector<>();
	
	protected void openRegistrations()
	{
		state = State.REGISTER;
		Broadcast.announceToOnlinePlayers("Random Fight Event will start in 1 minute.");
		Broadcast.announceToOnlinePlayers("Type .join_rf to Join");
		Broadcast.announceToOnlinePlayers("Type .leave_rf to leave");
		ThreadPoolManager.getInstance().scheduleGeneral(new checkRegist(), 60000L);
	}
	
	protected void checkRegistrations()
	{
		state = State.LOADING;
		if ((players.isEmpty()) || (players.size() < 2))
		{
			Broadcast.announceToOnlinePlayers("Random Fight Event will not start cause of no many partitipations, we are sorry.");
			clean();
			return;
		}
		Broadcast.announceToOnlinePlayers("Amount of players Registed: " + players.size());
		Broadcast.announceToOnlinePlayers("2 Random players will be choosen in 30 seconds!");
		ThreadPoolManager.getInstance().scheduleGeneral(new pickPlayers(), 30000L);
	}
	
	protected void pickPlayers()
	{
		if ((players.isEmpty()) || (players.size() < 2))
		{
			Broadcast.announceToOnlinePlayers("Random Fight Event aborted because no many partitipations, we are sorry.");
			clean();
			return;
		}
		for (L2PcInstance p : players)
		{
			if ((p.isInOlympiadMode()) || (OlympiadManager.getInstance().isRegistered(p)))
			{
				players.remove(p);
				p.sendMessage("You automatically left from event because of your olympiad obligations.");
			}
		}
		int rnd1 = Rnd.get(players.size());
		int rnd2 = Rnd.get(players.size());
		while (rnd2 == rnd1)
		{
			rnd2 = Rnd.get(players.size());
		}
		for (L2PcInstance player : players)
		{
			if ((player != players.get(rnd1)) && (player != players.get(rnd2)))
			{
				players.remove(player);
			}
		}
		Broadcast.announceToOnlinePlayers("Players selected: " + players.firstElement().getName() + " || " + players.lastElement().getName());
		Broadcast.announceToOnlinePlayers("Players will be teleported in 15 seconds");
		ThreadPoolManager.getInstance().scheduleGeneral(new teleportPlayers(), 15000L);
	}
	
	protected void teleport()
	{
		if ((players.isEmpty()) || (players.size() < 2))
		{
			Broadcast.announceToOnlinePlayers("Random Fight Event aborted because no many partitipations, we are sorry.");
			clean();
			return;
		}
		Broadcast.announceToOnlinePlayers("Players teleported!");
		
		players.firstElement().teleToLocation(113474, 15552, 3968, 0);
		players.lastElement().teleToLocation(112990, 15489, 3968, 0);
		players.firstElement().setTeam(1);
		players.lastElement().setTeam(2);
		
		players.firstElement().sendMessage("Fight will begin in 15 seconds!");
		players.lastElement().sendMessage("Fight will begin in 15 seconds!");
		
		ThreadPoolManager.getInstance().scheduleGeneral(new fight(), 15000L);
	}
	
	protected void startFight()
	{
		if ((players.isEmpty()) || (players.size() < 2))
		{
			Broadcast.announceToOnlinePlayers("One of the players isn't online, event aborted we are sorry!");
			clean();
			return;
		}
		state = State.FIGHT;
		Broadcast.announceToOnlinePlayers("FIGHT STARTED!");
		players.firstElement().sendMessage("Start Fight!!");
		players.lastElement().sendMessage("Start Fight!");
		ThreadPoolManager.getInstance().scheduleGeneral(new checkLast(), 120000L);
	}
	
	protected void lastCheck()
	{
		if (state == State.FIGHT)
		{
			if ((players.isEmpty()) || (players.size() < 2))
			{
				revert();
				clean();
				return;
			}
			int alive = 0;
			for (L2PcInstance player : players)
			{
				if (!player.isDead())
				{
					alive++;
				}
			}
			if (alive == 2)
			{
				Broadcast.announceToOnlinePlayers("Random Fight ended tie!");
				clean();
				revert();
			}
		}
	}
	
	public static void revert()
	{
		if (!players.isEmpty())
		{
			for (L2PcInstance p : players)
			{
				if (p != null)
				{
					if (p.isDead())
					{
						p.doRevive();
					}
					p.setCurrentHp(p.getMaxHp());
					p.setCurrentCp(p.getMaxCp());
					p.setCurrentMp(p.getMaxMp());
					p.broadcastUserInfo();
					p.teleToLocation(82698, 148638, -3473, 0);
				}
			}
		}
	}
	
	public static void clean()
	{
		if (state == State.FIGHT)
		{
			for (L2PcInstance p : players)
			{
				p.setTeam(0);
			}
		}
		players.clear();
		state = State.INACTIVE;
	}
	
	protected RandomFight()
	{
		ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new Event(), 60000 * Config.EVERY_MINUTES, 60000 * Config.EVERY_MINUTES);
	}
	
	public static RandomFight getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final RandomFight _instance = new RandomFight();
	}
	
	protected class Event implements Runnable
	{
		protected Event()
		{
		}
		
		@Override
		public void run()
		{
			if (RandomFight.state == RandomFight.State.INACTIVE)
			{
				RandomFight.this.openRegistrations();
			}
		}
	}
	
	protected class checkRegist implements Runnable
	{
		protected checkRegist()
		{
		}
		
		@Override
		public void run()
		{
			RandomFight.this.checkRegistrations();
		}
	}
	
	protected class pickPlayers implements Runnable
	{
		protected pickPlayers()
		{
		}
		
		@Override
		public void run()
		{
			RandomFight.this.pickPlayers();
		}
	}
	
	protected class teleportPlayers implements Runnable
	{
		protected teleportPlayers()
		{
		}
		
		@Override
		public void run()
		{
			RandomFight.this.teleport();
		}
	}
	
	protected class fight implements Runnable
	{
		protected fight()
		{
		}
		
		@Override
		public void run()
		{
			RandomFight.this.startFight();
		}
	}
	
	protected class checkLast implements Runnable
	{
		protected checkLast()
		{
		}
		
		@Override
		public void run()
		{
			RandomFight.this.lastCheck();
		}
	}
}