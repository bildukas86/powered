package net.sf.l2j.gameserver.taskmanager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.L2Playable;
import net.sf.l2j.gameserver.model.actor.L2Summon;
import net.sf.l2j.gameserver.model.actor.instance.L2CubicInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.AutoAttackStop;

/**
 * @author Luca Baldi
 */
public class AttackStanceTaskManager
{
	protected Map<L2Character, Long> _attackStanceTasks = new ConcurrentHashMap<>();
	
	public AttackStanceTaskManager()
	{
		ThreadPoolManager.getInstance().scheduleAiAtFixedRate(new FightModeScheduler(), 0, 1000);
	}
	
	public static AttackStanceTaskManager getInstance()
	{
		return SingletonHolder._instance;
	}
	
	public void add(L2Character actor)
	{
		if (actor instanceof L2Playable)
		{
			for (L2CubicInstance cubic : actor.getActingPlayer().getCubics().values())
				if (cubic.getId() != L2CubicInstance.LIFE_CUBIC)
					cubic.doAction();
		}
		_attackStanceTasks.put(actor, System.currentTimeMillis());
	}
	
	public void remove(L2Character actor)
	{
		if (actor instanceof L2Summon)
			actor = actor.getActingPlayer();
		
		_attackStanceTasks.remove(actor);
	}
	
	public boolean get(L2Character actor)
	{
		if (actor instanceof L2Summon)
			actor = actor.getActingPlayer();
		
		return _attackStanceTasks.containsKey(actor);
	}
	
	private class FightModeScheduler implements Runnable
	{
		protected FightModeScheduler()
		{
			// Do nothing
		}
		
		@Override
		public void run()
		{
			if (!_attackStanceTasks.isEmpty())
			{
				Long current = System.currentTimeMillis();
				synchronized (this)
				{
					for (L2Character actor : _attackStanceTasks.keySet())
					{
						if ((current - _attackStanceTasks.get(actor)) > 15000)
						{
							actor.broadcastPacket(new AutoAttackStop(actor.getObjectId()));
							
							// Stop pet attackstance animation
							if (actor instanceof L2PcInstance && ((L2PcInstance) actor).getPet() != null)
								((L2PcInstance) actor).getPet().broadcastPacket(new AutoAttackStop(((L2PcInstance) actor).getPet().getObjectId()));
							
							actor.getAI().setAutoAttacking(false);
							_attackStanceTasks.remove(actor);
						}
					}
				}
			}
		}
	}
	
	private static class SingletonHolder
	{
		protected static final AttackStanceTaskManager _instance = new AttackStanceTaskManager();
	}
}