package Extensions.AutoManagers;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.Announcements;
import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.model.L2World;

public class PlayersOnlineAutoAnnounce implements Runnable
{
	
	@Override
	public void run()
	{
		Announcements.getInstance();
		Announcements.announceToAll("There are " + L2World.getInstance().getAllPlayersCount() + " players online");
		ThreadPoolManager.getInstance().scheduleGeneral(new PlayersOnlineAutoAnnounce(), Config.ANNOUNCE_ONLINE_PLAYER_EVERY * 1000);
		
	}
	
	public static PlayersOnlineAutoAnnounce getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final PlayersOnlineAutoAnnounce _instance = new PlayersOnlineAutoAnnounce();
	}
}