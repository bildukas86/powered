package Extensions.Menu.Security;

import javax.mail.MessagingException;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class MailDelay implements Runnable
{
	private final L2PcInstance p;
	
	public MailDelay(L2PcInstance player)
	{
		p = player;
	}
	
	@Override
	public void run()
	{
		String[] email =
		{
			AccountManager.getEmailAddress(p)
		};
		AccountManager.generateCode(p);
		try
		{
			L2Emailer.sendL2Mail(email, "Security Code", "Your security code is : " + AccountManager.getCode(p));
		}
		catch (MessagingException e)
		{
			e.printStackTrace();
		}
		HtmlHolder.successhtml(p);
	}
}