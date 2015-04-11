package net.sf.l2j.gameserver.model.actor.instance;

import java.util.StringTokenizer;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.datatables.ItemTable;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.model.item.kind.Item;
import net.sf.l2j.gameserver.model.itemcontainer.Inventory;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.EnchantResult;
import net.sf.l2j.gameserver.network.serverpackets.ItemList;
import net.sf.l2j.gameserver.network.serverpackets.MyTargetSelected;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.network.serverpackets.StatusUpdate;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.network.serverpackets.ValidateLocation;
import net.sf.l2j.util.Rnd;

public class L2EnchantNpcInstance extends L2NpcInstance
{
	
	/* Menu NPC *//** @formatter:off */
	private final String HTML_MEMU = "<html><title>Enchant NPC</title><body>"
	+ "<center>Hello there. What do you want ?<br>"
	+ "<a action=\"bypass -h npc_%objectId%_showewindow\">enchant your items</a></center><br><br><br><br>"
	+ "</body></html>";
	/** @formatter:on */
	
	/* Prices */
	private final int EnchantItemId = Config.ITEM_NEEDED_FOR_ENCH; // ID of the item needed
	private final int EnchantItemCount = Config.AM_ITEM_NEEDED_FOR_ENCH; // Amount of it
	private final String EnchantItemName = ItemTable.getInstance().getTemplate(Config.ITEM_NEEDED_FOR_ENCH).getName(); // name displayed
	
	/* enchaning options */
	private final int MAX_ENCHANT_LEVEL = Config.MAX_ENCH_FOR_NPC; // Enchant max
	private final int ENCHNAT_ADD = Config.ENCH_ADDITION; // ++ added on enchant
	private final int ENCHNAT_FAIL = Config.ENCH_FAIL_LVL; // item enchant when it fails
	private final int ENCHANT_SAFE_MAX = Config.ENCH_SAFE_NPC; // safe of enchanting
	private int ENCHANT_CHANCE_ARMOR = Config.ENCHANT_CHANCE_ARM_NPC; // armor enchant chance
	private int ENCHANT_CHANCE_WEAPON = Config.ENCHANT_CHANCE_WEAP_NPC; // weapon enchant chance
	private int ENCHANT_CHANCE_JEWELERY = Config.ENCHANT_CHANCE_JEWELERY_NPC; // jewel enchant
	
	/* Other */
	private final boolean shEnchantWindowAfterSelect = true;
	
	SystemMessage sm;
	
	public L2EnchantNpcInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void onAction(L2PcInstance player)
	{
		
		if (this != player.getTarget())
		{
			player.setTarget(this);
			MyTargetSelected my = new MyTargetSelected(getObjectId(), 0);
			player.sendPacket(my);
			player.sendPacket(new ValidateLocation(this));
		}
		else
		{
			if (!canInteract(player))
			{
				
			}
			else
			{
				NpcHtmlMessage html = new NpcHtmlMessage(1);
				html.setHtml(HTML_MEMU);
				sendHtmlMessage(player, html);
			}
		}
		player.sendPacket(ActionFailed.STATIC_PACKET);
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		StringTokenizer st = new StringTokenizer(command, " ");
		String cCommand = st.nextToken();
		if (cCommand.startsWith("showewindow"))
		{
			showEnchantWindow(player);
		}
		else if (cCommand.startsWith("showmwindow"))
		{
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			html.setHtml(HTML_MEMU);
			sendHtmlMessage(player, html);
		}
		else if (cCommand.startsWith("doenchant"))
		{
			doEnchnat(player, Integer.parseInt(st.nextToken()));
		}
	}
	
	private void sendHtmlMessage(L2PcInstance player, NpcHtmlMessage html)
	{
		html.replace("%objectId%", String.valueOf(getObjectId()));
		html.replace("%npcId%", String.valueOf(getNpcId()));
		player.sendPacket(html);
	}
	
	public void showEnchantWindow(L2PcInstance player)
	{
		StringBuilder tb = new StringBuilder();
		tb.append("<html><title>Enchant Items</title><body><center>");
		tb.append("Here you can enchant items.<br>Enchant that will be added: +" + "<font color=\"LEVEL\">" + ENCHNAT_ADD + "</font>.<br> Select item to be enchanted.<br></center>");
		tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
		tb.append("<center><font color=\"LEVEL\">Armor</font></center>");
		tb.append("<table width=\"300\" align=\"center\">");
		tb.append("<tr><td align=center><img src=\"icon.armor_helmet_i00\" width=32 height=32></td><td align=center><img src=\"icon.armor_t88_u_i00\" width=32 height=32></td><td align=center><img src=\"icon.armor_t88_l_i00\" width=32 height=32></td><td align=center><img src=\"icon.armor_t88_g_i00\" width=32 height=32></td><td align=center><img src=\"icon.armor_t88_b_i00\" width=32 height=32></td></tr>");
		tb.append("<tr><td align=center>" + eLink("Helmet", 0) + "</td><td align=center>" + eLink("Upper", 1) + "</td><td align=center>" + eLink("Lower", 2) + "</td><td align=center>" + eLink("Gloves", 3) + "</td><td align=center>" + eLink("Boots", 4) + "</td></tr>");
		tb.append("</table><br>");
		tb.append("<center><font color=\"LEVEL\">Weapon</font></center>");
		tb.append("<center><font color=\"LEVEL\">Jewels</font></center>");
		tb.append("<table width=\"300\" align=\"center\">");
		tb.append("<tr><td align=center><img src=\"icon.accessory_tateossian_necklace_i00\" width=32 height=32></td><td align=center><img src=\"icon.accessory_tateossian_earring_i00\" width=32 height=32></td><td align=center><img src=\"icon.accessory_tateossian_earring_i00\" width=32 height=32></td><td align=center><img src=\"icon.accessory_tateossian_ring_i00\" width=32 height=32></td><td align=center><img src=\"icon.accessory_tateossian_ring_i00\" width=32 height=32></td></tr>");
		tb.append("<tr><td align=center>" + eLink("Necklace", 7) + "</td><td align=center>" + eLink("L Earring", 8) + "</td><td align=center>" + eLink("R Earring", 9) + "</td><td align=center>" + eLink("L Ring", 10) + "</td><td align=center>" + eLink("R Ring", 11) + "</td></tr>");
		tb.append("</table><br>");
		tb.append("<center><font color=\"LEVEL\">Other</font></center>");
		tb.append("<table width=\"300\" align=\"center\">");
		tb.append("<tr><td align=center><img src=\"icon.weapon_forgotten_blade_i00\" width=32 height=32></td><td align=center><img src=\"icon.shield_imperial_crusader_shield_i00\" width=32 height=32></td><td align=center><img src=\"icon.weapon_voodoo_doll_i00\" width=32 height=32></td></tr>");
		tb.append("<tr><td align=center>" + eLink("Weapon", 5) + "</td><td align=center>" + eLink("Shield", 6) + "</td><td align=center>" + eLink("Tattoo", 12) + "</td></tr>");
		tb.append("</table><br><br>");
		tb.append("<center>Price: <font color=\"LEVEL\">" + EnchantItemCount + " " + EnchantItemName + "</font><br><br>");
		tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
		tb.append("<a action=\"bypass -h npc_%objectId%_showmwindow\">Back</a><br>");
		tb.append("</center></body></html>");
		NpcHtmlMessage html = new NpcHtmlMessage(1);
		html.setHtml(tb.toString());
		sendHtmlMessage(player, html);
	}
	
	private void doEnchnat(L2PcInstance player, int type)
	{
		int item_type = -1;
		int chance = 0;
		switch (type)
		{
			case 0:
				item_type = Inventory.PAPERDOLL_HEAD;
				break;
			case 1:
				item_type = Inventory.PAPERDOLL_CHEST;
				break;
			case 2:
				item_type = Inventory.PAPERDOLL_LEGS;
				break;
			case 3:
				item_type = Inventory.PAPERDOLL_GLOVES;
				break;
			case 4:
				item_type = Inventory.PAPERDOLL_FEET;
				break;
			case 5:
				item_type = Inventory.PAPERDOLL_RHAND;
				break;
			case 6:
				item_type = Inventory.PAPERDOLL_LHAND;
				break;
			case 7:
				item_type = Inventory.PAPERDOLL_NECK;
				break;
			case 8:
				item_type = Inventory.PAPERDOLL_LEAR;
				break;
			case 9:
				item_type = Inventory.PAPERDOLL_REAR;
				break;
			case 10:
				item_type = Inventory.PAPERDOLL_LFINGER;
				break;
			case 11:
				item_type = Inventory.PAPERDOLL_RFINGER;
				break;
			case 12:
				item_type = Inventory.PAPERDOLL_UNDER;
				break;
		
		}
		ItemInstance item = player.getInventory().getPaperdollItem(item_type);
		if (item != null)
		{
			int rndValue = Rnd.get(100);
			if (item.getEnchantLevel() >= MAX_ENCHANT_LEVEL && MAX_ENCHANT_LEVEL != 0)
			{
				player.sendPacket(SystemMessageId.INAPPROPRIATE_ENCHANT_CONDITION);
				return;
			}
			if (player.getInventory().getItemByItemId(EnchantItemId) == null || player.getInventory().getItemByItemId(EnchantItemId).getCount() < EnchantItemCount)
			{
				player.sendMessage("You don't have enough items.");
				return;
			}
			player.destroyItemByItemId("Consume", EnchantItemId, EnchantItemCount, player, false);
			if (item.getItem().getType2() == Item.TYPE2_WEAPON)
			{
				chance = ENCHANT_CHANCE_WEAPON;
			}
			if (item.getItem().getType2() == Item.TYPE2_SHIELD_ARMOR)
			{
				chance = ENCHANT_CHANCE_ARMOR;
			}
			if (item.getItem().getType2() == Item.TYPE2_ACCESSORY)
			{
				chance = ENCHANT_CHANCE_JEWELERY;
			}
			if (item.getEnchantLevel() < ENCHANT_SAFE_MAX)
			{
				chance = 100;
			}
			if (rndValue < chance)
			{
				if (item.getOwnerId() != player.getObjectId() || item.getEnchantLevel() >= MAX_ENCHANT_LEVEL && MAX_ENCHANT_LEVEL != 0)
				{
					player.sendPacket(SystemMessageId.INAPPROPRIATE_ENCHANT_CONDITION);
					return;
				}
				if (item.getLocation() != ItemInstance.ItemLocation.PAPERDOLL)
				{
					player.sendPacket(SystemMessageId.INAPPROPRIATE_ENCHANT_CONDITION);
					return;
				}
				if (item.getEnchantLevel() == 0)
				{
					sm = SystemMessage.getSystemMessage(SystemMessageId.S1_SUCCESSFULLY_ENCHANTED);
					sm.addItemName(item.getItemId());
					player.sendPacket(sm);
				}
				else
				{
					sm = SystemMessage.getSystemMessage(SystemMessageId.S1_S2_SUCCESSFULLY_ENCHANTED);
					sm.addNumber(item.getEnchantLevel());
					sm.addItemName(item.getItemId());
					player.sendPacket(sm);
				}
				item.setEnchantLevel(item.getEnchantLevel() + ENCHNAT_ADD);
				item.updateDatabase();
			}
			else
			{
				sm = SystemMessage.getSystemMessage(SystemMessageId.BLESSED_ENCHANT_FAILED);
				player.sendPacket(sm);
				item.setEnchantLevel(ENCHNAT_FAIL);
				item.updateDatabase();
			}
			sm = null;
			StatusUpdate su = new StatusUpdate(player);
			su.addAttribute(StatusUpdate.CUR_LOAD, player.getCurrentLoad());
			player.sendPacket(su);
			su = null;
			player.sendPacket(EnchantResult.UNSUCCESS);
			player.sendPacket(new ItemList(player, false));
			player.broadcastUserInfo();
		}
		else
		{
			player.sendMessage("Nothing is equipped in that slot.");
		}
		if (shEnchantWindowAfterSelect)
		{
			showEnchantWindow(player);
		}
	}
	
	private static String eLink(String arg0, int arg1)
	{
		return "<a action=\"bypass -h npc_%objectId%_doenchant " + arg1 + "\">" + arg0 + "</a>";
	}
}