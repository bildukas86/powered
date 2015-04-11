package Extensions.AchievmentsEngine.Conditions;

import Extensions.AchievmentsEngine.Base.Condition;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

//import net.sf.l2j.gameserver.event.EventStats;

public class events extends Condition
{
	public events(Object value)
	{
		super(value);
		setName("Events played");
	}
	
	@Override
	public boolean meetConditionRequirements(L2PcInstance player)
	{
		if (getValue() == null)
			return false;
		
		// int val = Integer.parseInt(getValue().toString());
		
		/*
		 * if (EventStats.getInstance().getEvents(player.getObjectId()) >= val) return true;
		 */
		
		return false;
	}
}