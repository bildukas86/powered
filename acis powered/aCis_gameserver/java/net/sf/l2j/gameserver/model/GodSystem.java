package net.sf.l2j.gameserver.model;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.clientpackets.Say2;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.util.Broadcast;

public class GodSystem
{
	
	private static final Map<String, Integer> godMap = new LinkedHashMap<>();
	private static final List<String> ips = new CopyOnWriteArrayList<>();
	private static final Logger _log = Logger.getLogger(GodSystem.class.getName());
	private static int started = 0;
	private static String GOD_NAME;
	
	public static String returnRemainingTime()
	{
		long millis = getTimeForNewStart();
		
		String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
		
		return hms;
	}
	
	public static String returnGodName()
	{
		return GOD_NAME;
	}
	
	public static void deleteAndUpdate()
	{
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement st = con.prepareStatement("SELECT obj_Id,godvotes FROM characters WHERE godvotes>0 ORDER BY godvotes DESC LIMIT 1");
			ResultSet rset = st.executeQuery();
			
			// store the new GOD infos
			int objectId = 0;
			int godVotes = 0;
			
			while (rset.next())
			{
				objectId = rset.getInt("obj_Id");
				godVotes = rset.getInt("godvotes");
			}
			
			Broadcast.announceToOnlinePlayers("God System initialized! New period started!");
			
			started = 0;
			GOD_NAME = "None";
			
			if (objectId == 0)
			{
				Broadcast.announceToOnlinePlayers("New GOD wasn't found, GOD remains the same player");
				return;
			}
			
			// delete old
			cleanGodSystem();
			
			// release new
			
			L2PcInstance player = L2World.getInstance().getPlayer(objectId);
			if (player != null)
			{
				player.setGod(true);
				player.sendPacket(new CreatureSay(0, Say2.SHOUT, "God Manager", "You are the new God! Congratulations!!!"));
				Broadcast.announceToOnlinePlayers("New God of server is " + player.getName() + " with " + godVotes + " votes!");
				
				ips.clear();
			}
			else
			{
				st = con.prepareStatement("UPDATE characters SET god=1 WHERE obj_Id=?");
				st.setInt(1, objectId);
				st.executeUpdate();
				
				st = con.prepareStatement("SELECT char_name FROM characters WHERE god=1");
				rset = st.executeQuery();
				
				String name = "";
				while (rset.next())
					name = rset.getString("char_name");
				
				Broadcast.announceToOnlinePlayers("New God of server is " + name + " with " + godVotes + " votes!");
				
				st = con.prepareStatement("DELETE FROM god_ips");
				st.executeUpdate();
				
				st.close();
				
			}
			
		}
		catch (Exception e)
		{
			_log.warning("Problem to delete/update new God: " + e.getMessage());
		}
	}
	
	protected static long getTimeForNewStart()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		cal.add(Calendar.WEEK_OF_MONTH, 1);
		cal.set(Calendar.HOUR, 00);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 1);
		
		long time = ((cal.getTimeInMillis() - System.currentTimeMillis()));
		
		return time;
	}
	
	protected static void cleanGodSystem()
	{
		for (L2PcInstance player : L2World.getInstance().getAllPlayers().values())
			if (player != null)
			{
				player.setGodVotes(0);
				player.setGod(false);
				player.setGodVoted(false);
			}
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement st = con.prepareStatement("UPDATE characters SET godvotes=0,godvoted=0,god=0");
			st.executeUpdate();
			st.close();
		}
		catch (Exception e)
		{
			_log.warning("Error ar clean gold System " + e.getMessage());
		}
	}
	
	public static void vote(L2PcInstance player, String playerToVote)
	{
		L2PcInstance playerVote = L2World.getInstance().getPlayer(playerToVote);
		
		if (player.getName().equals(playerToVote))
		{
			player.sendMessage("You cannot vote yourself, we are sorry!");
			return;
		}
		if (ips.contains(player.getClient().getConnection().getInetAddress().getHostAddress()))
		{
			player.sendMessage("You have already voted from this IP , we are sorry!");
			return;
		}
		boolean voted = false;
		if (playerVote != null)
		{
			player.votePlayer(playerVote);
			playerVote.sendMessage("You received one vote for God Player, congratulations!");
			player.sendPacket(new CreatureSay(0, Say2.SHOUT, "God Manager", "You voted successfully for " + playerToVote + ", thank you!"));
			voted = true;
		}
		else
		{
			try (Connection con = L2DatabaseFactory.getInstance().getConnection())
			{
				PreparedStatement st = con.prepareStatement("SELECT count(*) FROM characters WHERE UPPER(char_name)=UPPER(?)");
				st.setString(1, playerToVote);
				ResultSet rset = st.executeQuery();
				
				int count = 0;
				
				while (rset.next())
					count = rset.getInt(1);
				
				if (count == 0)
				{
					player.sendMessage("Incorrect name , we are sorry!");
					return;
				}
				
				st = con.prepareStatement("UPDATE characters SET godvotes=godvotes+1 WHERE char_name=?");
				st.setString(1, playerToVote);
				st.executeUpdate();
				
				player.setGodVoted(true);
				player.sendPacket(new CreatureSay(0, Say2.SHOUT, "God Manager", "You voted successfully for " + playerToVote + ", thank you!"));
				voted = true;
				rset.close();
				st.close();
				
			}
			catch (Exception e)
			{
				_log.warning("Failed to vote : " + e.getMessage());
			}
		}
		
		if (voted)
		{
			String ip = player.getClient().getConnection().getInetAddress().getHostAddress();
			
			try (Connection con = L2DatabaseFactory.getInstance().getConnection())
			{
				PreparedStatement st = con.prepareStatement("INSERT INTO god_ips VALUES (?)");
				st.setString(1, ip);
				st.executeUpdate();
				st.close();
			}
			catch (Exception e)
			{
				_log.warning(e.getMessage());
			}
		}
		
	}
	
	public static void showPersonalGodVotes(L2PcInstance player)
	{
		String filename = "data/html/mods/GodManagement/personal.htm";
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		html.setFile(filename);
		html.replace("%votes%", String.valueOf(player.getGodVotes()));
		player.sendPacket(html);
	}
	
	public static void showGodRanking(L2PcInstance player)
	{
		StringBuilder sb = new StringBuilder("<html><head>");
		sb.append("<title>God Manager</title></head>");
		sb.append("<body><center>");
		sb.append("<br><br>");
		sb.append("<table width=210 border=1");
		int counter = 1;
		
		sb.append("<tr>");
		
		sb.append("<td><font color=\"LEVEL\">Rank</font></td>");
		sb.append("<td><font color=\"LEVEL\">Name</font></td>");
		sb.append("<td><font color=\"LEVEL\">Votes</font></td>");
		
		sb.append("</tr>");
		for (String charname : godMap.keySet())
		{
			sb.append("<tr>");
			
			sb.append("<td>" + counter + "</td>");
			sb.append("<td>" + charname + "</td>");
			sb.append("<td>" + godMap.get(charname) + "</td>");
			
			sb.append("</tr>");
			
			counter++;
		}
		
		sb.append("</table>");
		sb.append("</center></body></html>");
		
		NpcHtmlMessage nhm = new NpcHtmlMessage(0);
		nhm.setHtml(sb.toString());
		player.sendPacket(nhm);
	}
	
	protected GodSystem()
	{
		ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new Runnable()
		{
			@Override
			public void run()
			{
				init();
			}
			
		}, 0, 30000);
		
		ThreadPoolManager.getInstance().scheduleGeneral(new NewStart(), getTimeForNewStart());
		
	}
	
	protected class NewStart implements Runnable
	{
		
		@Override
		public void run()
		{
			
			deleteAndUpdate();
			ThreadPoolManager.getInstance().scheduleGeneral(new NewStart(), getTimeForNewStart());
			
		}
		
	}
	
	protected void init()
	{
		godMap.clear();
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement st = con.prepareStatement("SELECT godvotes,char_name FROM characters WHERE godvotes>0 ORDER BY godvotes desc");
			ResultSet rset = st.executeQuery();
			
			while (rset.next())
			{
				int votes = rset.getInt("godvotes");
				String name = rset.getString("char_name");
				
				godMap.put(name, votes);
			}
			
			if (started == 0)
			{
				st = con.prepareStatement("SELECT char_name FROM characters WHERE god=1");
				rset = st.executeQuery();
				
				String godName = "";
				
				while (rset.next())
					godName = rset.getString("char_name");
				
				if (godName == "")
					GOD_NAME = "None";
				else
					GOD_NAME = godName;
				
				started++;
			}
			
			st = con.prepareStatement("SELECT ip FROM god_ips");
			rset = st.executeQuery();
			
			while (rset.next())
				ips.add(rset.getString("ip"));
			
			rset.close();
			st.close();
			
		}
		catch (Exception e)
		{
			_log.warning("Error at storing God Map : \n" + e.getMessage());
		}
	}
	
	public static GodSystem getInstance()
	{
		return SingletonHolder._instance;
	}
	
	protected static class SingletonHolder
	{
		protected final static GodSystem _instance = new GodSystem();
	}
}