package Extensions.VoicedCommands;

import Extensions.Events.StriderRace;

import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class StriderRaceCMD implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{
		"striderrace_join",
		"striderrace_leave"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		if (command.equals("striderrace_join"))
		{
			if (canJoin(activeChar))
			{
				StriderRace._players.add(activeChar);
				activeChar.sendMessage("You have registered for Strider Race event.");
			}
		}
		
		if (command.equals("striderrace_leave"))
		{
			if (canLeave(activeChar))
			{
				StriderRace._players.remove(activeChar);
				activeChar.sendMessage("You have removed your participation from Strider Race event.");
			}
		}
		
		return true;
	}
	
	private static boolean canLeave(L2PcInstance player)
	{
		if (StriderRace.EventState == StriderRace.State.INACTIVE)
		{
			player.sendMessage("Strider Race event is inactive.");
			return false;
		}
		if (!StriderRace._players.contains(player))
		{
			player.sendMessage("You haven't registered for Strider Race event.");
			return false;
		}
		if (StriderRace.EventState != StriderRace.State.REGISTERING && StriderRace.EventState != StriderRace.State.INACTIVE)
		{
			player.sendMessage("You can't leave Strider Race event while the event is running.");
			return false;
		}
		
		return true;
	}
	
	private static boolean canJoin(L2PcInstance player)
	{
		if (StriderRace.EventState == StriderRace.State.INACTIVE)
		{
			player.sendMessage("Strider Race event is inactive.");
			return false;
		}
		if (StriderRace.EventState != StriderRace.State.REGISTERING && StriderRace.EventState != StriderRace.State.INACTIVE)
		{
			player.sendMessage("You can't join Strider Race event while the event is running.");
			return false;
		}
		if (StriderRace._players.contains(player))
		{
			player.sendMessage("You have already joined Strider Race event.");
			return false;
		}
		if (player.getKarma() != 0)
		{
			player.sendMessage("You can't join Strider Race event while having karma.");
			return false;
		}
		if (player.getPvpFlag() != 0)
		{
			player.sendMessage("You can't join Strider Race event while being flagged.");
			return false;
		}
		if (player.isDead())
		{
			player.sendMessage("You can't join Strider Race event while being dead.");
			return false;
		}
		if (player.isInOlympiadMode())
		{
			player.sendMessage("You can't join Strider Race event while being in olympiad mode.");
			return false;
		}
		if (player.isInJail())
		{
			player.sendMessage("You can't join Strider Race event while being in jail.");
			return false;
		}
		if (player.isCursedWeaponEquipped())
		{
			player.sendMessage("You can't join Strider Race event while having a cursed weapon equipped.");
			return false;
		}
		
		return true;
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return VOICED_COMMANDS;
	}
}