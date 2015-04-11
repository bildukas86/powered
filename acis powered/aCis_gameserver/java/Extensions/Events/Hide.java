package Extensions.Events;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.clientpackets.Say2;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.util.Broadcast;
import net.sf.l2j.util.Rnd;

public class Hide
{
	
	public static final int rewardId = (Config.HIDE_EVENT_REWARD_ID);
	public static final int rewardCount = (Config.HIDE_EVENT_REWARD_COUNT);
	private static Hide _instance;
	private final int delay = (Config.HIDE_EVENT_DELAY_BEFORE_START);
	private final static int itemId = (Config.HIDE_EVENT_ITEM_WILL_DROP);
	private final static int itemCount = 1;
	public static boolean running = false;
	private static int x;
	private static int y;
	private static int z = 0;
	private final int[][] teleports =
	{
		{
			116496,
			145020,
			-2569
		},
		{
			18605,
			145378,
			-3129
		},
		{
			-83083,
			150505,
			-3134
		}
	};
	static ItemInstance item = null;
	
	public static int getX()
	{
		return x;
	}
	
	public static int getY()
	{
		return y;
	}
	
	public static int getZ()
	{
		return z;
	}
	
	public static int getItemId()
	{
		return itemId;
	}
	
	public static int getItemCount()
	{
		return itemCount;
	}
	
	public void startEvent()
	{
		running = true;
		System.out.println("Automatic Hide Event started with success.");
		int s = Rnd.get(teleports.length);
		x = teleports[s][0];
		y = teleports[s][1];
		z = teleports[s][2];
		item = new ItemInstance(Rnd.get(65535), itemId);
		L2World.getInstance().storeObject(item);
		item.setCount(itemCount);
		item.setHide(true);
		item.getPosition().setWorldPosition(x, y, z);
		item.getPosition().setWorldRegion(L2World.getInstance().getRegion(item.getPosition().getWorldPosition()));
		item.getPosition().getWorldRegion().addVisibleObject(item);
		item.setProtected(false);
		item.setIsVisible(true);
		L2World.getInstance().addVisibleObject(item, item.getPosition().getWorldRegion());
		
		Broadcast.toAllOnlinePlayers(new CreatureSay(0, Say2.ANNOUNCEMENT, "Hide Event", "Event started, Item dropped: " + item.getItem().getName() + ", find it and win!"));
		Broadcast.toAllOnlinePlayers(SystemMessage.getSystemMessage(SystemMessageId.S2_WAS_DROPPED_IN_THE_S1_REGION).addItemName(itemId).addZoneName(getX(), getY(), getZ()));
		ThreadPoolManager.getInstance().scheduleGeneral(new Check(), 60000);
	}
	
	public void checkAfterTime()
	{
		if (running == false)
			return;
		if (item.isHide())
			item.setHide(false);
		item.decayMe();
		L2World.getInstance().removeObject(item);
		cleanEvent();
		Broadcast.toAllOnlinePlayers(new CreatureSay(0, Say2.ANNOUNCEMENT, "Hide Event", "Unfortunately,none find the item , event finished!"));
	}
	
	public static void cleanEvent()
	{
		x = 0;
		y = 0;
		z = 0;
		running = false;
		if (item != null)
		{
			item.decayMe();
			L2World.getInstance().removeObject(item);
		}
		item = null;
	}
	
	private Hide()
	{
		ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new Event(), delay, delay);
		System.out.println("Automatic Hide event loaded with success.");
	}
	
	public static Hide getInstance()
	{
		if (_instance == null)
			_instance = new Hide();
		return _instance;
	}
	
	public class Check implements Runnable
	{
		@Override
		public void run()
		{
			checkAfterTime();
		}
	}
	
	public class Event implements Runnable
	{
		@Override
		public void run()
		{
			startEvent();
		}
	}
}