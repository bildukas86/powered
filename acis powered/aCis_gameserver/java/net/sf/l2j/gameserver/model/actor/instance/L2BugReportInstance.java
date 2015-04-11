package net.sf.l2j.gameserver.model.actor.instance;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import net.sf.l2j.gameserver.ai.CtrlIntention;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.network.L2GameClient;
import net.sf.l2j.gameserver.network.clientpackets.Say2;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;
import net.sf.l2j.gameserver.network.serverpackets.MyTargetSelected;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.network.serverpackets.ValidateLocation;

public class L2BugReportInstance extends L2NpcInstance
{
	private static String _type;
	
	public L2BugReportInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		if (command.startsWith("send_report"))
		{
			try
			{
				StringTokenizer st = new StringTokenizer(command);
				st.nextToken();
				String msg = null;
				String type = null;
				type = st.nextToken();
				msg = st.nextToken();
				while (st.hasMoreTokens())
				{
					msg = msg + " " + st.nextToken();
				}
				
				sendReport(player, type, msg);
			}
			catch (StringIndexOutOfBoundsException e)
			{
				
			}
			catch(NoSuchElementException e1)
			{
				
			}
		}
	}
	
	private static void sendReport(L2PcInstance player, String command, String msg)
	{
		String type = command;
		L2GameClient info = player.getClient().getConnection().getClient();
		
		if (type.equals("General"))
			_type = "General";
		if (type.equals("Fatal"))
			_type = "Fatal";
		if (type.equals("Misuse"))
			_type = "Misuse";
		if (type.equals("Balance"))
			_type = "Balance";
		if (type.equals("Other"))
			_type = "Other";
		
		try
		{
			new File("data/CustomLogs/BugReports/").mkdirs();
			String fname = "data/CustomLogs/BugReports/" + player.getName() + ".txt";
			File file = new File(fname);
			boolean exist = file.createNewFile();
			if (!exist)
			{
				player.sendMessage("You have already sent a bug report, GMs must check it first.");
				return;
			}
			FileWriter fstream = new FileWriter(fname);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("Character Info: " + info + "\r\nBug Type: " + _type + "\r\nMessage: " + msg);
			player.sendMessage("Report sent. GMs will check it soon. Thanks...");
			
			for (L2PcInstance allgms : L2World.getAllGMs())
				allgms.sendPacket(new CreatureSay(0, Say2.SHOUT, "Bug Report Manager", player.getName() + " sent a bug report."));
			
			System.out.println("Character: " + player.getName() + " sent a bug report.");
			out.close();
		}
		catch (Exception e)
		{
			player.sendMessage("Something went wrong try again.");
		}
	}
	
	@Override
	public void onAction(L2PcInstance player)
	{
		if (player.getTarget() != this)
		{
			player.setTarget(this);
			player.sendPacket(new MyTargetSelected(getObjectId(), 0));
			player.sendPacket(new ValidateLocation(this));
		}
		else if (!canInteract(player))
			player.getAI().setIntention(CtrlIntention.INTERACT, this);
		else
			showHtmlWindow(player);
		player.sendPacket(new ActionFailed());
	}
	
	private void showHtmlWindow(L2PcInstance activeChar)
	{
		NpcHtmlMessage nhm = new NpcHtmlMessage(5);
		StringBuilder replyMSG = new StringBuilder("");
		
		replyMSG.append("<html><title>Bug Report Manager</title>");
		replyMSG.append("<body><br><br><center>");
		replyMSG.append("<table border=0 height=10 bgcolor=\"444444\" width=240>");
		replyMSG.append("<tr><td align=center><font color=\"00FFFF\">Hello " + activeChar.getName() + ".</font></td></tr>");
		replyMSG.append("<tr><td align=center><font color=\"00FFFF\">There are no Gms online</font></td></tr>");
		replyMSG.append("<tr><td align=center><font color=\"00FFFF\">and you want to report something?</font></td></tr>");
		replyMSG.append("</table><br>");
		replyMSG.append("<img src=\"L2UI.SquareWhite\" width=280 height=1><br><br>");
		replyMSG.append("<table width=250><tr>");
		replyMSG.append("<td><font color=\"LEVEL\">Select Report Type:</font></td>");
		replyMSG.append("<td><combobox width=105 var=type list=General;Fatal;Misuse;Balance;Other></td>");
		replyMSG.append("</tr></table><br><br>");
		replyMSG.append("<multiedit var=\"msg\" width=250 height=50><br>");
		replyMSG.append("<br><img src=\"L2UI.SquareWhite\" width=280 height=1><br><br><br><br><br><br><br>");
		replyMSG.append("<button value=\"Send Report\" action=\"bypass -h npc_" + getObjectId() + "_send_report $type $msg\" width=204 height=20 back=\"sek.cbui75\" fore=\"sek.cbui75\">");
		replyMSG.append("</center></body></html>");
		
		nhm.setHtml(replyMSG.toString());
		activeChar.sendPacket(nhm);
		
		activeChar.sendPacket(new ActionFailed());
	}
}