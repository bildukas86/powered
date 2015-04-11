package Extensions.TitleNameColorsPKPvP;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

/**
 * @author xTreme
 */
public class ColorStatus
{
	/**
	 * Update pvp color.
	 * @param player 
	 * @check if character isGM() return;
	 * @check if system is enabled
	 */
	public static void updatePvPColor(L2PcInstance player)
	{
		if (Config.PVP_COLOR_SYSTEM_ENABLED)
		{
			int PVP = player.getPvpKills();
			if (player.isGM())
				return;
			
			if (PVP >= Config.PVP_AMOUNT1 && PVP < Config.PVP_AMOUNT2)
				player.getAppearance().setNameColor(Config.NAME_COLOR_FOR_PVP_AMOUNT1);
			else if (PVP >= Config.PVP_AMOUNT2 && PVP < Config.PVP_AMOUNT3)
				player.getAppearance().setNameColor(Config.NAME_COLOR_FOR_PVP_AMOUNT2);
			else if (PVP >= Config.PVP_AMOUNT3 && PVP < Config.PVP_AMOUNT4)
				player.getAppearance().setNameColor(Config.NAME_COLOR_FOR_PVP_AMOUNT3);
			else if (PVP >= Config.PVP_AMOUNT4 && PVP < Config.PVP_AMOUNT5)
				player.getAppearance().setNameColor(Config.NAME_COLOR_FOR_PVP_AMOUNT4);
			else if (PVP >= Config.PVP_AMOUNT5)
				player.getAppearance().setNameColor(Config.NAME_COLOR_FOR_PVP_AMOUNT5);
		}
	}
	
	/**
	 * Update pk color.
	 * @param player 
	 * @check if character isGM() return;
	 * @check if system is enabled
	 */
	public static void updatePkColor(L2PcInstance player)
	{
		if (Config.PK_COLOR_SYSTEM_ENABLED)
		{
			int PK = player.getPvpKills();
			if (player.isGM())
				return;
			
			if (PK >= Config.PK_AMOUNT1 && PK < Config.PVP_AMOUNT2)
				player.getAppearance().setTitleColor(Config.TITLE_COLOR_FOR_PK_AMOUNT1);
			else if (PK >= Config.PK_AMOUNT2 && PK < Config.PVP_AMOUNT3)
				player.getAppearance().setTitleColor(Config.TITLE_COLOR_FOR_PK_AMOUNT2);
			else if (PK >= Config.PK_AMOUNT3 && PK < Config.PVP_AMOUNT4)
				player.getAppearance().setTitleColor(Config.TITLE_COLOR_FOR_PK_AMOUNT3);
			else if (PK >= Config.PK_AMOUNT4 && PK < Config.PVP_AMOUNT5)
				player.getAppearance().setTitleColor(Config.TITLE_COLOR_FOR_PK_AMOUNT4);
			else if (PK >= Config.PK_AMOUNT5)
				player.getAppearance().setTitleColor(Config.TITLE_COLOR_FOR_PK_AMOUNT5);
		}
	}
}