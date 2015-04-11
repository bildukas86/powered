package Extensions.CCB;

import Extensions.Utilities.ClassList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.l2j.L2DatabaseFactory;

public class HeroeList
{
	protected static final Logger _log = Logger.getLogger(HeroeList.class.getName());
	
	private static final String SELECT_DATA = "SELECT h.count, h.played, ch.char_name, ch.base_class, ch.online, cl.clan_name, cl.ally_name FROM heroes h LEFT JOIN characters ch ON ch.obj_Id=h.char_id LEFT OUTER JOIN clan_data cl ON cl.clan_id=ch.clanid ORDER BY h.count DESC, ch.char_name ASC LIMIT 20";
	private int _posId;
	private final StringBuilder _heroeList = new StringBuilder();
	
	public HeroeList()
	{
		loadFromDB();
	}
	
	private void loadFromDB()
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			_posId = 0;
			PreparedStatement statement = con.prepareStatement(SELECT_DATA);
			ResultSet result = statement.executeQuery();
			
			while (result.next())
			{
				boolean status = false;
				_posId = _posId + 1;
				
				if (result.getInt("online") == 1)
					status = true;
				
				addPlayerToList(_posId, result.getInt("count"), result.getInt("played"), result.getString("char_name"), result.getInt("base_class"), result.getString("clan_name"), result.getString("ally_name"), status);
			}
			result.close();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, getClass().getName() + ": Error Loading DB " + e);
			{
				e.printStackTrace();
			}
		}
	}
	
	public String loadHeroeList()
	{
		return _heroeList.toString();
	}
	
	private void addPlayerToList(int objId, int count, int played, String name, int ChrClass, String clan, String ally, boolean isOnline)
	{
		_heroeList.append("<table border=0 cellspacing=0 cellpadding=2 width=610>");
		_heroeList.append("<tr>");
		_heroeList.append("<td FIXWIDTH=10></td>");
		_heroeList.append("<td FIXWIDTH=40>" + objId + ".</td>");
		_heroeList.append("<td FIXWIDTH=150>" + name + "</td>");
		_heroeList.append("<td FIXWIDTH=160>" + className(ChrClass) + "</td>");
		_heroeList.append("<td FIXWIDTH=80>" + count + "</td>");
		_heroeList.append("<td FIXWIDTH=80>" + played + "</td>");
		_heroeList.append("<td FIXWIDTH=160>" + clan + "</td>");
		_heroeList.append("<td FIXWIDTH=160>" + ally + "</td>");
		_heroeList.append("<td FIXWIDTH=70>" + ((isOnline) ? "<font color=99FF00>Online</font>" : "<font color=CC0000>Offline</font>") + "</td>");
		_heroeList.append("<td FIXWIDTH=5></td>");
		_heroeList.append("</tr>");
		_heroeList.append("</table>");
		_heroeList.append("<img src=\"L2UI.Squaregray\" width=\"610\" height=\"1\">");
	}
	
	public final static String className(int classId)
	{
		return String.valueOf(ClassList.className(classId));
	}
}