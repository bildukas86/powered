package Extensions.Vip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.l2j.Config;
import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class VIPEngine
{
	private static final Logger _log = Logger.getLogger(VIPEngine.class.getName());
	
	// Data
	private static Map<Integer, Long> _vips;
	private ScheduledFuture<?> _vipClean;
	
	public static VIPEngine getInstance()
	{
		return SingletonHolder._instance;
	}
	
	protected VIPEngine()
	{
		_vips = new HashMap<>();
		
		// Loading data to map
		loadData();
		// Clean up before thread starts
		vipCleanUp();
		// Starting main thread
		initTask();
	}
	
	private void initTask()
	{
		_vipClean = ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new vipMaintenanceTask(), 21600000, 21600000);
		_log.info("Vip system threads started.");
	}
	
	private class vipMaintenanceTask implements Runnable
	{
		@Override
		public void run()
		{
			vipCleanUp();
		}
		
		public vipMaintenanceTask()
		{
		}
	}
	
	public void vipCleanUp()
	{
		synchronized (_vips)
		{
			Iterator<Map.Entry<Integer, Long>> it = _vips.entrySet().iterator();
			
			while (it.hasNext())
			{
				long time = System.currentTimeMillis() - Config.VIP_LIFE_TIME;
				long savedTime = it.next().getValue();
				
				if (time > savedTime)
					it.remove();
				
				_log.info("Vips table has been updated.");
			}
		}
	}
	
	/**
	 * Checks Vip status on a single player
	 * @param killer
	 * @return
	 */
	public boolean isVip(L2Character killer)
	{
		return _vips.containsKey(killer.getObjectId());
	}
	
	/**
	 * Get all vips from _vips
	 * @return
	 */
	public Collection<Integer> getAllVips()
	{
		return _vips.keySet();
	}
	
	/**
	 * get all vip timers
	 * @return
	 */
	public Collection<Long> getVipTimers()
	{
		return _vips.values();
	}
	
	/**
	 * returns the time of a single player
	 * @param player
	 * @return
	 */
	public Long getVipTime(L2PcInstance player)
	{
		return _vips.get(player.getObjectId());
	}
	
	/**
	 * Gives vip status to the target
	 * @param player
	 */
	public void addVip(L2PcInstance player)
	{
		_vips.put(player.getObjectId(), System.currentTimeMillis());
		player.sendMessage("You are now a vip player, congratulations!");
	}
	
	/**
	 * delete vip status
	 * @param player
	 */
	public void deleteVip(L2PcInstance player)
	{
		_vips.remove(player.getObjectId());
		player.sendMessage("Your vip status has dissapear!");
	}
	
	/**
	 * Saves _vips data to database and stops threads
	 */
	public void saveData()
	{
		_vipClean.cancel(false);
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			
			PreparedStatement stm = con.prepareStatement("INSERT INTO vips (charId, time) VALUES (?,?)");
			
			for (int vip : _vips.keySet())
				stm.setInt(1, vip);
			
			for (long time : _vips.values())
				stm.setLong(2, time);
			
			stm.execute();
			
			stm.close();
			con.close();
			
			_log.info(getClass().getSimpleName() + " Saved: " + _vips.size() + " data to database.");
		}
		catch (SQLException sql)
		{
			_log.log(Level.WARNING, "Couldn't save vip data to the database: " + sql);
		}
	}
	
	/**
	 * Load database to _vips
	 */
	private void loadData()
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement stm = con.prepareStatement("SELECT * FROM vips");
			ResultSet rset = stm.executeQuery();
			
			while (rset.next())
				_vips.put(rset.getInt("charId"), rset.getLong("time"));
			
			rset.close();
			stm.close();
			con.close();
			_log.info(getClass().getSimpleName() + ": Loaded " + _vips.size() + " characters with vip status.");
		}
		catch (SQLException sql)
		{
			_log.log(Level.WARNING, "Couldn't load vip data from database: " + sql);
		}
	}
	
	/**
	 * Send a message to all vips online
	 * @param message
	 */
	public void broadcastMessageToVips(String message)
	{
		Collection<L2PcInstance> players = L2World.getInstance().getAllPlayers().values();
		for (L2PcInstance player : players)
			if (isVip(player))
				player.sendMessage(message);
	}
	
	public void broadcastHtmlToVips(NpcHtmlMessage html)
	{
		Collection<L2PcInstance> players = L2World.getInstance().getAllPlayers().values();
		for (L2PcInstance player : players)
			if (isVip(player))
				player.sendPacket(html);
	}
	
	private static class SingletonHolder
	{
		protected static final VIPEngine _instance = new VIPEngine();
	}
}