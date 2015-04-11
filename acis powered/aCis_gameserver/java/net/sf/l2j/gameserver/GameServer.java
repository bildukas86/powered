package net.sf.l2j.gameserver;

import Extensions.ConsoleServerStatus;
import Extensions.L2JxTreme;
import Extensions.AchievmentsEngine.AchievementsManager;
import Extensions.AutoManagers.AutoAnnounce;
import Extensions.AutoManagers.AutoRestartGameServer;
import Extensions.AutoManagers.AutoRewarder;
import Extensions.AutoManagers.PlayersOnlineAutoAnnounce;
import Extensions.AutoVoteReward.VoteRewardHopzone;
import Extensions.AutoVoteReward.VoteRewardTopzone;
import Extensions.Balancer.BalanceLoad;
import Extensions.Events.Hide;
import Extensions.Events.RandomFight;
import Extensions.Events.SiegeReward;
import Extensions.Events.StriderRace;
import Extensions.Events.Phoenix.EventManager;
import Extensions.Events.Phoenix.Engines.EventBuffer;
import Extensions.Events.Phoenix.Engines.EventStats;
import Extensions.Protection.ipCatcher;
import Extensions.Vip.VIPEngine;
import Extensions.VortexEngine.VoteMain;
import classbalancer.ClassBalanceManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import net.sf.l2j.Config;
import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.Server;
import net.sf.l2j.gameserver.buffer.BufferParser;
import net.sf.l2j.gameserver.buffer.L2Buffer;
import net.sf.l2j.gameserver.cache.CrestCache;
import net.sf.l2j.gameserver.cache.HtmCache;
import net.sf.l2j.gameserver.communitybbs.Manager.ForumsBBSManager;
import net.sf.l2j.gameserver.datatables.AccessLevels;
import net.sf.l2j.gameserver.datatables.AdminCommandAccessRights;
import net.sf.l2j.gameserver.datatables.ArmorSetsTable;
import net.sf.l2j.gameserver.datatables.AugmentationData;
import net.sf.l2j.gameserver.datatables.BookmarkTable;
import net.sf.l2j.gameserver.datatables.BufferTable;
import net.sf.l2j.gameserver.datatables.BuyListTable;
import net.sf.l2j.gameserver.datatables.CharNameTable;
import net.sf.l2j.gameserver.datatables.CharTemplateTable;
import net.sf.l2j.gameserver.datatables.ClanTable;
import net.sf.l2j.gameserver.datatables.DoorTable;
import net.sf.l2j.gameserver.datatables.FishTable;
import net.sf.l2j.gameserver.datatables.GmListTable;
import net.sf.l2j.gameserver.datatables.HelperBuffTable;
import net.sf.l2j.gameserver.datatables.HennaTable;
import net.sf.l2j.gameserver.datatables.HerbDropTable;
import net.sf.l2j.gameserver.datatables.ItemTable;
import net.sf.l2j.gameserver.datatables.MapRegionTable;
import net.sf.l2j.gameserver.datatables.MultisellData;
import net.sf.l2j.gameserver.datatables.NpcTable;
import net.sf.l2j.gameserver.datatables.NpcWalkerRoutesTable;
import net.sf.l2j.gameserver.datatables.OfflineTradersTable;
import net.sf.l2j.gameserver.datatables.PetDataTable;
import net.sf.l2j.gameserver.datatables.RecipeTable;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.datatables.SkillTreeTable;
import net.sf.l2j.gameserver.datatables.SkipTable;
import net.sf.l2j.gameserver.datatables.SoulCrystalsTable;
import net.sf.l2j.gameserver.datatables.SpawnTable;
import net.sf.l2j.gameserver.datatables.SpellbookTable;
import net.sf.l2j.gameserver.datatables.StaticObjects;
import net.sf.l2j.gameserver.datatables.SummonItemsData;
import net.sf.l2j.gameserver.datatables.TeleportLocationTable;
import net.sf.l2j.gameserver.geoengine.GeoData;
import net.sf.l2j.gameserver.geoengine.PathFinding;
import net.sf.l2j.gameserver.handler.AdminCommandHandler;
import net.sf.l2j.gameserver.handler.ChatHandler;
import net.sf.l2j.gameserver.handler.ItemHandler;
import net.sf.l2j.gameserver.handler.SkillHandler;
import net.sf.l2j.gameserver.handler.UserCommandHandler;
import net.sf.l2j.gameserver.handler.VoicedCommandHandler;
import net.sf.l2j.gameserver.idfactory.IdFactory;
import net.sf.l2j.gameserver.instancemanager.AuctionManager;
import net.sf.l2j.gameserver.instancemanager.AutoSpawnManager;
import net.sf.l2j.gameserver.instancemanager.BoatManager;
import net.sf.l2j.gameserver.instancemanager.CastleManager;
import net.sf.l2j.gameserver.instancemanager.CastleManorManager;
import net.sf.l2j.gameserver.instancemanager.ClanHallManager;
import net.sf.l2j.gameserver.instancemanager.CoupleManager;
import net.sf.l2j.gameserver.instancemanager.CursedWeaponsManager;
import net.sf.l2j.gameserver.instancemanager.DayNightSpawnManager;
import net.sf.l2j.gameserver.instancemanager.DimensionalRiftManager;
import net.sf.l2j.gameserver.instancemanager.FishingChampionshipManager;
import net.sf.l2j.gameserver.instancemanager.FourSepulchersManager;
import net.sf.l2j.gameserver.instancemanager.GrandBossManager;
import net.sf.l2j.gameserver.instancemanager.ItemsOnGroundManager;
import net.sf.l2j.gameserver.instancemanager.MercTicketManager;
import net.sf.l2j.gameserver.instancemanager.MovieMakerManager;
import net.sf.l2j.gameserver.instancemanager.PetitionManager;
import net.sf.l2j.gameserver.instancemanager.QuestManager;
import net.sf.l2j.gameserver.instancemanager.RaidBossPointsManager;
import net.sf.l2j.gameserver.instancemanager.RaidBossSpawnManager;
import net.sf.l2j.gameserver.instancemanager.SevenSigns;
import net.sf.l2j.gameserver.instancemanager.SevenSignsFestival;
import net.sf.l2j.gameserver.instancemanager.SiegeManager;
import net.sf.l2j.gameserver.instancemanager.ZoneManager;
import net.sf.l2j.gameserver.instancemanager.games.MonsterRace;
import net.sf.l2j.gameserver.model.GodSystem;
import net.sf.l2j.gameserver.model.L2Manor;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.entity.Hero;
import net.sf.l2j.gameserver.model.olympiad.Olympiad;
import net.sf.l2j.gameserver.model.olympiad.OlympiadGameManager;
import net.sf.l2j.gameserver.model.partymatching.PartyMatchRoomList;
import net.sf.l2j.gameserver.model.partymatching.PartyMatchWaitingList;
import net.sf.l2j.gameserver.network.L2GameClient;
import net.sf.l2j.gameserver.network.L2GamePacketHandler;
import net.sf.l2j.gameserver.scripting.L2ScriptEngineManager;
import net.sf.l2j.gameserver.taskmanager.ItemsAutoDestroyTaskManager;
import net.sf.l2j.gameserver.taskmanager.KnownListUpdateTaskManager;
import net.sf.l2j.gameserver.taskmanager.TaskManager;
import net.sf.l2j.gameserver.xmlfactory.XMLDocumentFactory;
import net.sf.l2j.util.DeadLockDetector;
import net.sf.l2j.util.IPv4Filter;
import net.sf.l2j.util.Util;

import org.mmocore.network.SelectorConfig;
import org.mmocore.network.SelectorThread;

import skillsbalancer.SkillsBalanceManager;

public class GameServer
{
	private static final Logger _log = Logger.getLogger(GameServer.class.getName());
	
	private final SelectorThread<L2GameClient> _selectorThread;
	private final L2GamePacketHandler _gamePacketHandler;
	private final DeadLockDetector _deadDetectThread;
	public static GameServer gameServer;
	private final LoginServerThread _loginThread;
	public static final Calendar dateTimeServerStarted = Calendar.getInstance();
	
	public long getUsedMemoryMB()
	{
		return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576; // 1024 * 1024 = 1048576;
	}
	
	public SelectorThread<L2GameClient> getSelectorThread()
	{
		return _selectorThread;
	}
	
	public GameServer() throws Exception
	{
		long serverLoadStart = System.currentTimeMillis();
		gameServer = this;
		
		if (Config.GAMESERVERSTATUS)
		{
			ConsoleServerStatus.getInstance();
			_log.info("ServerStatus Started!");
		}
		
		IdFactory.getInstance();
		ThreadPoolManager.getInstance();
		
		new File("./data/crests").mkdirs();
		
		L2JxTreme.info();
		Util.printSection("World");
		GameTimeController.getInstance();
		L2World.getInstance();
		MapRegionTable.getInstance();
		Announcements.getInstance();
		BookmarkTable.getInstance();
		
		Util.printSection("Skills");
		SkillTable.getInstance();
		SkillTreeTable.getInstance();
		
		Util.printSection("Items");
		ItemTable.getInstance();
		SummonItemsData.getInstance();
		BuyListTable.getInstance();
		MultisellData.getInstance();
		RecipeTable.getInstance();
		ArmorSetsTable.getInstance();
		FishTable.getInstance();
		SpellbookTable.getInstance();
		SoulCrystalsTable.load();
		
		Util.printSection("Augments");
		AugmentationData.getInstance();
		
		Util.printSection("Characters");
		AccessLevels.getInstance();
		AdminCommandAccessRights.getInstance();
		CharTemplateTable.getInstance();
		CharNameTable.getInstance();
		GmListTable.getInstance();
		RaidBossPointsManager.getInstance();
		ClassBalanceManager.getInstance();
		SkillsBalanceManager.getInstance();
		
		Util.printSection("Community server");
		if (Config.ENABLE_COMMUNITY_BOARD)
			ForumsBBSManager.getInstance().initRoot();
		else
			_log.config("Community server is disabled.");
		
		Util.printSection("Cache");
		HtmCache.getInstance();
		CrestCache.load();
		TeleportLocationTable.getInstance();
		PartyMatchWaitingList.getInstance();
		PartyMatchRoomList.getInstance();
		PetitionManager.getInstance();
		HennaTable.getInstance();
		HelperBuffTable.getInstance();
		CursedWeaponsManager.getInstance();
		SkipTable.getInstance();
		
		Util.printSection("Clans");
		ClanTable.getInstance();
		AuctionManager.getInstance();
		ClanHallManager.getInstance();
		
		Util.printSection("Geodata & Pathfinding");
		GeoData.initialize();
		PathFinding.initialize();
		
		Util.printSection("World Bosses");
		GrandBossManager.getInstance();
		
		Util.printSection("Zones");
		ZoneManager.getInstance();
		GrandBossManager.getInstance().initZones();
		
		Util.printSection("Castles");
		CastleManager.getInstance().load();
		
		Util.printSection("Seven Signs");
		SevenSigns.getInstance().spawnSevenSignsNPC();
		SevenSignsFestival.getInstance();
		
		Util.printSection("Sieges");
		SiegeManager.getInstance();
		SiegeManager.getSieges();
		MercTicketManager.getInstance();
		SiegeReward.getInstance();
		
		Util.printSection("Manor Manager");
		CastleManorManager.getInstance();
		L2Manor.getInstance();
		
		Util.printSection("NPCs");
		BufferTable.getInstance();
		HerbDropTable.getInstance();
		PetDataTable.getInstance();
		NpcTable.getInstance();
		NpcWalkerRoutesTable.getInstance();
		DoorTable.getInstance();
		StaticObjects.load();
		SpawnTable.getInstance();
		RaidBossSpawnManager.getInstance();
		DayNightSpawnManager.getInstance().trim().notifyChangeMode();
		DimensionalRiftManager.getInstance();
		BufferParser.getInstance();
		L2Buffer.getInstance();
		
		Util.printSection("Olympiads & Heroes");
		OlympiadGameManager.getInstance();
		Olympiad.getInstance();
		Hero.getInstance();
		
		Util.printSection("Four Sepulchers");
		FourSepulchersManager.getInstance().init();
		
		Util.printSection("Protection");
		Guard.Protection.Init();
		
		Util.printSection("Restart Manager");
		if (Config.RESTART_BY_TIME_OF_DAY)
			AutoRestartGameServer.getInstance().StartCalculationOfNextRestartTime();
		else
			_log.info("Restart System: Auto Restart System is Disabled. ");
		
		Util.printSection("Offline Shop");
		if ((Config.OFFLINE_TRADE_ENABLE || Config.OFFLINE_CRAFT_ENABLE) && Config.RESTORE_OFFLINERS)
			OfflineTradersTable.getInstance().restoreOfflineTraders();
		else
			_log.info("Offline Shop System Is Disabled. ");
		
		Util.printSection("Vip Engine");
		VIPEngine.getInstance();
		
		Util.printSection("Vote Manager");
		if (Config.ALLOW_HOPZONE_VOTE_REWARD)
			VoteRewardHopzone.getInstance();
		else
			_log.info("AutoVoteReward: HopZone Is Disabled. ");
		if (Config.ALLOW_TOPZONE_VOTE_REWARD)
			VoteRewardTopzone.getInstance();
		else
			_log.info("AutoVoteReward: TopZone Is Disabled. ");
		VoteMain.load();
		
		Util.printSection("Balancer");
		BalanceLoad.LoadEm();
		
		Util.printSection("Phoenix Event Engine");
		EventManager.getInstance();
		EventStats.getInstance();
		if (EventManager.getInstance().getBoolean("eventBufferEnabled"))
			EventBuffer.getInstance();
		
		Util.printSection("Events");
		AchievementsManager.getInstance();
		GodSystem.getInstance();
		if (Config.ALLOW_STRIDER_RACE_EVENT)
			StriderRace.getInstance();
		else
			_log.info("Event: Strider Race Is Disabled. ");
		if (Config.HIDE_EVENT)
			Hide.getInstance();
		else
			_log.info("Event: Hide Is Disabled. ");
		if (Config.ALLOW_RANDOM_FIGHT)
			RandomFight.getInstance();
		else
			_log.info("Event: Random Fight Is Disabled. ");
		
		Util.printSection("Automatation");
		if (Config.ALLOW_ANNOUNCE_ONLINE_PLAYERS)
			PlayersOnlineAutoAnnounce.getInstance();
		if (Config.ALLOW_AUTO_REWARDER)
			AutoRewarder.getInstance();
		if (Config.AUTO_ANNOUNCE_ENABLE)
			AutoAnnounce.getInstance();
		
		QuestManager.getInstance();
		BoatManager.getInstance();
		if (!Config.ALT_DEV_NO_SCRIPTS)
		{
			Thread quests = new Thread(L2ScriptEngineManager.getInstance());
			quests.start();
		}
		else
			_log.config("QuestManager: Skipping scripts.");
		
		if (Config.SAVE_DROPPED_ITEM)
			ItemsOnGroundManager.getInstance();
		
		if (Config.ITEM_AUTO_DESTROY_TIME > 0 || Config.HERB_AUTO_DESTROY_TIME > 0)
			ItemsAutoDestroyTaskManager.getInstance();
		MonsterRace.getInstance();
		
		Util.printSection("Handlers");
		_log.config("AutoSpawnHandler: Loaded " + AutoSpawnManager.getInstance().size() + " handlers.");
		_log.config("AdminCommandHandler: Loaded " + AdminCommandHandler.getInstance().size() + " handlers.");
		_log.config("ChatHandler: Loaded " + ChatHandler.getInstance().size() + " handlers.");
		_log.config("ItemHandler: Loaded " + ItemHandler.getInstance().size() + " handlers.");
		_log.config("SkillHandler: Loaded " + SkillHandler.getInstance().size() + " handlers.");
		_log.config("UserCommandHandler: Loaded " + UserCommandHandler.getInstance().size() + " handlers.");
		_log.config("VoicedCommandHandler: Loaded " + VoicedCommandHandler.getInstance().size() + " handlers.");
		
		if (Config.ALLOW_WEDDING)
			CoupleManager.getInstance();
		
		if (Config.ALT_FISH_CHAMPIONSHIP_ENABLED)
			FishingChampionshipManager.getInstance();
		
		ipCatcher.ipsLoad();
		
		Util.printSection("System");
		TaskManager.getInstance();
		
		Runtime.getRuntime().addShutdownHook(Shutdown.getInstance());
		ForumsBBSManager.getInstance();
		_log.config("IdFactory: Free ObjectIDs remaining: " + IdFactory.getInstance().size());
		
		KnownListUpdateTaskManager.getInstance();
		MovieMakerManager.getInstance();
		
		if (Config.DEADLOCK_DETECTOR)
		{
			_log.info("Deadlock detector is enabled. Timer: " + Config.DEADLOCK_CHECK_INTERVAL + "s.");
			_deadDetectThread = new DeadLockDetector();
			_deadDetectThread.setDaemon(true);
			_deadDetectThread.start();
		}
		else
		{
			_log.info("Deadlock detector is disabled.");
			_deadDetectThread = null;
		}
		
		System.gc();
		
		long usedMem = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576;
		long totalMem = Runtime.getRuntime().maxMemory() / 1048576;
		
		_log.log(Level.INFO, getClass().getSimpleName() + ": Gameserver have started, used memory: " + usedMem + " / " + totalMem + " MB.");
		_log.log(Level.INFO, getClass().getSimpleName() + ": Maximum allowed players: " + Config.MAXIMUM_ONLINE_USERS);
		long serverLoadEnd = System.currentTimeMillis();
		_log.log(Level.INFO, getClass().getSimpleName() + ": Server Started in: " + ((serverLoadEnd - serverLoadStart) / 1000) + " seconds.");
		
		Util.printSection("Login");
		_loginThread = LoginServerThread.getInstance();
		_loginThread.start();
		
		final SelectorConfig sc = new SelectorConfig();
		sc.MAX_READ_PER_PASS = Config.MMO_MAX_READ_PER_PASS;
		sc.MAX_SEND_PER_PASS = Config.MMO_MAX_SEND_PER_PASS;
		sc.SLEEP_TIME = Config.MMO_SELECTOR_SLEEP_TIME;
		sc.HELPER_BUFFER_COUNT = Config.MMO_HELPER_BUFFER_COUNT;
		
		_gamePacketHandler = new L2GamePacketHandler();
		_selectorThread = new SelectorThread<>(sc, _gamePacketHandler, _gamePacketHandler, _gamePacketHandler, new IPv4Filter());
		
		InetAddress bindAddress = null;
		if (!Config.GAMESERVER_HOSTNAME.equals("*"))
		{
			try
			{
				bindAddress = InetAddress.getByName(Config.GAMESERVER_HOSTNAME);
			}
			catch (UnknownHostException e1)
			{
				_log.log(Level.SEVERE, "WARNING: The GameServer bind address is invalid, using all available IPs. Reason: " + e1.getMessage(), e1);
			}
		}
		
		try
		{
			_selectorThread.openServerSocket(bindAddress, Config.PORT_GAME);
		}
		catch (IOException e)
		{
			_log.log(Level.SEVERE, "FATAL: Failed to open server socket. Reason: " + e.getMessage(), e);
			System.exit(1);
		}
		_selectorThread.start();
	}
	
	public static void main(String[] args) throws Exception
	{
		Server.serverMode = Server.MODE_GAMESERVER;
		
		final String LOG_FOLDER = "./log"; // Name of folder for log file
		final String LOG_NAME = "config/log.cfg"; // Name of log file
		
		// Create log folder
		File logFolder = new File(LOG_FOLDER);
		logFolder.mkdir();
		
		// Create input stream for log file -- or store file data into memory
		InputStream is = new FileInputStream(new File(LOG_NAME));
		LogManager.getLogManager().readConfiguration(is);
		is.close();
		
		// Initialize config
		Config.load();
		
		Util.printSection("L2JxTreme v." + Config.VER + " builded:" + Config.BUILD);
		
		// Factories
		XMLDocumentFactory.getInstance();
		L2DatabaseFactory.getInstance();
		
		gameServer = new GameServer();
	}
}