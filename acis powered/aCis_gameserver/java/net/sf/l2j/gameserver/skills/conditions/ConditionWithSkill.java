package net.sf.l2j.gameserver.skills.conditions;

import net.sf.l2j.gameserver.skills.Env;

/**
 * @author Steuf
 */
public class ConditionWithSkill extends Condition
{
	private final boolean _skill;
	
	public ConditionWithSkill(boolean skill)
	{
		_skill = skill;
	}
	
	@Override
	public boolean testImpl(Env env)
	{
		if (!_skill && env.getSkill() != null)
			return false;
		
		return true;
	}
}