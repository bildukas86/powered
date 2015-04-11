package net.sf.l2j.gameserver.model.actor.instance;

import Extensions.Vip.VIPEngine;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.StringTokenizer;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.util.StringUtil;

public class L2VipManagerInstance extends L2NpcInstance
{
	
	public L2VipManagerInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public String getHtmlPath(int npcId, int val)
	{
		return "data/html/mods/Vip/vipmanager.htm";
	}
	
	@Override
	public void showChatWindow(L2PcInstance player)
	{
		if (!Config.ENABLE_VIP_NPC)
		{
			showChatWindow(player, "data/html/mods/Vip/npcisdisabled.htm");
			return;
		}
			showChatWindow(player, player.isGM() ? "data/html/mods/Vip/adminmanage.htm" : "data/html/mods/Vip/vipmanager.htm");
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		StringTokenizer st = new StringTokenizer(command);
		String actualCommand = st.nextToken();
		
		if (actualCommand.equalsIgnoreCase("getVip"))
		{
			NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
			
			if (!VIPEngine.getInstance().isVip(player))
			{
				if (player.getInventory().hasAtLeastOneItem(Config.VIP_NPC_ITEM))
				{
					addVips(player);
					player.destroyItem("_vip", Config.VIP_NPC_ITEM, 1, player, true);
					html.setFile("data/html/mods/Vip/vipmanager.htm");
					player.sendPacket(html);
				}
				else
				{
					html.setFile("data/html/mods/Vip/noenoughitems.htm");
					html.replace("%objectId%", String.valueOf(getObjectId()));
					player.sendPacket(html);
				}
			}
			else
			{
				html.setFile("data/html/mods/Vip/alreadyvip.htm");
				html.replace("%objectId%", String.valueOf(getObjectId()));
				player.sendPacket(html);
			}
		}
		else if (actualCommand.equalsIgnoreCase("checkVipTime"))
		{
			NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
			
			if (VIPEngine.getInstance().isVip(player))
			{
				html.setFile("data/html/mods/Vip/checktime.htm");
				html.replace("%acquireDate%", convertToDate(VIPEngine.getInstance().getVipTime(player)));
				html.replace("%endDate%", convertToDate(VIPEngine.getInstance().getVipTime(player)) + Config.VIP_LIFE_TIME);
			}
			else
				html.setFile("data/html/mods/Vip/noenoughitems.htm");
			
			player.sendPacket(html);
		}
		else if (actualCommand.equalsIgnoreCase("cleantask"))
		{
			if (player.isGM())
				VIPEngine.getInstance().vipCleanUp();
			else
				player.sendMessage("You can't access this function.");
		}
		else if (actualCommand.equalsIgnoreCase("checklist"))
		{
			showAllVips(player);
		}
		
		else
			super.onBypassFeedback(player, actualCommand);
		
	}
	
	private static String convertToDate(long timeInMillis)
	{
		Date time = new Date(timeInMillis);
		SimpleDateFormat format = new SimpleDateFormat("MMM dd,yyyy HH:mm");
		
		return format.format(time);
	}
	
	/**
	 * Shows the html with a list of vips with or without timers
	 * @param activeChar
	 */
	private static void showAllVips(L2PcInstance activeChar)
	{
		Collection<L2PcInstance> players = L2World.getInstance().getAllPlayers().values();
		Collection<Integer> allVips = VIPEngine.getInstance().getAllVips();
		
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		html.setFile("data/html/mods/Vip/viplist.htm");
		
		if (allVips.isEmpty())
		{
			html.replace("%users%", "No vip users found.");
			html.replace("%totalvips%", "0");
			activeChar.sendPacket(html);
			return;
		}
		
		final StringBuilder reply = new StringBuilder(500 + allVips.size() * 200);
		
		String name, time, finishTime;
		
		for (L2PcInstance vips : players)
		{
			long vipTime = VIPEngine.getInstance().getVipTime(vips);
			
			if (allVips.contains(vips.getObjectId()))
			{
				name = vips.getName();
				Date timeDate = new Date(vipTime);
				Date finishDate = new Date(vipTime + Config.VIP_LIFE_TIME);
				SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm");
				time = dateFormat.format(timeDate);
				finishTime = dateFormat.format(finishDate);
				
				StringUtil.append(reply, "<tr><td>" + "Username: " + name + " Vip Start time: " + time + " Finish time: " + finishTime + "</td></tr>");
			}
		}
		
		html.replace("%users%", reply.toString());
		html.replace("%totalvips%", String.valueOf(allVips.size()));
		activeChar.sendPacket(html);
	}
	
	private static void addVips(L2PcInstance activeChar)
	{
		String text;
		L2Object object = activeChar.getTarget();
		L2PcInstance target = (L2PcInstance) object;
		
		if (target == null)
			activeChar.sendMessage("Usage: //setvip and target someone.");
		
		if (VIPEngine.getInstance().isVip(target))
		{
			text = "The user is already a vip player";
			return;
		}
		
		VIPEngine.getInstance().addVip(target);
		if (Config.VIP_NAME_COLOR_CONFIG)
		{
			Integer colorName = Integer.decode("0x" + Config.VIPS_NAME_COLOR);
			Integer colorTitle = Integer.decode("0x" + Config.VIPS_TITLE_COLOR);
			activeChar.getAppearance().setNameColor(colorName);
			activeChar.getAppearance().setTitleColor(colorTitle);
		}
		
		if (Config.VIPS_SKILLS_CONFIG)
		{
			for (int skillid : Config.VIP_SKILLS.keySet())
			{
				L2Skill skill = SkillTable.getInstance().getInfo(skillid, Config.VIP_SKILLS.get(skillid));
				activeChar.addSkill(skill, false);
			}
		}
		
		if (Config.VIPS_HERO_AURA)
			activeChar.broadcastUserInfo();
		
		text = "The user " + activeChar.getName() + " has been added to vip list.";
		
		activeChar.sendMessage(text);
	}
}