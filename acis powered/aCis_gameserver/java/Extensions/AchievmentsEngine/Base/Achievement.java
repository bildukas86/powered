package Extensions.AchievmentsEngine.Base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class Achievement
{
	private int _id;
	private String _name;
	private String _reward;
	private String _description = "No Description!";
	private boolean _repeatable;
	
	private HashMap<Integer, Long> _rewardList;
	private ArrayList<Condition> _conditions;
	
	private static Logger _log = Logger.getLogger(Achievement.class.getName());
	
	public Achievement(int id, String name, String description, String reward, boolean repeatable, ArrayList<Condition> conditions)
	{
		_rewardList = new HashMap<>();
		_id = id;
		_name = name;
		_description = description;
		_reward = reward;
		_conditions = conditions;
		_repeatable = repeatable;
		
		createRewardList();
	}
	
	private void createRewardList()
	{
		for (String s : _reward.split(";"))
		{
			if (s == null || s.isEmpty())
				continue;
			
			String[] split = s.split(",");
			Integer item = 0;
			Long count = new Long(0);
			try
			{
				item = Integer.valueOf(split[0]);
				count = Long.valueOf(split[1]);
			}
			catch (NumberFormatException nfe)
			{
				_log.warning("[AchievementsEngine] Error: Wrong reward " + nfe);
			}
			_rewardList.put(item, count);
		}
	}
	
	public boolean meetAchievementRequirements(L2PcInstance player)
	{
		for (Condition c : getConditions())
		{
			if (!c.meetConditionRequirements(player))
			{
				return false;
			}
		}
		return true;
	}
	
	public int getID()
	{
		return _id;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public String getDescription()
	{
		return _description;
	}
	
	public String getReward()
	{
		return _reward;
	}
	
	public boolean isRepeatable()
	{
		return _repeatable;
	}
	
	public HashMap<Integer, Long> getRewardList()
	{
		return _rewardList;
	}
	
	public ArrayList<Condition> getConditions()
	{
		return _conditions;
	}
}