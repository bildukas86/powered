package net.sf.l2j.gameserver;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.l2j.gameserver.ai.CtrlEvent;
import net.sf.l2j.gameserver.ai.L2CharacterAI;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.instancemanager.DayNightSpawnManager;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.base.Race;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;

/**
 * Game Time controller class.
 */
public final class GameTimeController extends Thread
{
	protected static final Logger _log = Logger.getLogger(GameTimeController.class.getName());
	
	public static final int TICKS_PER_SECOND = 10; // not able to change this without checking through code
	public static final int MILLIS_IN_TICK = 1000 / TICKS_PER_SECOND;
	public static final int IG_DAYS_PER_DAY = 6;
	public static final int MILLIS_PER_IG_DAY = (3600000 * 24) / IG_DAYS_PER_DAY;
	public static final int SECONDS_PER_IG_DAY = MILLIS_PER_IG_DAY / 1000;
	public static final int MINUTES_PER_IG_DAY = SECONDS_PER_IG_DAY / 60;
	public static final int TICKS_PER_IG_DAY = SECONDS_PER_IG_DAY * TICKS_PER_SECOND;
	public static final int TICKS_SUN_STATE_CHANGE = TICKS_PER_IG_DAY / 4;
	
	private final Map<Integer, L2Character> _movingObjects = new ConcurrentHashMap<>();
	private final long _referenceTime;
	
	public static final GameTimeController getInstance()
	{
		return SingletonHolder._instance;
	}
	
	protected GameTimeController()
	{
		super("GameTimeController");
		super.setDaemon(true);
		super.setPriority(MAX_PRIORITY);
		
		final Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		_referenceTime = c.getTimeInMillis();
		
		super.start();
	}
	
	public final int getGameTime()
	{
		return (getGameTicks() % TICKS_PER_IG_DAY) / MILLIS_IN_TICK;
	}
	
	public final int getGameHour()
	{
		return getGameTime() / 60;
	}
	
	public final int getGameMinute()
	{
		return getGameTime() % 60;
	}
	
	public final boolean isNight()
	{
		return getGameHour() < 6;
	}
	
	/**
	 * The true GameTime tick. Directly taken from current time. This represents the tick of the time.
	 * @return
	 */
	public final int getGameTicks()
	{
		return (int) ((System.currentTimeMillis() - _referenceTime) / MILLIS_IN_TICK);
	}
	
	/**
	 * Add a L2Character to movingObjects of GameTimeController.
	 * @param cha The L2Character to add to movingObjects of GameTimeController
	 */
	public final void registerMovingObject(final L2Character cha)
	{
		if (cha == null)
			return;
		
		if (_movingObjects.get(cha.getObjectId()) == null)
			_movingObjects.put(cha.getObjectId(), cha);
	}
	
	public final void stopTimer()
	{
		super.interrupt();
		_log.log(Level.INFO, "Stopping " + getClass().getSimpleName());
	}
	
	@Override
	public final void run()
	{
		_log.log(Level.CONFIG, getClass().getSimpleName() + ": Started.");
		
		long nextTickTime, sleepTime;
		boolean isNight = isNight();
		
		if (isNight)
		{
			ThreadPoolManager.getInstance().executeAi(new Runnable()
			{
				@Override
				public final void run()
				{
					DayNightSpawnManager.getInstance().notifyChangeMode();
				}
			});
		}
		
		while (true)
		{
			nextTickTime = ((System.currentTimeMillis() / MILLIS_IN_TICK) * MILLIS_IN_TICK) + 100;
			
			try
			{
				for (Map.Entry<Integer, L2Character> e : _movingObjects.entrySet())
				{
					L2Character character = e.getValue();
					
					if (character.updatePosition(getGameTicks()))
					{
						// Destination reached. Remove from map and execute arrive event.
						_movingObjects.remove(e.getKey());
						
						final L2CharacterAI ai = character.getAI();
						if (ai == null)
							return;
						
						ThreadPoolManager.getInstance().executeAi(new Runnable()
						{
							@Override
							public final void run()
							{
								try
								{
									ai.notifyEvent(CtrlEvent.EVT_ARRIVED);
								}
								catch (final Throwable e)
								{
									_log.log(Level.WARNING, "", e);
								}
							}
						});
					}
				}
			}
			catch (final Throwable e)
			{
				_log.log(Level.WARNING, "", e);
			}
			
			sleepTime = nextTickTime - System.currentTimeMillis();
			if (sleepTime > 0)
			{
				try
				{
					Thread.sleep(sleepTime);
				}
				catch (final InterruptedException e)
				{
					
				}
			}
			
			if (isNight() != isNight)
			{
				isNight = !isNight;
				
				ThreadPoolManager.getInstance().executeAi(new Runnable()
				{
					@Override
					public final void run()
					{
						DayNightSpawnManager.getInstance().notifyChangeMode();
						
						// "Activate" shadow sense at 00h00 (night) and 06h00 (sunrise)
						for (L2PcInstance player : L2World.getInstance().getAllPlayers().values())
						{
							// if a player is a DE, verify if he got the skill
							if (player != null && player.getRace() == Race.DarkElf)
							{
								final L2Skill skill = SkillTable.getInstance().getInfo(294, 1);
								if (skill != null && player.getSkillLevel(294) == 1)
								{
									player.sendPacket(SystemMessage.getSystemMessage((isNight()) ? SystemMessageId.NIGHT_S1_EFFECT_APPLIES : SystemMessageId.DAY_S1_EFFECT_DISAPPEARS).addSkillName(294));
									
									// You saw nothing and that pack doesn't even exist w_w.
									player.removeSkill(skill, false);
									player.addSkill(skill, false);
								}
							}
						}
					}
				});
			}
		}
	}
	
	private static class SingletonHolder
	{
		protected static final GameTimeController _instance = new GameTimeController();
	}
}