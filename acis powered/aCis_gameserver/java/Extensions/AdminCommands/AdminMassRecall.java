package Extensions.AdminCommands;

import net.sf.l2j.gameserver.ai.CtrlIntention;
import net.sf.l2j.gameserver.datatables.ClanTable;
import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.L2Clan;
import net.sf.l2j.gameserver.model.L2Party;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class AdminMassRecall implements IAdminCommandHandler
{
	private static String[] ADMIN_COMMANDS =
	{
		"admin_recallclan",
		"admin_recallparty",
		"admin_recallally"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_recallclan"))
		{
			try
			{
				String val = command.substring(17).trim();
				
				L2Clan clan = ClanTable.getInstance().getClanByName(val);
				if (clan == null)
				{
					activeChar.sendMessage("This clan doesn't exists.");
					return false;
				}
				
				L2PcInstance[] m = clan.getOnlineMembers();
				for (int i = 0; i < m.length; i++)
				{
					Teleport(m[i], activeChar.getX(), activeChar.getY(), activeChar.getZ(), "Admin is teleporting you");
				}
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Error in recallclan command.");
			}
		}
		else if (command.startsWith("admin_recallally"))
		{
			try
			{
				String val = command.substring(17).trim();
				
				L2Clan clan = ClanTable.getInstance().getClanByName(val);
				if (clan == null)
				{
					activeChar.sendMessage("This clan doesn't exists.");
					return false;
				}
				
				int ally = clan.getAllyId();
				
				if (ally == 0)
				{
					L2PcInstance[] m = clan.getOnlineMembers();
					for (int i = 0; i < m.length; i++)
					{
						Teleport(m[i], activeChar.getX(), activeChar.getY(), activeChar.getZ(), "Admin is teleporting you");
					}
				}
				else
				{
					for (L2Clan aclan : ClanTable.getInstance().getClans())
					{
						if (aclan.getAllyId() == ally)
						{
							L2PcInstance[] m = aclan.getOnlineMembers();
							for (int i = 0; i < m.length; i++)
							{
								Teleport(m[i], activeChar.getX(), activeChar.getY(), activeChar.getZ(), "Admin is teleporting you");
							}
						}
					}
				}
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Error in recallally command.");
			}
		}
		else if (command.startsWith("admin_recallparty"))
		{
			try
			{
				String val = command.substring(18).trim();
				
				L2PcInstance player = L2World.getInstance().getPlayer(val);
				if (player == null)
				{
					activeChar.sendMessage("Target error.");
					return false;
				}
				if (!player.isInParty())
				{
					activeChar.sendMessage("Player is not in party.");
					return false;
				}
				L2Party p = player.getParty();
				
				for (L2PcInstance ppl : p.getPartyMembers())
				{
					Teleport(ppl, activeChar.getX(), activeChar.getY(), activeChar.getZ(), "Admin is teleporting you");
				}
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Error in recallparty command.");
			}
		}
		return true;
	}
	
	private static void Teleport(L2PcInstance player, int X, int Y, int Z, String Message)
	{
		player.sendMessage(Message);
		player.getAI().setIntention(CtrlIntention.IDLE);
		player.teleToLocation(X, Y, Z, 0);
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}