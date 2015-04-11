package Extensions.ItemHandlers;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.handler.IItemHandler;
import net.sf.l2j.gameserver.model.actor.L2Playable;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;

public class ClanRepsItem implements IItemHandler
{
	private static final int ITEM_IDS[] =
	{
		Config.CR_ITEM_REPS_ITEM_ID
	};
	
	@Override
	public void useItem(L2Playable playable, ItemInstance item, boolean forceUse)
	{
		if (!(playable instanceof L2PcInstance))
		{
			return;
		}
		
		L2PcInstance activeChar = (L2PcInstance) playable;
		
		if (!activeChar.isClanLeader())
		{
			activeChar.sendMessage("This can be used only by Clan Leaders!");
			return;
		}
		
		else if (!(activeChar.getClan().getLevel() >= Config.CR_ITEM_MIN_CLAN_LVL))
		{
			activeChar.sendMessage("Your Clan Level is not big enough to use this item!");
			return;
		}
		else
		{
			activeChar.getClan().setReputationScore(activeChar.getClan().getReputationScore() + Config.CR_ITEM_REPS_TO_BE_AWARDED);
			activeChar.sendMessage("Your clan has earned " + Config.CR_ITEM_REPS_TO_BE_AWARDED + " rep points!");
			MagicSkillUse MSU = new MagicSkillUse(activeChar, activeChar, 2024, 1, 1, 0);
			activeChar.broadcastPacket(MSU);
			playable.destroyItem("Consume", item.getObjectId(), 1, null, false);
		}
	}
	
	public int[] getItemIds()
	{
		return ITEM_IDS;
	}
}