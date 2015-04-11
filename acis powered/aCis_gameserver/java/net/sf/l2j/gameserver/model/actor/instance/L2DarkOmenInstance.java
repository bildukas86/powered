package net.sf.l2j.gameserver.model.actor.instance;

import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class L2DarkOmenInstance extends L2NpcInstance
{
	public L2DarkOmenInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		if (command.startsWith("1"))
		{
			player.teleToLocation(-19195, 13501, -4898, 0);
		}
		
		super.onBypassFeedback(player, command);
	}
	
	@Override
	public void showChatWindow(L2PcInstance player)
	{/** @formatter:off */
		NpcHtmlMessage htm = new NpcHtmlMessage(0);
		StringBuilder sb = new StringBuilder("<html><body>");
		sb.append("<center>");
		sb.append("<br><font color=\"00A5FF\">" + player.getName() + "</font>, <font color=\"FFA500\">the gates of Dark Omen Catacomb are open.<br1>" +
		"Are you ready to face your inner fears?<br1>" + 
		"If you do you can proceed by clicking the <br1>" +
		"button below. Remember though. There are many<br1>" +
		"strong enemies inside the Catacomb of Dark Omens. <br1>" +
		"On the other hand they will reward you<br1>" +
		"greatly for killing them. At the end of the Catacomb <br1>" +
		"it is said that 2 great enemies live. Find their gates <br1>" + 
		"and challenge them. Time to write some history!</font><br1>");
		sb.append("<a action=\"bypass -h npc_%objectId%_1\">Enter The Dark Omen.</a>");
		sb.append("</body></html>");
		htm.setHtml(sb.toString());
		htm.replace("%objectId%", String.valueOf(getObjectId()));
		player.sendPacket(htm);
	}/** @formatter:on */
}