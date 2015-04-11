package Extensions.ItemHandlers;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.handler.IItemHandler;
import net.sf.l2j.gameserver.model.actor.L2Playable;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.network.serverpackets.InventoryUpdate;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.util.Rnd;

public class RandomRewardBox implements IItemHandler
{
	private final int[] REWARD_BOX_IDS =
	{
		Config.REWARD_BOX_ID
	};
	
	private final int[][] ITEMS =
	{
		{
			Config.FIRST_REWARD_ID,
			Config.FIRST_REWARD_COUNT
		},
		{
			Config.SECOND_REWARD_ID,
			Config.SECOND_REWARD_COUNT
		},
		{
			Config.THIRD_REWARD_ID,
			Config.THIRD_REWARD_COUNT
		}
	};
	
	public int[] getItemIds()
	{
		return REWARD_BOX_IDS;
	}
	
	private static void sendCompleteWindow(L2PcInstance player)
	{
		StringBuilder tb = new StringBuilder();
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		
		tb.append("<html><head>");
		tb.append("<title>Reward Box</title></head>");
		tb.append("<body>");
		tb.append("Hello " + player.getName() + ",<br>You have get your reward in your inventory.");
		tb.append("</body>");
		tb.append("</html");
		html.setHtml(tb.toString());
		player.sendPacket(html);
	}
	
	@Override
	public void useItem(L2Playable playable, ItemInstance item, boolean forceUse)
	{
		L2PcInstance player = null;
		
		if (playable == null)
			return;
		
		if (playable instanceof L2PcInstance)
			player = (L2PcInstance) playable;
		if (player != null)
			if (item.getItemId() == REWARD_BOX_IDS[0])
			{
				int[] randomSet = ITEMS[Rnd.get(ITEMS.length)];
				int id = randomSet[0];
				int count = randomSet[1];
				sendCompleteWindow(player);
				player.destroyItem(null, item, null, false);
				player.addItem("", id, count, null, true);
				player.sendPacket(new InventoryUpdate());
				player.getInventory().updateDatabase();
			}
	}
}