package Extensions.ItemHandlers;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.handler.IItemHandler;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.L2Playable;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;

public class SkillGiver implements IItemHandler
{
	private static final int[] ITEM_IDS =
	{
		Config.SKILL_GIVER_SKILL_ID
	};
	
	@Override
	public void useItem(L2Playable playable, ItemInstance item, boolean forceUse)
	{
		
		if (!(playable instanceof L2PcInstance))
			return;
		
		L2PcInstance activeChar = (L2PcInstance) playable;
		if (activeChar.isInOlympiadMode())
		{
			activeChar.sendMessage("You can't you this in Olympiad.");
			return;
		}
		
		L2Skill skillGiverSkill = SkillTable.getInstance().getInfo(Config.SKILL_GIVER_SKILL_LEVEL, Config.SKILL_GIVER_ID);
		
		activeChar.addSkill(skillGiverSkill, true);
		activeChar.sendMessage("For now and forever you will got a new skill.");
		playable.destroyItem("Consume", item.getObjectId(), 1, null, false);
		activeChar.broadcastUserInfo();
		activeChar.sendSkillList();
	}
	
	public int[] getItemIds()
	{
		return ITEM_IDS;
	}
}