package Extensions.AchievmentsEngine.Conditions;

import Extensions.AchievmentsEngine.Base.Condition;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

//import net.sf.l2j.gameserver.event.EventStats;

public class eventKills extends Condition
{
	public eventKills(Object value)
	{
		super(value);
		setName("Event Kills");
	}
	
	@Override
	public boolean meetConditionRequirements(L2PcInstance player)
	{
		if (getValue() == null)
			return false;
		
		// int val = Integer.parseInt(getValue().toString());
		
		/*
		 * if (EventStats.getInstance().getEventKills(player.getObjectId()) >= val) return true;
		 */
		
		return false;
	}
}