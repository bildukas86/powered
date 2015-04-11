package Extensions.VoicedCommands;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class Online implements IVoicedCommandHandler
{
	
	private static final String[] VOICED_COMMANDS =
	{
		"online"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance player, String target)
	{
		if (command.equalsIgnoreCase("online"))
		{
			if (Config.ADDFAKEPLAYERSMODE)
			{
				int playerSizeOnline = L2World.getInstance().getAllPlayers().size() + Config.ADDFAKEPLAYERSNUMBER;
				player.sendMessage("There are " + playerSizeOnline + " players online!");
			}
			
			else
			{
				player.sendMessage("There are " + L2World.getInstance().getAllPlayers().size() + " players online!");
			}
		}
		return true;
		
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return VOICED_COMMANDS;
	}
}