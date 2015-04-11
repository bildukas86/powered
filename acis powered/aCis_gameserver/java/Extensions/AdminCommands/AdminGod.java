package Extensions.AdminCommands;

import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.GodSystem;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.util.Broadcast;

public class AdminGod implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_end_god"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.equals("admin_end_god"))
		{
			Broadcast.announceToOnlinePlayers("Admin " + activeChar.getName() + " ended God Period!");
			GodSystem.deleteAndUpdate();
		}
		
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}