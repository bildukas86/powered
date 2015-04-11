package net.sf.l2j.gameserver.model.actor.instance;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public final class L2NoblesseInstance extends L2NpcInstance
{
	private static final int BLACK_CROWN = 9210;
	private static final int GOLD_CROWN = 9211;
	private static final int RED_CROWN = 9212;
	private static final int SILVER_CROWN = 9213;
	private static final int NOBLESSE_TIARA = 7694;
	
	public L2NoblesseInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		if (command.startsWith("becomeNoblesse"))
		{
			int currBirth = L2RebirthInstance.getRebirthLevel(player);
			if (player.isNoble())
			{
				NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
				html.setFile("data/html/mods/Noblesse/noblesse-already.htm");
				
				player.sendPacket(html);
				return;
			}
			else if (player.getLevel() < 78)
			{
				NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
				html.setFile("data/html/mods/Noblesse/noblesse-level.htm");
				
				player.sendPacket(html);
				return;
			}
			else if (currBirth < Config.REBIRTH_MAX)
			{
				NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
				html.setFile("data/html/mods/Noblesse/noblesse-rebirths.htm");
				
				player.sendPacket(html);
				return;
			}
			else if (player.getInventory().getItemByItemId(BLACK_CROWN) == null || player.getInventory().getItemByItemId(GOLD_CROWN) == null || player.getInventory().getItemByItemId(RED_CROWN) == null || player.getInventory().getItemByItemId(SILVER_CROWN) == null)
			{
				NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
				html.setFile("data/html/mods/Noblesse/noblesse-crowns.htm");
				
				player.sendPacket(html);
				return;
			}
			else
			{
				NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
				html.setFile("data/html/mods/Noblesse/noblesse-successfully.htm");
				
				player.destroyItemByItemId("Consume", BLACK_CROWN, 1, player, true);
				player.destroyItemByItemId("Consume", GOLD_CROWN, 1, player, true);
				player.destroyItemByItemId("Consume", RED_CROWN, 1, player, true);
				player.destroyItemByItemId("Consume", SILVER_CROWN, 1, player, true);
				player.addItem("Loot", NOBLESSE_TIARA, 1, player, true);
				player.setNoble(true, true);
				player.sendPacket(html);
			}
		}
		else
			super.onBypassFeedback(player, command);
	}
	
	@Override
	public void showChatWindow(L2PcInstance player)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
		html.setFile("data/html/mods/Noblesse/noblesse.htm");
		html.replace("%objectId%", String.valueOf(player.getTargetId()));
		html.replace("%blackcrown%", player.getInventory().getItemByItemId(BLACK_CROWN) == null ? "l2ui.CheckBox" : "l2ui.CheckBox_checked");
		html.replace("%goldcrown%", player.getInventory().getItemByItemId(GOLD_CROWN) == null ? "l2ui.CheckBox" : "l2ui.CheckBox_checked");
		html.replace("%redcrown%", player.getInventory().getItemByItemId(RED_CROWN) == null ? "l2ui.CheckBox" : "l2ui.CheckBox_checked");
		html.replace("%silvercrown%", player.getInventory().getItemByItemId(SILVER_CROWN) == null ? "l2ui.CheckBox" : "l2ui.CheckBox_checked");
		html.replace("%level%", player.getLevel() < 78 ? "l2ui.CheckBox" : "l2ui.CheckBox_checked");
		
		int currBirth = L2RebirthInstance.getRebirthLevel(player);
		html.replace("%rebbox%", currBirth < Config.REBIRTH_MAX ? "l2ui.CheckBox" : "l2ui.CheckBox_checked");
		
		switch (currBirth)
		{
			case 0:
				html.replace("%rebirth%", "0/3");
				break;
			
			case 1:
				html.replace("%rebirth%", "1/3");
				break;
			
			case 2:
				html.replace("%rebirth%", "2/3");
				break;
			
			case 3:
				html.replace("%rebirth%", "3/3");
				break;
		}
		
		player.sendPacket(html);
	}
}