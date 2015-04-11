package Extensions.VoicedCommands;

import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class Res implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{
		"res"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		if (command.equalsIgnoreCase("res"))
		{
			if (!activeChar.isAlikeDead())
			{
				activeChar.sendMessage("You cannot be ressurected while alive.");
				return false;
			}
			if (activeChar.isInOlympiadMode())
			{
				activeChar.sendMessage("You cannot use this feature during olympiad.");
				return false;
			}
			if (activeChar.isInJail())
			{
				activeChar.sendMessage("You cannot use this command while you are in Jail.");
				return false;
			}
			if (activeChar.isInDuel())
			{
				activeChar.sendMessage("You cannot use this feature during Duel.");
				return false;
			}
			if (activeChar.isFestivalParticipant())
			{
				activeChar.sendMessage("You cannot use this feature during a Festival.");
				return false;
			}
			if (activeChar.inObserverMode())
			{
				activeChar.sendMessage("You cannot use this feature during Observer Mode.");
				return false;
			}
			if (activeChar.getInventory().getItemByItemId(57) == null)
			{
				activeChar.sendMessage("You need 5000 Adena to use the .res");
				return false;
			}
			{
				activeChar.sendMessage("You have been ressurected!");
				activeChar.getInventory().destroyItemByItemId("root", 57, 5000, activeChar, activeChar.getTarget());
				activeChar.doRevive();
				activeChar.broadcastUserInfo();
				activeChar.sendMessage("Good Fight !");
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