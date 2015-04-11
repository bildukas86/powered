package Extensions.VoicedCommands;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.ai.CtrlIntention;
import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;
import net.sf.l2j.gameserver.network.serverpackets.SetupGauge;
import net.sf.l2j.gameserver.util.Broadcast;

public class FarmPvp implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{
		"farm1",
		"farm2",
		"farm3",
		"pvp1",
		"pvp2",
		"pvp3",
		"giran",
		"dion",
		"aden",
		"goddard",
		"gludio",
		"rune",
		"heine",
		"schuttgart",
		"oren"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		int placex;
		int placey;
		int placez;
		String message;
		
		if (activeChar.isInJail())
		{
			activeChar.sendMessage("Sorry,you are in Jail!");
			return false;
		}
		if (activeChar.getPvpFlag() > 0)
		{
			activeChar.sendMessage("Cannot use while have pvp flag.");
			return false;
		}
		if (activeChar.getKarma() > 0)
		{
			activeChar.sendMessage("Cannot use while have karma.");
			return false;
		}
		if (activeChar.isAlikeDead())
		{
			activeChar.sendMessage("Cannot use while in fake death mode.");
			return false;
		}
		else if (activeChar.isDead())
		{
			activeChar.sendMessage("Sorry,Dead player can't teleport.");
			return false;
		}
		else if (activeChar.isFakeDeath())
		{
			activeChar.sendMessage("Sorry,on fake death mode can't use this.");
			return false;
		}
		if (activeChar.isInCombat())
		{
			activeChar.sendMessage("Cannot use while in combat.");
			return false;
		}
		
		if (activeChar.isInOlympiadMode())
		{
			activeChar.sendMessage("Sorry,you are in the Olympiad now.");
			return false;
		}
		if (activeChar.isInDuel())
		{
			activeChar.sendMessage("Sorry,you are in a duel!");
			return false;
		}
		if (activeChar.isDead())
		{
			activeChar.sendMessage("Sorry,you are dead!");
			return false;
		}
		if (activeChar.isCastingNow())
		{
			activeChar.sendMessage("Sorry,you casting now!");
			return false;
		}
		if (activeChar.inObserverMode())
		{
			activeChar.sendMessage("Sorry,you are in the observation mode.");
			return false;
		}
		if (activeChar.isFestivalParticipant())
		{
			activeChar.sendMessage("Sorry,you are in a festival.");
			return false;
		}
		if (!Config.KARMA_PLAYER_CAN_USE_GK && activeChar.getKarma() > 0)
		{
			activeChar.sendMessage("Sorry,you are PK");
			return false;
		}
		
		if (command.equalsIgnoreCase("farm1") && Config.ALLOW_FARM1_COMMAND)
		{
			placex = Config.FARM1_X;
			placey = Config.FARM1_Y;
			placez = Config.FARM1_Z;
			message = Config.FARM1_CUSTOM_MESSAGE;
		}
		else if (command.equalsIgnoreCase("farm2") && Config.ALLOW_FARM2_COMMAND)
		{
			placex = Config.FARM2_X;
			placey = Config.FARM2_Y;
			placez = Config.FARM2_Z;
			message = Config.FARM2_CUSTOM_MESSAGE;
		}
		else if (command.equalsIgnoreCase("farm3") && Config.ALLOW_FARM3_COMMAND)
		{
			placex = Config.FARM3_X;
			placey = Config.FARM3_Y;
			placez = Config.FARM3_Z;
			message = Config.FARM3_CUSTOM_MESSAGE;
		}
		else if (command.equalsIgnoreCase("pvp1") && Config.ALLOW_PVP1_COMMAND)
		{
			placex = Config.PVP1_X;
			placey = Config.PVP1_Y;
			placez = Config.PVP1_Z;
			message = Config.PVP1_CUSTOM_MESSAGE;
		}
		else if (command.equalsIgnoreCase("pvp2") && Config.ALLOW_PVP2_COMMAND)
		{
			placex = Config.PVP2_X;
			placey = Config.PVP2_Y;
			placez = Config.PVP2_Z;
			message = Config.PVP2_CUSTOM_MESSAGE;
		}
		else if (command.equalsIgnoreCase("pvp3") && Config.ALLOW_PVP3_COMMAND)
		{
			placex = Config.PVP3_X;
			placey = Config.PVP3_Y;
			placez = Config.PVP3_Z;
			message = Config.PVP3_CUSTOM_MESSAGE;
		}
		else if (command.equalsIgnoreCase("giran") && Config.ALLOW_GIRAN_COMMAND)
		{
			placex = Config.GIRAN_X;
			placey = Config.GIRAN_Y;
			placez = Config.GIRAN_Z;
			message = Config.GIRAN_CUSTOM_MESSAGE;
		}
		else if (command.equalsIgnoreCase("dion") && Config.ALLOW_DION_COMMAND)
		{
			placex = Config.DION_X;
			placey = Config.DION_Y;
			placez = Config.DION_Z;
			message = Config.DION_CUSTOM_MESSAGE;
		}
		else if (command.equalsIgnoreCase("aden") && Config.ALLOW_ADEN_COMMAND)
		{
			placex = Config.ADEN_X;
			placey = Config.ADEN_Y;
			placez = Config.ADEN_Z;
			message = Config.ADEN_CUSTOM_MESSAGE;
		}
		else if (command.equalsIgnoreCase("goddard") && Config.ALLOW_GODDARD_COMMAND)
		{
			placex = Config.GODDARD_X;
			placey = Config.GODDARD_Y;
			placez = Config.GODDARD_Z;
			message = Config.GODDARD_CUSTOM_MESSAGE;
		}
		else if (command.equalsIgnoreCase("gludio") && Config.ALLOW_GLUDIO_COMMAND)
		{
			placex = Config.GLUDIO_X;
			placey = Config.GLUDIO_Y;
			placez = Config.GLUDIO_Z;
			message = Config.GLUDIO_CUSTOM_MESSAGE;
		}
		else if (command.equalsIgnoreCase("rune") && Config.ALLOW_RUNE_COMMAND)
		{
			placex = Config.RUNE_X;
			placey = Config.RUNE_Y;
			placez = Config.RUNE_Z;
			message = Config.RUNE_CUSTOM_MESSAGE;
		}
		else if (command.equalsIgnoreCase("heine") && Config.ALLOW_HEINE_COMMAND)
		{
			placex = Config.HEINE_X;
			placey = Config.HEINE_Y;
			placez = Config.HEINE_Z;
			message = Config.HEINE_CUSTOM_MESSAGE;
		}
		else if (command.equalsIgnoreCase("schuttgart") && Config.ALLOW_SCHUTTGART_COMMAND)
		{
			placex = Config.SCHUTTGART_X;
			placey = Config.SCHUTTGART_Y;
			placez = Config.SCHUTTGART_Z;
			message = Config.SCHUTTGART_CUSTOM_MESSAGE;
		}
		else if (command.equalsIgnoreCase("oren") && Config.ALLOW_OREN_COMMAND)
		{
			placex = Config.OREN_X;
			placey = Config.OREN_Y;
			placez = Config.OREN_Z;
			message = Config.OREN_CUSTOM_MESSAGE;
		}
		else
			return false;
		
		activeChar.getAI().setIntention(CtrlIntention.IDLE);
		activeChar.setTarget(activeChar);
		activeChar.disableAllSkills();
		
		MagicSkillUse msk = new MagicSkillUse(activeChar, 1050, 1, 15000, 0);
		Broadcast.toSelfAndKnownPlayersInRadius(activeChar, msk, 810000/* 900 */);
		SetupGauge sg = new SetupGauge(SetupGauge.BLUE, 15000);
		activeChar.sendPacket(sg);
		sg = null;
		
		// End SoE Animation section
		activeChar.setSkillCast(ThreadPoolManager.getInstance().scheduleGeneral(new teleportTask(activeChar, placex, placey, placez, message), 15000));
		
		return true;
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return VOICED_COMMANDS;
	}
	
	class teleportTask implements Runnable
	{
		private final L2PcInstance _activeChar;
		private final int _x;
		private final int _y;
		private final int _z;
		private final String _message;
		
		teleportTask(L2PcInstance activeChar, int x, int y, int z, String message)
		{
			_activeChar = activeChar;
			_x = x;
			_y = y;
			_z = z;
			_message = message;
		}
		
		@Override
		public void run()
		{
			if (_activeChar == null)
				return;
			
			try
			{
				_activeChar.teleToLocation(_x, _y, _z, 0);
				_activeChar.sendMessage(_message);
				_activeChar.enableAllSkills();
			}
			catch (Throwable e)
			{
				e.printStackTrace();
			}
		}
	}
}