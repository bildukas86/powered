package Extensions.VoicedCommands;

import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class Pin implements IVoicedCommandHandler
{
	private static String[] _voicedCommands =
	{
		"pincode"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		if (command.equalsIgnoreCase("pincode"))
		{
			if (activeChar.getPincheck())
			{
				StringBuilder tb = new StringBuilder();
				NpcHtmlMessage html = new NpcHtmlMessage(1);
				
				tb.append("<html><head><title>Character Pin Panel</title></head>");
				tb.append("<body>");
				tb.append("<center>");
				tb.append("<table width=\"250\" cellpadding=\"5\" bgcolor=\"000000\">");
				tb.append("<tr>");
				tb.append("<td width=\"45\" valign=\"top\" align=\"center\"><img src=\"L2ui_ch3.menubutton4\" width=\"38\" height=\"38\"></td>");
				tb.append("<td valign=\"top\">");
				tb.append("<font color=\"FF6600\">Pin Panel</font><br1><font color=\"00FF00\">" + activeChar.getName() + "</font>, use this interface to enable pin secirity.</td>");
				tb.append("</tr></table>");
				tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32 align=center><br>");
				tb.append("</center>");
				tb.append("<table width=\"350\" cellpadding=\"5\" bgcolor=\"000000\">");
				tb.append("<tr>");
				tb.append("<td width=\"45\" valign=\"top\" align=\"center\"><img src=\"Icon.etc_old_key_i02\" width=\"32\" height=\"32\"></td>");
				tb.append("<td valign=\"top\">Please enter your PIN:<edit var=\"pin\" width=80 height=15>");
				tb.append("<br1>info or something (can delete)</td>");
				tb.append("</tr>");
				tb.append("</table>");
				tb.append("<br>");
				tb.append("<center>");
				tb.append("<button value=\"Submit\" action=\"bypass -h submitpin $pin\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\">");
				tb.append("<img src=\"l2ui_ch3.herotower_deco\" width=256 height=32>");
				tb.append("</center>");
				tb.append("</body></html>");
				
				html.setHtml(tb.toString());
				activeChar.sendPacket(html);
			}
			else
				activeChar.sendMessage("You have already submitted a Pin code");
			
		}
		return true;
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return _voicedCommands;
	}
}