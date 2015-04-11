package net.sf.l2j.gameserver;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.clientpackets.Say2;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.util.Broadcast;

public class Announcements
{
	private static Logger _log = Logger.getLogger(Announcements.class.getName());
	
	private static Announcements _instance;
	private final List<String> _announcements = new ArrayList<>();
	
	private Announcements()
	{
		loadAnnouncements();
	}
	
	public static Announcements getInstance()
	{
		if (_instance == null)
			_instance = new Announcements();
		
		return _instance;
	}
	
	public void loadAnnouncements()
	{
		_announcements.clear();
		File file = new File("./data/announcements.txt");
		if (file.exists())
			readFromDisk(file);
		else
			_log.config("The announcements file (normally located to 'data/announcements.txt') doesn't exist.");
	}
	
	public void showAnnouncements(L2PcInstance activeChar)
	{
		for (int i = 0; i < _announcements.size(); i++)
		{
			CreatureSay cs = new CreatureSay(0, Say2.ANNOUNCEMENT, activeChar.getName(), _announcements.get(i));
			activeChar.sendPacket(cs);
		}
	}
	
	public void listAnnouncements(L2PcInstance activeChar)
	{
		NpcHtmlMessage adminReply = new NpcHtmlMessage(0);
		adminReply.setFile("data/html/admin/announce.htm");
		StringBuilder replyMSG = new StringBuilder("<br>");
		for (int i = 0; i < _announcements.size(); i++)
		{
			replyMSG.append("<table width=260><tr><td width=220>" + _announcements.get(i) + "</td><td width=40>");
			replyMSG.append("<button value=\"Delete\" action=\"bypass -h admin_del_announcement " + i + "\" width=60 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></td></tr></table>");
		}
		adminReply.replace("%announces%", replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}
	
	public void addAnnouncement(String text)
	{
		_announcements.add(text);
		saveToDisk();
	}
	
	public void delAnnouncement(int line)
	{
		_announcements.remove(line);
		saveToDisk();
	}
	
	private void readFromDisk(File file)
	{
		try (LineNumberReader lnr = new LineNumberReader(new FileReader(file)))
		{
			int i = 0;
			String line = null;
			while ((line = lnr.readLine()) != null)
			{
				StringTokenizer st = new StringTokenizer(line, "\n\r");
				if (st.hasMoreTokens())
				{
					String announcement = st.nextToken();
					_announcements.add(announcement);
					
					i++;
				}
			}
			_log.config("Announcements: Loaded " + i + " Announcements.");
		}
		catch (IOException e1)
		{
			_log.log(Level.SEVERE, "Error reading announcements", e1);
		}
	}
	
	private void saveToDisk()
	{
		final File file = new File("data/announcements.txt");
		try (FileWriter save = new FileWriter(file))
		{
			for (int i = 0; i < _announcements.size(); i++)
			{
				save.write(_announcements.get(i));
				save.write("\r\n");
			}
		}
		catch (IOException e)
		{
			_log.warning("saving the announcements file has failed: " + e);
		}
	}
	
	public static void announceToAll(String text)
	{
		Broadcast.announceToOnlinePlayers(text);
	}
	
	public static void announceToAll(SystemMessage sm)
	{
		Broadcast.toAllOnlinePlayers(sm);
	}
	
	// Method for handling announcements from admin
	public static void handleAnnounce(String command, int lengthToTrim)
	{
		try
		{
			// Announce string to everyone on server
			announceToAll(command.substring(lengthToTrim));
		}
		catch (StringIndexOutOfBoundsException e)
		{
		}
	}
}