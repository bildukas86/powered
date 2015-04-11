package Extensions.CCB;

import Extensions.Utilities.ClassList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.l2j.Config;
import net.sf.l2j.L2DatabaseFactory;


public class TopPlayers
{
	protected static final Logger _log = Logger.getLogger(TopPlayers.class.getName());

	private static final String SELECT_CHARS = "SELECT SUM(chr.points), SUM(it.count), ch.char_name, ch.pkkills, ch.pvpkills, ch.onlinetime, ch.base_class, ch.online FROM characters ch LEFT JOIN character_raid_points chr ON ch.obj_Id=ch.obj_Id LEFT OUTER JOIN items it ON ch.obj_Id=it.owner_id WHERE item_id=57 GROUP BY ch.obj_Id ORDER BY ";
	
	private int pos;
	private final StringBuilder _topList = new StringBuilder();
	String sort = "";

	public TopPlayers(String file)
	{
		loadDB(file);
	}

	private void loadDB(String file)
	{
		switch (file)
		{
			case "toppvp":
				sort = "pvpkills";
			break;
			case "toppk":
				sort = "pkkills";
			break;
			case "topadena":
				sort = "SUM(it.count)";
			break;
			case "toprbrank":
				sort = "SUM(chr.points)";
			break;
			case "toponline":
				sort = "onlinetime";
			break;
			default:
			break;

		}

		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			pos = 0;
			PreparedStatement statement = con.prepareStatement(SELECT_CHARS + sort + " DESC LIMIT " + Config.TOP_PLAYER_RESULTS);

			ResultSet result = statement.executeQuery();

			while (result.next())
			{
				boolean status = false;
				pos++;

				if (result.getInt("online") == 1)
				{
					status = true;
				}
				String timeon = getPlayerRunTime(result.getInt("ch.onlinetime"));
				String adenas = getAdenas(result.getLong("SUM(it.count)"));

				addChar(pos, result.getString("ch.char_name"), result.getInt("base_class"), result.getInt("ch.pvpkills"), result.getInt("ch.pkkills"), result.getInt("SUM(chr.points)"), adenas, timeon, status);
			}

			result.close();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": Could not Select Top Players " + e);
		}
	}

	public String loadTopList()
	{
		return _topList.toString();
	}

	private void addChar(int position, String name, int classid, int pvp, int pk, int raid, String adenas, String online, boolean isOnline)
	{
		_topList.append("<table border=0 cellspacing=0 cellpadding=2 bgcolor=050505 height=" + Config.TOP_PLAYER_ROW_HEIGHT + "><tr><td FIXWIDTH=5></td>");
		_topList.append("<td FIXWIDTH=27>" + position + ".</td>");
		_topList.append("<td FIXWIDTH=160>" + name + "</td>");
		_topList.append("<td FIXWIDTH=145>" + className(classid) + "</td>");
		_topList.append("<td FIXWIDTH=60>" + pvp + "</td>");
		_topList.append("<td FIXWIDTH=60>" + pk + "</td>");
		_topList.append("<td FIXWIDTH=60>" + raid + "</td>");
		_topList.append("<td FIXWIDTH=150>" + adenas + "</td>");
		_topList.append("<td FIXWIDTH=150>" + online + "</td>");
		_topList.append("<td FIXWIDTH=65>" + ((isOnline) ? "<font color=99FF00>Online</font>" : "<font color=CC0000>Offline</font>") + "</td>");
		_topList.append("</tr></table><img src=\"L2UI.Squaregray\" width=\"610\" height=\"1\">");
	}

	public final static String className(int classId)
	{
		return String.valueOf(ClassList.className(classId));
	}

	public String getPlayerRunTime(int secs)
	{
		String timeResult = "";
		timeResult = ((secs >= 86400 ? Integer.toString(secs / 86400) + " Days " + Integer.toString((secs % 86400) / 3600) + " hours." : Integer.toString(secs / 3600) + " Hours " + Integer.toString((secs % 3600) / 60) + " mins."));
		return timeResult;
	}

	public String getAdenas(Long adena)
	{
		String adenas = "";
		adenas = (adena >= 1000000000 ? Long.toString(adena / 1000000000) + " Billion " + Long.toString((adena % 1000000000) / 1000000) + " million" : Long.toString(adena / 1000000) + " Million " + Long.toString((adena % 1000000) / 1000) + " k");
		return adenas;
	}
}