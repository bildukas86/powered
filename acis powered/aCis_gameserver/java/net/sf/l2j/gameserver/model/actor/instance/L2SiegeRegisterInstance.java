package net.sf.l2j.gameserver.model.actor.instance;

import net.sf.l2j.gameserver.ai.CtrlIntention;
import net.sf.l2j.gameserver.instancemanager.CastleManager;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.model.entity.Castle;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.MyTargetSelected;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.network.serverpackets.SiegeInfo;
import net.sf.l2j.gameserver.network.serverpackets.ValidateLocation;

public class L2SiegeRegisterInstance extends L2NpcInstance
{
	public L2SiegeRegisterInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void onAction(L2PcInstance player)
	{
		if (this != player.getTarget())
		{
			player.setTarget(this);
			MyTargetSelected my = new MyTargetSelected(getObjectId(), 0);
			player.sendPacket(my);
			my = null;
			player.sendPacket(new ValidateLocation(this));
		}
		else if (!canInteract(player))
			player.getAI().setIntention(CtrlIntention.INTERACT, this);
		else
			showHtmlWindow(player);
		player.sendPacket(ActionFailed.STATIC_PACKET);
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		if (command.startsWith("gludio_castle"))
			showSiegeInfoWindow(player, 1);
		else if (command.startsWith("dion_castle"))
			showSiegeInfoWindow(player, 2);
		else if (command.startsWith("giran_castle"))
			showSiegeInfoWindow(player, 3);
		else if (command.startsWith("oren_castle"))
			showSiegeInfoWindow(player, 4);
		else if (command.startsWith("aden_castle"))
			showSiegeInfoWindow(player, 5);
		else if (command.startsWith("innadril_castle"))
			showSiegeInfoWindow(player, 6);
		else if (command.startsWith("goddard_castle"))
			showSiegeInfoWindow(player, 7);
		else if (command.startsWith("rune_castle"))
			showSiegeInfoWindow(player, 8);
		else if (command.startsWith("schuttgart_castle"))
			showSiegeInfoWindow(player, 9);
		else
			super.onBypassFeedback(player, command);
	}
	
	public void showHtmlWindow(L2PcInstance activeChar)
	{
		NpcHtmlMessage nhm = new NpcHtmlMessage(5);
		StringBuilder replyMSG = new StringBuilder("");
		replyMSG.append("<html><body><title>Siege Manager</title><center>");
		replyMSG.append("<br><img src=\"l2font-e.replay_logo-e\" width=255 height=60>");
		replyMSG.append("<br><br>Welcome adventurer.");
		replyMSG.append("<br>Register your clan to conquer a castle and Good luck.");
		replyMSG.append("<br><img src=\"L2UI_CH3.onscrmsg_pattern01_2\" width=300 height=32>");
		replyMSG.append("<table width=280><tr>");
		replyMSG.append("<td><button value=\"Giran Castle\" action=\"bypass -h npc_" + getObjectId() + "_giran_castle\" width=75 height=21 back=L2UI_ch3.Btn1_normalOn fore=L2UI_ch3.Btn1_normalOn></td>");
		replyMSG.append("<td><button value=\"Aden Castle\" action=\"bypass -h npc_"+getObjectId()+"_aden_castle\" width=75 height=21 back=L2UI_ch3.Btn1_normalOn fore=L2UI_ch3.Btn1_normalOn></td>");
		replyMSG.append("<td><button value=\"Rune Castle\" action=\"bypass -h npc_"+getObjectId()+"_rune_castle\" width=75 height=21 back=L2UI_ch3.Btn1_normalOn fore=L2UI_ch3.Btn1_normalOn></td>");
		replyMSG.append("</tr></table>");
		replyMSG.append("<br><br>");
		replyMSG.append("<table width=280><tr>");
		replyMSG.append("<td><button value=\"Goddard Castle\" action=\"bypass -h npc_"+getObjectId()+"_goddard_castle\" width=75 height=21 back=L2UI_ch3.Btn1_normalOn fore=L2UI_ch3.Btn1_normalOn></td>");
		replyMSG.append("<td><button value=\"Dion Castle\" action=\"bypass -h npc_"+getObjectId()+"_dion_castle\" width=75 height=21 back=L2UI_ch3.Btn1_normalOn fore=L2UI_ch3.Btn1_normalOn></td>");
		replyMSG.append("<td><button value=\"Schuttgart Castle\" action=\"bypass -h npc_"+getObjectId()+"_schuttgart_castle\" width=75 height=21 back=L2UI_ch3.Btn1_normalOn fore=L2UI_ch3.Btn1_normalOn></td>");
		replyMSG.append("</tr></table>");
		replyMSG.append("<br><br>");
		replyMSG.append("<table width=280><tr>");
		replyMSG.append("<td><button value=\"Innadril Castle\" action=\"bypass -h npc_"+getObjectId()+"_innadril_castle\" width=75 height=21 back=L2UI_ch3.Btn1_normalOn fore=L2UI_ch3.Btn1_normalOn></td>");
		replyMSG.append("<td><button value=\"Oren Castle\" action=\"bypass -h npc_"+getObjectId()+"_oren_castle\" width=75 height=21 back=L2UI_ch3.Btn1_normalOn fore=L2UI_ch3.Btn1_normalOn></td>");
		replyMSG.append("<td><button value=\"Gludio Castle\" action=\"bypass -h npc_"+getObjectId()+"_gludio_castle\" width=75 height=21 back=L2UI_ch3.Btn1_normalOn fore=L2UI_ch3.Btn1_normalOn></td>");
		replyMSG.append("</tr></table>");
		replyMSG.append("<br><img src=\"L2UI_CH3.onscrmsg_pattern01_1\" width=300 height=32></body></html>");
		nhm.setHtml(replyMSG.toString());
		activeChar.sendPacket(nhm);
	}
	
	public void showSiegeInfoWindow(L2PcInstance player, int castleId)
	{
		Castle c = CastleManager.getInstance().getCastleById(castleId);
		if (c != null)
			player.sendPacket(new SiegeInfo(c));
	}
}