package Extensions.AdminCommands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class AdminLocations implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_loc",
		"admin_reset_loc"
	};
	
	public static List<String> _locs = new ArrayList<>();
	private int count = 0;
	private int loop = 0;
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		
		if (command.equals("admin_reset_loc"))
		{
			_locs.clear();
			activeChar.sendMessage("Locations deleted.");
		}
		
		else if (command.equals("admin_loc"))
		{
			count = 0;
			_locs.add("" + activeChar.getX());
			_locs.add("" + activeChar.getY());
			_locs.add("" + activeChar.getZ());
			
			String fname = "data/CustomLogs/Locations/locations.txt";
			File file = new File(fname);
			try
			{
				file.createNewFile();
				FileWriter fstream = new FileWriter(fname);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write("private int [] x = {};\r\nprivate int [] y = {};\r\nprivate int [] z = {};\r\n\r\n");
				for (String loc : _locs)
				{
					loop++;
					out.write(getCoord(loop, count, loc));
				}
				out.close();
				activeChar.sendMessage("Location saved.");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
	private String getCoord(int a, int e, String o)
	{
		switch (a)
		{
			case 1:
				return "x[" + e + "] = " + o + ";\r\n";
			case 2:
				return "y[" + e + "] = " + o + ";\r\n";
			case 3:
				count++;
				loop = 0;
				return "z[" + e + "] = " + o + ";\r\n\r\n";
		}
		return null;
	}
}