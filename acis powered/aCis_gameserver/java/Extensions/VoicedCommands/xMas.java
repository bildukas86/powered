package Extensions.VoicedCommands;

import net.sf.l2j.gameserver.Announcements;
import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class xMas implements IVoicedCommandHandler
{
	private static final String[] _voicedCommands =
	{
		"xmas"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		Announcements.getInstance();
		Announcements.announceToAll(activeChar.getName() + ": Merry Christmas everybody , type .xmas to come to that magic place.");
		activeChar.teleToLocation(113818, -109245, -847, 0);
		activeChar.sendMessage("Merry Christmas :)");
		activeChar.setHero(true);
		return false;
		
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return _voicedCommands;
	}
}