package custom.Services;

import Extensions.Vip.VIPEngine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Logger;

import net.sf.l2j.Config;
import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.datatables.CharNameTable;
import net.sf.l2j.gameserver.datatables.ClanTable;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.olympiad.OlympiadManager;
import net.sf.l2j.gameserver.model.quest.Quest;
import net.sf.l2j.gameserver.model.quest.QuestState;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;

public class Services extends Quest
{
	public static final Logger _log = Logger.getLogger(Services.class.getName());
	
	// NPC Id
	int servicesNpc = Config.SERVICES_NPC_ID;
	
	// Noble Items
	int nobleItemId = Config.SERVICES_NOBLE_ITEM_ID;
	int nobleItemCount = Config.SERVICES_NOBLE_ITEM_COUNT;
	
	// PK Reduce Items
	int pkReduceItemId = Config.SERVICES_PK_ITEM_ID;
	int pkReduceItemCount = Config.SERVICES_PK_ITEM_COUNT;
	
	// Change Name Items
	int changeNameItemId = Config.SERVICES_CHANGE_NAME_ID;
	int changeNameItemCount = Config.SERVICES_CHANGE_NAME_COUNT;
	
	// Change Clan Name Items
	int changeClanNameItemId = Config.SERVICES_CLAN_NAME_CHANGE_ID;
	long changeClanNameItemCount = Config.SERVICES_CLAN_NAME_CHANGE_COUNT;
	int clanMinLevel = Config.SERVICES_CLAN_NAME_CHANGE_MIN_LVL;
	
	// Clan Level Items
	int[] clanLevelItemsId =
	{
		Config.SERVICES_CLAN_LEVEL_UP_5to6_ID, // Level 5 to 6
		Config.SERVICES_CLAN_LEVEL_UP_6to7_ID, // Level 6 to 7
		Config.SERVICES_CLAN_LEVEL_UP_7to8_ID, // Level 7 to 8
	};
	
	int[] clanLevelItemsCount =
	{
		Config.SERVICES_CLAN_LEVEL_UP_5to6_COUNT, // Level 5 to 6
		Config.SERVICES_CLAN_LEVEL_UP_6to7_COUNT, // Level 6 to 7
		Config.SERVICES_CLAN_LEVEL_UP_7to8_COUNT, // Level 7 to 8
	};
	
	// Clan Reputation Points Items
	int clanReputationPointsItemId = Config.SERVICES_CLAN_REP_POINTS_ID;
	int clanReputationPointsItemCount = Config.SERVICES_CLAN_REP_POINTS_COUNT;
	
	// Change Gender Items
	int changeGenderItemId = Config.SERVICES_CHANGE_SEX_ID;
	int changeGenderItemCount = Config.SERVICES_CHANGE_SEX_COUNT;
	
	// name/title color
	int ColorItemId = Config.SERVICES_COLOR_ID;
	int ColorItemCount = Config.SERVICES_COLOR_COUNT;
	
	// premium
	int PremiumItemId = Config.SERVICES_PREMIUM_ID;
	int PremiumItemCount = Config.SERVICES_PREMIUM_COUNT;
	
	public Services(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(servicesNpc);
		addFirstTalkId(servicesNpc);
		addTalkId(servicesNpc);
	}
	
	public static void main(String[] args)
	{
		new Services(-1, Services.class.getSimpleName(), "custom");
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		if (player.getQuestState(getName()) == null)
		{
			newQuestState(player);
		}
		else if (player.isInCombat())
			return "Services-Blocked.htm";
		
		else if (player.getPvpFlag() == 1)
			return "Services-Blocked.htm";
		
		else if (player.getKarma() != 0)
			return "Services-Blocked.htm";
		
		else if (OlympiadManager.getInstance().isRegistered(player))
			return "Services-Blocked.htm";
		
		else if (player.isDead() || player.isFakeDeath())
			return "Services-Blocked.htm";
		
		return "Services.htm";
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmlText = event;
		QuestState st = player.getQuestState(getName());
		
		if (event.equals("setNoble"))
		{
			if (!player.isNoble())
			{
				if (st.getQuestItemsCount(nobleItemId) >= nobleItemCount)
				{
					st.takeItems(nobleItemId, nobleItemCount);
					player.setNoble(true, true);
					player.setTarget(player);
					player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
					player.broadcastUserInfo();
					return "NoblesseServices-Success.htm";
				}
				return "NoblesseServices-NoItems.htm";
			}
			return "NoblesseServices-AlredyNoble.htm";
		}
		
		else if (event.equals("levelUpClan"))
		{
			if (player.getClan() == null)
			{
				return "ClanLevelUp-NoClan.htm";
			}
			else if (!player.isClanLeader())
			{
				return "ClanLevelUp-NoLeader.htm";
			}
			else
			{
				if (player.getClan().getLevel() == 8)
				{
					return "ClanLevelUp-MaxLevel.htm";
				}
				if (((player.getClan().getLevel() <= 1) || (player.getClan().getLevel() == 2) || (player.getClan().getLevel() == 3) || (player.getClan().getLevel() == 4)))
				{
					player.getClan().changeLevel(player.getClan().getLevel() + 1);
					player.getClan().broadcastClanStatus();
					player.sendMessage("Your clan is now level " + player.getClan().getLevel() + ".");
					player.setTarget(player);
					player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
					
					return "ClanLevelUp.htm";
				}
				else if (player.getClan().getLevel() == 5)
				{
					if (st.getQuestItemsCount(clanLevelItemsId[0]) >= clanLevelItemsCount[0])
					{
						st.takeItems(clanLevelItemsId[0], clanLevelItemsCount[0]);
						player.getClan().changeLevel(player.getClan().getLevel() + 1);
						player.getClan().broadcastClanStatus();
						player.sendMessage("Your clan is now level " + player.getClan().getLevel() + ".");
						player.setTarget(player);
						player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
						
						return "ClanLevelUp.htm";
					}
					return "ClanLevelUp-NoItems.htm";
				}
				else if (player.getClan().getLevel() == 6)
				{
					if (st.getQuestItemsCount(clanLevelItemsId[1]) >= clanLevelItemsCount[1])
					{
						st.takeItems(clanLevelItemsId[1], clanLevelItemsCount[1]);
						player.getClan().changeLevel(player.getClan().getLevel() + 1);
						player.getClan().broadcastClanStatus();
						player.sendMessage("Your clan is now level " + player.getClan().getLevel() + ".");
						player.setTarget(player);
						player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
						
						return "ClanLevelUp.htm";
					}
					return "ClanLevelUp-NoItems.htm";
				}
				else if (player.getClan().getLevel() == 7)
				{
					if (st.getQuestItemsCount(clanLevelItemsId[2]) >= clanLevelItemsCount[2])
					{
						st.takeItems(clanLevelItemsId[2], clanLevelItemsCount[2]);
						player.getClan().changeLevel(player.getClan().getLevel() + 1);
						player.getClan().broadcastClanStatus();
						player.sendMessage("Your clan is now level " + player.getClan().getLevel() + ".");
						player.setTarget(player);
						player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
						
						return "ClanLevelUp.htm";
					}
					return "ClanLevelUp-NoItems.htm";
				}
				
				player.getClan().broadcastClanStatus();
				return "ClanLevelUp.htm";
			}
		}
		else if (event.equals("changeGender"))
		{
			if (st.getQuestItemsCount(changeGenderItemId) >= changeGenderItemCount)
			{
				st.takeItems(changeGenderItemId, changeGenderItemCount);
				player.getAppearance().setSex(player.getAppearance().getSex() ? false : true);
				player.setTarget(player);
				player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
				player.broadcastUserInfo();
				
				return "ChangeGender-Success.htm";
			}
			return "ChangeGender-NoItems.htm";
		}
		else if (event.startsWith("changeName"))
		{
			try
			{
				String newName = event.substring(11);
				
				if (st.getQuestItemsCount(changeNameItemId) >= changeNameItemCount)
				{
					if (newName == null)
					{
						return "ChangeName.htm";
					}
					if (!newName.matches("^[a-zA-Z0-9]+$"))
					{
						player.sendMessage("Incorrect name. Please try again.");
						return "ChangeName.htm";
					}
					else if (newName.equals(player.getName()))
					{
						player.sendMessage("Please, choose a different name.");
						return "ChangeName.htm";
					}
					else if (CharNameTable.doesCharNameExist(newName))
					{
						player.sendMessage("The name " + newName + " already exists.");
						return "ChangeName.htm";
					}
					else
					{
						st.takeItems(changeNameItemId, changeNameItemCount);
						player.setName(newName);
						player.store();
						player.sendMessage("Your new character name is " + newName);
						player.broadcastUserInfo();
						player.getClan().broadcastClanStatus();
						
						return "ChangeName-Success.htm";
					}
				}
				return "ChangeName-NoItems.htm";
			}
			catch (Exception e)
			{
				player.sendMessage("Please, insert a correct name.");
				return "ChangeName.htm";
			}
		}
		else if (event.startsWith("reducePks"))
		{
			try
			{
				String pkReduceString = event.substring(10);
				int pkReduceCount = Integer.parseInt(pkReduceString);
				
				if (player.getPkKills() != 0)
				{
					if (pkReduceCount == 0)
					{
						player.sendMessage("Please, put a higher value.");
						return "PkServices.htm";
					}
					if (st.getQuestItemsCount(pkReduceItemId) >= pkReduceItemCount)
					{
						st.takeItems(pkReduceItemId, pkReduceItemCount * pkReduceCount);
						player.setPkKills(player.getPkKills() - pkReduceCount);
						player.sendMessage("You have successfuly cleaned " + pkReduceCount + " PKs.");
						player.broadcastUserInfo();
						
						return "PkServices-Success.htm";
					}
					return "PkServices-NoItems.htm";
				}
				return "PkServices-NoPks.htm";
			}
			catch (Exception e)
			{
				player.sendMessage("Incorrect value. Please try again.");
				return "PkServices.htm";
			}
		}
		else if (event.startsWith("changeClanName"))
		{
			if (player.getClan() == null)
			{
				return "ChangeClanName-NoClan.htm";
			}
			try
			{
				String newClanName = event.substring(15);
				
				if (st.getQuestItemsCount(changeClanNameItemId) >= changeClanNameItemCount)
				{
					if (newClanName == null)
					{
						return "ChangeClanName.htm";
					}
					if (!player.isClanLeader())
					{
						player.sendMessage("Only the clan leader can change the clan name.");
						return "ChangeClanName.htm";
					}
					else if (player.getClan().getLevel() < clanMinLevel)
					{
						player.sendMessage("Your clan must be at least level " + clanMinLevel + " to change the name.");
						return "ChangeClanName.htm";
					}
					else if (!newClanName.matches("^[a-zA-Z0-9]+$"))
					{
						player.sendMessage("Incorrect name. Please try again.");
						return "ChangeClanName.htm";
					}
					else if (newClanName.equals(player.getClan().getName()))
					{
						player.sendMessage("Please, choose a different name.");
						return "ChangeClanName.htm";
					}
					else if (ClanTable.getInstance().getClanByName(newClanName) != null)
					{
						player.sendMessage("The name " + newClanName + " already exists.");
						return "ChangeClanName.htm";
					}
					else
					{
						st.takeItems(changeNameItemId, changeNameItemCount);
						player.getClan().setName(newClanName);
						
						try (Connection con = L2DatabaseFactory.getInstance().getConnection())
						{
							PreparedStatement statement = con.prepareStatement("UPDATE clan_data SET clan_name=? WHERE clan_id=?");
							statement.setString(1, newClanName);
							statement.setInt(2, player.getClan().getClanId());
							statement.execute();
							statement.close();
						}
						catch (Exception e)
						{
							_log.info("Error updating clan name for player " + player.getName() + ". Error: " + e);
						}
						
						player.sendMessage("Your new clan name is " + newClanName);
						player.getClan().broadcastClanStatus();
						
						return "ChangeClanName-Success.htm";
					}
				}
				return "ChangeClanName-NoItems.htm";
			}
			catch (Exception e)
			{
				player.sendMessage("Please, insert a correct name.");
				return "ChangeClanName.htm";
			}
		}
		else if (event.startsWith("setReputationPoints"))
		{
			try
			{
				String reputationPointsString = event.substring(20);
				int reputationPointsCount = Integer.parseInt(reputationPointsString);
				
				if (player.getClan() == null)
				{
					return "ClanReputationPoints-NoClan.htm";
				}
				else if (!player.isClanLeader())
				{
					return "ClanReputationPoints-NoLeader.htm";
				}
				else
				{
					if (reputationPointsCount == 0)
					{
						player.sendMessage("Please, put a higher value.");
						return "ClanReputationPoints.htm";
					}
					if (st.getQuestItemsCount(clanReputationPointsItemId) >= clanReputationPointsItemCount)
					{
						st.takeItems(clanReputationPointsItemId, clanReputationPointsItemCount * reputationPointsCount);
						player.getClan().addReputationScore(reputationPointsCount);
						player.getClan().broadcastClanStatus();
						return "ClanReputationPoints-Success.htm";
					}
					return "ClanReputationPoints-NoItems.htm";
				}
			}
			catch (Exception e)
			{
				player.sendMessage("Incorrect value. Please try again.");
				return "ClanReputationPoints.htm";
			}
		}
		else if (event.equals("1"))
		{
			if (st.getQuestItemsCount(ColorItemId) >= ColorItemCount)
			{
				st.takeItems(ColorItemId, ColorItemCount);
				st.getPlayer().getAppearance().setTitleColor(0x009900);
				player.setTarget(player);
				player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
				player.broadcastUserInfo();
				player.sendMessage("Your title color has been changed!");
				return "Services-Page2.htm";
			}
			return "ColorServices-NoItems.htm";
		}
		else if (event.equals("2"))
		{
			if (st.getQuestItemsCount(ColorItemId) >= ColorItemCount)
			{
				st.takeItems(ColorItemId, ColorItemCount);
				st.getPlayer().getAppearance().setTitleColor(0xff7f00);
				player.setTarget(player);
				player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
				player.broadcastUserInfo();
				player.sendMessage("Your title color has been changed!");
				return "Services-Page2.htm";
			}
			return "ColorServices-NoItems.htm";
		}
		else if (event.equals("3"))
		{
			if (st.getQuestItemsCount(ColorItemId) >= ColorItemCount)
			{
				st.takeItems(ColorItemId, ColorItemCount);
				st.getPlayer().getAppearance().setTitleColor(0xff00ff);
				player.setTarget(player);
				player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
				player.broadcastUserInfo();
				player.sendMessage("Your title color has been changed!");
				return "Services-Page2.htm";
			}
			return "ColorServices-NoItems.htm";
		}
		else if (event.equals("4"))
		{
			if (st.getQuestItemsCount(ColorItemId) >= ColorItemCount)
			{
				st.takeItems(ColorItemId, ColorItemCount);
				st.getPlayer().getAppearance().setTitleColor(0x00ffff);
				player.setTarget(player);
				player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
				player.broadcastUserInfo();
				player.sendMessage("Your title color has been changed!");
				return "Services-Page2.htm";
			}
			return "ColorServices-NoItems.htm";
		}
		else if (event.equals("5"))
		{
			if (st.getQuestItemsCount(ColorItemId) >= ColorItemCount)
			{
				st.takeItems(ColorItemId, ColorItemCount);
				st.getPlayer().getAppearance().setTitleColor(0x0000ff);
				player.setTarget(player);
				player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
				player.broadcastUserInfo();
				player.sendMessage("Your title color has been changed!");
				return "Services-Page2.htm";
			}
			return "ColorServices-NoItems.htm";
		}
		else if (event.equals("6"))
		{
			if (st.getQuestItemsCount(ColorItemId) >= ColorItemCount)
			{
				st.takeItems(ColorItemId, ColorItemCount);
				st.getPlayer().getAppearance().setTitleColor(0x0099ff);
				player.setTarget(player);
				player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
				player.broadcastUserInfo();
				player.sendMessage("Your title color has been changed!");
				return "Services-Page2.htm";
			}
			return "ColorServices-NoItems.htm";
		}
		else if (event.equals("7"))
		{
			if (st.getQuestItemsCount(ColorItemId) >= ColorItemCount)
			{
				st.takeItems(ColorItemId, ColorItemCount);
				st.getPlayer().getAppearance().setTitleColor(0x70db93);
				player.setTarget(player);
				player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
				player.broadcastUserInfo();
				player.sendMessage("Your title color has been changed!");
				return "Services-Page2.htm";
			}
			return "ColorServices-NoItems.htm";
		}
		else if (event.equals("8"))
		{
			if (st.getQuestItemsCount(ColorItemId) >= ColorItemCount)
			{
				st.takeItems(ColorItemId, ColorItemCount);
				st.getPlayer().getAppearance().setTitleColor(0x9f9f9f);
				player.setTarget(player);
				player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
				player.broadcastUserInfo();
				player.sendMessage("Your title color has been changed!");
				return "Services-Page2.htm";
			}
			return "ColorServices-NoItems.htm";
		}
		else if (event.equals("9"))
		{
			if (st.getQuestItemsCount(ColorItemId) >= ColorItemCount)
			{
				st.takeItems(ColorItemId, ColorItemCount);
				st.getPlayer().getAppearance().setTitleColor(0xffc285);
				player.setTarget(player);
				player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
				player.broadcastUserInfo();
				player.sendMessage("Your title color has been changed!");
				return "Services-Page2.htm";
			}
			return "ColorServices-NoItems.htm";
		}
		
		else if (event.equals("10"))
		{
			if (st.getQuestItemsCount(ColorItemId) >= ColorItemCount)
			{
				st.takeItems(ColorItemId, ColorItemCount);
				st.getPlayer().getAppearance().setNameColor(0x009900);
				player.setTarget(player);
				player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
				player.broadcastUserInfo();
				player.sendMessage("Your name color has been changed!");
				return "Services-Page2.htm";
			}
			return "ColorServices-NoItems.htm";
		}
		else if (event.equals("11"))
		{
			if (st.getQuestItemsCount(ColorItemId) >= ColorItemCount)
			{
				st.takeItems(ColorItemId, ColorItemCount);
				st.getPlayer().getAppearance().setNameColor(0xff7f00);
				player.setTarget(player);
				player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
				player.broadcastUserInfo();
				player.sendMessage("Your name color has been changed!");
				return "Services-Page2.htm";
			}
			return "ColorServices-NoItems.htm";
		}
		else if (event.equals("12"))
		{
			if (st.getQuestItemsCount(ColorItemId) >= ColorItemCount)
			{
				st.takeItems(ColorItemId, ColorItemCount);
				st.getPlayer().getAppearance().setNameColor(0xff00ff);
				player.setTarget(player);
				player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
				player.broadcastUserInfo();
				player.sendMessage("Your name color has been changed!");
				return "Services-Page2.htm";
			}
			return "ColorServices-NoItems.htm";
		}
		else if (event.equals("13"))
		{
			if (st.getQuestItemsCount(ColorItemId) >= ColorItemCount)
			{
				st.takeItems(ColorItemId, ColorItemCount);
				st.getPlayer().getAppearance().setNameColor(0x00ffff);
				player.setTarget(player);
				player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
				player.broadcastUserInfo();
				player.sendMessage("Your name color has been changed!");
				return "Services-Page2.htm";
			}
			return "ColorServices-NoItems.htm";
		}
		else if (event.equals("14"))
		{
			if (st.getQuestItemsCount(ColorItemId) >= ColorItemCount)
			{
				st.takeItems(ColorItemId, ColorItemCount);
				st.getPlayer().getAppearance().setNameColor(0x0000ff);
				player.setTarget(player);
				player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
				player.broadcastUserInfo();
				player.sendMessage("Your name color has been changed!");
				return "Services-Page2.htm";
			}
			return "ColorServices-NoItems.htm";
		}
		else if (event.equals("15"))
		{
			if (st.getQuestItemsCount(ColorItemId) >= ColorItemCount)
			{
				st.takeItems(ColorItemId, ColorItemCount);
				st.getPlayer().getAppearance().setNameColor(0x0099ff);
				player.setTarget(player);
				player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
				player.broadcastUserInfo();
				player.sendMessage("Your name color has been changed!");
				return "Services-Page2.htm";
			}
			return "ColorServices-NoItems.htm";
		}
		else if (event.equals("16"))
		{
			if (st.getQuestItemsCount(ColorItemId) >= ColorItemCount)
			{
				st.takeItems(ColorItemId, ColorItemCount);
				st.getPlayer().getAppearance().setNameColor(0x70db93);
				player.setTarget(player);
				player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
				player.broadcastUserInfo();
				player.sendMessage("Your name color has been changed!");
				return "Services-Page2.htm";
			}
			return "ColorServices-NoItems.htm";
		}
		else if (event.equals("17"))
		{
			if (st.getQuestItemsCount(ColorItemId) >= ColorItemCount)
			{
				st.takeItems(ColorItemId, ColorItemCount);
				st.getPlayer().getAppearance().setNameColor(0x9f9f9f);
				player.setTarget(player);
				player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
				player.broadcastUserInfo();
				player.sendMessage("Your name color has been changed!");
				return "Services-Page2.htm";
			}
			return "ColorServices-NoItems.htm";
		}
		else if (event.equals("18"))
		{
			if (st.getQuestItemsCount(ColorItemId) >= ColorItemCount)
			{
				st.takeItems(ColorItemId, ColorItemCount);
				st.getPlayer().getAppearance().setNameColor(0xffc285);
				player.setTarget(player);
				player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
				player.broadcastUserInfo();
				player.sendMessage("Your name color has been changed!");
				return "Services-Page2.htm";
			}
			return "ColorServices-NoItems.htm";
		}
		else if (event.equals("premium"))
		{
			player.setTarget(player);
			if (VIPEngine.getInstance().isVip(player))
			{
				player.sendMessage("You are already vip!");
				return "Services-Page2.htm";
			}
			
			if (st.getQuestItemsCount(PremiumItemId) >= PremiumItemCount)
			{
				st.takeItems(PremiumItemId, PremiumItemCount);
				VIPEngine.getInstance().addVip(player);
				if (Config.VIP_NAME_COLOR_CONFIG)
				{
					Integer colorName = Integer.decode("0x" + Config.VIPS_NAME_COLOR);
					Integer colorTitle = Integer.decode("0x" + Config.VIPS_TITLE_COLOR);
					player.getAppearance().setNameColor(colorName);
					player.getAppearance().setTitleColor(colorTitle);
				}
				
				if (Config.VIPS_SKILLS_CONFIG)
				{
					for (int skillid : Config.VIP_SKILLS.keySet())
					{
						L2Skill skill = SkillTable.getInstance().getInfo(skillid, Config.VIP_SKILLS.get(skillid));
						player.addSkill(skill, false);
					}
				}
				
				if (Config.VIPS_HERO_AURA)
					player.broadcastUserInfo();
				player.setTarget(player);
				player.broadcastPacket(new MagicSkillUse(player, 5103, 1, 1000, 0));
				player.broadcastUserInfo();
				player.sendMessage("Your are now a server VIP!");
				return "Services-Page2.htm";
			}
			return "PremiumServices-NoItems.htm";
		}
		
		return htmlText;
	}
}