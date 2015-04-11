package net.sf.l2j.gameserver.handler.admincommandhandlers;

import java.util.logging.Logger;

import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.olympiad.Olympiad;

public class AdminOlympiad implements IAdminCommandHandler
{
	private static Logger _log = Logger.getLogger(AdminOlympiad.class.getName());
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_endoly",
		"admin_manualhero",
		"admin_saveoly",
		"admin_sethero",
		"admin_setnoble"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_endoly"))
		{
			try
			{
				Olympiad.getInstance().manualSelectHeroes();
			}
			catch (Exception e)
			{
				_log.warning("An error occured while ending olympiad: " + e);
			}
			activeChar.sendMessage("Heroes have been formed.");
		}
		else if (command.startsWith("admin_manualhero") || command.startsWith("admin_sethero"))
		{
			L2PcInstance target = null;
			if (activeChar.getTarget() instanceof L2PcInstance)
				target = (L2PcInstance) activeChar.getTarget();
			else
				target = activeChar;
			
			target.setHero(!target.isHero());
			target.broadcastUserInfo();
			activeChar.sendMessage("You have modified " + target.getName() + "'s hero status.");
		}
		else if (command.startsWith("admin_saveoly"))
		{
			Olympiad.getInstance().saveOlympiadStatus();
			activeChar.sendMessage("Olympiad stats have been saved.");
		}
		else if (command.startsWith("admin_setnoble"))
		{
			L2PcInstance target = null;
			if (activeChar.getTarget() instanceof L2PcInstance)
				target = (L2PcInstance) activeChar.getTarget();
			else
				target = activeChar;
			
			target.setNoble(!target.isNoble(), true);
			activeChar.sendMessage("You have modified " + target.getName() + "'s noble status.");
		}
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}