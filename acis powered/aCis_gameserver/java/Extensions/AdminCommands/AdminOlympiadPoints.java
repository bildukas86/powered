package Extensions.AdminCommands;

import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.olympiad.Olympiad;
import net.sf.l2j.gameserver.templates.StatsSet;

public class AdminOlympiadPoints implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_addolypoints",
		"admin_removeolypoints",
		"admin_setolypoints",
		"admin_getolypoints"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_addolypoints"))
		{
			try
			{
				String val = command.substring(19);
				L2Object target = activeChar.getTarget();
				L2PcInstance player = null;
				if (target instanceof L2PcInstance)
				{
					player = (L2PcInstance) target;
					if (player.isNoble())
					{
						StatsSet playerStat = Olympiad.getNobleStats(player.getObjectId());
						if (playerStat == null)
						{
							activeChar.sendMessage("Oops! This player hasn't played on Olympiad yet!");
							return false;
						}
						int oldpoints = Olympiad.getInstance().getNoblePoints(player.getObjectId());
						int points = oldpoints + Integer.parseInt(val);
						if (points > 100)
						{
							activeChar.sendMessage("You can't set more than 100 or less than 0 Olympiad points!");
							return false;
						}
						playerStat.set("olympiad_points", points);
						
						activeChar.sendMessage("Player " + player.getName() + " now has " + points + " Olympiad points.");
					}
					else
					{
						activeChar.sendMessage("Oops! This player is not noblesse!");
						return false;
					}
				}
				else
				{
					activeChar.sendMessage("Usage: target a player and write the amount of points you would like to add.");
					activeChar.sendMessage("Example: //addolypoints 10");
					activeChar.sendMessage("However, keep in mind that you can't have less than 0 or more than 100 points.");
				}
			}
			catch (StringIndexOutOfBoundsException e)
			{
				activeChar.sendMessage("Usage: //addolypoints <points>");
			}
		}
		else if (command.startsWith("admin_removeolypoints"))
		{
			try
			{
				String val = command.substring(22);
				L2Object target = activeChar.getTarget();
				L2PcInstance player = null;
				if (target instanceof L2PcInstance)
				{
					player = (L2PcInstance) target;
					if (player.isNoble())
					{
						StatsSet playerStat = Olympiad.getNobleStats(player.getObjectId());
						if (playerStat == null)
						{
							activeChar.sendMessage("Oops! This player hasn't played on Olympiad yet!");
							return false;
						}
						int oldpoints = Olympiad.getInstance().getNoblePoints(player.getObjectId());
						int points = oldpoints - Integer.parseInt(val);
						if (points < 0)
							points = 0;
						playerStat.set("olympiad_points", points);
						activeChar.sendMessage("Player " + player.getName() + " now has " + points + " Olympiad points.");
					}
					else
					{
						activeChar.sendMessage("Oops! This player is not noblesse!");
						return false;
					}
				}
				else
				{
					activeChar.sendMessage("Usage: target a player and write the amount of points you would like to remove.");
					activeChar.sendMessage("Example: //removeolypoints 10");
					activeChar.sendMessage("However, keep in mind that you can't have less than 0 or more than 100 points.");
				}
			}
			catch (StringIndexOutOfBoundsException e)
			{
				activeChar.sendMessage("Usage: //removeolypoints points");
			}
		}
		else if (command.startsWith("admin_setolypoints"))
		{
			try
			{
				String val = command.substring(19);
				L2Object target = activeChar.getTarget();
				L2PcInstance player = null;
				if (target instanceof L2PcInstance)
				{
					player = (L2PcInstance) target;
					if (player.isNoble())
					{
						StatsSet playerStat = Olympiad.getNobleStats(player.getObjectId());
						if (playerStat == null)
						{
							activeChar.sendMessage("Oops! This player hasn't played on Olympiad yet!");
							return false;
						}
						if (Integer.parseInt(val) < 1 && Integer.parseInt(val) > 100)
						{
							activeChar.sendMessage("You can't set more than 100 or less than 0 Olympiad points! or lower then 0");
							return false;
						}
						playerStat.set("olympiad_points", Integer.parseInt(val));
						activeChar.sendMessage("Player " + player.getName() + " now has " + Integer.parseInt(val) + " Olympiad points.");
					}
					else
					{
						activeChar.sendMessage("Oops! This player is not noblesse!");
						return false;
					}
				}
				else
				{
					activeChar.sendMessage("Usage: target a player and write the amount of points you would like to set.");
					activeChar.sendMessage("Example: //setolypoints 10");
					activeChar.sendMessage("However, keep in mind that you can't have less than 0 or more than 100 points.");
				}
			}
			catch (StringIndexOutOfBoundsException e)
			{
				activeChar.sendMessage("Usage: //setolypoints <points>");
			}
		}
		else if (command.startsWith("admin_getolypoints"))
		{
			try
			{
				L2Object target = activeChar.getTarget();
				L2PcInstance player = null;
				if (target instanceof L2PcInstance)
				{
					player = (L2PcInstance) target;
					if (player.isNoble())
					{
						activeChar.sendMessage(">=========>>" + player.getName() + "<<=========");
						activeChar.sendMessage("   Match(s):  " + Olympiad.getInstance().getCompetitionDone(player.getObjectId()));
						activeChar.sendMessage("   Win(s):    " + Olympiad.getInstance().getCompetitionWon(player.getObjectId()));
						activeChar.sendMessage("   Defeat(s): " + Olympiad.getInstance().getCompetitionLost(player.getObjectId()));
						activeChar.sendMessage("   Point(s):  " + Olympiad.getInstance().getNoblePoints(player.getObjectId()));
						activeChar.sendMessage(">=========>>" + player.getName() + "<<=========");
					}
					else
					{
						activeChar.sendMessage("Oops! This player is not noblesse!");
						return false;
					}
				}
				else
					activeChar.sendMessage("You must target a player to use the command.");
			}
			catch (StringIndexOutOfBoundsException e)
			{
				activeChar.sendMessage("Usage: //getolypoints");
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