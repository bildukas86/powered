package net.sf.l2j.gameserver.model;

import java.util.concurrent.ScheduledFuture;

import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PetInstance;

/**
 * @author DrHouse
 */
public class DropProtection implements Runnable
{
	private volatile boolean _isProtected = false;
	private L2PcInstance _owner = null;
	private ScheduledFuture<?> _task = null;
	
	private static final long PROTECTED_MILLIS_TIME = 15000;
	
	@Override
	public synchronized void run()
	{
		_isProtected = false;
		_owner = null;
		_task = null;
	}
	
	public boolean isProtected()
	{
		return _isProtected;
	}
	
	public L2PcInstance getOwner()
	{
		return _owner;
	}
	
	public synchronized boolean tryPickUp(L2PcInstance actor)
	{
		if (!_isProtected)
			return true;
		
		if (_owner == actor)
			return true;
		
		if (_owner.getParty() != null && _owner.getParty() == actor.getParty())
			return true;
		
		return false;
	}
	
	public boolean tryPickUp(L2PetInstance pet)
	{
		return tryPickUp(pet.getOwner());
	}
	
	public synchronized void unprotect()
	{
		if (_task != null)
			_task.cancel(false);
		
		_isProtected = false;
		_owner = null;
		_task = null;
	}
	
	public synchronized void protect(L2PcInstance player)
	{
		unprotect();
		
		_isProtected = true;
		
		if ((_owner = player) == null)
			throw new NullPointerException("Trying to protect dropped item to null owner");
		
		_task = ThreadPoolManager.getInstance().scheduleGeneral(this, PROTECTED_MILLIS_TIME);
	}
}
