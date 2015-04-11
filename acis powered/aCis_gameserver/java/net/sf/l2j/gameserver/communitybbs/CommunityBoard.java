package net.sf.l2j.gameserver.communitybbs;

import Extensions.CCB.Manager.AnnouncementsBBSManager;
import Extensions.CCB.Manager.BuffBBSManager;
import Extensions.CCB.Manager.ClanBBSManager;
import Extensions.CCB.Manager.ClassBBSManager;
import Extensions.CCB.Manager.DonationBBSManager;
import Extensions.CCB.Manager.OlyStatsBBSManager;
import Extensions.CCB.Manager.PartyMatchingBBSManager;
import Extensions.CCB.Manager.PasswordBBSManager;
import Extensions.CCB.Manager.ProblemReportBBSManager;
import Extensions.CCB.Manager.RepairBBSManager;
import Extensions.CCB.Manager.ShopBBSManager;
import Extensions.CCB.Manager.TeleBBSManager;
import classbalancer.ClassBalanceBBSManager;

import java.util.StringTokenizer;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.communitybbs.Manager.BaseBBSManager;
import net.sf.l2j.gameserver.communitybbs.Manager.FriendsBBSManager;
import net.sf.l2j.gameserver.communitybbs.Manager.MailBBSManager;
import net.sf.l2j.gameserver.communitybbs.Manager.PostBBSManager;
import net.sf.l2j.gameserver.communitybbs.Manager.RegionBBSManager;
import net.sf.l2j.gameserver.communitybbs.Manager.TopBBSManager;
import net.sf.l2j.gameserver.communitybbs.Manager.TopicBBSManager;
import net.sf.l2j.gameserver.datatables.MultisellData;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.L2GameClient;
import net.sf.l2j.gameserver.network.SystemMessageId;

import skillsbalancer.SkillsBalanceBBSManager;

public class CommunityBoard
{
	protected CommunityBoard()
	{
	}
	
	public static CommunityBoard getInstance()
	{
		return SingletonHolder._instance;
	}
	
	public void handleCommands(L2GameClient client, String command)
	{
		final L2PcInstance activeChar = client.getActiveChar();
		if (activeChar == null)
			return;

		if (command.startsWith("_bbsskillsbalancer") && activeChar.isGM())
		{
			SkillsBalanceBBSManager.getInstance().parseCmd(command, activeChar);
			return;
		}

		if (command.startsWith("_bbsbalancer") && activeChar.isGM())
		{
			ClassBalanceBBSManager.getInstance().parseCmd(command, activeChar);
			return;
		}
		
		if (!Config.ENABLE_COMMUNITY_BOARD)
		{
			activeChar.sendPacket(SystemMessageId.CB_OFFLINE);
			return;
		}
		
		if (command.startsWith("_bbshome"))
			TopBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbsloc"))
			RegionBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbsannouncements"))
			AnnouncementsBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbsProblemReport"))
			ProblemReportBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbsdonation"))
			DonationBBSManager.getInstance().parseCmd(command, activeChar);
		else if ((command.startsWith("_bbsOlyStats")) || (command.startsWith("_bbsClassList")))
			OlyStatsBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbsclan"))
			ClanBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbsmemo"))
			TopicBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbsmail") || command.equals("_maillist_0_1_0_"))
			MailBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_friend") || command.startsWith("_block"))
			FriendsBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbstopics"))
			TopicBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbsposts"))
			PostBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbstele"))
			TeleBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbsbuff"))
			BuffBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbsrepair"))
			RepairBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbstop"))
			TopBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbshop"))
		{
			ShopBBSManager.getInstance().parsecmd(command, activeChar);
		}
		else if ((command.equals("_bbsPassPanel")) || (command.startsWith("_bbsChangePass")))
			PasswordBBSManager.getInstance().parseCmd(command, activeChar);
		else if (command.startsWith("_bbsmultisell;"))
		{
			StringTokenizer st = new StringTokenizer(command, ";");
			st.nextToken();
			ShopBBSManager.getInstance().parsecmd("_bbsshop;" + st.nextToken(), activeChar);
			MultisellData.getInstance().separateAndSend(Integer.parseInt(st.nextToken()), activeChar, false, 0);
		}
		else if (command.startsWith("_bbsclass"))
			ClassBBSManager.getInstance().parsecmd(command, activeChar);
		else if (command.startsWith("_bbspartymatching"))
		{
			String[] value = command.split(" ");
			String type = value[1];
			if (type.equals("on"))
				if (activeChar.isInParty())
					activeChar.sendMessage("You cant use this while you're in party!");
				else if (activeChar.isInPartyMatching())
				{
					activeChar.sendMessage("You're alredy in the Party Matching list!");
					PartyMatchingBBSManager.refresh(activeChar);
				}
				else
				{
					activeChar.setPartyMatchingStatus(1);
					activeChar.setInPartyMatching(true);
					activeChar.sendMessage("You are now on the Party Matching list.");
				}
			else if (type.equals("off"))
				if (activeChar.isInParty())
					activeChar.sendMessage("You cant use this while you're in party!");
				else if (!activeChar.isInPartyMatching())
				{
					PartyMatchingBBSManager.refresh(activeChar);
					activeChar.sendMessage("You've alredy left from the Party Matching list!");
				}
				else
				{
					activeChar.setPartyMatchingStatus(0);
					activeChar.setInPartyMatching(false);
					activeChar.sendMessage("You've left the party matching list.");
				}
		}
		else if (command.startsWith("pmatch"))
			PartyMatchingBBSManager.getInstance().parseCmd(command, activeChar);
		else
			BaseBBSManager.separateAndSend("<html><body><br><br><center>The command: " + command + " isn't implemented.</center></body></html>", activeChar);
	}
	
	public void handleWriteCommands(L2GameClient client, String url, String arg1, String arg2, String arg3, String arg4, String arg5)
	{
		final L2PcInstance activeChar = client.getActiveChar();
		if (activeChar == null)
			return;
		
		if (!Config.ENABLE_COMMUNITY_BOARD)
		{
			activeChar.sendPacket(SystemMessageId.CB_OFFLINE);
			return;
		}
		
		if (url.equals("Topic"))
			TopicBBSManager.getInstance().parseWrite(arg1, arg2, arg3, arg4, arg5, activeChar);
		else if (url.equals("Post"))
			PostBBSManager.getInstance().parseWrite(arg1, arg2, arg3, arg4, arg5, activeChar);
		else if (url.equals("_bbsloc"))
			RegionBBSManager.getInstance().parseWrite(arg1, arg2, arg3, arg4, arg5, activeChar);
		else if (url.equals("_bbsclan"))
			ClanBBSManager.getInstance().parseWrite(arg1, arg2, arg3, arg4, arg5, activeChar);
		else if (url.equals("Mail"))
			MailBBSManager.getInstance().parseWrite(arg1, arg2, arg3, arg4, arg5, activeChar);
		else if (url.equals("_friend"))
			FriendsBBSManager.getInstance().parseWrite(arg1, arg2, arg3, arg4, arg5, activeChar);
		else
			BaseBBSManager.separateAndSend("<html><body><br><br><center>The command: " + url + " isn't implemented.</center></body></html>", activeChar);
	}
	
	private static class SingletonHolder
	{
		protected static final CommunityBoard _instance = new CommunityBoard();
	}
}