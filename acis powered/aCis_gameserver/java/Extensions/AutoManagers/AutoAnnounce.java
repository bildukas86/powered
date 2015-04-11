package Extensions.AutoManagers;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.Announcements;
import net.sf.l2j.gameserver.ThreadPoolManager;

public class AutoAnnounce
{
	@SuppressWarnings("synthetic-access")
	private AutoAnnounce()
	{
		ThreadPoolManager.getInstance().scheduleAiAtFixedRate(new AnnounceDat(), Config.AUTO_ANNOUNCE_DELAY * 60000, Config.AUTO_ANNOUNCE_DELAY * 60000);
	}
	
	public static void getInstance()
	{
		new AutoAnnounce();
	}
	
	private class AnnounceDat implements Runnable
	{
		@Override
		public void run()
		{
			Announcements.announceToAll(Config.AUTO_ANNOUNCE_TEXT);
		}
	}
}