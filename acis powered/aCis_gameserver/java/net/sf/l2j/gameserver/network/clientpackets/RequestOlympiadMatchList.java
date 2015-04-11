package net.sf.l2j.gameserver.network.clientpackets;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.olympiad.Olympiad;
import net.sf.l2j.gameserver.model.olympiad.OlympiadGameManager;
import net.sf.l2j.gameserver.model.olympiad.OlympiadGameTask;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.util.StringUtil;

/**
 * format ch c: (id) 0xD0 h: (subid) 0x13
 * @author -Wooden-
 */
public final class RequestOlympiadMatchList extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
	}
	
	@Override
	protected void runImpl()
	{
		final L2PcInstance activeChar = getClient().getActiveChar();
		if (activeChar == null || !activeChar.inObserverMode())
			return;
		
		NpcHtmlMessage message = new NpcHtmlMessage(0);
		StringBuilder list = new StringBuilder(1500);
		OlympiadGameTask task;
		
		message.setFile(Olympiad.OLYMPIAD_HTML_PATH + "olympiad_arena_observe_list.htm");
		for (int i = 0; i <= 21; i++)
		{
			task = OlympiadGameManager.getInstance().getOlympiadTask(i);
			if (task != null)
			{
				StringUtil.append(list, "<tr><td fixwidth=10><a action=\"bypass arenachange ", String.valueOf(i), "\">", String.valueOf(i + 1), "</a></td><td fixwidth=80>");
				
				if (task.isGameStarted())
				{
					if (task.isInTimerTime())
						StringUtil.append(list, "&$907;"); // Counting In Progress
					else if (task.isBattleStarted())
						StringUtil.append(list, "&$829;"); // In Progress
					else
						StringUtil.append(list, "&$908;"); // Terminate
						
					StringUtil.append(list, "</td><td>", task.getGame().getPlayerNames()[0], "&nbsp; / &nbsp;", task.getGame().getPlayerNames()[1]);
				}
				else
					StringUtil.append(list, "&$906;", "</td><td>&nbsp;"); // Initial State
					
				StringUtil.append(list, "</td><td><font color=\"aaccff\"></font></td></tr>");
			}
		}
		message.replace("%list%", list.toString());
		activeChar.sendPacket(message);
	}
}