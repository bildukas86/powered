package Extensions.VoicedCommands;

import Extensions.GearScore.GearScore;

import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class GearScoreCmd implements IVoicedCommandHandler
{
	private static String[] _voicedCommands =
	{
		"gearscore"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		if (command.equalsIgnoreCase(_voicedCommands[0]))
		{
			if (!((activeChar.getTarget() instanceof L2PcInstance)))
			{
				activeChar.sendMessage("Only players have GearScore");
				return false;
			}
			
			if (activeChar.getTarget() == activeChar || activeChar.getTarget() == null)
			{
				activeChar.sendMessage("Your overall GearScore is: " + GearScore.getGearScore(activeChar));
				return false;
			}
			
			if (activeChar.getTarget() != activeChar || activeChar.getTarget() != null)
			{
				activeChar.sendMessage(activeChar.getTarget().getName() + "'s GearScore is: " + GearScore.getGearScore((L2PcInstance) activeChar.getTarget()));
			}
		}
		return true;
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return _voicedCommands;
	}
}