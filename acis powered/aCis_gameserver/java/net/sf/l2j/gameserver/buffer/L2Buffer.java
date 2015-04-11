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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * @author Erlandys
 *
 */
public class L2Buffer
{
	static final Logger _log = Logger.getLogger(L2Buffer.class.getName());
	private class UpdateTask implements Runnable
	{
		public UpdateTask()
		{
			
		}

		@Override
		public void run()
		{
			updateSQL();
		}
	}

	private HashMap<String, ArrayList<String[]>> buffTemplates;

	private HashMap<String, Boolean> changes;

	private UpdateTask updateTask;

	public L2Buffer()
	{
		updateTask = new UpdateTask();
		changes = new HashMap<>();
		buffTemplates = new HashMap<>();
		loadSQL();
		ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(updateTask, 300000, 300000);
	}

	public HashMap<String, ArrayList<String[]>> getBuffTemplates()
	{
		return buffTemplates;
	}

	public ArrayList<String[]> getBuffTemplate(String charInfo)
	{
		if (buffTemplates.get(charInfo) != null)
			return buffTemplates.get(charInfo);
		return null;
	}

	public void buffPlayer(L2PcInstance player)
	{
		String playerId = "" + player.getObjectId() + player.getClassIndex();
		
		if(!buffTemplates.containsKey(playerId))
		{
			_log.warning("The player : "+player.getName()+" ("+playerId+") without template");
			return;
		}
		
		for (String[] skill : buffTemplates.get(playerId)) {
			L2Skill sk = SkillTable.getInstance().getInfo(Integer.parseInt(skill[0]), Integer.parseInt(skill[1]));
			if (sk != null)
				sk.getEffects(player, player);
		}
	}

	public void changeList(L2PcInstance player, String buff[], boolean action)
	{
		String playerId = "" + player.getObjectId() + player.getClassIndex();
		int id = -1;
		if (!buffTemplates.containsKey(playerId))
		{
			buffTemplates.put(playerId, new ArrayList<String[]>());
			changes.put(playerId, true);
		}
		if (!changes.containsKey(playerId))
			changes.put(playerId, false);
		if (action)
			buffTemplates.get(playerId).add(buff);
		else
		{
			for (int i = 0; i < buffTemplates.get(playerId).size(); i++)
			{
				if (buff[0].equals(buffTemplates.get(playerId).get(i)[0]) && buff[1].equals(buffTemplates.get(playerId).get(i)[1]))
				{
					id = i;
					i = buffTemplates.get(playerId).size();
				}
			}
			buffTemplates.get(playerId).remove(buffTemplates.get(playerId).get(id));
		}
	}

	private void loadSQL()
	{
		_log.info(getClass().getSimpleName()+": Initializing");
		PreparedStatement statement = null;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection();)
		{
			statement = con.prepareStatement("SELECT * FROM scheme_buffs");
			ResultSet rset = statement.executeQuery();
			int count = 0;
			while (rset.next())
			{
				count++;

				buffTemplates.put(rset.getString("player"), new ArrayList<String[]>());

				StringTokenizer st = new StringTokenizer(rset.getString("buffs"), ";");

				ArrayList<String[]> templist = new ArrayList<>();

				while (st.hasMoreTokens())
					templist.add(st.nextToken().split(","));

				String player = rset.getString("player");
				if (buffTemplates.containsKey(player))
					buffTemplates.remove(player);
				buffTemplates.put(player, templist);
			}
			rset.close();
			statement.close();

			_log.info(getClass().getSimpleName()+": Successfully loaded " + count + " Buff Templates.");
		}
		catch (Exception e)
		{
			_log.info("SchemeBuffs SQL catch");
		}
	}

	public void showHtml(L2PcInstance player, int objectId)
	{
		try
		{
			String playerId = "" + player.getObjectId() + player.getClassIndex();
	
			if (!buffTemplates.containsKey(playerId))
			{
				buffTemplates.put(playerId, new ArrayList<String[]>());
				changes.put(playerId, true);
			}
	
			NpcHtmlMessage html = new NpcHtmlMessage(0);
			String sb = "";
	
			int buffCount = player.getMaxBuffCount();
			if(getBuffTemplate(playerId) != null)
				buffCount = (player.getMaxBuffCount() - getBuffTemplate(playerId).size());
			
			sb += ("<html><title>Buff list</title><body><center><img src=\"L2UI_CH3.onscrmsg_pattern01_1\" width=300 height=32 align=left>");
			sb += ("<center><table width=270 bgcolor=000000><tr><td width=12></td><td width=245 align=\"center\"><font color=\"ac9775\">Remaining slots for buffs:</font><font color=\"8B0000\"> " + buffCount + "</font><font color=\"ac9775\"></td><td width=13></td></tr></table><br><br>");
			sb += ("<center><table width=270 bgcolor=000000><tr><td><font color=\"ac9775\">Added buffs:</font></td></tr></table><br>");
			sb += ("<center><table width=226>");
	
			int c = 0;
			for (String[] skill : buffTemplates.get(playerId))
			{
				L2Skill sk = SkillTable.getInstance().getInfo(Integer.parseInt(skill[0]), Integer.parseInt(skill[1]));
				if (sk == null)
					continue;
				c++;
				String skillStr = "0000";
				if (Integer.parseInt(skill[0]) < 100)
					skillStr = "00" + Integer.parseInt(skill[0]);
				else if (Integer.parseInt(skill[0]) > 99 && Integer.parseInt(skill[0]) < 1000)
					skillStr = "0" + Integer.parseInt(skill[0]);
				else if (Integer.parseInt(skill[0]) > 4698 && Integer.parseInt(skill[0]) < 4701)
					skillStr = "1331";
				else if (Integer.parseInt(skill[0]) > 4701 && Integer.parseInt(skill[0]) < 4704)
					skillStr = "1332";
				else
					skillStr = "" + Integer.parseInt(skill[0]);
	
				if (c % 2 == 1)
					sb += ("<tr><td width=33><img src=\"Icon.skill" + skillStr + "\" width=32 height=32></td><td width=80><a action=\"bypass npc_" + objectId + "_schemeBuffS " + Integer.parseInt(skill[0]) + "," + Integer.parseInt(skill[1]) + "\">" + sk.getName() + "</a></td>");
				if (c % 2 == 0)
					sb += ("<td width=33><img src=\"Icon.skill" + skillStr + "\" width=32 height=32></td><td width=80><a action=\"bypass npc_" + objectId + "_schemeBuffS " + Integer.parseInt(skill[0]) + "," + Integer.parseInt(skill[1]) + "\">" + sk.getName() + "</a></td></tr><tr></tr>");
	
			}
			if (c % 2 == 1)
				sb += ("<td width=33></td><td width=100></td></tr>");
	
			sb += ("</table><br>");
			sb += ("<br><img src=\"L2UI_CH3.onscrmsg_pattern01_1\" width=300 height=32 align=left>");
			sb += ("<button action=\"bypass npc_" + objectId + "_mainWindow\" value=\"Main Screen\" width=94 height=21 back=\"L2UI_CH3.bigbutton_down\" fore=\"L2UI_CH3.bigbutton\"></center></body></html>");
			html.setHtml(sb.toString());
			player.sendPacket(html);
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
	}

	public void updateSQL()
	{
		PreparedStatement statement = null;

		try (Connection con = L2DatabaseFactory.getInstance().getConnection();)
		{

			for (Map.Entry<String, Boolean> player : changes.entrySet())
			{

				String sb = "";

				int c = 0;
				for (String buff[] : buffTemplates.get(player.getKey()))
				{
					if (c == 0)
						sb += (buff[0] + "," + buff[1]);
					else
						sb += (";" + buff[0] + "," + buff[1]);
					c++;
				}

				if (player.getValue())
				{
					statement = con.prepareStatement("INSERT INTO scheme_buffs(player,buffs) VALUES (?,?)");
					statement.setString(1, player.getKey());
					statement.setString(2, sb.toString());

					statement.executeUpdate();
					statement.close();
				}
				else
				{
					statement = con.prepareStatement("UPDATE scheme_buffs SET buffs=? WHERE player=?");
					statement.setString(1, sb.toString());
					statement.setString(2, player.getKey());

					statement.executeUpdate();
					statement.close();
				}
			}
			//if (Config.DEBUG)
			//	_log.info("[L2Buffer]: Sql update was successfully finished!");
		}
		catch (Exception e)
		{
			_log.info("[L2Buffer]: Sql update was uncompleted, error: " + e);
		}

		changes.clear();
	}

	private static class SingletonHolder
	{
		protected static final L2Buffer _instance = new L2Buffer();
	}

	public static L2Buffer getInstance()
	{
		return SingletonHolder._instance;
	}
}
