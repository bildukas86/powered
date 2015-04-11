package Extensions.AutoManagers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.Announcements;
import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.clientpackets.Say2;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;
import net.sf.l2j.gameserver.network.serverpackets.InventoryUpdate;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;

public class AutoRewarder implements Runnable
{
	protected static final Logger _log = Logger.getLogger(AutoRewarder.class.getName());
	public static int rewardTime = Config.AUTO_REWARDER_SCHEDULE; // schedule in minutes.
	public static int[][] rewards = Config.AUTO_REWARDER_REWARDS; // rewards.
	public static int dualboxAllowed = Config.AUTO_REWARDER_DUALBOX_ALLOWED;
	private static Map<String, Integer> playerIps = new HashMap<>();
	
	@Override
	public void run()
	{
		schedule(rewardTime);
		rewardOnlinePlayers();
	}
	
	public void rewardOnlinePlayers()
	{
		Collection<L2PcInstance> _players = L2World.getInstance().getAllPlayers().values();
		for (L2PcInstance players : _players)
		{
			boolean canReward = false;
			String pIp = players.getClient().getConnection().getInetAddress().getHostAddress();
			if (playerIps.containsKey(pIp))
			{
				int count = playerIps.get(pIp);
				if (count < dualboxAllowed)
				{
					playerIps.remove(pIp);
					playerIps.put(pIp, count + 1);
					canReward = true;
				}
			}
			else
			{
				canReward = true;
				playerIps.put(pIp, 1);
			}
			
			if (canReward)
			{
				Announcements.announceToAll("All players have been rewarded.");
				players.sendPacket(new CreatureSay(0, Say2.TRADE, "[AR]", "You have been rewarded."));
				autoRewards(players, getAutoRewards());
			}
			else
			{
				players.sendMessage("Already " + dualboxAllowed + " character(s) of your ip have been rewarded, so this character won't be rewarded.");
			}
		}
		playerIps.clear();
	}
	
	public static final void autoRewards(L2PcInstance player, int[][] reward)
	{
		if (player == null || !player.isOnline() || reward == null)
			return;
		
		try
		{
			final InventoryUpdate iu = new InventoryUpdate();
			for (int[] it : reward)
			{
				if (it == null || it.length != 2)
					continue;
				
				final ItemInstance item = player.getInventory().addItem("Init", it[0], it[1], player, null);
				if (item == null)
					continue;
				
				iu.addModifiedItem(item);
				player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.EARNED_S2_S1_S).addItemName(it[0]).addNumber(it[1]));
			}
			player.sendPacket(iu);
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	public final static int[][] getAutoRewards()
	{
		return rewards;
	}
	
	protected ThreadPoolManager thread;
	private AutoRewarder task;
	
	protected void schedule(int time)
	{
		thread.scheduleGeneral(task, time * 1000 * 60);
	}
	
	public static AutoRewarder getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final AutoRewarder _instance = new AutoRewarder();
	}
}