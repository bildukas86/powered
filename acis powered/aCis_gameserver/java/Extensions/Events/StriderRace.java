package Extensions.Events;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.Announcements;
import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.datatables.NpcTable;
import net.sf.l2j.gameserver.datatables.SpawnTable;
import net.sf.l2j.gameserver.model.L2Effect;
import net.sf.l2j.gameserver.model.L2Spawn;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.network.serverpackets.RadarControl;
import net.sf.l2j.util.Rnd;

import javolution.util.FastList;

public class StriderRace
{
	public static enum State
	{
		INACTIVE,
		REGISTERING,
		TELEPORTING,
		RACING
	}
	
	public static State EventState = State.INACTIVE;
	@SuppressWarnings("unused")
	public static FastList<L2PcInstance> _players = new FastList<L2PcInstance>();
	
	public static void getInstance()
	{
		ThreadPoolManager.getInstance().scheduleEffectAtFixedRate(new Runnable()
		{
			@Override
			public void run()
			{
				runEvent();
			}
		}, (Config.STRIDER_RACE_DELAY / 2) * 1000 * 60, Config.STRIDER_RACE_DELAY * 1000 * 60);
	}
	
	public static void endRace(L2PcInstance winner)
	{
		EventState = State.INACTIVE;
		Announcements.getInstance();
		Announcements.announceToAll(winner.getName() + " wins the race! Next event in " + Config.STRIDER_RACE_DELAY + " minutes.");
		for (int i : Config.STRIDER_RACE_WINNER_REWARDS.keySet())
		{
			winner.addItem("Strider Race winner.", i, Config.STRIDER_RACE_WINNER_REWARDS.get(i), winner, true);
		}
		winner.sendMessage("You won the race!");
		for (L2PcInstance p : _players)
		{
			p.sendPacket(new RadarControl(1, 1, Config.STRIDER_RACE_ENDNPC_SPAWN_X, Config.STRIDER_RACE_ENDNPC_SPAWN_Y, Config.STRIDER_RACE_ENDNPC_SPAWN_Z));
			p.dismount();
			p.teleToLocation(82884, 148594, -3472, 0);
			if (p != winner)
			{
				p.sendMessage(winner.getName() + " won the race.");
			}
		}
		_players.clear();
	}
	
	public static void runEvent()
	{
		if (EventState != State.INACTIVE)
		{
			System.out.println("Tried to start Strider Race event while it is already running.");
			return;
		}
		
		openRegistrations();
		waitM(Config.STRIDER_RACE_REGISTRATION_TIME);
		closeRegistrations();
		if (EventState == State.INACTIVE)
		{
			return;
		}
		waitS(20);
		teleportPlayers();
		if (EventState == State.INACTIVE)
		{
			return;
		}
		waitS(10);
		startRace();
		if (EventState == State.INACTIVE)
		{
			return;
		}
		waitM(Config.STRIDER_RACE_RUNNING_TIME);
		if (EventState != State.INACTIVE)
		{
			EventState = State.INACTIVE;
			Announcements.getInstance();
			Announcements.announceToAll("Strider Race event ended in a tie, since noone won in " + Config.STRIDER_RACE_RUNNING_TIME + " minutes.");
			Announcements.getInstance();
			Announcements.announceToAll("Next event in " + Config.STRIDER_RACE_DELAY + " minutes.");
			if (_players.size() > 0)
			{
				for (L2PcInstance p : _players)
				{
					p.dismount();
					p.teleToLocation(82884, 148594, -3472, 0);
					p.sendMessage("The event ended in a tie.");
					
				}
				_players.clear();
			}
		}
	}
	
	private static void startRace()
	{
		if (_players.size() < 2)
		{
			Announcements.getInstance();
			Announcements.announceToAll("Strider Race event was aborted due to lack of participation.");
			EventState = State.INACTIVE;
			for (L2PcInstance p : _players)
			{
				p.setIsParalyzed(false);
				p.stopAbnormalEffect(0x0400);
				p.teleToLocation(82884, 148594, -3472, 0);
			}
			_players.clear();
			return;
		}
		
		Announcements.getInstance();
		Announcements.announceToAll("The race has started!");
		for (L2PcInstance p : _players)
		{
			p.setIsParalyzed(false);
			p.stopAbnormalEffect(0x0400);
			p.sendMessage("The race has started! Quickly, run to event ending place!");
			p.setTarget(p);
		}
	}
	
	private static void teleportPlayers()
	{
		if (_players.size() < 2)
		{
			Announcements.getInstance();
			Announcements.announceToAll("Strider Race event was aborted due to lack of participation.");
			EventState = State.INACTIVE;
			_players.clear();
			return;
		}
		
		Announcements.getInstance();
		Announcements.announceToAll("Players have been teleported to event starting place.");
		Announcements.getInstance();
		Announcements.announceToAll("The race will begin in 10 seconds.");
		for (L2PcInstance p : _players)
		{
			for (L2Effect e : p.getAllEffects())
			{
				e.exit();
			}
			p.teleToLocation(Config.STRIDER_RACE_X, Config.STRIDER_RACE_Y, Config.STRIDER_RACE_Z, 0);
			p.mount(12526, 0, false);
			p.setIsParalyzed(true);
			p.startAbnormalEffect(0x0400);
			p.sendMessage("You have been paralised until the race begins.");
			p.sendPacket(new RadarControl(0, 1, Config.STRIDER_RACE_ENDNPC_SPAWN_X, Config.STRIDER_RACE_ENDNPC_SPAWN_Y, Config.STRIDER_RACE_ENDNPC_SPAWN_Z));
		}
		spawn(Config.STRIDER_RACE_ENDNPCID, Config.STRIDER_RACE_ENDNPC_SPAWN_X, Config.STRIDER_RACE_ENDNPC_SPAWN_Y, Config.STRIDER_RACE_ENDNPC_SPAWN_Z);
		
		EventState = State.RACING;
	}
	
	private static void spawn(int monsterId, int x, int y, int z)
	{
		
		NpcTemplate template = NpcTable.getInstance().getTemplate(monsterId);
		
		try
		{
			L2Spawn spawn = null;
			
			x = x + Rnd.get(-100, 100);
			y = y + Rnd.get(-100, 100);
			spawn = new L2Spawn(template);
			spawn.setLocx(x);
			spawn.setLocy(y);
			spawn.setLocz(z);
			spawn.setHeading(0);
			
			SpawnTable.getInstance().addNewSpawn(spawn, false);
			spawn.init();
			spawn.stopRespawn();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static void closeRegistrations()
	{
		if (_players.size() < 2)
		{
			Announcements.getInstance();
			Announcements.announceToAll("Strider Race event was aborted due to lack of participation.");
			EventState = State.INACTIVE;
			_players.clear();
			return;
		}
		
		Announcements.getInstance();
		Announcements.announceToAll("Registrations for the Strider Race event have closed.");
		Announcements.getInstance();
		Announcements.announceToAll("Players will be teleported to event starting place in 20 seconds.");
		EventState = State.TELEPORTING;
	}
	
	private static void openRegistrations()
	{
		String extra = Config.STRIDER_RACE_REGISTRATION_TIME == 1 ? "" : "s";
		Announcements.getInstance();
		Announcements.announceToAll("Registrations for the Strider Race event have opened for " + Config.STRIDER_RACE_REGISTRATION_TIME + " minute" + extra + ".");
		Announcements.getInstance();
		Announcements.announceToAll("Type .striderrace_join to join or .striderrace_leave to leave.");
		EventState = State.REGISTERING;
	}
	
	private static void waitM(int minutes)
	{
		try
		{
			Thread.sleep(1000 * 60 * minutes);
		}
		catch (InterruptedException ie)
		{
		}
	}
	
	private static void waitS(int seconds)
	{
		try
		{
			Thread.sleep(1000 * seconds);
		}
		catch (InterruptedException ie)
		{
		}
	}
}