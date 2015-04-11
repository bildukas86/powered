package Extensions.CCB.Manager;

import java.util.StringTokenizer;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.cache.HtmCache;
import net.sf.l2j.gameserver.communitybbs.Manager.BaseBBSManager;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.zone.ZoneId;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;

public class BuffBBSManager extends BaseBBSManager
{
	@Override
	public void parseCmd(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("_bbsbuff"))
		{
			if (!checkAllowed(activeChar))
				return;
			{
				String val = command.substring(8);
				StringTokenizer st = new StringTokenizer(val, "_");
				
				String a = st.nextToken();
				int id = Integer.parseInt(a);
				String b = st.nextToken();
				int lvl = Integer.parseInt(b);
				
				L2Skill skill = SkillTable.getInstance().getInfo(id, lvl);
				if (skill != null)
				{
					skill.getEffects(activeChar, activeChar);
					activeChar.broadcastPacket(new MagicSkillUse(activeChar, activeChar, id, lvl, 500, 0));
				}
				
				String filename = "data/html/CommunityBoard/Custom/buffer.htm";
				String content = HtmCache.getInstance().getHtm(filename);
				
				separateAndSend(content, activeChar);
			}
		}
		
	}
	
	@Override
	public void parseWrite(String ar1, String ar2, String ar3, String ar4, String ar5, L2PcInstance activeChar)
	{
		
	}
	
	/**
	 * @param activeChar
	 * @return <br>
	 * <br>
	 *         check if player may use additional Community board functions. Such as buffer, gatekeeper.
	 */
	public boolean checkAllowed(L2PcInstance activeChar)
	{
		String msg = null;
		
		if (activeChar.isSitting())
			msg = "You can't use Community Gatekeeper when you sit!";
		else if (activeChar.isCastingNow())
			msg = "You can't use Community Gatekeeper when you cast!";
		else if (activeChar.isDead())
			msg = "You can't use Community Gatekeeper when you are dead!";
		else if (activeChar.isInCombat())
			msg = "You can't use Community Gatekeeper while you are in combat!";
		else if (activeChar.isInOlympiadMode())
			msg = "You can't use Community Gatekeeper in Olympiad!";
		else if (Config.COMMUNITY_GATEKEEPER_EXCLUDE_ON.contains("JAIL") && activeChar.isInJail())
			msg = "You can't use Community Gatekeeper in jail!";
		else if (Config.COMMUNITY_GATEKEEPER_EXCLUDE_ON.contains("KARMA") && activeChar.getKarma() > 0)
			msg = "You can't use Community Gatekeeper while you have karma!";
		else if (Config.COMMUNITY_GATEKEEPER_EXCLUDE_ON.contains("CURSED") && activeChar.isCursedWeaponEquipped())
			msg = "You can't use Community Gatekeeper when carring Cursed Weapon!";
		else if (Config.COMMUNITY_GATEKEEPER_EXCLUDE_ON.contains("SEVEN") && activeChar.isIn7sDungeon())
			msg = "You can't use Community Gatekeeper on 7 Signs!";
		else if (Config.COMMUNITY_GATEKEEPER_EXCLUDE_ON.contains("RB") && activeChar.isInsideZone(ZoneId.NO_SUMMON_FRIEND))
			msg = "You can't use Community Gatekeeper on Raid Zone!";
		else if (Config.COMMUNITY_GATEKEEPER_EXCLUDE_ON.contains("PVP") && activeChar.isInsideZone(ZoneId.PVP))
			msg = "You can't use Community Gatekeeper on PvP Zone!";
		else if (Config.COMMUNITY_GATEKEEPER_EXCLUDE_ON.contains("PEACE") && activeChar.isInsideZone(ZoneId.PEACE))
			msg = "You can't use Community Gatekeeper on Peace Zone!";
		else if (Config.COMMUNITY_GATEKEEPER_EXCLUDE_ON.contains("NOTINTOWN") && !activeChar.isInsideZone(ZoneId.TOWN))
			msg = "You can't use Community Gatekeeper outside town!";
		else if (Config.COMMUNITY_GATEKEEPER_EXCLUDE_ON.contains("SIEGE") && activeChar.isInsideZone(ZoneId.SIEGE))
			msg = "You can't use Community Gatekeeper on siege!";
		
		if (msg != null)
			activeChar.sendMessage(msg);
		
		return msg == null;
	}
	
	/**
	 * @return
	 */
	public static BuffBBSManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final BuffBBSManager INSTANCE = new BuffBBSManager();
	}
}