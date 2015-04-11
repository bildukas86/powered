package Extensions.AdminCommands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.l2j.Config;
import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.datatables.GmListTable;
import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.clientpackets.Say2;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;

public class AdminAio implements IAdminCommandHandler
{
	private static String[] _adminCommands =
	{
		"admin_setaio",
		"admin_removeaio"
	};
	private final static Logger _log = Logger.getLogger(AdminAio.class.getName());
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_setaio"))
		{
			StringTokenizer str = new StringTokenizer(command);
			
			L2Object target = activeChar.getTarget();
			L2PcInstance player = null;
			
			if (target != null && target instanceof L2PcInstance)
				player = (L2PcInstance) target;
			else
				player = activeChar;
			
			try
			{
				str.nextToken();
				String time = str.nextToken();
				if (str.hasMoreTokens())
				{
					String playername = time;
					time = str.nextToken();
					player = L2World.getInstance().getPlayer(playername);
					doAio(activeChar, player, playername, time);
				}
				else
				{
					String playername = player.getName();
					doAio(activeChar, player, playername, time);
				}
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Usage: //setaio <char_name> [time](in days)");
			}
			
			player.broadcastUserInfo();
			
			if (player.isAio())
				return true;
		}
		else if (command.startsWith("admin_removeaio"))
		{
			StringTokenizer str = new StringTokenizer(command);
			
			L2Object target = activeChar.getTarget();
			L2PcInstance player = null;
			
			if (target instanceof L2PcInstance)
				player = (L2PcInstance) target;
			else
				player = activeChar;
			
			try
			{
				str.nextToken();
				
				if (str.hasMoreTokens())
				{
					String playername = str.nextToken();
					player = L2World.getInstance().getPlayer(playername);
					removeAio(activeChar, player, playername);
				}
				else
				{
					String playername = player.getName();
					removeAio(activeChar, player, playername);
				}
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Usage: //removeaio <char_name>");
			}
			
			player.broadcastUserInfo();
			
			if (player.isAio())
				return false;
		}
		return false;
	}
	
	public void doAio(L2PcInstance activeChar, L2PcInstance _player, String _playername, String _time)
	{
		int days = Integer.parseInt(_time);
		
		if (_player == null)
		{
			activeChar.sendMessage("Character not found.");
			return;
		}
		if (_player.isAio())
		{
			activeChar.sendMessage("Player " + _playername + " is already an AIO.");
			return;
		}
		
		if (days > 0)
		{
			_player.getStat().addExp(_player.getStat().getExpForLevel(81));
			_player.setClassId(_player.getClassId().getFirstClass().getId());
			_player.lostAioSkills();
			_player.setAio(true);
			_player.setEndTime("aio", days);
			_player.sendPacket(new CreatureSay(0, Say2.HERO_VOICE, "System", "Dear player, you are now an AIO, congratulations."));
			
			try (Connection con = L2DatabaseFactory.getInstance().getConnection())
			{
				PreparedStatement statement = con.prepareStatement("UPDATE characters SET aio=1, aio_end=? WHERE obj_id=?");
				statement.setLong(1, _player.getAioEndTime());
				statement.setInt(2, _player.getObjectId());
				statement.execute();
				statement.close();
				
				if (Config.ALLOW_AIO_NCOLOR)
					_player.getAppearance().setNameColor(Config.AIO_NCOLOR);
				
				if (Config.ALLOW_AIO_TCOLOR)
					_player.getAppearance().setTitleColor(Config.AIO_TCOLOR);
				
				_player.rewardAioSkills();
				
				if (Config.ALLOW_AIO_ITEM)
				{
					_player.getInventory().addItem("", Config.AIO_ITEMID, 1, _player, null);
					_player.getInventory().equipItem(_player.getInventory().getItemByItemId(Config.AIO_ITEMID));
					
				}
				_player.broadcastUserInfo();
				_player.sendSkillList();
				
				GmListTable.broadcastMessageToGMs("GM " + activeChar.getName() + " set an AIO status for player " + _playername + " for " + _time + " day(s)");
			}
			catch (Exception e)
			{
				_log.log(Level.WARNING, "Something went wrong, check log folder for details", e);
			}
		}
	}
	
	public void removeAio(L2PcInstance activeChar, L2PcInstance _player, String _playername)
	{
		if (!_player.isAio())
		{
			activeChar.sendMessage("Player " + _playername + " is not an AIO.");
			return;
		}
		
		_player.setAio(false);
		_player.setAioEndTime(0);
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("UPDATE characters SET Aio=0, Aio_end=0 WHERE obj_id=?");
			statement.setInt(1, _player.getObjectId());
			statement.execute();
			statement.close();
			
			_player.lostAioSkills();
			_player.removeExpAndSp(6299994999L, 366666666);
			
			if (Config.ALLOW_AIO_ITEM)
			{
				_player.getInventory().destroyItemByItemId("", Config.AIO_ITEMID, 1, _player, null);
				_player.getWarehouse().destroyItemByItemId("", Config.AIO_ITEMID, 1, _player, null);
			}
			_player.getAppearance().setNameColor(0xFFFF77);
			_player.getAppearance().setTitleColor(0xFFFF77);
			_player.broadcastUserInfo();
			_player.sendSkillList();
			
			GmListTable.broadcastMessageToGMs("GM " + activeChar.getName() + " removed Aio status of player " + _playername);
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "Something went wrong, check log folder for details", e);
		}
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return _adminCommands;
	}
}