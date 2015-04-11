package net.sf.l2j.gameserver.handler.admincommandhandlers;

import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.SocialAction;

public class AdminMassHero implements IAdminCommandHandler
{
	private static String[] ADMIN_COMMANDS =
	{
		"admin_masshero"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (activeChar == null)
			return false;
		
		if (command.startsWith("admin_masshero"))
		{
			for (L2PcInstance player : L2World.getInstance().getAllPlayers().values())
			{
				/* Check to see if the player already is Hero and if aren't in Olympiad Mode */
				if (!player.isHero() || !player.isInOlympiadMode())
				{
					player.setHero(true);
					player.sendMessage("Admin is rewarding all online players with Hero Status.");
					player.broadcastPacket(new SocialAction(player, 16));
					player.broadcastUserInfo();
				}
			}
		}
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}