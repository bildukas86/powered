package Extensions.Protection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.clientpackets.Say2;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;

public class ProtectionIP
{
	public static void onEnterWorld(L2PcInstance player)
	{
		String last = "";
		String curr = "";
		try
		{
			last = LastIP(player);
			curr = player.getClient().getConnection().getInetAddress().getHostAddress();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		player.sendPacket(new CreatureSay(1, Say2.PARTY, "SYSTEM", "Your IP is: " + curr));
		if (!last.equalsIgnoreCase(curr))
		{
			player.sendPacket(new CreatureSay(1, Say2.PARTY, "SYSTEM", "Your last IP was: " + last + " and current: " + curr));
			player.sendPacket(new CreatureSay(1, Say2.PARTY, "SYSTEM", "Your IP updated if this was not you its recommended to change your password."));
		}
		UpdateLastIP(player, player.getAccountName());
	}
	
	public static String LastIP(L2PcInstance player)
	{
		String lastIp = "";
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT * FROM accounts WHERE login=?");
			statement.setString(1, player.getAccountName());
			ResultSet rset = statement.executeQuery();
			while (rset.next())
			{
				lastIp = rset.getString("lastIP");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return lastIp;
	}
	
	public static void UpdateLastIP(L2PcInstance player, String user)
	{
		String address = player.getClient().getConnection().getInetAddress().getHostAddress();
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("UPDATE accounts SET lastIP=? WHERE login=?");
			statement.setString(1, address);
			statement.setString(2, user);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
}