package Extensions.CCB.Manager;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.cache.HtmCache;
import net.sf.l2j.gameserver.communitybbs.Manager.BaseBBSManager;
import net.sf.l2j.gameserver.datatables.TeleportLocationTable;
import net.sf.l2j.gameserver.model.L2TeleportLocation;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.zone.ZoneId;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;

import java.util.StringTokenizer;

public class TeleBBSManager extends BaseBBSManager
{
	@Override
	public void parseCmd(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("_bbstele;"))
		{
			if (!checkAllowed(activeChar))
				return;
			{
				StringTokenizer st = new StringTokenizer(command, ";");
				st.nextToken();
				int id = Integer.parseInt(st.nextToken());
				
				doTeleport(activeChar, id);
				
				String filename = "data/html/CommunityBoard/Custom/gk.htm";
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
	 *         Check if player may use additional Community board functions. Such as buffer, gatekeeper.
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
	
	private static void doTeleport(L2PcInstance player, int val)
	{
		L2TeleportLocation list = TeleportLocationTable.getInstance().getTemplate(val);
		if (list != null)
		{
			player.teleToLocation(list.getLocX(), list.getLocY(), list.getLocZ(), 0);
		}
		else
			System.out.println("No teleport destination with id:" + val);
		
		player.sendPacket(ActionFailed.STATIC_PACKET);
	}
	
	public static TeleBBSManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final TeleBBSManager INSTANCE = new TeleBBSManager();
	}
}