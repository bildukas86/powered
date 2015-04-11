package Extensions.VoicedCommands;

import net.sf.l2j.gameserver.datatables.MapRegionTable;
import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.instancemanager.CastleManager;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class GoToCastle implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{
		"gotocastle"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		if (command.equalsIgnoreCase("gotocastle"))
		{
			if (activeChar.getPvpFlag() > 0)
			{
				activeChar.sendMessage("You cannot use this when you are flag");
				return false;
			}
			else if (activeChar.isInOlympiadMode())
			{
				activeChar.sendMessage("You cannot use this when you are flag");
				return false;
			}
			else if (activeChar.isInDuel())
			{
				activeChar.sendMessage("You cannot use this wehn you are in duel");
				return false;
			}
			else if (activeChar.getKarma() > 0)
			{
				activeChar.sendMessage("You cannot use this when you have karma");
				return false;
			}
			else
			{
				if (CastleManager.getInstance().getCastleByOwner(activeChar.getClan()) != null)
				{
					try
					{
						wait(10000);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					activeChar.sendMessage("You are teleporting now at your castle");
					activeChar.teleToLocation(MapRegionTable.TeleportWhereType.Castle);
				}
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