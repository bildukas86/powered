package net.sf.l2j.gameserver.model.actor.knownlist;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.ai.CtrlIntention;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.actor.instance.L2GuardInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2MonsterInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class GuardKnownList extends AttackableKnownList
{
	public GuardKnownList(L2GuardInstance activeChar)
	{
		super(activeChar);
	}
	
	@Override
	public boolean addKnownObject(L2Object object)
	{
		if (!super.addKnownObject(object))
			return false;
		
		if (object instanceof L2PcInstance)
		{
			// Check if the object added is a L2PcInstance that owns Karma
			if (((L2PcInstance) object).getKarma() > 0)
			{
				// Set the L2GuardInstance Intention to ACTIVE
				if (getActiveChar().getAI().getIntention() == CtrlIntention.IDLE)
					getActiveChar().getAI().setIntention(CtrlIntention.ACTIVE, null);
			}
		}
		else if ((Config.GUARD_ATTACK_AGGRO_MOB && getActiveChar().isInActiveRegion()) && object instanceof L2MonsterInstance)
		{
			// Check if the object added is an aggressive L2MonsterInstance
			if (((L2MonsterInstance) object).isAggressive())
			{
				// Set the L2GuardInstance Intention to ACTIVE
				if (getActiveChar().getAI().getIntention() == CtrlIntention.IDLE)
					getActiveChar().getAI().setIntention(CtrlIntention.ACTIVE, null);
			}
		}
		return true;
	}
	
	@Override
	public boolean removeKnownObject(L2Object object)
	{
		if (!super.removeKnownObject(object))
			return false;
		
		// If the _aggroList of the L2GuardInstance is empty, set to IDLE
		if (getActiveChar().gotNoTarget())
		{
			if (getActiveChar().hasAI())
				getActiveChar().getAI().setIntention(CtrlIntention.IDLE, null);
		}
		return true;
	}
	
	@Override
	public final L2GuardInstance getActiveChar()
	{
		return (L2GuardInstance) super.getActiveChar();
	}
}