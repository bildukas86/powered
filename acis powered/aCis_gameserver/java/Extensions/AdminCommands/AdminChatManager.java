package Extensions.AdminCommands;

import Extensions.Chat.ChatAioVipVoice;

import java.util.StringTokenizer;

import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.handler.chathandlers.ChatHeroVoice;
import net.sf.l2j.gameserver.handler.chathandlers.ChatShout;
import net.sf.l2j.gameserver.handler.chathandlers.ChatTrade;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.clientpackets.Say2;
import net.sf.l2j.gameserver.network.serverpackets.ExShowScreenMessage;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class AdminChatManager implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_quiet"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_quiet"))
		{
			StringTokenizer st = new StringTokenizer(command);
			st.nextToken();
			
			try
			{
				String type = st.nextToken();
				if (type.startsWith("all"))
				{
					if (!Say2.isChatDisabled())
					{
						Say2.setIsChatDisabled(true);
						activeChar.sendMessage("All chats have been disabled!");
						activeChar.sendPacket(new ExShowScreenMessage("All chats have been disabled!", 6000));
					}
					else
					{
						Say2.setIsChatDisabled(false);
						activeChar.sendMessage("All Chats have been enabled!");
						activeChar.sendPacket(new ExShowScreenMessage("All Chats have been enabled!", 6000));
					}
				}
				else if (type.startsWith("hero"))
				{
					if (!ChatHeroVoice.isChatDisabled())
					{
						ChatHeroVoice.setIsChatDisabled(true);
						activeChar.sendMessage("Hero Voice has been disabled!");
						activeChar.sendPacket(new ExShowScreenMessage("Hero Voice has been disabled!", 6000));
					}
					else
					{
						ChatHeroVoice.setIsChatDisabled(false);
						activeChar.sendMessage("Hero Voice has been enabled!");
						activeChar.sendPacket(new ExShowScreenMessage("Hero Voice has been enabled!", 6000));
					}
				}
				else if (type.startsWith("trade"))
				{
					if (!ChatTrade.isChatDisabled())
					{
						ChatTrade.setIsChatDisabled(true);
						activeChar.sendMessage("Trade Chat has been disabled!");
						activeChar.sendPacket(new ExShowScreenMessage("Trade Chat has been disabled!", 6000));
					}
					else
					{
						ChatTrade.setIsChatDisabled(false);
						activeChar.sendMessage("Trade Chat has been enabled!");
						activeChar.sendPacket(new ExShowScreenMessage("Trade Chat has been enabled!", 6000));
					}
				}
				else if (type.startsWith("global"))
				{
					if (!ChatShout.isChatDisabled())
					{
						ChatShout.setIsChatDisabled(true);
						activeChar.sendMessage("Global Chat has been disabled!");
						activeChar.sendPacket(new ExShowScreenMessage("Global Chat has been disabled!", 6000));
					}
					else
					{
						ChatShout.setIsChatDisabled(false);
						activeChar.sendMessage("Global Chat has been enabled!");
						activeChar.sendPacket(new ExShowScreenMessage("Global Chat has been enabled!", 6000));
					}
				}
				else if (type.startsWith("AioVip"))
				{
					if (!ChatAioVipVoice.isChatDisabled())
					{
						ChatAioVipVoice.setIsChatDisabled(true);
						activeChar.sendMessage("Premium Voice has been disabled!");
					}
					else
					{
						ChatAioVipVoice.setIsChatDisabled(false);
						activeChar.sendMessage("Premium Voice has been enabled!");
					}
				}
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Usage : //quiet <all|hero|trade|global>");
			}
			mainHtml(activeChar);
		}
		return true;
	}
	
	public static void mainHtml(L2PcInstance activeChar)
	{
		NpcHtmlMessage nhm = new NpcHtmlMessage(5);
		StringBuilder tb = new StringBuilder("<html><head><title>Chat Menu</title></head><body>");
		tb.append("<table width=260>");
		tb.append("<tr>");
		tb.append("<td><button value=\"Main\" action=\"bypass -h admin_admin\" width=65 height=19 back=\"L2UI_ch3.smallbutton2_over\" fore=\"L2UI_ch3.smallbutton2\"></td>");
		tb.append("<td><button value=\"Game\" action=\"bypass -h admin_admin2\" width=65 height=19 back=\"L2UI_ch3.smallbutton2_over\" fore=\"L2UI_ch3.smallbutton2\"></td>");
		tb.append("<td><button value=\"Effects\" action=\"bypass -h admin_admin3\" width=65 height=19 back=\"L2UI_ch3.smallbutton2_over\" fore=\"L2UI_ch3.smallbutton2\"></td>");
		tb.append("<td><button value=\"Server\" action=\"bypass -h admin_admin4\" width=65 height=19 back=\"L2UI_ch3.smallbutton2_over\" fore=\"L2UI_ch3.smallbutton2\"></td>");
		tb.append("</tr>");
		tb.append("</table>");
		tb.append("<center><img src=\"l2ui.SquareWhite\" width=275 height=1><br>");
		
		tb.append("<table border=\"1\" width=\"250\" height=\"12\" bgcolor=\"000000\">");
		// ALL Chat
		tb.append("<tr>");
		tb.append("<td align=\"center\" width=\"52\">ALL Chat</td>");
		if (Say2.isChatDisabled())
			tb.append("<td width=\"16\" align=center><button action=\"bypass -h admin_quiet all\" width=24 height=12 back=\"L2UI_CH3.br_bar1_mp\" fore=\"L2UI_CH3.br_bar1_mp\"></td>");
		if (!Say2.isChatDisabled())
			tb.append("<td width=\"16\" align=center><button action=\"bypass -h admin_quiet all\" width=24 height=12 back=\"L2UI_CH3.br_bar1_hp\" fore=\"L2UI_CH3.br_bar1_hp\"></td>");
		tb.append("</tr>");
		// Hero Chat
		tb.append("<tr>");
		tb.append("<td align=\"center\" width=\"52\">Hero Chat</td>");
		if (ChatHeroVoice.isChatDisabled())
			tb.append("<td width=\"16\" align=center><button action=\"bypass -h admin_quiet hero\" width=24 height=12 back=\"L2UI_CH3.br_bar1_mp\" fore=\"L2UI_CH3.br_bar1_mp\"></td>");
		if (!ChatHeroVoice.isChatDisabled())
			tb.append("<td width=\"16\" align=center><button action=\"bypass -h admin_quiet hero\" width=24 height=12 back=\"L2UI_CH3.br_bar1_hp\" fore=\"L2UI_CH3.br_bar1_hp\"></td>");
		tb.append("</tr>");
		// Trade Chat
		tb.append("<tr>");
		tb.append("<td align=\"center\" width=\"52\">Trade Chat</td>");
		if (ChatTrade.isChatDisabled())
			tb.append("<td width=\"16\" align=center><button action=\"bypass -h admin_quiet trade\" width=24 height=12 back=\"L2UI_CH3.br_bar1_mp\" fore=\"L2UI_CH3.br_bar1_mp\"></td>");
		if (!ChatTrade.isChatDisabled())
			tb.append("<td width=\"16\" align=center><button action=\"bypass -h admin_quiet trade\" width=24 height=12 back=\"L2UI_CH3.br_bar1_hp\" fore=\"L2UI_CH3.br_bar1_hp\"></td>");
		tb.append("</tr>");
		// Global Chat
		tb.append("<tr>");
		tb.append("<td align=\"center\" width=\"52\">Global Chat</td>");
		if (ChatShout.isChatDisabled())
			tb.append("<td width=\"16\" align=center><button action=\"bypass -h admin_quiet global\" width=24 height=12 back=\"L2UI_CH3.br_bar1_mp\" fore=\"L2UI_CH3.br_bar1_mp\"></td>");
		if (!ChatShout.isChatDisabled())
			tb.append("<td width=\"16\" align=center><button action=\"bypass -h admin_quiet global\" width=24 height=12 back=\"L2UI_CH3.br_bar1_hp\" fore=\"L2UI_CH3.br_bar1_hp\"></td>");
		tb.append("</tr>");
		tb.append("</table>");
		
		tb.append("<br>Enable or disable the world chat.");
		
		tb.append("</center>");
		tb.append("</body></html>");
		
		nhm.setHtml(tb.toString());
		activeChar.sendPacket(nhm);
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}