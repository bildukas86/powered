package Extensions.CCB.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.cache.HtmCache;
import net.sf.l2j.gameserver.communitybbs.Manager.BaseBBSManager;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class OlyStatsBBSManager extends BaseBBSManager
{
	private static final Logger _log = Logger.getLogger(OlyStatsBBSManager.class.getName());
	private static int _posId;
	
	@Override
	public void parseCmd(String command, L2PcInstance activeChar)
	{
		if (command.equals("_bbsOlyStats"))
		{
			showClassMenu(activeChar);
		}
		else if (command.startsWith("_bbsClassList"))
		{
			StringTokenizer st = new StringTokenizer(command, ";");
			st.nextToken();
			int classId = Integer.valueOf(st.nextToken()).intValue();
			
			showClassList(activeChar, classId);
		}
		else
		{
			super.parseCmd(command, activeChar);
		}
	}
	
	@Override
	protected String getFolder()
	{
		return "OlyStats/";
	}
	
	public static OlyStatsBBSManager getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static void showClassMenu(L2PcInstance activeChar)
	{
		String content = HtmCache.getInstance().getHtm("data/html/CommunityBoard/OlyStats/main.htm");
		separateAndSend(content, activeChar);
	}
	
	private static void showClassList(L2PcInstance activeChar, int classId)
	{
		String content = HtmCache.getInstance().getHtm("data/html/CommunityBoard/OlyStats/list.htm");
		StringBuilder tb = new StringBuilder();
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			_posId = 0;
			PreparedStatement statement = con.prepareStatement("SELECT h.char_id, h.olympiad_points, h.competitions_done, ch.char_name, ch.online, cl.clan_name, cl.ally_name FROM olympiad_nobles h LEFT JOIN characters ch ON ch.obj_Id=h.char_id LEFT OUTER JOIN clan_data cl ON cl.clan_id=ch.clanid where h.class_id=? ORDER BY h.olympiad_points DESC, h.competitions_done DESC");
			
			statement.setInt(1, classId);
			ResultSet result = statement.executeQuery();
			while (result.next())
			{
				boolean status = false;
				_posId += 1;
				if (result.getInt("online") == 1)
				{
					status = true;
				}
				tb.append("<table border=0 cellspacing=0 cellpadding=2 width=610>");
				tb.append("<tr>");
				tb.append("<td FIXWIDTH=10></td>");
				tb.append("<td FIXWIDTH=40>" + _posId + ".</td>");
				tb.append("<td FIXWIDTH=190>" + result.getString("char_name") + "</td>");
				tb.append("<td FIXWIDTH=140>" + className(classId) + "</td>");
				tb.append("<td FIXWIDTH=100>" + result.getInt("olympiad_points") + "</td>");
				tb.append("<td FIXWIDTH=120>" + result.getInt("competitions_done") + "</td>");
				tb.append("<td FIXWIDTH=120>" + result.getString("clan_name") + "</td>");
				tb.append("<td FIXWIDTH=120>" + result.getString("ally_name") + "</td>");
				tb.append("<td FIXWIDTH=70>" + (status ? "<font color=99FF00>Online</font>" : "<font color=CC0000>Offline</font>") + "</td>");
				tb.append("<td FIXWIDTH=5></td>");
				tb.append("</tr>");
				tb.append("</table>");
				tb.append("<img src=\"L2UI.Squaregray\" width=\"610\" height=\"1\">");
			}
			result.close();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "Failed to load " + className(classId) + " list " + e.getMessage(), e);
		}
		content = content.replaceAll("%showList%", tb.toString());
		content = content.replaceAll("%className%", className(classId));
		separateAndSend(content, activeChar);
	}
	
	public static final String className(int classid)
	{
		Map<Integer, String> classList = new HashMap<>();
		
		classList.put(Integer.valueOf(88), "Duelist");
		classList.put(Integer.valueOf(89), "Dreadnought");
		classList.put(Integer.valueOf(90), "Phoenix Knight");
		classList.put(Integer.valueOf(91), "Hell Knight");
		classList.put(Integer.valueOf(92), "Sagittarius");
		classList.put(Integer.valueOf(93), "Adventurer");
		classList.put(Integer.valueOf(94), "Archmage");
		classList.put(Integer.valueOf(95), "Soultaker");
		classList.put(Integer.valueOf(96), "Arcana Lord");
		classList.put(Integer.valueOf(97), "Cardinal");
		classList.put(Integer.valueOf(98), "Hierophant");
		classList.put(Integer.valueOf(99), "Evas Templar");
		classList.put(Integer.valueOf(100), "Sword Muse");
		classList.put(Integer.valueOf(101), "Wind Rider");
		classList.put(Integer.valueOf(102), "Moonlight Sentinel");
		classList.put(Integer.valueOf(103), "Mystic Muse");
		classList.put(Integer.valueOf(104), "Elemental Master");
		classList.put(Integer.valueOf(105), "Evas Saint");
		classList.put(Integer.valueOf(106), "Shillien Templar");
		classList.put(Integer.valueOf(107), "Spectral Dancer");
		classList.put(Integer.valueOf(108), "Ghost Hunter");
		classList.put(Integer.valueOf(109), "Ghost Sentinel");
		classList.put(Integer.valueOf(110), "Storm Screamer");
		classList.put(Integer.valueOf(111), "Spectral Master");
		classList.put(Integer.valueOf(112), "Shillien Saint");
		classList.put(Integer.valueOf(113), "Titan");
		classList.put(Integer.valueOf(114), "Grand Khavatari");
		classList.put(Integer.valueOf(115), "Dominator");
		classList.put(Integer.valueOf(116), "Doomcryer");
		classList.put(Integer.valueOf(117), "Fortune Seeker");
		classList.put(Integer.valueOf(118), "Maestro");
		
		return classList.get(Integer.valueOf(classid));
	}
	
	private static class SingletonHolder
	{
		protected static final OlyStatsBBSManager _instance = new OlyStatsBBSManager();
	}
}