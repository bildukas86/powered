package Extensions;

import Extensions.Menu.Security.AccountManager;
import Extensions.Menu.Security.OnEnterMail;
import Extensions.Menu.Security.UserFirst;
import Extensions.PinSystem.PinSystem;
import Extensions.Protection.ProtectionIP;
import Extensions.StartUpSystem.StartupSystem;
import Extensions.TitleNameColorsPKPvP.ColorStatus;
import Extensions.Vip.VIPEngine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;

import net.sf.l2j.Config;
import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.Announcements;
import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.communitybbs.Manager.MailBBSManager;
import net.sf.l2j.gameserver.instancemanager.CastleManager;
import net.sf.l2j.gameserver.model.L2Clan;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.entity.Castle;
import net.sf.l2j.gameserver.model.olympiad.Olympiad;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.clientpackets.Say2;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;
import net.sf.l2j.gameserver.network.serverpackets.ExMailArrived;
import net.sf.l2j.gameserver.network.serverpackets.ExShowScreenMessage;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.network.serverpackets.PlaySound;
import net.sf.l2j.gameserver.network.serverpackets.SocialAction;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;

public class OnEnter
{
	public static void addCustoms(L2PcInstance activeChar)
	{
		final L2Clan clan = activeChar.getClan();
		
		// Mail pop up
		if (!AccountManager.hasSubEmail(activeChar))
			ThreadPoolManager.getInstance().scheduleGeneral(new OnEnterMail(activeChar), Config.ON_ENTER_MAIL * 1000);
		
		// Panel pop up
		if (activeChar.getLevel() <= Config.USER_FIRST_LVL)
			ThreadPoolManager.getInstance().scheduleGeneral(new UserFirst(activeChar), Config.USER_FIRST * 1000);
		
		// Unlimited Arrows & soulshots/spiritshots/blessed spiritshots/pet shots/fish shots
		// Enable if character is VIP and menu panel is true
		if (VIPEngine.getInstance().getAllVips().contains(activeChar.getObjectId()) && Config.ENABLE_PERSONAL_MENU_SYSTEM)
			activeChar.setUnlimitedArrowsSS(true);
		// Startup System
		if (Config.STARTUP_SYSTEM_ENABLED)
			StartupSystem.startIt(activeChar);
		// Last IP
		if (Config.LASTIP_SYSTEM_ENABLED)
			ProtectionIP.onEnterWorld(activeChar);
		// Welcome message
		if (Config.WELCOME_MESSAGE_ENABLED)
			activeChar.sendPacket(new ExShowScreenMessage(Config.WELCOME_MESSAGE_TEXT, Config.WELCOME_MESSAGE_TIME));
		// Unread mails make a popup appears.
		if (Config.ENABLE_COMMUNITY_BOARD && MailBBSManager.getInstance().checkUnreadMail(activeChar) > 0)
		{
			activeChar.sendPacket(SystemMessageId.NEW_MAIL);
			activeChar.sendPacket(new PlaySound("systemmsg_e.1233"));
			activeChar.sendPacket(ExMailArrived.STATIC_PACKET);
		}
		// Clan notice, if active.
		if (Config.ENABLE_COMMUNITY_BOARD && clan != null && clan.isNoticeEnabled())
		{
			NpcHtmlMessage notice = new NpcHtmlMessage(0);
			notice.setFile("data/html/clan_notice.htm");
			notice.replace("%clan_name%", clan.getName());
			notice.replace("%notice_text%", clan.getNotice().replaceAll("\r\n", "<br>").replaceAll("action", "").replaceAll("bypass", ""));
			activeChar.sendPacket(notice);
		}
		// Check Skills On Enter
		if (Config.CHECK_SKILLS_ON_ENTER && !Config.AUTO_LEARN_SKILLS || !activeChar.isAio() || !activeChar.isGM())
		{
			activeChar.checkAllowedSkills();
		}
		// Server News
		else if (Config.SERVER_NEWS)
		{
			NpcHtmlMessage html = new NpcHtmlMessage(0);
			html.setFile("data/html/servnews.htm");
			activeChar.sendPacket(html);
		}
		// Fireworks & Social Action (victory) When Login
		if (Config.SOCIAL_ACTION_ON_LOGIN)
		{
			activeChar.broadcastPacket(new SocialAction(activeChar, 3));
			MagicSkillUse MSU = new MagicSkillUse(activeChar, activeChar, 2025, 1, 1, 0);
			activeChar.sendPacket(MSU);
			activeChar.broadcastPacket(MSU);
		}
		// Server Name on Enter
		if (Config.ALT_SERVER_NAME_ENABLED)
			activeChar.sendPacket(new CreatureSay(2, Say2.HERO_VOICE, "Welcome to", Config.ALT_SERVER_NAME));
		// Announce Newbie login
		if (Config.ANNOUNCE_NEWBIE_LOGIN && activeChar.isNewbie())
			Announcements.announceToAll("Newbie: " + activeChar.getName() + " has been logged in.");
		// Announce Hero Login
		if (Config.ANNOUNCE_HERO_LOGIN && activeChar.isHero())
			Announcements.announceToAll("Hero: " + activeChar.getName() + "  Class: " + activeChar.getClassId() + " is now online!");
		// Announce Noble Login
		if (Config.ANNOUNCE_NOBLE_LOGIN && activeChar.isNoble())
			Announcements.announceToAll("Noble: " + activeChar.getName() + " has been logged in.");
		// Announce AIO Login
		if (Config.ANNOUNCE_AIO_LOGIN && activeChar.isAio())
			Announcements.announceToAll("AIO: " + activeChar.getName() + " has been logged in.");
		// Announce ADMIN Login
		if (Config.ANNOUNCE_ADMIN_LOGIN && activeChar.isGM())
			Announcements.announceToAll("Admin: " + activeChar.getName() + " has been logged in.");
		// Announce Clan Leader Login
		if (Config.ANNOUNCE_CLAN_LOGIN && activeChar.isClanLeader())
			Announcements.announceToAll("The Clan Leader: " + activeChar.getName() + " has been logged in.");
		// Announce God Login
		if (Config.ANNOUNCE_GOD_LOGIN && activeChar.isGod())
			Announcements.announceToAll("The God: " + activeChar.getName() + " has been logged in.");
		// Announce Castle Lords on Login
		if (Config.ANNOUNCE_LORDS_LOGIN)
			if (clan != null)
				if (clan.hasCastle())
				{
					Castle castle = CastleManager.getInstance().getCastleById(clan.getCastleId());
					Announcements.announceToAll("Lord " + activeChar.getName() + " Ruler of " + castle.getName() + " Castle has been logged in.");
				}
		// Online players on Hero voice for player
		if (Config.ONLINE_PLAYERS_ON_LOGIN)
		{
			if (Config.ADDFAKEPLAYERSMODE)
			{
				int playerSizeOnline = L2World.getInstance().getAllPlayers().size() + Config.ADDFAKEPLAYERSNUMBER;
				activeChar.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.S1_S2).addString("There are " + playerSizeOnline + " players online."));
			}
			
			else
			{
				activeChar.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.S1_S2).addString("There are " + L2World.getInstance().getAllPlayers().size() + " players online."));
			}
		}
		// Olympiad End on Hero Message for player
		if (Config.OLYMPIAD_END_ON_LOGIN)
			Olympiad.getInstance().olympiadEnd(activeChar);
		// Server Time on Login
		if (Config.SERVER_TIME_ON_LOGIN)
			activeChar.sendMessage("Server Time is " + (new Date(System.currentTimeMillis())));
		
		// Pin Check
		if (!activeChar.getPincheck())
			PinSystem.PinCheck(activeChar);
		
		// Mail System
		if (Config.ENABLE_MAIL_SYSTEM)
		{
			int results = 0;
			try (Connection con = L2DatabaseFactory.getInstance().getConnection())
			{
				PreparedStatement statement = con.prepareStatement("SELECT * FROM mails WHERE `to`=?");
				statement.setString(1, activeChar.getName());
				ResultSet result = statement.executeQuery();
				while (result.next())
					results++;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			activeChar.sendMessage("You have " + results + " messages.");
		}
		
		// Color System
		// IMPORTANCE: [AIO > VIP > PVP/PK] COLORS
		if (Config.ENABLE_AIO_SYSTEM && activeChar.isAio())
		{
			onEnterAio(activeChar);
			if (Config.ALLOW_AIO_NCOLOR && activeChar.isAio())
				activeChar.getAppearance().setNameColor(Config.AIO_NCOLOR);
			if (Config.ALLOW_AIO_TCOLOR && activeChar.isAio())
				activeChar.getAppearance().setTitleColor(Config.AIO_TCOLOR);
		}
		else if (Config.VIP_NAME_COLOR_CONFIG && VIPEngine.getInstance().getAllVips().contains(activeChar.getObjectId()))
		{ // VIP: colors is more important than pvp/pk colors so we add them first.
			Integer colorName = Integer.decode("0x" + Config.VIPS_NAME_COLOR);
			Integer colorTitle = Integer.decode("0x" + Config.VIPS_TITLE_COLOR);
			activeChar.getAppearance().setNameColor(colorName);
			activeChar.getAppearance().setTitleColor(colorTitle);
		}
		else
		{ // else if character is not vip
			ColorStatus.updatePvPColor(activeChar);
			ColorStatus.updatePkColor(activeChar);
		}
	}
	
	private static void onEnterAio(L2PcInstance activeChar)
	{
		long now = Calendar.getInstance().getTimeInMillis();
		long endDay = activeChar.getAioEndTime();
		if (now > endDay)
		{
			activeChar.setAio(false);
			activeChar.setAioEndTime(0);
			activeChar.lostAioSkills();
			activeChar.removeExpAndSp(6299994999L, 366666666);
			if (Config.ALLOW_AIO_ITEM)
			{
				activeChar.getInventory().destroyItemByItemId("", Config.AIO_ITEMID, 1, activeChar, null);
				activeChar.getWarehouse().destroyItemByItemId("", Config.AIO_ITEMID, 1, activeChar, null);
			}
			activeChar.sendPacket(new CreatureSay(0, Say2.HERO_VOICE, "System", "Your AIO period ends."));
		}
		else
		{
			Date dt = new Date(endDay);
			if (activeChar.isAio())
				activeChar.sendMessage("Your AIO period ends at: " + dt);
		}
	}
}