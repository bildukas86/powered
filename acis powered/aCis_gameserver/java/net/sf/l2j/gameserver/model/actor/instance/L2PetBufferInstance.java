package net.sf.l2j.gameserver.model.actor.instance;

import java.util.StringTokenizer;

import net.sf.l2j.gameserver.ai.CtrlIntention;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.MyTargetSelected;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.network.serverpackets.SocialAction;
import net.sf.l2j.gameserver.network.serverpackets.ValidateLocation;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;
import net.sf.l2j.util.StringUtil;
import net.sf.l2j.util.Rnd;

public final class L2PetBufferInstance extends L2NpcInstance
{
	public L2PetBufferInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		StringTokenizer st = new StringTokenizer(command, " ");
		String actualCommand = st.nextToken();
		int buffid = 0;
		int bufflevel = 1;
		if (st.countTokens() == 2)
		{
			buffid = Integer.valueOf(st.nextToken());
			bufflevel = Integer.valueOf(st.nextToken());
		}
		else if (st.countTokens() == 1)
			buffid = Integer.valueOf(st.nextToken());
		if (actualCommand.equalsIgnoreCase("getbuff"))
		{
			if (buffid != 0)
			{
				MagicSkillUse mgc = new MagicSkillUse(this, player.getPet(), buffid, bufflevel, 5, 0);
				SkillTable.getInstance().getInfo(buffid, bufflevel).getEffects(this, player.getPet());
				showMessageWindow(player);
				player.broadcastPacket(mgc);
			}
		}
		else if (actualCommand.equalsIgnoreCase("restore"))
		{
			player.getPet().setCurrentHpMp(player.getMaxHp(), player.getMaxMp());
			player.getPet().setCurrentCp(player.getMaxCp());
			showMessageWindow(player);
		}
		else if (actualCommand.equalsIgnoreCase("cancel"))
		{
			player.getPet().stopAllEffects();
			showMessageWindow(player);
		}
		else
			super.onBypassFeedback(player, command);
	}
	
	@Override
	public void onAction(L2PcInstance player)
	{
		if (this != player.getTarget())
		{
			player.setTarget(this);
			player.sendPacket(new MyTargetSelected(getObjectId(), player.getLevel() - getLevel()));
			player.sendPacket(new ValidateLocation(this));
		}
		else if (isInsideRadius(player, INTERACTION_DISTANCE, false, false))
		{
			SocialAction sa = new SocialAction(this, Rnd.get(8));
			broadcastPacket(sa);
			player.setCurrentFolkNPC(this);
			showMessageWindow(player);
			player.sendPacket(ActionFailed.STATIC_PACKET);
		}
		else
		{
			player.getAI().setIntention(CtrlIntention.INTERACT, this);
			player.sendPacket(ActionFailed.STATIC_PACKET);
		}
	}
	
	private void showMessageWindow(L2PcInstance player)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(1);
		final StringBuilder strBuffer = StringUtil.startAppend(3500, "<html><title>Pet buffer</title><body><center>");
		if (player.isSitting())
		{
			player.sendMessage("You can't use the buffer while you're sitting.");
			strBuffer.append("Stand up, <font color=\"LEVEL\">%charname%</font>!<br>");
			strBuffer.append("How dare you to talk with me while you're sitting?!<br>");
		}
		else if (player.isAlikeDead())
		{
			player.sendMessage("You can't use the buffer while you're dead or using fake death.");
			strBuffer.append("Sadly, <font color=\"LEVEL\">%charname%</font>, you're dead.<br>");
			strBuffer.append("I can't offer any support effect for dead people...<br>");
		}
		else if (player.isInCombat())
		{
			player.sendMessage("You can't use the buffer while you're in combat.");
			strBuffer.append("Sadly, <font color=\"LEVEL\">%charname%</font>, I can't serve you.<br>");
			strBuffer.append("Came back when you will not be in a combat.<br>");
		}
		else if (player.getPet() == null)
		{
			player.sendMessage("You can't use the buffer. Summon your pet.");
			strBuffer.append("Sadly, <font color=\"LEVEL\">%charname%</font>, I can't serve you.<br>");
		}
		else
		{
			strBuffer.append("Welcome, <font color=\"LEVEL\">%charname%</font>!<br>");
			strBuffer.append("Here is the list of all available effects:<br>");
			strBuffer.append("<table width=300>");
			strBuffer.append("<tr><td><a action=\"bypass -h npc_%objectId%_getbuff 1204 1\">Wind Walk</a></td> <td><a action=\"bypass -h npc_%objectId%_getbuff 1040 1\">Shield</a></td> <td><a action=\"bypass -h npc_%objectId%_getbuff 1389 1\">Greater Shield</td></tr>");
			strBuffer.append("<tr><td><a action=\"bypass -h npc_%objectId%_getbuff 1068 1\">Might</a></td> <td><a action=\"bypass -h npc_%objectId%_getbuff 1036 1\">Magic Barrier</a></td> <td><a action=\"bypass -h npc_%objectId%_getbuff 1259 1\">Resist Shock</a></td></tr>");
			strBuffer.append("<tr><td><a action=\"bypass -h npc_%objectId%_getbuff 1035 1\">Mental Shield</a></td> <td><a action=\"bypass -h npc_%objectId%_getbuff 1045 1\">Blessed Body</a></td> <td><a action=\"bypass -h npc_%objectId%_getbuff 1304 1\">Advanced Block</a></td></tr>");
			strBuffer.append("<tr><td><a action=\"bypass -h npc_%objectId%_getbuff 1048 1\">Blessed Soul</a></td> <td><a action=\"bypass -h npc_%objectId%_getbuff 1062 1\">Berserker Spirit</a></td> <td><a action=\"bypass -h npc_%objectId%_getbuff 1189 1\">Resist Wind</a></td></tr>");
			strBuffer.append("<tr><td><a action=\"bypass -h npc_%objectId%_getbuff 1086 1\">Haste</a></td> <td><a action=\"bypass -h npc_%objectId%_getbuff 1240 1\">Guidance</a></td> <td><a action=\"bypass -h npc_%objectId%_getbuff 1393 1\">Unholy Resistance</a></td></tr>");
			strBuffer.append("<tr><td><a action=\"bypass -h npc_%objectId%_getbuff 1242 1\">Death Whisper</a></td> <td><a action=\"bypass -h npc_%objectId%_getbuff 1077 1\">Focus</a></td> <td><a action=\"bypass -h npc_%objectId%_getbuff 1353 1\">Divine Protection</a></td></tr>");
			strBuffer.append("<tr><td><a action=\"bypass -h npc_%objectId%_getbuff 1268 1\">Vampiric Rage</a></td> <td><a action=\"bypass -h npc_%objectId%_getbuff 1087 1\">Agility</a></td> <td><a action=\"bypass -h npc_%objectId%_getbuff 1352 1\">Elemental Protection</a></td></tr>");
			strBuffer.append("<tr><td><a action=\"bypass -h npc_%objectId%_getbuff 1085 1\">Acumen</a></td> <td><a action=\"bypass -h npc_%objectId%_getbuff 1059 1\">Empower</a></td> <td><a action=\"bypass -h npc_%objectId%_getbuff 1388 1\">Greater Might</a></td></tr>");
			strBuffer.append("<tr><td></td><td></td><td></td></tr>");
			strBuffer.append("<tr><td></td></tr>");
			strBuffer.append("<tr><td><font color=\"ff9900\">Other:</font></td></tr>");
			strBuffer.append("<tr><td><a action=\"bypass -h npc_%objectId%_cancel\"><font color=\"ffffff\">Cancel</font></a></td> <td><a action=\"bypass -h npc_%objectId%_restore\"><font color=\"ffffff\">Restore</font></a></td></tr>");
		}
		strBuffer.append("</center></body></html>");
		html.setHtml(strBuffer.toString());
		html.replace("%objectId%", String.valueOf(getObjectId()));
		html.replace("%charname%", player.getName());
		player.sendPacket(html);
	}
}