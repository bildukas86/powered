package net.sf.l2j.gameserver.model.zone.type;

import java.util.concurrent.Future;

import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.zone.L2CastleZoneType;
import net.sf.l2j.gameserver.model.zone.ZoneId;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.EtcStatusUpdate;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.skills.Stats;

/**
 * A damage zone
 * @author durgus
 */
public class L2DamageZone extends L2CastleZoneType
{
	private int _hpDps;
	private Future<?> _task;
	
	private int _startTask;
	private int _reuseTask;
	
	private String _target = "L2Playable"; // default only playable
	
	public L2DamageZone(int id)
	{
		super(id);
		
		_hpDps = 100; // setup default damage
		
		// Setup default start / reuse time
		_startTask = 10;
		_reuseTask = 5000;
	}
	
	@Override
	public void setParameter(String name, String value)
	{
		if (name.equals("dmgSec"))
			_hpDps = Integer.parseInt(value);
		else if (name.equalsIgnoreCase("initialDelay"))
			_startTask = Integer.parseInt(value);
		else if (name.equalsIgnoreCase("reuse"))
			_reuseTask = Integer.parseInt(value);
		else if (name.equals("targetClass"))
			_target = value;
		else
			super.setParameter(name, value);
	}
	
	@Override
	protected boolean isAffected(L2Character character)
	{
		// check obj class
		try
		{
			if (!(Class.forName("net.sf.l2j.gameserver.model.actor." + _target).isInstance(character)))
				return false;
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		return true;
	}
	
	@Override
	protected void onEnter(L2Character character)
	{
		if (_task == null && _hpDps != 0)
		{
			// Castle traps are active only during siege, or if they're activated.
			if (getCastle() != null && (!isEnabled() || !getCastle().getSiege().isInProgress()))
				return;
			
			synchronized (this)
			{
				if (_task == null)
				{
					_task = ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new ApplyDamage(this), _startTask, _reuseTask);
					
					// Message for castle traps.
					if (getCastle() != null)
						getCastle().getSiege().announceToPlayer(SystemMessage.getSystemMessage(SystemMessageId.A_TRAP_DEVICE_HAS_BEEN_TRIPPED), false);
				}
			}
		}
		
		if (character instanceof L2PcInstance)
		{
			character.setInsideZone(ZoneId.DANGER_AREA, true);
			character.sendPacket(new EtcStatusUpdate((L2PcInstance) character));
		}
	}
	
	@Override
	protected void onExit(L2Character character)
	{
		if (character instanceof L2PcInstance)
		{
			character.setInsideZone(ZoneId.DANGER_AREA, false);
			if (!character.isInsideZone(ZoneId.DANGER_AREA))
				character.sendPacket(new EtcStatusUpdate((L2PcInstance) character));
		}
	}
	
	protected int getHpDps()
	{
		return _hpDps;
	}
	
	protected void stopTask()
	{
		if (_task != null)
		{
			_task.cancel(false);
			_task = null;
		}
	}
	
	class ApplyDamage implements Runnable
	{
		private final L2DamageZone _dmgZone;
		
		ApplyDamage(L2DamageZone zone)
		{
			_dmgZone = zone;
		}
		
		@Override
		public void run()
		{
			// Cancels the task if config has changed, if castle isn't in siege anymore, or if zone isn't enabled.
			if (_dmgZone.getHpDps() <= 0 || (_dmgZone.getCastle() != null && (!_dmgZone.isEnabled() || !_dmgZone.getCastle().getSiege().isInProgress())))
			{
				_dmgZone.stopTask();
				return;
			}
			
			// Cancels the task if characters list is empty.
			if (_dmgZone.getCharactersInside().isEmpty())
			{
				_dmgZone.stopTask();
				return;
			}
			
			// Effect all people inside the zone.
			for (L2Character temp : _dmgZone.getCharactersInside())
			{
				if (temp != null && !temp.isDead())
					temp.reduceCurrentHp(_dmgZone.getHpDps() * (1 + (temp.calcStat(Stats.DAMAGE_ZONE_VULN, 0, null, null) / 100)), null, null);
			}
		}
	}
}