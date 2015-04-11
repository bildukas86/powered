package Extensions.Menu.Security;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class UserFirst implements Runnable
{
	private L2PcInstance p;
	
	public UserFirst(L2PcInstance player)
	{
		p = player;
	}
	
	@Override
	public void run()
	{
		HtmlHolder.UserEnter(p);
	}
}