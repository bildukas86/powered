package Extensions.CCB.Manager;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.l2j.Config;
import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.cache.HtmCache;
import net.sf.l2j.gameserver.communitybbs.Manager.BaseBBSManager;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class PasswordBBSManager extends BaseBBSManager
{
	private static final Logger _log = Logger.getLogger(PasswordBBSManager.class.getName());
	
	public static PasswordBBSManager getInstance()
	{
		return SingletonHolder._instance;
	}
	
	@Override
	public void parseCmd(String command, L2PcInstance activeChar)
	{
		if (command.equals("_bbsPassPanel"))
		{
			if (Config.ENABLE_BBS_PASS_CHANGE)
			{
				separateAndSend(HtmCache.getInstance().getHtm("data/html/CommunityBoard/Custom/passChange.htm"), activeChar);
			}
			else
			{
				separateAndSend(HtmCache.getInstance().getHtm("data/html/CommunityBoard/Custom/functionDisabled.htm"), activeChar);
			}
		}
		else if (command.startsWith("_bbsChangePass"))
		{
			StringTokenizer st = new StringTokenizer(command);
			st.nextToken();
			
			String currPass = null;
			String newPass = null;
			String repeatNewPass = null;
			try
			{
				if (st.hasMoreTokens())
				{
					currPass = st.nextToken();
					newPass = st.nextToken();
					repeatNewPass = st.nextToken();
				}
				else
				{
					activeChar.sendMessage("Please fill in all the blank fields before requesting for a password change.");
					return;
				}
				changePassword(currPass, newPass, repeatNewPass, activeChar);
			}
			catch (StringIndexOutOfBoundsException e)
			{
				activeChar.sendMessage("Something went wrong please contact with server's administrator with this error.");
				_log.log(Level.WARNING, "PasswordChangeFunction: Something went wrong for " + activeChar.getName());
				e.getStackTrace();
				return;
			}
		}
		else
		{
			super.parseCmd(command, activeChar);
		}
	}
	
	public static void changePassword(String currPass, String newPass, String repeatNewPass, L2PcInstance activeChar)
	{
		if (newPass.length() < 4)
		{
			activeChar.sendMessage("The new password should be at least 4 letters.");
			return;
		}
		if (newPass.length() > 16)
		{
			activeChar.sendMessage("The new password cannot be bigger than 16 letters.");
			return;
		}
		if (!newPass.equals(repeatNewPass))
		{
			activeChar.sendMessage("Repeated password doesn't match the new password.");
			return;
		}
		Connection con = null;
		String password = null;
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA");
			byte[] raw = currPass.getBytes("UTF-8");
			raw = md.digest(raw);
			String currPassEncoded = Base64.getEncoder().encodeToString(raw);
				
				//encodeBytes(raw);
			
			con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT password FROM accounts WHERE login=?");
			statement.setString(1, activeChar.getAccountName());
			ResultSet rset = statement.executeQuery();
			while (rset.next())
			{
				password = rset.getString("password");
			}
			rset.close();
			statement.close();
			
			byte[] password2 = null;
			if (currPassEncoded.equals(password))
			{
				password2 = newPass.getBytes("UTF-8");
				password2 = md.digest(password2);
				
				PreparedStatement statement2 = con.prepareStatement("UPDATE accounts SET password=? WHERE login=?");
				statement2.setString(1, Base64.getEncoder().encodeToString(password2));
				statement2.setString(2, activeChar.getAccountName());
				statement2.executeUpdate();
				statement2.close();
				
				separateAndSend(HtmCache.getInstance().getHtm("data/html/CommunityBoard/Custom/passChanged.htm"), activeChar);
			}
			else
			{
				activeChar.sendMessage("The current password you've inserted is incorrect! Please try again!");
				return;
			}
		}
		catch (Exception e)
		{
			_log.warning("could not update the password of account: " + activeChar.getAccountName());
			e.getStackTrace();
		}
	}
	
	private static class SingletonHolder
	{
		protected static final PasswordBBSManager _instance = new PasswordBBSManager();
	}
}