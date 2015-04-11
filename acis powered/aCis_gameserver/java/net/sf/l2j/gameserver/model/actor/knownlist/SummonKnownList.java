package net.sf.l2j.gameserver.model.actor.knownlist;

import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.actor.L2Summon;

public class SummonKnownList extends PlayableKnownList
{
	public SummonKnownList(L2Summon activeChar)
	{
		super(activeChar);
	}
	
	@Override
	public final L2Summon getActiveChar()
	{
		return (L2Summon) super.getActiveChar();
	}
	
	@Override
	public int getDistanceToForgetObject(L2Object object)
	{
		if (object == getActiveChar().getOwner() || object == getActiveChar().getTarget())
			return 6000;
		
		return 3000;
	}
	
	@Override
	public int getDistanceToWatchObject(L2Object object)
	{
		return 1500;
	}
}