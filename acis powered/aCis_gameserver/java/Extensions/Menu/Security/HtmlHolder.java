package Extensions.Menu.Security;

import net.sf.l2j.gameserver.cache.HtmCache;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class HtmlHolder
{
	/**
	 * Checked on Bypass request
	 * @param player has email if true it will send main html<br>
	 *            else it will send registration html
	 */
	public static void showHtmlWindow(L2PcInstance player)
	{
		if (AccountManager.hasSubEmail(player))
			mainHtml(player);
		else
			subhtml(player);
		
		player.sendPacket(ActionFailed.STATIC_PACKET);
	}
	
	/**
	 * Security Main HTML
	 * @param player has submitted email
	 */
	public static void mainHtml(L2PcInstance player)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		String htm = HtmCache.getInstance().getHtm("data/html/mods/UserPanel/Security/Main.htm");
		html.setHtml(htm.toString());
		html.replace("%name%", String.valueOf(player.getName()));
		player.sendPacket(html);
	}
	
	/**
	 * Security Main HTML
	 * @param player has not submitted email
	 */
	protected static void subhtml(L2PcInstance player)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		String htm = HtmCache.getInstance().getHtm("data/html/mods/UserPanel/Security/OnEnter.htm");
		html.setHtml(htm.toString());
		html.replace("%name%", String.valueOf(player.getName()));
		player.sendPacket(html);
	}
	
	/**
	 * Change password request
	 * @param player public on RequestBypassToServer class
	 */
	public static void sendingHtml(L2PcInstance player)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		String htm = HtmCache.getInstance().getHtm("data/html/mods/UserPanel/Security/Sending.htm");
		html.setHtml(htm.toString());
		html.replace("%name%", String.valueOf(player.getName()));
		player.sendPacket(html);
	}
	
	/**
	 * MailDelay class will send a success html
	 * @param player must enter the 4 digit code now
	 */
	protected static void successhtml(L2PcInstance player)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		String htm = HtmCache.getInstance().getHtm("data/html/mods/UserPanel/Security/Success.htm");
		html.setHtml(htm.toString());
		html.replace("%name%", String.valueOf(player.getName()));
		player.sendPacket(html);
	}
	
	/**
	 * player has entered the 4 digit code correct
	 * @param player can enter the password now
	 */
	public static void changepasshtml(L2PcInstance player)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		String htm = HtmCache.getInstance().getHtm("data/html/mods/UserPanel/Security/ChangePassword.htm");
		html.setHtml(htm.toString());
		html.replace("%name%", String.valueOf(player.getName()));
		player.sendPacket(html);
	}
	
	/**
	 * Checked on bypass request
	 * @param player gives 1 word for security question
	 */
	public static void addSecHtml(L2PcInstance player)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		String htm = HtmCache.getInstance().getHtm("data/html/mods/UserPanel/Security/AddSecurityQuestion.htm");
		html.setHtml(htm.toString());
		html.replace("%name%", String.valueOf(player.getName()));
		player.sendPacket(html);
	}
	
	/**
	 * Checked on request bypass
	 * @param player must put the correct answer to proceed
	 */
	public static void forgPassHtml(L2PcInstance player)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		String htm = HtmCache.getInstance().getHtm("data/html/mods/UserPanel/Security/ForgotPass.htm");
		html.setHtml(htm.toString());
		html.replace("%name%", String.valueOf(player.getName()));
		player.sendPacket(html);
	}
	
	/**
	 * Checked on Request bypass
	 * @param player must enter a new email
	 */
	public static void changeemailhtml(L2PcInstance player)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		String htm = HtmCache.getInstance().getHtm("data/html/mods/UserPanel/Security/ChangeEmail.htm");
		html.setHtml(htm.toString());
		html.replace("%name%", String.valueOf(player.getName()));
		player.sendPacket(html);
	}
	
	/**
	 * Checked on SecMailDelay class
	 * @param player submits the secCode after successfully send the email from the server
	 */
	public static void secsuccesshtml(L2PcInstance player)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		String htm = HtmCache.getInstance().getHtm("data/html/mods/UserPanel/Security/SecSuccess.htm");
		html.setHtml(htm.toString());
		html.replace("%name%", String.valueOf(player.getName()));
		player.sendPacket(html);
	}
	
	/**
	 * Checked on ChangeMailDelay Class
	 * @param player success after sending the email now he has to put the 4 digit code
	 */
	public static void successchangehtml(L2PcInstance player)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		String htm = HtmCache.getInstance().getHtm("data/html/mods/UserPanel/Security/ChangeSec.htm");
		html.setHtml(htm.toString());
		html.replace("%name%", String.valueOf(player.getName()));
		player.sendPacket(html);
	}
	
	/**
	 * Sends first timers an window to teach the .menu command
	 * @param player
	 */
	public static void UserEnter(L2PcInstance player)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		String htm = HtmCache.getInstance().getHtm("data/html/mods/UserPanel/UserEnter.htm");
		html.setHtml(htm.toString());
		html.replace("%name%", String.valueOf(player.getName()));
		player.sendPacket(html);
	}
}