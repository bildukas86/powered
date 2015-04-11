package Extensions.AdminCommands;

import Extensions.Vip.VIPEngine;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.util.StringUtil;

public class AdminVipCommands implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_viplist",
		"admin_setvip",
		"admin_removevip",
		"admin_vipmessage",
		"admin_cleanvips" // JUST FOR TEST!!!! DON'T USE IT ON LIVE SERVER
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_setvip"))
			addVips(activeChar);
		else if (command.startsWith("admin_removevip"))
			removeVips(activeChar);
		else if (command.startsWith("admin_viplist"))
			showAllVips(activeChar);
		else if (command.startsWith("admin_vipmessage"))
		{
			if (VIPEngine.getInstance().getAllVips().isEmpty())
				activeChar.sendMessage("There is no vip to send a message");
			
			VIPEngine.getInstance().broadcastMessageToVips("Message to vip users: " + command.substring(17));
		}
		// Not recommend to use. Set vipCleanUp as void only not public. Just for test.
		else if (command.startsWith("admin_cleanvips"))
			VIPEngine.getInstance().vipCleanUp();
		
		return true;
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
			html.replace("%users%", "No vip users found. ");
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
	
	private static void removeVips(L2PcInstance activeChar)
	{
		String text;
		L2Object object = activeChar.getTarget();
		L2PcInstance target = (L2PcInstance) object;
		
		if (target == null)
			activeChar.sendMessage("Usage: //removevip and target someone.");
		
		if (!VIPEngine.getInstance().isVip(target))
		{
			text = "The user is not vip, you can't remove any status.";
			return;
		}
		
		VIPEngine.getInstance().deleteVip(target);
		
		if (Config.VIPS_HERO_AURA)
			activeChar.broadcastUserInfo();
		
		if (Config.VIPS_SKILLS_CONFIG)
		{
			for (int skillid : Config.VIP_SKILLS.keySet())
			{
				L2Skill skill = SkillTable.getInstance().getInfo(skillid, Config.VIP_SKILLS.get(skillid));
				activeChar.removeSkill(skill);
			}
		}
		
		if (Config.VIP_NAME_COLOR_CONFIG)
		{
			Integer colorName = Integer.decode("0xFFFFFF");
			Integer colorTitle = Integer.decode("0xFFFF77");
			activeChar.getAppearance().setNameColor(colorName);
			activeChar.getAppearance().setTitleColor(colorTitle);
		}
		
		text = "You have remove vip status to " + activeChar.getName() + ".";
		
		activeChar.sendMessage(text);
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}