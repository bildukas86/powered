package Extensions.AdminCommands;

import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ConfirmDlg;
import net.sf.l2j.gameserver.util.Util;
import net.sf.l2j.util.Rnd;

public class AdminRecallAll implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_recallall"
	};
	public static boolean isAdminSummoning = false;
	public static int x = 0;
	public static int y = 0;
	public static int z = 0;
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_recallall"))
		{
			
			isAdminSummoning = true;
			
			for (L2PcInstance player : L2World.getInstance().getAllPlayers().values())
			{
				try
				{
					if (!L2PcInstance.checkSummonTargetStatus(player, activeChar) || player.isAlikeDead() || player.isInStoreMode() || player.isRooted() || player.isInCombat() || player.isInOlympiadMode() || player.isFestivalParticipant())
						continue;
					
					x = activeChar.getX() + Rnd.get(1, 20);
					y = activeChar.getY() + Rnd.get(1, 20);
					z = activeChar.getZ() + Rnd.get(1, 20);
					
					if (!Util.checkIfInRange(0, activeChar, player, false))
					{
						ThreadPoolManager.getInstance().scheduleGeneral(new Restore(), 15000);
						ConfirmDlg confirm = new ConfirmDlg(SystemMessageId.S1_WISHES_TO_SUMMON_YOU_FROM_S2_DO_YOU_ACCEPT.getId());
						confirm.addString(activeChar.getName());
						confirm.addZoneName(activeChar.getX(), activeChar.getY(), activeChar.getZ());
						confirm.addTime(15000);
						confirm.addRequesterId(activeChar.getObjectId());
						player.sendPacket(confirm);
					}
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	class Restore implements Runnable
	{
		@Override
		public void run()
		{
			x = 0;
			y = 0;
			z = 0;
			isAdminSummoning = false;
		}
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}