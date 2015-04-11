package Extensions.CCB.Manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.StringTokenizer;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.cache.HtmCache;
import net.sf.l2j.gameserver.communitybbs.Manager.BaseBBSManager;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class ProblemReportBBSManager extends BaseBBSManager
{
	static String _type = "";
	static String _majority = "";
	static String _title = "";
	
	public static ProblemReportBBSManager getInstance()
	{
		return SingletonHolder._instance;
	}
	
	@Override
	public void parseCmd(String command, L2PcInstance activeChar)
	{
		if (command.equals("_bbsProblemReport"))
		{
			showReportWindow(activeChar);
		}
		else if (command.startsWith("_bbsProblemReport"))
		{
			StringTokenizer st = new StringTokenizer(command, ";");
			st.nextToken();
			String secondCommand = st.nextToken();
			if (secondCommand.startsWith("toDescription"))
			{
				StringTokenizer st1 = new StringTokenizer(secondCommand);
				st1.nextToken();
				
				String text = "";
				
				setMajority(st1.nextToken());
				
				setType(st1.nextToken());
				while (st1.hasMoreTokens())
				{
					text = text + st1.nextToken() + " ";
				}
				if (text == "")
				{
					activeChar.sendMessage("Please insert title first.");
					return;
				}
				setTitle(text);
				
				showDescriptionWindow(activeChar);
			}
			else if (secondCommand.startsWith("submit"))
			{
				String description = secondCommand.substring(9);
				if (description == "")
				{
					activeChar.sendMessage("Please insert description first.");
					return;
				}
				if (description.length() >= 150)
				{
					activeChar.sendMessage("The current description lenght is " + description.length() + ". Maximum lenght is 800!");
					return;
				}
				try
				{
					String fname = "data/CustomLogs/BugReports/" + getMajority() + "_" + getType() + "_report_" + activeChar.getName() + ".txt";
					File file = new File(fname);
					boolean exist = file.createNewFile();
					if (!exist)
					{
						activeChar.sendMessage("You have already submit a report, staff member must confirm it first.");
						return;
					}
					FileWriter fstream = new FileWriter(fname);
					BufferedWriter out = new BufferedWriter(fstream);
					out.write("Problem Report");
					out.newLine();
					out.write("- - - - - - - - - - - - - - - - - - - -");
					out.newLine();
					out.write("Player Details:");
					out.newLine();
					out.write("Account: " + activeChar.getAccountName());
					out.newLine();
					out.write("Name: " + activeChar.getName());
					out.newLine();
					out.write("IP: " + activeChar.getClient().getConnection().getInetAddress().getHostAddress());
					out.newLine();
					out.write("- - - - - - - - - - - - - - - - - - - -");
					out.newLine();
					out.write("Type of report: " + getType());
					out.newLine();
					out.newLine();
					out.write("Majority of report: " + getMajority());
					out.newLine();
					out.newLine();
					out.write("Title: " + getTitle());
					out.newLine();
					out.newLine();
					out.write("Description: " + description);
					out.close();
					
					separateAndSend(HtmCache.getInstance().getHtm("data/html/CommunityBoard/Reporting/completed.htm"), activeChar);
					
					for (L2PcInstance gms : L2World.getAllGMs())
					{
						gms.sendMessage("ATTENTION: " + activeChar.getName() + " just submited a report! Please take care of his report by browsing in /data/CustomLogs/BugReports folder.");
					}
				}
				catch (Exception e)
				{
					activeChar.sendMessage("Failed to submit report. Try again or contact with staff member. This error should not occur.");
					e.printStackTrace();
					return;
				}
			}
		}
		else
		{
			super.parseCmd(command, activeChar);
		}
	}
	
	@Override
	protected String getFolder()
	{
		return "Reporting/";
	}
	
	private static void showReportWindow(L2PcInstance activeChar)
	{
		if (Config.ENABLE_BBS_REPORT)
		{
			String content = HtmCache.getInstance().getHtm("data/html/CommunityBoard/Reporting/main.htm");
			
			content = content.replaceAll("%charName%", activeChar.getName());
			separateAndSend(content, activeChar);
		}
		else
		{
			separateAndSend(HtmCache.getInstance().getHtm("data/html/CommunityBoard/Reporting/functionDisabled.htm"), activeChar);
		}
	}
	
	static void setType(String val)
	{
		_type = val;
	}
	
	static void setMajority(String val)
	{
		_majority = val;
	}
	
	static void setTitle(String val)
	{
		_title = val;
	}
	
	static String getType()
	{
		return _type;
	}
	
	static String getMajority()
	{
		return _majority;
	}
	
	static String getTitle()
	{
		return _title;
	}
	
	private static void showDescriptionWindow(L2PcInstance activeChar)
	{
		String content = HtmCache.getInstance().getHtm("data/html/CommunityBoard/Reporting/description.htm");
		
		content = content.replaceAll("%charName%", activeChar.getName());
		content = content.replaceAll("%type%", getType());
		content = content.replaceAll("%majority%", getMajority());
		content = content.replaceAll("%title%", getTitle());
		separateAndSend(content, activeChar);
	}
	
	private static class SingletonHolder
	{
		protected static final ProblemReportBBSManager _instance = new ProblemReportBBSManager();
	}
}