package Extensions.Events;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Logger;

import javolution.util.FastList;
import javolution.util.FastMap;

import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.model.L2Clan;
import net.sf.l2j.gameserver.model.L2ClanMember;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class SiegeReward
{
	// Singleton
	private static SiegeReward _instance;
	static Logger _log = Logger.getLogger(SiegeReward.class.getName());
	
	// Config
	public static boolean ACTIVATED_SYSTEM;
	public static boolean REWARD_ACTIVE_MEMBERS_ONLY;
	
	// Constant
	private final FastList<RewardInfoz> _list;
	private final FastMap<Integer, FastList<ToReward>> _toReward; // Offline players that didn't get rewarded. =( poor guys, But they'll have a surprise
	
	public SiegeReward()
	{
		if (ACTIVATED_SYSTEM)
			_log.info("SiegeReward: Activated.");
		else
			_log.info("SiegeReward: Inactive.");
		_list = new FastList<>();
		_toReward = new FastMap<>();
		
	}
	
	public static SiegeReward getInstance()
	{
		if (_instance == null)
		{
			_instance = new SiegeReward();
			_instance.loadConfigs();
			_instance.loadOfflineMembers();
		}
		
		return _instance;
	}
	
	private void loadOfflineMembers()
	{
		// Mysql connector
		Connection con = null;
		
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement st = con.prepareStatement("select charId, itemId, count, castle_name, rewarded from reward_list");
			ResultSet rs = st.executeQuery();
			
			while (rs.next())
			{
				int charId = rs.getInt("charId");
				int itemId = rs.getInt("itemId");
				int count = rs.getInt("count");
				String castle_name = rs.getString("castle_name");
				boolean rewarded = rs.getBoolean("rewarded");
				
				if (rewarded)
				{
					deleteRewarded(charId, itemId);
					continue;
				}
				
				ToReward tr = new ToReward();
				tr.charId = charId;
				tr.itemId = itemId;
				tr.count = count;
				tr.castleName = castle_name;
				
				if (!_toReward.containsKey(charId))
				{
					try
					// prevent errors
					{
						_toReward.put(charId, new FastList<ToReward>());
					}
					finally
					{
						_toReward.get(charId).add(tr);
					}
				}
				else
					_toReward.get(charId).add(tr);
				
			}
			
			rs.close();
			st.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void deleteRewarded(int charId, int itemId)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement st = con.prepareStatement("DELETE FROM reward_list WHERE charId=? AND itemId=?");
			st.setInt(1, charId);
			st.setInt(2, itemId);
			st.execute();
			st.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void loadConfigs()
	{
		try
		{
			Properties prop = new Properties();
			prop.load(new FileInputStream(new File("./config/Events/SiegeRewards.properties")));
			
			ACTIVATED_SYSTEM = Boolean.parseBoolean(prop.getProperty("ActivateSystem", "false"));
			REWARD_ACTIVE_MEMBERS_ONLY = Boolean.parseBoolean(prop.getProperty("RewardOnlineOnly", "false"));
			
			if (ACTIVATED_SYSTEM)
			{
				String[] splitz = prop.getProperty("RewardInfo").split(";");
				
				for (String str : splitz)
				{
					String[] splits = str.split(",");
					_list.add(new RewardInfoz(splits));
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			_log.info("SiegeReward Loaded: " + _list.size() + " Reword Item(s).");
		}
	}
	
	public void storeDataBase(int charId, String castleName)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			for (RewardInfoz rewz : _list)
			{
				PreparedStatement st = con.prepareStatement("REPLACE INTO reward_list VALUES(?,?,?,?,?)");
				st.setInt(1, charId);
				st.setInt(2, rewz.getItemId());
				st.setInt(3, rewz.getItemCount());
				st.setString(4, castleName);
				st.setInt(5, 0);
				st.execute();
				st.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void processWorldEnter(L2PcInstance activeChar)
	{
		if (_toReward.containsKey(activeChar.getObjectId()))
		{
			String castleName = "";
			
			for (ToReward tr : _toReward.get(activeChar.getObjectId()))
			{
				activeChar.addItem("SiegeReward", tr.itemId, tr.count, activeChar, true);
				castleName = tr.castleName;
				tr.rewarded = true;
			}
			activeChar.sendMessage("Congratulations! You have been rewarded for the " + castleName + " siege victory!");
		}
	}
	
	public class ToReward
	{
		String castleName;
		int charId, itemId, count;
		boolean rewarded;
	}
	
	public class RewardInfoz
	{
		// Constants
		private final int _itemId;
		private final int _itemCount;
		
		public RewardInfoz(String... strings)
		{
			_itemId = Integer.parseInt(strings[0]);
			_itemCount = Integer.parseInt(strings[1]);
		}
		
		/**
		 * @return Returns the itemId.
		 */
		public int getItemId()
		{
			return _itemId;
		}
		
		/**
		 * @return Returns the itemCount.
		 */
		public int getItemCount()
		{
			return _itemCount;
		}
		
	}
	
	public void notifySiegeEnded(L2Clan clan, String castleName)
	{
		for (L2ClanMember member : clan.getMembers())
		{
			if (member.isOnline())
			{
				L2PcInstance activeChar = member.getPlayerInstance();
				
				for (RewardInfoz tr : _list)
					activeChar.addItem("SiegeReward", tr.getItemId(), tr.getItemCount(), activeChar, true);
				
				activeChar.sendMessage("Congratulations! You have been rewarded for the " + castleName + " siege victory!");
			}
			else
				storeDataBase(member.getObjectId(), castleName);
		}
	}
}