package Extensions.StartUpSystem;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.network.L2GameClient;
import net.sf.l2j.gameserver.network.serverpackets.ExShowScreenMessage;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class StartupSystem
{
	
	public static void startIt(L2PcInstance activeChar)
	{
		if (activeChar.getStartStage() == 1)
			firstStage(activeChar);
	}
	
	public static void firstStage(L2PcInstance activeChar)
	{
		long _exp;
		
		_exp = 15422851L;
		
		activeChar.sendPacket(new ExShowScreenMessage(" Welcome to L2J xTreme Smart Startup System ", 6000));
		activeChar.setStartStage(0);
		if (activeChar.getClassId().getId() == 0)
		{
			activeChar.removeExpAndSp(activeChar.getExp(), 0);
			activeChar.addExpAndSp(_exp, 0);
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your class " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Gladiator\" action=\"bypass -h Startup_gladi\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Warlord\" action=\"bypass -h Startup_warlord\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Paladin\" action=\"bypass -h Startup_pala\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Avenger\" action=\"bypass -h Startup_daven\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Treasure Hunter\" action=\"bypass -h Startup_tres\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Hawkeye\" action=\"bypass -h Startup_hawkeye\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (activeChar.getClassId().getId() == 10)
		{
			activeChar.removeExpAndSp(activeChar.getExp(), 0);
			activeChar.addExpAndSp(_exp, 0);
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your class " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Sorcerer\" action=\"bypass -h Startup_sorce\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Necromancer\" action=\"bypass -h Startup_necro\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Warlock\" action=\"bypass -h Startup_warlock\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Bishop\" action=\"bypass -h Startup_bishop\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Prophet\" action=\"bypass -h Startup_prophet\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (activeChar.getClassId().getId() == 18)
		{
			activeChar.removeExpAndSp(activeChar.getExp(), 0);
			activeChar.addExpAndSp(_exp, 0);
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your class " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Temple Knight\" action=\"bypass -h Startup_tknight\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Swordsinger\" action=\"bypass -h Startup_sws\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Plains Walker\" action=\"bypass -h Startup_pwalker\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Silver Ranger\" action=\"bypass -h Startup_srang\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (activeChar.getClassId().getId() == 25)
		{
			activeChar.removeExpAndSp(activeChar.getExp(), 0);
			activeChar.addExpAndSp(_exp, 0);
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your class " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Spellsinger\" action=\"bypass -h Startup_sps\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Elemental Summoner\" action=\"bypass -h Startup_elesum\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Elder\" action=\"bypass -h Startup_elder\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (activeChar.getClassId().getId() == 31)
		{
			activeChar.removeExpAndSp(activeChar.getExp(), 0);
			activeChar.addExpAndSp(_exp, 0);
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your class " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Shillien Knight\" action=\"bypass -h Startup_shkni\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Bladedancer\" action=\"bypass -h Startup_bld\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Abyss Walker\" action=\"bypass -h Startup_awalk\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Phantom Ranger\" action=\"bypass -h Startup_phra\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (activeChar.getClassId().getId() == 38)
		{
			activeChar.removeExpAndSp(activeChar.getExp(), 0);
			activeChar.addExpAndSp(_exp, 0);
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your class " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Spellhowler\" action=\"bypass -h Startup_shl\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Phantom Summoner\" action=\"bypass -h Startup_phsum\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Shilien Elder\" action=\"bypass -h Startup_selder\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (activeChar.getClassId().getId() == 44)
		{
			activeChar.removeExpAndSp(activeChar.getExp(), 0);
			activeChar.addExpAndSp(_exp, 0);
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your class " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Destroyer\" action=\"bypass -h Startup_dest\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Tyrant\" action=\"bypass -h Startup_tyrant\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (activeChar.getClassId().getId() == 49)
		{
			activeChar.removeExpAndSp(activeChar.getExp(), 0);
			activeChar.addExpAndSp(_exp, 0);
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your class " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Overlord\" action=\"bypass -h Startup_overlord\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Warcryer\" action=\"bypass -h Startup_warcryer\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (activeChar.getClassId().getId() == 53)
		{
			activeChar.removeExpAndSp(activeChar.getExp(), 0);
			activeChar.addExpAndSp(_exp, 0);
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your class " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Bounty Hunter\" action=\"bypass -h Startup_bountyh\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Warsmith\" action=\"bypass -h Startup_warsmith\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
	}
	
	public static void checkClassIds(L2PcInstance activeChar)
	{
		if (activeChar.getClassId().getId() == 2)
		{
			activeChar.setClassId(88);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 3)
		{
			activeChar.setClassId(89);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 5)
		{
			activeChar.setClassId(90);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 6)
		{
			activeChar.setClassId(91);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 8)
		{
			activeChar.setClassId(93);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 9)
		{
			activeChar.setClassId(92);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 12)
		{
			activeChar.setClassId(94);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 13)
		{
			activeChar.setClassId(95);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 14)
		{
			activeChar.setClassId(96);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 16)
		{
			activeChar.setClassId(97);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 17)
		{
			activeChar.setClassId(98);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 20)
		{
			activeChar.setClassId(99);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 21)
		{
			activeChar.setClassId(100);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 23)
		{
			activeChar.setClassId(101);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 24)
		{
			activeChar.setClassId(102);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 27)
		{
			activeChar.setClassId(103);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 28)
		{
			activeChar.setClassId(104);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 30)
		{
			activeChar.setClassId(105);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 33)
		{
			activeChar.setClassId(106);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 34)
		{
			activeChar.setClassId(107);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 36)
		{
			activeChar.setClassId(108);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 37)
		{
			activeChar.setClassId(109);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 40)
		{
			activeChar.setClassId(110);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 41)
		{
			activeChar.setClassId(111);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 43)
		{
			activeChar.setClassId(112);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 46)
		{
			activeChar.setClassId(113);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 48)
		{
			activeChar.setClassId(114);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 51)
		{
			activeChar.setClassId(115);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 52)
		{
			activeChar.setClassId(116);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 55)
		{
			activeChar.setClassId(117);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		else if (activeChar.getClassId().getId() == 57)
		{
			activeChar.setClassId(118);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
		}
		
		activeChar.sendPacket(new ExShowScreenMessage("You completed the startup selection successfully!", 6000));
		StringBuilder tb = new StringBuilder();
		NpcHtmlMessage html = new NpcHtmlMessage(1);
		
		tb.append("<html><head><title>User Control Panel</title></head><body>");
		tb.append("<center>");
		tb.append("<td valign=\"top\"><font color=\"FF6600\">Where you start your journey " + activeChar.getName() + ".</font>");
		tb.append("<button value=\"Castle Town of Giran\" action=\"bypass -h Startup_tele\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
		tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
		
		tb.append("</center>");
		tb.append("</body></html>");
		
		html.setHtml(tb.toString());
		activeChar.sendPacket(html);
	}
	
	public static void handleCommands(L2GameClient client, String _command)
	{
		final L2PcInstance activeChar = client.getActiveChar();
		if (activeChar == null)
			return;
		long _exp;
		if (_command.startsWith("fbhaste"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6581, 1, activeChar, null);
			ItemInstance item1 = activeChar.getInventory().addItem("", 6377, 1, activeChar, null);
			
			activeChar.getInventory().equipItemAndRecord(item);
			activeChar.getInventory().equipItemAndRecord(item1);
			
			checkClassIds(activeChar);
			
		}
		else if (_command.startsWith("tele"))
		{
			activeChar.teleToLocation(83420, 147928, -3399, 0);
		}
		else if (_command.startsWith("fbhealth"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6582, 1, activeChar, null);
			ItemInstance item1 = activeChar.getInventory().addItem("", 6377, 1, activeChar, null);
			
			activeChar.getInventory().equipItemAndRecord(item);
			activeChar.getInventory().equipItemAndRecord(item1);
			
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("fbfocus"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6583, 1, activeChar, null);
			ItemInstance item1 = activeChar.getInventory().addItem("", 6377, 1, activeChar, null);
			
			activeChar.getInventory().equipItemAndRecord(item);
			activeChar.getInventory().equipItemAndRecord(item1);
			
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("forgblade"))
		{
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your weapon " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Forgotten Blade (Haste)\" action=\"bypass -h Startup_fbhaste\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Forgotten Blade (Health)\" action=\"bypass -h Startup_fbhealth\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Forgotten Blade (Focus)\" action=\"bypass -h Startup_fbfocus\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("bhhpd"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6584, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("bhh"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6585, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("bhhr"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6586, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("bshammer"))
		{
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your weapon " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Basalt Battlehammer (HP Drain)\" action=\"bypass -h Startup_bhhpd\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Basalt Battlehammer (Health)\" action=\"bypass -h Startup_bhh\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Basalt Battlehammer (HP Regeneration)\" action=\"bypass -h Startup_bhhr\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("imste"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6587, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("imstmp"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6588, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("imstmh"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6589, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("imperialstaff"))
		{
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your weapon " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Imperial Staff (Empower)\" action=\"bypass -h Startup_imste\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Staff (MP Regeneration)\" action=\"bypass -h Startup_imstmp\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Staff (Magic Hold)\" action=\"bypass -h Startup_imstmh\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("ascd"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6590, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("ascdhp"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6591, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("ash"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6592, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("angelslayer"))
		{
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your weapon " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Angel Slayer (Critical Damage)\" action=\"bypass -h Startup_ascd\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Angel Slayer (HP Drain)\" action=\"bypass -h Startup_ascdhp\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Angel Slayer (Haste)\" action=\"bypass -h Startup_ash\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("shbcs"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6593, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("shbf"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6594, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("shbcs"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6595, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("shiningbow"))
		{
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your weapon " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Shining Bow (Cheap Shot)\" action=\"bypass -h Startup_shbcs\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Shining Bow (Focus)\" action=\"bypass -h Startup_shbf\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Shining Bow (Critical Slow)\" action=\"bypass -h Startup_shbcs\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("dbcs"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 7576, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("dbf"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 7577, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("dbcslow"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 7578, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("dracobow"))
		{
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your weapon " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Bow (Cheap Shot)\" action=\"bypass -h Startup_dbcs\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Draconic Bow (Focus)\" action=\"bypass -h Startup_dbf\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Draconic Bow (Critical Slow)\" action=\"bypass -h Startup_dbcslow\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("dhahr"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6596, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("dhah"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6597, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("dhahd"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6598, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("dragonhunter"))
		{
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your weapon " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Dragon Hunter Axe (HP Regeneration)\" action=\"bypass -h Startup_dhahr\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dragon Hunter Axe (Health)\" action=\"bypass -h Startup_dhah\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dragon Hunter Axe (HP Drain)\" action=\"bypass -h Startup_dhahd\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("ssh"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6599, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("ssg"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6600, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("saintshas"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6601, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("saintspear"))
		{
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your weapon " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Saint Spear (Health)\" action=\"bypass -h Startup_ssh\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Saint Spear (Guidance)\" action=\"bypass -h Startup_ssg\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Saint Spear (Haste)\" action=\"bypass -h Startup_saintshas\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("demdf"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6602, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("demdh"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6603, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("demdcs"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6604, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("demonsplinter"))
		{
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your weapon " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Demon Splinter (Focus)\" action=\"bypass -h Startup_demdf\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Demon Splinter (Health)\" action=\"bypass -h Startup_demdh\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Demon Splinter (Critical Stun)\" action=\"bypass -h Startup_demdcs\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("heavdha"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6605, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("heavdhe"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6606, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("heavdf"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6607, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("heavendiv"))
		{
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your weapon " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Heavens Divider (Haste)\" action=\"bypass -h Startup_heavdha\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Heavens Divider (Health)\" action=\"bypass -h Startup_heavdhe\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Heavens Divider (Focus)\" action=\"bypass -h Startup_heavdf\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("arcma"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6608, 1, activeChar, null);
			ItemInstance item1 = activeChar.getInventory().addItem("", 6377, 1, activeChar, null);
			
			activeChar.getInventory().equipItemAndRecord(item);
			activeChar.getInventory().equipItemAndRecord(item1);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("arcmmp"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6609, 1, activeChar, null);
			ItemInstance item1 = activeChar.getInventory().addItem("", 6377, 1, activeChar, null);
			
			activeChar.getInventory().equipItemAndRecord(item);
			activeChar.getInventory().equipItemAndRecord(item1);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("arcmma"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6610, 1, activeChar, null);
			ItemInstance item1 = activeChar.getInventory().addItem("", 6377, 1, activeChar, null);
			
			activeChar.getInventory().equipItemAndRecord(item);
			activeChar.getInventory().equipItemAndRecord(item1);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("arcanamace"))
		{
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your weapon " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Arcana Mace (Acumen)\" action=\"bypass -h Startup_arcma\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Arcana Mace (MP Regeneration)\" action=\"bypass -h Startup_arcmmp\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Arcana Mace (Mana Up)\" action=\"bypass -h Startup_arcmma\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("tallumduals"))
		{
			ItemInstance item = activeChar.getInventory().addItem("", 6580, 1, activeChar, null);
			activeChar.getInventory().equipItemAndRecord(item);
			checkClassIds(activeChar);
		}
		else if (_command.startsWith("dracoar"))
		{
			_exp = 4200000000L;
			
			ItemInstance item1 = activeChar.getInventory().addItem("", 6379, 1, activeChar, null);
			ItemInstance item2 = activeChar.getInventory().addItem("", 6380, 1, activeChar, null);
			ItemInstance item3 = activeChar.getInventory().addItem("", 6381, 1, activeChar, null);
			ItemInstance item4 = activeChar.getInventory().addItem("", 6382, 1, activeChar, null);
			
			ItemInstance item5 = activeChar.getInventory().addItem("", 858, 1, activeChar, null);
			ItemInstance item6 = activeChar.getInventory().addItem("", 858, 1, activeChar, null);
			ItemInstance item7 = activeChar.getInventory().addItem("", 889, 1, activeChar, null);
			ItemInstance item8 = activeChar.getInventory().addItem("", 889, 1, activeChar, null);
			ItemInstance item9 = activeChar.getInventory().addItem("", 920, 1, activeChar, null);
			
			activeChar.getInventory().equipItemAndRecord(item1);
			activeChar.getInventory().equipItemAndRecord(item2);
			activeChar.getInventory().equipItemAndRecord(item3);
			activeChar.getInventory().equipItemAndRecord(item4);
			activeChar.getInventory().equipItemAndRecord(item5);
			activeChar.getInventory().equipItemAndRecord(item6);
			activeChar.getInventory().equipItemAndRecord(item7);
			activeChar.getInventory().equipItemAndRecord(item8);
			activeChar.getInventory().equipItemAndRecord(item9);
			
			activeChar.removeExpAndSp(activeChar.getExp(), 0);
			activeChar.addExpAndSp(_exp, 0);
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your weapon " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Forgotten Blade\" action=\"bypass -h Startup_forgblade\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Basalt Battlehammer\" action=\"bypass -h Startup_bshammer\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Staff\" action=\"bypass -h Startup_imperialstaff\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Angel Slayer\" action=\"bypass -h Startup_angelslayer\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Shining Bow\" action=\"bypass -h Startup_shiningbow\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Draconic Bow\" action=\"bypass -h Startup_dracobow\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dragon Hunter Axe\" action=\"bypass -h Startup_dragonhunter\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Saint Spear\" action=\"bypass -h Startup_saintspear\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Demon Splinter\" action=\"bypass -h Startup_demonsplinter\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Heaven's Divider\" action=\"bypass -h Startup_heavendiv\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Arcana Mace\" action=\"bypass -h Startup_arcanamace\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Tallum Blade*Dark Legion's Edge\" action=\"bypass -h Startup_tallumduals\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("impar"))
		{
			_exp = 4200000000L;
			
			ItemInstance item1 = activeChar.getInventory().addItem("", 6373, 1, activeChar, null);
			ItemInstance item2 = activeChar.getInventory().addItem("", 6374, 1, activeChar, null);
			ItemInstance item3 = activeChar.getInventory().addItem("", 6375, 1, activeChar, null);
			ItemInstance item4 = activeChar.getInventory().addItem("", 6376, 1, activeChar, null);
			ItemInstance item5 = activeChar.getInventory().addItem("", 6378, 1, activeChar, null);
			
			ItemInstance item6 = activeChar.getInventory().addItem("", 858, 1, activeChar, null);
			ItemInstance item7 = activeChar.getInventory().addItem("", 858, 1, activeChar, null);
			ItemInstance item8 = activeChar.getInventory().addItem("", 889, 1, activeChar, null);
			ItemInstance item9 = activeChar.getInventory().addItem("", 889, 1, activeChar, null);
			ItemInstance item10 = activeChar.getInventory().addItem("", 920, 1, activeChar, null);
			
			activeChar.getInventory().equipItemAndRecord(item1);
			activeChar.getInventory().equipItemAndRecord(item2);
			activeChar.getInventory().equipItemAndRecord(item3);
			activeChar.getInventory().equipItemAndRecord(item4);
			activeChar.getInventory().equipItemAndRecord(item5);
			activeChar.getInventory().equipItemAndRecord(item6);
			activeChar.getInventory().equipItemAndRecord(item7);
			activeChar.getInventory().equipItemAndRecord(item8);
			activeChar.getInventory().equipItemAndRecord(item9);
			activeChar.getInventory().equipItemAndRecord(item10);
			
			activeChar.removeExpAndSp(activeChar.getExp(), 0);
			activeChar.addExpAndSp(_exp, 0);
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your weapon " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Forgotten Blade\" action=\"bypass -h Startup_forgblade\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Basalt Battlehammer\" action=\"bypass -h Startup_bshammer\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Staff\" action=\"bypass -h Startup_imperialstaff\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Angel Slayer\" action=\"bypass -h Startup_angelslayer\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Shining Bow\" action=\"bypass -h Startup_shiningbow\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Draconic Bow\" action=\"bypass -h Startup_dracobow\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dragon Hunter Axe\" action=\"bypass -h Startup_dragonhunter\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Saint Spear\" action=\"bypass -h Startup_saintspear\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Demon Splinter\" action=\"bypass -h Startup_demonsplinter\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Heaven's Divider\" action=\"bypass -h Startup_heavendiv\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Arcana Mace\" action=\"bypass -h Startup_arcanamace\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Tallum Blade*Dark Legion's Edge\" action=\"bypass -h Startup_tallumduals\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("dcar"))
		{
			_exp = 4200000000L;
			
			ItemInstance item1 = activeChar.getInventory().addItem("", 512, 1, activeChar, null);
			ItemInstance item2 = activeChar.getInventory().addItem("", 2407, 1, activeChar, null);
			ItemInstance item3 = activeChar.getInventory().addItem("", 5767, 1, activeChar, null);
			ItemInstance item4 = activeChar.getInventory().addItem("", 5779, 1, activeChar, null);
			
			ItemInstance item5 = activeChar.getInventory().addItem("", 858, 1, activeChar, null);
			ItemInstance item6 = activeChar.getInventory().addItem("", 858, 1, activeChar, null);
			ItemInstance item7 = activeChar.getInventory().addItem("", 889, 1, activeChar, null);
			ItemInstance item8 = activeChar.getInventory().addItem("", 889, 1, activeChar, null);
			ItemInstance item9 = activeChar.getInventory().addItem("", 920, 1, activeChar, null);
			
			activeChar.getInventory().equipItemAndRecord(item1);
			activeChar.getInventory().equipItemAndRecord(item2);
			activeChar.getInventory().equipItemAndRecord(item3);
			activeChar.getInventory().equipItemAndRecord(item4);
			activeChar.getInventory().equipItemAndRecord(item5);
			activeChar.getInventory().equipItemAndRecord(item6);
			activeChar.getInventory().equipItemAndRecord(item7);
			activeChar.getInventory().equipItemAndRecord(item8);
			activeChar.getInventory().equipItemAndRecord(item9);
			
			activeChar.removeExpAndSp(activeChar.getExp(), 0);
			activeChar.addExpAndSp(_exp, 0);
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your weapon " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Forgotten Blade\" action=\"bypass -h Startup_forgblade\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Basalt Battlehammer\" action=\"bypass -h Startup_bshammer\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Staff\" action=\"bypass -h Startup_imperialstaff\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Angel Slayer\" action=\"bypass -h Startup_angelslayer\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Shining Bow\" action=\"bypass -h Startup_shiningbow\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Draconic Bow\" action=\"bypass -h Startup_dracobow\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dragon Hunter Axe\" action=\"bypass -h Startup_dragonhunter\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Saint Spear\" action=\"bypass -h Startup_saintspear\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Demon Splinter\" action=\"bypass -h Startup_demonsplinter\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Heaven's Divider\" action=\"bypass -h Startup_heavendiv\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Arcana Mace\" action=\"bypass -h Startup_arcanamace\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Tallum Blade*Dark Legion's Edge\" action=\"bypass -h Startup_tallumduals\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("gladi"))
		{
			activeChar.setClassId(2);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("warlord"))
		{
			activeChar.setClassId(3);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("pala"))
		{
			activeChar.setClassId(5);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("daven"))
		{
			activeChar.setClassId(6);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("tres"))
		{
			activeChar.setClassId(8);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("hawkeye"))
		{
			activeChar.setClassId(9);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("sorce"))
		{
			activeChar.setClassId(12);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		/*
		 * else if (_command.startsWith("webbrowser")) { activeChar.openURL("http://maxcheaters.com"); }
		 */
		else if (_command.startsWith("necro"))
		{
			activeChar.setClassId(13);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("warlock"))
		{
			activeChar.setClassId(14);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("bishop"))
		{
			activeChar.setClassId(16);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("prophet"))
		{
			activeChar.setClassId(17);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("tknight"))
		{
			activeChar.setClassId(20);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("sws"))
		{
			activeChar.setClassId(21);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("pwalker"))
		{
			activeChar.setClassId(23);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("srang"))
		{
			activeChar.setClassId(24);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("sps"))
		{
			activeChar.setClassId(27);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("elesum"))
		{
			activeChar.setClassId(28);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("elder"))
		{
			activeChar.setClassId(30);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("shkni"))
		{
			activeChar.setClassId(33);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("bld"))
		{
			activeChar.setClassId(34);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("awalk"))
		{
			activeChar.setClassId(36);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("phra"))
		{
			activeChar.setClassId(37);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("shl"))
		{
			activeChar.setClassId(40);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("phsum"))
		{
			activeChar.setClassId(41);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("selder"))
		{
			activeChar.setClassId(43);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("dest"))
		{
			activeChar.setClassId(46);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("tyrant"))
		{
			activeChar.setClassId(48);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("overlord"))
		{
			activeChar.setClassId(51);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("warcryer"))
		{
			activeChar.setClassId(52);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("bountyh"))
		{
			activeChar.setClassId(55);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		else if (_command.startsWith("warsmith"))
		{
			activeChar.setClassId(57);
			activeChar.broadcastUserInfo();
			activeChar.setBaseClass(activeChar.getActiveClass());
			activeChar.store();
			
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>User Control Panel</title></head><body>");
			tb.append("<center>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your set " + activeChar.getName() + ".</font>");
			tb.append("<button value=\"Draconic Leather Armor\" action=\"bypass -h Startup_dracoar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Imperial Crusader Armor\" action=\"bypass -h Startup_impar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<button value=\"Dark Crystal Robe\" action=\"bypass -h Startup_dcar\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
			tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center>");
			
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
	}
}