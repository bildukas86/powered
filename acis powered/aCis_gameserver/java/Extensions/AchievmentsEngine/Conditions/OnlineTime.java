package Extensions.AchievmentsEngine.Conditions;

import Extensions.AchievmentsEngine.Base.Condition;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class OnlineTime extends Condition
{
	public OnlineTime(Object value)
	{
		super(value);
		setName("Online Time");
	}
	
	@Override
	public boolean meetConditionRequirements(L2PcInstance player)
	{
		if (getValue() == null)
			return false;
		
		int val = Integer.parseInt(getValue().toString());
		
		if (player.getOnlineTime() >= val * 24 * 60 * 60 * 1000)
		{
			return true;
		}
		return false;
	}
}