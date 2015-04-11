package net.sf.l2j.gameserver.model;

/**
 * @author Trance
 * @skype chr.trance
 */
public final class L2RebirthSkillLearn
{
	private final int _id;
	private final int _level;
	private final int _costSp;
	private final int _itemId;
	
	public L2RebirthSkillLearn(int id, int lvl, int costSp, int itemId)
	{
		_id = id;
		_level = lvl;
		_costSp = costSp;
		_itemId = itemId;
	}
	
	public int getId()
	{
		return _id;
	}
	
	public int getLevel()
	{
		return _level;
	}
	
	public int getCostSp()
	{
		return _costSp;
	}
	
	public int getItemId()
	{
		return _itemId;
	}
}