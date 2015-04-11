package net.sf.l2j.gameserver.taskmanager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;

public class TakeBreakTaskManager
{
	protected Map<L2PcInstance, Long> _takeBreakTask = new ConcurrentHashMap<>();
	
	public TakeBreakTaskManager()
	{
		ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new TakeBreakScheduler(), 0, 1000);
	}
	
	public static TakeBreakTaskManager getInstance()
	{
		return SingletonHolder._instance;
	}
	
	public void add(L2PcInstance actor)
	{
		_takeBreakTask.put(actor, System.currentTimeMillis() + 7200000);
	}
	
	public void remove(L2Character actor)
	{
		_takeBreakTask.remove(actor);
	}
	
	private class TakeBreakScheduler implements Runnable
	{
		protected TakeBreakScheduler()
		{
			// Do nothing
		}
		
		@Override
		public void run()
		{
			if (!_takeBreakTask.isEmpty())
			{
				Long current = System.currentTimeMillis();
				synchronized (this)
				{
					for (L2PcInstance actor : _takeBreakTask.keySet())
					{
						if (current > _takeBreakTask.get(actor))
						{
							if (actor.isOnline())
							{
								actor.sendPacket(SystemMessageId.PLAYING_FOR_LONG_TIME);
								_takeBreakTask.put(actor, System.currentTimeMillis() + 7200000);
							}
						}
					}
				}
			}
		}
	}
	
	private static class SingletonHolder
	{
		protected static final TakeBreakTaskManager _instance = new TakeBreakTaskManager();
	}
}