package net.sf.l2j.gameserver.taskmanager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;

public class WaterTaskManager
{
	protected Map<L2PcInstance, Long> _waterTask = new ConcurrentHashMap<>();
	
	public WaterTaskManager()
	{
		ThreadPoolManager.getInstance().scheduleEffectAtFixedRate(new WaterScheduler(), 0, 1000);
	}
	
	public static WaterTaskManager getInstance()
	{
		return SingletonHolder._instance;
	}
	
	public void add(L2PcInstance actor, long time)
	{
		_waterTask.put(actor, time);
	}
	
	public void remove(L2Character actor)
	{
		_waterTask.remove(actor);
	}
	
	private class WaterScheduler implements Runnable
	{
		protected WaterScheduler()
		{
			// Do nothing
		}
		
		@Override
		public void run()
		{
			if (!_waterTask.isEmpty())
			{
				Long current = System.currentTimeMillis();
				synchronized (this)
				{
					for (L2PcInstance actor : _waterTask.keySet())
					{
						if (current > _waterTask.get(actor))
						{
							double reduceHp = actor.getMaxHp() / 100.0;
							
							if (reduceHp < 1)
								reduceHp = 1;
							
							actor.reduceCurrentHp(reduceHp, actor, false, false, null);
							actor.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.DROWN_DAMAGE_S1).addNumber((int) reduceHp));
						}
					}
				}
			}
		}
	}
	
	private static class SingletonHolder
	{
		protected static final WaterTaskManager _instance = new WaterTaskManager();
	}
}