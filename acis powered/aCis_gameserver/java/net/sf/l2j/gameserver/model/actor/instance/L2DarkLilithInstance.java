package net.sf.l2j.gameserver.model.actor.instance;

import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.util.Rnd;

public class L2DarkLilithInstance extends L2NpcInstance
{
	public L2DarkLilithInstance(int objectId, NpcTemplate template)
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
				player.teleToLocation(-114119, -174276, -6757, 0);
			else if (chance == 1)
				player.teleToLocation(-113604, -174273, -6757, 0);
		}
		
		super.onBypassFeedback(player, command);
	}
	
	@Override
	public void showChatWindow(L2PcInstance player)
	{/** @formatter:off */
		NpcHtmlMessage htm = new NpcHtmlMessage(0);
		StringBuilder sb = new StringBuilder("<html><body>");
		sb.append("<center>");
		sb.append("<br><font color=\"FFA500\">" + player.getName() + " you found the path to Dark Omem's End. <br1>" +
		"You can now challenge on of the two great bosses Lilith. <br1>" +
		"Are you ready?<br1></font>");
		sb.append("<a action=\"bypass -h npc_%objectId%_1\">Teleport to Lilith.</a>");
		sb.append("</body></html>");
		htm.setHtml(sb.toString());
		htm.replace("%objectId%", String.valueOf(getObjectId()));
		player.sendPacket(htm);
	}/** @formatter:on */
}