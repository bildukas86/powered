package Extensions.VoicedCommands;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.datatables.NpcTable;
import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.instancemanager.GrandBossManager;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class BossInfo implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{
		"bossinfo"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		StringBuilder tb = new StringBuilder();
		NpcHtmlMessage msg = new NpcHtmlMessage(5);
		tb.append("<html><title>Grand Boss</title><body><br><center>");
		tb.append("<img src=\"L2UI_CH3.herotower_deco\" width=256 height=32><br><br>");
		
		for (int boss : Config.GRAND_BOSS_LIST)
		{
			String name = NpcTable.getInstance().getTemplate(boss).getName();
			long delay = 0;
			delay = GrandBossManager.getInstance().getStatsSet(boss).getLong("respawn_time");
			if (delay <= System.currentTimeMillis())
			{
				tb.append("<font color=\"00C3FF\">" + name + "</color>: " + "<font color=\"9CC300\">Is Alive</color>" + "<br1>");
			}
			else
			{
				int hours = (int) ((delay - System.currentTimeMillis()) / 1000 / 60 / 60);
				int mins = (int) (((delay - (hours * 60 * 60 * 1000)) - System.currentTimeMillis()) / 1000 / 60);
				int seconts = (int) (((delay - ((hours * 60 * 60 * 1000) + (mins * 60 * 1000))) - System.currentTimeMillis()) / 1000);
				tb.append("<font color=\"00C3FF\">" + name + "</color>" + "<font color=\"FFFFFF\">" + " " + "Respawn in :</color>" + " " + " <font color=\"32C332\">" + hours + " : " + mins + " : " + seconts + "</color><br1>");
			}
		}
		
		tb.append("<br><img src=\"L2UI_CH3.herotower_deco\" width=256 height=32><br>");
		tb.append("</center></body></html>");
		msg.setHtml(tb.toString());
		activeChar.sendPacket(msg);
		return true;
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return VOICED_COMMANDS;
	}
}