package net.sf.l2j.gameserver.handler.admincommandhandlers;

import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.instancemanager.games.MonsterRace;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.DeleteObject;
import net.sf.l2j.gameserver.network.serverpackets.MonRaceInfo;
import net.sf.l2j.gameserver.network.serverpackets.PlaySound;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;

public class AdminMonsterRace implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_mons"
	};
	
	protected static int state = -1;
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.equalsIgnoreCase("admin_mons"))
		{
			/*
			 * -1 0 to initialize the race 0 15322 to start race 13765 -1 in middle of race -1 0 to end the race 8003 to 8027
			 */
			int[][] codes =
			{
				{
					-1,
					0
				},
				{
					0,
					15322
				},
				{
					13765,
					-1
				},
				{
					-1,
					0
				}
			};
			MonsterRace race = MonsterRace.getInstance();
			
			if (state == -1)
			{
				state++;
				race.newRace();
				race.newSpeeds();
				activeChar.broadcastPacket(new MonRaceInfo(codes[state][0], codes[state][1], race.getMonsters(), race.getSpeeds()));
			}
			else if (state == 0)
			{
				state++;
				activeChar.broadcastPacket(SystemMessage.getSystemMessage(SystemMessageId.MONSRACE_RACE_START));
				activeChar.broadcastPacket(new PlaySound(1, "S_Race", 0, 0, 0, 0, 0));
				activeChar.broadcastPacket(new PlaySound(0, "ItemSound2.race_start", 1, 121209259, 12125, 182487, -3559));
				activeChar.broadcastPacket(new MonRaceInfo(codes[state][0], codes[state][1], race.getMonsters(), race.getSpeeds()));
				
				ThreadPoolManager.getInstance().scheduleGeneral(new RunRace(codes, activeChar), 5000);
			}
		}
		return true;
	}
	
	class RunRace implements Runnable
	{
		
		private final int[][] codes;
		private final L2PcInstance activeChar;
		
		public RunRace(int[][] pCodes, L2PcInstance pActiveChar)
		{
			codes = pCodes;
			activeChar = pActiveChar;
		}
		
		@Override
		public void run()
		{
			activeChar.broadcastPacket(new MonRaceInfo(codes[2][0], codes[2][1], MonsterRace.getInstance().getMonsters(), MonsterRace.getInstance().getSpeeds()));
			ThreadPoolManager.getInstance().scheduleGeneral(new RunEnd(activeChar), 30000);
		}
	}
	
	class RunEnd implements Runnable
	{
		private final L2PcInstance activeChar;
		
		public RunEnd(L2PcInstance pActiveChar)
		{
			activeChar = pActiveChar;
		}
		
		@Override
		public void run()
		{
			for (int i = 0; i < 8; i++)
				activeChar.broadcastPacket(new DeleteObject(MonsterRace.getInstance().getMonsters()[i]));
			
			state = -1;
		}
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}