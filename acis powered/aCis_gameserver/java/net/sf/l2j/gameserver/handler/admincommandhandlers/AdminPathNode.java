package net.sf.l2j.gameserver.handler.admincommandhandlers;

import java.util.List;

import net.sf.l2j.gameserver.geoengine.PathFinding;
import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.Location;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class AdminPathNode implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_pn_info",
		"admin_find_path",
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.equals("admin_pn_info"))
		{
			final List<String> info = PathFinding.getInstance().getStat();
			if (info == null)
				activeChar.sendMessage("Not supported");
			else
				for (String msg : info)
					activeChar.sendMessage(msg);
		}
		else if (command.equals("admin_find_path"))
		{
			if (activeChar.getTarget() != null)
			{
				List<Location> path = PathFinding.getInstance().findPath(activeChar.getX(), activeChar.getY(), (short) activeChar.getZ(), activeChar.getTarget().getX(), activeChar.getTarget().getY(), (short) activeChar.getTarget().getZ(), true);
				if (path == null)
				{
					activeChar.sendMessage("No Route!");
					return true;
				}
				
				for (Location a : path)
					activeChar.sendMessage("x:" + a.getX() + " y:" + a.getY() + " z:" + a.getZ());
			}
			else
				activeChar.sendMessage("No Target!");
		}
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}