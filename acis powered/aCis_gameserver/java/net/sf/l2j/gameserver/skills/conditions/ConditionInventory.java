package net.sf.l2j.gameserver.skills.conditions;

import net.sf.l2j.gameserver.skills.Env;

/**
 * @author mkizub
 */
public abstract class ConditionInventory extends Condition
{
	protected final int _slot;
	
	public ConditionInventory(int slot)
	{
		_slot = slot;
	}
	
	@Override
	public abstract boolean testImpl(Env env);
}