package Extensions.VoicedCommands;

import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class AioOnline implements IVoicedCommandHandler
{
	private static String[] commands =
	{
		"showaio"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String params)
	{
		showAllAio(activeChar);
		return true;
	}
	
	private static void showAllAio(L2PcInstance activeChar)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		StringBuilder sb = new StringBuilder();
		
		sb.append("<html><body><br><center>AIO(s) <font color=00FF00>Online</font><center><br>");
		for (L2PcInstance p : L2World.getInstance().getAllPlayers().values())
		{
			if (p.isAio())
				sb.append("<font color=LEVEL>" + p.getName() + "</font><br1>");
		}
		
		sb.append("</body></html>");
		html.setHtml(sb.toString());
		activeChar.sendPacket(html);
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return commands;
	}
}