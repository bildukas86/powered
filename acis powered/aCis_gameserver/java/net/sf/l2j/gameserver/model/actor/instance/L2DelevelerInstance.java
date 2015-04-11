package net.sf.l2j.gameserver.model.actor.instance;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.network.serverpackets.SocialAction;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;

public class L2DelevelerInstance extends L2NpcInstance
{
	private static int newlevel = 0;
	
	public L2DelevelerInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		if (command.startsWith("delevel"))
		{
			StringTokenizer s = new StringTokenizer(command);
			s.nextToken();
			
			try
			{
				newlevel = Integer.parseInt(s.nextToken());
			}
			catch (NoSuchElementException e)
			{
				player.sendMessage("Please enter the level you wish to have.");
				mainHtm(player, false);
				return;
			}
			
			if (newlevel == 0)
			{
				player.sendMessage("You cannot delevel to level 0.");
				mainHtm(player, false);
				return;
			}
			else if (newlevel >= player.getLevel())
			{
				player.sendMessage("You cannot delevel to the same or higher level than you are.");
				mainHtm(player, false);
				return;
			}
			mainHtm(player, true);
			return;
		}
		else if (command.startsWith("confirmed"))
		{
			if (player.getAdena() >= 1000000)
			{
				for (L2Skill skill : player.getAllSkills())
				{
					L2Skill[] a = SkillTable.getNobleSkills();
					L2Skill[] b = SkillTable.getHeroSkills();
					L2Skill[] c = SkillTable.getInstance().getSiegeSkills(true);
					
					if (a.equals(skill) || b.equals(skill) || c.equals(skill))
						continue;
					
					if (skill.getId() == L2Skill.SKILL_DIVINE_INSPIRATION)
						continue;
					
					player.removeSkill(skill);
				}
				
				MagicSkillUse mgc = new MagicSkillUse(this, player, 1265, 1, 500, 0);
				player.broadcastPacket(mgc);
				player.broadcastPacket(new SocialAction(player, 8));
				player.reduceAdena("", 1000000, player, true);
				long exp = player.getStat().getExpForLevel(newlevel);
				if (player.isSubClassActive() && newlevel < 40)
				{
					newlevel = 40;
					player.getStat().setExp(newlevel);
					player.getStat().setLevel((byte) newlevel);
				}
				else
				{
					player.getStat().setExp(exp);
					player.getStat().setLevel((byte) newlevel);
				}
				player.sendMessage("You have been deleveled to level " + newlevel + ".");
				player.giveAvailableSkills();
				newlevel = 0;
				return;
			}
			player.sendMessage("You don't have enough adena.");
			return;
		}
	}
	
	@Override
	public void showChatWindow(L2PcInstance player)
	{
		mainHtm(player, false);
	}
	
	private void mainHtm(L2PcInstance player, boolean confirm)
	{
		NpcHtmlMessage nhm = new NpcHtmlMessage(5);
		StringBuilder sb = new StringBuilder("");
		
		sb.append("<html><head><title>Deleveler</title></head><body>");
		sb.append("<center><font color=\"333333\" align=\"center\">_______________________________________</font><br>");
		sb.append("<table width=224><tr>");
		sb.append("<td width=32><img src=alphabet.alphabet_d height=32 width=32></td>");
		sb.append("<td width=32><img src=alphabet.alphabet_e height=32 width=32></td>");
		sb.append("<td width=32><img src=alphabet.alphabet_l height=32 width=32></td>");
		sb.append("<td width=32><img src=alphabet.alphabet_e height=32 width=32></td>");
		sb.append("<td width=32><img src=alphabet.alphabet_v height=32 width=32></td>");
		sb.append("<td width=32><img src=alphabet.alphabet_e height=32 width=32></td>");
		sb.append("<td width=32><img src=alphabet.alphabet_l height=32 width=32></td>");
		sb.append("</tr></table><br><font color=\"333333\" align=\"center\">_______________________________________</font><br>");
		if (confirm)
		{
			sb.append("<br>Are you sure you want to delevel from level <font color=\"LEVEL\">" + player.getLevel() + "</font> to <font color=\"LEVEL\">" + (player.isSubClassActive() && newlevel < 40 ? "40" : newlevel) + "</font>?<br><br>");
			sb.append("<br>Don't forget that costs <font color=\"LEVEL\">1.000.000 adena</font>.<br><br>");
			sb.append("<br><font color=\"FF0000\">If you are on a subclass then maximum delevel is 40</font>.<br><br>");
			sb.append("<br>");
			sb.append("<button value=\"Yes\" action=\"bypass -h npc_" + getObjectId() + "_confirmed\" width=211 height=22 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
		}
		else
		{
			sb.append("<br>Hello dear player! Please enter the level<br> you want to have.<br><br>");
			sb.append("<br>You can only put a number below your actual level.<br>That costs <font color=\"LEVEL\">1.000.000 adena</font>.<br><br>");
			sb.append("<table><tr>");
			sb.append("<td align=\"center\"><font color=\"LEVEL\"> >>> </font></td><td align=\"center\"><edit var=\"level\" width=40 type=number><font color=\"LEVEL\"></td><td align=\"center\"> <<< </font></td>");
			sb.append("</tr></table><br>");
			sb.append("<br>");
			sb.append("<button value=\"Delevel\" action=\"bypass -h npc_" + getObjectId() + "_delevel $level\" width=211 height=21 back=\"sek.cbui75\" fore=\"sek.cbui75\"><br>");
		}
		sb.append("</center>");
		sb.append("</body></html>");
		nhm.setHtml(sb.toString());
		player.sendPacket(nhm);
	}
}