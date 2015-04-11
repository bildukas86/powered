package net.sf.l2j.gameserver.model.actor.instance;

import net.sf.l2j.gameserver.ai.CtrlIntention;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.ItemList;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.network.serverpackets.SetupGauge;
import net.sf.l2j.gameserver.util.Broadcast;
import net.sf.l2j.util.Rnd;

public class L2CasinoInstance extends L2NpcInstance
{
	public String filename;
	
	public L2CasinoInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		if (player == null)
			return;
		
		if (command.startsWith("play1"))
		{
			if (player.getInventory().getInventoryItemCount(6392, 0) >= 2)
				Casino1(player);
			else
				player.sendMessage("You don't have enough items.");
		}
		
		else if (command.startsWith("play2"))
		{
			if (player.getInventory().getInventoryItemCount(6392, 0) >= 4)
				Casino2(player);
			else
				player.sendMessage("You don't have enough items.");
		}
		else if (command.startsWith("play3"))
		{
			if (player.getInventory().getInventoryItemCount(6392, 0) >= 8)
				Casino3(player);
			else
				player.sendMessage("You don't have enough items.");
		}
		else if (command.startsWith("play4"))
		{
			if (player.getInventory().getInventoryItemCount(6392, 0) >= 16)
				Casino4(player);
			else
				player.sendMessage("You don't have enough items.");
		}
		else if (command.startsWith("play5"))
		{
			if (player.getInventory().getInventoryItemCount(57, 0) >= 500000)
				Casino5(player);
			else
				player.sendMessage("You don't have enough items.");
		}
		else if (command.startsWith("play6"))
		{
			if (player.getInventory().getInventoryItemCount(57, 0) >= 1000000)
				Casino6(player);
			else
				player.sendMessage("You don't have enough items.");
		}
		else if (command.startsWith("play7"))
		{
			if (player.getInventory().getInventoryItemCount(57, 0) >= 10000000)
				Casino7(player);
			else
				player.sendMessage("You don't have enough items.");
		}
		else
			player.sendMessage("Wrong bypass command.");
	}
	
	public static void displayCongrats(L2PcInstance player)
	{
		MagicSkillUse MSU = new MagicSkillUse(player, player, 2024, 1, 1, 0);
		player.broadcastPacket(MSU);
		player.sendMessage("Congratulations, you won!");
	}
	
	@Override
	public void showChatWindow(L2PcInstance player, int val)
	{
		filename = (getHtmlPath(getNpcId(), val));
		NpcHtmlMessage msg = new NpcHtmlMessage(this.getObjectId());
		msg.setHtml(casinoWindow(player));
		msg.replace("%objectId%", String.valueOf(this.getObjectId()));
		player.sendPacket(msg);
	}
	
	private static String casinoWindow(L2PcInstance player)
	{
		StringBuilder tb = new StringBuilder();
		tb.append("<html><title>Casino Manager</title><body>");
		tb.append("<center>");
		tb.append("<br>");
		tb.append("<font color=\"999999\">Chance to win : 50%</font><br>");
		tb.append("<img src=\"L2UI.SquareGray\" width=\"200\" height=\"1\"><br>");
		tb.append("Welcome " + player.getName() + "<br>");
		tb.append("<tr><td>Double or Nothing ?</td></tr><br>");
		tb.append("<img src=\"L2UI.SquareGray\" width=\"280\" height=\"1\"></center><br>");
		tb.append("<center>");
		tb.append("Place your bets");
		tb.append("</center>");
		tb.append("<img src=\"L2UI.SquareGray\" width=\"280\" height=\"1\"></center><br>");
		tb.append("<br>");
		tb.append("<center>");
		tb.append("<tr>");
		tb.append("<td><button value=\"2 Event Medals\" action=\"bypass -h npc_%objectId%_play1\" back=\"sek.cbui75\" fore=\"sek.cbui75\" width=211 height=21></td>");
		tb.append("<td><button value=\"4 Event Medals\" action=\"bypass -h npc_%objectId%_play2\" back=\"sek.cbui75\" fore=\"sek.cbui75\" width=211 height=21></td>");
		tb.append("</tr>");
		tb.append("<tr>");
		tb.append("<td><button value=\"8 Event Medals\" action=\"bypass -h npc_%objectId%_play3\" back=\"sek.cbui75\" fore=\"sek.cbui75\" width=211 height=21></td>");
		tb.append("<td><button value=\"16 Event Medals\" action=\"bypass -h npc_%objectId%_play4\" back=\"sek.cbui75\" fore=\"sek.cbui75\" width=211 height=21></td>");
		tb.append("</tr>");
		tb.append("<tr>");
		tb.append("<td><button value=\"500k\" action=\"bypass -h npc_%objectId%_play5\" back=\"sek.cbui75\" fore=\"sek.cbui75\" width=211 height=21></td>");
		tb.append("<td><button value=\"1kk\" action=\"bypass -h npc_%objectId%_play6\" back=\"sek.cbui75\" fore=\"sek.cbui75\" width=211 height=21></td>");
		tb.append("</tr>");
		tb.append("<tr>");
		tb.append("<td><button value=\"10kk\" action=\"bypass -h npc_%objectId%_play7\" back=\"sek.cbui75\" fore=\"sek.cbui75\" width=211 height=21></td>");
		tb.append("</tr>");
		tb.append("</center>");
		tb.append("<center><img src=\"L2UI.SquareGray\" width=\"280\" height=\"1\">");
		tb.append("</body></html>");
		player.sendPacket(ActionFailed.STATIC_PACKET);
		return tb.toString();
	}
	
	public static void Casino1(L2PcInstance player)
	{
		int unstuckTimer = 1000;
		player.setTarget(player);
		player.getAI().setIntention(CtrlIntention.INTERACT);
		player.disableAllSkills();
		MagicSkillUse msk = new MagicSkillUse(player, 361, 1, unstuckTimer, 0);
		Broadcast.toSelfAndKnownPlayersInRadius(player, msk, 810000);
		SetupGauge sg = new SetupGauge(0, unstuckTimer);
		player.sendPacket(sg);
		if (player.isDead())
			return;
		
		player.setIsIn7sDungeon(false);
		player.enableAllSkills();
		int chance = Rnd.get(2);
		
		if (player.getInventory().getInventoryItemCount(6392, 0) >= 2)
		{
			if (chance == 0)
			{
				displayCongrats(player);
				player.getInventory().addItem("Adena", 6392, 2, player, null);
				ItemList il = new ItemList(player.getClient().getActiveChar(), true);
				player.sendPacket(il);
			}
			if (chance == 1)
			{
				player.sendMessage("You lost the bet");
				player.getInventory().destroyItemByItemId("Adena", 6392, 2, player, null);
				ItemList il = new ItemList(player.getClient().getActiveChar(), true);
				player.sendPacket(il);
			}
		}
		else
			player.sendMessage("You do not have enough items.");
	}
	
	public static void Casino2(L2PcInstance player)
	{
		int unstuckTimer = 1000;
		player.setTarget(player);
		player.getAI().setIntention(CtrlIntention.INTERACT);
		player.disableAllSkills();
		MagicSkillUse msk = new MagicSkillUse(player, 361, 1, unstuckTimer, 0);
		Broadcast.toSelfAndKnownPlayersInRadius(player, msk, 810000);
		SetupGauge sg = new SetupGauge(0, unstuckTimer);
		player.sendPacket(sg);
		if (player.isDead())
			return;
		
		player.setIsIn7sDungeon(false);
		player.enableAllSkills();
		int chance = Rnd.get(3);
		
		if (player.getInventory().getInventoryItemCount(6392, 0) >= 4)
		{
			if (chance == 0)
			{
				displayCongrats(player);
				player.getInventory().addItem("Adena", 6392, 4, player, null);
				ItemList il = new ItemList(player.getClient().getActiveChar(), true);
				player.sendPacket(il);
			}
			if (chance == 1)
			{
				player.sendMessage("You lost the bet.");
				player.getInventory().destroyItemByItemId("Adena", 6392, 4, player, null);
				ItemList il = new ItemList(player.getClient().getActiveChar(), true);
				player.sendPacket(il);
			}
			if (chance == 2)
			{
				player.sendMessage("You lost the bet.");
				player.getInventory().destroyItemByItemId("Adena", 6392, 4, player, null);
				ItemList il = new ItemList(player.getClient().getActiveChar(), true);
				player.sendPacket(il);
			}
		}
		else
			player.sendMessage("You do not have enough items.");
	}
	
	public static void Casino3(L2PcInstance player)
	{
		int unstuckTimer = 1000;
		player.setTarget(player);
		player.getAI().setIntention(CtrlIntention.INTERACT);
		player.disableAllSkills();
		MagicSkillUse msk = new MagicSkillUse(player, 361, 1, unstuckTimer, 0);
		Broadcast.toSelfAndKnownPlayersInRadius(player, msk, 810000);
		SetupGauge sg = new SetupGauge(0, unstuckTimer);
		player.sendPacket(sg);
		if (player.isDead())
			return;
		player.setIsIn7sDungeon(false);
		player.enableAllSkills();
		int chance = Rnd.get(3);
		
		if (player.getInventory().getInventoryItemCount(6392, 0) >= 8)
		{
			if (chance == 0)
			{
				displayCongrats(player);
				player.getInventory().addItem("Adena", 6392, 8, player, null);
				ItemList il = new ItemList(player.getClient().getActiveChar(), true);
				player.sendPacket(il);
			}
			if (chance == 1)
			{
				player.sendMessage("You lost the bet.");
				player.getInventory().destroyItemByItemId("Adena", 6392, 8, player, null);
				ItemList il = new ItemList(player.getClient().getActiveChar(), true);
				player.sendPacket(il);
			}
			if (chance == 2)
			{
				player.sendMessage("You lost the bet.");
				player.getInventory().destroyItemByItemId("Adena", 6392, 8, player, null);
				ItemList il = new ItemList(player.getClient().getActiveChar(), true);
				player.sendPacket(il);
			}
		}
		else
			player.sendMessage("You do not have enough items.");
	}
	
	public static void Casino4(L2PcInstance player)
	{
		int unstuckTimer = 1000;
		player.setTarget(player);
		player.getAI().setIntention(CtrlIntention.INTERACT);
		player.disableAllSkills();
		MagicSkillUse msk = new MagicSkillUse(player, 361, 1, unstuckTimer, 0);
		Broadcast.toSelfAndKnownPlayersInRadius(player, msk, 810000);
		SetupGauge sg = new SetupGauge(0, unstuckTimer);
		player.sendPacket(sg);
		if (player.isDead())
			return;
		
		player.setIsIn7sDungeon(false);
		player.enableAllSkills();
		int chance = Rnd.get(3);
		
		if (player.getInventory().getInventoryItemCount(6392, 0) >= 16)
		{
			if (chance == 0)
			{
				displayCongrats(player);
				player.getInventory().addItem("Adena", 6392, 16, player, null);
				ItemList il = new ItemList(player.getClient().getActiveChar(), true);
				player.sendPacket(il);
			}
			if (chance == 1)
			{
				player.sendMessage("You lost the bet.");
				player.getInventory().destroyItemByItemId("Adena", 6392, 16, player, null);
				ItemList il = new ItemList(player.getClient().getActiveChar(), true);
				player.sendPacket(il);
			}
			if (chance == 2)
			{
				player.sendMessage("You lost the bet.");
				player.getInventory().destroyItemByItemId("Adena", 6392, 16, player, null);
				ItemList il = new ItemList(player.getClient().getActiveChar(), true);
				player.sendPacket(il);
			}
		}
		else
			player.sendMessage("You do not have enough items.");
	}
	
	public static void Casino5(L2PcInstance player)
	{
		int unstuckTimer = 1000;
		player.setTarget(player);
		player.getAI().setIntention(CtrlIntention.INTERACT);
		player.disableAllSkills();
		MagicSkillUse msk = new MagicSkillUse(player, 361, 1, unstuckTimer, 0);
		Broadcast.toSelfAndKnownPlayersInRadius(player, msk, 810000);
		SetupGauge sg = new SetupGauge(0, unstuckTimer);
		player.sendPacket(sg);
		if (player.isDead())
			return;
		
		player.setIsIn7sDungeon(false);
		player.enableAllSkills();
		int chance = Rnd.get(2);
		
		if (player.getInventory().getInventoryItemCount(57, 0) >= 500000)
		{
			if (chance == 0)
			{
				displayCongrats(player);
				player.getInventory().addItem("Adena", 57, 500000, player, null);
				ItemList il = new ItemList(player.getClient().getActiveChar(), true);
				player.sendPacket(il);
			}
			if (chance == 1)
			{
				player.sendMessage("You lost the bet.");
				player.getInventory().destroyItemByItemId("Adena", 57, 500000, player, null);
				ItemList il = new ItemList(player.getClient().getActiveChar(), true);
				player.sendPacket(il);
			}
		}
		else
			player.sendMessage("You do not have enough items.");
	}
	
	public static void Casino6(L2PcInstance player)
	{
		int unstuckTimer = 1000;
		player.setTarget(player);
		player.getAI().setIntention(CtrlIntention.INTERACT);
		player.disableAllSkills();
		MagicSkillUse msk = new MagicSkillUse(player, 361, 1, unstuckTimer, 0);
		Broadcast.toSelfAndKnownPlayersInRadius(player, msk, 810000);
		SetupGauge sg = new SetupGauge(0, unstuckTimer);
		player.sendPacket(sg);
		if (player.isDead())
			return;
		
		player.setIsIn7sDungeon(false);
		player.enableAllSkills();
		int chance = Rnd.get(3);
		
		if (player.getInventory().getInventoryItemCount(57, 0) >= 1000000)
		{
			if (chance == 0)
			{
				displayCongrats(player);
				player.getInventory().addItem("Adena", 57, 1000000, player, null);
				ItemList il = new ItemList(player.getClient().getActiveChar(), true);
				player.sendPacket(il);
			}
			if (chance == 1)
			{
				player.sendMessage("You lost the bet.");
				player.getInventory().destroyItemByItemId("Adena", 57, 1000000, player, null);
				ItemList il = new ItemList(player.getClient().getActiveChar(), true);
				player.sendPacket(il);
			}
			if (chance == 2)
			{
				player.sendMessage("You lost the bet");
				player.getInventory().destroyItemByItemId("Adena", 57, 1000000, player, null);
				ItemList il = new ItemList(player.getClient().getActiveChar(), true);
				player.sendPacket(il);
			}
		}
		else
			player.sendMessage("You do not have enough items.");
	}
	
	public static void Casino7(L2PcInstance player)
	{
		int unstuckTimer = 1000;
		player.setTarget(player);
		player.getAI().setIntention(CtrlIntention.INTERACT);
		player.disableAllSkills();
		MagicSkillUse msk = new MagicSkillUse(player, 361, 1, unstuckTimer, 0);
		Broadcast.toSelfAndKnownPlayersInRadius(player, msk, 810000);
		SetupGauge sg = new SetupGauge(0, unstuckTimer);
		player.sendPacket(sg);
		if (player.isDead())
			return;
		
		player.setIsIn7sDungeon(false);
		player.enableAllSkills();
		int chance = Rnd.get(3);
		
		if (player.getInventory().getInventoryItemCount(57, 0) >= 10000000)
		{
			if (chance == 0)
			{
				displayCongrats(player);
				player.getInventory().addItem("Adena", 57, 10000000, player, null);
				ItemList il = new ItemList(player.getClient().getActiveChar(), true);
				player.sendPacket(il);
			}
			if (chance == 1)
			{
				player.sendMessage("You lost the bet.");
				player.getInventory().destroyItemByItemId("Adena", 57, 10000000, player, null);
				ItemList il = new ItemList(player.getClient().getActiveChar(), true);
				player.sendPacket(il);
			}
			if (chance == 2)
			{
				player.sendMessage("You lost the bet.");
				player.getInventory().destroyItemByItemId("Adena", 57, 10000000, player, null);
				ItemList il = new ItemList(player.getClient().getActiveChar(), true);
				player.sendPacket(il);
			}
		}
		else
			player.sendMessage("You do not have enough items.");
	}
}