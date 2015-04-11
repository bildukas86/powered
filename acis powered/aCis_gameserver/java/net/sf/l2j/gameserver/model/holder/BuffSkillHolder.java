package net.sf.l2j.gameserver.model.holder;

public final class BuffSkillHolder
{
	private final int _skillId;
	private final int _price;
	
	private final String _groupType;
	
	public BuffSkillHolder(int skillId, int price, String groupType)
	{
		_skillId = skillId;
		_price = price;
		_groupType = groupType;
	}
	
	public final int getSkillId()
	{
		return _skillId;
	}
	
	public final int getPrice()
	{
		return _price;
	}
	
	public final String getGroupType()
	{
		return _groupType;
	}
}
