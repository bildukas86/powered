/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.l2j.gameserver.buffer;

import java.util.ArrayList;

import net.sf.l2j.gameserver.buffer.BufferParser.Buff;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * @author Erlandys
 *
 */
public class BufferHtml
{
	BufferParser bp = BufferParser.getInstance();
	L2Buffer buffer = L2Buffer.getInstance();

	public void buffWindow(L2PcInstance player, int pageId, String type, int objectId)
	{
		try
		{
			String schemePlayerId = "" + player.getObjectId() + player.getClassIndex();
			int limitInPage = 6, count = 1;
			String buffs = "", leftBuffs = "";
			NpcHtmlMessage html = new NpcHtmlMessage(0);
			String sb = "";

			int buffCount = player.getMaxBuffCount();
			if(buffer.getBuffTemplate(schemePlayerId) != null)
				buffCount = (player.getMaxBuffCount() - buffer.getBuffTemplate(schemePlayerId).size());

			leftBuffs += ("<table border=\"1\" width=\"245\" height=\"24\"><tr><td align=\"center\"><font color=\"ac9775\">Remaining slots for buffs:</font><font color=\"8B0000\"> " + buffCount + "</font></td></tr></table><br>");

			for(Buff buff : bp.getBuffs())
			{
				if(type.equalsIgnoreCase(buff.getType()))
				{
					String bgColor = count % 2 == 0 ? "" : " bgcolor=\"000000\"";
					String skillImg = "0000";
					if(buff.getId() < 100)
						skillImg = "00" + buff.getId();
					else if(buff.getId() > 99 && buff.getId() < 1000)
						skillImg = "0" + buff.getId();
					else if(buff.getId() > 4698 && buff.getId() < 4701)
						skillImg = "1331";
					else if(buff.getId() > 4701 && buff.getId() < 4704)
						skillImg = "1332";
					else
						skillImg = "" + buff.getId();
					if(count > (limitInPage * pageId - limitInPage) && count <= (limitInPage * pageId))
					{
						buffs += ("<table" + bgColor + "><tr>\n");
						buffs += ("<td width=40><img width=32 height=32 src=\"icon.skill" + skillImg + "\"></td><td width=150><table><tr>\n");
						buffs += ("<td width=150><font color=\"FFFFFF\"><a action=\"bypass npc_" + objectId + "_buff " + pageId + ";" + buff.getId() + ";" + type + "\">" + buff.getName() + "</a></font> <font color=a1a1a1>Lv</font> <font color=ae9977>" + buff.getLevel() + "</font></td></tr>\n");
						buffs += ("<tr><td width=225><font color=b0bccc>" + buff.getInformation() + "</font></td></tr>");
						buffs += ("</table></td><td>" + schemeButton(schemePlayerId, buff, objectId, pageId, buffCount) + "</td></tr></table>");
					}
					count++;
				}
			}
			sb += ("<html><title>" + type + "</title><body><center><font color=3c3c3c>_________</font> <font color=1E90FF>" + type + "</font> <font color=0000CD>Page</font> <font color=FF0000>" + pageId + "</font><font color=3c3c3c>_________</font><br>" + leftBuffs + buffs);
			sb += ("<table width=170><tr>");
			if((pageId - 1) != 0)
				sb += ("<td align=right width=190><button value=\"Prev\" action=\"bypass npc_" + objectId + "_Chat " + (pageId - 1) + ";" + type + "\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"></td>");
			else
				sb += ("<td align=right width=190><button value=\"Prev\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalDisable\" fore=\"L2UI_ch3.Btn1_normalDisable\"></td>");

			if(pageId * limitInPage >= count-1)
				sb += ("<td width=190><button value=\"Next\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalDisable\" fore=\"L2UI_ch3.Btn1_normalDisable\"></td>");
			else
				sb += ("<td width=190><button value=\"Next\" action=\"bypass npc_" + objectId + "_Chat " + (pageId + 1) + ";" + type + "\" width=75 height=21 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"></td>");
			sb += ("</tr></table>");
			sb += ("<button action=\"bypass npc_" + objectId + "_mainWindow\" value=\"Main Screen\" width=94 height=21 back=\"L2UI_CH3.bigbutton_down\" fore=\"L2UI_CH3.bigbutton\"></center></body></html>");
			html.setHtml(sb.toString());
			player.sendPacket(html);
			player.sendPacket(ActionFailed.STATIC_PACKET);
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
	}

	public String schemeButton(String schemePlayerId, Buff buff, int objectId, int pageId, int buffCount)
	{
		String button = "";
		ArrayList<String[]> template = buffer.getBuffTemplate(schemePlayerId);
		if (template != null && template.size() > 0)
		{
			for (String[] temp : template)
			{
				if (temp[0].equalsIgnoreCase("" + buff.getId()) && temp[1].equalsIgnoreCase("" + buff.getLevel()))
				{
					button = ("<button action=\"bypass npc_" + objectId + "_schemeBuff " + pageId + ";" + buff.getId() + ";" + buff.getType() + ";1\" width=22 height=22 back=\"L2UI_CH3.Minimap.mapbutton_zoomout2\" fore=\"L2UI_CH3.Minimap.mapbutton_zoomout1\">");
					return button;
				}
				button = ("<button action=\"bypass npc_" + objectId + "_schemeBuff " + pageId + ";" + buff.getId() + ";" + buff.getType() + ";0\" width=22 height=22 back=\"L2UI_CH3.Minimap.mapbutton_zoomin2\" fore=\"L2UI_CH3.Minimap.mapbutton_zoomin1\">");
			}
		}
		else
			button = ("<button action=\"bypass npc_" + objectId + "_schemeBuff " + pageId + ";" + buff.getId() + ";" + buff.getType() + ";0\" width=22 height=22 back=\"L2UI_CH3.Minimap.mapbutton_zoomin2\" fore=\"L2UI_CH3.Minimap.mapbutton_zoomin1\">");
		return button;
	}

	public void mainWindow(L2PcInstance player, int objectId)
	{
		try
		{
			String sb = "";
			String schemePlayerId = "" + player.getObjectId() + player.getClassIndex();
			String buttons = "";
			int h = 0;
			buttons += "<table>";
			for (String type : bp.getBuffTypes())
			{
				if (h % 2 == 0)
					buttons += "<tr><td><img src=\"L2UI.SquareBlank\" width=4 height=20></td><td><button action=\"bypass npc_" + objectId + "_Chat 1;" + type + "\" value=\"" + type + "\" width=135 height=21 back=\"L2UI_ch3.bigbutton3_down\" fore=\"L2UI_ch3.bigbutton3\"></td>";
				else if(h % 2 == 1)
					buttons += "<td><button action=\"bypass npc_" + objectId + "_Chat 1;" + type + "\" value=\"" + type + "\" width=135 height=21 back=\"L2UI_ch3.bigbutton3_down\" fore=\"L2UI_ch3.bigbutton3\"></td></tr>";
				h++;
			}
			if(h % 2 == 1)
				buttons += "</tr>";
			buttons += "</table>";
			int buffCount = player.getMaxBuffCount();
			if(buffer.getBuffTemplate(schemePlayerId) != null)
				buffCount = (player.getMaxBuffCount() - buffer.getBuffTemplate(schemePlayerId).size());

			sb += ("<html><title>Buffer</title><body><center><br><font color=3c3c3c>_________</font> <font color=1E90FF>Buffs Categories</font> <font color=3c3c3c>_________</font>");
			sb += ("<br>");
			sb += ("<table border=\"1\" width=\"245\" height=\"24\"><tr><td align=\"center\"><font color=\"ac9775\">Remaining slots for buffs:</font><font color=\"8B0000\"> " + buffCount + "</font></td></tr></table><br>");
			sb += (buttons);
			sb += ("<font color=3c3c3c>_________</font> <font color=1E90FF>Other Functions</font> <font color=3c3c3c>_________</font><br>");
			sb += ("<table><tr>");
			sb += ("<td><img src=\"L2UI.SquareBlank\" width=14 height=32></td>");
			sb += ("<td><button action=\"bypass npc_" + objectId + "_addedBuffs\" value=\"Added Buffs\" width=135 height=21 back=\"L2UI_ch3.bigbutton3_down\" fore=\"L2UI_ch3.bigbutton3\"></td></tr></table>");
			sb += ("<table><tr><td><img src=\"L2UI.SquareBlank\" width=4 height=20></td>");
			sb += ("<td><button action=\"bypass npc_" + objectId + "_heal\" value=\"Heal\" width=135 height=21 back=\"L2UI_ch3.bigbutton3_down\" fore=\"L2UI_ch3.bigbutton3\"></td>");
			sb += ("<td><button action=\"bypass npc_" + objectId + "_cancel\" value=\"Cancel\" width=135 height=21 back=\"L2UI_ch3.bigbutton3_down\" fore=\"L2UI_ch3.bigbutton3\"></td>");
			sb += ("</tr><td></td><td></td></tr></table>");
			sb += ("<table><tr><td><img src=\"L2UI.SquareBlank\" width=4 height=20><td><img src=\"L2UI.SquareBlank\" width=4 height=20></td><td><img src=\"icon.skill4452\" width=32 height=32></td>");
			sb += ("<td><center><img src=\"L2UI.SquareBlank\" width=25 height=32></center></td>");
			sb += ("<td><center><button action=\"bypass npc_" + objectId + "_wBuffSet\" width=32 height=32 back=\"icon.skill4279\" fore=\"icon.skill4279\"></center></td>");
			sb += ("<td><img src=\"L2UI_CH3.shortcut_prev_down\" width=16 height=16></td>");
			sb += ("<td><img src=\"L2UI.SquareBlank\" width=32 height=32></td>");
			sb += ("<td><img src=\"L2UI_CH3.shortcut_next_down\" width=16 height=16></td>");
			sb += ("<td><center><button action=\"bypass npc_" + objectId + "_mBuffSet\" width=32 height=32 back=\"icon.skill4280\" fore=\"icon.skill4280\"></center></td>");
			sb += ("<td><center><img src=\"L2UI.SquareBlank\" width=25 height=32></center></td>");
			sb += ("<td><img src=\"icon.skill4453\" width=32 height=32></td>");
			sb += ("</tr></table>");
			sb += ("<table><tr>");
			sb += ("<td><img src=\"L2UI.SquareBlank\" width=14 height=32></td>");
			sb += ("<td><button action=\"bypass -h npc_" + objectId + "_useScheme\" value=\"Use Scheme\" width=135 height=21 back=\"L2UI_ch3.bigbutton3_down\" fore=\"L2UI_ch3.bigbutton3\"></td></tr></table>");
			sb += ("<img src=\"L2UI.SquareBlank\" width=80 height=32><font color=3c3c3c>__________________________</font><br><font color=ffffff>*Made by <font color=9f9f9f>Erlandys</font>*</font></center></body></html>");
			NpcHtmlMessage html = new NpcHtmlMessage(0);
			html.setHtml(sb.toString());
			player.sendPacket(html);
			player.sendPacket(ActionFailed.STATIC_PACKET);
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
	}

	private static class SingletonHolder
	{
		protected static final BufferHtml _instance = new BufferHtml();
	}

	public static BufferHtml getInstance()
	{
		return SingletonHolder._instance;
	}

}
