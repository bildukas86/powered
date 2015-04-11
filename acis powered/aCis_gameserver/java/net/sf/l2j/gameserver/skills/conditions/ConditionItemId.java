package net.sf.l2j.gameserver.skills.conditions;

import net.sf.l2j.gameserver.skills.Env;

/**
 * @author mkizub
 */
public final class ConditionItemId extends Condition
{
	private final int _itemId;
	
	public ConditionItemId(int itemId)
	{
		_itemId = itemId;
	}
	
	@Override
	public boolean testImpl(Env env)
	{
		return env.getItem() != null && env.getItem().getItemId() == _itemId;
	}
}