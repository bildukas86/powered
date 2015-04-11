package net.sf.l2j.gameserver.handler.admincommandhandlers;

import java.util.StringTokenizer;

import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;

/**
 * This class handles polymorph commands.
 */
public class AdminPolymorph implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_polymorph",
		"admin_unpolymorph",
		"admin_polymorph_menu",
		"admin_unpolymorph_menu"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (activeChar.isMounted())
			return false;
		
		if (command.startsWith("admin_polymorph"))
		{
			StringTokenizer st = new StringTokenizer(command);
			L2Object target = activeChar.getTarget();
			try
			{
				st.nextToken();
				String p1 = st.nextToken();
				if (st.hasMoreTokens())
				{
					String p2 = st.nextToken();
					doPolymorph(activeChar, target, p2, p1);
				}
				else
					doPolymorph(activeChar, target, p1, "npc");
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Usage: //polymorph [type] <id>");
			}
			
			if (command.contains("menu"))
				showMainPage(activeChar);
		}
		else if (command.startsWith("admin_unpolymorph"))
		{
			doUnpoly(activeChar, activeChar.getTarget());
			
			if (command.contains("menu"))
				showMainPage(activeChar);
		}
		
		return true;
	}
	
	private static void doPolymorph(L2PcInstance activeChar, L2Object target, String id, String type)
	{
		// Target the GM if no target is found.
		if (target == null)
			target = activeChar;
		
		if (!target.getPoly().setPolyInfo(type, id))
		{
			activeChar.sendPacket(SystemMessageId.APPLICANT_INFORMATION_INCORRECT);
			return;
		}
		
		activeChar.sendMessage("You polymorphed " + target.getName() + " into a " + type + " with id: " + id + ".");
	}
	
	private static void doUnpoly(L2PcInstance activeChar, L2Object target)
	{
		// Target the GM if no target is found.
		if (target == null)
			target = activeChar;
		
		if (!target.getPoly().isMorphed())
		{
			activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
			return;
		}
		
		target.getPoly().setPolyInfo(null, "1");
		
		activeChar.sendMessage("You successfully unpolymorphed " + target.getName() + ".");
	}
	
	private static void showMainPage(L2PcInstance activeChar)
	{
		AdminHelpPage.showHelpPage(activeChar, "effects_menu.htm");
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}