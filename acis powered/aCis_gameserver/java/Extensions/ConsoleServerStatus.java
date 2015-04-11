package Extensions;

import Extensions.Utilities.Memory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Logger;

import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.util.Util;

public class ConsoleServerStatus
{
	static final SimpleDateFormat fmt = new SimpleDateFormat("H:mm.");
	private static ConsoleServerStatus _instance;
	protected ScheduledFuture<?> _scheduledTask;
	static Logger _log = Logger.getLogger("Loader");
	
	public static ConsoleServerStatus getInstance()
	{
		if (_instance == null)
			_instance = new ConsoleServerStatus();
		return _instance;
	}
	
	private ConsoleServerStatus()
	{
		_scheduledTask = ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new Runnable()
		{
			@Override
			public void run()
			{
				Util.printSection("Server Status");
				_log.info("Server Time: " + fmt.format(new Date(System.currentTimeMillis())));
				_log.info("Players Online: " + L2World.getInstance().getAllPlayers().size());
				_log.info("Threads: " + Thread.activeCount());
				_log.info("Free Memory: " + Memory.getFreeMemory() + " MB");
				_log.info("Used memory: " + Memory.getUsedMemory() + " MB");
				Util.printSection("Server Status");
			}
		}
		
		, 1800000, 3600000);
	}
}