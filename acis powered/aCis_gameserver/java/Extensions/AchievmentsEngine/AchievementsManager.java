package Extensions.AchievmentsEngine;

import Extensions.AchievmentsEngine.Base.Achievement;
import Extensions.AchievmentsEngine.Base.Condition;
import Extensions.AchievmentsEngine.Conditions.Adena;
import Extensions.AchievmentsEngine.Conditions.Castle;
import Extensions.AchievmentsEngine.Conditions.ClanLeader;
import Extensions.AchievmentsEngine.Conditions.ClanLevel;
import Extensions.AchievmentsEngine.Conditions.CompleteAchievements;
import Extensions.AchievmentsEngine.Conditions.Crp;
import Extensions.AchievmentsEngine.Conditions.Hero;
import Extensions.AchievmentsEngine.Conditions.HeroCount;
import Extensions.AchievmentsEngine.Conditions.ItemsCount;
import Extensions.AchievmentsEngine.Conditions.Karma;
import Extensions.AchievmentsEngine.Conditions.Level;
import Extensions.AchievmentsEngine.Conditions.Mage;
import Extensions.AchievmentsEngine.Conditions.Marry;
import Extensions.AchievmentsEngine.Conditions.MinCMcount;
import Extensions.AchievmentsEngine.Conditions.Noble;
import Extensions.AchievmentsEngine.Conditions.OnlineTime;
import Extensions.AchievmentsEngine.Conditions.Pk;
import Extensions.AchievmentsEngine.Conditions.Pvp;
import Extensions.AchievmentsEngine.Conditions.RaidKill;
import Extensions.AchievmentsEngine.Conditions.RaidPoints;
import Extensions.AchievmentsEngine.Conditions.SkillEnchant;
import Extensions.AchievmentsEngine.Conditions.Sub;
import Extensions.AchievmentsEngine.Conditions.WeaponEnchant;
import Extensions.AchievmentsEngine.Conditions.eventKills;
import Extensions.AchievmentsEngine.Conditions.eventWins;
import Extensions.AchievmentsEngine.Conditions.events;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.xmlfactory.XMLDocumentFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class AchievementsManager
{
	private Map<Integer, Achievement> _achievementList = new HashMap<>();
	
	private ArrayList<String> _binded = new ArrayList<>();
	
	private static Logger _log = Logger.getLogger(AchievementsManager.class.getName());
	
	public AchievementsManager()
	{
		loadAchievements();
	}
	
	private void loadAchievements()
	{
		
		try
		{
			File file = new File("./config/Events/Achievements.xml");
			Document doc = XMLDocumentFactory.getInstance().loadDocument(file);
			
			for (Node n = doc.getFirstChild(); n != null; n = n.getNextSibling())
			{
				if (n.getNodeName().equalsIgnoreCase("list"))
				{
					for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
					{
						if (d.getNodeName().equalsIgnoreCase("achievement"))
						{
							int id = checkInt(d, "id");
							
							String name = String.valueOf(d.getAttributes().getNamedItem("name").getNodeValue());
							String description = String.valueOf(d.getAttributes().getNamedItem("description").getNodeValue());
							String reward = String.valueOf(d.getAttributes().getNamedItem("reward").getNodeValue());
							boolean repeat = checkBoolean(d, "repeatable");
							
							ArrayList<Condition> conditions = conditionList(d.getAttributes());
							
							_achievementList.put(id, new Achievement(id, name, description, reward, repeat, conditions));
							alterTable(id);
						}
					}
				}
			}
			
			_log.info("AchievementsEngine: loaded: " + getAchievementList().size() + " achievements.");
		}
		catch (Exception e)
		{
			_log.warning("AchievementsEngine: Error: " + e);
			e.printStackTrace();
		}
	}
	
	public void rewardForAchievement(int achievementID, L2PcInstance player)
	{
		Achievement achievement = _achievementList.get(achievementID);
		
		for (int id : achievement.getRewardList().keySet())
		{
			int count = achievement.getRewardList().get(id).intValue();
			player.addItem(achievement.getName(), id, count, player, true);
			
		}
	}
	
	private static boolean checkBoolean(Node d, String nodename)
	{
		boolean b = false;
		
		try
		{
			b = Boolean.valueOf(d.getAttributes().getNamedItem(nodename).getNodeValue());
		}
		catch (Exception e)
		{
			
		}
		return b;
	}
	
	private static int checkInt(Node d, String nodename)
	{
		int i = 0;
		
		try
		{
			i = Integer.valueOf(d.getAttributes().getNamedItem(nodename).getNodeValue());
		}
		catch (Exception e)
		{
			
		}
		return i;
	}
	
	/**
	 * Alter table, catch exception if already exist.
	 * @param fieldID
	 */
	private static void alterTable(int fieldID)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			Statement statement = con.createStatement();
			statement.executeUpdate("ALTER TABLE achievements ADD a" + fieldID + " INT DEFAULT 0");
			statement.close();
		}
		catch (SQLException e)
		{
			
		}
	}
	
	public ArrayList<Condition> conditionList(NamedNodeMap attributesList)
	{
		ArrayList<Condition> conditions = new ArrayList<>();
		
		for (int j = 0; j < attributesList.getLength(); j++)
		{
			addToConditionList(attributesList.item(j).getNodeName(), attributesList.item(j).getNodeValue(), conditions);
		}
		
		return conditions;
	}
	
	public Map<Integer, Achievement> getAchievementList()
	{
		return _achievementList;
	}
	
	public ArrayList<String> getBinded()
	{
		return _binded;
	}
	
	public boolean isBinded(int obj, int ach)
	{
		for (String binds : _binded)
		{
			String[] spl = binds.split("@");
			if (spl[0].equals(String.valueOf(obj)) && spl[1].equals(String.valueOf(ach)))
				return true;
		}
		return false;
	}
	
	public static AchievementsManager getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final AchievementsManager _instance = new AchievementsManager();
	}
	
	private static void addToConditionList(String nodeName, Object value, ArrayList<Condition> conditions)
	{
		if (nodeName.equals("minLevel"))
			conditions.add(new Level(value));
		else if (nodeName.equals("minPvPCount"))
			conditions.add(new Pvp(value));
		else if (nodeName.equals("minPkCount"))
			conditions.add(new Pk(value));
		else if (nodeName.equals("minClanLevel"))
			conditions.add(new ClanLevel(value));
		else if (nodeName.equals("mustBeHero"))
			conditions.add(new Hero(value));
		else if (nodeName.equals("mustBeNoble"))
			conditions.add(new Noble(value));
		else if (nodeName.equals("minWeaponEnchant"))
			conditions.add(new WeaponEnchant(value));
		else if (nodeName.equals("minKarmaCount"))
			conditions.add(new Karma(value));
		else if (nodeName.equals("minAdenaCount"))
			conditions.add(new Adena(value));
		else if (nodeName.equals("minClanMembersCount"))
			conditions.add(new MinCMcount(value));
		else if (nodeName.equals("mustBeClanLeader"))
			conditions.add(new ClanLeader(value));
		else if (nodeName.equals("mustBeMarried"))
			conditions.add(new Marry(value));
		else if (nodeName.equals("itemAmmount"))
			conditions.add(new ItemsCount(value));
		else if (nodeName.equals("crpAmmount"))
			conditions.add(new Crp(value));
		else if (nodeName.equals("lordOfCastle"))
			conditions.add(new Castle(value));
		else if (nodeName.equals("mustBeMageClass"))
			conditions.add(new Mage(value));
		else if (nodeName.equals("minSubclassCount"))
			conditions.add(new Sub(value));
		else if (nodeName.equals("CompleteAchievements"))
			conditions.add(new CompleteAchievements(value));
		else if (nodeName.equals("minSkillEnchant"))
			conditions.add(new SkillEnchant(value));
		else if (nodeName.equals("minOnlineTime"))
			conditions.add(new OnlineTime(value));
		else if (nodeName.equals("minHeroCount"))
			conditions.add(new HeroCount(value));
		else if (nodeName.equals("raidToKill"))
			conditions.add(new RaidKill(value));
		else if (nodeName.equals("raidToKill1"))
			conditions.add(new RaidKill(value));
		else if (nodeName.equals("raidToKill2"))
			conditions.add(new RaidKill(value));
		else if (nodeName.equals("minRaidPoints"))
			conditions.add(new RaidPoints(value));
		else if (nodeName.equals("eventKills"))
			conditions.add(new eventKills(value));
		else if (nodeName.equals("events"))
			conditions.add(new events(value));
		else if (nodeName.equals("eventWins"))
			conditions.add(new eventWins(value));
	}
	
	public void loadUsed()
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement;
			ResultSet rs;
			String sql = "SELECT ";
			for (int i = 1; i <= getAchievementList().size(); i++)
			{
				if (i != getAchievementList().size())
					sql = sql + "a" + i + ",";
				else
					sql = sql + "a" + i;
				
			}
			
			sql = sql + " from achievements";
			statement = con.prepareStatement(sql);
			
			rs = statement.executeQuery();
			while (rs.next())
			{
				for (int i = 1; i <= getAchievementList().size(); i++)
				{
					String ct = rs.getString(i);
					if (ct.length() > 1 && ct.startsWith("1"))
					{
						_binded.add(ct.substring(ct.indexOf("1") + 1) + "@" + i);
					}
				}
			}
			statement.close();
			rs.close();
		}
		catch (SQLException e)
		{
			_log.warning("[ACHIEVEMENTS SAVE GETDATA]");
			e.printStackTrace();
		}
	}
}