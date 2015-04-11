package net.sf.l2j.gameserver.communitybbs.Manager;

import Extensions.CCB.CastleStatus;
import Extensions.CCB.ClanList;
import Extensions.CCB.HeroeList;
import Extensions.CCB.RaidList;
import Extensions.CCB.ServerStats;
import Extensions.CCB.TopPlayers;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import net.sf.l2j.Config;
import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.GameTimeController;
import net.sf.l2j.gameserver.cache.HtmCache;
import net.sf.l2j.gameserver.instancemanager.GrandBossManager;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.ShowBoard;

public class TopBBSManager extends BaseBBSManager
{
	@Override
	public void parseCmd(String command, L2PcInstance activeChar)
	{
		if (Config.ENABLE_COMMUNITY_BOARD)
		{
			String path = "";
			String filepath = "";
			String content = "";
			
			path = (Config.CustomCB ? "data/html/CommunityBoard/Custom/" : "data/html/CommunityBoard/top/");
			
			if (command.equals("_bbstop") || command.equals("_bbshome"))
			{
				filepath = path + "index.htm";
				content = HtmCache.getInstance().getHtm(filepath);
				separateAndSend(content, activeChar);
			}
			else if (command.startsWith("_bbstop;"))
			{
				StringTokenizer st = new StringTokenizer(command, ";");
				st.nextToken();
				String file = st.nextToken();
				filepath = path + file + ".htm";
				File filecom = new File(filepath);
				if (!(filecom.exists()))
				{
					content = "<html><body><br><br><center>The command " + command + " points to file(" + filepath + ") that NOT exists.</center></body></html>";
					separateAndSend(content, activeChar);
					return;
				}
				content = HtmCache.getInstance().getHtm(filepath);
				
				if (content.isEmpty())
					content = "<html><body><br><br><center>Content Empty: The command " + command + " points to an invalid or empty html file(" + filepath + ").</center></body></html>";
				
				switch (file)
				{
					case "gms": // Online Gamemasters
					{
						content = content.replace("%gmlist%", getOnlineGMs());
					}
						break;
					case "repair": // Character Repair
					{
						content = content.replace("%acc_chars%", getCharList(activeChar));
					}
						break;
					case "toppvp": // Top PvP
					{
						TopPlayers pvp = new TopPlayers(file);
						content = content.replace("%toppvp%", pvp.loadTopList());
					}
						break;
					case "toppk": // Top PK
					{
						TopPlayers pk = new TopPlayers(file);
						content = content.replace("%toppk%", pk.loadTopList());
					}
						break;
					case "toprbrank": // Top RB Players
					{
						TopPlayers raid = new TopPlayers(file);
						content = content.replace("%toprbrank%", raid.loadTopList());
					}
						break;
					case "toponline": // Top Online Players
					{
						TopPlayers online = new TopPlayers(file);
						content = content.replace("%toponline%", online.loadTopList());
					}
						break;
					case "topadena": // Top Adena Owners
					{
						TopPlayers adena = new TopPlayers(file);
						content = content.replace("%topadena%", adena.loadTopList());
					}
						break;
					case "heroes": // Heroes
					{
						HeroeList hr = new HeroeList();
						content = content.replace("%heroelist%", hr.loadHeroeList());
					}
						break;
					case "castle": // Castles Status
					{
						CastleStatus status = new CastleStatus();
						content = content.replace("%castle%", status.loadCastleList());
					}
						break;
					case "boss":// GrandBoss Status
					{
						content = content.replace("%antharas%", bossStatus(29019));
						content = content.replace("%valakas%", bossStatus(29028));
						content = content.replace("%baium%", bossStatus(29001));
						content = content.replace("%antqueen%", bossStatus(29020));
						content = content.replace("%core%", bossStatus(29006));
						content = content.replace("%zaken%", bossStatus(29022));
						content = content.replace("%frintezza%", bossStatus(29045));
					}
						break;
					case "stats": // Server Stats
					{
						ServerStats stats = new ServerStats();
						content = content.replace("%online%", stats.getOnlineCount());
						content = content.replace("%servercapacity%", stats.getOnlineCount() + "/" + Integer.toString(Config.MAXIMUM_ONLINE_USERS));
						content = content.replace("%serverruntime%", getServerRunTime());
						content = content.replace("%stats%", stats.getServerStats());
					}
						break;
					case "accinfo": // Account Info
					{
						content = content.replace("%account%", String.valueOf(activeChar.getAccountName()));
						content = content.replace("%ip%", String.valueOf(activeChar.getClient().getConnection().getInetAddress().getHostAddress()));
						content = content.replace("%name%", String.valueOf(activeChar.getName()));
						content = content.replace("%hero%", String.valueOf(activeChar.isHero() ? "Yes" : "No"));
						content = content.replace("%noble%", String.valueOf(activeChar.isNoble() ? "Yes" : "No"));
						content = content.replace("%level%", String.valueOf(activeChar.getLevel()));
						content = content.replace("%clan%", String.valueOf(activeChar.getClan() != null ? activeChar.getClan() : "Not in clan yet."));// = (String.valueOf(ClanTable.getInstance().getClan(activeChar.getClanId())));
						content = content.replace("%xp%", String.valueOf(activeChar.getExp()));
						content = content.replace("%sp%", String.valueOf(activeChar.getSp()));
						content = content.replace("%class%", activeChar.getTemplate().getClassName());
						content = content.replace("%classid%", String.valueOf(activeChar.getClassId()));
						content = content.replace("%currenthp%", String.valueOf((int) activeChar.getCurrentHp()));
						content = content.replace("%maxhp%", String.valueOf(activeChar.getMaxHp()));
						content = content.replace("%currentmp%", String.valueOf((int) activeChar.getCurrentMp()));
						content = content.replace("%maxmp%", String.valueOf(activeChar.getMaxMp()));
						content = content.replace("%currentcp%", String.valueOf((int) activeChar.getCurrentCp()));
						content = content.replace("%maxcp%", String.valueOf(activeChar.getMaxCp()));
						content = content.replace("%currentload%", String.valueOf(activeChar.getCurrentLoad()));
						content = content.replace("%maxload%", String.valueOf(activeChar.getMaxLoad()));
						content = content.replace("%access%", String.valueOf(activeChar.getAccessLevel().getLevel()));
					}
						break;
					
					// Default
					default:
						break;
				
				}
				
				if (file.startsWith("clan"))
				{
					int cid = Integer.parseInt(file.substring(4));
					ClanList cl = new ClanList(cid);
					content = content.replace("%clanlist%", String.valueOf(cl.loadClanList()));
				}
				if (file.startsWith("raid"))
				{
					String rfid = file.substring(4);
					RaidList rd = new RaidList(rfid);
					content = content.replace("%raidlist%", rd.loadRaidList());
				}
				separateAndSend(content, activeChar);
			}
			else
			{
				ShowBoard sb = new ShowBoard("<html><body><br><br><center>the command: " + command + " is not implemented yet</center><br><br></body></html>", "101");
				activeChar.sendPacket(sb);
				activeChar.sendPacket(new ShowBoard(null, "102"));
				activeChar.sendPacket(new ShowBoard(null, "103"));
			}
		}
	}
	
	/**
	 * @return server online time
	 */
	public String getServerRunTime()
	{
		int timeSeconds = (GameTimeController.getInstance().getGameTicks() - 36000) / 10;
		String timeResult = "";
		timeResult = (timeSeconds >= 86400 ? Integer.toString(timeSeconds / 86400) + " Days " + Integer.toString((timeSeconds % 86400) / 3600) + " hours" : Integer.toString(timeSeconds / 3600) + " Hours " + Integer.toString((timeSeconds % 3600) / 60) + " mins");
		return timeResult;
	}
	
	/**
	 * @return online gms
	 */
	private static String getOnlineGMs()
	{
		String msg = "<br>";
		
		L2World.getInstance();
		if (L2World.getAllGMs().isEmpty())
		{
			msg = "There are not Online GMs at this moment!";
		}
		else
		{
			L2World.getInstance();
			for (L2PcInstance player : L2World.getAllGMs())
			{
				msg += player.getName();
				msg += "<br>";
			}
		}
		return msg;
	}
	
	/**
	 * @param bossId
	 * @return
	 */
	private static String bossStatus(int bossId)
	{
		long delay = 0;
		delay = GrandBossManager.getInstance().getStatsSet(bossId).getLong("respawn_time");
		String result = "";
		result = ((delay <= System.currentTimeMillis() ? "<font color=\"9CC300\">Alive</font>" : "<font color=\"ff0000\">Dead</font>"));
		return result;
	}
	
	/**
	 * @param activeChar
	 * @return
	 */
	private static String getCharList(L2PcInstance activeChar)
	{
		String result = "";
		String repCharAcc = activeChar.getAccountName();
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT char_name FROM characters WHERE account_name=?");
			statement.setString(1, repCharAcc);
			ResultSet rset = statement.executeQuery();
			while (rset.next())
			{
				if (activeChar.getName().compareTo(rset.getString(1)) != 0)
					result += rset.getString(1) + ";";
			}
			
			rset.close();
			statement.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return result;
		}
		return result;
	}
	
	@Override
	public void parseWrite(String ar1, String ar2, String ar3, String ar4, String ar5, L2PcInstance activeChar)
	{
		
	}
	
	public static TopBBSManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final TopBBSManager INSTANCE = new TopBBSManager();
	}
}