package Extensions.VoicedCommands;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class Version implements IVoicedCommandHandler
{
	private static String[] _voicedCommands =
	{
		"version"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		if (command.equalsIgnoreCase("version"))
		{
			activeChar.sendMessage("L2JxTreme core revision: " + Config.VER);
			activeChar.sendMessage("L2JxTreme build date:     " + Config.BUILD);
		}
		return true;
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return _voicedCommands;
	}
}