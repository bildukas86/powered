package net.sf.l2j.gameserver.model.actor.instance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class L2TopInstance extends L2NpcInstance
{
	public L2TopInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		if (command.equals("topPvp"))
		{
			NpcHtmlMessage htm = new NpcHtmlMessage(getObjectId());
			htm.setFile("data/html/mods/TopStats/" + getNpcId() + "-pvp.htm");
			htm.replace("%toppvp%", generateTopPvp());
			htm.replace("%objectId%", getObjectId() + "");
			
			player.sendPacket(htm);
		}
		if (command.equals("topPk"))
		{
			NpcHtmlMessage htm = new NpcHtmlMessage(getObjectId());
			htm.setFile("data/html/mods/TopStats/" + getNpcId() + "-pk.htm");
			htm.replace("%toppk%", generateTopPk());
			htm.replace("%objectId%", getObjectId() + "");
			
			player.sendPacket(htm);
		}
		super.onBypassFeedback(player, command);
	}
	
	private static String generateTopPk()
	{
		String val = "";
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement stm = con.prepareStatement("SELECT char_name,pkkills,online,accesslevel FROM characters WHERE accesslevel=0 ORDER BY pkkills DESC LIMIT 10");
			ResultSet rset = stm.executeQuery();
			
			int pos = 1;
			while (rset.next())
			{
				String player = rset.getString("char_name");
				int pkkills = rset.getInt("pkkills");
				int online = rset.getInt("online");
				
				val = val + "<tr><td>" + pos + ".</td><td>" + player + "</td><td>" + pkkills + "</td><td>" + (online == 1 ? "ONLINE" : "OFFLINE") + "</td></tr>";
				pos++;
			}
			rset.close();
			stm.close();
			
			return val;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return val;
	}
	
	private static String generateTopPvp()
	{
		String val = "";
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement stm = con.prepareStatement("SELECT char_name,pvpkills,online,accesslevel FROM characters WHERE accesslevel=0 ORDER BY pvpkills DESC LIMIT 10");
			ResultSet rset = stm.executeQuery();
			
			int pos = 1;
			while (rset.next())
			{
				String player = rset.getString("char_name");
				int pvpkills = rset.getInt("pvpkills");
				int online = rset.getInt("online");
				
				val = val + "<tr><td>" + pos + ".</td><td>" + player + "</td><td>" + pvpkills + "</td><td>" + (online == 1 ? "ONLINE" : "OFFLINE") + "</td></tr>";
				pos++;
			}
			rset.close();
			stm.close();
			
			return val;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return val;
	}
	
	@Override
	public String getHtmlPath(int npcId, int val)
	{
		String filename = "";
		if (val == 0)
		{
			filename = "" + npcId;
		}
		else
		{
			filename = npcId + "-" + val;
		}
		return "data/html/mods/TopStats/" + filename + ".htm";
	}
}