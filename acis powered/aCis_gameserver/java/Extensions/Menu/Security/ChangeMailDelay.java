package Extensions.Menu.Security;

import javax.mail.MessagingException;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class ChangeMailDelay implements Runnable
{
	private final L2PcInstance p;
	
	public ChangeMailDelay(L2PcInstance player)
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
		AccountManager.generatechangeCode(p);
		try
		{
			L2Emailer.sendL2Mail(email, "Email Change Code", "Your email change code is : " + AccountManager.getChangeCode(p));
		}
		catch (MessagingException e)
		{
			e.printStackTrace();
		}
		HtmlHolder.successchangehtml(p);
	}
}