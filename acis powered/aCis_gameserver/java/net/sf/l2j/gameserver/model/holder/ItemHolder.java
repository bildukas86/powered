package net.sf.l2j.gameserver.model.holder;

/**
 * Holder for item id-count.
 * @author UnAfraid
 */
public class ItemHolder
{
	private int _id;
	private int _count;
	
	public ItemHolder(int id, int count)
	{
		_id = id;
		_count = count;
	}
	
	/**
	 * @return the item/object identifier.
	 */
	public int getId()
	{
		return _id;
	}
	
	/**
	 * @return the item count.
	 */
	public int getCount()
	{
		return _count;
	}
	
	/**
	 * @param id : The new value to set.
	 */
	public void setId(int id)
	{
		_id = id;
	}
	
	/**
	 * @param count : The new value to set.
	 */
	public void setCount(int count)
	{
		_count = count;
	}
	
	@Override
	public String toString()
	{
		return getClass().getSimpleName() + ": Id: " + _id + " Count: " + _count;
	}
}