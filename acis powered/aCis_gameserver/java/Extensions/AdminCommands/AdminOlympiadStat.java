package Extensions.AdminCommands;

import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.olympiad.Olympiad;

public class AdminOlympiadStat implements IAdminCommandHandler
{
	private static String[] ADMIN_COMMANDS =
	{
		"admin_olympiad_stat"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_olympiad_stat"))
		{
			L2Object target = activeChar.getTarget();
			
			if (target instanceof L2PcInstance)
			{
				L2PcInstance player = (L2PcInstance) target;
				
				if (!player.isNoble())
				{
					activeChar.sendMessage("Oops! Your target is not a Noble!");
					return false;
				}
				activeChar.sendMessage("Match(s): " + Olympiad.getInstance().getCompetitionDone(player.getObjectId()));
				activeChar.sendMessage("Points: " + Olympiad.getInstance().getNoblePoints(player.getObjectId()));
				return true;
			}
			activeChar.sendMessage("Usage: //olympiad_stat <target>");
			return false;
		}
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}