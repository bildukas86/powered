package Extensions.Menu.Security;

import javax.mail.MessagingException;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class SecMailDelay implements Runnable
{
	private final L2PcInstance p;
	
	public SecMailDelay(L2PcInstance player)
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
		AccountManager.generateSecCode(p);
		try
		{
			L2Emailer.sendL2Mail(email, "Security Question Change Code", "Your security question change code is : " + AccountManager.getSecCode(p));
		}
		catch (MessagingException e)
		{
			e.printStackTrace();
		}
		HtmlHolder.secsuccesshtml(p);
	}
}