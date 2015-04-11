/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.l2j.gameserver.handler.admincommandhandlers;

import classbalancer.ClassBalanceBBSManager;
import classbalancer.ClassBalanceManager;

import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

/**
 * This class handles following admin commands: - target name = sets player with respective name as target
 */
public class AdminClassBalancer implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_classbalancer",
		"admin_loadclassbalancer",
		"admin_updateclassbalancer"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_classbalancer"))
		{
			ClassBalanceBBSManager.getInstance().parseCmd(command, activeChar);
		}
		else if (command.equalsIgnoreCase("admin_loadclassbalancer"))
		{
			ClassBalanceManager.getInstance().loadBalances();
			activeChar.sendMessage("Class balances has successfully been loaded!");
		}
		else if (command.equalsIgnoreCase("admin_updateclassbalancer"))
		{
			ClassBalanceManager.getInstance().updateBalances();
			activeChar.sendMessage("Class balances has successfully been updated!");
		}
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}