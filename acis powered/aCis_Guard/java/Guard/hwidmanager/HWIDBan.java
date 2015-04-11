package Guard.hwidmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.network.L2GameClient;

public class HWIDBan
{
	private static Logger _log = Logger.getLogger(HWIDBan.class.getName());
	private static HWIDBan _instance;

	private static Map<Integer, HWIDBanList> _lists;

	public static HWIDBan getInstance()
	{
		if (_instance == null)
		{
			_instance = new HWIDBan();
		}
		return _instance;
	}

	public static void reload()
	{
		_instance = new HWIDBan();
	}

	public HWIDBan()
	{
		_lists = new HashMap<Integer, HWIDBanList>();
		load();
	}

	private void load()
	{
		PreparedStatement statement = null;
		ResultSet rset = null;
		String HWID = "";
		int counterHWIDBan = 0;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			statement = con.prepareStatement("SELECT * FROM hwid_bans");
			rset = statement.executeQuery();
			while (rset.next())
			{

				HWID = rset.getString("HWID");
				HWIDBanList hb = new HWIDBanList(counterHWIDBan);
				hb.setHWIDBan(HWID);
				_lists.put(counterHWIDBan, hb);
				counterHWIDBan++;
			}
		}
		catch (Exception e)
		{
			_log.log(Level.INFO, "Could not select " + HWID + " from database!" + e);
		}
	}

	public boolean checkFullHWIDBanned(L2GameClient client)
	{
		if (_lists.size() == 0)
		{
			return false;
		}
		for (int i = 0; i < _lists.size(); i++)
		{
			if (_lists.get(i).getHWID().equals(client.getHWID()))
			{
				return true;
			}
		}
		return false;
	}

	public static int getCountHWIDBan()
	{
		return _lists.size();
	}

	public static void addHWIDBan(L2GameClient client)
	{
		String HWID = client.getHWID();
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("INSERT INTO hwid_bans SET HWID=?");
			statement.setString(1, HWID);
			statement.execute();
		}
		catch (Exception e)
		{
			_log.log(Level.INFO, "Client with hwid " + HWID + " failed insert into database!" + e);
		}
	}
}