package Extensions.AdminCommands;

import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class AdminWho implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_who"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.equalsIgnoreCase("admin_who"))
		{
			activeChar.sendMessage("SYS: current(" + L2World.getInstance().getAllPlayers().size() + "), playing(" + L2World.getInstance().getAllPlayers().size() + ")");
		}
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}