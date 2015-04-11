package Extensions.Menu;

import Extensions.GearScore.GearScore;
import Extensions.Vip.VIPEngine;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.GameServer;
import net.sf.l2j.gameserver.GameTimeController;
import net.sf.l2j.gameserver.cache.HtmCache;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public final class Menu
{
	// Server Time
	private static String getServerRunTime()
	{
		int timeSeconds = (GameTimeController.getInstance().getGameTicks() - 36000) / 10;
		String timeResult = (timeSeconds >= 86400 ? Integer.toString(timeSeconds / 86400) + " Days " + Integer.toString((timeSeconds % 86400) / 3600) + " hours." : Integer.toString(timeSeconds / 3600) + " Hours " + Integer.toString((timeSeconds % 3600) / 60) + " mins.");
		return timeResult;
	}
	
	// Sends User Page
	public static void sendUserPage(L2PcInstance Player)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		String htm = HtmCache.getInstance().getHtm("data/html/mods/UserPanel/User.htm");
		html.setHtml(htm.toString());
		html.replace("%name%", String.valueOf(Player.getName()));
		html.replace("%servercapacity%", Integer.toString(Config.MAXIMUM_ONLINE_USERS));
		html.replace("%serverruntime%", getServerRunTime());
		html.replace("%onlineplayers%", Integer.toString(L2World.getInstance().getAllPlayersCount()));
		html.replace("%gearscore%", Integer.toString(GearScore.getGearScore(Player)));
		Player.sendPacket(html);
	}
	
	// Sends Panel Page replacements
	public static void sendUserPanelPage(L2PcInstance Player)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		if (Config.SKIN == 1)
		{
			
			html.setFile("data/html/mods/UserPanel/Skins/1.htm");
			html.replace("%name%", String.valueOf(Player.getName()));
			html.replace("%pm%", (Player.isInRefusalMode() ? "<font color=66FF00>ON</font>" : "<font color=FF0000>OFF</font>"));
			html.replace("%trade%", (Player.getTradeRefusal() ? "<font color=66FF00>ON</font>" : "<font color=FF0000>OFF</font>"));
			html.replace("%exchange%", (Player.getExchangeRefusal() ? "<font color=66FF00>ON</font>" : "<font color=FF0000>OFF</font>"));
			html.replace("%exp%", (Player.getExpSpRefusal() ? "<font color=66FF00>ON</font>" : "<font color=FF0000>OFF</font>"));
			html.replace("%buff%", (Player.isBuffProtected() ? "<font color=66FF00>ON</font>" : "<font color=FF0000>OFF</font>"));
			html.replace("%arrowss%", (Player.getUnlimitedArrowsSS() ? "<font color=66FF00>ON</font>" : "<font color=FF0000>OFF</font>"));
			html.replace("%effects%", (Player.getSsEffects() ? "<font color=66FF00>ON</font>" : "<font color=FF0000>OFF</font>"));
			
		}
		else if (Config.SKIN == 2)
		{
			html.setFile("data/html/mods/UserPanel/Skins/2.htm");
			html.replace("%name%", String.valueOf(Player.getName()));
			html.replace("%online%", Integer.toString(L2World.getInstance().getAllPlayersCount()));
			html.replace("%pm%", (Player.isInRefusalMode() ? "L2UI_CH3.br_bar1_mp" : "L2UI_CH3.br_bar1_hp"));
			html.replace("%trade%", (Player.getTradeRefusal() ? "L2UI_CH3.br_bar1_mp" : "L2UI_CH3.br_bar1_hp"));
			html.replace("%exchange%", (Player.getExchangeRefusal() ? "L2UI_CH3.br_bar1_mp" : "L2UI_CH3.br_bar1_hp"));
			html.replace("%exp%", (Player.getExpSpRefusal() ? "L2UI_CH3.br_bar1_mp" : "L2UI_CH3.br_bar1_hp"));
			html.replace("%buff%", (Player.isBuffProtected() ? "L2UI_CH3.br_bar1_mp" : "L2UI_CH3.br_bar1_hp"));
			html.replace("%arrowss%", (Player.getUnlimitedArrowsSS() ? "L2UI_CH3.br_bar1_mp" : "L2UI_CH3.br_bar1_hp"));
			html.replace("%effects%", (Player.getSsEffects() ? "L2UI_CH3.br_bar1_mp" : "L2UI_CH3.br_bar1_hp"));
		}
		else
		{
			Player.sendMessage("No skin for user panel blaim the server admin.");
		}
		Player.sendPacket(html);
	}
	
	// Sends Info Page
	public static void sendUserPanelInfoPage(L2PcInstance Player)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		String htm = HtmCache.getInstance().getHtm("data/html/mods/UserPanel/Info/info.htm");
		html.setHtml(htm.toString());
		html.replace("%name%", String.valueOf(Player.getName()));// Char Name
		Player.sendPacket(html);
	}
	
	// Sends Server Page
	public static void sendUserPanelInfoServerPage(L2PcInstance Player)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		String htm = HtmCache.getInstance().getHtm("data/html/mods/UserPanel/Info/server.htm");
		html.setHtml(htm.toString());
		html.replace("%name%", String.valueOf(Player.getName()));// Char Name
		Player.sendPacket(html);
	}
	
	// Sends Sub-menu Pages
	public static void sendUserPanelSubmenuPages(L2PcInstance Player, String path)
	{
		if (path.indexOf("..") != -1)
			return;
		final StringTokenizer st = new StringTokenizer(path);
		final String[] cmd = st.nextToken().split("#");
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		html.setFile("data/html/mods/UserPanel/" + cmd[0]);
		if (cmd.length > 1)
			html.setItemId(Integer.parseInt(cmd[1]));
		
		// Account Page
		html.replace("%name%", String.valueOf(Player.getName()));// Char Name
		// Defines the account if is vip or not
		html.replace("%accstatus%", String.valueOf(VIPEngine.getInstance().getAllVips().contains(Player.getObjectId()) ? "<font color=\"LEVEL\">VIP</font>" : "<font color=00FFFF>Normal</font>"));
		// Rate Configs Red if disabled green if enabled
		html.replace("%VIP_XP%", String.valueOf(!VIPEngine.getInstance().getAllVips().contains(Player.getObjectId()) ? "<font color=00FFFF>" + (int) Config.RATE_XP + "</font> (" + (Config.VIPS_RATE_CONFIG ? "<font color=\"LEVEL\">" + (int) Config.RATE_XP_VIP + "</font>" : "<font color=FF0000>" + (int) Config.RATE_XP_VIP + "</font>") + " for VIP)" : (Config.VIPS_RATE_CONFIG ? "<font color=\"LEVEL\">" + (int) Config.RATE_XP_VIP + "</font>" : "<font color=FF0000>" + (int) Config.RATE_XP_VIP + "</font>")));
		html.replace("%VIP_SP%", String.valueOf(!VIPEngine.getInstance().getAllVips().contains(Player.getObjectId()) ? "<font color=00FFFF>" + (int) Config.RATE_SP + "</font> (" + (Config.VIPS_RATE_CONFIG ? "<font color=\"LEVEL\">" + (int) Config.RATE_SP_VIP + "</font>" : "<font color=FF0000>" + (int) Config.RATE_SP_VIP + "</font>") + " for VIP)" : (Config.VIPS_RATE_CONFIG ? "<font color=\"LEVEL\">" + (int) Config.RATE_SP_VIP + "</font>" : "<font color=FF0000>" + (int) Config.RATE_SP_VIP + "</font>")));
		html.replace("%VIP_ADENA%", String.valueOf(!VIPEngine.getInstance().getAllVips().contains(Player.getObjectId()) ? "<font color=00FFFF>" + (int) Config.RATE_DROP_ADENA + "</font> (" + (Config.VIPS_RATE_CONFIG ? "<font color=\"LEVEL\">" + (int) Config.RATE_DROP_ADENA_VIP + "</font>" : "<font color=FF0000>" + (int) Config.RATE_DROP_ADENA_VIP + "</font>") + " for VIP)" : (Config.VIPS_RATE_CONFIG ? "<font color=\"LEVEL\">" + (int) Config.RATE_DROP_ADENA_VIP + "</font>" : "<font color=FF0000>" + (int) Config.RATE_DROP_ADENA_VIP + "</font>")));
		html.replace("%VIP_ITEMS%", String.valueOf(!VIPEngine.getInstance().getAllVips().contains(Player.getObjectId()) ? "<font color=00FFFF>" + (int) Config.RATE_DROP_ITEMS + "</font> (" + (Config.VIPS_RATE_CONFIG ? "<font color=\"LEVEL\">" + (int) Config.RATE_DROP_ITEMS_VIP + "</font>" : "<font color=FF0000>" + (int) Config.RATE_DROP_ITEMS_VIP + "</font>") + " for VIP)" : (Config.VIPS_RATE_CONFIG ? "<font color=\"LEVEL\">" + (int) Config.RATE_DROP_ITEMS_VIP + "</font>" : "<font color=FF0000>" + (int) Config.RATE_DROP_ITEMS_VIP + "</font>")));
		html.replace("%VIP_RAID_ITEMS%", String.valueOf(!VIPEngine.getInstance().getAllVips().contains(Player.getObjectId()) ? "<font color=00FFFF>" + (int) Config.RATE_DROP_ITEMS_BY_RAID + "</font> (" + (Config.VIPS_RATE_CONFIG ? "<font color=\"LEVEL\">" + (int) Config.RATE_DROP_ITEMS_BY_RAID_VIP + "</font>" : "<font color=FF0000>" + (int) Config.RATE_DROP_ITEMS_BY_RAID_VIP + "</font>") + " for VIP)" : (Config.VIPS_RATE_CONFIG ? "<font color=\"LEVEL\">" + (int) Config.RATE_DROP_ITEMS_BY_RAID_VIP + "</font>" : "<font color=FF0000>" + (int) Config.RATE_DROP_ITEMS_BY_RAID_VIP + "</font>")));
		html.replace("%VIP_SPOIL%", String.valueOf(!VIPEngine.getInstance().getAllVips().contains(Player.getObjectId()) ? "<font color=00FFFF>" + (int) Config.RATE_DROP_SPOIL + "</font> (" + (Config.VIPS_RATE_CONFIG ? "<font color=\"LEVEL\">" + (int) Config.RATE_DROP_SPOIL_VIP + "</font>" : "<font color=FF0000>" + (int) Config.RATE_DROP_SPOIL_VIP + "</font>") + " for VIP)" : (Config.VIPS_RATE_CONFIG ? "<font color=\"LEVEL\">" + (int) Config.RATE_DROP_SPOIL_VIP + "</font>" : "<font color=FF0000>" + (int) Config.RATE_DROP_SPOIL_VIP + "</font>")));
		// Enchant Configs Red if disabled green if enabled
		html.replace("%VIP_CWM%", String.valueOf(!VIPEngine.getInstance().getAllVips().contains(Player.getObjectId()) ? "<font color=00FFFF>" + Config.ENCHANT_CHANCE_WEAPON_MAGIC * 100 + "</font> (" + (Config.VIP_ENCHANT_CONFIG ? "<font color=\"LEVEL\">" + Config.VIP_ENCHANT_CHANCE_WEAPON_MAGIC * 100 + "</font>" : "<font color=FF0000>" + Config.VIP_ENCHANT_CHANCE_WEAPON_MAGIC * 100 + "</font>") + " for VIP)" : (Config.VIP_ENCHANT_CONFIG ? "<font color=\"LEVEL\">" + Config.VIP_ENCHANT_CHANCE_WEAPON_MAGIC * 100 + "</font>" : "<font color=FF0000>" + Config.VIP_ENCHANT_CHANCE_WEAPON_MAGIC * 100 + "</font>")));
		html.replace("%VIP_CWM15PLUS%", String.valueOf(!VIPEngine.getInstance().getAllVips().contains(Player.getObjectId()) ? "<font color=00FFFF>" + Config.ENCHANT_CHANCE_WEAPON_MAGIC_15PLUS * 100 + "</font> (" + (Config.VIP_ENCHANT_CONFIG ? "<font color=\"LEVEL\">" + Config.VIP_ENCHANT_CHANCE_WEAPON_MAGIC_15PLUS * 100 + "</font>" : "<font color=FF0000>" + Config.VIP_ENCHANT_CHANCE_WEAPON_MAGIC_15PLUS * 100 + "</font>") + " for VIP)" : (Config.VIP_ENCHANT_CONFIG ? "<font color=\"LEVEL\">" + Config.VIP_ENCHANT_CHANCE_WEAPON_MAGIC_15PLUS * 100 + "</font>" : "<font color=FF0000>" + Config.VIP_ENCHANT_CHANCE_WEAPON_MAGIC_15PLUS * 100 + "</font>")));
		html.replace("%VIP_CWNM%", String.valueOf(!VIPEngine.getInstance().getAllVips().contains(Player.getObjectId()) ? "<font color=00FFFF>" + Config.ENCHANT_CHANCE_WEAPON_NONMAGIC * 100 + "</font> (" + (Config.VIP_ENCHANT_CONFIG ? "<font color=\"LEVEL\">" + Config.VIP_ENCHANT_CHANCE_WEAPON_NONMAGIC * 100 + "</font>" : "<font color=FF0000>" + Config.VIP_ENCHANT_CHANCE_WEAPON_NONMAGIC * 100 + "</font>") + " for VIP)" : (Config.VIP_ENCHANT_CONFIG ? "<font color=\"LEVEL\">" + Config.VIP_ENCHANT_CHANCE_WEAPON_NONMAGIC * 100 + "</font>" : "<font color=FF0000>" + Config.VIP_ENCHANT_CHANCE_WEAPON_NONMAGIC * 100 + "</font>")));
		html.replace("%VIP_CWNM15PLUS%", String.valueOf(!VIPEngine.getInstance().getAllVips().contains(Player.getObjectId()) ? "<font color=00FFFF>" + Config.ENCHANT_CHANCE_WEAPON_NONMAGIC_15PLUS * 100 + "</font> (" + (Config.VIP_ENCHANT_CONFIG ? "<font color=\"LEVEL\">" + Config.VIP_ENCHANT_CHANCE_WEAPON_NONMAGIC_15PLUS * 100 + "</font>" : "<font color=FF0000>" + Config.VIP_ENCHANT_CHANCE_WEAPON_NONMAGIC_15PLUS * 100 + "</font>") + " for VIP)" : (Config.VIP_ENCHANT_CONFIG ? "<font color=\"LEVEL\">" + Config.VIP_ENCHANT_CHANCE_WEAPON_NONMAGIC_15PLUS * 100 + "</font>" : "<font color=FF0000>" + Config.VIP_ENCHANT_CHANCE_WEAPON_NONMAGIC_15PLUS * 100 + "</font>")));
		html.replace("%VIP_ARMOR%", String.valueOf(!VIPEngine.getInstance().getAllVips().contains(Player.getObjectId()) ? "<font color=00FFFF>" + Config.ENCHANT_CHANCE_ARMOR * 100 + "</font> (" + (Config.VIP_ENCHANT_CONFIG ? "<font color=\"LEVEL\">" + Config.VIP_ENCHANT_CHANCE_ARMOR * 100 + "</font>" : "<font color=FF0000>" + Config.VIP_ENCHANT_CHANCE_ARMOR * 100 + "</font>") + " for VIP)" : (Config.VIP_ENCHANT_CONFIG ? "<font color=\"LEVEL\">" + Config.VIP_ENCHANT_CHANCE_ARMOR * 100 + "</font>" : "<font color=FF0000>" + Config.VIP_ENCHANT_CHANCE_ARMOR * 100 + "</font>")));
		
		// Server - Rates
		html.replace("%rate_xp%", String.valueOf(Config.RATE_XP));
		html.replace("%rate_sp%", String.valueOf(Config.RATE_SP));
		html.replace("%rate_party_xp%", String.valueOf(Config.RATE_PARTY_XP));
		html.replace("%rate_adena%", String.valueOf(Config.RATE_DROP_ADENA));
		html.replace("%rate_party_sp%", String.valueOf(Config.RATE_PARTY_SP));
		html.replace("%rate_items%", String.valueOf(Config.RATE_DROP_ITEMS));
		html.replace("%rate_spoil%", String.valueOf(Config.RATE_DROP_SPOIL));
		html.replace("%rate_drop_manor%", String.valueOf(Config.RATE_DROP_MANOR));
		html.replace("%rate_quest_reward%", String.valueOf(Config.RATE_QUEST_REWARD));
		html.replace("%rate_drop_quest%", String.valueOf(Config.RATE_QUEST_DROP));
		html.replace("%pet_rate_xp%", String.valueOf(Config.PET_XP_RATE));
		html.replace("%sineater_rate_xp%", String.valueOf(Config.SINEATER_XP_RATE));
		html.replace("%pet_food_rate%", String.valueOf(Config.PET_FOOD_RATE));
		
		// Server - Misc
		html.replace("%deathpenalty%", Integer.toString(Config.DEATH_PENALTY_CHANCE));
		html.replace("%maxbuffs%", Integer.toString(Config.BUFFS_MAX_AMOUNT));
		html.replace("%maxdwarf%", Integer.toString(Config.INVENTORY_MAXIMUM_DWARF));
		html.replace("%maxnondwarf%", Integer.toString(Config.INVENTORY_MAXIMUM_NO_DWARF));
		html.replace("%slotdwarf%", Integer.toString(Config.WAREHOUSE_SLOTS_DWARF));
		html.replace("%slotnondwarf%", Integer.toString(Config.WAREHOUSE_SLOTS_NO_DWARF));
		html.replace("%slotclan%", Integer.toString(Config.WAREHOUSE_SLOTS_CLAN));
		html.replace("%maxally%", Integer.toString(Config.ALT_MAX_NUM_OF_CLANS_IN_ALLY));
		html.replace("%deletedays%", Integer.toString(Config.DELETE_DAYS));
		html.replace("%keyboard%", (Config.ALLOW_KEYBOARD_MOVEMENT ? "<font color=66FF00>Enabled</font>" : "<font color=FF0000>Disabled</font>"));
		html.replace("%aio%", (Config.ENABLE_AIO_SYSTEM ? "<font color=66FF00>Enabled</font>" : "<font color=FF0000>Disabled</font>"));
		html.replace("%multipleitem%", (Config.MULTIPLE_ITEM_DROP ? "<font color=66FF00>Enabled</font>" : "<font color=FF0000>Disabled</font>"));
		
		// Server - Basic
		Date dateInfo = new Date();
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
		String dayInfo = df.format(dateInfo);
		html.replace("%serverdateinfo%", String.valueOf(dayInfo));
		html.replace("%proccessors%", String.valueOf(Runtime.getRuntime().availableProcessors()));
		html.replace("%proccessor_id%", String.valueOf(System.getenv("PROCESSOR_IDENTIFIER")));
		html.replace("%osname%", String.valueOf(System.getProperty("os.name")));
		html.replace("%osbuild%", String.valueOf(System.getProperty("os.version")));
		html.replace("%architecture%", String.valueOf(System.getProperty("os.arch").equalsIgnoreCase("x86") ? "32Bit System" : "64Bit System"));
		html.replace("%server_free_mem%", String.valueOf((Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1048576));
		html.replace("%server_total_mem%", String.valueOf(Runtime.getRuntime().totalMemory() / 1048576));
		html.replace("%javaruntime%", String.valueOf(System.getProperty("java.runtime.name")));
		html.replace("%javaversion%", String.valueOf(System.getProperty("java.version")));
		html.replace("%javaclassversion%", String.valueOf(System.getProperty("java.class.version")));
		html.replace("%javavmname%", String.valueOf(System.getProperty("java.vm.name")));
		html.replace("%javavmversion%", String.valueOf(System.getProperty("java.vm.version")));
		html.replace("%javavmvedor%", String.valueOf(System.getProperty("java.vm.vendor")));
		html.replace("%javavminfo%", String.valueOf(System.getProperty("java.vm.info")));
		html.replace("%server_restarted%", String.valueOf(GameServer.dateTimeServerStarted.getTime()));
		
		// Clan
		html.replace("%clanjoindays%", Integer.toString(Config.ALT_CLAN_JOIN_DAYS));
		html.replace("%clancreatedays%", Integer.toString(Config.ALT_CLAN_CREATE_DAYS));
		html.replace("%clandisolvedays%", Integer.toString(Config.ALT_CLAN_DISSOLVE_DAYS));
		html.replace("%allyjoindayswhenleaved%", Integer.toString(Config.ALT_ALLY_JOIN_DAYS_WHEN_LEAVED));
		html.replace("%allyjoindayswhendismissed%", Integer.toString(Config.ALT_ALLY_JOIN_DAYS_WHEN_DISMISSED));
		html.replace("%acceptclandayswhendismissed%", Integer.toString(Config.ALT_ACCEPT_CLAN_DAYS_WHEN_DISMISSED));
		html.replace("%createallydayswhendissolved%", Integer.toString(Config.ALT_CREATE_ALLY_DAYS_WHEN_DISSOLVED));
		html.replace("%maxclansinally%", Integer.toString(Config.ALT_MAX_NUM_OF_CLANS_IN_ALLY));
		html.replace("%clanmembersforwar%", Integer.toString(Config.ALT_CLAN_MEMBERS_FOR_WAR));
		html.replace("%clanwarpenaltywhenend%", Integer.toString(Config.ALT_CLAN_WAR_PENALTY_WHEN_ENDED));
		
		// Enchants
		html.replace("%EnchantChanceMagicWeapon%", Double.toString(Config.ENCHANT_CHANCE_WEAPON_MAGIC));
		html.replace("%EnchantChanceMagicWeapon15Plus%", Double.toString(Config.ENCHANT_CHANCE_WEAPON_MAGIC_15PLUS) + 10);
		html.replace("%EnchantChanceNonMagicWeapon%", Double.toString(Config.ENCHANT_CHANCE_WEAPON_NONMAGIC) + 10);
		html.replace("%EnchantChanceNonMagicWeapon15Plus%", Double.toString(Config.ENCHANT_CHANCE_WEAPON_NONMAGIC_15PLUS) + 10);
		html.replace("%EnchantChanceArmor%", Double.toString(Config.ENCHANT_CHANCE_ARMOR) + 10);
		html.replace("%EnchantMaxWeapon%", Integer.toString(Config.ENCHANT_MAX_WEAPON));
		html.replace("%EnchantMaxArmor%", Integer.toString(Config.ENCHANT_MAX_ARMOR));
		html.replace("%EnchantSafeMax%", Integer.toString(Config.ENCHANT_SAFE_MAX));
		html.replace("%EnchantSafeMaxFull%", Integer.toString(Config.ENCHANT_SAFE_MAX_FULL));
		
		Player.sendPacket(html);
		
		html.disableValidation();
		Player.sendPacket(html);
	}
}