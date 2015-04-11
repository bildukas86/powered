package Extensions.Events.Phoenix.Engines;

import Extensions.Events.Phoenix.EventManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Set;

import javolution.util.FastMap;

import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.ShowBoard;

public class EventStats
{
	public FastMap<Integer, int[]> tempTable;
	private FastMap<Integer, FastMap<Integer, StatModell>> stats;
	private FastMap<Integer, ShowBoard> htmls;
	private FastMap<Integer, int[]> statSums;
	
	private static class SingletonHolder
	{
		protected static final EventStats _instance = new EventStats();
	}
	
	public static EventStats getInstance()
	{
		return SingletonHolder._instance;
	}
	
	protected class StatModell
	{
		protected int num;
		protected int wins;
		protected int losses;
		protected int kills;
		protected int deaths;
		protected int scores;
		
		protected StatModell(int num, int wins, int losses, int kills, int deaths, int scores)
		{
			this.num = num;
			this.wins = wins;
			this.losses = losses;
			this.kills = kills;
			this.deaths = deaths;
			this.scores = scores;
		}
	}
	
	public EventStats()
	{
		stats = new FastMap<>();
		tempTable = new FastMap<>();
		htmls = new FastMap<>();
		statSums = new FastMap<>();
		loadSQL();
	}
	
	public void applyChanges()
	{
		if (!EventManager.getInstance().getBoolean("statTrackingEnabled"))
			return;
		
		int eventId = EventManager.getInstance().getCurrentEvent().eventId;
		for (L2PcInstance player : EventManager.getInstance().getCurrentEvent().getPlayerList())
		{
			int playerId = player.getObjectId();
			
			if (!stats.containsKey(playerId))
				stats.put(playerId, new FastMap<Integer, StatModell>());
			
			if (!stats.get(playerId).containsKey(eventId))
				stats.get(playerId).put(eventId, new StatModell(0, 0, 0, 0, 0, 0));
			
			if (tempTable.get(playerId)[0] == 1)
				stats.get(playerId).get(eventId).wins = stats.get(playerId).get(eventId).wins + 1;
			else
				stats.get(playerId).get(eventId).losses = stats.get(playerId).get(eventId).losses + 1;
			
			stats.get(playerId).get(eventId).num = stats.get(playerId).get(eventId).num + 1;
			stats.get(playerId).get(eventId).kills = stats.get(playerId).get(eventId).kills + tempTable.get(playerId)[1];
			stats.get(playerId).get(eventId).deaths = stats.get(playerId).get(eventId).deaths + tempTable.get(playerId)[2];
			stats.get(playerId).get(eventId).scores = stats.get(playerId).get(eventId).scores + tempTable.get(playerId)[3];
		}
	}
	
	protected void createHtmls()
	{
		htmls.clear();
		StringBuilder sb = new StringBuilder();
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT characters.char_name, event_stats_full.* FROM event_stats_full INNER JOIN characters ON characters.obj_Id = event_stats_full.player ORDER BY event_stats_full.wins DESC");
			ResultSet rset = statement.executeQuery();
			rset.last();
			rset.beforeFirst();
			int count = 0;
			while (rset.next())
			{
				count++;
				// @formatter:off
				if (count % 10 == 1)
					sb.append("<html><body><br><br><center>" +
							"<table width=615 bgcolor=5A5A5A><tr>" +
							"<td width=30><center>Rank</td><td width=100><center>Name</td>" +
							"<td width=55><center>Events</td><td width=55><center>Win%</td>" +
							"<td width=55><center>K:D</td><td width=55><center>Wins</td>" +
							"<td width=55><center>Losses</td><td width=55><center>Kills</td>" +
							"<td width=55><center>Deaths</td><td width=100><center>Favourite Event</td>" +
							"</tr></table><br>" + 
							"<center><table width=635>");
				
				sb.append("<tr><td width=30><center>" + count + "</td>" +
						"<td width=100>" + rset.getString("char_name") + "</td>" +
						"<td width=55><center>" + rset.getInt("num") + "</td>" +
						"<td width=55><center>" + rset.getInt("winpercent") + "%</td>" +
						"<td width=55><center>" + rset.getDouble("kdratio") + "</td>" +
						"<td width=55><center>" + rset.getInt("wins") + "</td>" +
						"<td width=55><center>" + rset.getInt("losses") + "</td>" +
						"<td width=55><center>" + rset.getInt("kills") + "</td>" + 
						"<td width=55><center>" + rset.getInt("deaths") + "</td>" +
						"<td width=120><center>" + EventManager.getInstance().events.get(rset.getInt("favevent")).getString("eventName") + 
						"</td></tr>");
				// @formatter:on
				if (count % 10 == 0)
				{
					sb.append("</table></body></html>");
					htmls.put(count / 10, new ShowBoard(sb.toString(), "101"));
				}
			}
			if (count % 10 != 0 && !htmls.containsKey(count / 10 + 1))
			{
				sb.append("</table></body></html>");
				htmls.put((count / 10 + 1), new ShowBoard(sb.toString(), "101"));
			}
			rset.close();
			statement.close();
		}
		catch (Exception e)
		{
			System.out.println("create SQL exception. " + e);
		}
	}
	
	private void loadSQL()
	{
		if (!EventManager.getInstance().getBoolean("statTrackingEnabled"))
			return;
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT * FROM event_stats");
			ResultSet rset = statement.executeQuery();
			while (rset.next())
			{
				if (!stats.containsKey(rset.getInt("player")))
					stats.put(rset.getInt("player"), new FastMap<Integer, StatModell>());
				
				stats.get(rset.getInt("player")).put(rset.getInt("event"), new StatModell(rset.getInt("num"), rset.getInt("wins"), rset.getInt("losses"), rset.getInt("kills"), rset.getInt("deaths"), rset.getInt("scores")));
			}
			rset.close();
			statement.close();
		}
		catch (Exception e)
		{
			System.out.println("EventStats SQL catch " + e);
		}
		createHtmls();
	}
	
	public void showHtml(int id, L2PcInstance player)
	{
		if (!EventManager.getInstance().getBoolean("statTrackingEnabled"))
		{
			player.sendMessage("The stat tracking is disabled.");
			return;
		}
		
		player.sendPacket(htmls.get(id));
		player.sendPacket(new ShowBoard(null, "102"));
		player.sendPacket(new ShowBoard(null, "103"));
	}
	
	public void showPlayerStats(int playerId, L2PcInstance player)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<html><body><br><br><center><table width=640 bgcolor=5A5A5A><tr><td width=120><center>Event</td><td width=65><center>Count</td><td width=65><center>Win%</td><td width=65><center>K:D</td><td width=65><center>Wins</td><td width=65><center>Losses</td><td width=65><center>Kills</td><td width=65><center>Deaths</td><td width=65><center>Scores</td></tr></table><br>" + "<center><table width=640>");
		
		if (stats.containsKey(playerId))
		{
			for (Map.Entry<Integer, StatModell> event : stats.get(playerId).entrySet())
			{
				StatModell stats = event.getValue();
				
				if (EventManager.getInstance().events.containsKey(event.getKey()))
					sb.append("<tr><td width=120>" + EventManager.getInstance().events.get(event.getKey()).getString("eventName") + "</td><td width=65><center>" + stats.num + "</td><td width=65><center>" + (stats.wins / stats.num) * 100 + "%</td><td width=65><center>" + (stats.deaths == 0 ? (double) stats.kills / stats.num : (double) ((stats.kills / stats.deaths) / stats.num)) + "</td><td width=65><center>" + stats.wins + "</td><td width=65><center>" + stats.losses + "</td><td width=65><center>" + stats.kills / stats.num + "</td><td width=65><center>" + stats.deaths / stats.num + "</td><td width=65><center>" + stats.scores / stats.num + "</td></tr>");
			}
		}
		
		sb.append("</table></body></html>");
		player.sendPacket(new ShowBoard(sb.toString(), "101"));
		player.sendPacket(new ShowBoard(null, "102"));
		player.sendPacket(new ShowBoard(null, "103"));
	}
	
	public int getEventKills(int playerId)
	{
		int kills = 0;
		
		if (!stats.containsKey(playerId))
			return 0;
		
		for (Map.Entry<Integer, StatModell> statmodell : stats.get(playerId).entrySet())
			kills += statmodell.getValue().kills;
		
		return kills;
	}
	
	public int getEvents(int playerId)
	{
		int num = 0;
		
		if (!stats.containsKey(playerId))
			return 0;
		
		for (Map.Entry<Integer, StatModell> statmodell : stats.get(playerId).entrySet())
			num += statmodell.getValue().num;
		
		return num;
	}
	
	public int getEventWins(int playerId)
	{
		int wins = 0;
		
		if (!stats.containsKey(playerId))
			return 0;
		
		for (Map.Entry<Integer, StatModell> statmodell : stats.get(playerId).entrySet())
			wins += statmodell.getValue().wins;
		
		return wins;
	}
	
	// num | wins | losses | kills | deaths | fav_event_id
	protected void sumPlayerStats()
	{
		if (!EventManager.getInstance().getBoolean("statTrackingEnabled"))
			return;
		
		statSums.clear();
		
		for (int playerId : stats.keySet())
		{
			int num = 0;
			int wins = 0;
			int losses = 0;
			int kills = 0;
			int deaths = 0;
			int faveventid = 0;
			int faveventamm = 0;
			
			for (Map.Entry<Integer, StatModell> statmodell : stats.get(playerId).entrySet())
			{
				num += statmodell.getValue().num;
				wins += statmodell.getValue().wins;
				losses += statmodell.getValue().losses;
				kills += statmodell.getValue().kills;
				deaths += statmodell.getValue().deaths;
				
				if (statmodell.getValue().num > faveventamm)
				{
					faveventamm = statmodell.getValue().num;
					faveventid = statmodell.getKey();
				}
			}
			statSums.put(playerId, new int[]
			{
				num,
				wins,
				losses,
				kills,
				deaths,
				faveventid
			});
		}
	}
	
	public void updateSQL(Set<L2PcInstance> players, int eventId)
	{
		if (!EventManager.getInstance().getBoolean("statTrackingEnabled"))
			return;
		
		sumPlayerStats();
		PreparedStatement statement = null;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			for (L2PcInstance player : players)
			{
				int id = player.getObjectId();
				
				if (statSums.get(id)[0] != 1)
				{
					statement = con.prepareStatement("UPDATE event_stats_full SET num=?, winpercent=?, kdratio=?, wins=?, losses=?, kills=?, deaths=?, favevent=? WHERE player=?");
					statement.setInt(1, statSums.get(id)[0]);
					statement.setInt(2, (statSums.get(id)[0] == 0 ? 1 : statSums.get(id)[1] / statSums.get(id)[0]) * 100);
					statement.setDouble(3, (statSums.get(id)[4] == 0 ? (double) statSums.get(id)[3] : (double) (statSums.get(id)[3] / statSums.get(id)[4])));
					statement.setInt(4, statSums.get(id)[1]);
					statement.setInt(5, statSums.get(id)[2]);
					statement.setInt(6, statSums.get(id)[3]);
					statement.setInt(7, statSums.get(id)[4]);
					statement.setInt(8, statSums.get(id)[5]);
					statement.setInt(9, id);
					
					statement.executeUpdate();
					statement.close();
				}
				else
				{
					statement = con.prepareStatement("INSERT INTO event_stats_full(player,num,winpercent,kdratio,wins,losses,kills,deaths,favevent) VALUES (?,?,?,?,?,?,?,?,?)");
					statement.setInt(1, id);
					statement.setInt(2, statSums.get(id)[0]);
					statement.setInt(3, (statSums.get(id)[0] == 0 ? 1 : statSums.get(id)[1] / statSums.get(id)[0]) * 100);
					statement.setDouble(4, (statSums.get(id)[4] == 0 ? (double) statSums.get(id)[3] : (double) (statSums.get(id)[3] / statSums.get(id)[4])));
					statement.setInt(5, statSums.get(id)[1]);
					statement.setInt(6, statSums.get(id)[2]);
					statement.setInt(7, statSums.get(id)[3]);
					statement.setInt(8, statSums.get(id)[4]);
					statement.setInt(9, statSums.get(id)[5]);
					statement.executeUpdate();
					statement.close();
				}
				
				if (stats.get(id).get(eventId).num != 1)
				{
					statement = con.prepareStatement("UPDATE event_stats SET num=?, wins=?, losses=?, kills=?, deaths=?, scores=? WHERE player=? AND event=?");
					statement.setInt(1, stats.get(id).get(eventId).num);
					statement.setInt(2, stats.get(id).get(eventId).wins);
					statement.setInt(3, stats.get(id).get(eventId).losses);
					statement.setInt(4, stats.get(id).get(eventId).kills);
					statement.setInt(5, stats.get(id).get(eventId).deaths);
					statement.setInt(6, stats.get(id).get(eventId).scores);
					statement.setInt(7, id);
					statement.setInt(8, eventId);
					statement.executeUpdate();
					statement.close();
				}
				else
				{
					statement = con.prepareStatement("INSERT INTO event_stats(player,event,num,wins,losses,kills,deaths,scores) VALUES (?,?,?,?,?,?,?,?)");
					statement.setInt(1, id);
					statement.setInt(2, eventId);
					statement.setInt(3, stats.get(id).get(eventId).num);
					statement.setInt(4, stats.get(id).get(eventId).wins);
					statement.setInt(5, stats.get(id).get(eventId).losses);
					statement.setInt(6, stats.get(id).get(eventId).kills);
					statement.setInt(7, stats.get(id).get(eventId).deaths);
					statement.setInt(8, stats.get(id).get(eventId).scores);
					statement.executeUpdate();
					statement.close();
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("EventStats SQL catch " + e);
		}
		createHtmls();
	}
}