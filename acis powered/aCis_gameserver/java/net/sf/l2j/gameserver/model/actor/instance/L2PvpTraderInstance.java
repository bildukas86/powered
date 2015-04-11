package net.sf.l2j.gameserver.model.actor.instance;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.instance.L2NpcInstance;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;

public class L2PvpTraderInstance extends L2NpcInstance
{
	public L2PvpTraderInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		if (command.startsWith("setHero"))
		{
			if (player.isHero())
			{
				player.sendMessage("You are already hero.");
				return;
			}
			else if (player.getPvpKills() < Config.SET_HERO_PVP_COST)
			{
				player.sendMessage("You need " + Config.SET_HERO_PVP_COST + " pvp(s) to become hero until restart.");
				return;
			}
			else if (!player.isHero() && player.getPvpKills() >= Config.SET_HERO_PVP_COST)
			{
				player.setHero(true);
				player.setPvpKills(player.getPvpKills() - Config.SET_HERO_PVP_COST);
				player.broadcastUserInfo();
				player.sendMessage("You have become a server hero until restart, and " + Config.SET_HERO_PVP_COST + " pvp(s) were taken from you.");
			}
		}
		if (command.startsWith("setNoble"))
		{
			if (player.isNoble())
			{
				player.sendMessage("You are already noblesse.");
				return;
			}
			else if (player.getPvpKills() < Config.SET_NOBLE_PVP_COST)
			{
				player.sendMessage("You need " + Config.SET_NOBLE_PVP_COST + " pvp(s) to become noblesse.");
				return;
			}
			else if (!player.isNoble() && player.getPvpKills() >= Config.SET_NOBLE_PVP_COST)
			{
				player.setNoble(true, true);
				player.setPvpKills(player.getPvpKills() - Config.SET_NOBLE_PVP_COST);
				player.broadcastUserInfo();
				player.sendMessage("You have become noblesse, and " + Config.SET_NOBLE_PVP_COST + " pvp(s) were taken from you.");
			}
		}
		if (command.startsWith("addItem"))
		{
			if (player.getPvpKills() < Config.GIVE_ITEM_PVP_COST)
			{
				player.sendMessage("You need " + Config.SET_NOBLE_PVP_COST + " pvp(s) to get a reward.");
				return;
			}
			else if (player.getPvpKills() >= Config.GIVE_ITEM_PVP_COST)
			{
				player.addItem("PvpReward", Config.ITEM_ID, Config.ITEM_COUNT, player, true);
				player.setPvpKills(player.getPvpKills() - Config.GIVE_ITEM_PVP_COST);
				player.broadcastUserInfo();
				player.sendMessage("You have taken a reward and " + Config.GIVE_ITEM_PVP_COST + " pvp(s) have been taken from you. Check your inventory.");
			}
		}
		if (command.startsWith("addSkill"))
		{
			if (player.getPvpKills() < Config.GET_A_SKILL_PVP_COST)
			{
				player.sendMessage("You need " + Config.GET_A_SKILL_PVP_COST + " pvp(s) to get a skill.");
				return;
			}
			else if (player.getPvpKills() >= Config.GET_A_SKILL_PVP_COST)
			{
				L2Skill skill = null;
				skill = SkillTable.getInstance().getInfo(Config.SKILL_ID, Config.SKILL_LVL);
				player.addSkill(skill, true);
				player.setPvpKills(player.getPvpKills() - Config.GET_A_SKILL_PVP_COST);
				player.sendSkillList();
				player.broadcastUserInfo();
				player.sendMessage("You have learned " + skill.getName() + " lvl " + Config.SKILL_LVL + ", and " + Config.GET_A_SKILL_PVP_COST + " pvp(s) have been taken from you. Check your skills list.");
			}
		}
	}
}