package net.sf.l2j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import net.sf.l2j.commons.config.ExProperties;
import net.sf.l2j.gameserver.geoengine.geodata.GeoFormat;
import net.sf.l2j.gameserver.model.holder.BuffSkillHolder;
import net.sf.l2j.gameserver.model.holder.ItemHolder;
import net.sf.l2j.gameserver.util.FloodProtectorConfig;
import net.sf.l2j.util.StringUtil;

public final class Config
{
	protected static final Logger _log = Logger.getLogger(Config.class.getName());
	
	/** Version */
	public static final String VERSION = "./config/L2J Xtreme Version.ini";
	/** Others */
	public static final String BANNED_IP_XML = "./config/banned.xml";
	public static final String LOGIN_CONFIGURATION_FILE = "./config/loginserver.properties";
	/** Admin */
	public static final String ADMIN_FILE = "./config/Admin/Admin.properties";
	public static final String ADMINMODS_FILE = "./config/Admin/AdminMods.properties";
	/** Balance */
	public static final String RESTRICTIONS_FILE = "./config/Balance/Restrictions.properties";
	/** Bosses */
	public static final String AA_FILE = "./config/Bosses/AA.properties";
	public static final String ANTHARAS_FILE = "./config/Bosses/Antharas.properties";
	public static final String ANTQUEEN_FILE = "./config/Bosses/AntQueen.properties";
	public static final String BAIUM_FILE = "./config/Bosses/Baium.properties";
	public static final String CORE_FILE = "./config/Bosses/Core.properties";
	public static final String FRINTEZZA_FILE = "./config/Bosses/Frintezza.properties";
	public static final String ORFEN_FILE = "./config/Bosses/Orfen.properties";
	public static final String SAILREN_FILE = "./config/Bosses/Sailren.properties";
	public static final String VALAKAS_FILE = "./config/Bosses/Valakas.properties";
	public static final String ZAKEN_FILE = "./config/Bosses/Zaken.properties";
	/** Main */
	public static final String PVP_FILE = "./config/Main/Pvp.properties";
	public static final String CLANS_FILE = "./config/Main/clans.properties";
	public static final String NPCS_FILE = "./config/Main/npcs.properties";
	public static final String PLAYERS_FILE = "./config/Main/players.properties";
	public static final String SIEGE_FILE = "./config/Main/siege.properties";
	public static final String CUSTOM_SIEGE_FILE = "./config/CustomSiege.properties";
	public static final String RATES_FILE = "./config/Main/Rates.properties";
	public static final String GEOENGINE_FILE = "./config/Main/Geodata.properties";
	/** Mods */
	public static final String AUTOMATATION_FILE = "./config/Mods/Automatation.properties";
	public static final String CUSTOMNPCS_FILE = "./config/Mods/CustomNpcs.properties";
	public static final String COMMANDS_FILE = "./config/Mods/Commands.properties";
	public static final String COMMUNITYBOARD_FILE = "./config/Mods/CommunityBoard.properties";
	public static final String MODS_FILE = "./config/Mods/Mods.properties";
	public static final String VOTEMANAGER_FILE = "./config/Mods/VoteManager.properties";
	public static final String CHAMPIONMOBS_FILE = "./config/Mods/ChampionMobs.properties";
	public static final String DONATOR_FILE = "./config/Mods/Donator.properties";
	public static final String TELEPORTS_FILE = "./config/Mods/Teleports.properties";
	public static final String OFFLINETRADES_FILE = "./config/Mods/OfflineTrades.properties";
	public static final String CHATMANAGER_FILE = "./config/Mods/ChatManager.properties";
	/** Events */
	public static final String SMALLEVENTS_FILE = "./config/Events/SmallEvents.properties";
	public static final String OLYMPIAD_FILE = "./config/Events/Olympiad.properties";
	public static final String STRIDER_FILE = "./config/Events/Strider.properties";
	public static final String RANDOMFIGHT_FILE = "./config/Events/RandomFight.properties";
	public static final String SUPERMONSTER_FILE = "./config/Events/SuperMonster.properties";
	public static final String HIDE_FILE = "./config/Events/Hide.properties";
	/** Protection */
	public static final String FLOOD_PROTECTOR_FILE = "./config/Protection/floodprotector.properties";
	public static final String PROTECTIONS_FILE = "./config/Protection/Protections.properties";
	public static final String ENCHANTPROTECTIONS_FILE = "./config/Protection/EnchantProtections.properties";
	public static final String PVPPROTECTIONS_FILE = "./config/Protection/PvpProtections.properties";
	/** Network */
	public static final String HEXID_FILE = "./config/Network/hexid.txt";
	public static final String SERVER_FILE = "./config/Network/server.properties";
	
	public static String VER;
	public static String BUILD;
	
	// --------------------------------------------------
	// Clans settings
	// --------------------------------------------------
	
	/** Clans */
	public static int ALT_CLAN_JOIN_DAYS;
	public static int ALT_CLAN_CREATE_DAYS;
	public static int ALT_CLAN_DISSOLVE_DAYS;
	public static int ALT_ALLY_JOIN_DAYS_WHEN_LEAVED;
	public static int ALT_ALLY_JOIN_DAYS_WHEN_DISMISSED;
	public static int ALT_ACCEPT_CLAN_DAYS_WHEN_DISMISSED;
	public static int ALT_CREATE_ALLY_DAYS_WHEN_DISSOLVED;
	public static int ALT_MAX_NUM_OF_CLANS_IN_ALLY;
	public static int ALT_CLAN_MEMBERS_FOR_WAR;
	public static int ALT_CLAN_WAR_PENALTY_WHEN_ENDED;
	public static boolean ALT_MEMBERS_CAN_WITHDRAW_FROM_CLANWH;
	public static boolean REMOVE_CASTLE_CIRCLETS;
	
	/** Manor */
	public static int ALT_MANOR_REFRESH_TIME;
	public static int ALT_MANOR_REFRESH_MIN;
	public static int ALT_MANOR_APPROVE_TIME;
	public static int ALT_MANOR_APPROVE_MIN;
	public static int ALT_MANOR_MAINTENANCE_PERIOD;
	public static boolean ALT_MANOR_SAVE_ALL_ACTIONS;
	public static int ALT_MANOR_SAVE_PERIOD_RATE;
	
	/** Clan Hall function */
	public static long CH_TELE_FEE_RATIO;
	public static int CH_TELE1_FEE;
	public static int CH_TELE2_FEE;
	public static long CH_ITEM_FEE_RATIO;
	public static int CH_ITEM1_FEE;
	public static int CH_ITEM2_FEE;
	public static int CH_ITEM3_FEE;
	public static long CH_MPREG_FEE_RATIO;
	public static int CH_MPREG1_FEE;
	public static int CH_MPREG2_FEE;
	public static int CH_MPREG3_FEE;
	public static int CH_MPREG4_FEE;
	public static int CH_MPREG5_FEE;
	public static long CH_HPREG_FEE_RATIO;
	public static int CH_HPREG1_FEE;
	public static int CH_HPREG2_FEE;
	public static int CH_HPREG3_FEE;
	public static int CH_HPREG4_FEE;
	public static int CH_HPREG5_FEE;
	public static int CH_HPREG6_FEE;
	public static int CH_HPREG7_FEE;
	public static int CH_HPREG8_FEE;
	public static int CH_HPREG9_FEE;
	public static int CH_HPREG10_FEE;
	public static int CH_HPREG11_FEE;
	public static int CH_HPREG12_FEE;
	public static int CH_HPREG13_FEE;
	public static long CH_EXPREG_FEE_RATIO;
	public static int CH_EXPREG1_FEE;
	public static int CH_EXPREG2_FEE;
	public static int CH_EXPREG3_FEE;
	public static int CH_EXPREG4_FEE;
	public static int CH_EXPREG5_FEE;
	public static int CH_EXPREG6_FEE;
	public static int CH_EXPREG7_FEE;
	public static long CH_SUPPORT_FEE_RATIO;
	public static int CH_SUPPORT1_FEE;
	public static int CH_SUPPORT2_FEE;
	public static int CH_SUPPORT3_FEE;
	public static int CH_SUPPORT4_FEE;
	public static int CH_SUPPORT5_FEE;
	public static int CH_SUPPORT6_FEE;
	public static int CH_SUPPORT7_FEE;
	public static int CH_SUPPORT8_FEE;
	public static long CH_CURTAIN_FEE_RATIO;
	public static int CH_CURTAIN1_FEE;
	public static int CH_CURTAIN2_FEE;
	public static long CH_FRONT_FEE_RATIO;
	public static int CH_FRONT1_FEE;
	public static int CH_FRONT2_FEE;
	
	// --------------------------------------------------
	// Events settings
	// --------------------------------------------------
	
	/** Random Fight */
	public static boolean ALLOW_RANDOM_FIGHT;
	public static int EVERY_MINUTES;
	public static int RANDOM_FIGHT_REWARD_ID;
	public static int RANDOM_FIGHT_REWARD_COUNT;
	
	/** Hide Event */
	public static boolean HIDE_EVENT;
	public static int HIDE_EVENT_REWARD_ID;
	public static int HIDE_EVENT_REWARD_COUNT;
	public static int HIDE_EVENT_ITEM_WILL_DROP;
	public static int HIDE_EVENT_DELAY_BEFORE_START;
	
	/** Super Monster */
	public static boolean ENABLE_SUPER_MONSTER;
	public static String SUPER_MONSTERS;
	public static List<Integer> SUPER_MONSTERS_IDS;
	public static boolean SM_REWARD_PARTY;
	public static boolean SM_REWARD_PARTY_NOBLE;
	public static boolean SM_REWARD_PARTY_HERO;
	public static boolean SM_GIVE_NOBLE;
	public static boolean SM_GIVE_HERO;
	public static boolean SM_GIVE_ITEM;
	public static List<int[]> SM_ITEM_REWARD;
	
	/** Strider Race */
	public static boolean ALLOW_STRIDER_RACE_EVENT;
	public static int STRIDER_RACE_DELAY;
	public static int STRIDER_RACE_REGISTRATION_TIME;
	public static int STRIDER_RACE_RUNNING_TIME;
	public static Map<Integer, Integer> STRIDER_RACE_WINNER_REWARDS = new HashMap<>();
	public static int STRIDER_RACE_X;
	public static int STRIDER_RACE_Y;
	public static int STRIDER_RACE_Z;
	public static int STRIDER_RACE_ENDNPCID;
	public static int STRIDER_RACE_ENDNPC_SPAWN_X;
	public static int STRIDER_RACE_ENDNPC_SPAWN_Y;
	public static int STRIDER_RACE_ENDNPC_SPAWN_Z;
	
	/** Olympiad */
	public static boolean ALT_OLY_WEEK;
	public static int ALT_OLY_DAY;
	public static int ALT_OLY_START_TIME;
	public static int ALT_OLY_MIN;
	public static long ALT_OLY_CPERIOD;
	public static long ALT_OLY_BATTLE;
	public static long ALT_OLY_WPERIOD;
	public static long ALT_OLY_VPERIOD;
	public static int ALT_OLY_WAIT_TIME;
	public static int ALT_OLY_WAIT_BATTLE;
	public static int ALT_OLY_WAIT_END;
	public static int ALT_OLY_START_POINTS;
	public static int ALT_OLY_WEEKLY_POINTS;
	public static int ALT_OLY_MIN_MATCHES;
	public static int ALT_OLY_CLASSED;
	public static int ALT_OLY_NONCLASSED;
	public static int[][] ALT_OLY_CLASSED_REWARD;
	public static int[][] ALT_OLY_NONCLASSED_REWARD;
	public static int ALT_OLY_COMP_RITEM;
	public static int ALT_OLY_GP_PER_POINT;
	public static int ALT_OLY_HERO_POINTS;
	public static int ALT_OLY_RANK1_POINTS;
	public static int ALT_OLY_RANK2_POINTS;
	public static int ALT_OLY_RANK3_POINTS;
	public static int ALT_OLY_RANK4_POINTS;
	public static int ALT_OLY_RANK5_POINTS;
	public static int ALT_OLY_MAX_POINTS;
	public static int ALT_OLY_DIVIDER_CLASSED;
	public static int ALT_OLY_DIVIDER_NON_CLASSED;
	public static boolean ALT_OLY_ANNOUNCE_GAMES;
	public static boolean BLOCK_AUGMENT_SKILLS_IN_OLY;
	public static boolean DISABLE_OLYMPIAD_CHAT;
	public static boolean OLY_SKILL_PROTECT;
	public static List<Integer> OLY_SKILL_LIST = new ArrayList<>();
	
	/** SevenSigns Festival */
	public static boolean ALT_GAME_REQUIRE_CLAN_CASTLE;
	public static boolean ALT_GAME_CASTLE_DAWN;
	public static boolean ALT_GAME_CASTLE_DUSK;
	public static int ALT_FESTIVAL_MIN_PLAYER;
	public static int ALT_MAXIMUM_PLAYER_CONTRIB;
	public static long ALT_FESTIVAL_MANAGER_START;
	public static long ALT_FESTIVAL_LENGTH;
	public static long ALT_FESTIVAL_CYCLE_LENGTH;
	public static long ALT_FESTIVAL_FIRST_SPAWN;
	public static long ALT_FESTIVAL_FIRST_SWARM;
	public static long ALT_FESTIVAL_SECOND_SPAWN;
	public static long ALT_FESTIVAL_SECOND_SWARM;
	public static long ALT_FESTIVAL_CHEST_SPAWN;
	public static boolean ALT_SEVENSIGNS_LAZY_UPDATE;
	
	/** Four Sepulchers */
	public static int FS_TIME_ATTACK;
	public static int FS_TIME_COOLDOWN;
	public static int FS_TIME_ENTRY;
	public static int FS_TIME_WARMUP;
	public static int FS_PARTY_MEMBER_COUNT;
	
	/** dimensional rift */
	public static int RIFT_MIN_PARTY_SIZE;
	public static int RIFT_SPAWN_DELAY;
	public static int RIFT_MAX_JUMPS;
	public static int RIFT_AUTO_JUMPS_TIME_MIN;
	public static int RIFT_AUTO_JUMPS_TIME_MAX;
	public static int RIFT_ENTER_COST_RECRUIT;
	public static int RIFT_ENTER_COST_SOLDIER;
	public static int RIFT_ENTER_COST_OFFICER;
	public static int RIFT_ENTER_COST_CAPTAIN;
	public static int RIFT_ENTER_COST_COMMANDER;
	public static int RIFT_ENTER_COST_HERO;
	public static double RIFT_BOSS_ROOM_TIME_MUTIPLY;
	
	/** Lottery */
	public static int ALT_LOTTERY_PRIZE;
	public static int ALT_LOTTERY_TICKET_PRICE;
	public static double ALT_LOTTERY_5_NUMBER_RATE;
	public static double ALT_LOTTERY_4_NUMBER_RATE;
	public static double ALT_LOTTERY_3_NUMBER_RATE;
	public static int ALT_LOTTERY_2_AND_1_NUMBER_PRIZE;
	
	/** Fishing tournament */
	public static boolean ALT_FISH_CHAMPIONSHIP_ENABLED;
	public static int ALT_FISH_CHAMPIONSHIP_REWARD_ITEM;
	public static int ALT_FISH_CHAMPIONSHIP_REWARD_1;
	public static int ALT_FISH_CHAMPIONSHIP_REWARD_2;
	public static int ALT_FISH_CHAMPIONSHIP_REWARD_3;
	public static int ALT_FISH_CHAMPIONSHIP_REWARD_4;
	public static int ALT_FISH_CHAMPIONSHIP_REWARD_5;
	
	// --------------------------------------------------
	// HexID
	// --------------------------------------------------
	
	public static int SERVER_ID;
	public static byte[] HEX_ID;
	
	// --------------------------------------------------
	// FloodProtectors
	// --------------------------------------------------
	
	public static FloodProtectorConfig FLOOD_PROTECTOR_ROLL_DICE;
	public static FloodProtectorConfig FLOOD_PROTECTOR_HERO_VOICE;
	public static FloodProtectorConfig FLOOD_PROTECTOR_SUBCLASS;
	public static FloodProtectorConfig FLOOD_PROTECTOR_DROP_ITEM;
	public static FloodProtectorConfig FLOOD_PROTECTOR_SERVER_BYPASS;
	public static FloodProtectorConfig FLOOD_PROTECTOR_MULTISELL;
	public static FloodProtectorConfig FLOOD_PROTECTOR_MANUFACTURE;
	public static FloodProtectorConfig FLOOD_PROTECTOR_MANOR;
	public static FloodProtectorConfig FLOOD_PROTECTOR_SENDMAIL;
	public static FloodProtectorConfig FLOOD_PROTECTOR_CHARACTER_SELECT;
	public static FloodProtectorConfig FLOOD_PROTECTOR_MACRO;
	public static FloodProtectorConfig FLOOD_PROTECTOR_AIO_VIP_VOICE;
	
	// --------------------------------------------------
	// Protections Folder Settings
	// --------------------------------------------------
	
	/** Forbidden Names */
	public static String[] FORBIDDEN_NAMES;
	
	/** Spawn Protections */
	public static int PLAYER_SPAWN_PROTECTION;
	
	/** AntiBot Captcha */
	public static boolean ANTIBOT_ENABLE;
	public static int ANTIBOT_TIME_JAIL;
	public static int ANTIBOT_TIME_VOTE;
	public static int ANTIBOT_KILL_MOBS;
	public static int ANTIBOT_MIN_LEVEL;
	
	/** Check Skills On Enter */
	public static boolean CHECK_SKILLS_ON_ENTER;
	public static String NON_CHECK_SKILLS;
	public static List<Integer> LIST_NON_CHECK_SKILLS = new ArrayList<>();
	public static boolean CHECK_NOBLE_SKILLS;
	public static boolean CHECK_HERO_SKILLS;
	
	/** Newbie Protection System */
	public static boolean ENABLE_NEWBIE_PROTECTION_SYSTEM;
	public static int PROTECTION_DIFFERENCE_LEVEL;
	public static int PROTECTION_SKILL_ID;
	public static int PROTECTION_SKILL_LVL;
	public static String PROTECTION_MESSAGE;
	
	/** Last Ip System Protection */
	public static boolean LASTIP_SYSTEM_ENABLED;
	
	/** Disable chat on dead players */
	public static boolean DISABLE_DEAD_CHAT;
	
	// --------------------------------------------------
	// PvP Protections Settings
	// --------------------------------------------------
	
	/** Anti Farm PvP Protection */
	public static boolean ANTI_FARM_ENABLED;
	public static boolean ANTI_FARM_CLAN_ALLY_ENABLED;
	public static boolean ANTI_FARM_LVL_DIFF_ENABLED;
	public static int ANTI_FARM_MAX_LVL_DIFF;
	public static boolean ANTI_FARM_PDEF_DIFF_ENABLED;
	public static int ANTI_FARM_MAX_PDEF_DIFF;
	public static boolean ANTI_FARM_PATK_DIFF_ENABLED;
	public static int ANTI_FARM_MAX_PATK_DIFF;
	public static boolean ANTI_FARM_PARTY_ENABLED;
	public static boolean ANTI_FARM_IP_ENABLED;
	
	// --------------------------------------------------
	// Enchant Protections Settings
	// --------------------------------------------------
	
	/** Enchant Protections */
	public static boolean CAN_ENCHANT_IN_CRAFT_MODE;
	public static boolean CAN_ENCHANT_IN_TELEPORTING;
	public static boolean CAN_ENCHANT_IN_DEAD;
	public static boolean CAN_ENCHANT_IN_SLEEP;
	public static boolean CAN_ENCHANT_IN_PARALYZED;
	public static boolean CAN_ENCHANT_IN_CASTING;
	public static boolean CAN_ENCHANT_IN_MOVING;
	public static boolean CAN_ENCHANT_IN_TRADE;
	public static boolean CAN_ENCHANT_IN_STUNNED;
	public static boolean CAN_ENCHANT_IN_MOUNTED;
	public static boolean CAN_ENCHANT_IN_FAKEDEATH;
	public static boolean CAN_ENCHANT_IN_JAIL;
	public static boolean CAN_ENCHANT_IN_CURSED_WEAPON_EQUIP;
	public static boolean CAN_ENCHANT_IN_FLYING;
	public static boolean CAN_ENCHANT_IN_FISHING;
	public static boolean CAN_ENCHANT_IN_SITTING;
	public static boolean CAN_LOGOUT_IN_ENCHANT;
	
	/** Anti Over Enchant */
	public static boolean ENABLE_ANTI_OVER_ENCHANT_PROTECTION;
	public static int ENCHANT_MAX_WEAPON_PROTECTION;
	public static int ENCHANT_MAX_ARMOR_PROTECTION;
	public static int ENCHANT_MAX_JEWELRY_PROTECTION;
	
	// --------------------------------------------------
	// Loginserver
	// --------------------------------------------------
	
	public static String LOGIN_BIND_ADDRESS;
	public static int PORT_LOGIN;
	
	public static boolean ACCEPT_NEW_GAMESERVER;
	public static int REQUEST_ID;
	public static boolean ACCEPT_ALTERNATE_ID;
	
	public static boolean GAMESERVERSTATUS;
	
	public static int LOGIN_TRY_BEFORE_BAN;
	public static int LOGIN_BLOCK_AFTER_BAN;
	
	public static boolean LOG_LOGIN_CONTROLLER;
	
	public static boolean SHOW_LICENCE;
	public static int IP_UPDATE_TIME;
	public static boolean FORCE_GGAUTH;
	
	public static boolean AUTO_CREATE_ACCOUNTS;
	
	public static boolean FLOOD_PROTECTION;
	public static int FAST_CONNECTION_LIMIT;
	public static int NORMAL_CONNECTION_TIME;
	public static int FAST_CONNECTION_TIME;
	public static int MAX_CONNECTION_PER_IP;
	
	// --------------------------------------------------
	// NPCs / Monsters
	// --------------------------------------------------
	
	/** Champion Mod */
	public static int CHAMPION_FREQUENCY;
	public static int CHAMP_MIN_LVL;
	public static int CHAMP_MAX_LVL;
	public static int CHAMPION_HP;
	public static int CHAMPION_REWARDS;
	public static int CHAMPION_ADENAS_REWARDS;
	public static double CHAMPION_HP_REGEN;
	public static int CHAMPION_REWARD;
	public static int CHAMPION_REWARD_ID;
	public static int CHAMPION_REWARD_QTY;
	
	/** Buffer */
	public static int BUFFER_MAX_SCHEMES;
	public static int BUFFER_MAX_SKILLS;
	public static int BUFFER_STATIC_BUFF_COST;
	public static String BUFFER_BUFFS;
	public static Map<Integer, BuffSkillHolder> BUFFER_BUFFLIST;
	
	/** Vortex Vote Reward Npc */
	public static String VOTE_LINK_HOPZONE;
	public static String VOTE_LINK_TOPZONE;
	public static int VOTE_REWARD_ID1;
	public static int VOTE_REWARD_ID2;
	public static int VOTE_REWARD_ID3;
	public static int VOTE_REWARD_ID4;
	public static int VOTE_REWARD_AMOUNT1;
	public static int VOTE_REWARD_AMOUNT2;
	public static int VOTE_REWARD_AMOUNT3;
	public static int VOTE_REWARD_AMOUNT4;
	public static int SECS_TO_VOTE;
	public static int EXTRA_REW_VOTE_AM;
	
	/** Rebirth Manager */
	public static int REBIRTH_MIN_LEVEL;
	public static int REBIRTH_MAX;
	public static int REBIRTH_RETURN_TO_LEVEL;
	public static String[] REBIRTH_ITEMS;
	
	/** Protector Manager Instance */
	public static boolean PROTECTOR_PLAYER_PK;
	public static boolean PROTECTOR_PLAYER_PVP;
	public static int PROTECTOR_RADIUS_ACTION;
	public static int PROTECTOR_SKILLID;
	public static int PROTECTOR_SKILLLEVEL;
	public static int PROTECTOR_SKILLTIME;
	public static String PROTECTOR_MESSAGE;
	public static int PROTECTOR_SCAN_RANGE;
	
	/** PvP Trader */
	public static int SET_HERO_PVP_COST;
	public static int SET_NOBLE_PVP_COST;
	public static int GIVE_ITEM_PVP_COST;
	public static int ITEM_ID;
	public static int ITEM_COUNT;
	public static int GET_A_SKILL_PVP_COST;
	public static int SKILL_ID;
	public static int SKILL_LVL;
	
	/** Wedding system */
	public static boolean ALLOW_WEDDING;
	public static int WEDDING_PRICE;
	public static boolean WEDDING_SAMESEX;
	public static boolean WEDDING_FORMALWEAR;
	
	/** Services Manager */
	public static int SERVICES_NPC_ID;
	public static int SERVICES_NOBLE_ITEM_ID;
	public static int SERVICES_NOBLE_ITEM_COUNT;
	public static int SERVICES_PK_ITEM_ID;
	public static int SERVICES_PK_ITEM_COUNT;
	public static int SERVICES_CHANGE_NAME_ID;
	public static int SERVICES_CHANGE_NAME_COUNT;
	public static boolean SERVICES_CHANGE_NAME_LOGGING;
	public static int SERVICES_CLAN_NAME_CHANGE_ID;
	public static long SERVICES_CLAN_NAME_CHANGE_COUNT;
	public static boolean SERVICES_CLAN_NAME_CHANGE_LOGGING;
	public static int SERVICES_CLAN_NAME_CHANGE_MIN_LVL;
	public static int SERVICES_CLAN_LEVEL_UP_5to6_ID;
	public static int SERVICES_CLAN_LEVEL_UP_6to7_ID;
	public static int SERVICES_CLAN_LEVEL_UP_7to8_ID;
	public static int SERVICES_CLAN_LEVEL_UP_5to6_COUNT;
	public static int SERVICES_CLAN_LEVEL_UP_6to7_COUNT;
	public static int SERVICES_CLAN_LEVEL_UP_7to8_COUNT;
	public static int SERVICES_CLAN_REP_POINTS_ID;
	public static int SERVICES_CLAN_REP_POINTS_COUNT;
	public static int SERVICES_CHANGE_SEX_ID;
	public static int SERVICES_CHANGE_SEX_COUNT;
	public static int SERVICES_COLOR_ID;
	public static int SERVICES_COLOR_COUNT;
	public static int SERVICES_PREMIUM_ID;
	public static int SERVICES_PREMIUM_COUNT;
	
	/** Misc */
	public static boolean ALLOW_CLASS_MASTERS;
	public static ClassMasterSettings CLASS_MASTER_SETTINGS;
	public static boolean ALLOW_ENTIRE_TREE;
	public static boolean ANNOUNCE_MAMMON_SPAWN;
	public static boolean ALT_MOB_AGRO_IN_PEACEZONE;
	public static boolean ALT_GAME_FREE_TELEPORT;
	public static boolean SHOW_NPC_LVL;
	public static boolean SHOW_NPC_CREST;
	public static boolean SHOW_SUMMON_CREST;
	
	/** Wyvern Manager */
	public static boolean WYVERN_ALLOW_UPGRADER;
	public static int WYVERN_REQUIRED_LEVEL;
	public static int WYVERN_REQUIRED_CRYSTALS;
	
	/** Blessing of Experience Custom EXP Quest */
	public static double EXPERIENCE_BLESSING_BONUS;
	public static int BLESSING_HOURS;
	public static int BLESSING_NPC_ID;
	
	/** Raid Boss */
	public static double RAID_HP_REGEN_MULTIPLIER;
	public static double RAID_MP_REGEN_MULTIPLIER;
	public static double RAID_MINION_RESPAWN_TIMER;
	
	public static boolean RAID_DISABLE_CURSE;
	public static int RAID_CHAOS_TIME;
	public static int GRAND_CHAOS_TIME;
	public static int MINION_CHAOS_TIME;
	
	/** Grand Boss */
	public static int SPAWN_INTERVAL_AQ;
	public static int RANDOM_SPAWN_TIME_AQ;
	
	public static int SPAWN_INTERVAL_ANTHARAS;
	public static int RANDOM_SPAWN_TIME_ANTHARAS;
	public static int WAIT_TIME_ANTHARAS;
	
	public static int SPAWN_INTERVAL_BAIUM;
	public static int RANDOM_SPAWN_TIME_BAIUM;
	
	public static int SPAWN_INTERVAL_CORE;
	public static int RANDOM_SPAWN_TIME_CORE;
	
	public static int SPAWN_INTERVAL_FRINTEZZA;
	public static int RANDOM_SPAWN_TIME_FRINTEZZA;
	public static int WAIT_TIME_FRINTEZZA;
	
	public static int SPAWN_INTERVAL_ORFEN;
	public static int RANDOM_SPAWN_TIME_ORFEN;
	
	public static int SPAWN_INTERVAL_SAILREN;
	public static int RANDOM_SPAWN_TIME_SAILREN;
	public static int WAIT_TIME_SAILREN;
	
	public static int SPAWN_INTERVAL_VALAKAS;
	public static int RANDOM_SPAWN_TIME_VALAKAS;
	public static int WAIT_TIME_VALAKAS;
	
	public static int SPAWN_INTERVAL_ZAKEN;
	public static int RANDOM_SPAWN_TIME_ZAKEN;
	
	/** IA */
	public static boolean GUARD_ATTACK_AGGRO_MOB;
	public static int MAX_DRIFT_RANGE;
	public static long KNOWNLIST_UPDATE_INTERVAL;
	public static int MIN_NPC_ANIMATION;
	public static int MAX_NPC_ANIMATION;
	public static int MIN_MONSTER_ANIMATION;
	public static int MAX_MONSTER_ANIMATION;
	
	public static boolean GRIDS_ALWAYS_ON;
	public static int GRID_NEIGHBOR_TURNON_TIME;
	public static int GRID_NEIGHBOR_TURNOFF_TIME;
	public static boolean ENABLE_SKIPPING;
	
	// --------------------------------------------------
	// Players
	// --------------------------------------------------
	
	/** Misc */
	public static ArrayList<Integer> AVAILABLE_GMS = new ArrayList<>();
	public static boolean ALLOW_CUSTOM_STARTER_ITEMS;
	public static List<int[]> CUSTOM_STARTER_ITEMS = new ArrayList<>();
	public static int STARTING_ADENA;
	public static boolean EFFECT_CANCELING;
	public static double HP_REGEN_MULTIPLIER;
	public static double MP_REGEN_MULTIPLIER;
	public static double CP_REGEN_MULTIPLIER;
	public static int PLAYER_FAKEDEATH_UP_PROTECTION;
	public static double RESPAWN_RESTORE_HP;
	public static int MAX_PVTSTORE_SLOTS_DWARF;
	public static int MAX_PVTSTORE_SLOTS_OTHER;
	public static boolean DEEPBLUE_DROP_RULES;
	public static boolean ALT_GAME_DELEVEL;
	public static boolean DISABLE_LOST_EXP;
	public static int DEATH_PENALTY_CHANCE;
	
	/** Inventory & WH */
	public static int INVENTORY_MAXIMUM_NO_DWARF;
	public static int INVENTORY_MAXIMUM_DWARF;
	public static int INVENTORY_MAXIMUM_QUEST_ITEMS;
	public static int INVENTORY_MAXIMUM_PET;
	public static int MAX_ITEM_IN_PACKET;
	public static double ALT_WEIGHT_LIMIT;
	public static int WAREHOUSE_SLOTS_NO_DWARF;
	public static int WAREHOUSE_SLOTS_DWARF;
	public static int WAREHOUSE_SLOTS_CLAN;
	public static int FREIGHT_SLOTS;
	public static boolean ALT_GAME_FREIGHTS;
	public static int ALT_GAME_FREIGHT_PRICE;
	
	/** Enchant */
	public static double ENCHANT_CHANCE_WEAPON_MAGIC;
	public static double ENCHANT_CHANCE_WEAPON_MAGIC_15PLUS;
	public static double ENCHANT_CHANCE_WEAPON_NONMAGIC;
	public static double ENCHANT_CHANCE_WEAPON_NONMAGIC_15PLUS;
	public static double ENCHANT_CHANCE_ARMOR;
	public static int ENCHANT_MAX_WEAPON;
	public static int ENCHANT_MAX_ARMOR;
	public static int ENCHANT_SAFE_MAX;
	public static int ENCHANT_SAFE_MAX_FULL;
	public static int BREAK_ENCHANT;
	
	/** Augmentations */
	public static int AUGMENTATION_NG_SKILL_CHANCE;
	public static int AUGMENTATION_NG_GLOW_CHANCE;
	public static int AUGMENTATION_MID_SKILL_CHANCE;
	public static int AUGMENTATION_MID_GLOW_CHANCE;
	public static int AUGMENTATION_HIGH_SKILL_CHANCE;
	public static int AUGMENTATION_HIGH_GLOW_CHANCE;
	public static int AUGMENTATION_TOP_SKILL_CHANCE;
	public static int AUGMENTATION_TOP_GLOW_CHANCE;
	public static int AUGMENTATION_BASESTAT_CHANCE;
	
	/** Karma & PvP */
	public static boolean KARMA_PLAYER_CAN_BE_KILLED_IN_PZ;
	public static boolean KARMA_PLAYER_CAN_SHOP;
	public static boolean KARMA_PLAYER_CAN_USE_GK;
	public static boolean KARMA_PLAYER_CAN_TELEPORT;
	public static boolean KARMA_PLAYER_CAN_TRADE;
	public static boolean KARMA_PLAYER_CAN_USE_WH;
	public static boolean FLAGED_PLAYER_CAN_USE_GK;
	
	public static boolean KARMA_DROP_GM;
	public static boolean KARMA_AWARD_PK_KILL;
	public static int KARMA_PK_LIMIT;
	
	public static String KARMA_NONDROPPABLE_PET_ITEMS;
	public static String KARMA_NONDROPPABLE_ITEMS;
	public static int[] KARMA_LIST_NONDROPPABLE_PET_ITEMS;
	public static int[] KARMA_LIST_NONDROPPABLE_ITEMS;
	
	public static int PVP_NORMAL_TIME;
	public static int PVP_PVP_TIME;
	
	/** PvP & Pk Reward */
	public static boolean ALLOW_PVP_REWARD;
	public static int PVP_REWARD_ITEM;
	public static int PVP_REWARD_COUNT;
	public static boolean ALLOW_PK_REWARD;
	public static int PK_REWARD_ITEM;
	public static int PK_REWARD_COUNT;
	
	/** PvP & Pk Name / Title Color System */
	public static boolean PVP_COLOR_SYSTEM_ENABLED;
	public static int PVP_AMOUNT1;
	public static int PVP_AMOUNT2;
	public static int PVP_AMOUNT3;
	public static int PVP_AMOUNT4;
	public static int PVP_AMOUNT5;
	public static int NAME_COLOR_FOR_PVP_AMOUNT1;
	public static int NAME_COLOR_FOR_PVP_AMOUNT2;
	public static int NAME_COLOR_FOR_PVP_AMOUNT3;
	public static int NAME_COLOR_FOR_PVP_AMOUNT4;
	public static int NAME_COLOR_FOR_PVP_AMOUNT5;
	public static boolean PK_COLOR_SYSTEM_ENABLED;
	public static int PK_AMOUNT1;
	public static int PK_AMOUNT2;
	public static int PK_AMOUNT3;
	public static int PK_AMOUNT4;
	public static int PK_AMOUNT5;
	public static int TITLE_COLOR_FOR_PK_AMOUNT1;
	public static int TITLE_COLOR_FOR_PK_AMOUNT2;
	public static int TITLE_COLOR_FOR_PK_AMOUNT3;
	public static int TITLE_COLOR_FOR_PK_AMOUNT4;
	public static int TITLE_COLOR_FOR_PK_AMOUNT5;
	
	/** Party */
	public static String PARTY_XP_CUTOFF_METHOD;
	public static int PARTY_XP_CUTOFF_LEVEL;
	public static double PARTY_XP_CUTOFF_PERCENT;
	public static int ALT_PARTY_RANGE;
	public static int ALT_PARTY_RANGE2;
	public static boolean ALT_LEAVE_PARTY_LEADER;
	
	/** GMs & Admin Stuff */
	public static boolean EVERYBODY_HAS_ADMIN_RIGHTS;
	public static int MASTERACCESS_LEVEL;
	public static int MASTERACCESS_NAME_COLOR;
	public static int MASTERACCESS_TITLE_COLOR;
	public static boolean GM_HERO_AURA;
	public static boolean GM_STARTUP_INVULNERABLE;
	public static boolean GM_STARTUP_INVISIBLE;
	public static boolean GM_STARTUP_SILENCE;
	public static boolean GM_STARTUP_AUTO_LIST;
	
	/** Admin Mods */
	/** Clan Full */
	public static boolean ENABLE_CLAN_SYSTEM;
	public static Map<Integer, Integer> CLAN_SKILLS;
	public static byte CLAN_LEVEL;
	public static int REPUTATION_QUANTITY;
	
	/** petitions */
	public static boolean PETITIONING_ALLOWED;
	public static boolean PETITION_LOGGING;
	public static int MAX_PETITIONS_PER_PLAYER;
	public static int MAX_PETITIONS_PENDING;
	
	/** Crafting **/
	public static boolean IS_CRAFTING_ENABLED;
	public static int DWARF_RECIPE_LIMIT;
	public static int COMMON_RECIPE_LIMIT;
	public static boolean ALT_BLACKSMITH_USE_RECIPES;
	
	/** Skills & Classes **/
	public static boolean AUTO_LEARN_SKILLS;
	public static boolean ALT_GAME_MAGICFAILURES;
	public static boolean ALT_GAME_SHIELD_BLOCKS;
	public static int ALT_PERFECT_SHLD_BLOCK;
	public static boolean LIFE_CRYSTAL_NEEDED;
	public static boolean SP_BOOK_NEEDED;
	public static boolean ES_SP_BOOK_NEEDED;
	public static boolean DIVINE_SP_BOOK_NEEDED;
	public static boolean ALT_GAME_SUBCLASS_WITHOUT_QUESTS;
	
	/** Buffs */
	public static boolean STORE_SKILL_COOLTIME;
	public static int BUFFS_MAX_AMOUNT;

	public static long CLASS_BALANCER_UPDATE_DELAY;
	public static boolean CLASS_BALANCER_AFFECTS_SECOND_PROFFESION;
	public static boolean CLASS_BALANCER_AFFECTS_MONSTERS;

	public static long SKILLS_BALANCER_UPDATE_DELAY;
	public static boolean SKILLS_BALANCER_AFFECTS_SECOND_PROFFESION;
	public static boolean SKILLS_BALANCER_AFFECTS_MONSTERS;
	
 	// --------------------------------------------------
	// Custom Siege Config
	// --------------------------------------------------
	/** Siege day of each castle */
	// Gludio
	public static int SIEGEDAYCASTLEGludio;
	// Dion
	public static int SIEGEDAYCASTLEDion;
	// Giran
	public static int SIEGEDAYCASTLEGiran;
	// Oren
	public static int SIEGEDAYCASTLEOren;
	// Aden
	public static int SIEGEDAYCASTLEAden;
	// Innadril/Heine
	public static int SIEGEDAYCASTLEInnadril;
	// Goddard
	public static int SIEGEDAYCASTLEGoddard;
	// Rune
	public static int SIEGEDAYCASTLERune;
	// Schuttgart
	public static int SIEGEDAYCASTLESchuttgart;
	/** Next siege time config (Retail 2)*/
	public static int NEXT_SIEGE_TIME;

	//--------------------------------------------------
	// Castles Siege Time
	//-------------------------------------------------
	
	// Gludio
	public static int SIEGEHOURCASTLEGludio;
	// Dion
	public static int SIEGEHOURCASTLEDion;
	// Giran
	public static int SIEGEHOURCASTLEGiran;
	// Oren
	public static int SIEGEHOURCASTLEOren;
	// Aden
	public static int SIEGEHOURCASTLEAden;
	// Innadril/Heine
	public static int SIEGEHOURCASTLEInnadril;
	// Goddard
	public static int SIEGEHOURCASTLEGoddard;
	// Rune
	public static int SIEGEHOURCASTLERune;
	// Schuttgart
	public static int SIEGEHOURCASTLESchuttgart;

	
	// --------------------------------------------------
	// Server
	// --------------------------------------------------
	
	public static String GAMESERVER_HOSTNAME;
	public static int PORT_GAME;
	public static String EXTERNAL_HOSTNAME;
	public static String INTERNAL_HOSTNAME;
	public static int GAME_SERVER_LOGIN_PORT;
	public static String GAME_SERVER_LOGIN_HOST;
	
	/** Access to database */
	public static String DATABASE_URL;
	public static String DATABASE_LOGIN;
	public static String DATABASE_PASSWORD;
	public static int DATABASE_MAX_CONNECTIONS;
	public static int DATABASE_MAX_IDLE_TIME;
	
	/** serverList & Test */
	public static boolean SERVER_LIST_BRACKET;
	public static boolean SERVER_LIST_CLOCK;
	public static boolean SERVER_LIST_TESTSERVER;
	public static boolean SERVER_GMONLY;
	public static boolean TEST_SERVER;
	
	/** clients related */
	public static int DELETE_DAYS;
	public static int MAXIMUM_ONLINE_USERS;
	public static int MIN_PROTOCOL_REVISION;
	public static int MAX_PROTOCOL_REVISION;
	
	/** Jail & Punishements **/
	public static boolean JAIL_IS_PVP;
	public static int DEFAULT_PUNISH;
	public static int DEFAULT_PUNISH_PARAM;
	
	/** Auto-loot */
	public static boolean AUTO_LOOT;
	public static boolean AUTO_LOOT_HERBS;
	public static boolean AUTO_LOOT_RAID;
	
	/** Items Management */
	public static boolean ALLOW_DISCARDITEM;
	public static boolean MULTIPLE_ITEM_DROP;
	public static int ITEM_AUTO_DESTROY_TIME;
	public static int HERB_AUTO_DESTROY_TIME;
	public static String PROTECTED_ITEMS;
	
	public static List<Integer> LIST_PROTECTED_ITEMS;
	
	public static boolean DESTROY_DROPPED_PLAYER_ITEM;
	public static boolean DESTROY_EQUIPABLE_PLAYER_ITEM;
	public static boolean SAVE_DROPPED_ITEM;
	public static boolean EMPTY_DROPPED_ITEM_TABLE_AFTER_LOAD;
	public static int SAVE_DROPPED_ITEM_INTERVAL;
	public static boolean CLEAR_DROPPED_ITEM_TABLE;
	
	/** Rate control */
	public static double RATE_XP;
	public static double RATE_SP;
	public static double SATURDAY_RATE_XP;
	public static double SATURDAY_RATE_SP;
	public static double SUNDAY_RATE_XP;
	public static double SUNDAY_RATE_SP;
	public static double RATE_PARTY_XP;
	public static double RATE_PARTY_SP;
	public static double RATE_DROP_ADENA;
	public static double RATE_CONSUMABLE_COST;
	public static double RATE_DROP_ITEMS;
	public static double RATE_DROP_ITEMS_BY_RAID;
	public static double RATE_DROP_SPOIL;
	public static int RATE_DROP_MANOR;
	
	public static double RATE_QUEST_DROP;
	public static double RATE_QUEST_REWARD;
	public static double RATE_QUEST_REWARD_XP;
	public static double RATE_QUEST_REWARD_SP;
	public static double RATE_QUEST_REWARD_ADENA;
	
	public static double RATE_KARMA_EXP_LOST;
	public static double RATE_SIEGE_GUARDS_PRICE;
	
	public static int PLAYER_DROP_LIMIT;
	public static int PLAYER_RATE_DROP;
	public static int PLAYER_RATE_DROP_ITEM;
	public static int PLAYER_RATE_DROP_EQUIP;
	public static int PLAYER_RATE_DROP_EQUIP_WEAPON;
	
	public static int KARMA_DROP_LIMIT;
	public static int KARMA_RATE_DROP;
	public static int KARMA_RATE_DROP_ITEM;
	public static int KARMA_RATE_DROP_EQUIP;
	public static int KARMA_RATE_DROP_EQUIP_WEAPON;
	
	public static double PET_XP_RATE;
	public static int PET_FOOD_RATE;
	public static double SINEATER_XP_RATE;
	
	public static double RATE_DROP_COMMON_HERBS;
	public static double RATE_DROP_HP_HERBS;
	public static double RATE_DROP_MP_HERBS;
	public static double RATE_DROP_SPECIAL_HERBS;
	
	/** Allow types */
	public static boolean ALLOW_FREIGHT;
	public static boolean ALLOW_WAREHOUSE;
	public static boolean ALLOW_WEAR;
	public static int WEAR_DELAY;
	public static int WEAR_PRICE;
	public static boolean ALLOW_LOTTERY;
	public static boolean ALLOW_RACE;
	public static boolean ALLOW_WATER;
	public static boolean ALLOWFISHING;
	public static boolean ALLOW_BOAT;
	public static boolean ALLOW_CURSED_WEAPONS;
	public static boolean ALLOW_MANOR;
	public static boolean ENABLE_FALLING_DAMAGE;
	
	/** Debug & Dev */
	public static boolean ALT_DEV_NO_SCRIPTS;
	public static boolean ALT_DEV_NO_SPAWNS;
	public static boolean PACKET_HANDLER_DEBUG;
	
	/** Deadlock Detector */
	public static boolean DEADLOCK_DETECTOR;
	public static int DEADLOCK_CHECK_INTERVAL;
	public static boolean RESTART_ON_DEADLOCK;
	
	/** Logs */
	public static boolean LOG_CHAT;
	public static boolean LOG_ITEMS;
	public static boolean GMAUDIT;
	
	/** Community Board */
	public static boolean ENABLE_COMMUNITY_BOARD;
	public static String BBS_DEFAULT;
	public static int RAID_LIST_ROW_HEIGHT;
	public static int RAID_LIST_RESULTS;
	public static boolean RAID_LIST_SORT_ASC;
	public static boolean CustomCB;
	public static int TOP_PLAYER_ROW_HEIGHT;
	public static int TOP_PLAYER_RESULTS;
	public static String ALLOW_CLASS_MASTERSCB;
	public static String CLASS_MASTERS_PRICECB;
	public static int CLASS_MASTERS_PRICE_ITEMCB;
	public static int[] CLASS_MASTERS_PRICE_LISTCB = new int[4];
	public static ArrayList<Integer> ALLOW_CLASS_MASTERS_LISTCB = new ArrayList<>();
	public static boolean ENABLE_BBS_PASS_CHANGE;
	public static boolean ENABLE_BBS_REPORT;
	public static String PAY_PAL_BBS_LINK;
	public static boolean ENABLE_PAY_SAFE_DONATION_BBS;
	public static boolean ENABLE_PAY_PAL_DONATION_BBS;
	public static List<String> COMMUNITY_BUFFER_EXCLUDE_ON = new ArrayList<>();
	public static List<String> COMMUNITY_GATEKEEPER_EXCLUDE_ON = new ArrayList<>();
	
	/** Geodata */
	public static int GEODATA;
	public static String GEODATA_PATH;
	public static GeoFormat GEODATA_FORMAT;
	public static boolean GEODATA_DIAGONAL;
	public static int COORD_SYNCHRONIZE;
	
	/** Path checking */
	public static int PART_OF_CHARACTER_HEIGHT;
	public static int MAX_OBSTACLE_HEIGHT;
	
	/** Path finding */
	public static String PATHFIND_BUFFERS;
	public static int BASE_WEIGHT;
	public static int DIAGONAL_WEIGHT;
	public static int HEURISTIC_WEIGHT;
	public static int OBSTACLE_MULTIPLIER;
	public static int MAX_ITERATIONS;
	public static boolean DEBUG_PATH;
	
	/** Misc */
	public static boolean L2WALKER_PROTECTION;
	public static boolean AUTODELETE_INVALID_QUEST_DATA;
	public static boolean GAMEGUARD_ENFORCE;
	public static boolean SERVER_NEWS;
	public static int ZONE_TOWN;
	public static boolean DISABLE_TUTORIAL;
	
	/** Offline stores */
	public static boolean OFFLINE_TRADE_ENABLE;
	public static boolean OFFLINE_CRAFT_ENABLE;
	public static boolean RESTORE_OFFLINERS;
	public static int OFFLINE_MAX_DAYS;
	public static boolean OFFLINE_DISCONNECT_FINISHED;
	public static boolean OFFLINE_SET_NAME_COLOR;
	public static int OFFLINE_NAME_COLOR;
	public static boolean OFFLINE_FAME;
	public static boolean OFFLINE_TRADE_EFFECT;
	public static int OFFLINE_EFFECT_ID;
	
	/** Vote Manager settings */
	public static boolean ALLOW_HOPZONE_VOTE_REWARD;
	public static String HOPZONE_SERVER_LINK;
	public static String HOPZONE_FIRST_PAGE_LINK;
	public static int HOPZONE_VOTES_DIFFERENCE;
	public static int HOPZONE_FIRST_PAGE_RANK_NEEDED;
	public static int HOPZONE_REWARD_CHECK_TIME;
	public static int[][] HOPZONE_SMALL_REWARD;
	public static int[][] HOPZONE_BIG_REWARD;
	public static int HOPZONE_DUALBOXES_ALLOWED;
	public static boolean ALLOW_HOPZONE_GAME_SERVER_REPORT;
	public static boolean ALLOW_TOPZONE_VOTE_REWARD;
	public static String TOPZONE_SERVER_LINK;
	public static String TOPZONE_FIRST_PAGE_LINK;
	public static int TOPZONE_VOTES_DIFFERENCE;
	public static int TOPZONE_FIRST_PAGE_RANK_NEEDED;
	public static int TOPZONE_REWARD_CHECK_TIME;
	public static int[][] TOPZONE_SMALL_REWARD;
	public static int[][] TOPZONE_BIG_REWARD;
	public static int TOPZONE_DUALBOXES_ALLOWED;
	public static boolean ALLOW_TOPZONE_GAME_SERVER_REPORT;
	
	/** Full Armor & Weapon Restrictions */
	public static boolean ALLOW_LIGHT_USE_HEAVY;
	public static String NOTALLOWCLASS;
	public static List<Integer> NOTALLOWEDUSEHEAVY;
	public static boolean ALLOW_HEAVY_USE_LIGHT;
	public static String NOTALLOWCLASSE;
	public static List<Integer> NOTALLOWEDUSELIGHT;
	
	public static boolean ALT_DISABLE_BOW_CLASSES;
	public static String DISABLE_BOW_CLASSES_STRING;
	public static ArrayList<Integer> DISABLE_BOW_CLASSES = new ArrayList<>();
	public static boolean ALT_DISABLE_DAGGER_CLASSES;
	public static String DISABLE_DAGGER_CLASSES_STRING;
	public static ArrayList<Integer> DISABLE_DAGGER_CLASSES = new ArrayList<>();
	public static boolean ALT_DISABLE_SWORD_CLASSES;
	public static String DISABLE_SWORD_CLASSES_STRING;
	public static ArrayList<Integer> DISABLE_SWORD_CLASSES = new ArrayList<>();
	public static boolean ALT_DISABLE_BLUNT_CLASSES;
	public static String DISABLE_BLUNT_CLASSES_STRING;
	public static ArrayList<Integer> DISABLE_BLUNT_CLASSES = new ArrayList<>();
	public static boolean ALT_DISABLE_DUAL_CLASSES;
	public static String DISABLE_DUAL_CLASSES_STRING;
	public static ArrayList<Integer> DISABLE_DUAL_CLASSES = new ArrayList<>();
	public static boolean ALT_DISABLE_POLE_CLASSES;
	public static String DISABLE_POLE_CLASSES_STRING;
	public static ArrayList<Integer> DISABLE_POLE_CLASSES = new ArrayList<>();
	public static boolean ALT_DISABLE_BIGSWORD_CLASSES;
	public static String DISABLE_BIGSWORD_CLASSES_STRING;
	public static ArrayList<Integer> DISABLE_BIGSWORD_CLASSES = new ArrayList<>();
	
	/** Change & Add SubClass At Any Village Master */
	public static boolean ALT_GAME_SUBCLASS_EVERYWHERE;
	
	/** Enchanter Npc */
	public static int ITEM_NEEDED_FOR_ENCH;
	public static int AM_ITEM_NEEDED_FOR_ENCH;
	public static int MAX_ENCH_FOR_NPC;
	public static int ENCH_ADDITION;
	public static int ENCH_FAIL_LVL;
	public static int ENCH_SAFE_NPC;
	public static int ENCHANT_CHANCE_ARM_NPC;
	public static int ENCHANT_CHANCE_WEAP_NPC;
	public static int ENCHANT_CHANCE_JEWELERY_NPC;
	
	/** Account Manager */
	public static boolean ALLOW_ACCOUNT_MANAGER;
	public static String EMAIL_USER;
	public static String EMAIL_PASS;
	
	/** Start Up System */
	public static boolean STARTUP_SYSTEM_ENABLED;
	
	/** Lucky Blessed Scrolls */
	public static boolean ENALBE_LUCKY_SCROLL;
	public static int LUCKY_SCROLL_ID;
	
	/** Skill Giver */
	public static int SKILL_GIVER_SKILL_ID;
	public static int SKILL_GIVER_SKILL_LEVEL;
	public static int SKILL_GIVER_ID;
	
	/** Emotions On Talk */
	public static boolean ENABLE_SAY_SOCIAL_ACTIONS;
	
	/** All / Trade Chat System */
	public static boolean ENABLE_SHOUT_CHAT_FOR_PVPS;
	public static boolean ENABLE_TRADE_CHAT_FOR_PVPS;
	public static int SHOUT_CHAT_PVPS;
	public static int TRADE_CHAT_PVPS;
	
	public static boolean ENABLE_SHOUT_CHAT_FOR_LEVEL;
	public static int SHOUT_CHAT_LEVEL;
	
	/** PvPs / PKs To Use Store */
	public static int MIN_PVP_TO_USE_STORE;
	public static int MIN_PK_TO_USE_STORE;
	
	/** Keyboard Movement */
	public static boolean ALLOW_KEYBOARD_MOVEMENT;
	
	/** Announce Spawn RB */
	public static boolean ANNOUNCE_TO_ALL_SPAWN_RB;
	
	/** Add Exp , SP At Pvp! */
	public static boolean ADD_EXP_SP;
	public static int ADD_EXP;
	public static int ADD_SP;
	
	/** Announce PvP & Pk */
	public static boolean ANNOUNCE_PK_PVP;
	public static boolean ANNOUNCE_PK_PVP_NORMAL_MESSAGE;
	public static String ANNOUNCE_PK_MSG;
	public static String ANNOUNCE_PVP_MSG;
	
	/** Santa hat for new players */
	public static boolean ALLOW_SANTA_HAT_ON_NEW_CHARACTERS;
	
	/** Skill Giver */
	public static boolean ALLOW_SKILL_GIVER;
	
	/** Auto pvp enchant */
	public static boolean ENABLE_PVP_ENCHANTMENT;
	public static int PVP_COUNT_TILL_ENCHANTMENT;
	
	/** Random Reward Box */
	public static boolean ALLOW_REWARD_BOX;
	public static int REWARD_BOX_ID;
	public static int FIRST_REWARD_ID;
	public static int FIRST_REWARD_COUNT;
	public static int SECOND_REWARD_ID;
	public static int SECOND_REWARD_COUNT;
	public static int THIRD_REWARD_ID;
	public static int THIRD_REWARD_COUNT;
	
	/** Clan Reputation Item */
	public static boolean USE_CR_ITEM;
	public static int CR_ITEM_MIN_CLAN_LVL;
	public static int CR_ITEM_REPS_TO_BE_AWARDED;
	public static int CR_ITEM_REPS_ITEM_ID;
	
	/** Custom Spawn For Mages And Fighters */
	public static boolean CUSTOM_SPAWN_CLASS;
	public static int SPANW_MAGE_X;
	public static int SPANW_MAGE_Y;
	public static int SPANW_MAGE_Z;
	public static int SPANW_FIGHTER_X;
	public static int SPANW_FIGHTER_Y;
	public static int SPANW_FIGHTER_Z;
	
	/** Auto Rewarder Every X Time */
	public static boolean ALLOW_AUTO_REWARDER;
	public static int AUTO_REWARDER_SCHEDULE;
	public static int[][] AUTO_REWARDER_REWARDS;
	public static int AUTO_REWARDER_DUALBOX_ALLOWED;
	
	/** Automatic Announce */
	public static int AUTO_ANNOUNCE_DELAY;
	public static String AUTO_ANNOUNCE_TEXT;
	public static boolean AUTO_ANNOUNCE_ENABLE;
	
	/** Auto Restart */
	public static boolean RESTART_BY_TIME_OF_DAY;
	public static int RESTART_SECONDS;
	public static String[] RESTART_INTERVAL_BY_TIME_OF_DAY;
	
	/** Social Action On Login */
	public static boolean SOCIAL_ACTION_ON_LOGIN;
	
	/** Last Visit */
	public static boolean ALLOW_LAST_VISIT_INFORMATION;
	
	/** Welcome Message On Screen */
	public static boolean WELCOME_MESSAGE_ENABLED;
	public static String WELCOME_MESSAGE_TEXT;
	public static int WELCOME_MESSAGE_TIME;
	
	/** Entering pms */
	public static boolean ALLOW_ENTER_PMS;
	public static int ENTER_PMS_SYSTEM;
	public static Map<String, String> ENTER_PMS = new HashMap<>();
	
	/** Admin On Login */
	public static boolean ANNOUNCE_ADMIN_LOGIN;
	/** Clan Leader On Login */
	public static boolean ANNOUNCE_CLAN_LOGIN;
	/** God On Login */
	public static boolean ANNOUNCE_GOD_LOGIN;
	/** Hero On Login */
	public static boolean ANNOUNCE_HERO_LOGIN;
	/** Noble On Login */
	public static boolean ANNOUNCE_NOBLE_LOGIN;
	/** AIO On Login */
	public static boolean ANNOUNCE_AIO_LOGIN;
	/** Lords On Login */
	public static boolean ANNOUNCE_LORDS_LOGIN;
	/** Online Players On Login & Auto Announce Every X Seconds */
	public static boolean ONLINE_PLAYERS_ON_LOGIN;
	public static int ANNOUNCE_ONLINE_PLAYER_EVERY;
	public static boolean ALLOW_ANNOUNCE_ONLINE_PLAYERS;
	/** Fake players announce */
	public static boolean ADDFAKEPLAYERSMODE;
	public static int ADDFAKEPLAYERSNUMBER;
	/** Olympiad End on login */
	public static boolean OLYMPIAD_END_ON_LOGIN;
	/** Server Time On Login */
	public static boolean SERVER_TIME_ON_LOGIN;
	/** Show Server Name At Login */
	public static boolean ALT_SERVER_NAME_ENABLED;
	public static String ALT_SERVER_NAME;
	/** Newbie On Login */
	public static boolean ANNOUNCE_NEWBIE_LOGIN;
	/** Show Aio Online */
	public static boolean SHOW_AIO_ENABLE;
	public static boolean SHOW_OFFLINE_SHOP;
	/** New Char Buffs On Login */
	public static boolean NEW_PLAYER_BUFFS;
	public static int[][] NEW_PLAYER_MAGE_BUFF_LIST;
	public static int[][] NEW_PLAYER_FIGHT_BUFF_LIST;
	
	/** Aio System */
	public static boolean ENABLE_AIO_SYSTEM;
	public static Map<Integer, Integer> AIO_SKILLS;
	public static boolean ALLOW_AIO_NCOLOR;
	public static int AIO_NCOLOR;
	public static boolean ALLOW_AIO_TCOLOR;
	public static int AIO_TCOLOR;
	public static boolean ALLOW_AIO_ITEM;
	public static int AIO_ITEMID;
	
	/** Vip System */
	public static long VIP_LIFE_TIME;
	public static boolean VIP_NAME_COLOR_CONFIG;
	public static String VIPS_NAME_COLOR;
	public static String VIPS_TITLE_COLOR;
	public static boolean VIPS_HERO_AURA;
	public static boolean VIPS_SKILLS_CONFIG;
	public static HashMap<Integer, Integer> VIP_SKILLS;
	public static boolean VIPS_RATE_CONFIG;
	public static double RATE_XP_VIP;
	public static double RATE_SP_VIP;
	public static double RATE_DROP_ADENA_VIP;
	public static double RATE_DROP_ITEMS_VIP;
	public static double RATE_DROP_ITEMS_BY_RAID_VIP;
	public static double RATE_DROP_SPOIL_VIP;
	public static boolean VIP_ENCHANT_CONFIG;
	public static double VIP_ENCHANT_CHANCE_WEAPON_MAGIC;
	public static double VIP_ENCHANT_CHANCE_WEAPON_MAGIC_15PLUS;
	public static double VIP_ENCHANT_CHANCE_WEAPON_NONMAGIC;
	public static double VIP_ENCHANT_CHANCE_WEAPON_NONMAGIC_15PLUS;
	public static double VIP_ENCHANT_CHANCE_ARMOR;
	public static boolean ENABLE_VIP_NPC;
	public static int VIP_NPC_ITEM;
	
	/** CUSTOM START TITLE */
	public static boolean ALLOW_START_TITLE;
	public static String START_TITLE;
	
	/** Skill Duration List */
	public static boolean ENABLE_MODIFY_SKILL_DURATION;
	public static HashMap<Integer, Integer> SKILL_DURATION_LIST;
	
	/** Chat Manager */
	public static String DEFAULT_GLOBAL_CHAT;
	public static String DEFAULT_TRADE_CHAT;
	
	public static boolean ALLOW_AIO_AND_VIP_CHAT;
	
	// --------------------------------------------------
	// Teleports Commands Settings
	// --------------------------------------------------
	
	/** .buff Command */
	public static boolean ENABLE_BUFF_COMMAND;
	public static String LIST_BUFF_COMMAND;
	public static int[] BUFF_COMMAND_FIGHT_IDBUFFS;
	public static int[] BUFF_COMMAND_MAGE_IDBUFFS;
	
	/** Allow Commands */
	public static boolean ALLOW_FARM1_COMMAND;
	public static boolean ALLOW_FARM2_COMMAND;
	public static boolean ALLOW_FARM3_COMMAND;
	public static boolean ALLOW_PVP1_COMMAND;
	public static boolean ALLOW_PVP2_COMMAND;
	public static boolean ALLOW_PVP3_COMMAND;
	public static boolean ALLOW_GIRAN_COMMAND;
	public static boolean ALLOW_DION_COMMAND;
	public static boolean ALLOW_ADEN_COMMAND;
	public static boolean ALLOW_GODDARD_COMMAND;
	public static boolean ALLOW_GLUDIO_COMMAND;
	public static boolean ALLOW_RUNE_COMMAND;
	public static boolean ALLOW_HEINE_COMMAND;
	public static boolean ALLOW_SCHUTTGART_COMMAND;
	public static boolean ALLOW_OREN_COMMAND;
	
	/** X Y Z */
	public static int FARM1_X;
	public static int FARM1_Y;
	public static int FARM1_Z;
	
	public static int FARM2_X;
	public static int FARM2_Y;
	public static int FARM2_Z;
	
	public static int FARM3_X;
	public static int FARM3_Y;
	public static int FARM3_Z;
	
	public static int PVP1_X;
	public static int PVP1_Y;
	public static int PVP1_Z;
	
	public static int PVP2_X;
	public static int PVP2_Y;
	public static int PVP2_Z;
	
	public static int PVP3_X;
	public static int PVP3_Y;
	public static int PVP3_Z;
	
	/** TOWNS X Y Z */
	public static int GIRAN_X;
	public static int GIRAN_Y;
	public static int GIRAN_Z;
	
	public static int DION_X;
	public static int DION_Y;
	public static int DION_Z;
	
	public static int ADEN_X;
	public static int ADEN_Y;
	public static int ADEN_Z;
	
	public static int GODDARD_X;
	public static int GODDARD_Y;
	public static int GODDARD_Z;
	
	public static int GLUDIO_X;
	public static int GLUDIO_Y;
	public static int GLUDIO_Z;
	
	public static int RUNE_X;
	public static int RUNE_Y;
	public static int RUNE_Z;
	
	public static int HEINE_X;
	public static int HEINE_Y;
	public static int HEINE_Z;
	
	public static int SCHUTTGART_X;
	public static int SCHUTTGART_Y;
	public static int SCHUTTGART_Z;
	
	public static int OREN_X;
	public static int OREN_Y;
	public static int OREN_Z;
	
	/** Custom Message On Farm */
	public static String FARM1_CUSTOM_MESSAGE;
	public static String FARM2_CUSTOM_MESSAGE;
	public static String FARM3_CUSTOM_MESSAGE;
	
	/** Custom Message On PvP */
	public static String PVP1_CUSTOM_MESSAGE;
	public static String PVP2_CUSTOM_MESSAGE;
	public static String PVP3_CUSTOM_MESSAGE;
	
	/** Custom Message On Towns */
	public static String GIRAN_CUSTOM_MESSAGE;
	public static String DION_CUSTOM_MESSAGE;
	public static String ADEN_CUSTOM_MESSAGE;
	public static String GODDARD_CUSTOM_MESSAGE;
	public static String GLUDIO_CUSTOM_MESSAGE;
	public static String RUNE_CUSTOM_MESSAGE;
	public static String HEINE_CUSTOM_MESSAGE;
	public static String SCHUTTGART_CUSTOM_MESSAGE;
	public static String OREN_CUSTOM_MESSAGE;
	
	// --------------------------------------------------
	// Commands Settings
	// --------------------------------------------------
	
	/** .bossinfo VC */
	public static boolean ENABLE_BOSSINFO_VC;
	public static String GRAND_BOSS;
	public static ArrayList<Integer> GRAND_BOSS_LIST = new ArrayList<>();
	
	/** .gotocastle VC */
	public static boolean GO_TO_CASTLE_COMMAND_ENABLE;
	
	/** .xmas VC */
	public static boolean XMAS_COMMAND_ENABLE;
	
	/** .whoami VC */
	public static boolean ENABLE_WHO_AM_I_VC;
	
	/** .online VC */
	public static boolean ONLINE_PLAYERS;
	
	/** .pincode VC */
	public static boolean ENABLE_PIN_SYSTEM;
	
	/** .gearscore VC */
	public static boolean GEARSCORE_ENABLED;
	
	/** .details VC */
	public static boolean VIEWDETAILS_ENABLED;
	
	/** .version VC */
	public static boolean ENABLE_VERSION_COMMAND;
	
	/** Mail System */
	public static boolean ENABLE_MAIL_SYSTEM;
	
	/** .menu VC */
	public static boolean ENABLE_PERSONAL_MENU_SYSTEM;
	public static int SKIN;
	public static int USER_FIRST_LVL;
	public static int USER_FIRST;
	public static int ON_ENTER_MAIL;
	
	/** .res */
	public static boolean RES_COMMAND;
	
	/** Register on sieges by command */
	public static boolean REGISTER_SIEGE_BY_COMMAND;
	
	/** Banking System */
	public static boolean BANKING_SYSTEM_ENABLED;
	public static int BANKING_SYSTEM_GOLDBARS;
	public static int BANKING_SYSTEM_ADENA;
	
	/** Vote Buff Command .getreward */
	public static int VOTE_ITEM_ID;
	public static int VOTE_ITEM_AMOUNT;
	public static int VOTE_BUFF_ID;
	public static int VOTE_BUFF_LVL;
	public static boolean VOTE_BUFF_ENABLED;
	public static String VOTE_ITEM_NAME;
	
	// --------------------------------------------------
	// Those "hidden" settings haven't configs to avoid admins to fuck their server
	// You still can experiment changing values here. But don't say I didn't warn you.
	// --------------------------------------------------
	
	/** Threads & Packets size */
	public static int THREAD_P_EFFECTS = 6; // default 6
	public static int THREAD_P_GENERAL = 15; // default 15
	public static int GENERAL_PACKET_THREAD_CORE_SIZE = 4; // default 4
	public static int IO_PACKET_THREAD_CORE_SIZE = 2; // default 2
	public static int GENERAL_THREAD_CORE_SIZE = 4; // default 4
	public static int AI_MAX_THREAD = 10; // default 10
	
	/** Packet information */
	public static boolean COUNT_PACKETS = false; // default false
	public static boolean DUMP_PACKET_COUNTS = false; // default false
	public static int DUMP_INTERVAL_SECONDS = 60; // default 60
	
	/** IA settings */
	public static int MINIMUM_UPDATE_DISTANCE = 50; // default 50
	public static int MINIMUN_UPDATE_TIME = 500; // default 500
	public static int KNOWNLIST_FORGET_DELAY = 10000; // default 10000
	
	/** Time after which a packet is considered as lost */
	public static int PACKET_LIFETIME = 0; // default 0 (unlimited)
	
	/** Reserve Host on LoginServerThread */
	public static boolean RESERVE_HOST_ON_LOGIN = false; // default false
	
	/** MMO settings */
	public static int MMO_SELECTOR_SLEEP_TIME = 20; // default 20
	public static int MMO_MAX_SEND_PER_PASS = 12; // default 12
	public static int MMO_MAX_READ_PER_PASS = 12; // default 12
	public static int MMO_HELPER_BUFFER_COUNT = 20; // default 20
	
	/** Client Packets Queue settings */
	public static int CLIENT_PACKET_QUEUE_SIZE = 14; // default MMO_MAX_READ_PER_PASS + 2
	public static int CLIENT_PACKET_QUEUE_MAX_BURST_SIZE = 13; // default MMO_MAX_READ_PER_PASS + 1
	public static int CLIENT_PACKET_QUEUE_MAX_PACKETS_PER_SECOND = 80; // default 80
	public static int CLIENT_PACKET_QUEUE_MEASURE_INTERVAL = 5; // default 5
	public static int CLIENT_PACKET_QUEUE_MAX_AVERAGE_PACKETS_PER_SECOND = 40; // default 40
	public static int CLIENT_PACKET_QUEUE_MAX_FLOODS_PER_MIN = 2; // default 2
	public static int CLIENT_PACKET_QUEUE_MAX_OVERFLOWS_PER_MIN = 1; // default 1
	public static int CLIENT_PACKET_QUEUE_MAX_UNDERFLOWS_PER_MIN = 1; // default 1
	public static int CLIENT_PACKET_QUEUE_MAX_UNKNOWN_PER_MIN = 5; // default 5
	
	// --------------------------------------------------
	
	/**
	 * This class initializes all global variables for configuration.<br>
	 * If key doesn't appear in properties file, a default value is setting on by this class.
	 */
	public static void load()
	{
		if (Server.serverMode == Server.MODE_GAMESERVER)
		{
			_log.info("Loading flood protectors.");
			FLOOD_PROTECTOR_ROLL_DICE = new FloodProtectorConfig("RollDiceFloodProtector");
			FLOOD_PROTECTOR_HERO_VOICE = new FloodProtectorConfig("HeroVoiceFloodProtector");
			FLOOD_PROTECTOR_SUBCLASS = new FloodProtectorConfig("SubclassFloodProtector");
			FLOOD_PROTECTOR_DROP_ITEM = new FloodProtectorConfig("DropItemFloodProtector");
			FLOOD_PROTECTOR_SERVER_BYPASS = new FloodProtectorConfig("ServerBypassFloodProtector");
			FLOOD_PROTECTOR_MULTISELL = new FloodProtectorConfig("MultiSellFloodProtector");
			FLOOD_PROTECTOR_MANUFACTURE = new FloodProtectorConfig("ManufactureFloodProtector");
			FLOOD_PROTECTOR_MANOR = new FloodProtectorConfig("ManorFloodProtector");
			FLOOD_PROTECTOR_SENDMAIL = new FloodProtectorConfig("SendMailFloodProtector");
			FLOOD_PROTECTOR_CHARACTER_SELECT = new FloodProtectorConfig("CharacterSelectFloodProtector");
			FLOOD_PROTECTOR_MACRO = new FloodProtectorConfig("MacroFloodProtector");
			
			_log.info("Loading gameserver configuration files.");
			
			// Version
			ExProperties Version = load(VERSION);
			VER = Version.getProperty("version", "fake?");
			BUILD = Version.getProperty("builddate", "");
			
			// Enchant Protections
			ExProperties EnchantProtections = load(ENCHANTPROTECTIONS_FILE);
			CAN_LOGOUT_IN_ENCHANT = EnchantProtections.getProperty("CanLogoutInEnchant", false);
			CAN_ENCHANT_IN_CRAFT_MODE = EnchantProtections.getProperty("CanEnchantInCraftMode", false);
			CAN_ENCHANT_IN_TELEPORTING = EnchantProtections.getProperty("CanEnchantInTeleporting", false);
			CAN_ENCHANT_IN_DEAD = EnchantProtections.getProperty("CanEnchantInDead", false);
			CAN_ENCHANT_IN_SLEEP = EnchantProtections.getProperty("CanEnchantInSleep", false);
			CAN_ENCHANT_IN_PARALYZED = EnchantProtections.getProperty("CanEnchantInParalyzed", false);
			CAN_ENCHANT_IN_CASTING = EnchantProtections.getProperty("CanEnchantInCasting", false);
			CAN_ENCHANT_IN_MOVING = EnchantProtections.getProperty("CanEnchantInMoving", false);
			CAN_ENCHANT_IN_TRADE = EnchantProtections.getProperty("CanEnchantInTrade", false);
			CAN_ENCHANT_IN_STUNNED = EnchantProtections.getProperty("CanEnchantInStunned", false);
			CAN_ENCHANT_IN_MOUNTED = EnchantProtections.getProperty("CanEnchantInMounted", false);
			CAN_ENCHANT_IN_FAKEDEATH = EnchantProtections.getProperty("CanEnchantInFakeDeath", false);
			CAN_ENCHANT_IN_JAIL = EnchantProtections.getProperty("CanEnchantInJail", false);
			CAN_ENCHANT_IN_CURSED_WEAPON_EQUIP = EnchantProtections.getProperty("CanEnchantInCoursedWeaponEquip", false);
			CAN_ENCHANT_IN_FLYING = EnchantProtections.getProperty("CanEnchantInFlying", false);
			CAN_ENCHANT_IN_FISHING = EnchantProtections.getProperty("CanEnchantInFishing", false);
			CAN_ENCHANT_IN_SITTING = EnchantProtections.getProperty("CanEnchantInSitting", false);
			
			ENABLE_ANTI_OVER_ENCHANT_PROTECTION = EnchantProtections.getProperty("EnableAntiOverEnchantProtection", false);
			ENCHANT_MAX_WEAPON_PROTECTION = EnchantProtections.getProperty("MaxWeaponProtection", 25);
			ENCHANT_MAX_ARMOR_PROTECTION = EnchantProtections.getProperty("MaxArmorProtection", 25);
			ENCHANT_MAX_JEWELRY_PROTECTION = EnchantProtections.getProperty("MaxJewelProtection", 25);
			
			// PvP Protections
			ExProperties PvpProtections = load(PVPPROTECTIONS_FILE);
			ANTI_FARM_ENABLED = PvpProtections.getProperty("AntiFarmEnabled", false);
			ANTI_FARM_CLAN_ALLY_ENABLED = PvpProtections.getProperty("AntiFarmClanAlly", false);
			ANTI_FARM_LVL_DIFF_ENABLED = PvpProtections.getProperty("AntiFarmLvlDiff", false);
			ANTI_FARM_MAX_LVL_DIFF = PvpProtections.getProperty("AntiFarmMaxLvlDiff", 40);
			ANTI_FARM_PDEF_DIFF_ENABLED = PvpProtections.getProperty("AntiFarmPdefDiff", false);
			ANTI_FARM_MAX_PDEF_DIFF = PvpProtections.getProperty("AntiFarmMaxPdefDiff", 300);
			ANTI_FARM_PATK_DIFF_ENABLED = PvpProtections.getProperty("AntiFarmPatkDiff", false);
			ANTI_FARM_MAX_PATK_DIFF = PvpProtections.getProperty("AntiFarmMaxPatkDiff", 300);
			ANTI_FARM_PARTY_ENABLED = PvpProtections.getProperty("AntiFarmParty", false);
			ANTI_FARM_IP_ENABLED = PvpProtections.getProperty("AntiFarmIP", false);
			
			// Protections
			ExProperties Protections = load(PROTECTIONS_FILE);
			LOG_CHAT = Protections.getProperty("LogChat", false);
			LOG_ITEMS = Protections.getProperty("LogItems", false);
			
			DEFAULT_PUNISH = Protections.getProperty("DefaultPunish", 2);
			DEFAULT_PUNISH_PARAM = Protections.getProperty("DefaultPunishParam", 0);
			
			ANTIBOT_ENABLE = Protections.getProperty("AntiBotEnable", true);
			ANTIBOT_TIME_JAIL = Protections.getProperty("AntiBotTimeJail", 1);
			ANTIBOT_TIME_VOTE = Protections.getProperty("AntiBotTimeVote", 30);
			ANTIBOT_KILL_MOBS = Protections.getProperty("AntiBotKillMobs", 1);
			ANTIBOT_MIN_LEVEL = Protections.getProperty("AntiBotMinLevel", 1);
			
			CHECK_SKILLS_ON_ENTER = Protections.getProperty("CheckSkillsOnEnter", false);
			CHECK_HERO_SKILLS = Protections.getProperty("CheckHeroSkills", true);
			CHECK_NOBLE_SKILLS = Protections.getProperty("CheckNobleSkills", true);
			NON_CHECK_SKILLS = Protections.getProperty("NonCheckSkills", "10000");
			LIST_NON_CHECK_SKILLS = new ArrayList<>();
			for (String id : NON_CHECK_SKILLS.split(","))
			{
				LIST_NON_CHECK_SKILLS.add(Integer.parseInt(id));
			}
			
			ENABLE_NEWBIE_PROTECTION_SYSTEM = Protections.getProperty("EnableNewbieProtectionSystem", false);
			PROTECTION_DIFFERENCE_LEVEL = Protections.getProperty("ProtectionDifferenceLevel", 60);
			PROTECTION_SKILL_ID = Protections.getProperty("ProtectionSkilId", 1069);
			PROTECTION_SKILL_LVL = Protections.getProperty("ProtectionSkilLvl", 42);
			PROTECTION_MESSAGE = Protections.getProperty("ProtectionMessage", "You can not do PVPs with characters with " + PROTECTION_DIFFERENCE_LEVEL + " levels difference of you.");
			
			PLAYER_SPAWN_PROTECTION = Protections.getProperty("PlayerSpawnProtection", 0);
			
			LASTIP_SYSTEM_ENABLED = Protections.getProperty("EnableLastIpSystem", false);
			
			ALLOW_LAST_VISIT_INFORMATION = Protections.getProperty("AllowLastVisitInformation", false);
			
			L2WALKER_PROTECTION = Protections.getProperty("L2WalkerProtection", false);
			
			GAMEGUARD_ENFORCE = Protections.getProperty("GameGuardEnforce", false);
			
			FORBIDDEN_NAMES = Protections.getProperty("ForbiddenNames", "").split(",");
			
			DEADLOCK_DETECTOR = Protections.getProperty("DeadLockDetector", false);
			DEADLOCK_CHECK_INTERVAL = Protections.getProperty("DeadLockCheckInterval", 20);
			RESTART_ON_DEADLOCK = Protections.getProperty("RestartOnDeadlock", false);
			
			// Clans settings
			ExProperties clans = load(CLANS_FILE);
			ALT_CLAN_JOIN_DAYS = clans.getProperty("DaysBeforeJoinAClan", 5);
			ALT_CLAN_CREATE_DAYS = clans.getProperty("DaysBeforeCreateAClan", 10);
			ALT_MAX_NUM_OF_CLANS_IN_ALLY = clans.getProperty("AltMaxNumOfClansInAlly", 3);
			ALT_CLAN_MEMBERS_FOR_WAR = clans.getProperty("AltClanMembersForWar", 15);
			ALT_CLAN_WAR_PENALTY_WHEN_ENDED = clans.getProperty("AltClanWarPenaltyWhenEnded", 5);
			ALT_CLAN_DISSOLVE_DAYS = clans.getProperty("DaysToPassToDissolveAClan", 7);
			ALT_ALLY_JOIN_DAYS_WHEN_LEAVED = clans.getProperty("DaysBeforeJoinAllyWhenLeaved", 1);
			ALT_ALLY_JOIN_DAYS_WHEN_DISMISSED = clans.getProperty("DaysBeforeJoinAllyWhenDismissed", 1);
			ALT_ACCEPT_CLAN_DAYS_WHEN_DISMISSED = clans.getProperty("DaysBeforeAcceptNewClanWhenDismissed", 1);
			ALT_CREATE_ALLY_DAYS_WHEN_DISSOLVED = clans.getProperty("DaysBeforeCreateNewAllyWhenDissolved", 10);
			ALT_MEMBERS_CAN_WITHDRAW_FROM_CLANWH = clans.getProperty("AltMembersCanWithdrawFromClanWH", false);
			REMOVE_CASTLE_CIRCLETS = clans.getProperty("RemoveCastleCirclets", true);
			
			ALT_MANOR_REFRESH_TIME = clans.getProperty("AltManorRefreshTime", 20);
			ALT_MANOR_REFRESH_MIN = clans.getProperty("AltManorRefreshMin", 0);
			ALT_MANOR_APPROVE_TIME = clans.getProperty("AltManorApproveTime", 6);
			ALT_MANOR_APPROVE_MIN = clans.getProperty("AltManorApproveMin", 0);
			ALT_MANOR_MAINTENANCE_PERIOD = clans.getProperty("AltManorMaintenancePeriod", 360000);
			ALT_MANOR_SAVE_ALL_ACTIONS = clans.getProperty("AltManorSaveAllActions", false);
			ALT_MANOR_SAVE_PERIOD_RATE = clans.getProperty("AltManorSavePeriodRate", 2);
			
			CH_TELE_FEE_RATIO = clans.getProperty("ClanHallTeleportFunctionFeeRatio", 86400000);
			CH_TELE1_FEE = clans.getProperty("ClanHallTeleportFunctionFeeLvl1", 7000);
			CH_TELE2_FEE = clans.getProperty("ClanHallTeleportFunctionFeeLvl2", 14000);
			CH_SUPPORT_FEE_RATIO = clans.getProperty("ClanHallSupportFunctionFeeRatio", 86400000);
			CH_SUPPORT1_FEE = clans.getProperty("ClanHallSupportFeeLvl1", 17500);
			CH_SUPPORT2_FEE = clans.getProperty("ClanHallSupportFeeLvl2", 35000);
			CH_SUPPORT3_FEE = clans.getProperty("ClanHallSupportFeeLvl3", 49000);
			CH_SUPPORT4_FEE = clans.getProperty("ClanHallSupportFeeLvl4", 77000);
			CH_SUPPORT5_FEE = clans.getProperty("ClanHallSupportFeeLvl5", 147000);
			CH_SUPPORT6_FEE = clans.getProperty("ClanHallSupportFeeLvl6", 252000);
			CH_SUPPORT7_FEE = clans.getProperty("ClanHallSupportFeeLvl7", 259000);
			CH_SUPPORT8_FEE = clans.getProperty("ClanHallSupportFeeLvl8", 364000);
			CH_MPREG_FEE_RATIO = clans.getProperty("ClanHallMpRegenerationFunctionFeeRatio", 86400000);
			CH_MPREG1_FEE = clans.getProperty("ClanHallMpRegenerationFeeLvl1", 14000);
			CH_MPREG2_FEE = clans.getProperty("ClanHallMpRegenerationFeeLvl2", 26250);
			CH_MPREG3_FEE = clans.getProperty("ClanHallMpRegenerationFeeLvl3", 45500);
			CH_MPREG4_FEE = clans.getProperty("ClanHallMpRegenerationFeeLvl4", 96250);
			CH_MPREG5_FEE = clans.getProperty("ClanHallMpRegenerationFeeLvl5", 140000);
			CH_HPREG_FEE_RATIO = clans.getProperty("ClanHallHpRegenerationFunctionFeeRatio", 86400000);
			CH_HPREG1_FEE = clans.getProperty("ClanHallHpRegenerationFeeLvl1", 4900);
			CH_HPREG2_FEE = clans.getProperty("ClanHallHpRegenerationFeeLvl2", 5600);
			CH_HPREG3_FEE = clans.getProperty("ClanHallHpRegenerationFeeLvl3", 7000);
			CH_HPREG4_FEE = clans.getProperty("ClanHallHpRegenerationFeeLvl4", 8166);
			CH_HPREG5_FEE = clans.getProperty("ClanHallHpRegenerationFeeLvl5", 10500);
			CH_HPREG6_FEE = clans.getProperty("ClanHallHpRegenerationFeeLvl6", 12250);
			CH_HPREG7_FEE = clans.getProperty("ClanHallHpRegenerationFeeLvl7", 14000);
			CH_HPREG8_FEE = clans.getProperty("ClanHallHpRegenerationFeeLvl8", 15750);
			CH_HPREG9_FEE = clans.getProperty("ClanHallHpRegenerationFeeLvl9", 17500);
			CH_HPREG10_FEE = clans.getProperty("ClanHallHpRegenerationFeeLvl10", 22750);
			CH_HPREG11_FEE = clans.getProperty("ClanHallHpRegenerationFeeLvl11", 26250);
			CH_HPREG12_FEE = clans.getProperty("ClanHallHpRegenerationFeeLvl12", 29750);
			CH_HPREG13_FEE = clans.getProperty("ClanHallHpRegenerationFeeLvl13", 36166);
			CH_EXPREG_FEE_RATIO = clans.getProperty("ClanHallExpRegenerationFunctionFeeRatio", 86400000);
			CH_EXPREG1_FEE = clans.getProperty("ClanHallExpRegenerationFeeLvl1", 21000);
			CH_EXPREG2_FEE = clans.getProperty("ClanHallExpRegenerationFeeLvl2", 42000);
			CH_EXPREG3_FEE = clans.getProperty("ClanHallExpRegenerationFeeLvl3", 63000);
			CH_EXPREG4_FEE = clans.getProperty("ClanHallExpRegenerationFeeLvl4", 105000);
			CH_EXPREG5_FEE = clans.getProperty("ClanHallExpRegenerationFeeLvl5", 147000);
			CH_EXPREG6_FEE = clans.getProperty("ClanHallExpRegenerationFeeLvl6", 163331);
			CH_EXPREG7_FEE = clans.getProperty("ClanHallExpRegenerationFeeLvl7", 210000);
			CH_ITEM_FEE_RATIO = clans.getProperty("ClanHallItemCreationFunctionFeeRatio", 86400000);
			CH_ITEM1_FEE = clans.getProperty("ClanHallItemCreationFunctionFeeLvl1", 210000);
			CH_ITEM2_FEE = clans.getProperty("ClanHallItemCreationFunctionFeeLvl2", 490000);
			CH_ITEM3_FEE = clans.getProperty("ClanHallItemCreationFunctionFeeLvl3", 980000);
			CH_CURTAIN_FEE_RATIO = clans.getProperty("ClanHallCurtainFunctionFeeRatio", 86400000);
			CH_CURTAIN1_FEE = clans.getProperty("ClanHallCurtainFunctionFeeLvl1", 2002);
			CH_CURTAIN2_FEE = clans.getProperty("ClanHallCurtainFunctionFeeLvl2", 2625);
			CH_FRONT_FEE_RATIO = clans.getProperty("ClanHallFrontPlatformFunctionFeeRatio", 86400000);
			CH_FRONT1_FEE = clans.getProperty("ClanHallFrontPlatformFunctionFeeLvl1", 3031);
			CH_FRONT2_FEE = clans.getProperty("ClanHallFrontPlatformFunctionFeeLvl2", 9331);
			
			// Balance Folder
			// Restrictions
			ExProperties Restrictions = load(RESTRICTIONS_FILE);
			ALLOW_HEAVY_USE_LIGHT = Restrictions.getProperty("AllowHeavyUseLight", false);
			NOTALLOWCLASSE = Restrictions.getProperty("NotAllowedUseLight", "");
			NOTALLOWEDUSELIGHT = new ArrayList<>();
			for (String classId : NOTALLOWCLASSE.split(","))
			{
				NOTALLOWEDUSELIGHT.add(Integer.parseInt(classId));
			}
			ALLOW_LIGHT_USE_HEAVY = Restrictions.getProperty("AllowLightUseHeavy", false);
			NOTALLOWCLASS = Restrictions.getProperty("NotAllowedUseHeavy", "");
			NOTALLOWEDUSEHEAVY = new ArrayList<>();
			for (String classId : NOTALLOWCLASS.split(","))
			{
				NOTALLOWEDUSEHEAVY.add(Integer.parseInt(classId));
			}
			
			ALT_DISABLE_BOW_CLASSES = Restrictions.getProperty("AltDisableBow", false);
			DISABLE_BOW_CLASSES_STRING = Restrictions.getProperty("DisableBowForClasses", "");
			DISABLE_BOW_CLASSES = new ArrayList<>();
			for (String class_id : DISABLE_BOW_CLASSES_STRING.split(","))
			{
				if (!class_id.equals(""))
					DISABLE_BOW_CLASSES.add(Integer.parseInt(class_id));
			}
			ALT_DISABLE_DAGGER_CLASSES = Restrictions.getProperty("AltDisableDagger", false);
			DISABLE_DAGGER_CLASSES_STRING = Restrictions.getProperty("DisableDaggerForClasses", "");
			DISABLE_DAGGER_CLASSES = new ArrayList<>();
			for (String class_id : DISABLE_DAGGER_CLASSES_STRING.split(","))
			{
				if (!class_id.equals(""))
					DISABLE_DAGGER_CLASSES.add(Integer.parseInt(class_id));
			}
			ALT_DISABLE_SWORD_CLASSES = Restrictions.getProperty("AltDisableSword", false);
			DISABLE_SWORD_CLASSES_STRING = Restrictions.getProperty("DisableSwordForClasses", "");
			DISABLE_SWORD_CLASSES = new ArrayList<>();
			for (String class_id : DISABLE_SWORD_CLASSES_STRING.split(","))
			{
				if (!class_id.equals(""))
					DISABLE_SWORD_CLASSES.add(Integer.parseInt(class_id));
			}
			ALT_DISABLE_BLUNT_CLASSES = Restrictions.getProperty("AltDisableBlunt", false);
			DISABLE_BLUNT_CLASSES_STRING = Restrictions.getProperty("DisableBluntForClasses", "");
			DISABLE_BLUNT_CLASSES = new ArrayList<>();
			for (String class_id : DISABLE_BLUNT_CLASSES_STRING.split(","))
			{
				if (!class_id.equals(""))
					DISABLE_BLUNT_CLASSES.add(Integer.parseInt(class_id));
			}
			ALT_DISABLE_DUAL_CLASSES = Restrictions.getProperty("AltDisableDual", false);
			DISABLE_DUAL_CLASSES_STRING = Restrictions.getProperty("DisableDualForClasses", "");
			DISABLE_DUAL_CLASSES = new ArrayList<>();
			for (String class_id : DISABLE_DUAL_CLASSES_STRING.split(","))
			{
				if (!class_id.equals(""))
					DISABLE_DUAL_CLASSES.add(Integer.parseInt(class_id));
			}
			ALT_DISABLE_POLE_CLASSES = Restrictions.getProperty("AltDisablePolle", false);
			DISABLE_POLE_CLASSES_STRING = Restrictions.getProperty("DisablePolleForClasses", "");
			DISABLE_POLE_CLASSES = new ArrayList<>();
			for (String class_id : DISABLE_POLE_CLASSES_STRING.split(","))
			{
				if (!class_id.equals(""))
					DISABLE_POLE_CLASSES.add(Integer.parseInt(class_id));
			}
			ALT_DISABLE_BIGSWORD_CLASSES = Restrictions.getProperty("AltDisableBigSword", false);
			DISABLE_BIGSWORD_CLASSES_STRING = Restrictions.getProperty("DisableBigSwordForClasses", "");
			DISABLE_BIGSWORD_CLASSES = new ArrayList<>();
			for (String class_id : DISABLE_BIGSWORD_CLASSES_STRING.split(","))
			{
				if (!class_id.equals(""))
					DISABLE_BIGSWORD_CLASSES.add(Integer.parseInt(class_id));
			}
			
			// Main Folder
			// Pvp
			ExProperties Pvp = load(PVP_FILE);
			ENABLE_PVP_ENCHANTMENT = Pvp.getProperty("EnablePvpEnchantSystem", false);
			PVP_COUNT_TILL_ENCHANTMENT = Pvp.getProperty("PvpCountTillEnchanment", 20);
			
			ADD_EXP_SP = Pvp.getProperty("AddExpSpAtPvp", false);
			ADD_EXP = Pvp.getProperty("AddExpAtPvp", 0);
			ADD_SP = Pvp.getProperty("AddSpAtPvp", 0);
			
			ANNOUNCE_PK_PVP = Pvp.getProperty("AnnouncePkPvP", false);
			ANNOUNCE_PK_PVP_NORMAL_MESSAGE = Pvp.getProperty("AnnouncePkPvPNormalMessage", true);
			ANNOUNCE_PK_MSG = Pvp.getProperty("AnnouncePkMsg", "$killer has slaughtered $target");
			ANNOUNCE_PVP_MSG = Pvp.getProperty("AnnouncePvpMsg", "$killer has defeated $target");
			
			ALLOW_PVP_REWARD = Pvp.getProperty("AllowPvpRewardSystem", false);
			PVP_REWARD_ITEM = Pvp.getProperty("PvpRewardItem", 57);
			PVP_REWARD_COUNT = Pvp.getProperty("PvpRewardAmount", 1);
			ALLOW_PK_REWARD = Pvp.getProperty("AllowPkRewardSystem", false);
			PK_REWARD_ITEM = Pvp.getProperty("PkRewardItem", 57);
			PK_REWARD_COUNT = Pvp.getProperty("PkRewardAmount", 1);
			
			PVP_COLOR_SYSTEM_ENABLED = Pvp.getProperty("EnablePvPColorSystem", false);
			PVP_AMOUNT1 = Pvp.getProperty("PvpAmount1", 500);
			PVP_AMOUNT2 = Pvp.getProperty("PvpAmount2", 1000);
			PVP_AMOUNT3 = Pvp.getProperty("PvpAmount3", 1500);
			PVP_AMOUNT4 = Pvp.getProperty("PvpAmount4", 2500);
			PVP_AMOUNT5 = Pvp.getProperty("PvpAmount5", 5000);
			NAME_COLOR_FOR_PVP_AMOUNT1 = Integer.decode("0x" + Pvp.getProperty("ColorForAmount1", "00FF00"));
			NAME_COLOR_FOR_PVP_AMOUNT2 = Integer.decode("0x" + Pvp.getProperty("ColorForAmount2", "00FF00"));
			NAME_COLOR_FOR_PVP_AMOUNT3 = Integer.decode("0x" + Pvp.getProperty("ColorForAmount3", "00FF00"));
			NAME_COLOR_FOR_PVP_AMOUNT4 = Integer.decode("0x" + Pvp.getProperty("ColorForAmount4", "00FF00"));
			NAME_COLOR_FOR_PVP_AMOUNT5 = Integer.decode("0x" + Pvp.getProperty("ColorForAmount5", "00FF00"));
			PK_COLOR_SYSTEM_ENABLED = Pvp.getProperty("EnablePkColorSystem", false);
			PK_AMOUNT1 = Pvp.getProperty("PkAmount1", 500);
			PK_AMOUNT2 = Pvp.getProperty("PkAmount2", 1000);
			PK_AMOUNT3 = Pvp.getProperty("PkAmount3", 1500);
			PK_AMOUNT4 = Pvp.getProperty("PkAmount4", 2500);
			PK_AMOUNT5 = Pvp.getProperty("PkAmount5", 5000);
			TITLE_COLOR_FOR_PK_AMOUNT1 = Integer.decode("0x" + Pvp.getProperty("TitleForAmount1", "00FF00"));
			TITLE_COLOR_FOR_PK_AMOUNT2 = Integer.decode("0x" + Pvp.getProperty("TitleForAmount2", "00FF00"));
			TITLE_COLOR_FOR_PK_AMOUNT3 = Integer.decode("0x" + Pvp.getProperty("TitleForAmount3", "00FF00"));
			TITLE_COLOR_FOR_PK_AMOUNT4 = Integer.decode("0x" + Pvp.getProperty("TitleForAmount4", "00FF00"));
			TITLE_COLOR_FOR_PK_AMOUNT5 = Integer.decode("0x" + Pvp.getProperty("TitleForAmount5", "00FF00"));
			
			ZONE_TOWN = Pvp.getProperty("ZoneTown", 0);
			JAIL_IS_PVP = Pvp.getProperty("JailIsPvp", false);
			PVP_NORMAL_TIME = Pvp.getProperty("PvPVsNormalTime", 15000);
			PVP_PVP_TIME = Pvp.getProperty("PvPVsPvPTime", 30000);
			KARMA_PLAYER_CAN_BE_KILLED_IN_PZ = Pvp.getProperty("KarmaPlayerCanBeKilledInPeaceZone", false);
			KARMA_PLAYER_CAN_SHOP = Pvp.getProperty("KarmaPlayerCanShop", true);
			KARMA_PLAYER_CAN_USE_GK = Pvp.getProperty("KarmaPlayerCanUseGK", false);
			KARMA_PLAYER_CAN_TELEPORT = Pvp.getProperty("KarmaPlayerCanTeleport", true);
			KARMA_PLAYER_CAN_TRADE = Pvp.getProperty("KarmaPlayerCanTrade", true);
			KARMA_PLAYER_CAN_USE_WH = Pvp.getProperty("KarmaPlayerCanUseWareHouse", true);
			FLAGED_PLAYER_CAN_USE_GK = Pvp.getProperty("FlagedPlayerCanUseGK", true);
			KARMA_DROP_GM = Pvp.getProperty("CanGMDropEquipment", false);
			KARMA_AWARD_PK_KILL = Pvp.getProperty("AwardPKKillPVPPoint", true);
			KARMA_PK_LIMIT = Pvp.getProperty("MinimumPKRequiredToDrop", 5);
			KARMA_NONDROPPABLE_PET_ITEMS = Pvp.getProperty("ListOfPetItems", "2375,3500,3501,3502,4422,4423,4424,4425,6648,6649,6650");
			KARMA_NONDROPPABLE_ITEMS = Pvp.getProperty("ListOfNonDroppableItemsForPK", "1147,425,1146,461,10,2368,7,6,2370,2369");
			
			String[] array = KARMA_NONDROPPABLE_PET_ITEMS.split(",");
			KARMA_LIST_NONDROPPABLE_PET_ITEMS = new int[array.length];
			
			for (int i = 0; i < array.length; i++)
				KARMA_LIST_NONDROPPABLE_PET_ITEMS[i] = Integer.parseInt(array[i]);
			
			array = KARMA_NONDROPPABLE_ITEMS.split(",");
			KARMA_LIST_NONDROPPABLE_ITEMS = new int[array.length];
			
			for (int i = 0; i < array.length; i++)
				KARMA_LIST_NONDROPPABLE_ITEMS[i] = Integer.parseInt(array[i]);
			
			Arrays.sort(KARMA_LIST_NONDROPPABLE_PET_ITEMS);
			Arrays.sort(KARMA_LIST_NONDROPPABLE_ITEMS);
			
			// Geodata
			ExProperties geoengine = load(GEOENGINE_FILE);
			GEODATA = geoengine.getProperty("GeoData", 0);
			GEODATA_PATH = geoengine.getProperty("GeoDataPath", "./data/geodata/");
			GEODATA_FORMAT = Enum.valueOf(GeoFormat.class, geoengine.getProperty("GeoDataFormat", GeoFormat.L2J.toString()));
			COORD_SYNCHRONIZE = geoengine.getProperty("CoordSynchronize", -1);
			
			PART_OF_CHARACTER_HEIGHT = geoengine.getProperty("PartOfCharacterHeight", 75);
			MAX_OBSTACLE_HEIGHT = geoengine.getProperty("MaxObstacleHeight", 32);
			
			PATHFIND_BUFFERS = geoengine.getProperty("PathFindBuffers", "100x6;128x6;192x6;256x4;320x4;384x4;500x2");
			BASE_WEIGHT = geoengine.getProperty("BaseWeight", 10);
			DIAGONAL_WEIGHT = geoengine.getProperty("DiagonalWeight", 14);
			OBSTACLE_MULTIPLIER = geoengine.getProperty("ObstacleMultiplier", 10);
			HEURISTIC_WEIGHT = geoengine.getProperty("HeuristicWeight", 20);
			MAX_ITERATIONS = geoengine.getProperty("MaxIterations", 3500);
			DEBUG_PATH = geoengine.getProperty("DebugPath", false);
			
			// Rates
			ExProperties Rates = load(RATES_FILE);
			RATE_XP = Rates.getProperty("RateXp", 1.);
			RATE_SP = Rates.getProperty("RateSp", 1.);
			SATURDAY_RATE_XP = Rates.getProperty("SaturDayXp", 2.);
			SATURDAY_RATE_SP = Rates.getProperty("SaturDaySp", 2.);
			SUNDAY_RATE_XP = Rates.getProperty("SunDayXp", 2.);
			SUNDAY_RATE_SP = Rates.getProperty("SunDaySp", 2.);
			RATE_PARTY_XP = Rates.getProperty("RatePartyXp", 1.);
			RATE_PARTY_SP = Rates.getProperty("RatePartySp", 1.);
			RATE_DROP_ADENA = Rates.getProperty("RateDropAdena", 1.);
			RATE_CONSUMABLE_COST = Rates.getProperty("RateConsumableCost", 1.);
			RATE_DROP_ITEMS = Rates.getProperty("RateDropItems", 1.);
			RATE_DROP_ITEMS_BY_RAID = Rates.getProperty("RateRaidDropItems", 1.);
			RATE_DROP_SPOIL = Rates.getProperty("RateDropSpoil", 1.);
			RATE_DROP_MANOR = Rates.getProperty("RateDropManor", 1);
			RATE_QUEST_DROP = Rates.getProperty("RateQuestDrop", 1.);
			RATE_QUEST_REWARD = Rates.getProperty("RateQuestReward", 1.);
			RATE_QUEST_REWARD_XP = Rates.getProperty("RateQuestRewardXP", 1.);
			RATE_QUEST_REWARD_SP = Rates.getProperty("RateQuestRewardSP", 1.);
			RATE_QUEST_REWARD_ADENA = Rates.getProperty("RateQuestRewardAdena", 1.);
			RATE_KARMA_EXP_LOST = Rates.getProperty("RateKarmaExpLost", 1.);
			RATE_SIEGE_GUARDS_PRICE = Rates.getProperty("RateSiegeGuardsPrice", 1.);
			RATE_DROP_COMMON_HERBS = Rates.getProperty("RateCommonHerbs", 1.);
			RATE_DROP_HP_HERBS = Rates.getProperty("RateHpHerbs", 1.);
			RATE_DROP_MP_HERBS = Rates.getProperty("RateMpHerbs", 1.);
			RATE_DROP_SPECIAL_HERBS = Rates.getProperty("RateSpecialHerbs", 1.);
			PLAYER_DROP_LIMIT = Rates.getProperty("PlayerDropLimit", 3);
			PLAYER_RATE_DROP = Rates.getProperty("PlayerRateDrop", 5);
			PLAYER_RATE_DROP_ITEM = Rates.getProperty("PlayerRateDropItem", 70);
			PLAYER_RATE_DROP_EQUIP = Rates.getProperty("PlayerRateDropEquip", 25);
			PLAYER_RATE_DROP_EQUIP_WEAPON = Rates.getProperty("PlayerRateDropEquipWeapon", 5);
			PET_XP_RATE = Rates.getProperty("PetXpRate", 1.);
			PET_FOOD_RATE = Rates.getProperty("PetFoodRate", 1);
			SINEATER_XP_RATE = Rates.getProperty("SinEaterXpRate", 1.);
			KARMA_DROP_LIMIT = Rates.getProperty("KarmaDropLimit", 10);
			KARMA_RATE_DROP = Rates.getProperty("KarmaRateDrop", 70);
			KARMA_RATE_DROP_ITEM = Rates.getProperty("KarmaRateDropItem", 50);
			KARMA_RATE_DROP_EQUIP = Rates.getProperty("KarmaRateDropEquip", 40);
			KARMA_RATE_DROP_EQUIP_WEAPON = Rates.getProperty("KarmaRateDropEquipWeapon", 10);
			
			PARTY_XP_CUTOFF_METHOD = Rates.getProperty("PartyXpCutoffMethod", "level");
			PARTY_XP_CUTOFF_PERCENT = Rates.getProperty("PartyXpCutoffPercent", 3.);
			PARTY_XP_CUTOFF_LEVEL = Rates.getProperty("PartyXpCutoffLevel", 20);
			ALT_PARTY_RANGE = Rates.getProperty("AltPartyRange", 1600);
			ALT_PARTY_RANGE2 = Rates.getProperty("AltPartyRange2", 1400);
			ALT_LEAVE_PARTY_LEADER = Rates.getProperty("AltLeavePartyLeader", false);
			
			ENCHANT_CHANCE_WEAPON_MAGIC = Rates.getProperty("EnchantChanceMagicWeapon", 0.4);
			ENCHANT_CHANCE_WEAPON_MAGIC_15PLUS = Rates.getProperty("EnchantChanceMagicWeapon15Plus", 0.2);
			ENCHANT_CHANCE_WEAPON_NONMAGIC = Rates.getProperty("EnchantChanceNonMagicWeapon", 0.7);
			ENCHANT_CHANCE_WEAPON_NONMAGIC_15PLUS = Rates.getProperty("EnchantChanceNonMagicWeapon15Plus", 0.35);
			ENCHANT_CHANCE_ARMOR = Rates.getProperty("EnchantChanceArmor", 0.66);
			ENCHANT_MAX_WEAPON = Rates.getProperty("EnchantMaxWeapon", 0);
			ENCHANT_MAX_ARMOR = Rates.getProperty("EnchantMaxArmor", 0);
			ENCHANT_SAFE_MAX = Rates.getProperty("EnchantSafeMax", 3);
			ENCHANT_SAFE_MAX_FULL = Rates.getProperty("EnchantSafeMaxFull", 4);
			
			BREAK_ENCHANT = Rates.getProperty("BreakEnchant", 0);
			
			AUGMENTATION_NG_SKILL_CHANCE = Rates.getProperty("AugmentationNGSkillChance", 15);
			AUGMENTATION_NG_GLOW_CHANCE = Rates.getProperty("AugmentationNGGlowChance", 0);
			AUGMENTATION_MID_SKILL_CHANCE = Rates.getProperty("AugmentationMidSkillChance", 30);
			AUGMENTATION_MID_GLOW_CHANCE = Rates.getProperty("AugmentationMidGlowChance", 40);
			AUGMENTATION_HIGH_SKILL_CHANCE = Rates.getProperty("AugmentationHighSkillChance", 45);
			AUGMENTATION_HIGH_GLOW_CHANCE = Rates.getProperty("AugmentationHighGlowChance", 70);
			AUGMENTATION_TOP_SKILL_CHANCE = Rates.getProperty("AugmentationTopSkillChance", 60);
			AUGMENTATION_TOP_GLOW_CHANCE = Rates.getProperty("AugmentationTopGlowChance", 100);
			AUGMENTATION_BASESTAT_CHANCE = Rates.getProperty("AugmentationBaseStatChance", 1);
			
			// Admin Folder
			ExProperties AdminMods = load(ADMINMODS_FILE);
			ENABLE_CLAN_SYSTEM = AdminMods.getProperty("EnableClanSystem", false);
			if (ENABLE_CLAN_SYSTEM)
			{
				String AioSkillsSplit[] = AdminMods.getProperty("ClanSkills", "").split(";");
				CLAN_SKILLS = new HashMap<>(AioSkillsSplit.length);
				String arr[] = AioSkillsSplit;
				int len = arr.length;
				for (int i = 0; i < len; i++)
				{
					String skill = arr[i];
					String skillSplit[] = skill.split(",");
					if (skillSplit.length != 2)
					{
						System.out.println((new StringBuilder()).append("[Clan System]: invalid config property in Admin/AdminMods.properties -> ClanSkills \"").append(skill).append("\"").toString());
						continue;
					}
					try
					{
						CLAN_SKILLS.put(Integer.valueOf(Integer.parseInt(skillSplit[0])), Integer.valueOf(Integer.parseInt(skillSplit[1])));
						continue;
					}
					catch (NumberFormatException nfe)
					{
					}
					if (!skill.equals(""))
						System.out.println((new StringBuilder()).append("[Clan System]: invalid config property in Admin/AdminMods.properties -> ClanSkills \"").append(skillSplit[0]).append("\"").append(skillSplit[1]).toString());
				}
				
			}
			CLAN_LEVEL = Byte.parseByte(AdminMods.getProperty("ClanSetLevel", "8"));
			REPUTATION_QUANTITY = AdminMods.getProperty("ReputationScore", 10000);
			
			ExProperties Admin = load(ADMIN_FILE);
			EVERYBODY_HAS_ADMIN_RIGHTS = Admin.getProperty("EverybodyHasAdminRights", false);
			GMAUDIT = Admin.getProperty("GMAudit", false);
			MASTERACCESS_LEVEL = Admin.getProperty("MasterAccessLevel", 127);
			MASTERACCESS_NAME_COLOR = Integer.decode(StringUtil.concat("0x", Admin.getProperty("MasterNameColor", "00FF00")));
			MASTERACCESS_TITLE_COLOR = Integer.decode(StringUtil.concat("0x", Admin.getProperty("MasterTitleColor", "00FF00")));
			GM_HERO_AURA = Admin.getProperty("GMHeroAura", false);
			GM_STARTUP_INVULNERABLE = Admin.getProperty("GMStartupInvulnerable", true);
			GM_STARTUP_INVISIBLE = Admin.getProperty("GMStartupInvisible", true);
			GM_STARTUP_SILENCE = Admin.getProperty("GMStartupSilence", true);
			GM_STARTUP_AUTO_LIST = Admin.getProperty("GMStartupAutoList", true);
			
			PETITIONING_ALLOWED = Admin.getProperty("PetitioningAllowed", true);
			PETITION_LOGGING = Admin.getProperty("EnablePetitionLogging", false);
			MAX_PETITIONS_PER_PLAYER = Admin.getProperty("MaxPetitionsPerPlayer", 5);
			MAX_PETITIONS_PENDING = Admin.getProperty("MaxPetitionsPending", 25);
			
			ALT_DEV_NO_SCRIPTS = Admin.getProperty("NoScripts", false);
			ALT_DEV_NO_SPAWNS = Admin.getProperty("NoSpawns", false);
			PACKET_HANDLER_DEBUG = Admin.getProperty("PacketHandlerDebug", false);
			
			SERVER_LIST_BRACKET = Admin.getProperty("ServerListBrackets", false);
			SERVER_LIST_CLOCK = Admin.getProperty("ServerListClock", false);
			SERVER_GMONLY = Admin.getProperty("ServerGMOnly", false);
			TEST_SERVER = Admin.getProperty("TestServer", false);
			SERVER_LIST_TESTSERVER = Admin.getProperty("TestServer", false);
			
			// Mods Folder
			// Chat Manager
			ExProperties ChatManager = load(CHATMANAGER_FILE);
			DEFAULT_GLOBAL_CHAT = ChatManager.getProperty("GlobalChat", "GLOBAL");
			DEFAULT_TRADE_CHAT = ChatManager.getProperty("TradeChat", "ON");
			DISABLE_DEAD_CHAT = ChatManager.getProperty("DisableDeadChat", false);
			ENABLE_SHOUT_CHAT_FOR_PVPS = ChatManager.getProperty("EnableShoutPvp", false);
			ENABLE_TRADE_CHAT_FOR_PVPS = ChatManager.getProperty("EnableTradePvp", false);
			SHOUT_CHAT_PVPS = ChatManager.getProperty("ShoutPvpsNeeded", 100);
			TRADE_CHAT_PVPS = ChatManager.getProperty("TradePvpsNeeded", 100);
			
			ALLOW_AIO_AND_VIP_CHAT = ChatManager.getProperty("AllowAioAndVipChat", false);
			
			ENABLE_SHOUT_CHAT_FOR_LEVEL = ChatManager.getProperty("EnableShoutLevel", false);
			SHOUT_CHAT_LEVEL = ChatManager.getProperty("ShoutChatLevel", 20);
			
			// Offline Trades & Craft
			ExProperties OfflineTrades = load(OFFLINETRADES_FILE);
			OFFLINE_TRADE_ENABLE = OfflineTrades.getProperty("OfflineTradeEnable", false);
			OFFLINE_CRAFT_ENABLE = OfflineTrades.getProperty("OfflineCraftEnable", false);
			OFFLINE_SET_NAME_COLOR = OfflineTrades.getProperty("OfflineSetNameColor", false);
			OFFLINE_NAME_COLOR = Integer.decode("0x" + OfflineTrades.getProperty("OfflineNameColor", "808080"));
			OFFLINE_FAME = OfflineTrades.getProperty("OfflineFame", true);
			RESTORE_OFFLINERS = OfflineTrades.getProperty("RestoreOffliners", false);
			OFFLINE_MAX_DAYS = OfflineTrades.getProperty("OfflineMaxDays", 10);
			OFFLINE_DISCONNECT_FINISHED = OfflineTrades.getProperty("OfflineDisconnectFinished", true);
			OFFLINE_TRADE_EFFECT = OfflineTrades.getProperty("OfflineTradeEffect", false);
			OFFLINE_EFFECT_ID = OfflineTrades.getProperty("OfflineEffectId", 1);
			
			// Teleports
			ExProperties Teleports = load(TELEPORTS_FILE);
			ALLOW_FARM1_COMMAND = Teleports.getProperty("AllowFarm1Command", false);
			ALLOW_FARM2_COMMAND = Teleports.getProperty("AllowFarm2Command", false);
			ALLOW_FARM3_COMMAND = Teleports.getProperty("AllowFarm3Command", false);
			ALLOW_PVP1_COMMAND = Teleports.getProperty("AllowPvP1Command", false);
			ALLOW_PVP2_COMMAND = Teleports.getProperty("AllowPvP2Command", false);
			ALLOW_PVP3_COMMAND = Teleports.getProperty("AllowPvP3Command", false);
			
			FARM1_X = Teleports.getProperty("farm1_X", 81304);
			FARM1_Y = Teleports.getProperty("farm1_Y", 14589);
			FARM1_Z = Teleports.getProperty("farm1_Z", -3469);
			FARM2_X = Teleports.getProperty("farm2_X", 81304);
			FARM2_Y = Teleports.getProperty("farm2_Y", 14589);
			FARM2_Z = Teleports.getProperty("farm2_Z", -3469);
			FARM3_X = Teleports.getProperty("farm3_X", 81304);
			FARM3_Y = Teleports.getProperty("farm3_Y", 14589);
			FARM3_Z = Teleports.getProperty("farm3_Z", -3469);
			PVP1_X = Teleports.getProperty("pvp1_X", 81304);
			PVP1_Y = Teleports.getProperty("pvp1_Y", 14589);
			PVP1_Z = Teleports.getProperty("pvp1_Z", -3469);
			PVP2_X = Teleports.getProperty("pvp2_X", 81304);
			PVP2_Y = Teleports.getProperty("pvp2_Y", 14589);
			PVP2_Z = Teleports.getProperty("pvp2_Z", -3469);
			PVP3_X = Teleports.getProperty("pvp3_X", 81304);
			PVP3_Y = Teleports.getProperty("pvp3_Y", 14589);
			PVP3_Z = Teleports.getProperty("pvp3_Z", -3469);
			
			FARM1_CUSTOM_MESSAGE = Teleports.getProperty("Farm1CustomMeesage", "You have been teleported to Farm Zone 1!");
			FARM2_CUSTOM_MESSAGE = Teleports.getProperty("Farm2CustomMeesage", "You have been teleported to Farm Zone 2!");
			FARM3_CUSTOM_MESSAGE = Teleports.getProperty("Farm3CustomMeesage", "You have been teleported to Farm Zone 3!");
			PVP1_CUSTOM_MESSAGE = Teleports.getProperty("PvP1CustomMeesage", "You have been teleported to PvP Zone 1!");
			PVP2_CUSTOM_MESSAGE = Teleports.getProperty("PvP2CustomMeesage", "You have been teleported to PvP Zone 2!");
			PVP3_CUSTOM_MESSAGE = Teleports.getProperty("PvP3CustomMeesage", "You have been teleported to PvP Zone 3!");
			
			// TOWNS TELEPORTS
			GIRAN_CUSTOM_MESSAGE = Teleports.getProperty("GiranCustomMeesage", "You have been teleported to Giran!");
			DION_CUSTOM_MESSAGE = Teleports.getProperty("DionCustomMeesage", "You have been teleported to Dion!");
			ADEN_CUSTOM_MESSAGE = Teleports.getProperty("AdenCustomMeesage", "You have been teleported to Aden!");
			GLUDIO_CUSTOM_MESSAGE = Teleports.getProperty("GludioCustomMeesage", "You have been teleported to Gludio!");
			RUNE_CUSTOM_MESSAGE = Teleports.getProperty("RuneCustomMeesage", "You have been teleported to Rune!");
			HEINE_CUSTOM_MESSAGE = Teleports.getProperty("HeineCustomMeesage", "You have been teleported to Heine!");
			GODDARD_CUSTOM_MESSAGE = Teleports.getProperty("GoddardCustomMeesage", "You have been teleported to Goddard!");
			SCHUTTGART_CUSTOM_MESSAGE = Teleports.getProperty("SchuttgartCustomMeesage", "You have been teleported to Schuttgart!");
			OREN_CUSTOM_MESSAGE = Teleports.getProperty("OrenCustomMeesage", "You have been teleported to Oren!");
			
			ALLOW_GIRAN_COMMAND = Teleports.getProperty("AllowGiranCommand", false);
			ALLOW_DION_COMMAND = Teleports.getProperty("AllowDionCommand", false);
			ALLOW_ADEN_COMMAND = Teleports.getProperty("AllowAdenCommand", false);
			ALLOW_GLUDIO_COMMAND = Teleports.getProperty("AllowGludioCommand", false);
			ALLOW_RUNE_COMMAND = Teleports.getProperty("AllowRuneCommand", false);
			ALLOW_HEINE_COMMAND = Teleports.getProperty("AllowHeineCommand", false);
			ALLOW_GODDARD_COMMAND = Teleports.getProperty("AllowGoddardCommand", false);
			ALLOW_SCHUTTGART_COMMAND = Teleports.getProperty("AllowSchuttgartCommand", false);
			ALLOW_OREN_COMMAND = Teleports.getProperty("AllowOrenCommand", false);
			
			GIRAN_X = Teleports.getProperty("giran_X", 83473);
			GIRAN_Y = Teleports.getProperty("giran_Y", 148554);
			GIRAN_Z = Teleports.getProperty("giran_Z", -3400);
			
			DION_X = Teleports.getProperty("dion_X", 15619);
			DION_Y = Teleports.getProperty("dion_Y", 143132);
			DION_Z = Teleports.getProperty("dion_Z", -2705);
			
			ADEN_X = Teleports.getProperty("aden_X", 147974);
			ADEN_Y = Teleports.getProperty("aden_Y", 26883);
			ADEN_Z = Teleports.getProperty("aden_Z", -2200);
			
			GLUDIO_X = Teleports.getProperty("gludio_X", -14413);
			GLUDIO_Y = Teleports.getProperty("gludio_Y", 123044);
			GLUDIO_Z = Teleports.getProperty("gludio_Z", -3112);
			
			RUNE_X = Teleports.getProperty("rune_X", 43759);
			RUNE_Y = Teleports.getProperty("rune_Y", -48122);
			RUNE_Z = Teleports.getProperty("rune_Z", -792);
			
			HEINE_X = Teleports.getProperty("heine_X", 111381);
			HEINE_Y = Teleports.getProperty("heine_Y", 218981);
			HEINE_Z = Teleports.getProperty("heine_Z", -3538);
			
			GODDARD_X = Teleports.getProperty("goddard_X", 147732);
			GODDARD_Y = Teleports.getProperty("goddard_Y", -56554);
			GODDARD_Z = Teleports.getProperty("goddard_Z", -2776);
			
			SCHUTTGART_X = Teleports.getProperty("schuttgart_X", 87355);
			SCHUTTGART_Y = Teleports.getProperty("schuttgart_Y", -142095);
			SCHUTTGART_Z = Teleports.getProperty("schuttgart_Z", -1336);
			
			OREN_X = Teleports.getProperty("oren_X", 82760);
			OREN_Y = Teleports.getProperty("oren_Y", 53578);
			OREN_Z = Teleports.getProperty("oren_Z", -1491);
			
			// Community Board
			ExProperties CommunityBoard = load(COMMUNITYBOARD_FILE);
			ENABLE_COMMUNITY_BOARD = CommunityBoard.getProperty("EnableCommunityBoard", false);
			BBS_DEFAULT = CommunityBoard.getProperty("BBSDefault", "_bbshome");
			
			CustomCB = CommunityBoard.getProperty("CustomCB", false);
			TOP_PLAYER_RESULTS = CommunityBoard.getProperty("TopPlayerResults", 20);
			TOP_PLAYER_ROW_HEIGHT = CommunityBoard.getProperty("TopPlayerRowHeight", 18);
			
			RAID_LIST_ROW_HEIGHT = CommunityBoard.getProperty("RaidListRowHeight", 18);
			RAID_LIST_RESULTS = CommunityBoard.getProperty("RaidListResults", 20);
			RAID_LIST_SORT_ASC = CommunityBoard.getProperty("RaidListSortAsc", true);
			
			ENABLE_BBS_PASS_CHANGE = CommunityBoard.getProperty("EnablePasswordChangeBBS", false);
			ENABLE_BBS_REPORT = CommunityBoard.getProperty("EnableProblemReportingBBSFuncion", false);
			
			PAY_PAL_BBS_LINK = CommunityBoard.getProperty("PayPalBBSLink", "http://paypal.com");
			ENABLE_PAY_SAFE_DONATION_BBS = CommunityBoard.getProperty("EnablePaySafeBBSDonation", false);
			ENABLE_PAY_PAL_DONATION_BBS = CommunityBoard.getProperty("EnablePayPalBBSDonation", false);
			
			StringTokenizer buff = new StringTokenizer(CommunityBoard.getProperty("CommunityBufferExcludeOn", ""), " ");
			while (buff.hasMoreTokens())
			{
				COMMUNITY_BUFFER_EXCLUDE_ON.add(buff.nextToken());
			}
			StringTokenizer gk = new StringTokenizer(CommunityBoard.getProperty("GatekeeperExcludeOn", ""), " ");
			while (gk.hasMoreTokens())
			{
				COMMUNITY_GATEKEEPER_EXCLUDE_ON.add(gk.nextToken());
			}
			
			ALLOW_CLASS_MASTERSCB = CommunityBoard.getProperty("AllowClassMastersCB", "0");
			CLASS_MASTERS_PRICE_ITEMCB = CommunityBoard.getProperty("ClassMastersPriceItemCB", 57);
			if ((ALLOW_CLASS_MASTERSCB.length() != 0) && (!ALLOW_CLASS_MASTERSCB.equals("0")))
				for (String classid : ALLOW_CLASS_MASTERSCB.split(","))
					ALLOW_CLASS_MASTERS_LISTCB.add(Integer.valueOf(classid));
			CLASS_MASTERS_PRICECB = CommunityBoard.getProperty("ClassMastersPriceCB", "0,0,0");
			if (CLASS_MASTERS_PRICECB.length() >= 5)
			{
				int level = 0;
				for (String classid : CLASS_MASTERS_PRICECB.split(","))
				{
					CLASS_MASTERS_PRICE_LISTCB[level] = Integer.parseInt(classid);
					level++;
				}
			}
			
			// Donator
			ExProperties Donator = load(DONATOR_FILE);
			ENABLE_AIO_SYSTEM = Donator.getProperty("EnableAioSystem", true);
			ALLOW_AIO_NCOLOR = Donator.getProperty("AllowAioNameColor", true);
			AIO_NCOLOR = Integer.decode("0x" + Donator.getProperty("AioNameColor", "88AA88"));
			ALLOW_AIO_TCOLOR = Donator.getProperty("AllowAioTitleColor", true);
			AIO_TCOLOR = Integer.decode("0x" + Donator.getProperty("AioTitleColor", "88AA88"));
			AIO_ITEMID = Donator.getProperty("ItemIdAio", 9945);
			ALLOW_AIO_ITEM = Donator.getProperty("AllowAIOItem", false);
			
			if (ENABLE_AIO_SYSTEM) // create map if system is enabled
			{
				String[] AioSkillsSplit = Donator.getProperty("AioSkills", "").split(";");
				AIO_SKILLS = new HashMap<>(AioSkillsSplit.length);
				for (String skill : AioSkillsSplit)
				{
					String[] skillSplit = skill.split(",");
					if (skillSplit.length != 2)
					{
						System.out.println("[Aio System]: invalid config property in players.properties -> AioSkills \"" + skill + "\"");
					}
					else
					{
						try
						{
							AIO_SKILLS.put(Integer.parseInt(skillSplit[0]), Integer.parseInt(skillSplit[1]));
						}
						catch (NumberFormatException nfe)
						{
							if (!skill.equals(""))
							{
								System.out.println("[Aio System]: invalid config property in players.props -> AioSkills \"" + skillSplit[0] + "\"" + skillSplit[1]);
							}
						}
					}
				}
			}
			
			VIP_LIFE_TIME = Donator.getProperty("VipLifeTime", 86400000L);
			VIPS_HERO_AURA = Donator.getProperty("VipAura", false);
			VIP_NAME_COLOR_CONFIG = Donator.getProperty("VipNameEnable", true);
			VIPS_NAME_COLOR = Donator.getProperty("VipsNameColor", "FFFFFF");
			VIPS_TITLE_COLOR = Donator.getProperty("VipsTitleColor", "FFFFFF");
			VIPS_SKILLS_CONFIG = Donator.getProperty("VipSkillsEnable", true);
			VIP_SKILLS = new HashMap<>();
			for (String st : Donator.getProperty("VipSkills").split(";"))
			{
				try
				{
					int skill = Integer.valueOf(st.split(",")[0]);
					int level = Integer.valueOf(st.split(",")[1]);
					
					VIP_SKILLS.put(skill, level);
				}
				catch (NumberFormatException nfe)
				{
					nfe.printStackTrace();
				}
			}
			VIPS_RATE_CONFIG = Donator.getProperty("RateConfigEnable", true);
			RATE_XP_VIP = Donator.getProperty("RateXpVip", 1.);
			RATE_SP_VIP = Donator.getProperty("RateSpVip", 1.);
			RATE_DROP_ADENA_VIP = Donator.getProperty("RateAdenaVip", 1.);
			RATE_DROP_ITEMS_VIP = Donator.getProperty("RateDropVip", 1.);
			RATE_DROP_ITEMS_BY_RAID_VIP = Donator.getProperty("RateDropRaidboss", 1.);
			RATE_DROP_SPOIL_VIP = Donator.getProperty("RateDropSpoilVip", 1.);
			VIP_ENCHANT_CONFIG = Donator.getProperty("VipEnchantEnable", false);
			VIP_ENCHANT_CHANCE_WEAPON_MAGIC = Donator.getProperty("VipEnchantChanceMagicWeapon", 0.4);
			VIP_ENCHANT_CHANCE_WEAPON_MAGIC_15PLUS = Donator.getProperty("VipEnchantChanceMagicWeapon15Plus", 0.2);
			VIP_ENCHANT_CHANCE_WEAPON_NONMAGIC = Donator.getProperty("VipEnchantChanceNonMagicWeapon", 0.7);
			VIP_ENCHANT_CHANCE_WEAPON_NONMAGIC_15PLUS = Donator.getProperty("VipEnchantChanceNonMagicWeapon15Plus", 0.35);
			VIP_ENCHANT_CHANCE_ARMOR = Donator.getProperty("VipEnchantChanceArmor", 0.4);
			ENABLE_VIP_NPC = Donator.getProperty("EnableVipNpc", true);
			VIP_NPC_ITEM = Donator.getProperty("VipNpcItem", 9220);
			
			// Automatation
			ExProperties Automatation = load(AUTOMATATION_FILE);
			ALT_SERVER_NAME_ENABLED = Automatation.getProperty("ServerNameEnabled", false);
			ALT_SERVER_NAME = Automatation.getProperty("ServerName", "L2JxTreme");
			
			RESTART_BY_TIME_OF_DAY = Automatation.getProperty("EnableRestartSystem", false);
			RESTART_SECONDS = Automatation.getProperty("RestartSeconds", 360);
			RESTART_INTERVAL_BY_TIME_OF_DAY = Automatation.getProperty("RestartByTimeOfDay", "23:59").split(",");
			
			SOCIAL_ACTION_ON_LOGIN = Automatation.getProperty("SocialActionOnLogin", false);
			
			ALLOW_AUTO_REWARDER = Automatation.getProperty("AllowAutoRewarder", false);
			AUTO_REWARDER_SCHEDULE = Automatation.getProperty("AutoRewarderSchedule", 0);
			AUTO_REWARDER_REWARDS = parseItemsList(Automatation.getProperty("AutoRewarderRewards", "6651,50"));
			AUTO_REWARDER_DUALBOX_ALLOWED = Automatation.getProperty("AutoRewarderDualboxAllowed", 0);
			
			ALLOW_ENTER_PMS = Automatation.getProperty("AllowEnterPms", false);
			ENTER_PMS_SYSTEM = Automatation.getProperty("EnterPmsSystem", 0);
			String enter_pms = Automatation.getProperty("EnterPms", "Welcome to L2 Server!,L2Server;Don't forget to vote.,L2Server;");
			String[] enter_pms_splitted_1 = enter_pms.split(";");
			for (String i : enter_pms_splitted_1)
			{
				String eps2[] = i.split(",");
				ENTER_PMS.put(eps2[0], eps2[1]);
			}
			
			WELCOME_MESSAGE_ENABLED = Automatation.getProperty("ScreenWelcomeMessageEnable", false);
			WELCOME_MESSAGE_TEXT = Automatation.getProperty("ScreenWelcomeMessageText", "Welcome to L2JxTreme!");
			WELCOME_MESSAGE_TIME = Automatation.getProperty("ScreenWelcomeMessageTime", 10) * 1000;
			
			CUSTOM_SPAWN_CLASS = Automatation.getProperty("CustomSpawnClass", false);
			SPANW_MAGE_X = Automatation.getProperty("SpawnMageX", 10);
			SPANW_MAGE_Y = Automatation.getProperty("SpawnMageY", 10);
			SPANW_MAGE_Z = Automatation.getProperty("SpawnMageZ", 10);
			SPANW_FIGHTER_X = Automatation.getProperty("SpawnFighterX", 10);
			SPANW_FIGHTER_Y = Automatation.getProperty("SpawnFighterY", 10);
			SPANW_FIGHTER_Z = Automatation.getProperty("SpawnFighterZ", 10);
			
			ANNOUNCE_ADMIN_LOGIN = Automatation.getProperty("AnnounceAdminLogin", false);
			ANNOUNCE_CLAN_LOGIN = Automatation.getProperty("AnnounceClanLogin", false);
			ANNOUNCE_HERO_LOGIN = Automatation.getProperty("AnnounceHeroLogin", false);
			ANNOUNCE_NOBLE_LOGIN = Automatation.getProperty("AnnounceNobleLogin", false);
			ANNOUNCE_AIO_LOGIN = Automatation.getProperty("AnnounceAioLogin", false);
			ANNOUNCE_LORDS_LOGIN = Automatation.getProperty("AnnounceLords", false);
			ANNOUNCE_NEWBIE_LOGIN = Automatation.getProperty("AnnounceNewbie", false);
			ANNOUNCE_GOD_LOGIN = Automatation.getProperty("AnnounceGodLogin", false);
			
			SHOW_AIO_ENABLE = Automatation.getProperty("ShowAioOnline", false);
			
			SHOW_OFFLINE_SHOP = Automatation.getProperty("ShowOfflineShop", false);
			
			NEW_PLAYER_BUFFS = Automatation.getProperty("AltNewCharBuffs", false);
			NEW_PLAYER_FIGHT_BUFF_LIST = parseItemsList(Automatation.getProperty("FighterBuffList", "123,456"));
			NEW_PLAYER_MAGE_BUFF_LIST = parseItemsList(Automatation.getProperty("MageBuffList", "789,1223"));
			
			ONLINE_PLAYERS_ON_LOGIN = Automatation.getProperty("OnlineOnLogin", false);
			ANNOUNCE_ONLINE_PLAYER_EVERY = Automatation.getProperty("AnnounceOnlinePlayerEvery", 600);
			ALLOW_ANNOUNCE_ONLINE_PLAYERS = Automatation.getProperty("AllowAnnounceOnlinePlayers", false);
			
			ADDFAKEPLAYERSMODE = Automatation.getProperty("FakePlayersSystem", false);
			ADDFAKEPLAYERSNUMBER = Automatation.getProperty("NumberPlussForFakePlayers", 0);
			
			OLYMPIAD_END_ON_LOGIN = Automatation.getProperty("OlympiadEndOnLogin", false);
			SERVER_TIME_ON_LOGIN = Automatation.getProperty("ServerTimeOnLogin", false);
			
			ALLOW_START_TITLE = Automatation.getProperty("AllowStartTitle", false);
			START_TITLE = Automatation.getProperty("StartTitle", "L2JxTreme");
			
			SERVER_NEWS = Automatation.getProperty("ShowServerNews", false);
			
			AUTO_ANNOUNCE_DELAY = Automatation.getProperty("AutoAnnounceDelay", 60);
			AUTO_ANNOUNCE_TEXT = Automatation.getProperty("AutoAnnounceText", "L2JxTreme");
			AUTO_ANNOUNCE_ENABLE = Automatation.getProperty("AutoAnnounceEnable", false);
			
			// Commands
			ExProperties Commands = load(COMMANDS_FILE);
			ENABLE_BUFF_COMMAND = Commands.getProperty("EnableBuffCommand", false);
			LIST_BUFF_COMMAND = Commands.getProperty("buffCommandFightBuffsID", "123,456");
			
			String[] buffCommand = LIST_BUFF_COMMAND.split(",");
			BUFF_COMMAND_FIGHT_IDBUFFS = new int[buffCommand.length];
			for (int i = 0; i < buffCommand.length; i++)
				BUFF_COMMAND_FIGHT_IDBUFFS[i] = Integer.parseInt(buffCommand[i]);
			
			LIST_BUFF_COMMAND = Commands.getProperty("buffCommandMageBuffsID", "789,1011112");
			
			buffCommand = LIST_BUFF_COMMAND.split(",");
			BUFF_COMMAND_MAGE_IDBUFFS = new int[buffCommand.length];
			for (int i = 0; i < buffCommand.length; i++)
				BUFF_COMMAND_MAGE_IDBUFFS[i] = Integer.parseInt(buffCommand[i]);
			
			VOTE_BUFF_ENABLED = Commands.getProperty("VoteBuffCmdEnabled", false);
			VOTE_ITEM_ID = Commands.getProperty("VoteItemId", 3470);
			VOTE_ITEM_AMOUNT = Commands.getProperty("VoteItemRequired", 5);
			VOTE_BUFF_ID = Commands.getProperty("VoteBuffId", 3132);
			VOTE_BUFF_LVL = Commands.getProperty("VoteBuffLevel", 10);
			VOTE_ITEM_NAME = Commands.getProperty("VoteItemName", "VoteItemName");
			
			RES_COMMAND = Commands.getProperty("ResVoicedCommand", false);
			
			REGISTER_SIEGE_BY_COMMAND = Commands.getProperty("RegisterSiegeCommand", false);
			
			ENABLE_MAIL_SYSTEM = Commands.getProperty("MailSystemEnable", false);
			
			GO_TO_CASTLE_COMMAND_ENABLE = Commands.getProperty("EnableGoToCastleCMD", false);
			
			XMAS_COMMAND_ENABLE = Commands.getProperty("EnablexMasCMD", false);
			
			ENABLE_BOSSINFO_VC = Commands.getProperty("EnableBossInfoVC", false);
			GRAND_BOSS = Commands.getProperty("GrandBossList");
			GRAND_BOSS_LIST = new ArrayList<>();
			for (String id : GRAND_BOSS.trim().split(","))
			{
				GRAND_BOSS_LIST.add(Integer.parseInt(id.trim()));
			}
			
			ENABLE_WHO_AM_I_VC = Commands.getProperty("EnableWhoAmI", false);
			ONLINE_PLAYERS = Commands.getProperty("OnlinePlayers", false);
			ENABLE_VERSION_COMMAND = Commands.getProperty("VersionCommand", false);
			ENABLE_PIN_SYSTEM = Commands.getProperty("EnablePinSystem", false);
			GEARSCORE_ENABLED = Commands.getProperty("EnableGearScore", false);
			VIEWDETAILS_ENABLED = Commands.getProperty("EnableViewDetails", false);
			ENABLE_PERSONAL_MENU_SYSTEM = Commands.getProperty("EnablePersonalMenuSystem", false);
			SKIN = Commands.getProperty("Skin", 1);
			USER_FIRST_LVL = Commands.getProperty("UserFirstLvl", 0);
			USER_FIRST = Commands.getProperty("UserFirst", 0);
			ON_ENTER_MAIL = Commands.getProperty("OnEnterMail", 0);
			
			EMAIL_USER = Commands.getProperty("EmailUsername", "yourgmail@gmail.com");
			EMAIL_PASS = Commands.getProperty("EmailPassword", "yourpassword");
			
			BANKING_SYSTEM_ENABLED = Commands.getProperty("BankingEnabled", false);
			BANKING_SYSTEM_GOLDBARS = Commands.getProperty("BankingGoldbarCount", 1);
			BANKING_SYSTEM_ADENA = Commands.getProperty("BankingAdenaCount", 500000000);
			
			// Champion Mobs
			ExProperties ChampionMobs = load(CHAMPIONMOBS_FILE);
			CHAMPION_FREQUENCY = ChampionMobs.getProperty("ChampionFrequency", 0);
			CHAMP_MIN_LVL = ChampionMobs.getProperty("ChampionMinLevel", 20);
			CHAMP_MAX_LVL = ChampionMobs.getProperty("ChampionMaxLevel", 70);
			CHAMPION_HP = ChampionMobs.getProperty("ChampionHp", 8);
			CHAMPION_HP_REGEN = ChampionMobs.getProperty("ChampionHpRegen", 1.);
			CHAMPION_REWARDS = ChampionMobs.getProperty("ChampionRewards", 8);
			CHAMPION_ADENAS_REWARDS = ChampionMobs.getProperty("ChampionAdenasRewards", 1);
			CHAMPION_REWARD = ChampionMobs.getProperty("ChampionRewardItem", 0);
			CHAMPION_REWARD_ID = ChampionMobs.getProperty("ChampionRewardItemID", 6393);
			CHAMPION_REWARD_QTY = ChampionMobs.getProperty("ChampionRewardItemQty", 1);
			
			// Vote Manager
			ExProperties VoteManager = load(VOTEMANAGER_FILE);
			ALLOW_HOPZONE_VOTE_REWARD = VoteManager.getProperty("AllowHopzoneVoteReward", false);
			HOPZONE_SERVER_LINK = VoteManager.getProperty("HopzoneServerLink", "http://l2.topzone.net/lineage2/details/74078/L2World-Servers/");
			HOPZONE_FIRST_PAGE_LINK = VoteManager.getProperty("HopzoneFirstPageLink", "http://l2.topzone.net/lineage2/");
			HOPZONE_VOTES_DIFFERENCE = VoteManager.getProperty("HopzoneVotesDifference", 5);
			HOPZONE_FIRST_PAGE_RANK_NEEDED = VoteManager.getProperty("HopzoneFirstPageRankNeeded", 15);
			HOPZONE_REWARD_CHECK_TIME = VoteManager.getProperty("HopzoneRewardCheckTime", 5);
			HOPZONE_SMALL_REWARD = parseItemsList(VoteManager.getProperty("HopzoneSmallReward", "57,100000000;"));
			HOPZONE_BIG_REWARD = parseItemsList(VoteManager.getProperty("HopzoneBigReward", "3470,1;"));
			HOPZONE_DUALBOXES_ALLOWED = VoteManager.getProperty("HopzoneDualboxesAllowed", 1);
			ALLOW_HOPZONE_GAME_SERVER_REPORT = VoteManager.getProperty("AllowHopzoneGameServerReport", false);
			ALLOW_TOPZONE_VOTE_REWARD = VoteManager.getProperty("AllowTopzoneVoteReward", false);
			TOPZONE_SERVER_LINK = VoteManager.getProperty("TopzoneServerLink", "http://l2.topzone.net/lineage2/details/74078/L2World-Servers/");
			TOPZONE_FIRST_PAGE_LINK = VoteManager.getProperty("TopzoneFirstPageLink", "http://l2.topzone.net/lineage2/");
			TOPZONE_VOTES_DIFFERENCE = VoteManager.getProperty("TopzoneVotesDifference", 5);
			TOPZONE_FIRST_PAGE_RANK_NEEDED = VoteManager.getProperty("TopzoneFirstPageRankNeeded", 15);
			TOPZONE_REWARD_CHECK_TIME = VoteManager.getProperty("TopzoneRewardCheckTime", 5);
			TOPZONE_SMALL_REWARD = parseItemsList(VoteManager.getProperty("TopzoneSmallReward", "57,100000000;"));
			TOPZONE_BIG_REWARD = parseItemsList(VoteManager.getProperty("TopzoneBigReward", "3470,1;"));
			TOPZONE_DUALBOXES_ALLOWED = VoteManager.getProperty("TopzoneDualboxesAllowed", 1);
			ALLOW_TOPZONE_GAME_SERVER_REPORT = VoteManager.getProperty("AllowTopzoneGameServerReport", false);
			
			// Mods
			ExProperties Mods = load(MODS_FILE);
			ALLOW_SKILL_GIVER = Mods.getProperty("AllowSkillGiver", false);
			
			ALLOW_REWARD_BOX = Mods.getProperty("AllowRewardBox", false);
			REWARD_BOX_ID = Mods.getProperty("RewardBoxID", 3470);
			FIRST_REWARD_ID = Mods.getProperty("FirstRewardID", 7000);
			FIRST_REWARD_COUNT = Mods.getProperty("FirstRewardCount", 1);
			SECOND_REWARD_ID = Mods.getProperty("SecondRewardID", 7001);
			SECOND_REWARD_COUNT = Mods.getProperty("SecondRewardCount", 1);
			THIRD_REWARD_ID = Mods.getProperty("ThirdRewardID", 7002);
			THIRD_REWARD_COUNT = Mods.getProperty("ThirdRewardCount", 1);
			
			USE_CR_ITEM = Mods.getProperty("EnableTheClanRepPointsItem", false);
			CR_ITEM_MIN_CLAN_LVL = Mods.getProperty("ClanLevelNeededForCR", 5);
			CR_ITEM_REPS_TO_BE_AWARDED = Mods.getProperty("HowManyClanRepsToGive", 500);
			CR_ITEM_REPS_ITEM_ID = Mods.getProperty("CRItemID", 6673);
			
			ALLOW_SANTA_HAT_ON_NEW_CHARACTERS = Mods.getProperty("AllowSantaHatOnNewCharacters", false);
			
			ENALBE_LUCKY_SCROLL = Mods.getProperty("EnableLuckyScroll", false);
			LUCKY_SCROLL_ID = Mods.getProperty("LuckyScrollItemId", 3470);
			
			SKILL_GIVER_SKILL_ID = Mods.getProperty("SkillGiverSkillId", 1);
			SKILL_GIVER_SKILL_LEVEL = Mods.getProperty("SkillGiverSkillLevel", 1);
			SKILL_GIVER_ID = Mods.getProperty("SkillGiverItemId", 3470);
			
			ENABLE_SAY_SOCIAL_ACTIONS = Mods.getProperty("EnableSocialSayActions", false);
			
			STARTUP_SYSTEM_ENABLED = Mods.getProperty("EnableStartupSystem", false);
			
			DISABLE_TUTORIAL = Mods.getProperty("DisableTutorial", false);
			
			ALLOW_KEYBOARD_MOVEMENT = Mods.getProperty("AllowKeyboardMovement", false);
			
			MIN_PVP_TO_USE_STORE = Mods.getProperty("PvPToUseStore", 0);
			MIN_PK_TO_USE_STORE = Mods.getProperty("PkToUseStore", 0);
			
			// Custom Npcs
			ExProperties CustomNpcs = load(CUSTOMNPCS_FILE);
			
			PROTECTOR_PLAYER_PK = CustomNpcs.getProperty("ProtectorPlayerPK", false);
			PROTECTOR_PLAYER_PVP = CustomNpcs.getProperty("ProtectorPlayerPVP", false);
			PROTECTOR_RADIUS_ACTION = CustomNpcs.getProperty("ProtectorRadiusAction", 500);
			PROTECTOR_SKILLID = CustomNpcs.getProperty("ProtectorSkillId", 1069);
			PROTECTOR_SKILLLEVEL = CustomNpcs.getProperty("ProtectorSkillLevel", 42);
			PROTECTOR_SKILLTIME = CustomNpcs.getProperty("ProtectorSkillTime", 800);
			PROTECTOR_MESSAGE = CustomNpcs.getProperty("ProtectorMessage", "Protector, not spawnkilling here, go read the rules !!!");
			PROTECTOR_SCAN_RANGE = CustomNpcs.getProperty("ProtectorScanRange", 1250);
			
			SET_HERO_PVP_COST = CustomNpcs.getProperty("SetHeroPvpCost", 500);
			SET_NOBLE_PVP_COST = CustomNpcs.getProperty("SetNoblePvpCost", 250);
			GIVE_ITEM_PVP_COST = CustomNpcs.getProperty("GiveItemPvpCost", 100);
			ITEM_ID = CustomNpcs.getProperty("ItemId", 3470);
			ITEM_COUNT = CustomNpcs.getProperty("ItemCount", 500);
			GET_A_SKILL_PVP_COST = CustomNpcs.getProperty("GetASkillPvpCost", 1500);
			SKILL_ID = CustomNpcs.getProperty("SkillId", 3131);
			SKILL_LVL = CustomNpcs.getProperty("SkillLvl", 10);
			
			WYVERN_ALLOW_UPGRADER = CustomNpcs.getProperty("AllowWyvernUpgrader", true);
			WYVERN_REQUIRED_LEVEL = CustomNpcs.getProperty("RequiredStriderLevel", 55);
			WYVERN_REQUIRED_CRYSTALS = CustomNpcs.getProperty("RequiredCrystalsNumber", 10);
			
			EXPERIENCE_BLESSING_BONUS = CustomNpcs.getProperty("ExperienceBlessingBonus", 1.0);
			BLESSING_HOURS = CustomNpcs.getProperty("BlessingOfExperienceHours", 5);
			BLESSING_NPC_ID = CustomNpcs.getProperty("BlessingOfExperienceNpcId", 65533);
			
			ITEM_NEEDED_FOR_ENCH = CustomNpcs.getProperty("ItemConsumedForEnchantViaNpc", 3470);
			AM_ITEM_NEEDED_FOR_ENCH = CustomNpcs.getProperty("AmountOfItemConsumedForEnchantViaNpc", 1);
			MAX_ENCH_FOR_NPC = CustomNpcs.getProperty("MaxEnchantForEnchantViaNpc", 25);
			ENCH_ADDITION = CustomNpcs.getProperty("EnchantAddition", 1);
			ENCH_FAIL_LVL = CustomNpcs.getProperty("EnchantOfItemOnFail", 0);
			ENCH_SAFE_NPC = CustomNpcs.getProperty("SafeEnchantForEnchantViaNpc", 3);
			ENCHANT_CHANCE_ARM_NPC = CustomNpcs.getProperty("NpcEnchantChanceArm", 100);
			ENCHANT_CHANCE_WEAP_NPC = CustomNpcs.getProperty("NpcEnchantChanceWeap", 100);
			ENCHANT_CHANCE_JEWELERY_NPC = CustomNpcs.getProperty("NpcEnchantChanceJewel", 100);
			
			REBIRTH_MIN_LEVEL = CustomNpcs.getProperty("RebirthMinLevel", 78);
			REBIRTH_MAX = CustomNpcs.getProperty("RebirthMaxAllowed", 3);
			REBIRTH_RETURN_TO_LEVEL = CustomNpcs.getProperty("RebirthReturnToLevel", 1);
			REBIRTH_ITEMS = CustomNpcs.getProperty("RebirthItems", "").split(";");
			
			VOTE_LINK_HOPZONE = CustomNpcs.getProperty("HopzoneUrl", "null");
			VOTE_LINK_TOPZONE = CustomNpcs.getProperty("TopzoneUrl", "null");
			VOTE_REWARD_ID1 = CustomNpcs.getProperty("VoteRewardId1", 300);
			VOTE_REWARD_ID2 = CustomNpcs.getProperty("VoteRewardId2", 300);
			VOTE_REWARD_ID3 = CustomNpcs.getProperty("VoteRewardId3", 300);
			VOTE_REWARD_ID4 = CustomNpcs.getProperty("VoteRewardId4", 300);
			VOTE_REWARD_AMOUNT1 = CustomNpcs.getProperty("VoteRewardAmount1", 300);
			VOTE_REWARD_AMOUNT2 = CustomNpcs.getProperty("VoteRewardAmount2", 300);
			VOTE_REWARD_AMOUNT3 = CustomNpcs.getProperty("VoteRewardAmount3", 300);
			VOTE_REWARD_AMOUNT4 = CustomNpcs.getProperty("VoteRewardAmount4", 300);
			SECS_TO_VOTE = CustomNpcs.getProperty("SecondsToVote", 20);
			EXTRA_REW_VOTE_AM = CustomNpcs.getProperty("ExtraRewVoteAm", 20);
			
			ALT_GAME_SUBCLASS_EVERYWHERE = CustomNpcs.getProperty("AltSubclassEverywhere", false);
			
			ALLOW_WEDDING = CustomNpcs.getProperty("AllowWedding", false);
			WEDDING_PRICE = CustomNpcs.getProperty("WeddingPrice", 1000000);
			WEDDING_SAMESEX = CustomNpcs.getProperty("WeddingAllowSameSex", false);
			WEDDING_FORMALWEAR = CustomNpcs.getProperty("WeddingFormalWear", true);
			
			BUFFS_MAX_AMOUNT = CustomNpcs.getProperty("MaxBuffsAmount", 20);
			STORE_SKILL_COOLTIME = CustomNpcs.getProperty("StoreSkillCooltime", true);
			
			BUFFER_MAX_SCHEMES = CustomNpcs.getProperty("BufferMaxSchemesPerChar", 4);
			BUFFER_MAX_SKILLS = CustomNpcs.getProperty("BufferMaxSkillsPerScheme", 24);
			BUFFER_STATIC_BUFF_COST = CustomNpcs.getProperty("BufferStaticCostPerBuff", -1);
			BUFFER_BUFFS = CustomNpcs.getProperty("BufferBuffs");
			
			BUFFER_BUFFLIST = new HashMap<>();
			for (String skillInfo : BUFFER_BUFFS.split(";"))
			{
				final String[] infos = skillInfo.split(",");
				BUFFER_BUFFLIST.put(Integer.valueOf(infos[0]), new BuffSkillHolder(Integer.valueOf(infos[0]), Integer.valueOf(infos[1]), infos[2]));
			}
			
			ENABLE_MODIFY_SKILL_DURATION = CustomNpcs.getProperty("EnableModifySkillDuration", false);
			if (ENABLE_MODIFY_SKILL_DURATION)
			{
				SKILL_DURATION_LIST = new HashMap<>();
				
				String[] propertySplit;
				propertySplit = CustomNpcs.getProperty("SkillDurationList", "").split(";");
				
				for (String skill : propertySplit)
				{
					String[] skillSplit = skill.split(",");
					if (skillSplit.length != 2)
					{
						System.out.println("[SkillDurationList]: invalid config property -> SkillDurationList \"" + skill + "\"");
					}
					else
					{
						try
						{
							SKILL_DURATION_LIST.put(Integer.parseInt(skillSplit[0]), Integer.parseInt(skillSplit[1]));
						}
						catch (NumberFormatException nfe)
						{
							nfe.printStackTrace();
							
							if (!skill.equals(""))
							{
								System.out.println("[SkillDurationList]: invalid config property -> SkillList \"" + skillSplit[0] + "\"" + skillSplit[1]);
							}
						}
					}
				}
			}
			SERVICES_NPC_ID = CustomNpcs.getProperty("NpcId", 4);
			SERVICES_NOBLE_ITEM_ID = CustomNpcs.getProperty("NobleItemId", 57);
			SERVICES_NOBLE_ITEM_COUNT = CustomNpcs.getProperty("NobleItemCount", 100000);
			SERVICES_PK_ITEM_ID = CustomNpcs.getProperty("PkItemId", 57);
			SERVICES_PK_ITEM_COUNT = CustomNpcs.getProperty("PkItemCount", 1000);
			SERVICES_CHANGE_NAME_ID = CustomNpcs.getProperty("ChangeNameId", 57);
			SERVICES_CHANGE_NAME_COUNT = CustomNpcs.getProperty("ChangeNameCount", 1000000);
			SERVICES_CLAN_NAME_CHANGE_ID = CustomNpcs.getProperty("ClanNameId", 57);
			SERVICES_CLAN_NAME_CHANGE_COUNT = CustomNpcs.getProperty("ClanNameCount", 50000);
			SERVICES_CLAN_NAME_CHANGE_MIN_LVL = CustomNpcs.getProperty("ClanNameMinLevel", 5);
			SERVICES_CLAN_LEVEL_UP_5to6_ID = CustomNpcs.getProperty("LevelUp6Id", 57);
			SERVICES_CLAN_LEVEL_UP_6to7_ID = CustomNpcs.getProperty("LevelUp7Id", 57);
			SERVICES_CLAN_LEVEL_UP_7to8_ID = CustomNpcs.getProperty("LevelUp8Id", 57);
			SERVICES_CLAN_LEVEL_UP_5to6_COUNT = CustomNpcs.getProperty("LevelUp6Count", 1000);
			SERVICES_CLAN_LEVEL_UP_6to7_COUNT = CustomNpcs.getProperty("LevelUp7Count", 1000);
			SERVICES_CLAN_LEVEL_UP_7to8_COUNT = CustomNpcs.getProperty("LevelUp8Count", 1000);
			SERVICES_CLAN_REP_POINTS_ID = CustomNpcs.getProperty("ClanRepPointsId", 57);
			SERVICES_CLAN_REP_POINTS_COUNT = CustomNpcs.getProperty("ClanRepPointsCount", 1000);
			SERVICES_CHANGE_SEX_ID = CustomNpcs.getProperty("ChangeSexId", 57);
			SERVICES_CHANGE_SEX_COUNT = CustomNpcs.getProperty("ChangeSexCount", 1000);
			SERVICES_COLOR_ID = CustomNpcs.getProperty("ChangeColorId", 1000);
			SERVICES_COLOR_COUNT = CustomNpcs.getProperty("ChangeColorCount", 1000);
			SERVICES_PREMIUM_ID = CustomNpcs.getProperty("ChangePremiumId", 1000);
			SERVICES_PREMIUM_COUNT = CustomNpcs.getProperty("ChangePremiumCount", 1000);
			
			// Events Folder
			// Random Fight
			ExProperties RandomFight = load(RANDOMFIGHT_FILE);
			ALLOW_RANDOM_FIGHT = RandomFight.getProperty("AllowRandomFight", false);
			EVERY_MINUTES = RandomFight.getProperty("EveryMinutes", 3);
			RANDOM_FIGHT_REWARD_ID = RandomFight.getProperty("RewardId", 8762);
			RANDOM_FIGHT_REWARD_COUNT = RandomFight.getProperty("RewardCount", 5);
			
			// Hide Event
			ExProperties Hide = load(HIDE_FILE);
			HIDE_EVENT = Hide.getProperty("HideEvent", false);
			HIDE_EVENT_REWARD_ID = Hide.getProperty("HideEventRewardId", 57);
			HIDE_EVENT_REWARD_COUNT = Hide.getProperty("HideEventRewardCount", 1000);
			HIDE_EVENT_ITEM_WILL_DROP = Hide.getProperty("HideEventItemWillDrop", 2807);
			HIDE_EVENT_DELAY_BEFORE_START = Hide.getProperty("HideEventDelayBeforeStart", 180000);
			
			// Super Monster
			ExProperties SuperMonster = load(SUPERMONSTER_FILE);
			ENABLE_SUPER_MONSTER = SuperMonster.getProperty("EnableSuperMonster", false);
			SUPER_MONSTERS = SuperMonster.getProperty("SuperMonsters");
			
			SUPER_MONSTERS_IDS = new ArrayList<>();
			String[] arrayOfString1 = SUPER_MONSTERS.split(",");
			int i = arrayOfString1.length;
			int str1;
			for (str1 = 0; str1 < i; str1++)
			{
				String id = arrayOfString1[str1];
				SUPER_MONSTERS_IDS.add(Integer.valueOf(Integer.parseInt(id)));
			}
			SM_REWARD_PARTY = SuperMonster.getProperty("RewardParty", false);
			SM_REWARD_PARTY_NOBLE = SuperMonster.getProperty("GiveNoblesseFullParty", false);
			SM_REWARD_PARTY_HERO = SuperMonster.getProperty("GiveHeroFullParty", false);
			SM_GIVE_NOBLE = SuperMonster.getProperty("GiveNoblesse", false);
			SM_GIVE_HERO = SuperMonster.getProperty("GiveHero", false);
			SM_GIVE_ITEM = SuperMonster.getProperty("GiveItemReward", false);
			
			String[] smReward = SuperMonster.getProperty("ItemRewards", "57,100000").split(";");
			SM_ITEM_REWARD = new ArrayList<>();
			String[] arrayOfString2 = smReward;
			str1 = arrayOfString2.length;
			for (int id = 0; id < str1; id++)
			{
				String reward = arrayOfString2[id];
				
				String[] rewardSplit = reward.split(",");
				if (rewardSplit.length != 2)
				{
					_log.warning(StringUtil.concat(new String[]
					{
						"[Config.load()]: invalid config property -> ItemRewards \"",
						reward,
						"\""
					}));
				}
				else
				{
					try
					{
						SM_ITEM_REWARD.add(new int[]
						{
							
							Integer.parseInt(rewardSplit[0]),
							Integer.parseInt(rewardSplit[1])
						});
					}
					catch (NumberFormatException nfe)
					{
						if (!reward.isEmpty())
						{
							_log.warning(StringUtil.concat(new String[]
							{
								"[Config.load()]: invalid config property -> ItemRewards \"",
								reward,
								"\""
							}));
						}
					}
				}
			}
			
			// Strider
			ExProperties Strider = load(STRIDER_FILE);
			STRIDER_RACE_X = Strider.getProperty("StriderRacePlayersSpawnX", 82884);
			STRIDER_RACE_Y = Strider.getProperty("StriderRacePlayersSpawnY", 148594);
			STRIDER_RACE_Z = Strider.getProperty("StriderRacePlayersSpawnZ", -3472);
			STRIDER_RACE_ENDNPCID = Strider.getProperty("StriderRaceEndNpcId", 65534);
			STRIDER_RACE_ENDNPC_SPAWN_X = Strider.getProperty("StriderRaceEndNpsSpawnX", 82884);
			STRIDER_RACE_ENDNPC_SPAWN_Y = Strider.getProperty("StriderRaceEndNpsSpawnY", 148594);
			STRIDER_RACE_ENDNPC_SPAWN_Z = Strider.getProperty("StriderRaceEndNpsSpawnZ", -3472);
			ALLOW_STRIDER_RACE_EVENT = Strider.getProperty("AllowStriderRaceEvent", false);
			STRIDER_RACE_DELAY = Strider.getProperty("StriderRaceDelay", 120);
			STRIDER_RACE_REGISTRATION_TIME = Strider.getProperty("StriderRaceRegistrationTime", 10);
			STRIDER_RACE_RUNNING_TIME = Strider.getProperty("StriderRaceRunningTime", 5);
			
			String strider_race_winner_rewards = Strider.getProperty("StriderRaceWinnerRewards", "57,1000000;3470,5;");
			String[] strider_race_winner_rewards_splitted = strider_race_winner_rewards.split(";");
			for (String s : strider_race_winner_rewards_splitted)
			{
				String[] strider_race_winner_rewards_splitted_2 = s.split(",");
				STRIDER_RACE_WINNER_REWARDS.put(Integer.parseInt(strider_race_winner_rewards_splitted_2[0]), Integer.parseInt(strider_race_winner_rewards_splitted_2[1]));
			}
			// Olympiad
			ExProperties Olympiad = load(OLYMPIAD_FILE);
			ALT_OLY_WEEK = Olympiad.getProperty("AltOlyWeek", false);
			ALT_OLY_DAY = Olympiad.getProperty("AltDayHero", 1);
			ALT_OLY_START_TIME = Olympiad.getProperty("AltOlyStartTime", 18);
			ALT_OLY_MIN = Olympiad.getProperty("AltOlyMin", 0);
			ALT_OLY_CPERIOD = Olympiad.getProperty("AltOlyCPeriod", 21600000);
			ALT_OLY_BATTLE = Olympiad.getProperty("AltOlyBattle", 180000);
			ALT_OLY_WPERIOD = Olympiad.getProperty("AltOlyWPeriod", 604800000);
			ALT_OLY_VPERIOD = Olympiad.getProperty("AltOlyVPeriod", 86400000);
			ALT_OLY_WAIT_TIME = Olympiad.getProperty("AltOlyWaitTime", 30);
			ALT_OLY_WAIT_BATTLE = Olympiad.getProperty("AltOlyWaitBattle", 60);
			ALT_OLY_WAIT_END = Olympiad.getProperty("AltOlyWaitEnd", 40);
			ALT_OLY_START_POINTS = Olympiad.getProperty("AltOlyStartPoints", 18);
			ALT_OLY_WEEKLY_POINTS = Olympiad.getProperty("AltOlyWeeklyPoints", 3);
			ALT_OLY_MIN_MATCHES = Olympiad.getProperty("AltOlyMinMatchesToBeClassed", 5);
			ALT_OLY_CLASSED = Olympiad.getProperty("AltOlyClassedParticipants", 5);
			ALT_OLY_NONCLASSED = Olympiad.getProperty("AltOlyNonClassedParticipants", 9);
			ALT_OLY_CLASSED_REWARD = parseItemsList(Olympiad.getProperty("AltOlyClassedReward", "6651,50"));
			ALT_OLY_NONCLASSED_REWARD = parseItemsList(Olympiad.getProperty("AltOlyNonClassedReward", "6651,30"));
			ALT_OLY_COMP_RITEM = Olympiad.getProperty("AltOlyCompRewItem", 6651);
			ALT_OLY_GP_PER_POINT = Olympiad.getProperty("AltOlyGPPerPoint", 1000);
			ALT_OLY_HERO_POINTS = Olympiad.getProperty("AltOlyHeroPoints", 300);
			ALT_OLY_RANK1_POINTS = Olympiad.getProperty("AltOlyRank1Points", 100);
			ALT_OLY_RANK2_POINTS = Olympiad.getProperty("AltOlyRank2Points", 75);
			ALT_OLY_RANK3_POINTS = Olympiad.getProperty("AltOlyRank3Points", 55);
			ALT_OLY_RANK4_POINTS = Olympiad.getProperty("AltOlyRank4Points", 40);
			ALT_OLY_RANK5_POINTS = Olympiad.getProperty("AltOlyRank5Points", 30);
			ALT_OLY_MAX_POINTS = Olympiad.getProperty("AltOlyMaxPoints", 10);
			ALT_OLY_DIVIDER_CLASSED = Olympiad.getProperty("AltOlyDividerClassed", 3);
			ALT_OLY_DIVIDER_NON_CLASSED = Olympiad.getProperty("AltOlyDividerNonClassed", 3);
			ALT_OLY_ANNOUNCE_GAMES = Olympiad.getProperty("AltOlyAnnounceGames", true);
			DISABLE_OLYMPIAD_CHAT = Olympiad.getProperty("DisableOlympiadChat", false);
			BLOCK_AUGMENT_SKILLS_IN_OLY = Olympiad.getProperty("BlockAugmentSkillsInOly", false);
			OLY_SKILL_PROTECT = Olympiad.getProperty("OlySkillProtect", false);
			for (String id : Olympiad.getProperty("OllySkillId", "0").split(","))
			{
				OLY_SKILL_LIST.add(Integer.parseInt(id));
			}
			
			// Small Events
			ExProperties SmallEvents = load(SMALLEVENTS_FILE);
			ALT_GAME_REQUIRE_CLAN_CASTLE = SmallEvents.getProperty("AltRequireClanCastle", false);
			ALT_GAME_CASTLE_DAWN = SmallEvents.getProperty("AltCastleForDawn", true);
			ALT_GAME_CASTLE_DUSK = SmallEvents.getProperty("AltCastleForDusk", true);
			ALT_FESTIVAL_MIN_PLAYER = SmallEvents.getProperty("AltFestivalMinPlayer", 5);
			ALT_MAXIMUM_PLAYER_CONTRIB = SmallEvents.getProperty("AltMaxPlayerContrib", 1000000);
			ALT_FESTIVAL_MANAGER_START = SmallEvents.getProperty("AltFestivalManagerStart", 120000);
			ALT_FESTIVAL_LENGTH = SmallEvents.getProperty("AltFestivalLength", 1080000);
			ALT_FESTIVAL_CYCLE_LENGTH = SmallEvents.getProperty("AltFestivalCycleLength", 2280000);
			ALT_FESTIVAL_FIRST_SPAWN = SmallEvents.getProperty("AltFestivalFirstSpawn", 120000);
			ALT_FESTIVAL_FIRST_SWARM = SmallEvents.getProperty("AltFestivalFirstSwarm", 300000);
			ALT_FESTIVAL_SECOND_SPAWN = SmallEvents.getProperty("AltFestivalSecondSpawn", 540000);
			ALT_FESTIVAL_SECOND_SWARM = SmallEvents.getProperty("AltFestivalSecondSwarm", 720000);
			ALT_FESTIVAL_CHEST_SPAWN = SmallEvents.getProperty("AltFestivalChestSpawn", 900000);
			ALT_SEVENSIGNS_LAZY_UPDATE = SmallEvents.getProperty("AltSevenSignsLazyUpdate", true);
			
			FS_TIME_ATTACK = SmallEvents.getProperty("TimeOfAttack", 50);
			FS_TIME_COOLDOWN = SmallEvents.getProperty("TimeOfCoolDown", 5);
			FS_TIME_ENTRY = SmallEvents.getProperty("TimeOfEntry", 3);
			FS_TIME_WARMUP = SmallEvents.getProperty("TimeOfWarmUp", 2);
			FS_PARTY_MEMBER_COUNT = SmallEvents.getProperty("NumberOfNecessaryPartyMembers", 4);
			
			RIFT_MIN_PARTY_SIZE = SmallEvents.getProperty("RiftMinPartySize", 2);
			RIFT_MAX_JUMPS = SmallEvents.getProperty("MaxRiftJumps", 4);
			RIFT_SPAWN_DELAY = SmallEvents.getProperty("RiftSpawnDelay", 10000);
			RIFT_AUTO_JUMPS_TIME_MIN = SmallEvents.getProperty("AutoJumpsDelayMin", 480);
			RIFT_AUTO_JUMPS_TIME_MAX = SmallEvents.getProperty("AutoJumpsDelayMax", 600);
			RIFT_ENTER_COST_RECRUIT = SmallEvents.getProperty("RecruitCost", 18);
			RIFT_ENTER_COST_SOLDIER = SmallEvents.getProperty("SoldierCost", 21);
			RIFT_ENTER_COST_OFFICER = SmallEvents.getProperty("OfficerCost", 24);
			RIFT_ENTER_COST_CAPTAIN = SmallEvents.getProperty("CaptainCost", 27);
			RIFT_ENTER_COST_COMMANDER = SmallEvents.getProperty("CommanderCost", 30);
			RIFT_ENTER_COST_HERO = SmallEvents.getProperty("HeroCost", 33);
			RIFT_BOSS_ROOM_TIME_MUTIPLY = SmallEvents.getProperty("BossRoomTimeMultiply", 1.);
			
			ALT_LOTTERY_PRIZE = SmallEvents.getProperty("AltLotteryPrize", 50000);
			ALT_LOTTERY_TICKET_PRICE = SmallEvents.getProperty("AltLotteryTicketPrice", 2000);
			ALT_LOTTERY_5_NUMBER_RATE = SmallEvents.getProperty("AltLottery5NumberRate", 0.6);
			ALT_LOTTERY_4_NUMBER_RATE = SmallEvents.getProperty("AltLottery4NumberRate", 0.2);
			ALT_LOTTERY_3_NUMBER_RATE = SmallEvents.getProperty("AltLottery3NumberRate", 0.2);
			ALT_LOTTERY_2_AND_1_NUMBER_PRIZE = SmallEvents.getProperty("AltLottery2and1NumberPrize", 200);
			
			ALT_FISH_CHAMPIONSHIP_ENABLED = SmallEvents.getProperty("AltFishChampionshipEnabled", true);
			ALT_FISH_CHAMPIONSHIP_REWARD_ITEM = SmallEvents.getProperty("AltFishChampionshipRewardItemId", 57);
			ALT_FISH_CHAMPIONSHIP_REWARD_1 = SmallEvents.getProperty("AltFishChampionshipReward1", 800000);
			ALT_FISH_CHAMPIONSHIP_REWARD_2 = SmallEvents.getProperty("AltFishChampionshipReward2", 500000);
			ALT_FISH_CHAMPIONSHIP_REWARD_3 = SmallEvents.getProperty("AltFishChampionshipReward3", 300000);
			ALT_FISH_CHAMPIONSHIP_REWARD_4 = SmallEvents.getProperty("AltFishChampionshipReward4", 200000);
			ALT_FISH_CHAMPIONSHIP_REWARD_5 = SmallEvents.getProperty("AltFishChampionshipReward5", 100000);
			
			// FloodProtector
			ExProperties security = load(FLOOD_PROTECTOR_FILE);
			loadFloodProtectorConfig(security, FLOOD_PROTECTOR_ROLL_DICE, "RollDice", "42");
			loadFloodProtectorConfig(security, FLOOD_PROTECTOR_HERO_VOICE, "HeroVoice", "100");
			loadFloodProtectorConfig(security, FLOOD_PROTECTOR_SUBCLASS, "Subclass", "20");
			loadFloodProtectorConfig(security, FLOOD_PROTECTOR_DROP_ITEM, "DropItem", "10");
			loadFloodProtectorConfig(security, FLOOD_PROTECTOR_SERVER_BYPASS, "ServerBypass", "5");
			loadFloodProtectorConfig(security, FLOOD_PROTECTOR_MULTISELL, "MultiSell", "1");
			loadFloodProtectorConfig(security, FLOOD_PROTECTOR_MANUFACTURE, "Manufacture", "3");
			loadFloodProtectorConfig(security, FLOOD_PROTECTOR_MANOR, "Manor", "30");
			loadFloodProtectorConfig(security, FLOOD_PROTECTOR_SENDMAIL, "SendMail", "100");
			loadFloodProtectorConfig(security, FLOOD_PROTECTOR_CHARACTER_SELECT, "CharacterSelect", "30");
			loadFloodProtectorConfig(security, FLOOD_PROTECTOR_MACRO, "Macro", "10");
			
			// HexID
			ExProperties hexid = load(HEXID_FILE);
			SERVER_ID = Integer.parseInt(hexid.getProperty("ServerID"));
			HEX_ID = new BigInteger(hexid.getProperty("HexID"), 16).toByteArray();
			
			// Bosses Folder
			// AA Main Bosses Configs
			ExProperties AA = load(AA_FILE);
			RAID_HP_REGEN_MULTIPLIER = AA.getProperty("RaidHpRegenMultiplier", 1.);
			RAID_MP_REGEN_MULTIPLIER = AA.getProperty("RaidMpRegenMultiplier", 1.);
			RAID_MINION_RESPAWN_TIMER = AA.getProperty("RaidMinionRespawnTime", 300000);
			
			ANNOUNCE_TO_ALL_SPAWN_RB = AA.getProperty("AnnounceToAllSpawnRb", false);
			
			RAID_DISABLE_CURSE = AA.getProperty("DisableRaidCurse", false);
			RAID_CHAOS_TIME = AA.getProperty("RaidChaosTime", 30);
			GRAND_CHAOS_TIME = AA.getProperty("GrandChaosTime", 30);
			MINION_CHAOS_TIME = AA.getProperty("MinionChaosTime", 30);
			
			// AntQueen
			ExProperties AntQueen = load(ANTQUEEN_FILE);
			SPAWN_INTERVAL_AQ = AntQueen.getProperty("AntQueenSpawnInterval", 36);
			RANDOM_SPAWN_TIME_AQ = AntQueen.getProperty("AntQueenRandomSpawn", 17);
			
			// Antharas
			ExProperties Antharas = load(ANTHARAS_FILE);
			SPAWN_INTERVAL_ANTHARAS = Antharas.getProperty("AntharasSpawnInterval", 264);
			RANDOM_SPAWN_TIME_ANTHARAS = Antharas.getProperty("AntharasRandomSpawn", 72);
			WAIT_TIME_ANTHARAS = Antharas.getProperty("AntharasWaitTime", 30) * 60000;
			
			// Baium
			ExProperties Baium = load(BAIUM_FILE);
			SPAWN_INTERVAL_BAIUM = Baium.getProperty("BaiumSpawnInterval", 168);
			RANDOM_SPAWN_TIME_BAIUM = Baium.getProperty("BaiumRandomSpawn", 48);
			
			// Core
			ExProperties Core = load(CORE_FILE);
			SPAWN_INTERVAL_CORE = Core.getProperty("CoreSpawnInterval", 60);
			RANDOM_SPAWN_TIME_CORE = Core.getProperty("CoreRandomSpawn", 23);
			
			// Frintezza
			ExProperties Frintezza = load(FRINTEZZA_FILE);
			SPAWN_INTERVAL_FRINTEZZA = Frintezza.getProperty("FrintezzaSpawnInterval", 48);
			RANDOM_SPAWN_TIME_FRINTEZZA = Frintezza.getProperty("FrintezzaRandomSpawn", 8);
			WAIT_TIME_FRINTEZZA = Frintezza.getProperty("FrintezzaWaitTime", 1) * 60000;
			
			// Orfen
			ExProperties Orfen = load(ORFEN_FILE);
			SPAWN_INTERVAL_ORFEN = Orfen.getProperty("OrfenSpawnInterval", 48);
			RANDOM_SPAWN_TIME_ORFEN = Orfen.getProperty("OrfenRandomSpawn", 20);
			
			// Sailren
			ExProperties Sailren = load(SAILREN_FILE);
			SPAWN_INTERVAL_SAILREN = Sailren.getProperty("SailrenSpawnInterval", 36);
			RANDOM_SPAWN_TIME_SAILREN = Sailren.getProperty("SailrenRandomSpawn", 24);
			WAIT_TIME_SAILREN = Sailren.getProperty("SailrenWaitTime", 5) * 60000;
			
			// Valakas
			ExProperties Valakas = load(VALAKAS_FILE);
			SPAWN_INTERVAL_VALAKAS = Valakas.getProperty("ValakasSpawnInterval", 264);
			RANDOM_SPAWN_TIME_VALAKAS = Valakas.getProperty("ValakasRandomSpawn", 72);
			WAIT_TIME_VALAKAS = Valakas.getProperty("ValakasWaitTime", 30) * 60000;
			
			// Zaken
			ExProperties Zaken = load(ZAKEN_FILE);
			SPAWN_INTERVAL_ZAKEN = Zaken.getProperty("ZakenSpawnInterval", 60);
			RANDOM_SPAWN_TIME_ZAKEN = Zaken.getProperty("ZakenRandomSpawn", 20);
			
			// NPCs / Monsters
			ExProperties npcs = load(NPCS_FILE);
			
			ALLOW_CLASS_MASTERS = npcs.getProperty("AllowClassMasters", false);
			ALLOW_ENTIRE_TREE = npcs.getProperty("AllowEntireTree", false);
			if (ALLOW_CLASS_MASTERS)
				CLASS_MASTER_SETTINGS = new ClassMasterSettings(npcs.getProperty("ConfigClassMaster"));
			
			ALT_GAME_FREE_TELEPORT = npcs.getProperty("AltFreeTeleporting", false);
			ANNOUNCE_MAMMON_SPAWN = npcs.getProperty("AnnounceMammonSpawn", true);
			ALT_MOB_AGRO_IN_PEACEZONE = npcs.getProperty("AltMobAgroInPeaceZone", true);
			SHOW_NPC_LVL = npcs.getProperty("ShowNpcLevel", false);
			SHOW_NPC_CREST = npcs.getProperty("ShowNpcCrest", false);
			SHOW_SUMMON_CREST = npcs.getProperty("ShowSummonCrest", false);
			
			GUARD_ATTACK_AGGRO_MOB = npcs.getProperty("GuardAttackAggroMob", false);
			MAX_DRIFT_RANGE = npcs.getProperty("MaxDriftRange", 300);
			KNOWNLIST_UPDATE_INTERVAL = npcs.getProperty("KnownListUpdateInterval", 1250);
			MIN_NPC_ANIMATION = npcs.getProperty("MinNPCAnimation", 20);
			MAX_NPC_ANIMATION = npcs.getProperty("MaxNPCAnimation", 40);
			MIN_MONSTER_ANIMATION = npcs.getProperty("MinMonsterAnimation", 10);
			MAX_MONSTER_ANIMATION = npcs.getProperty("MaxMonsterAnimation", 40);
			
			GRIDS_ALWAYS_ON = npcs.getProperty("GridsAlwaysOn", false);
			GRID_NEIGHBOR_TURNON_TIME = npcs.getProperty("GridNeighborTurnOnTime", 1);
			GRID_NEIGHBOR_TURNOFF_TIME = npcs.getProperty("GridNeighborTurnOffTime", 90);
			
			ENABLE_SKIPPING = npcs.getProperty("EnableSkippingItems", false);
			
			// players
			ExProperties players = load(PLAYERS_FILE);
			ALLOW_CUSTOM_STARTER_ITEMS = players.getProperty("AllowCustomStarterItems", false);
			
			if (ALLOW_CUSTOM_STARTER_ITEMS)
			{
				String[] propertySplit = players.getProperty("CustomStarterItems", "0,0").split(";");
				for (String starteritems : propertySplit)
				{
					String[] starteritemsSplit = starteritems.split(",");
					if (starteritemsSplit.length != 2)
					{
						ALLOW_CUSTOM_STARTER_ITEMS = false;
						System.out.println("StarterItems[Config.load()]: invalid config property -> starter items \"" + starteritems + "\"");
					}
					else
					{
						try
						{
							CUSTOM_STARTER_ITEMS.add(new int[]
							{
								Integer.valueOf(starteritemsSplit[0]),
								Integer.valueOf(starteritemsSplit[1])
							});
						}
						catch (NumberFormatException nfe)
						{
							if (!starteritems.equals(""))
							{
								ALLOW_CUSTOM_STARTER_ITEMS = false;
								System.out.println("StarterItems[Config.load()]: invalid config property -> starter items \"" + starteritems + "\"");
							}
						}
					}
				}
			}
			AVAILABLE_GMS.clear();
			String avGMS[] = players.getProperty("AvailableGMs", "").split(";");
			for (String GM : avGMS) {
				AVAILABLE_GMS.add(Integer.parseInt(GM));
			}
			STARTING_ADENA = players.getProperty("StartingAdena", 100);
			EFFECT_CANCELING = players.getProperty("CancelLesserEffect", true);
			HP_REGEN_MULTIPLIER = players.getProperty("HpRegenMultiplier", 1.);
			MP_REGEN_MULTIPLIER = players.getProperty("MpRegenMultiplier", 1.);
			CP_REGEN_MULTIPLIER = players.getProperty("CpRegenMultiplier", 1.);
			PLAYER_FAKEDEATH_UP_PROTECTION = players.getProperty("PlayerFakeDeathUpProtection", 0);
			RESPAWN_RESTORE_HP = players.getProperty("RespawnRestoreHP", 0.7);
			MAX_PVTSTORE_SLOTS_DWARF = players.getProperty("MaxPvtStoreSlotsDwarf", 5);
			MAX_PVTSTORE_SLOTS_OTHER = players.getProperty("MaxPvtStoreSlotsOther", 4);
			DEEPBLUE_DROP_RULES = players.getProperty("UseDeepBlueDropRules", true);
			ALT_GAME_DELEVEL = players.getProperty("Delevel", true);
			DISABLE_LOST_EXP = players.getProperty("DisableLostExp", false);
			DEATH_PENALTY_CHANCE = players.getProperty("DeathPenaltyChance", 20);
			
			INVENTORY_MAXIMUM_NO_DWARF = players.getProperty("MaximumSlotsForNoDwarf", 80);
			INVENTORY_MAXIMUM_DWARF = players.getProperty("MaximumSlotsForDwarf", 100);
			INVENTORY_MAXIMUM_QUEST_ITEMS = players.getProperty("MaximumSlotsForQuestItems", 100);
			INVENTORY_MAXIMUM_PET = players.getProperty("MaximumSlotsForPet", 12);
			MAX_ITEM_IN_PACKET = Math.max(INVENTORY_MAXIMUM_NO_DWARF, INVENTORY_MAXIMUM_DWARF);
			ALT_WEIGHT_LIMIT = players.getProperty("AltWeightLimit", 1);
			WAREHOUSE_SLOTS_NO_DWARF = players.getProperty("MaximumWarehouseSlotsForNoDwarf", 100);
			WAREHOUSE_SLOTS_DWARF = players.getProperty("MaximumWarehouseSlotsForDwarf", 120);
			WAREHOUSE_SLOTS_CLAN = players.getProperty("MaximumWarehouseSlotsForClan", 150);
			FREIGHT_SLOTS = players.getProperty("MaximumFreightSlots", 20);
			ALT_GAME_FREIGHTS = players.getProperty("AltGameFreights", false);
			ALT_GAME_FREIGHT_PRICE = players.getProperty("AltGameFreightPrice", 1000);
			
			IS_CRAFTING_ENABLED = players.getProperty("CraftingEnabled", true);
			DWARF_RECIPE_LIMIT = players.getProperty("DwarfRecipeLimit", 50);
			COMMON_RECIPE_LIMIT = players.getProperty("CommonRecipeLimit", 50);
			ALT_BLACKSMITH_USE_RECIPES = players.getProperty("AltBlacksmithUseRecipes", true);
			
			AUTO_LEARN_SKILLS = players.getProperty("AutoLearnSkills", false);
			ALT_GAME_MAGICFAILURES = players.getProperty("MagicFailures", true);
			ALT_GAME_SHIELD_BLOCKS = players.getProperty("AltShieldBlocks", false);
			ALT_PERFECT_SHLD_BLOCK = players.getProperty("AltPerfectShieldBlockRate", 10);
			LIFE_CRYSTAL_NEEDED = players.getProperty("LifeCrystalNeeded", true);
			SP_BOOK_NEEDED = players.getProperty("SpBookNeeded", true);
			ES_SP_BOOK_NEEDED = players.getProperty("EnchantSkillSpBookNeeded", true);
			DIVINE_SP_BOOK_NEEDED = players.getProperty("DivineInspirationSpBookNeeded", true);
			ALT_GAME_SUBCLASS_WITHOUT_QUESTS = players.getProperty("AltSubClassWithoutQuests", false);
			AUTODELETE_INVALID_QUEST_DATA = players.getProperty("AutoDeleteInvalidQuestData", false);
			DELETE_DAYS = players.getProperty("DeleteCharAfterDays", 7);

			CLASS_BALANCER_UPDATE_DELAY = players.getProperty("ClassBalancerUpdateDelay", 300) * 1000;
			CLASS_BALANCER_AFFECTS_SECOND_PROFFESION = players.getProperty("ClassBalancerAffectSecondProffesion", false);
			CLASS_BALANCER_AFFECTS_MONSTERS = players.getProperty("ClassBalancerAffectMonsters", false);

			SKILLS_BALANCER_UPDATE_DELAY = players.getProperty("SkillsBalancerUpdateDelay", 300) * 1000;
			SKILLS_BALANCER_AFFECTS_SECOND_PROFFESION = players.getProperty("SkillsBalancerAffectSecondProffesion", false);
			SKILLS_BALANCER_AFFECTS_MONSTERS = players.getProperty("SkillsBalancerAffectMonsters", false);
			
			
			// Custom Config Siege
			ExProperties CustomSiegeConfig = load(CUSTOM_SIEGE_FILE);
			/** Siege day of each castle */
			// Gludio
			SIEGEDAYCASTLEGludio = Integer.parseInt(CustomSiegeConfig.getProperty("SiegeGludio", "7"));
			// Dion			
			SIEGEDAYCASTLEDion = Integer.parseInt(CustomSiegeConfig.getProperty("SiegeDion", "7"));
			// Giran
			SIEGEDAYCASTLEGiran = Integer.parseInt(CustomSiegeConfig.getProperty("SiegeGiran", "7"));
			// Oren
			SIEGEDAYCASTLEOren = Integer.parseInt(CustomSiegeConfig.getProperty("SiegeOren", "7"));
			// Aden
			SIEGEDAYCASTLEAden = Integer.parseInt(CustomSiegeConfig.getProperty("SiegeAden", "1"));
			// Innadril/Heine
			SIEGEDAYCASTLEInnadril = Integer.parseInt(CustomSiegeConfig.getProperty("SiegeInnadril", "1"));
			// Goddard
			SIEGEDAYCASTLEGoddard = Integer.parseInt(CustomSiegeConfig.getProperty("SiegeGoddard", "1"));
			// Rune
			SIEGEDAYCASTLERune = Integer.parseInt(CustomSiegeConfig.getProperty("SiegeRune", "1"));
			// Schuttgart
			SIEGEDAYCASTLESchuttgart = Integer.parseInt(CustomSiegeConfig.getProperty("SiegeSchuttgart", "1"));
			/** Next siege time config (Retail 2)*/
			NEXT_SIEGE_TIME = Integer.parseInt(CustomSiegeConfig.getProperty("NextSiegeTime", "2"));
			/** Hour of the siege will start*/
			/** Siege time of each castle */
			// Gludio
			SIEGEHOURCASTLEGludio = Integer.parseInt(CustomSiegeConfig.getProperty("HourGludio", "18"));
			// Dion			
			SIEGEHOURCASTLEDion = Integer.parseInt(CustomSiegeConfig.getProperty("HourDion", "18"));
			// Giran
			SIEGEHOURCASTLEGiran = Integer.parseInt(CustomSiegeConfig.getProperty("HourGiran", "18"));
			// Oren
			SIEGEHOURCASTLEOren = Integer.parseInt(CustomSiegeConfig.getProperty("HourOren", "18"));
			// Aden
			SIEGEHOURCASTLEAden = Integer.parseInt(CustomSiegeConfig.getProperty("HourAden", "18"));
			// Innadril/Heine
			SIEGEHOURCASTLEInnadril = Integer.parseInt(CustomSiegeConfig.getProperty("HourInnadril", "18"));
			// Goddard
			SIEGEHOURCASTLEGoddard = Integer.parseInt(CustomSiegeConfig.getProperty("HourGoddard", "18"));
			// Rune
			SIEGEHOURCASTLERune = Integer.parseInt(CustomSiegeConfig.getProperty("HourRune", "18"));
			// Schuttgart
			SIEGEHOURCASTLESchuttgart = Integer.parseInt(CustomSiegeConfig.getProperty("HourSchuttgart", "18"));
			
			// server
			ExProperties server = load(SERVER_FILE);
			
			GAMESERVER_HOSTNAME = server.getProperty("GameserverHostname");
			PORT_GAME = server.getProperty("GameserverPort", 7777);
			
			EXTERNAL_HOSTNAME = server.getProperty("ExternalHostname", "*");
			INTERNAL_HOSTNAME = server.getProperty("InternalHostname", "*");
			
			GAME_SERVER_LOGIN_PORT = server.getProperty("LoginPort", 9014);
			GAME_SERVER_LOGIN_HOST = server.getProperty("LoginHost", "127.0.0.1");
			
			REQUEST_ID = server.getProperty("RequestServerID", 0);
			ACCEPT_ALTERNATE_ID = server.getProperty("AcceptAlternateID", true);
			
			GAMESERVERSTATUS = server.getProperty("GameServerStatus", false);
			
			DATABASE_URL = server.getProperty("URL", "jdbc:mysql://localhost/l2jxtreme");
			DATABASE_LOGIN = server.getProperty("Login", "root");
			DATABASE_PASSWORD = server.getProperty("Password", "");
			DATABASE_MAX_CONNECTIONS = server.getProperty("MaximumDbConnections", 10);
			DATABASE_MAX_IDLE_TIME = server.getProperty("MaximumDbIdleTime", 0);
			
			SERVER_LIST_BRACKET = server.getProperty("ServerListBrackets", false);
			SERVER_LIST_CLOCK = server.getProperty("ServerListClock", false);
			SERVER_GMONLY = server.getProperty("ServerGMOnly", false);
			TEST_SERVER = server.getProperty("TestServer", false);
			SERVER_LIST_TESTSERVER = server.getProperty("TestServer", false);
			
			MAXIMUM_ONLINE_USERS = server.getProperty("MaximumOnlineUsers", 100);
			MIN_PROTOCOL_REVISION = server.getProperty("MinProtocolRevision", 730);
			MAX_PROTOCOL_REVISION = server.getProperty("MaxProtocolRevision", 746);
			if (MIN_PROTOCOL_REVISION > MAX_PROTOCOL_REVISION)
				throw new Error("MinProtocolRevision is bigger than MaxProtocolRevision in server.properties.");
			
			AUTO_LOOT = server.getProperty("AutoLoot", false);
			AUTO_LOOT_HERBS = server.getProperty("AutoLootHerbs", false);
			AUTO_LOOT_RAID = server.getProperty("AutoLootRaid", false);
			
			ALLOW_DISCARDITEM = server.getProperty("AllowDiscardItem", true);
			MULTIPLE_ITEM_DROP = server.getProperty("MultipleItemDrop", true);
			ITEM_AUTO_DESTROY_TIME = server.getProperty("AutoDestroyItemTime", 0) * 1000;
			HERB_AUTO_DESTROY_TIME = server.getProperty("AutoDestroyHerbTime", 15) * 1000;
			PROTECTED_ITEMS = server.getProperty("ListOfProtectedItems");
			
			LIST_PROTECTED_ITEMS = new ArrayList<>();
			for (String id : PROTECTED_ITEMS.split(","))
				LIST_PROTECTED_ITEMS.add(Integer.parseInt(id));
			
			DESTROY_DROPPED_PLAYER_ITEM = server.getProperty("DestroyPlayerDroppedItem", false);
			DESTROY_EQUIPABLE_PLAYER_ITEM = server.getProperty("DestroyEquipableItem", false);
			SAVE_DROPPED_ITEM = server.getProperty("SaveDroppedItem", false);
			EMPTY_DROPPED_ITEM_TABLE_AFTER_LOAD = server.getProperty("EmptyDroppedItemTableAfterLoad", false);
			SAVE_DROPPED_ITEM_INTERVAL = server.getProperty("SaveDroppedItemInterval", 0) * 60000;
			CLEAR_DROPPED_ITEM_TABLE = server.getProperty("ClearDroppedItemTable", false);
			
			ALLOW_FREIGHT = server.getProperty("AllowFreight", true);
			ALLOW_WAREHOUSE = server.getProperty("AllowWarehouse", true);
			ALLOW_WEAR = server.getProperty("AllowWear", true);
			WEAR_DELAY = server.getProperty("WearDelay", 5);
			WEAR_PRICE = server.getProperty("WearPrice", 10);
			ALLOW_LOTTERY = server.getProperty("AllowLottery", true);
			ALLOW_RACE = server.getProperty("AllowRace", true);
			ALLOW_WATER = server.getProperty("AllowWater", true);
			ALLOWFISHING = server.getProperty("AllowFishing", false);
			ALLOW_MANOR = server.getProperty("AllowManor", true);
			ALLOW_BOAT = server.getProperty("AllowBoat", true);
			ALLOW_CURSED_WEAPONS = server.getProperty("AllowCursedWeapons", true);
			
		}
		else if (Server.serverMode == Server.MODE_LOGINSERVER)
		{
			_log.info("Loading loginserver configuration files.");
			
			ExProperties server = load(LOGIN_CONFIGURATION_FILE);
			GAME_SERVER_LOGIN_HOST = server.getProperty("LoginHostname", "*");
			GAME_SERVER_LOGIN_PORT = server.getProperty("LoginPort", 9013);
			
			LOGIN_BIND_ADDRESS = server.getProperty("LoginserverHostname", "*");
			PORT_LOGIN = server.getProperty("LoginserverPort", 2106);
			
			PACKET_HANDLER_DEBUG = server.getProperty("PacketHandlerDebug", false);
			ACCEPT_NEW_GAMESERVER = server.getProperty("AcceptNewGameServer", true);
			REQUEST_ID = server.getProperty("RequestServerID", 0);
			ACCEPT_ALTERNATE_ID = server.getProperty("AcceptAlternateID", true);
			
			LOGIN_TRY_BEFORE_BAN = server.getProperty("LoginTryBeforeBan", 10);
			LOGIN_BLOCK_AFTER_BAN = server.getProperty("LoginBlockAfterBan", 600);
			
			LOG_LOGIN_CONTROLLER = server.getProperty("LogLoginController", false);
			
			INTERNAL_HOSTNAME = server.getProperty("InternalHostname", "localhost");
			EXTERNAL_HOSTNAME = server.getProperty("ExternalHostname", "localhost");
			
			DATABASE_URL = server.getProperty("URL", "jdbc:mysql://localhost/l2jxtreme");
			DATABASE_LOGIN = server.getProperty("Login", "root");
			DATABASE_PASSWORD = server.getProperty("Password", "");
			DATABASE_MAX_CONNECTIONS = server.getProperty("MaximumDbConnections", 10);
			DATABASE_MAX_IDLE_TIME = server.getProperty("MaximumDbIdleTime", 0);
			
			SHOW_LICENCE = server.getProperty("ShowLicence", true);
			IP_UPDATE_TIME = server.getProperty("IpUpdateTime", 15);
			FORCE_GGAUTH = server.getProperty("ForceGGAuth", false);
			
			AUTO_CREATE_ACCOUNTS = server.getProperty("AutoCreateAccounts", true);
			
			FLOOD_PROTECTION = server.getProperty("EnableFloodProtection", true);
			FAST_CONNECTION_LIMIT = server.getProperty("FastConnectionLimit", 15);
			NORMAL_CONNECTION_TIME = server.getProperty("NormalConnectionTime", 700);
			FAST_CONNECTION_TIME = server.getProperty("FastConnectionTime", 350);
			MAX_CONNECTION_PER_IP = server.getProperty("MaxConnectionPerIP", 50);
		}
		else
			_log.severe("Couldn't load configs: server mode wasn't set.");
	}
	
	// It has no instances
	private Config()
	{
	}
	
	public static void saveHexid(int serverId, String string)
	{
		Config.saveHexid(serverId, string, HEXID_FILE);
	}
	
	public static void saveHexid(int serverId, String hexId, String fileName)
	{
		try
		{
			Properties hexSetting = new Properties();
			File file = new File(fileName);
			file.createNewFile();
			
			OutputStream out = new FileOutputStream(file);
			hexSetting.setProperty("ServerID", String.valueOf(serverId));
			hexSetting.setProperty("HexID", hexId);
			hexSetting.store(out, "the hexID to auth into login");
			out.close();
		}
		catch (Exception e)
		{
			_log.warning("Failed to save hex id to " + fileName + " file.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads single flood protector configuration.
	 * @param properties L2Properties file reader
	 * @param config flood protector configuration instance
	 * @param configString flood protector configuration string that determines for which flood protector configuration should be read
	 * @param defaultInterval default flood protector interval
	 */
	private static void loadFloodProtectorConfig(final Properties properties, final FloodProtectorConfig config, final String configString, final String defaultInterval)
	{
		config.FLOOD_PROTECTION_INTERVAL = Integer.parseInt(properties.getProperty(StringUtil.concat("FloodProtector", configString, "Interval"), defaultInterval));
		config.LOG_FLOODING = Boolean.parseBoolean(properties.getProperty(StringUtil.concat("FloodProtector", configString, "LogFlooding"), "False"));
		config.PUNISHMENT_LIMIT = Integer.parseInt(properties.getProperty(StringUtil.concat("FloodProtector", configString, "PunishmentLimit"), "0"));
		config.PUNISHMENT_TYPE = properties.getProperty(StringUtil.concat("FloodProtector", configString, "PunishmentType"), "none");
		config.PUNISHMENT_TIME = Integer.parseInt(properties.getProperty(StringUtil.concat("FloodProtector", configString, "PunishmentTime"), "0"));
	}
	
	public static class ClassMasterSettings
	{
		
		private final Map<Integer, Boolean> _allowedClassChange;
		private final Map<Integer, List<ItemHolder>> _claimItems;
		private final Map<Integer, List<ItemHolder>> _rewardItems;
		
		public ClassMasterSettings(String configLine)
		{
			_allowedClassChange = new HashMap<>(3);
			_claimItems = new HashMap<>(3);
			_rewardItems = new HashMap<>(3);
			
			if (configLine != null)
				parseConfigLine(configLine.trim());
		}
		
		private void parseConfigLine(String configLine)
		{
			StringTokenizer st = new StringTokenizer(configLine, ";");
			while (st.hasMoreTokens())
			{
				// Get allowed class change.
				int job = Integer.parseInt(st.nextToken());
				
				_allowedClassChange.put(job, true);
				
				List<ItemHolder> items = new ArrayList<>();
				
				// Parse items needed for class change.
				if (st.hasMoreTokens())
				{
					StringTokenizer st2 = new StringTokenizer(st.nextToken(), "[],");
					while (st2.hasMoreTokens())
					{
						StringTokenizer st3 = new StringTokenizer(st2.nextToken(), "()");
						items.add(new ItemHolder(Integer.parseInt(st3.nextToken()), Integer.parseInt(st3.nextToken())));
					}
				}
				
				// Feed the map, and clean the list.
				_claimItems.put(job, items);
				items = new ArrayList<>();
				
				// Parse gifts after class change.
				if (st.hasMoreTokens())
				{
					StringTokenizer st2 = new StringTokenizer(st.nextToken(), "[],");
					while (st2.hasMoreTokens())
					{
						StringTokenizer st3 = new StringTokenizer(st2.nextToken(), "()");
						items.add(new ItemHolder(Integer.parseInt(st3.nextToken()), Integer.parseInt(st3.nextToken())));
					}
				}
				
				_rewardItems.put(job, items);
			}
		}
		
		public boolean isAllowed(int job)
		{
			if (_allowedClassChange == null)
				return false;
			
			if (_allowedClassChange.containsKey(job))
				return _allowedClassChange.get(job);
			
			return false;
		}
		
		public List<ItemHolder> getRewardItems(int job)
		{
			return _rewardItems.get(job);
		}
		
		public List<ItemHolder> getRequiredItems(int job)
		{
			return _claimItems.get(job);
		}
	}
	
	/**
	 * itemId1,itemNumber1;itemId2,itemNumber2... to the int[n][2] = [itemId1][itemNumber1],[itemId2][itemNumber2]...
	 * @param line
	 * @return an array consisting of parsed items.
	 */
	private static int[][] parseItemsList(String line)
	{
		final String[] propertySplit = line.split(";");
		if (propertySplit.length == 0)
			return null;
		
		int i = 0;
		String[] valueSplit;
		final int[][] result = new int[propertySplit.length][];
		for (String value : propertySplit)
		{
			valueSplit = value.split(",");
			if (valueSplit.length != 2)
			{
				_log.warning(StringUtil.concat("parseItemsList[Config.load()]: invalid entry -> \"", valueSplit[0], "\", should be itemId,itemNumber"));
				return null;
			}
			
			result[i] = new int[2];
			try
			{
				result[i][0] = Integer.parseInt(valueSplit[0]);
			}
			catch (NumberFormatException e)
			{
				_log.warning(StringUtil.concat("parseItemsList[Config.load()]: invalid itemId -> \"", valueSplit[0], "\""));
				return null;
			}
			
			try
			{
				result[i][1] = Integer.parseInt(valueSplit[1]);
			}
			catch (NumberFormatException e)
			{
				_log.warning(StringUtil.concat("parseItemsList[Config.load()]: invalid item number -> \"", valueSplit[1], "\""));
				return null;
			}
			i++;
		}
		return result;
	}
	
	public static ExProperties load(String filename)
	{
		return load(new File(filename));
	}
	
	public static ExProperties load(File file)
	{
		ExProperties result = new ExProperties();
		
		try
		{
			result.load(file);
		}
		catch (IOException e)
		{
			_log.warning("Error loading config : " + file.getName() + "!");
		}
		
		return result;
	}
}