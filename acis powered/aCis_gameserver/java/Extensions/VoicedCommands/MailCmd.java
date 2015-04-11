package Extensions.VoicedCommands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class MailCmd implements IVoicedCommandHandler
{
	public static final String[] VOICED_COMMANDS =
	{
		"mailread",
		"mailsend"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		if (command.equalsIgnoreCase("mailread"))
			mailread(activeChar);
		if (command.equalsIgnoreCase("mailsend"))
			mailsend(activeChar);
		
		return false;
	}
	
	public void mailread(L2PcInstance activeChar)
	{
		NpcHtmlMessage msg = new NpcHtmlMessage(20);
		msg.setHtml(showMailReadWindow(activeChar));
		msg.replace("%objectId%", String.valueOf(20));
		activeChar.sendPacket(msg);
	}
	
	public String showMailReadWindow(L2PcInstance activeChar)
	{
		StringBuilder tb = new StringBuilder();
		tb.append("<html><head><title>Inbox</title></head><body>");
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT * FROM mails WHERE `to`=? ORDER BY id DESC");
			statement.setString(1, activeChar.getName());
			ResultSet result = statement.executeQuery();
			int messageId = 0;
			while (result.next())
			{
				tb.append("<font color=\"D6A718\">From:</font> <br>" + result.getString(2) + "<br>");
				tb.append("<font color=\"D6A718\">Title:</font> <br>" + result.getString(4) + "<br>");
				tb.append("<font color=\"D6A718\">Message:</font> <br>" + result.getString(5) + "<br>");
				messageId = result.getInt(1);
				tb.append("<button value=\"Delete\" action=\"bypass -h delMsg " + messageId + "\" width=100 height=20><br>*******************************<br>");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		tb.append("</body></html>");
		return tb.toString();
	}
	
	public void mailsend(L2PcInstance activeChar)
	{
		NpcHtmlMessage msg = new NpcHtmlMessage(20);
		msg.setHtml(showMailSendWindow(activeChar));
		msg.replace("%objectId%", String.valueOf(20));
		activeChar.sendPacket(msg);
	}
	
	public String showMailSendWindow(L2PcInstance activeChar)
	{
		StringBuilder tb = new StringBuilder();
		tb.append("<html><head><title>Send a Mail</title></head><body>");
		
		tb.append("<br><font color=\"C99B10\">Welcome to the mail system.<br>Use the fields below in order to send messages to your friends.<br>Attention: Your title must include only 1 word.</font><br><br>");
		tb.append("<center>");
		tb.append("To:<br>");
		tb.append("<edit var=\"to\" width=\"120\" height=\"15\"><br><br>");
		tb.append("Title:<br>");
		tb.append("<edit var=\"title\" width=\"120\" height=\"15\"><br><br>");
		tb.append("Message:<br>");
		tb.append("<multiedit var=\"message\" width=\"120\" height=\"120\"><br><br>");
		tb.append("<button value=\"Send\" action=\"bypass -h sendMsg $to $title $message\" width=204 height=20>");
		
		tb.append("</center></body></html>");
		
		return tb.toString();
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return VOICED_COMMANDS;
	}
}