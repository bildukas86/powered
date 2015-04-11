package net.sf.l2j.gameserver.model.actor.knownlist;

import net.sf.l2j.gameserver.ai.CtrlEvent;
import net.sf.l2j.gameserver.ai.CtrlIntention;
import net.sf.l2j.gameserver.ai.L2CharacterAI;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.instance.L2MonsterInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class MonsterKnownList extends AttackableKnownList
{
	public MonsterKnownList(L2MonsterInstance activeChar)
	{
		super(activeChar);
	}
	
	@Override
	public boolean addKnownObject(L2Object object)
	{
		if (!super.addKnownObject(object))
			return false;
		
		final L2CharacterAI ai = getActiveChar().getAI(); // force AI creation
		
		// Set the L2MonsterInstance Intention to ACTIVE if the state was IDLE
		if (object instanceof L2PcInstance && ai != null && ai.getIntention() == CtrlIntention.IDLE)
			ai.setIntention(CtrlIntention.ACTIVE, null);
		
		return true;
	}
	
	@Override
	public boolean removeKnownObject(L2Object object)
	{
		if (!super.removeKnownObject(object))
			return false;
		
		if (!(object instanceof L2Character))
			return true;
		
		// Notify the L2MonsterInstance AI with EVT_FORGET_OBJECT
		if (getActiveChar().hasAI())
			getActiveChar().getAI().notifyEvent(CtrlEvent.EVT_FORGET_OBJECT, object);
		
		// Clear the _aggroList of the L2MonsterInstance
		if (getActiveChar().isVisible() && getKnownType(L2PcInstance.class).isEmpty())
			getActiveChar().clearAggroList();
		
		return true;
	}
	
	@Override
	public final L2MonsterInstance getActiveChar()
	{
		return (L2MonsterInstance) super.getActiveChar();
	}
}