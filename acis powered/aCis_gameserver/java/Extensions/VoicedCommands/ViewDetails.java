package Extensions.VoicedCommands;

import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.olympiad.Olympiad;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class ViewDetails implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{
		"details"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		if (command.equalsIgnoreCase("details"))
		{
			if (activeChar.getTarget() == null)
			{
				activeChar.sendMessage("You have no one targeted.");
				return false;
			}
			if (!(activeChar.getTarget() instanceof L2PcInstance))
			{
				activeChar.sendMessage("You can only get the info of a player.");
				
				return false;
			}
			
			L2PcInstance targetp = (L2PcInstance) activeChar.getTarget();
			L2PcInstance noble = activeChar;
			
			if (!targetp.isInViewProt())
			{
				
				NpcHtmlMessage nhm = new NpcHtmlMessage(5);
				StringBuilder tb = new StringBuilder("");
				
				tb.append("<html><head><title>" + targetp.getName() + " Stats </title></head><body>");
				tb.append("<center>");
				tb.append("Level: " + targetp.getLevel() + "<br1>");
				if (targetp.getClan() != null)
				{
					tb.append("Clan: " + targetp.getClan().getName() + "<br1>");
					tb.append("Alliance: " + targetp.getClan().getAllyName() + "<br1>");
				}
				else
				{
					tb.append("Alliance: None <br1>");
					tb.append("Clan: None <br1>");
				}
				tb.append("Weap Enchant: " + targetp.getInventory().getPaperdollItem(7).getEnchantLevel() + "<br1>");
				
				if (target != null && targetp.isNoble())
					noble = targetp;
				
				tb.append("Olympiads <br1>");
				tb.append("Competitions Done: " + Olympiad.getInstance().getCompetitionDone(noble.getObjectId()) + "<br1>");
				tb.append("Competitions Won: " + Olympiad.getInstance().getCompetitionWon(noble.getObjectId()) + "<br1>");
				tb.append("Competitions Lost: " + Olympiad.getInstance().getCompetitionLost(noble.getObjectId()) + "<br1>");
				tb.append("Points: " + Olympiad.getInstance().getNoblePoints(noble.getObjectId()) + "<br1>");
				
				tb.append("PvP Kills: " + targetp.getPvpKills() + "<br1>");
				tb.append("PvP Flags: " + targetp.getPvpFlag() + "<br1>");
				tb.append("PK Kills: " + targetp.getPkKills() + "<br1>");
				tb.append("HP, CP, MP: " + targetp.getMaxHp() + ", " + targetp.getMaxCp() + ", " + targetp.getMaxMp() + "<br1>");
				tb.append("Adena: " + targetp.getAdena() + "<br1>");
				if (targetp.isAio())
				{
					tb.append("AIO: Yes <br1>");
				}
				else
				{
					tb.append("AIO: No <br1>");
				}
				tb.append("</center>");
				tb.append("</body></html>");
				
				nhm.setHtml(tb.toString());
				activeChar.sendPacket(nhm);
			}
			if (targetp.isInViewProt())
			{
				activeChar.sendMessage("Sorry but " + targetp.getName() + " doesnt want that other ppl knows his stats");
			}
		}
		return true;
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return VOICED_COMMANDS;
	}
}