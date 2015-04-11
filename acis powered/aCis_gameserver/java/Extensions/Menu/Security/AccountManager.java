package Extensions.Menu.Security;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.util.Rnd;

public class AccountManager
{
	public static String ans = "";
	public static String email;
	
	/**
	 * Checks if player has email in database
	 * @param player
	 * @return email if exist
	 */
	public static boolean hasSubEmail(L2PcInstance player)
	{
		int hasSubEmail = -1;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT hasSubEmail FROM characters WHERE obj_Id=?");
			statement.setInt(1, player.getObjectId());
			ResultSet rset = statement.executeQuery();
			while (rset.next())
				hasSubEmail = rset.getInt("hasSubEmail");
			if (hasSubEmail == 1)
				return true;
			else if (hasSubEmail == 0)
				return false;
		}
		catch (Exception e)
		{
		}
		return false;
	}
	
	/**
	 * Checked on Request Bypass Adds for the first time email
	 * @param player
	 */
	public static void setHasSubEmail(L2PcInstance player)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("UPDATE characters SET hasSubEmail=? WHERE obj_Id=?");
			statement.setInt(1, 1);
			statement.setInt(2, player.getObjectId());
			statement.execute();
			statement.close();
			HtmlHolder.mainHtml(player);
		}
		catch (Exception e)
		{
			
		}
	}
	
	/**
	 * Checked on MailDelay class
	 * @param player
	 * @return the email
	 */
	public static String getEmailAddress(L2PcInstance player)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT email FROM characters WHERE obj_Id=?");
			statement.setInt(1, player.getObjectId());
			ResultSet rset = statement.executeQuery();
			while (rset.next())
				email = rset.getString("email");
		}
		catch (Exception e)
		{
			
		}
		return email;
	}
	
	/**
	 * Checked on MailDelay class after it gets the email<br>
	 * @param player Creates and send 4 digit code and stores it in the database<br>
	 */
	public static void generateCode(L2PcInstance player)
	{
		int code = Rnd.get(1000, 9999);
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("UPDATE characters SET emailcode=? WHERE obj_Id=?");
			statement.setInt(1, code);
			statement.setInt(2, player.getObjectId());
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
		}
	}
	
	/**
	 * Checked on MailDelay class<br>
	 * Takes the 4 digit code from database and sends to<br>
	 * @param player
	 * @return 4 digit code
	 */
	public static int getCode(L2PcInstance player)
	{
		int code = -1;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT emailcode FROM characters WHERE obj_Id=?");
			statement.setInt(1, player.getObjectId());
			ResultSet rset = statement.executeQuery();
			while (rset.next())
				code = rset.getInt("emailcode");
		}
		catch (Exception e)
		{
		}
		return code;
	}
	
	/**
	 * Checks if the passwords are correct
	 * @param newPass
	 * @param repeatNewPass
	 * @param activeChar
	 * @return password finally adds the password to the database if everything successfull
	 */
	public static boolean changePassword(String newPass, String repeatNewPass, L2PcInstance activeChar)
	{
		if (newPass.length() < 4)
		{
			activeChar.sendMessage("The new password is too short!");
			return false;
		}
		if (newPass.length() > 20)
		{
			activeChar.sendMessage("The new password is too long!");
			return false;
		}
		if (!newPass.equals(repeatNewPass))
		{
			activeChar.sendMessage("Repeated password doesn't match the new password.");
			return false;
		}
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			MessageDigest md = MessageDigest.getInstance("SHA");
			byte[] password = newPass.getBytes("UTF-8");
			password = md.digest(password);
			
			PreparedStatement statement = con.prepareStatement("UPDATE accounts SET password=? WHERE login=?");
			statement.setString(1, Base64.getEncoder().encodeToString(password));
			statement.setString(2, activeChar.getAccountName());
			statement.execute();
			statement.close();
			activeChar.sendMessage("Congratulations! Your password has been changed succesfully.");
			
			HtmlHolder.showHtmlWindow(activeChar);
		}
		catch (Exception e)
		{
			// _log.warning("could not update the password of account: " + activeChar.getAccountName());
		}
		return true;
	}
	
	/**
	 * @param player
	 * @return 1 if the player has already submitted a security question else it will return 0
	 */
	public static boolean hasSubSec(L2PcInstance player)
	{
		int hasSubSec = -1;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT hasSubSec FROM characters WHERE obj_Id=?");
			statement.setInt(1, player.getObjectId());
			ResultSet rset = statement.executeQuery();
			while (rset.next())
				hasSubSec = rset.getInt("hasSubSec");
			if (hasSubSec == 1)
				return true;
			else if (hasSubSec == 0)
				return false;
		}
		catch (Exception e)
		{
		}
		return false;
	}
	
	/**
	 * Checked on bypass request
	 * @param player sets hasSubSec on database 1 if he has submitted security question
	 */
	public static void setHasSubSec(L2PcInstance player)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("UPDATE characters SET hasSubSec=? WHERE obj_Id=?");
			statement.setInt(1, 1);
			statement.setInt(2, player.getObjectId());
			statement.execute();
			statement.close();
			HtmlHolder.mainHtml(player);
		}
		catch (Exception e)
		{
		}
	}
	
	/**
	 * checked in SecMailDelay class
	 * @param player return Sec 4digit code from database
	 */
	public static void generateSecCode(L2PcInstance player)
	{
		int code = Rnd.get(1000, 9999);
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("UPDATE characters SET secCode=? WHERE obj_Id=?");
			statement.setInt(1, code);
			statement.setInt(2, player.getObjectId());
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
		}
	}
	
	/**
	 * Checked on SecMailDelay class
	 * @param player
	 * @return secCode from database
	 */
	public static int getSecCode(L2PcInstance player)
	{
		int code = -1;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT secCode FROM characters WHERE obj_Id=?");
			statement.setInt(1, player.getObjectId());
			ResultSet rset = statement.executeQuery();
			while (rset.next())
				code = rset.getInt("secCode");
		}
		catch (Exception e)
		{
		}
		return code;
	}
	
	/**
	 * Checked on ChangeMailDelay class
	 * @param player creates a 4 digit code and add it on database
	 */
	public static void generatechangeCode(L2PcInstance player)
	{
		int code = Rnd.get(1000, 9999);
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("UPDATE characters SET emailchangecode=? WHERE obj_Id=?");
			statement.setInt(1, code);
			statement.setInt(2, player.getObjectId());
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
		}
	}
	
	/**
	 * Checked on ChangeMailDelay Class
	 * @param player
	 * @return the 4 digit code that has been send in the email
	 */
	public static int getChangeCode(L2PcInstance player)
	{
		int code = -1;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT emailchangecode FROM characters WHERE obj_Id=?");
			statement.setInt(1, player.getObjectId());
			ResultSet rset = statement.executeQuery();
			while (rset.next())
				code = rset.getInt("emailchangecode");
		}
		catch (Exception e)
		{
		}
		return code;
	}
	
	/**
	 * Changes the security question
	 * @param newSec
	 * @param repeatNewSec
	 * @param activeChar
	 * @return in database a new security question
	 */
	public static boolean changeAnswer(String newSec, String repeatNewSec, L2PcInstance activeChar)
	{
		if (newSec.length() > 50)
		{
			activeChar.sendMessage("The new answer is too long!");
			return false;
		}
		if (!newSec.equals(repeatNewSec))
		{
			activeChar.sendMessage("Repeated answer doesn't match the new answer.");
			return false;
		}
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("UPDATE characters SET answer=? WHERE obj_Id=?");
			statement.setString(1, newSec);
			statement.setInt(2, activeChar.getObjectId());
			statement.execute();
			statement.close();
			activeChar.sendMessage("Congratulations! Your security answer has been changed succesfully.");
		}
		catch (Exception e)
		{
			// _log.warning("could not update the answer of account: " + activeChar.getAccountName());
		}
		return true;
	}
	
	/**
	 * Checks if the answer is correct,<br>
	 * Selects the players E-Mail<br>
	 * Creates a new 7 digit password (encodes it in database and send it in players email) Resets the password,<br>
	 * Sends
	 * @param acc
	 * @param cha
	 * @param activeChar
	 * @return
	 */
	public static boolean resetPass(String acc, String cha, L2PcInstance activeChar)
	{
		String answer = null;
		String email = null;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT answer FROM characters WHERE obj_Id=?");
			statement.setInt(1, activeChar.getObjectId());
			ResultSet rset = statement.executeQuery();
			
			while (rset.next())
			{
				answer = rset.getString("answer");
				if (!ans.equalsIgnoreCase(answer))
				{
					activeChar.sendMessage("Some of your data you submitted does not fit. Please try again.");
					return false;
				}
			}
		}
		catch (Exception e)
		{
			// _log.warning("could not select answer fields from characters to reset pass for " + activeChar.getAccountName());
		}
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT email FROM characters WHERE account_name=?");
			statement.setString(1, acc);
			
			ResultSet rset = statement.executeQuery();
			
			while (rset.next())
			{
				email = rset.getString("email");
			}
			
		}
		catch (Exception e)
		{
		}
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			String newPass = Integer.toString(Rnd.get(1000000, 9999999));
			
			MessageDigest md = MessageDigest.getInstance("SHA");
			byte[] password = newPass.getBytes("UTF-8");
			password = md.digest(password);
			
			PreparedStatement statement2 = con.prepareStatement("UPDATE accounts SET password=? WHERE login=?");
			statement2.setString(1, Base64.getEncoder().encodeToString(password));
			statement2.setString(2, acc);
			statement2.execute();
			statement2.close();
			
			String[] mail =
			{
				email
			};
			try
			{
				L2Emailer.sendL2Mail(mail, "Password Reset", "Your account's " + acc + " password has been reset to : " + newPass);
			}
			catch (MessagingException e)
			{
				e.printStackTrace();
			}
			
			activeChar.sendMessage("Congratulations! The password of the account " + acc + " has been reset succesfully. Go to your email and get your new password!");
			HtmlHolder.mainHtml(activeChar);
		}
		catch (Exception e)
		{
			// _log.warning("could not update the password of account: " + activeChar.getAccountName());
		}
		return true;
	}
	
	/**
	 * Change the email
	 * @param newMail
	 * @param repeatNewMail
	 * @param activeChar
	 * @return
	 */
	public static boolean changeEmail(String newMail, String repeatNewMail, L2PcInstance activeChar)
	{
		if (newMail.length() > 50)
		{
			activeChar.sendMessage("The new email is too long!");
			return false;
		}
		if (!newMail.equals(repeatNewMail))
		{
			activeChar.sendMessage("Repeated email doesn't match the new email.");
			return false;
		}
		if (!isValidEmailAddress(repeatNewMail))
		{
			activeChar.sendMessage("Please add a valid email adress.");
			return false;
		}
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("UPDATE characters SET email=? WHERE obj_Id=?");
			statement.setString(1, newMail);
			statement.setInt(2, activeChar.getObjectId());
			statement.execute();
			statement.close();
			activeChar.sendMessage("Congratulations! Your email address has been changed succesfully to " + newMail + " .");
		}
		catch (Exception e)
		{
			// _log.warning("could not update the email of account: " + activeChar.getAccountName());
		}
		return true;
	}
	
	/**
	 * Selects the 4 digit code from database
	 * @param player
	 * @return 4 digit code to change the email
	 */
	public static int getMailCode(L2PcInstance player)
	{
		int code = -1;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT emailchangecode FROM characters WHERE obj_Id=?");
			statement.setInt(1, player.getObjectId());
			ResultSet rset = statement.executeQuery();
			while (rset.next())
				code = rset.getInt("emailchangecode");
		}
		catch (Exception e)
		{
		}
		return code;
	}
	
	public static boolean isValidEmailAddress(String email)
	{
		boolean result = true;
		try
		{
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		}
		catch (AddressException ex)
		{
			result = false;
		}
		return result;
	}
	
	public static void forgSec(L2PcInstance player)
	{
		if (!AccountManager.hasSubSec(player))
		{
			player.sendMessage("You have not submitted a security question yet.");
		}
		else
		{
			String answer = null;
			try (Connection con = L2DatabaseFactory.getInstance().getConnection())
			{
				PreparedStatement statement = con.prepareStatement("SELECT answer FROM characters WHERE obj_Id=?");
				statement.setInt(1, player.getObjectId());
				ResultSet rset = statement.executeQuery();
				while (rset.next())
				{
					answer = rset.getString("answer");
				}
			}
			catch (Exception e)
			{
			}
			String[] email =
			{
				AccountManager.getEmailAddress(player)
			};
			try
			{
				L2Emailer.sendL2Mail(email, "Security Answer", "Your security answer is : " + answer);
				player.sendMessage("We successfully sent the security answer to your email address: " + AccountManager.getEmailAddress(player));
			}
			catch (MessagingException e)
			{
				e.printStackTrace();
			}
		}
	}
}