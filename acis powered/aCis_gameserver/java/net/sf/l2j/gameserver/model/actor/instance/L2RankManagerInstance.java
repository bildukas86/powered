package net.sf.l2j.gameserver.model.actor.instance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.ai.CtrlIntention;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.MyTargetSelected;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.network.serverpackets.ValidateLocation;

public class L2RankManagerInstance extends L2NpcInstance
{
	public L2RankManagerInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance activeChar, String command)
	{
		if (activeChar == null)
		{
			return;
		}
		if (command.startsWith("showpvp"))
		{
			try (Connection con = L2DatabaseFactory.getInstance().getConnection())
			{
				PreparedStatement statement = con.prepareStatement("SELECT char_name,pvpkills FROM characters WHERE accesslevel=0 ORDER BY pvpkills DESC");
				ResultSet rset = statement.executeQuery();
				
				int[] PvP = new int[10];
				String[] Name = new String[10];
				StringBuilder tb = new StringBuilder();
				int i = 0;
				while ((rset.next()) && (rset.getRow() <= 10))
				{
					PvP[i] = rset.getInt("pvpkills");
					Name[i] = rset.getString("char_name");
					tb.append("<tr><td><center><font color =\"CCFF00\">" + i + "</td><td><center>" + Name[i] + "</center></td><td><center>" + PvP[i] + "</center></td></tr>");
					i++;
				}
				infoPvP(activeChar, tb.toString());
			}
			catch (Exception e)
			{
				_log.warning("Rank Manager: Error cannot read pvp rank:" + e);
			}
		}
		if (command.startsWith("showpk"))
		{
			try (Connection con = L2DatabaseFactory.getInstance().getConnection())
			{
				PreparedStatement statement = con.prepareStatement("SELECT char_name,pkkills FROM characters WHERE accesslevel=0 ORDER BY pkkills DESC");
				ResultSet rset = statement.executeQuery();
				
				int[] Pk = new int[10];
				String[] Name = new String[10];
				StringBuilder tb = new StringBuilder();
				int i = 0;
				while ((rset.next()) && (rset.getRow() <= 10))
				{
					Pk[i] = rset.getInt("pkkills");
					Name[i] = rset.getString("char_name");
					tb.append("<tr><td><center><font color =\"FF0000\">" + i + "</td><td><center>" + Name[i] + "</center></td><td><center>" + Pk[i] + "</center></td></tr>");
					i++;
				}
				infoPk(activeChar, tb.toString());
			}
			catch (Exception e)
			{
				_log.warning("Rank Manager: Error cannot read pk rank:" + e);
			}
		}
	}
	
	@Override
	public void onAction(L2PcInstance activeChar)
	{
		if (this != activeChar.getTarget())
		{
			activeChar.setTarget(this);
			activeChar.sendPacket(new MyTargetSelected(getObjectId(), activeChar.getLevel() - getLevel()));
			activeChar.sendPacket(new ValidateLocation(this));
		}
		else if (isInsideRadius(activeChar, 150, false, false))
		{
			activeChar.setCurrentFolkNPC(this);
			showHtmlWindow(activeChar);
			activeChar.sendPacket(ActionFailed.STATIC_PACKET);
		}
		else
		{
			activeChar.getAI().setIntention(CtrlIntention.INTERACT, this);
			activeChar.sendPacket(ActionFailed.STATIC_PACKET);
		}
	}
	
	private void showHtmlWindow(L2PcInstance activeChar)
	{
		StringBuilder tb = new StringBuilder();
		NpcHtmlMessage html = new NpcHtmlMessage(1);
		
		tb.append("<html><head><title>Top PvP/Pk Manager</title></head><body><center><table width=\"250\" bgcolor=\"000000\"><tr><td align=center><font color=\"6fd3d1\">Here you can report any bug you wish</font></td></tr></table>_______________________________________<br><br><button value=\"Show Top PvP\" action=\"bypass -h npc_" + getObjectId() + "_showpvp\" width=95 height=21 back=\"bigbutton_over\" fore=\"bigbutton\"><br>" + "<button value=\"Show Top Pk\" action=\"bypass -h npc_" + getObjectId() + "_showpk\" width=95 height=21 back=\"bigbutton_over\" fore=\"bigbutton\"><br>" + "</center></body></html>");
		
		html.setHtml(tb.toString());
		activeChar.sendPacket(html);
	}
	
	private static void infoPvP(L2PcInstance activeChar, String htmldata)
	{
		StringBuilder tb = new StringBuilder();
		NpcHtmlMessage html = new NpcHtmlMessage(1);
		
		tb.append("<html><head><title>Top PvP Kills</title></head><body><table width=300><tr><td><font color =\"00FF00\">Rank</td><td><center><font color =\"00FF00\">*** Player ***</color></center></td><td><center>*** Kills ***</center></td></tr>" + htmldata + "</body></html>");
		
		html.setHtml(tb.toString());
		activeChar.sendPacket(html);
	}
	
	private static void infoPk(L2PcInstance activeChar, String htmldata)
	{
		StringBuilder tb = new StringBuilder();
		NpcHtmlMessage html = new NpcHtmlMessage(1);
		
		tb.append("<html><head><title>Top PK Kills</title></head><body><table width=300><tr><td><font color =\"00FF00\">Rank</td><td><center><font color =\"00FF00\">*** Player ***</color></center></td><td><center>*** Kills ***</center></td></tr>" + htmldata + "</body></html>");
		
		html.setHtml(tb.toString());
		activeChar.sendPacket(html);
	}
}