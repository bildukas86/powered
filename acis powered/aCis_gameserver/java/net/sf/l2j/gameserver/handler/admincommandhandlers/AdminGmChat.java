package net.sf.l2j.gameserver.handler.admincommandhandlers;

import net.sf.l2j.gameserver.datatables.GmListTable;
import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.clientpackets.Say2;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;
import net.sf.l2j.gameserver.network.serverpackets.ExShowScreenMessage;

/**
 * This class handles following admin commands:
 * gmchat : sends text to all online GM's
 * gmchat_menu : same as gmchat, but displays the admin panel after chat
 */
public class AdminGmChat implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_gmchat",
		"admin_pplchat",
		"admin_gmchat_menu"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_pplchat"))
			handlePlayerChat(command, activeChar);
		else if (command.startsWith("admin_gmchat"))
			
			try
			{
				GmListTable.broadcastToGMs(new CreatureSay(0, Say2.ALLIANCE, activeChar.getName(), command.substring((command.startsWith("admin_gmchat_menu")) ? 18 : 13)));
			}
			catch (StringIndexOutOfBoundsException e)
			{
				// empty message.. ignore
			}
		
		if (command.startsWith("admin_gmchat_menu"))
			
			AdminHelpPage.showHelpPage(activeChar, "main_menu.htm");
		
		return true;
	}
	
	/**
	 * @param command
	 * @param activeChar
	 */
	private static void handlePlayerChat(String command, L2PcInstance activeChar)
	{
		try
		{
			int offset = 0;
			String text;
			if (command.contains("menu2"))
				offset = 17;
			else
				offset = 13;
			text = command.substring(offset);
			for (L2PcInstance player : L2World.getInstance().getAllPlayers().values())
				player.sendPacket(new ExShowScreenMessage(text, 10000));
		}
		catch (StringIndexOutOfBoundsException e)
		{
		}
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}