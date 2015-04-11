package Extensions.AdminCommands;

import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class AdminNpcChat implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_npcchat",
		"admin_npcchat_menu"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_npcchat"))
			handleNPChat(command, activeChar);
		if (command.startsWith("admin_npcchat_menu"))
			showNpcMenu(activeChar);
		
		return true;
	}
	
	private static void handleNPChat(String command, L2PcInstance activeChar)
	{
		try
		{
			if (activeChar.getTarget() instanceof L2Npc)
			{
				final L2Npc npc = (L2Npc) activeChar.getTarget();
				
				int offset = 0;
				if (command.startsWith("admin_npcchat_menu"))
					offset = 19;
				else
					offset = 14;
				final String text = command.substring(offset);
				final CreatureSay cs = new CreatureSay(npc.getObjectId(), 0, npc.getTemplate().getName(), text);
				npc.broadcastPacket(cs);
			}
			else
			{
				activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
				return;
			}
		}
		catch (Exception e)
		{
		}
	}
	
	public void showNpcMenu(final L2PcInstance activeChar)
	{
		final NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		final StringBuilder replyMSG = new StringBuilder("<html><title>Game menu</title><body><center>");
		replyMSG.append("<table width=260>");
		replyMSG.append("<tr>");
		replyMSG.append("<td><button value=\"Main\" action=\"bypass -h admin_admin\" width=65 height=19 back=\"L2UI_ch3.smallbutton2_over\" fore=\"L2UI_ch3.smallbutton2\"></td>");
		replyMSG.append("<td><button value=\"Game\" action=\"bypass -h admin_admin2\" width=65 height=19 back=\"L2UI_ch3.smallbutton2_over\" fore=\"L2UI_ch3.smallbutton2\"></td>");
		replyMSG.append("<td><button value=\"Effects\" action=\"bypass -h admin_admin3\" width=65 height=19 back=\"L2UI_ch3.smallbutton2_over\" fore=\"L2UI_ch3.smallbutton2\"></td>");
		replyMSG.append("<td><button value=\"Server\" action=\"bypass -h admin_admin4\" width=65 height=19 back=\"L2UI_ch3.smallbutton2_over\" fore=\"L2UI_ch3.smallbutton2\"></td>");
		replyMSG.append("</tr>");
		replyMSG.append("</table>");
		replyMSG.append("<img src=\"l2ui.SquareWhite\" width=275 height=1><br>");
		replyMSG.append("Instructions:<br>Target the NPC and write whatever you want to make the NPC say.<br>");
		replyMSG.append("<center>Text:<multiedit var=\"text\" width=250 height=50></center>");
		replyMSG.append("<table width=\"160\">");
		replyMSG.append("<tr><td><center><button value=\"Say\" action=\"bypass -h admin_npcchat_menu $text\" width=80 height=21 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></td></tr>");
		replyMSG.append("</table></body></html>");
		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}