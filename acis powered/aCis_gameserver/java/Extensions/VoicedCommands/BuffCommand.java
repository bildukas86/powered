package Extensions.VoicedCommands;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.zone.ZoneId;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class BuffCommand implements IVoicedCommandHandler
{
	private final String[] _voicedCommands =
	{
		"buff"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		if (check(activeChar))
			showHtml(activeChar);
		
		return true;
	}
	
	public static void getFullBuff(L2PcInstance p, boolean isClassMage)
	{
		if (check(p))
		{
			if (isClassMage)
			{
				for (int b : Config.BUFF_COMMAND_MAGE_IDBUFFS)
					SkillTable.getInstance().getInfo(b, SkillTable.getInstance().getMaxLevel(b)).getEffects(p, p);
			}
			else
			{
				for (int b : Config.BUFF_COMMAND_FIGHT_IDBUFFS)
					SkillTable.getInstance().getInfo(b, SkillTable.getInstance().getMaxLevel(b)).getEffects(p, p);
			}
			p.sendMessage("[Buff Command]: Voice for buffs!");
		}
	}
	
	public static boolean check(L2PcInstance p)
	{
		return p.isInsideZone(ZoneId.PEACE) && !p.isInCombat() && !p.isInOlympiadMode();
	}
	
	public static void showHtml(L2PcInstance player)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		html.setFile("data/html/mods/VoicedBuff/buffCommand.htm");
		html.replace("%currentBuffs%", player.getBuffCount());
		html.replace("%getMaxBuffs%", player.getMaxBuffCount());
		player.sendPacket(html);
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return _voicedCommands;
	}
}