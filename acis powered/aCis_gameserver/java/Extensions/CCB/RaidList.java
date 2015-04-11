package Extensions.CCB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.l2j.Config;
import net.sf.l2j.L2DatabaseFactory;

public class RaidList
{
	protected static final Logger _log = Logger.getLogger(RaidList.class.getName());
	
	private static final String SELECT_RAID_DATA = "SELECT boss_id, name, level, spawn_time, random_time, respawn_time FROM raidboss_spawnlist ORDER BY level ";
	
	private final StringBuilder _raidList = new StringBuilder();
	
	public RaidList(String raids)
	{
		loadFromDB(raids);
	}
	
	private void loadFromDB(String raids)
	{
		int type = Integer.parseInt(raids);
		int stpoint = 0;
		int pos = 0;
		String sort = "";
		if (Config.RAID_LIST_SORT_ASC)
			sort = "ASC";
		else
			sort = "DESC";
		for (int count = 1; count != type; count++)
		{
			stpoint += Config.RAID_LIST_RESULTS;
		}
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement(SELECT_RAID_DATA + sort + " Limit " + stpoint + ", " + Config.RAID_LIST_RESULTS);
			ResultSet result = statement.executeQuery();
			pos = stpoint;
			
			while (result.next())
			{
				pos++;
				String npcname = result.getString("name");
				int rlevel = result.getInt("level");
				long respawn = result.getLong("respawn_time");
				
				boolean rstatus = false;
				if (respawn == 0)
					rstatus = true;
				
				addRaidToList(pos, npcname, rlevel, rstatus);
			}
			
			result.close();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": Error Loading DB " + e);
			e.printStackTrace();
		}
	}
	
	private void addRaidToList(int pos, String npcname, int rlevel, boolean rstatus)
	{
		try
		{
			_raidList.append("<table border=0 cellspacing=0 cellpadding=2 width=610 height=" + Config.RAID_LIST_ROW_HEIGHT + ">");
			_raidList.append("<tr>");
			_raidList.append("<td FIXWIDTH=5></td>");
			_raidList.append("<td FIXWIDTH=25>" + pos + "</td>");
			_raidList.append("<td FIXWIDTH=270>" + npcname + "</td>");
			_raidList.append("<td FIXWIDTH=50>" + rlevel + "</td>");
			_raidList.append("<td FIXWIDTH=50 align=center>" + ((rstatus) ? "<font color=99FF00>Alive</font>" : "<font color=CC0000>Dead</font>") + "</td>");
			_raidList.append("<td FIXWIDTH=5></td>");
			_raidList.append("</tr>");
			_raidList.append("</table>");
			_raidList.append("<img src=\"L2UI.Squaregray\" width=\"610\" height=\"1\">");
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	public String loadRaidList()
	{
		return _raidList.toString();
	}
}