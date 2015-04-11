package net.sf.l2j.gameserver.model.actor.instance;

import java.util.StringTokenizer;

import net.sf.l2j.gameserver.buffer.BufferHtml;
import net.sf.l2j.gameserver.buffer.BufferParser;
import net.sf.l2j.gameserver.buffer.BufferParser.Buff;
import net.sf.l2j.gameserver.buffer.L2Buffer;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.network.clientpackets.Say2;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class L2BuffInstance extends L2NpcInstance
{
	public L2BuffInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}

	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		BufferParser bp = BufferParser.getInstance();
		if(command.equalsIgnoreCase("mainWindow"))
			BufferHtml.getInstance().mainWindow(player, getObjectId());
		else if(command.toLowerCase().startsWith("chat"))
		{
			String val = command.substring(5);
			StringTokenizer st = new StringTokenizer(val, ";");

			if(st.countTokens() > 4 || st.countTokens() < 1)
				return;

			String page = st.nextToken();
			String type = st.nextToken();
			int pageId = Integer.parseInt(page);
			BufferHtml.getInstance().buffWindow(player, pageId, type, getObjectId());
		}
		else if(command.toLowerCase().startsWith("buff"))
		{
			String val = command.substring(5);
			StringTokenizer st = new StringTokenizer(val, ";");

			if(st.countTokens() != 3)
				return;

			int pageId = Integer.parseInt(st.nextToken());
			String buffId = st.nextToken();
			String type = st.nextToken();

			Buff buff = bp.getBuffByBuffId(Integer.parseInt(buffId));
			if (buff != null)
			{
				SkillTable.getInstance().getInfo(buff.getId(), buff.getLevel()).getEffects(player, player);
				BufferHtml.getInstance().buffWindow(player, pageId, type, getObjectId());
			}
		}
		else if(command.toLowerCase().startsWith("schemebuffs"))
		{
			String val = command.substring(12);
			StringTokenizer st = new StringTokenizer(val, ",");

			if(st.countTokens() != 2)
				return;

			int buffId = Integer.parseInt(st.nextToken());
			boolean add = st.nextToken().equals("0");

			Buff buff = bp.getBuffByBuffId(buffId);
			if (buff != null)
			{
				String buffInfo = buff.getId() + "," + buff.getLevel();
				L2Buffer.getInstance().changeList(player, buffInfo.split(","), add);
				L2Buffer.getInstance().showHtml(player, getObjectId());
			}
			
		}
		else if(command.toLowerCase().startsWith("schemebuff"))
		{
			String val = command.substring(11);
			StringTokenizer st = new StringTokenizer(val, ";");

			if(st.countTokens() != 4)
				return;

			int pageId = Integer.parseInt(st.nextToken());
			int buffId = Integer.parseInt(st.nextToken());
			String type = st.nextToken();
			boolean add = st.nextToken().equals("0");

			Buff buff = bp.getBuffByBuffId(buffId);
			if (buff != null)
			{
				if (add && L2Buffer.getInstance().getBuffTemplate(player.getObjectId() + "" + player.getClassIndex()) != null && player.getMaxBuffCount() - L2Buffer.getInstance().getBuffTemplate(player.getObjectId() + "" + player.getClassIndex()).size() < 1)
				{
					player.sendPacket(new CreatureSay(0, Say2.PARTYROOM_ALL, getName(), "Your scheme has already max count of buffs. If you wanna add this buff you need to remove other buff."));
					return;
				}
				String buffInfo = buff.getId() + "," + buff.getLevel();
				L2Buffer.getInstance().changeList(player, buffInfo.split(","), add);
				BufferHtml.getInstance().buffWindow(player, pageId, type, getObjectId());
			}
			
		}
		else if(command.equalsIgnoreCase("heal"))
		{
			player.setCurrentCp(player.getMaxCp());
			player.setCurrentHp(player.getMaxHp());
			player.setCurrentMp(player.getMaxMp());
			BufferHtml.getInstance().mainWindow(player, getObjectId());
		}
		else if(command.equalsIgnoreCase("cancel"))
		{
			player.stopAllEffects();
			BufferHtml.getInstance().mainWindow(player, getObjectId());
		}

		else if(command.equalsIgnoreCase("wBuffSet"))
		{
			for (Buff buff : bp.getBuffs())
				if (buff.getBuffSet().equalsIgnoreCase("fighter") || buff.getBuffSet().equalsIgnoreCase("all"))
					SkillTable.getInstance().getInfo(buff.getId(), buff.getLevel()).getEffects(player, player);
			BufferHtml.getInstance().mainWindow(player, getObjectId());
		}
		else if(command.equalsIgnoreCase("mBuffSet"))
		{
			for (Buff buff : bp.getBuffs())
				if (buff.getBuffSet().equalsIgnoreCase("mage") || buff.getBuffSet().equalsIgnoreCase("all"))
					SkillTable.getInstance().getInfo(buff.getId(), buff.getLevel()).getEffects(player, player);
			BufferHtml.getInstance().mainWindow(player, getObjectId());
		}
		else if(command.equalsIgnoreCase("addedbuffs"))
			L2Buffer.getInstance().showHtml(player, getObjectId());
		else if(command.equalsIgnoreCase("firstwindow"))
			showChatWindow(player, 0);
		else if (command.equalsIgnoreCase("usescheme"))
			L2Buffer.getInstance().buffPlayer(player);
		else
			super.onBypassFeedback(player, command);
	}

	@Override
	public void showChatWindow(L2PcInstance player, int val)
	{
		if (player.isInCombat()) {
			player.sendMessage("Players in combat can't use me.");
			return;
		}
		String fileName = "data/html/Buffer/main.htm";
		NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
		html.setFile(fileName);
		html.replace("%objectId%", String.valueOf(getObjectId()));
		player.sendPacket(html);
		player.sendPacket(ActionFailed.STATIC_PACKET);
	}
}