package custom.BossRespawn;

import net.sf.l2j.gameserver.datatables.NpcTable;
import net.sf.l2j.gameserver.instancemanager.GrandBossManager;
import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.quest.Quest;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class BossRespawn extends Quest
{
	private static final int NPC_ID = 5;
	private static final int[] BOSSES =
	{
		29001,
		29006,
		29014,
		29019,
		29020,
		29022,
		29028,
		29045
	};
	
	public BossRespawn(int questid, String name, String descr)
	{
		super(questid, name, descr);
		addFirstTalkId(NPC_ID);
	}
	
	public String onFirstTalk(L2Npc npc, L2PcInstance pc)
	{
		if (npc == null || pc == null)
			return null;
		
		if (npc.getNpcId() == NPC_ID)
		{
			sendInfo(pc);
		}
		return null;
	}
	
	private void sendInfo(L2PcInstance activeChar)
	{
		StringBuilder tb = new StringBuilder();
		tb.append("<html><title>Grand Boss Info</title><body><br><center>");
		tb.append("<img src=\"L2UI_CH3.herotower_deco\" width=256 height=32><br><br>");
		
		for (int boss : BOSSES)
		{
			String name = NpcTable.getInstance().getTemplate(boss).getName();
			long delay = GrandBossManager.getInstance().getStatsSet(boss).getLong("respawn_time");
			if (delay <= System.currentTimeMillis())
			{
				tb.append("<font color=\"00C3FF\">" + name + "</color>: <font color=\"9CC300\">Is Alive</color><br1>");
			}
			else
			{
				int hours = (int) ((delay - System.currentTimeMillis()) / 1000 / 60 / 60);
				int mins = (int) (((delay - (hours * 60 * 60 * 1000)) - System.currentTimeMillis()) / 1000 / 60);
				int seconts = (int) (((delay - ((hours * 60 * 60 * 1000) + (mins * 60 * 1000))) - System.currentTimeMillis()) / 1000);
				tb.append("<font color=\"00C3FF\">" + name + "</color><font color=\"FFFFFF\"> Respawn in : </color><font color=\"32C332\">" + hours + ":" + mins + ":" + seconts + "</color><br1>");
			}
		}
		
		tb.append("<br><img src=\"L2UI_CH3.herotower_deco\" width=256 height=32><br>");
		tb.append("</center></body></html>");
		
		NpcHtmlMessage msg = new NpcHtmlMessage(NPC_ID);
		msg.setHtml(tb.toString());
		activeChar.sendPacket(msg);
	}
	
	public static void main(String[] args)
	{
		new BossRespawn(-1, "BossRespawn", "custom");
	}
}