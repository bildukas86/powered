package Extensions.ItemHandlers;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.handler.IItemHandler;
import net.sf.l2j.gameserver.model.actor.L2Playable;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.util.Rnd;

public class LuckyScroll implements IItemHandler
{
	private static final int[] ITEM_IDS =
	{
		Config.LUCKY_SCROLL_ID
	};
	
	@Override
	public void useItem(L2Playable playable, ItemInstance item, boolean forceUse)
	{
		if (!(playable instanceof L2PcInstance))
			return;
		
		L2PcInstance activeChar = (L2PcInstance) playable;
		
		int itemId = item.getItemId();
		if (Config.ENALBE_LUCKY_SCROLL)
		{
			if (itemId == Config.LUCKY_SCROLL_ID)
			{
				playable.destroyItem("Consume", item.getObjectId(), 1, null, false);
				switch (Rnd.get(9))
				{
					case 0:
					{
						activeChar.addItem("Blessed Scroll", 6569, 1 * 1, activeChar, true);
						activeChar.sendMessage("Earned a blessed scroll!");
						break;
					}
					case 1:
					{
						activeChar.addItem("Blessed Scroll", 6570, 1 * 1, activeChar, true);
						activeChar.sendMessage("Earned a blessed scroll!");
						break;
					}
					case 2:
					{
						activeChar.addItem("Blessed Scroll", 6571, 1 * 1, activeChar, true);
						activeChar.sendMessage("Earned a blessed scroll!");
						break;
					}
					case 3:
					{
						activeChar.addItem("Blessed Scroll", 6573, 1 * 1, activeChar, true);
						activeChar.sendMessage("Earned a blessed scroll!");
						break;
					}
					case 4:
					{
						activeChar.addItem("Blessed Scroll", 6574, 1 * 1, activeChar, true);
						activeChar.sendMessage("Earned a blessed scroll!");
						break;
					}
					case 5:
					{
						activeChar.addItem("Blessed Scroll", 6575, 1 * 1, activeChar, true);
						activeChar.sendMessage("Earned a blessed scroll!");
						break;
					}
					case 6:
					{
						activeChar.addItem("Blessed Scroll", 6576, 1 * 1, activeChar, true);
						activeChar.sendMessage("Earned a blessed scroll!");
						break;
					}
					case 7:
					{
						activeChar.addItem("Blessed Scroll", 6577, 1 * 1, activeChar, true);
						activeChar.sendMessage("Earned a blessed scroll!");
						break;
					}
					case 8:
					{
						activeChar.addItem("Blessed Scroll", 6578, 1 * 1, activeChar, true);
						activeChar.sendMessage("Earned a blessed scroll!");
						break;
					}
				}
			}
		}
	}
	
	public int[] getItemIds()
	{
		return ITEM_IDS;
	}
}