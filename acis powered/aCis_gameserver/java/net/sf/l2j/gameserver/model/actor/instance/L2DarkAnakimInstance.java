package net.sf.l2j.gameserver.model.actor.instance;

import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.util.Rnd;

public class L2DarkAnakimInstance extends L2NpcInstance
{
	public L2DarkAnakimInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		if (command.startsWith("1"))
		{
			int chance = Rnd.get(0, 1);
			if (chance == 0)
				player.teleToLocation(-119174, -178007, -6757, 0);
			else if (chance == 1)
				player.teleToLocation(-119163, -177499, -6757, 0);
		}
		
		super.onBypassFeedback(player, command);
	}
	
	@Override
	public void showChatWindow(L2PcInstance player)
	{
		NpcHtmlMessage htm = new NpcHtmlMessage(0);
		StringBuilder sb = new StringBuilder("<html><body>");
		sb.append("<center>");
		/*
		 * sb.append("<table width=\"250\" cellpadding=\"5\" bgcolor=\"000000\">"); sb.append("<tr>"); sb.append("<td width=\"45\" valign=\"top\" align=\"center\"><img src=\"L2ui_ch3.menubutton4\" width=\"38\" height=\"38\"></td>");
		 * sb.append("<td valign=\"top\"><font color=\"FF6600\">Top PvP/PK</font>"); sb.append("<br1><font color=\"00FF00\">" + player.getName() + "</font>, here you can find the top pvp and pk of our server.<br1></td>"); sb.append("</tr>"); sb.append("</table>");
		 */
		
		sb.append("<br><font color=\"FFA500\">" + player.getName() + " you found the path to Dark Omem's End. <br1>You can now challenge on of the two great bosses Anakim. <br1>Are you ready?<br1></font>");
		
		sb.append("<a action=\"bypass -h npc_%objectId%_1\">Teleport to Anakim.</a>");
		sb.append("</body></html>");
		htm.setHtml(sb.toString());
		htm.replace("%objectId%", String.valueOf(getObjectId()));
		player.sendPacket(htm);
	}
}