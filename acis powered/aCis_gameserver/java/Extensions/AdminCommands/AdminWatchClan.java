package Extensions.AdminCommands;

import net.sf.l2j.gameserver.datatables.ClanTable;
import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.L2Clan;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class AdminWatchClan implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_watchclan",
		"admin_stopwatch",
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith(ADMIN_COMMANDS[0]) || command.startsWith(ADMIN_COMMANDS[1]))
		{
			L2Clan clan;
			String clanName;
			try
			{
				clanName = command.substring(16);
				clan = ClanTable.getInstance().getClanByName(clanName);
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Incorrect clan name. Usage: //watchclan <clanname>");
				return false;
			}
			
			if (command.startsWith(ADMIN_COMMANDS[0]))
			{
				
				if (clan.getWatchers().contains(activeChar))
				{
					activeChar.sendMessage("You already watch " + clanName + " clan.");
					return false;
				}
				
				clan.addWatcher(activeChar);
				activeChar.sendMessage("You are now a watcher in " + clanName + " clan.");
			}
			
			else if (command.startsWith(ADMIN_COMMANDS[1]))
			{
				if (!clan.getWatchers().contains(activeChar))
				{
					activeChar.sendMessage("You don't watch even " + clanName + " clan.");
					return false;
				}
				
				clan.removeWatcher(activeChar);
				activeChar.sendMessage("You are not a watcher anymore in " + clanName + " clan.");
			}
		}
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}