package net.sf.l2j.gameserver.model.actor.instance;

import java.util.StringTokenizer;

import net.sf.l2j.gameserver.model.GodSystem;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class L2GodManagerInstance extends L2NpcInstance
{
	public L2GodManagerInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void showChatWindow(L2PcInstance player)
	{
		player.sendPacket(ActionFailed.STATIC_PACKET);
		
		String filename = "data/html/mods/GodManagement/intro.htm";
		
		// Send a Server->Client NpcHtmlMessage containing the text of the L2Npc to the L2PcInstance
		NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
		html.setFile(filename);
		html.replace("%charname%", player.getName());
		html.replace("%godname%", GodSystem.returnGodName());
		html.replace("%remain%", GodSystem.returnRemainingTime());
		html.replace("%objectId%", String.valueOf(getObjectId()));
		player.sendPacket(html);
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		if (command.startsWith("voteForGod"))
		{
			String filename = "data/html/mods/GodManagement/vote.htm";
			NpcHtmlMessage html = new NpcHtmlMessage(0);
			html.setFile(filename);
			html.replace("%objectId%", String.valueOf(getObjectId()));
			player.sendPacket(html);
		}
		else if (command.startsWith("showGodRanking"))
		{
			GodSystem.showGodRanking(player);
		}
		else if (command.startsWith("showPersonalGodVotes"))
		{
			GodSystem.showPersonalGodVotes(player);
		}
		else if (command.startsWith("voteplayer"))
		{
			
			if (player.isGodVoted())
			{
				player.sendMessage("You have already voted , we are sorry!");
				return;
			}
			
			String charName = null;
			
			try
			{
				StringTokenizer stringTokenizer = new StringTokenizer(command);
				stringTokenizer.nextToken();
				charName = stringTokenizer.nextToken();
			}
			catch (Exception e)
			{
				return;
			}
			
			if (charName == null)
			{
				player.sendMessage("Wrong name");
				return;
			}
			
			GodSystem.vote(player, charName);
		}
	}
}