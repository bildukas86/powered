package Extensions.VortexEngine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;

import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.ThreadPoolManager;

public class MonthlyResetTask
{
	
	public MonthlyResetTask()
	{
	}
	
	public static void getInstance()
	{
		ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
		{
			@Override
			public void run()
			{
				try (Connection con = L2DatabaseFactory.getInstance().getConnection())
				{
					PreparedStatement statement = con.prepareStatement("UPDATE characters SET monthlyVotes=?");
					
					statement.setInt(1, 0);
					statement.execute();
					statement.close();
				}
				catch (Exception e)
				{
				}
			}
		}, getValidationTime());
	}
	
	private static long getValidationTime()
	{
		Calendar cld = Calendar.getInstance();
		cld.set(5, 1);
		long time = cld.getTimeInMillis();
		if ((System.currentTimeMillis() - time) <= 0L)
		{
			long delay = cld.getTimeInMillis() - System.currentTimeMillis();
			return delay;
		}
		return 0L;
	}
}