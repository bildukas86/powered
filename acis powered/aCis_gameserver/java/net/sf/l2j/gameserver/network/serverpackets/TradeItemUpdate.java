package net.sf.l2j.gameserver.network.serverpackets;

import java.util.Arrays;

import net.sf.l2j.gameserver.model.TradeList;
import net.sf.l2j.gameserver.model.TradeList.TradeItem;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;

public class TradeItemUpdate extends L2GameServerPacket
{
	private ItemInstance[] _items;
	private TradeItem[] _currentTrade;
	
	public TradeItemUpdate(TradeList trade, L2PcInstance activeChar)
	{
		_items = activeChar.getInventory().getItems();
		_currentTrade = trade.getItems();
	}
	
	private int getItemCount(int objectId)
	{
		for (ItemInstance item : _items)
			if (item.getObjectId() == objectId)
				return item.getCount();
		
		return 0;
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0x74);
		try
		{
			writeH(_currentTrade.length);
			for (TradeItem item : _currentTrade)
			{
				int availableCount = getItemCount(item.getObjectId()) - item.getCount();
				boolean stackable = item.getItem().isStackable();
				
				if (availableCount == 0)
				{
					availableCount = 1;
					stackable = false;
				}
				
				writeH(stackable ? 3 : 2);
				writeH(item.getItem().getType1());
				writeD(item.getObjectId());
				writeD(item.getItem().getItemId());
				writeD(availableCount);
				writeH(item.getItem().getType2());
				writeH(0x00);
				writeD(item.getItem().getBodyPart());
				writeH(item.getEnchant());
				writeH(0x00);
				writeH(0x00);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			Arrays.fill(_items, null);
			Arrays.fill(_currentTrade, null);
			_items = null;
			_currentTrade = null;
		}
	}
}