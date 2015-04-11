package net.sf.l2j.gameserver.model;

import net.sf.l2j.gameserver.GameTimeController;

public class FishData
{
	private final int _id;
	private final int _level;
	private final String _name;
	private final int _hp;
	private final int _hpRegen;
	private int _type;
	private final int _group;
	private final int _fishGuts;
	private final int _gutsCheckTime;
	private final int _waitTime;
	private final int _combatTime;
	
	public FishData(int id, int lvl, String name, int HP, int HpRegen, int type, int group, int fish_guts, int guts_check_time, int wait_time, int combat_time)
	{
		_id = id;
		_level = lvl;
		_name = name.intern();
		_hp = HP;
		_hpRegen = HpRegen;
		_type = type;
		_group = group;
		_fishGuts = fish_guts;
		_gutsCheckTime = guts_check_time;
		_waitTime = wait_time;
		_combatTime = combat_time;
	}
	
	/**
	 * @return Returns the id.
	 */
	public int getId()
	{
		return _id;
	}
	
	/**
	 * @return Returns the level.
	 */
	public int getLevel()
	{
		return _level;
	}
	
	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return _name;
	}
	
	public int getHP()
	{
		return _hp;
	}
	
	public int getHpRegen()
	{
		return _hpRegen;
	}
	
	public int getType()
	{
		return _type;
	}
	
	public int getType(boolean isLureNight)
	{
		if (!GameTimeController.getInstance().isNight() && isLureNight)
			return -1;
		
		return _type;
	}
	
	public int getGroup()
	{
		return _group;
	}
	
	public int getFishGuts()
	{
		return _fishGuts;
	}
	
	public int getGutsCheckTime()
	{
		return _gutsCheckTime;
	}
	
	public int getWaitTime()
	{
		return _waitTime;
	}
	
	public int getCombatTime()
	{
		return _combatTime;
	}
	
	public void setType(int type)
	{
		_type = type;
	}
}
