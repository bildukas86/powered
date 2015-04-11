package net.sf.l2j.gameserver.model.actor.instance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.StringTokenizer;

import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.ai.CtrlIntention;
import net.sf.l2j.gameserver.datatables.ItemTable;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.CharInfo;
import net.sf.l2j.gameserver.network.serverpackets.InventoryUpdate;
import net.sf.l2j.gameserver.network.serverpackets.MyTargetSelected;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.network.serverpackets.UserInfo;
import net.sf.l2j.gameserver.network.serverpackets.ValidateLocation;

public class L2SmartMultisellInstance extends L2NpcInstance
{
	public L2SmartMultisellInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		if (player == null)
		{
			return;
		}
		
		if (command.startsWith("buyItem "))
		{
			String itemId = null;
			StringTokenizer st = new StringTokenizer(command, " ");
			
			while (st.hasMoreTokens())
			{
				itemId = st.nextToken();
			}
			
			int id = Integer.parseInt(itemId);
			
			if (player.getInventory().getItemByItemId(getItemCostId(id)).getCount() >= getItemCostCount(id))
			{
				player.getInventory().destroyItemByItemId("delete", getItemCostId(id), getItemCostCount(id), player, null);
				
				ItemInstance item = null;
				item = player.getInventory().addItem("Item", getItemId(id), 1, null, null);
				item.setEnchantLevel(getItemEnchant(id));
				
				// send packets
				InventoryUpdate iu = new InventoryUpdate();
				iu.addItem(item);
				player.sendPacket(iu);
				player.broadcastPacket(new CharInfo(player));
				player.sendPacket(new UserInfo(player));
				
				player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.YOU_PICKED_UP_S2_S1).addItemName(item.getItemId()).addNumber(1));
				iu = null;
			}
			else
			{
				player.sendMessage("You don't have enough items in order to buy this one");
				return;
			}
		}
		
	}
	
	@Override
	public void onAction(L2PcInstance player)
	{
		if (this != player.getTarget())
		{
			player.setTarget(this);
			
			player.sendPacket(new MyTargetSelected(getObjectId(), 0));
			
			player.sendPacket(new ValidateLocation(this));
		}
		else if (!canInteract(player))
		{
			player.getAI().setIntention(CtrlIntention.INTERACT, this);
		}
		else
		{
			showHtmlWindow(player);
		}
		
		player.sendPacket(ActionFailed.STATIC_PACKET);
	}
	
	private void showHtmlWindow(L2PcInstance activeChar)
	{
		NpcHtmlMessage nhm = new NpcHtmlMessage(5);
		StringBuilder tb = new StringBuilder("");
		
		tb.append("<html><head><title>Smart Shop</title></head><body>");
		tb.append("<center>");
		tb.append("<table width=\"250\" cellpadding=\"5\" bgcolor=\"000000\">");
		tb.append("<tr>");
		tb.append("<td width=\"45\" valign=\"top\" align=\"center\"><img src=\"L2ui_ch3.menubutton4\" width=\"38\" height=\"38\"></td>");
		tb.append("<td valign=\"top\"><font color=\"FF6600\">Smart Shop</font>");
		tb.append("<br1><font color=\"00FF00\">" + activeChar.getName() + "</font>, Here you can buy Enchanted Gear.</td>");
		tb.append("</tr>");
		tb.append("</table>");
		tb.append("</center>");
		tb.append("<center>");
		
		for (int i = 1; i <= getRowsCount(); i++)
			tb.append("<br><a action=\"bypass -h npc_" + getObjectId() + "_buyItem " + i + "\">Item name: " + ItemTable.getInstance().getTemplate(getItemId(i)).getName() + " Enchant: +" + getItemEnchant(i) + " Cost: " + getItemCostCount(i) + " " + ItemTable.getInstance().getTemplate(getItemCostId(i)).getName() + "</a>");
		
		tb.append("</center>");
		tb.append("<center>");
		tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
		tb.append("</center>");
		tb.append("</body></html>");
		
		nhm.setHtml(tb.toString());
		activeChar.sendPacket(nhm);
		
		activeChar.sendPacket(ActionFailed.STATIC_PACKET);
	}
	
	private static int getRowsCount()
	{
		int rows = 0;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT * FROM smart_shop");
			
			ResultSet rset = statement.executeQuery();
			while (rset.next())
			{
				rows++;
			}
			rset.close();
			statement.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return rows;
	}
	
	private static int getItemId(int itemId)
	{
		int itemIdd = 0;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT item_id FROM smart_shop WHERE id=?");
			statement.setInt(1, itemId);
			
			ResultSet rset = statement.executeQuery();
			while (rset.next())
			{
				itemIdd = rset.getInt("item_id");
			}
			rset.close();
			statement.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return itemIdd;
	}
	
	private static int getItemCostId(int costid)
	{
		int costIt = 0;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT cost_item_id FROM smart_shop WHERE id=?");
			statement.setInt(1, costid);
			
			ResultSet rset = statement.executeQuery();
			while (rset.next())
			{
				costIt = rset.getInt("cost_item_id");
			}
			rset.close();
			statement.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return costIt;
	}
	
	private static int getItemCostCount(int costid)
	{
		int costIt = 0;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT cost_item_count FROM smart_shop WHERE id=?");
			statement.setInt(1, costid);
			
			ResultSet rset = statement.executeQuery();
			while (rset.next())
			{
				costIt = rset.getInt("cost_item_count");
			}
			rset.close();
			statement.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return costIt;
	}
	
	private static int getItemEnchant(int id)
	{
		int itemEnch = 0;
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT item_enchant FROM smart_shop WHERE id=?");
			statement.setInt(1, id);
			
			ResultSet rset = statement.executeQuery();
			while (rset.next())
			{
				itemEnch = rset.getInt("item_enchant");
			}
			rset.close();
			statement.close();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return itemEnch;
	}
}