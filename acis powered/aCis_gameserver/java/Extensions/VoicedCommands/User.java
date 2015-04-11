package Extensions.VoicedCommands;

import Extensions.Menu.Menu;
import Extensions.Menu.Security.HtmlHolder;

import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

/**
 * @author Nightwolf
 */
public class User implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{
		"menu",
		"user",
		"panel",
		"options",
		"info",
		"security"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String params)
	{
		// Main Menu
		if (command.equalsIgnoreCase("user") || command.equalsIgnoreCase("menu"))
			Menu.sendUserPage(activeChar);
		if (command.equalsIgnoreCase("panel") || command.equalsIgnoreCase("options"))
			Menu.sendUserPanelPage(activeChar);
		
		// Info Menu
		if (command.equalsIgnoreCase("info"))
			Menu.sendUserPanelInfoPage(activeChar);
		
		// Security
		if (command.equalsIgnoreCase("security"))
			HtmlHolder.showHtmlWindow(activeChar);
		
		return false;
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return VOICED_COMMANDS;
	}
}