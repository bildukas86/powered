package Extensions.Menu.Security;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class OnEnterMail implements Runnable
{
	private L2PcInstance p;
	
	public OnEnterMail(L2PcInstance player)
	{
		p = player;
	}
	
	@Override
	public void run()
	{
		HtmlHolder.subhtml(p);
	}
}