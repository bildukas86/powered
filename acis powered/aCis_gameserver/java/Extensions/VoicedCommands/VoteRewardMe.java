package Extensions.VoicedCommands;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;

public class VoteRewardMe implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{
		"rewardme"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		
		if (activeChar.isInOlympiadMode() || activeChar.getOlympiadGameId() != -1)
		{
			activeChar.sendMessage("You can't use that command inside Olympiad.");
			return false;
		}
		
		if (activeChar.getInventory().getItemByItemId(Config.VOTE_ITEM_ID).getCount() >= Config.VOTE_ITEM_AMOUNT)
		{
			activeChar.getInventory().destroyItemByItemId("Consume", Config.VOTE_ITEM_ID, Config.VOTE_ITEM_AMOUNT, activeChar, null);
			activeChar.sendMessage(Config.VOTE_ITEM_AMOUNT + " " + Config.VOTE_ITEM_NAME + "(s) have been consumed.");
			MagicSkillUse mgc = new MagicSkillUse(activeChar, activeChar, Config.VOTE_BUFF_ID, Config.VOTE_BUFF_LVL, 1, 0);
			SkillTable.getInstance().getInfo(Config.VOTE_BUFF_ID, Config.VOTE_BUFF_LVL).getEffects(activeChar, activeChar);
			activeChar.broadcastPacket(mgc);
			activeChar.sendMessage("You have been blessed with the effects of the Vote Buff!");
		}
		else
		{
			activeChar.sendMessage("You don't have enough " + Config.VOTE_ITEM_NAME + "(s) in order to get rewarded!");
		}
		return true;
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return VOICED_COMMANDS;
	}
}