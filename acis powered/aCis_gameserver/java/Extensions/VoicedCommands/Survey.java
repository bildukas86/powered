package Extensions.VoicedCommands;

import Extensions.AdminCommands.AdminSurvey;

import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.L2GameClient;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class Survey implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{
		"survey"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		if (command.equals("survey"))
		{
			if (AdminSurvey.running == false)
			{
				activeChar.sendMessage("There is no survey running now");
				return false;
			}
			
			if (activeChar.hasVotedSurvey())
			{
				activeChar.sendMessage("You already voted for that survey.");
				return false;
			}
			
			if (AdminSurvey.running == true)
				mainHtml(activeChar);
		}
		
		return true;
	}
	
	public static void handleCommands(L2GameClient client, String command)
	{
		final L2PcInstance activeChar = client.getActiveChar();
		if (activeChar == null)
			return;
		
		if (command.equals("Survey_survey_vote1"))
		{
			if (AdminSurvey.running == false)
			{
				activeChar.sendMessage("There is no survey running now");
				return;
			}
			
			if (activeChar.hasVotedSurvey())
			{
				activeChar.sendMessage("You already voted for that survey.");
				return;
			}
			
			AdminSurvey.ans1_vote_count++;
			activeChar.sendMessage("You voted : " + AdminSurvey.ans1 + ". Thanks for voting");
			activeChar.setHasVotedSurvey(true);
		}
		else if (command.equals("Survey_survey_vote2"))
		{
			if (AdminSurvey.running == false)
			{
				activeChar.sendMessage("There is no survey running now");
				return;
			}
			if (activeChar.hasVotedSurvey())
			{
				activeChar.sendMessage("You already voted for that survey.");
				return;
			}
			
			AdminSurvey.ans2_vote_count++;
			activeChar.sendMessage("You voted : " + AdminSurvey.ans2 + ". Thanks for voting");
			activeChar.setHasVotedSurvey(true);
		}
		else if (command.equals("Survey_survey_vote3"))
		{
			if (AdminSurvey.running == false)
			{
				activeChar.sendMessage("There is no survey running now");
				return;
			}
			if (activeChar.hasVotedSurvey())
			{
				activeChar.sendMessage("You already voted for that survey.");
				return;
			}
			AdminSurvey.ans3_vote_count++;
			activeChar.sendMessage("You voted : " + AdminSurvey.ans3 + ". Thanks for voting");
			activeChar.setHasVotedSurvey(true);
		}
		else if (command.equals("Survey_survey_vote4"))
		{
			if (AdminSurvey.running == false)
			{
				activeChar.sendMessage("There is no survey running now");
				return;
			}
			if (activeChar.hasVotedSurvey())
			{
				activeChar.sendMessage("You already voted for that survey.");
				return;
			}
			AdminSurvey.ans4_vote_count++;
			activeChar.sendMessage("You voted : " + AdminSurvey.ans4 + ". Thanks for voting");
			activeChar.setHasVotedSurvey(true);
		}
		else if (command.equals("Survey_survey_vote5"))
		{
			if (AdminSurvey.running == false)
			{
				activeChar.sendMessage("There is no survey running now");
				return;
			}
			if (activeChar.hasVotedSurvey())
			{
				activeChar.sendMessage("You already voted for that survey.");
				return;
			}
			AdminSurvey.ans5_vote_count++;
			activeChar.sendMessage("You voted : " + AdminSurvey.ans5 + ". Thanks for voting");
			activeChar.setHasVotedSurvey(true);
		}
		
	}
	
	private static void mainHtml(L2PcInstance activeChar)
	{
		NpcHtmlMessage nhm = new NpcHtmlMessage(5);
		StringBuilder tb = new StringBuilder("");
		
		tb.append("<html><head><title>Survey form</title></head><body>");
		tb.append("<center>");
		tb.append("<table width=\"250\" cellpadding=\"5\" bgcolor=\"000000\">");
		tb.append("<tr>");
		tb.append("<td width=\"45\" valign=\"top\" align=\"center\"><img src=\"L2ui_ch3.menubutton4\" width=\"38\" height=\"38\"></td>");
		tb.append("<td valign=\"top\"><font color=\"FF6600\">Survey</font>");
		tb.append("<br1><font color=\"FF6600\">" + activeChar.getName() + "</font>, use this form in order to give us feedback.<br1></td>");
		tb.append("</tr>");
		tb.append("</table>");
		tb.append("</center>");
		tb.append("<center>");
		
		tb.append("<font color=\"FF6600\">The question set is:<br>");
		tb.append("<font color=\"FF0000\">" + AdminSurvey.quest + "</font>");
		tb.append("<br><font color=\"FF6600\">Choose an answer.");
		tb.append("<table width=\"300\" height=\"20\">");
		tb.append("<tr>");
		tb.append("<td align=\"center\" width=\"40\">Answer 1:</td>");
		tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + AdminSurvey.ans1 + "</font></td>");
		tb.append("<td align=\"center\"><a action=\"bypass -h Survey_survey_vote1\">Vote</a></td>");
		
		tb.append("</tr>");
		tb.append("<tr>");
		tb.append("<td align=\"center\" width=\"40\">Answer 2:</td>");
		tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + AdminSurvey.ans2 + "</font></td>");
		tb.append("<td align=\"center\"><a action=\"bypass -h Survey_survey_vote2\">Vote</a></td>");
		
		tb.append("</tr>");
		if (AdminSurvey.mode == 2)
		{
			tb.append("<tr>");
			tb.append("<td align=\"center\" width=\"40\">Answer 3:</td>");
			tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + AdminSurvey.ans3 + "</font></td>");
			tb.append("<td align=\"center\"><a action=\"bypass -h Survey_survey_vote3\">Vote</a></td>");
			
			tb.append("</tr>");
		}
		if (AdminSurvey.mode == 3)
		{
			tb.append("<tr>");
			tb.append("<td align=\"center\" width=\"40\">Answer 3:</td>");
			tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + AdminSurvey.ans3 + "</font></td>");
			tb.append("<td align=\"center\"><a action=\"bypass -h Survey_survey_vote3\">Vote</a></td>");
			
			tb.append("</tr>");
			tb.append("<tr>");
			tb.append("<td align=\"center\" width=\"40\">Answer 4:</td>");
			tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + AdminSurvey.ans4 + "</font></td>");
			tb.append("<td align=\"center\"><a action=\"bypass -h Survey_survey_vote4\">Vote</a></td>");
			
			tb.append("</tr>");
		}
		if (AdminSurvey.mode == 4)
		{
			tb.append("<tr>");
			tb.append("<td align=\"center\" width=\"40\">Answer 3:</td>");
			
			tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + AdminSurvey.ans3 + "</font></td>");
			tb.append("<td align=\"center\"><a action=\"bypass -h Survey_survey_vote3\">Vote</a></td>");
			
			tb.append("</tr>");
			tb.append("<tr>");
			tb.append("<td align=\"center\" width=\"40\">Answer 4:</td>");
			
			tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + AdminSurvey.ans4 + "</font></td>");
			tb.append("<td align=\"center\"><a action=\"bypass -h Survey_survey_vote4\">Vote</a></td>");
			
			tb.append("</tr>");
			tb.append("<tr>");
			tb.append("<td align=\"center\" width=\"40\">Answer 5:</td>");
			tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + AdminSurvey.ans5 + "</font></td>");
			tb.append("<td align=\"center\"><a action=\"bypass -h Survey_survey_vote5\">Vote</a></td>");
			
			tb.append("</tr>");
		}
		tb.append("</table><br>");
		tb.append("</center>");
		tb.append("</body></html>");
		
		nhm.setHtml(tb.toString());
		activeChar.sendPacket(nhm);
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return VOICED_COMMANDS;
	}
}