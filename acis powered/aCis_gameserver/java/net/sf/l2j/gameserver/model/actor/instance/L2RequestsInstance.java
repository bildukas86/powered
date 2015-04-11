package net.sf.l2j.gameserver.model.actor.instance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class L2RequestsInstance extends L2Npc
{
	public L2RequestsInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	public static ArrayList<String> reqs = new ArrayList<>();
	
	@SuppressWarnings("unused")
	@Override
	public void onBypassFeedback(final L2PcInstance player, String command)
	{
		if (player == null || command == null)
			return;
		
		if (command.startsWith("viewlist"))
		{
			StringBuilder sb = new StringBuilder();
			sb.append("<html><title>Requester NPC</title><body>");
			sb.append("<br><table width=300><tr><td>Title</td><td width=30>Info</td><td>Type</td></tr><br></font>");
			try (Connection con = L2DatabaseFactory.getInstance().getConnection())
			{
				PreparedStatement statement = con.prepareStatement("SELECT * FROM requests ORDER BY id DESC");
				
				ResultSet result = statement.executeQuery();
				
				String title;
				String desc;
				String type;
				int id;
				int counter = 0;
				while (result.next())
				{
					if (!reqs.contains(result.getString(2)))
					{
						reqs.add(result.getString(2));
					}
					title = result.getString(3);
					type = result.getString(4);
					desc = result.getString(5);
					id = result.getInt(1);
					sb.append("<tr><td>" + title + "</td><td><a  action=\"bypass -h showreq " + id + "\" <font color=\"FFFF00\">Info</font></a></td><td>" + type + "</td></tr>");
				}
				statement.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			sb.append("</body></html>");
			NpcHtmlMessage msg = new NpcHtmlMessage(getObjectId());
			msg.setHtml(sb.toString());
			player.sendPacket(msg);
			
		}
		else if (command.startsWith("post"))
		{
			StringBuilder sb = new StringBuilder();
			sb.append("<html noscrollbar><title>Requester NPC</title><body>");
			sb.append("Title:<edit var=\"title\" width=\"120\" height=\"15\"><br>");
			sb.append("Info: <multiedit var=\"info\" width=\"150\" height=\"120\"><br>");
			sb.append("Type:<combobox var=\"ty\" list=Request;Requester width=\"100\" height=\"150\"><br>");
			sb.append("<br><br><center><button value=\"Post\" action=\"bypass -h addreq $title $ty $info\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"></center>");
			sb.append("</body></html>");
			NpcHtmlMessage msg = new NpcHtmlMessage(getObjectId());
			msg.setHtml(sb.toString());
			player.sendPacket(msg);
		}
	}
	
	@Override
	public void showChatWindow(L2PcInstance player, int val)
	{
		StringBuilder tb = new StringBuilder();
		tb.append("<html><title>Requester NPC</title><body><center><br>");
		tb.append("Hello <font color=\"LEVEL\">" + player.getName() + "</font><br>Here you can view all requests<br> and post too.");
		tb.append("<br><img src=\"l2ui.squaregray\" width=\"270\" height=\"1\"><br>");
		tb.append("<button value=\"Request List\" action=\"bypass -h npc_%objectId%_viewlist\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\">");
		tb.append("<button value=\"Post\" action=\"bypass -h npc_%objectId%_post\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\">");
		
		NpcHtmlMessage msg = new NpcHtmlMessage(getObjectId());
		msg.setHtml(tb.toString());
		msg.replace("%objectId%", String.valueOf(getObjectId()));
		
		player.sendPacket(msg);
	}
}