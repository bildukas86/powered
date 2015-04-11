package Extensions.VoicedCommands;

import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class WhoAmI implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{
		"whoami"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		if (command.equals("whoami"))
		{
			StringBuilder tb = new StringBuilder();
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			
			tb.append("<html><head><title>Who Am I ?</title></head>");
			tb.append("<body>");
			tb.append("<center>");
			tb.append("<table width=\"250\" cellpadding=\"5\" bgcolor=\"000000\">");
			tb.append("<tr>");
			tb.append("<td width=\"45\" valign=\"top\" align=\"center\"><img src=\"L2ui_ch3.menubutton4\" width=\"38\" height=\"38\"></td>");
			tb.append("<br>");
			tb.append("<td valign=\"top\"><font color=\"FF6600\">Personal Informations</font>");
			tb.append("<br>");
			tb.append("<br1><font color=\"00FF00\">" + activeChar.getName() + "</font>, read this to know some things about your self.</td></tr></table></center>");
			tb.append("<br>");
			tb.append("<font color=\"FFAA00\">Your account's username is :</font>" + activeChar.getAccountName());
			tb.append("<br>");
			tb.append("<font color=\"FFAA00\">Your character's name is :</font>" + activeChar.getName());
			tb.append("<br>");
			tb.append("<font color=\"FFAA00\">Your character's title is :</font>" + activeChar.getTitle());
			tb.append("<br>");
			tb.append("<font color=\"FFAA00\">Your adena's count is :</font>" + activeChar.getAdena());
			tb.append("<br>");
			tb.append("<font color=\"FFAA00\">Your clan's name is :</font>" + activeChar.getClan());
			tb.append("<br>");
			tb.append("<font color=\"FFAA00\">Your current CP is :</font>" + activeChar.getCurrentCp());
			tb.append("<br>");
			tb.append("<font color=\"FFAA00\">Your current HP is :</font>" + activeChar.getCurrentHp());
			tb.append("<br>");
			tb.append("<font color=\"FFAA00\">Your current MP is :</font>" + activeChar.getCurrentMp());
			tb.append("<br>");
			tb.append("<font color=\"FFAA00\">Your death penalty's level is :</font>" + activeChar.getDeathPenaltyBuffLevel());
			tb.append("<br>");
			tb.append("<font color=\"FFAA00\">Your karma total is :</font>" + activeChar.getKarma());
			tb.append("<br>");
			tb.append("<font color=\"FFAA00\">Your level is :</font>" + activeChar.getLevel());
			tb.append("<br>");
			tb.append("<font color=\"FFAA00\">Your max CP is :</font>" + activeChar.getMaxCp());
			tb.append("<br>");
			tb.append("<font color=\"FFAA00\">Your max MP is :</font>" + activeChar.getMaxMp());
			tb.append("<br>");
			tb.append("<font color=\"FFAA00\">Your max HP is :</font>" + activeChar.getMaxHp());
			tb.append("<br>");
			tb.append("<font color=\"FFAA00\">Your pk kills are :</font>" + activeChar.getPkKills());
			tb.append("<br>");
			tb.append("<font color=\"FFAA00\">Your pvp kills are :</font>" + activeChar.getPvpKills());
			tb.append("<br>");
			tb.append("<font color=\"FFAA00\">Your recommends are :</font>" + activeChar.getRecomHave());
			tb.append("<br>");
			tb.append("<font color=\"FFAA00\">Your total subclasses are :</font>" + activeChar.getTotalSubClasses());
			tb.append("<br>");
			tb.append("<br>");
			
			int playersOnline = L2World.getInstance().getAllPlayersCount();
			
			if (playersOnline == 1)
				tb.append("<font color=\"FF0000\">Server has </font>" + playersOnline + "<font color=\"FF0000\">player online!</font>");
			else
				tb.append("<font color=\"FF0000\">Server has </font>" + playersOnline + "<font color=\"FF0000\">players online!</font>");
			
			tb.append("<br>");
			tb.append("</center>");
			tb.append("</body></html>");
			
			html.setHtml(tb.toString());
			activeChar.sendPacket(html);
		}
		
		return true;
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return VOICED_COMMANDS;
	}
}