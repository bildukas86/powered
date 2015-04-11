package Extensions.AdminCommands;

import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.clientpackets.Say2;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.util.Broadcast;

import java.util.Collection;
import java.util.StringTokenizer;

public class AdminSurvey implements IAdminCommandHandler
{
	public static int options = 2;
	public static int mode = 0;
	public static boolean running = false;
	private static boolean qset = false;
	private static boolean ans1set = false;
	private static boolean ans2set = false;
	private static boolean ans3set = false;
	private static boolean ans4set = false;
	private static boolean ans5set = false;
	public static String quest = "";
	public static String ans1 = "";
	public static String ans2 = "";
	public static String ans3 = "";
	public static String ans4 = "";
	public static String ans5 = "";
	public static int ans1_vote_count = 0;
	public static int ans2_vote_count = 0;
	public static int ans3_vote_count = 0;
	public static int ans4_vote_count = 0;
	public static int ans5_vote_count = 0;
	
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_survey_start",
		"admin_survey_results",
		"admin_opmore",
		"admin_opless",
		"admin_survey_run1",
		"admin_survey_run2",
		"admin_survey_run3",
		"admin_survey_run4",
		"admin_survey_qset",
		"admin_survey_ans1set",
		"admin_survey_ans2set",
		"admin_survey_ans3set",
		"admin_survey_ans4set",
		"admin_survey_ans5set",
		"admin_survey_end",
		"admin_survey_results"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.equals("admin_survey_start"))
			startHtml(activeChar);
		
		if (command.equals("admin_survey_results"))
			resultsHtml(activeChar);
		
		if (command.equals("admin_survey_end"))
		{
			int moreVotes = ans1_vote_count;
			if (moreVotes < ans2_vote_count)
			{
				moreVotes = ans2_vote_count;
			}
			if (moreVotes < ans3_vote_count)
			{
				moreVotes = ans3_vote_count;
			}
			if (moreVotes < ans4_vote_count)
			{
				moreVotes = ans4_vote_count;
			}
			if (moreVotes < ans5_vote_count)
			{
				moreVotes = ans5_vote_count;
			}
			
			Broadcast.toAllOnlinePlayers(new CreatureSay(0, Say2.ANNOUNCEMENT, "Survey", "The survey session is over.Thanks everyone for voting."));
			if (moreVotes == ans1_vote_count)
				Broadcast.toAllOnlinePlayers(new CreatureSay(0, Say2.ANNOUNCEMENT, "Survey", "The answer " + ans1 + " won the survey with " + ans1_vote_count + " votes on the question : " + quest + "."));
			if (moreVotes == ans2_vote_count)
				Broadcast.toAllOnlinePlayers(new CreatureSay(0, Say2.ANNOUNCEMENT, "Survey", "The answer " + ans2 + " won the survey with " + ans2_vote_count + " votes on the question : " + quest + "."));
			if (moreVotes == ans3_vote_count)
				Broadcast.toAllOnlinePlayers(new CreatureSay(0, Say2.ANNOUNCEMENT, "Survey", "The answer " + ans3 + " won the survey with " + ans3_vote_count + " votes on the question : " + quest + "."));
			if (moreVotes == ans4_vote_count)
				Broadcast.toAllOnlinePlayers(new CreatureSay(0, Say2.ANNOUNCEMENT, "Survey", "The answer " + ans4 + " won the survey with " + ans4_vote_count + " votes on the question : " + quest + "."));
			if (moreVotes == ans5_vote_count)
				Broadcast.toAllOnlinePlayers(new CreatureSay(0, Say2.ANNOUNCEMENT, "Survey", "The answer " + ans5 + " won the survey with " + ans5_vote_count + " votes on the question : " + quest + "."));
			running = false;
			resultsHtml(activeChar);
			quest = "";
			ans1 = "";
			ans2 = "";
			ans3 = "";
			ans4 = "";
			ans5 = "";
			mode = 0;
			ans1_vote_count = 0;
			ans2_vote_count = 0;
			ans3_vote_count = 0;
			ans4_vote_count = 0;
			ans5_vote_count = 0;
			setQset(false);
			setAns1set(false);
			setAns2set(false);
			setAns3set(false);
			setAns4set(false);
			setAns5set(false);
			
			Collection<L2PcInstance> pls = L2World.getInstance().getAllPlayers().values();
			for (L2PcInstance onlinePlayers : pls)
			{
				onlinePlayers.setHasVotedSurvey(false);
			}
			
			Broadcast.toAllOnlinePlayers(new CreatureSay(0, Say2.ANNOUNCEMENT, "Survey", "The survey session is over.Thanks everyone for voting."));
			
		}
		
		if (command.equals("admin_opmore"))
			if (options <= 4)
			{
				options++;
				startHtml(activeChar);
			}
			else
			{
				return false;
			}
		
		if (command.equals("admin_opless"))
			if (options >= 3)
			{
				options--;
				startHtml(activeChar);
			}
			else
			{
				return false;
			}
		
		if (command.startsWith("admin_survey_qset"))
		{
			if (isQset())
			{
				quest = "";
				setQset(false);
				startHtml(activeChar);
			}
			else if (!isQset())
			{
				StringTokenizer s = new StringTokenizer(command);
				s.nextToken();
				
				try
				{
					
					while (s.hasMoreTokens())
						quest = quest + s.nextToken() + " ";
					setQset(true);
					startHtml(activeChar);
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		if (command.startsWith("admin_survey_ans1set"))
		{
			if (isAns1set())
			{
				ans1 = "";
				setAns1set(false);
				startHtml(activeChar);
			}
			else if (!isAns1set())
			{
				StringTokenizer s = new StringTokenizer(command);
				s.nextToken();
				
				try
				{
					
					while (s.hasMoreTokens())
						ans1 = ans1 + s.nextToken() + " ";
					setAns1set(true);
					startHtml(activeChar);
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		if (command.startsWith("admin_survey_ans2set"))
		{
			if (isAns2set())
			{
				ans2 = "";
				setAns2set(false);
				startHtml(activeChar);
			}
			else if (!isAns2set())
			{
				StringTokenizer s = new StringTokenizer(command);
				s.nextToken();
				
				try
				{
					
					while (s.hasMoreTokens())
						ans2 = ans2 + s.nextToken() + " ";
					setAns2set(true);
					startHtml(activeChar);
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		if (command.startsWith("admin_survey_ans3set"))
		{
			if (isAns3set())
			{
				ans3 = "";
				setAns3set(false);
				startHtml(activeChar);
			}
			else if (!isAns3set())
			{
				StringTokenizer s = new StringTokenizer(command);
				s.nextToken();
				
				try
				{
					
					while (s.hasMoreTokens())
						ans3 = ans3 + s.nextToken() + " ";
					setAns3set(true);
					startHtml(activeChar);
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		if (command.startsWith("admin_survey_ans4set"))
		{
			if (isAns4set())
			{
				ans4 = "";
				setAns4set(false);
				startHtml(activeChar);
			}
			else if (!isAns4set())
			{
				StringTokenizer s = new StringTokenizer(command);
				s.nextToken();
				
				try
				{
					
					while (s.hasMoreTokens())
						ans4 = ans4 + s.nextToken() + " ";
					setAns4set(true);
					startHtml(activeChar);
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		if (command.startsWith("admin_survey_ans5set"))
		{
			if (isAns5set())
			{
				ans5 = "";
				setAns5set(false);
				startHtml(activeChar);
			}
			else if (!isAns5set())
			{
				StringTokenizer s = new StringTokenizer(command);
				s.nextToken();
				
				try
				{
					
					while (s.hasMoreTokens())
						ans5 = ans5 + s.nextToken() + " ";
					setAns5set(true);
					startHtml(activeChar);
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		if (command.startsWith("admin_survey_run1"))
		{
			if (running == true)
			{
				activeChar.sendMessage("A survey is already in progres.");
				return false;
			}
			if (!isQset() || !isAns1set() || !isAns2set())
			{
				activeChar.sendMessage("You have to set all the fields before you start the survey");
				return false;
			}
			mode = 1;
			running = true;
			Broadcast.toAllOnlinePlayers(new CreatureSay(0, Say2.ANNOUNCEMENT, "Survey", "Admin started a new survey with main question " + quest + ". Use .survey to vote."));
			
		}
		
		if (command.startsWith("admin_survey_run2"))
		{
			if (running == true)
			{
				activeChar.sendMessage("A survey is already in progress");
				return false;
				
			}
			if (!isQset() || !isAns1set() || !isAns2set() || !isAns3set())
			{
				activeChar.sendMessage("You have to set all the fields before you start the survey");
				return false;
			}
			mode = 2;
			running = true;
			Broadcast.toAllOnlinePlayers(new CreatureSay(0, Say2.ANNOUNCEMENT, "Survey", "Admin started a new survey with main question " + quest + ". Use .survey to vote."));
			
		}
		
		if (command.startsWith("admin_survey_run3"))
		{
			if (running == true)
			{
				activeChar.sendMessage("A survey is already in progress");
				return false;
				
			}
			
			if (!isQset() || !isAns1set() || !isAns2set() || !isAns3set() || !isAns4set())
			{
				activeChar.sendMessage("You have to set all the fields before you start the survey");
				return false;
			}
			mode = 3;
			running = true;
			Broadcast.toAllOnlinePlayers(new CreatureSay(0, Say2.ANNOUNCEMENT, "Survey", "Admin started a new survey with main question " + quest + ". Use .survey to vote."));
			
		}
		
		if (command.startsWith("admin_survey_run4"))
		{
			if (running == true)
			{
				activeChar.sendMessage("A survey is already in progress");
				return false;
				
			}
			
			if (!isQset() || !isAns1set() || !isAns2set() || !isAns3set() || !isAns4set() || !isAns5set())
			{
				activeChar.sendMessage("You have to set all the fields before you start the survey");
				return false;
			}
			mode = 4;
			running = true;
			Broadcast.toAllOnlinePlayers(new CreatureSay(0, Say2.ANNOUNCEMENT, "Survey", "Admin started a new survey with main question " + quest + ". Use .survey to vote."));
			
		}
		
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
	private static void startHtml(L2PcInstance activeChar)
	{
		NpcHtmlMessage nhm = new NpcHtmlMessage(5);
		StringBuilder tb = new StringBuilder("");
		
		tb.append("<html><head><title>Start Survey form</title></head><body>");
		tb.append("<center>");
		tb.append("<table width=\"250\" cellpadding=\"5\" bgcolor=\"000000\">");
		tb.append("<tr>");
		tb.append("<td width=\"45\" valign=\"top\" align=\"center\"><img src=\"L2ui_ch3.menubutton4\" width=\"38\" height=\"38\"></td>");
		tb.append("<td valign=\"top\"><font color=\"FF6600\">Start a Survey</font>");
		tb.append("<br1><font color=\"FF6600\">" + activeChar.getName() + "</font>, use this form in order to start a survey.<br1></td>");
		tb.append("</tr>");
		tb.append("</table>");
		tb.append("</center>");
		tb.append("<center>");
		if (!isQset())
		{
			tb.append("<font color=\"FF6600\">Type in the main question of the survey.</font><br>");
			tb.append("<table border=\"0\" width=\"250\" height=\"16\" bgcolor=\"000000\">");
			tb.append("<tr><td><multiedit var=\"quest\" width=170 height=20></td><td><a action=\"bypass admin_survey_qset $quest\">Save</a></td></tr></table>");
		}
		if (isQset())
		{
			tb.append("<font color=\"FF6600\">The question set is:<br>");
			tb.append("<table border=\"0\" width=\"250\" height=\"16\" bgcolor=\"000000\">");
			tb.append("<tr><td><font color=\"FF0000\">" + quest + "</font></td><td><a action=\"bypass admin_survey_qset\">Edit</a></td></tr></table>");
		}
		tb.append("<br><font color=\"FF6600\">Possible answers.");
		tb.append("<table border=\"0\" width=\"70\" height=\"16\" bgcolor=\"000000\">");
		tb.append("<tr>");
		tb.append("<td width=\"52\">More</td>");
		tb.append("<td width=\"16\"><button action=\"bypass admin_opmore\" width=16 height=16 back=\"L2UI_CH3.upbutton_down\" fore=\"L2UI_CH3.upbutton\"></td>");
		tb.append("</tr>");
		tb.append("<tr>");
		tb.append("<td width=\"52\">Less</td>");
		tb.append("<td width=\"16\"><button action=\"bypass admin_opless\" width=16 height=16 back=\"L2UI_CH3.downbutton_down\" fore=\"L2UI_CH3.downbutton_down\"></td>");
		tb.append("</tr>");
		tb.append("</table>");
		tb.append("<table width=\"300\" height=\"20\">");
		tb.append("<tr>");
		tb.append("<td align=\"center\" width=\"40\">Answer 1:</td>");
		if (!isAns1set())
		{
			tb.append("<td align=\"center\" width=\"150\"><multiedit var=\"ans1\" width=150 height=16></td>");
			tb.append("<td><a action=\"bypass admin_survey_ans1set $ans1\">Save</a></td>");
		}
		else if (isAns1set())
		{
			tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + ans1 + "</font></td>");
			tb.append("<td align=\"center\"><a action=\"bypass admin_survey_ans1set\">Edit</a></td>");
		}
		tb.append("</tr>");
		tb.append("<tr>");
		tb.append("<td align=\"center\" width=\"40\">Answer 2:</td>");
		if (!isAns2set())
		{
			tb.append("<td align=\"center\" width=\"150\"><multiedit var=\"ans2\" width=150 height=16></td>");
			tb.append("<td align=\"center\"><a action=\"bypass admin_survey_ans2set $ans2\">Save</a></td>");
		}
		else if (isAns2set())
		{
			tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + ans2 + "</font></td>");
			tb.append("<td align=\"center\"><a action=\"bypass admin_survey_ans2set\">Edit</a></td>");
		}
		tb.append("</tr>");
		if (options == 3)
		{
			tb.append("<tr>");
			tb.append("<td align=\"center\" width=\"40\">Answer 3:</td>");
			if (!isAns3set())
			{
				tb.append("<td align=\"center\" width=\"150\"><multiedit var=\"ans3\" width=150 height=16></td>");
				tb.append("<td align=\"center\"><a action=\"bypass admin_survey_ans3set $ans3\">Save</a></td>");
			}
			else if (isAns3set())
			{
				tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + ans3 + "</font></td>");
				tb.append("<td align=\"center\"><a action=\"bypass admin_survey_ans3set\">Edit</a></td>");
			}
			tb.append("</tr>");
		}
		if (options == 4)
		{
			tb.append("<tr>");
			tb.append("<td align=\"center\" width=\"40\">Answer 3:</td>");
			if (!isAns3set())
			{
				tb.append("<td align=\"center\" width=\"150\"><multiedit var=\"ans3\" width=150 height=16></td>");
				tb.append("<td align=\"center\"><a action=\"bypass admin_survey_ans3set $ans3\">Save</a></td>");
			}
			else if (isAns3set())
			{
				tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + ans3 + "</font></td>");
				tb.append("<td align=\"center\"><a action=\"bypass admin_survey_ans3set\">Edit</a></td>");
			}
			tb.append("</tr>");
			tb.append("<tr>");
			tb.append("<td align=\"center\" width=\"40\">Answer 4:</td>");
			if (!isAns4set())
			{
				tb.append("<td align=\"center\" width=\"150\"><multiedit var=\"ans4\" width=150 height=16></td>");
				tb.append("<td align=\"center\"><a action=\"bypass admin_survey_ans4set $ans4\">Save</a></td>");
			}
			else if (isAns4set())
			{
				tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + ans4 + "</font></td>");
				tb.append("<td align=\"center\"><a action=\"bypass admin_survey_ans4set\">Edit</a></td>");
			}
			tb.append("</tr>");
		}
		if (options == 5)
		{
			tb.append("<tr>");
			tb.append("<td align=\"center\" width=\"40\">Answer 3:</td>");
			if (!isAns3set())
			{
				tb.append("<td align=\"center\" width=\"150\"><multiedit var=\"ans3\" width=150 height=16></td>");
				tb.append("<td align=\"center\"><a action=\"bypass admin_survey_ans3set $ans3\">Save</a></td>");
			}
			else if (isAns3set())
			{
				tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + ans3 + "</font></td>");
				tb.append("<td align=\"center\"><a action=\"bypass admin_survey_ans3set\">Edit</a></td>");
			}
			tb.append("</tr>");
			tb.append("<tr>");
			tb.append("<td align=\"center\" width=\"40\">Answer 4:</td>");
			if (!isAns4set())
			{
				tb.append("<td align=\"center\" width=\"150\"><multiedit var=\"ans4\" width=150 height=16></td>");
				tb.append("<td align=\"center\"><a action=\"bypass admin_survey_ans4set $ans4\">Save</a></td>");
			}
			else if (isAns4set())
			{
				tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + ans4 + "</font></td>");
				tb.append("<td align=\"center\"><a action=\"bypass admin_survey_ans4set\">Edit</a></td>");
			}
			tb.append("</tr>");
			tb.append("<tr>");
			tb.append("<td align=\"center\" width=\"40\">Answer 5:</td>");
			if (!isAns5set())
			{
				tb.append("<td align=\"center\" width=\"150\"><multiedit var=\"ans5\" width=150 height=16></td>");
				tb.append("<td align=\"center\"><a action=\"bypass admin_survey_ans5set $ans5\">Save</a></td>");
			}
			else if (isAns5set())
			{
				tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + ans5 + "</font></td>");
				tb.append("<td align=\"center\"><a action=\"bypass admin_survey_ans5set\">Edit</a></td>");
			}
			tb.append("</tr>");
		}
		tb.append("</table><br>");
		if (options == 2)
			tb.append("<button value=\"Start the survey\" action=\"bypass -h admin_survey_run1\" width=150 height=22 back=\"L2UI_Ch3.bigbutton3_over\" fore=\"L2UI_Ch3.bigbutton3\">");
		if (options == 3)
			tb.append("<button value=\"Start the survey\" action=\"bypass -h admin_survey_run2\" width=150 height=22 back=\"L2UI_Ch3.bigbutton3_over\" fore=\"L2UI_Ch3.bigbutton3\">");
		if (options == 4)
			tb.append("<button value=\"Start the survey\" action=\"bypass -h admin_survey_run3\" width=150 height=22 back=\"L2UI_Ch3.bigbutton3_over\" fore=\"L2UI_Ch3.bigbutton3\">");
		if (options == 5)
			tb.append("<button value=\"Start the survey\" action=\"bypass -h admin_survey_run4\" width=150 height=22 back=\"L2UI_Ch3.bigbutton3_over\" fore=\"L2UI_Ch3.bigbutton3\">");
		tb.append("</center>");
		tb.append("</body></html>");
		
		nhm.setHtml(tb.toString());
		activeChar.sendPacket(nhm);
	}
	
	private static void resultsHtml(L2PcInstance activeChar)
	{
		NpcHtmlMessage nhm = new NpcHtmlMessage(5);
		StringBuilder tb = new StringBuilder("");
		
		tb.append("<html><head><title>Survey form</title></head><body>");
		tb.append("<center>");
		tb.append("<table width=\"250\" cellpadding=\"5\" bgcolor=\"000000\">");
		tb.append("<tr>");
		tb.append("<td width=\"45\" valign=\"top\" align=\"center\"><img src=\"L2ui_ch3.menubutton4\" width=\"38\" height=\"38\"></td>");
		tb.append("<td valign=\"top\"><font color=\"FF6600\">Survey</font>");
		tb.append("<br1><font color=\"FF6600\">" + activeChar.getName() + "</font>, here are the survey's results.<br1></td>");
		tb.append("</tr>");
		tb.append("</table>");
		tb.append("</center>");
		tb.append("<center>");
		tb.append("<font color=\"FF6600\">The question set is:<br>");
		tb.append("<font color=\"FF0000\">" + AdminSurvey.quest + "</font></center>");
		tb.append("<br><font color=\"FF6600\">Choose an answer.");
		tb.append("<table width=\"300\" height=\"20\">");
		tb.append("<tr>");
		tb.append("<td align=\"center\" width=\"40\">Answer 1:</td>");
		tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + AdminSurvey.ans1 + "</font></td>");
		tb.append("<td align=\"center\"><font color=\"FF0000\">" + AdminSurvey.ans1_vote_count + "</font></td>");
		tb.append("</tr>");
		tb.append("<tr>");
		tb.append("<td align=\"center\" width=\"40\">Answer 2:</td>");
		tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + AdminSurvey.ans2 + "</font></td>");
		tb.append("<td align=\"center\"><font color=\"FF0000\">" + AdminSurvey.ans2_vote_count + "</font></td>");
		tb.append("</tr>");
		if (mode == 2)
		{
			tb.append("<tr>");
			tb.append("<td align=\"center\" width=\"40\">Answer 3:</td>");
			tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + AdminSurvey.ans3 + "</font></td>");
			tb.append("<td align=\"center\"><font color=\"FF0000\">" + AdminSurvey.ans3_vote_count + "</font></td>");
			
			tb.append("</tr>");
		}
		if (mode == 3)
		{
			tb.append("<tr>");
			tb.append("<td align=\"center\" width=\"40\">Answer 3:</td>");
			tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + AdminSurvey.ans3 + "</font></td>");
			tb.append("<td align=\"center\"><font color=\"FF0000\">" + AdminSurvey.ans3_vote_count + "</font></td>");
			
			tb.append("</tr>");
			tb.append("<tr>");
			tb.append("<td align=\"center\" width=\"40\">Answer 4:</td>");
			tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + AdminSurvey.ans4 + "</font></td>");
			tb.append("<td align=\"center\"><font color=\"FF0000\">" + AdminSurvey.ans4_vote_count + "</font></td>");
			
			tb.append("</tr>");
		}
		if (mode == 4)
		{
			tb.append("<tr>");
			tb.append("<td align=\"center\" width=\"40\">Answer 3:</td>");
			
			tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + AdminSurvey.ans3 + "</font></td>");
			tb.append("<td align=\"center\"><font color=\"FF0000\">" + AdminSurvey.ans3_vote_count + "</font></td>");
			
			tb.append("</tr>");
			tb.append("<tr>");
			tb.append("<td align=\"center\" width=\"40\">Answer 4:</td>");
			
			tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + AdminSurvey.ans4 + "</font></td>");
			tb.append("<td align=\"center\"><font color=\"FF0000\">" + AdminSurvey.ans4_vote_count + "</font></td>");
			
			tb.append("</tr>");
			tb.append("<tr>");
			tb.append("<td align=\"center\" width=\"40\">Answer 5:</td>");
			tb.append("<td align=\"center\" width=\"150\"><font color=\"FF0000\">" + AdminSurvey.ans5 + "</font></td>");
			tb.append("<td align=\"center\"><font color=\"FF0000\">" + AdminSurvey.ans5_vote_count + "</font></td>");
			
			tb.append("</tr>");
		}
		tb.append("</table><br>");
		if (running == true)
			tb.append("<center><button value=\"End the survey\" action=\"bypass admin_survey_end\" width=150 height=22 back=\"L2UI_Ch3.bigbutton3_over\" fore=\"L2UI_Ch3.bigbutton3\"></center>");
		tb.append("</body></html>");
		
		nhm.setHtml(tb.toString());
		activeChar.sendPacket(nhm);
	}
	
	/**
	 * @return the qset
	 */
	public static boolean isQset()
	{
		return qset;
	}
	
	/**
	 * @param qset the qset to set
	 */
	public static void setQset(boolean qset)
	{
		AdminSurvey.qset = qset;
	}
	
	/**
	 * @return the ans1set
	 */
	public static boolean isAns1set()
	{
		return ans1set;
	}
	
	/**
	 * @param ans1set the ans1set to set
	 */
	public static void setAns1set(boolean ans1set)
	{
		AdminSurvey.ans1set = ans1set;
	}
	
	/**
	 * @return the ans2set
	 */
	public static boolean isAns2set()
	{
		return ans2set;
	}
	
	/**
	 * @param ans2set the ans2set to set
	 */
	public static void setAns2set(boolean ans2set)
	{
		AdminSurvey.ans2set = ans2set;
	}
	
	/**
	 * @return the ans3set
	 */
	public static boolean isAns3set()
	{
		return ans3set;
	}
	
	/**
	 * @param ans3set the ans3set to set
	 */
	public static void setAns3set(boolean ans3set)
	{
		AdminSurvey.ans3set = ans3set;
	}
	
	/**
	 * @return the ans4set
	 */
	public static boolean isAns4set()
	{
		return ans4set;
	}
	
	/**
	 * @param ans4set the ans4set to set
	 */
	public static void setAns4set(boolean ans4set)
	{
		AdminSurvey.ans4set = ans4set;
	}
	
	/**
	 * @return the ans5set
	 */
	public static boolean isAns5set()
	{
		return ans5set;
	}
	
	/**
	 * @param ans5set the ans5set to set
	 */
	public static void setAns5set(boolean ans5set)
	{
		AdminSurvey.ans5set = ans5set;
	}
}