package net.sf.l2j.gameserver.model.itemcontainer;

import java.util.ArrayList;
import java.util.List;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance.ItemLocation;

public class PcFreight extends ItemContainer
{
	private final L2PcInstance _owner; // This is the L2PcInstance that owns this Freight;
	private int _activeLocationId;
	private int _tempOwnerId = 0;
	
	public PcFreight(L2PcInstance owner)
	{
		_owner = owner;
	}
	
	@Override
	public String getName()
	{
		return "Freight";
	}
	
	@Override
	public L2PcInstance getOwner()
	{
		return _owner;
	}
	
	@Override
	public ItemLocation getBaseLocation()
	{
		return ItemLocation.FREIGHT;
	}
	
	public void setActiveLocation(int locationId)
	{
		_activeLocationId = locationId;
	}
	
	/**
	 * Returns the quantity of items in the inventory
	 * @return int
	 */
	@Override
	public int getSize()
	{
		int size = 0;
		for (ItemInstance item : _items)
		{
			if (item.getLocationSlot() == 0 || _activeLocationId == 0 || item.getLocationSlot() == _activeLocationId)
				size++;
		}
		return size;
	}
	
	/**
	 * Returns the list of items in inventory
	 * @return ItemInstance : items in inventory
	 */
	@Override
	public ItemInstance[] getItems()
	{
		List<ItemInstance> list = new ArrayList<>();
		for (ItemInstance item : _items)
		{
			if (item.getLocationSlot() == 0 || item.getLocationSlot() == _activeLocationId)
				list.add(item);
		}
		return list.toArray(new ItemInstance[list.size()]);
	}
	
	/**
	 * Returns the item from inventory by using its <B>itemId</B>
	 * @param itemId : int designating the ID of the item
	 * @return ItemInstance designating the item or null if not found in inventory
	 */
	@Override
	public ItemInstance getItemByItemId(int itemId)
	{
		for (ItemInstance item : _items)
		{
			if (item.getItemId() == itemId && (item.getLocationSlot() == 0 || _activeLocationId == 0 || item.getLocationSlot() == _activeLocationId))
				return item;
		}
		return null;
	}
	
	/**
	 * Adds item to PcFreight for further adjustments.
	 * @param item : ItemInstance to be added from inventory
	 */
	@Override
	protected void addItem(ItemInstance item)
	{
		super.addItem(item);
		if (_activeLocationId > 0)
			item.setLocation(item.getLocation(), _activeLocationId);
	}
	
	/**
	 * Get back items in PcFreight from database
	 */
	@Override
	public void restore()
	{
		int locationId = _activeLocationId;
		_activeLocationId = 0;
		super.restore();
		_activeLocationId = locationId;
	}
	
	@Override
	public boolean validateCapacity(int slots)
	{
		int cap = (_owner == null ? Config.FREIGHT_SLOTS : _owner.getFreightLimit());
		
		return (getSize() + slots <= cap);
	}
	
	@Override
	public int getOwnerId()
	{
		if (_owner == null)
			return _tempOwnerId;
		
		return super.getOwnerId();
	}
	
	/**
	 * This provides support to load a new PcFreight without owner so that transactions can be done
	 * @param val The id of the owner.
	 */
	public void doQuickRestore(int val)
	{
		_tempOwnerId = val;
		restore();
	}
}