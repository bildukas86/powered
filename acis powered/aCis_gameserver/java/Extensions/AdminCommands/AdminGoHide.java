package Extensions.AdminCommands;

import Extensions.Events.Hide;

import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class AdminGoHide implements IAdminCommandHandler
{
	private static String[] ADMIN_COMMANDS =
	{
		"admin_gohide"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_gohide"))
		{
			if (activeChar == null)
				return false;
			
			if (Hide.running == false)
			{
				activeChar.sendMessage("Event is not in progress");
				return false;
			}
			
			int x = Hide.getX(), y = Hide.getY(), z = Hide.getZ();
			activeChar.teleToLocation(x, y, z, 0);
		}
		
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}