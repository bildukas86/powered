package net.sf.l2j.gameserver.model.item;

/**
 * @author -Nemesiss-
 */
public class SummonItem
{
	private final int _itemId;
	private final int _npcId;
	private final byte _type;
	
	public SummonItem(int itemId, int npcId, byte type)
	{
		_itemId = itemId;
		_npcId = npcId;
		_type = type;
	}
	
	public int getItemId()
	{
		return _itemId;
	}
	
	public int getNpcId()
	{
		return _npcId;
	}
	
	public byte getType()
	{
		return _type;
	}
	
	public boolean isPetSummon()
	{
		return _type == 1 || _type == 2;
	}
}
