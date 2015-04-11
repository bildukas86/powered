package net.sf.l2j.gameserver.network.clientpackets;

import Extensions.OnEnter;
import Extensions.AutoManagers.AutoRestartGameServer;
import Extensions.Events.SiegeReward;
import Extensions.Protection.ipCatcher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.Announcements;
import net.sf.l2j.gameserver.GameTimeController;
import net.sf.l2j.gameserver.datatables.AdminCommandAccessRights;
import net.sf.l2j.gameserver.datatables.GmListTable;
import net.sf.l2j.gameserver.datatables.MapRegionTable;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.datatables.SkillTable.FrequentSkill;
import net.sf.l2j.gameserver.instancemanager.ClanHallManager;
import net.sf.l2j.gameserver.instancemanager.CoupleManager;
import net.sf.l2j.gameserver.instancemanager.DimensionalRiftManager;
import net.sf.l2j.gameserver.instancemanager.PetitionManager;
import net.sf.l2j.gameserver.instancemanager.QuestManager;
import net.sf.l2j.gameserver.instancemanager.SevenSigns;
import net.sf.l2j.gameserver.instancemanager.SiegeManager;
import net.sf.l2j.gameserver.model.L2Clan;
import net.sf.l2j.gameserver.model.L2Clan.SubPledge;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.base.Race;
import net.sf.l2j.gameserver.model.entity.ClanHall;
import net.sf.l2j.gameserver.model.entity.Couple;
import net.sf.l2j.gameserver.model.entity.Siege;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.model.olympiad.Olympiad;
import net.sf.l2j.gameserver.model.quest.Quest;
import net.sf.l2j.gameserver.model.quest.QuestState;
import net.sf.l2j.gameserver.model.zone.ZoneId;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;
import net.sf.l2j.gameserver.network.serverpackets.Die;
import net.sf.l2j.gameserver.network.serverpackets.EtcStatusUpdate;
import net.sf.l2j.gameserver.network.serverpackets.ExStorageMaxCount;
import net.sf.l2j.gameserver.network.serverpackets.FriendList;
import net.sf.l2j.gameserver.network.serverpackets.HennaInfo;
import net.sf.l2j.gameserver.network.serverpackets.ItemList;
import net.sf.l2j.gameserver.network.serverpackets.PledgeShowMemberListAll;
import net.sf.l2j.gameserver.network.serverpackets.PledgeShowMemberListUpdate;
import net.sf.l2j.gameserver.network.serverpackets.PledgeSkillList;
import net.sf.l2j.gameserver.network.serverpackets.PledgeStatusChanged;
import net.sf.l2j.gameserver.network.serverpackets.QuestList;
import net.sf.l2j.gameserver.network.serverpackets.ShortCutInit;
import net.sf.l2j.gameserver.network.serverpackets.SkillCoolTime;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.network.serverpackets.UserInfo;
import net.sf.l2j.gameserver.util.Util;

public class EnterWorld extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
		// this is just a trigger packet. it has no content
	}
	
	@Override
	protected void runImpl()
	{
		final L2PcInstance activeChar = getClient().getActiveChar();
		if (activeChar == null)
		{
			_log.warning("EnterWorld failed! activeChar is null...");
			getClient().closeNow();
			return;
		}
		
		if (L2World.getInstance().findObject(activeChar.getObjectId()) != null)
		{
		}
		// Last Visit Information
		if (Config.ALLOW_LAST_VISIT_INFORMATION)
		{
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(activeChar.getLastAccess());
			if (activeChar.getUptime() > 1)
				activeChar.sendMessage("Your last visit was at " + formatter.format(calendar.getTime()));
		}
		// Auto Restart GameServer System
		if (Config.RESTART_BY_TIME_OF_DAY)
		{
			ShowNextRestart(activeChar);
		}
		
		if (activeChar.isGM())
		{
			if (Config.AVAILABLE_GMS.contains(activeChar.getObjectId())) {
				if (Config.GM_STARTUP_INVULNERABLE && AdminCommandAccessRights.getInstance().hasAccess("admin_invul", activeChar.getAccessLevel()))
					activeChar.setIsInvul(true);
				
				if (Config.GM_STARTUP_INVISIBLE && AdminCommandAccessRights.getInstance().hasAccess("admin_hide", activeChar.getAccessLevel()))
					activeChar.getAppearance().setInvisible();
				
				if (Config.GM_STARTUP_SILENCE && AdminCommandAccessRights.getInstance().hasAccess("admin_silence", activeChar.getAccessLevel()))
					activeChar.setInRefusalMode(true);
				
				if (Config.GM_STARTUP_AUTO_LIST && AdminCommandAccessRights.getInstance().hasAccess("admin_gmliston", activeChar.getAccessLevel()))
					GmListTable.getInstance().addGm(activeChar, false);
				else
					GmListTable.getInstance().addGm(activeChar, true);
			}
			else {
				System.out.println("Player " + activeChar.getName() + " was not listed in GMs list with object id: " + activeChar.getObjectId() + " so his access level were made to 0.");
				activeChar.setAccessLevel(0);
			}
		}
		
		if (SiegeReward.ACTIVATED_SYSTEM && !SiegeReward.REWARD_ACTIVE_MEMBERS_ONLY)
			SiegeReward.getInstance().processWorldEnter(activeChar);
		
		// Set dead status if applies
		if (activeChar.getCurrentHp() < 0.5)
			activeChar.setIsDead(true);
		
		final L2Clan clan = activeChar.getClan();
		if (clan != null)
		{
			activeChar.sendPacket(new PledgeSkillList(clan));
			notifyClanMembers(activeChar);
			notifySponsorOrApprentice(activeChar);
			
			// Add message at connection if clanHall not paid.
			final ClanHall clanHall = ClanHallManager.getInstance().getClanHallByOwner(clan);
			if (clanHall != null)
			{
				if (!clanHall.getPaid())
					activeChar.sendPacket(SystemMessageId.PAYMENT_FOR_YOUR_CLAN_HALL_HAS_NOT_BEEN_MADE_PLEASE_MAKE_PAYMENT_TO_YOUR_CLAN_WAREHOUSE_BY_S1_TOMORROW);
			}
			
			for (Siege siege : SiegeManager.getSieges())
			{
				if (!siege.isInProgress())
					continue;
				
				if (siege.checkIsAttacker(clan))
					activeChar.setSiegeState((byte) 1);
				else if (siege.checkIsDefender(clan))
					activeChar.setSiegeState((byte) 2);
			}
			
			activeChar.sendPacket(new PledgeShowMemberListAll(clan, 0));
			
			for (SubPledge sp : clan.getAllSubPledges())
				activeChar.sendPacket(new PledgeShowMemberListAll(clan, sp.getId()));
			
			activeChar.sendPacket(new UserInfo(activeChar));
			activeChar.sendPacket(new PledgeStatusChanged(clan));
		}
		
		// Updating Seal of Strife Buff/Debuff
		if (SevenSigns.getInstance().isSealValidationPeriod() && SevenSigns.getInstance().getSealOwner(SevenSigns.SEAL_STRIFE) != SevenSigns.CABAL_NULL)
		{
			int cabal = SevenSigns.getInstance().getPlayerCabal(activeChar.getObjectId());
			if (cabal != SevenSigns.CABAL_NULL)
			{
				if (cabal == SevenSigns.getInstance().getSealOwner(SevenSigns.SEAL_STRIFE))
					activeChar.addSkill(FrequentSkill.THE_VICTOR_OF_WAR.getSkill());
				else
					activeChar.addSkill(FrequentSkill.THE_VANQUISHED_OF_WAR.getSkill());
			}
		}
		else
		{
			activeChar.removeSkill(FrequentSkill.THE_VICTOR_OF_WAR.getSkill());
			activeChar.removeSkill(FrequentSkill.THE_VANQUISHED_OF_WAR.getSkill());
		}
		
		if (Config.PLAYER_SPAWN_PROTECTION > 0)
			activeChar.setProtection(true);
		
		activeChar.spawnMe(activeChar.getX(), activeChar.getY(), activeChar.getZ());
		
		// engage and notify Partner
		if (Config.ALLOW_WEDDING)
			engage(activeChar);
		
		final ipCatcher ipc = new ipCatcher();
		if (ipc.isCatched(activeChar))
			activeChar.logout();
		
		// Announcements, welcome & Seven signs period messages
		activeChar.sendPacket(SystemMessageId.WELCOME_TO_LINEAGE);
		SevenSigns.getInstance().sendCurrentPeriodMsg(activeChar);
		Announcements.getInstance().showAnnouncements(activeChar);
		
		// if player is DE, check for shadow sense skill at night
		if (activeChar.getRace() == Race.DarkElf && activeChar.getSkillLevel(294) == 1)
			activeChar.sendPacket(SystemMessage.getSystemMessage((GameTimeController.getInstance().isNight()) ? SystemMessageId.NIGHT_S1_EFFECT_APPLIES : SystemMessageId.DAY_S1_EFFECT_DISAPPEARS).addSkillName(294));
		
		activeChar.getMacroses().sendUpdate();
		activeChar.sendPacket(new UserInfo(activeChar));
		activeChar.sendPacket(new HennaInfo(activeChar));
		activeChar.sendPacket(new FriendList(activeChar));
		// activeChar.queryGameGuard();
		activeChar.sendPacket(new ItemList(activeChar, false));
		activeChar.sendPacket(new ShortCutInit(activeChar));
		activeChar.sendPacket(new ExStorageMaxCount(activeChar));
		activeChar.updateEffectIcons();
		activeChar.sendPacket(new EtcStatusUpdate(activeChar));
		activeChar.sendSkillList();
		
		if (Config.ENABLE_ANTI_OVER_ENCHANT_PROTECTION)
		{
			for (ItemInstance i : activeChar.getInventory().getItems())
			{
				if (!activeChar.isGM())
				{
					if (i.isEquipable())
					{
						if ((i.getEnchantLevel() > Config.ENCHANT_MAX_WEAPON_PROTECTION) || (i.getEnchantLevel() > Config.ENCHANT_MAX_ARMOR_PROTECTION) || (i.getEnchantLevel() > Config.ENCHANT_MAX_JEWELRY_PROTECTION))
						{
							// Delete Item Over enchanted
							activeChar.getInventory().destroyItem(null, i, activeChar, null);
							// Message to Player
							activeChar.sendMessage("[Server]:You have Items over enchanted you will be kikked!");
							// Kick player and send message
							activeChar.logout();
							activeChar.sendMessage("You kicked " + activeChar.getName() + " from the game.");
							// Punishment
							Util.handleIllegalPlayerAction(activeChar, "Player " + activeChar.getName() + " have item Overenchanted ", Config.DEFAULT_PUNISH);
							// Log in console
							_log.warning("#### WARNING ####");
							_log.warning("Over Enchant Item");
							_log.warning(i + " item has been removed from player,");
							
						}
					}
				}
			}
		}
		
		Quest.playerEnter(activeChar);
		if (!Config.DISABLE_TUTORIAL)
			loadTutorial(activeChar);
		
		if (activeChar.getOnlineTime() == 0 && Config.NEW_PLAYER_BUFFS)
		{
			if (activeChar.isMageClass())
			{
				for (int[] mageBuffs : Config.NEW_PLAYER_MAGE_BUFF_LIST)
				{
					if (mageBuffs == null)
						continue;
					
					SkillTable.getInstance().getInfo(mageBuffs[0], mageBuffs[1]).getEffects(activeChar, activeChar);
				}
			}
			else
				for (int[] fighterBuffs : Config.NEW_PLAYER_FIGHT_BUFF_LIST)
				{
					if (fighterBuffs == null)
						continue;
					
					SkillTable.getInstance().getInfo(fighterBuffs[0], fighterBuffs[1]).getEffects(activeChar, activeChar);
				}
		}
		
		for (Quest quest : QuestManager.getInstance().getAllManagedScripts())
		{
			if (quest != null && quest.getOnEnterWorld())
				quest.notifyEnterWorld(activeChar);
		}
		activeChar.sendPacket(new QuestList(activeChar));
		
		/** Customs */
		OnEnter.addCustoms(activeChar);
		/** /Customs */
		
		PetitionManager.getInstance().checkPetitionMessages(activeChar);
		
		// no broadcast needed since the player will already spawn dead to others
		if (activeChar.isAlikeDead())
			sendPacket(new Die(activeChar));
		
		activeChar.onPlayerEnter();
		
		sendPacket(new SkillCoolTime(activeChar));
		
		// If player logs back in a stadium, port him in nearest town.
		if (Olympiad.getInstance().playerInStadia(activeChar))
			activeChar.teleToLocation(MapRegionTable.TeleportWhereType.Town);
		
		if (DimensionalRiftManager.getInstance().checkIfInRiftZone(activeChar.getX(), activeChar.getY(), activeChar.getZ(), false))
			DimensionalRiftManager.getInstance().teleportToWaitingRoom(activeChar);
		
		if (activeChar.getClanJoinExpiryTime() > System.currentTimeMillis())
			activeChar.sendPacket(SystemMessageId.CLAN_MEMBERSHIP_TERMINATED);
		
		// Attacker or spectator logging into a siege zone will be ported at town.
		if (!activeChar.isGM() && (!activeChar.isInSiege() || activeChar.getSiegeState() < 2) && activeChar.isInsideZone(ZoneId.SIEGE))
			activeChar.teleToLocation(MapRegionTable.TeleportWhereType.Town);
		if (Config.ALLOW_ENTER_PMS)
		{
			Set<String> epks = Config.ENTER_PMS.keySet();
			for (String i : epks)
			{
				CreatureSay cs = new CreatureSay(0, Config.ENTER_PMS_SYSTEM, Config.ENTER_PMS.get(i), i);
				activeChar.sendPacket(cs);
			}
		}
	}
	
	private static void ShowNextRestart(L2PcInstance activeChar)
	{
		activeChar.sendMessage("Next Server AutoRestart: " + AutoRestartGameServer.getInstance().getRestartNextTime());
	}
	
	private static void engage(L2PcInstance cha)
	{
		int _chaid = cha.getObjectId();
		
		for (Couple cl : CoupleManager.getInstance().getCouples())
		{
			if (cl.getPlayer1Id() == _chaid || cl.getPlayer2Id() == _chaid)
			{
				if (cl.getMaried())
					cha.setMarried(true);
				
				cha.setCoupleId(cl.getId());
			}
		}
	}
	
	private static void notifyClanMembers(L2PcInstance activeChar)
	{
		final L2Clan clan = activeChar.getClan();
		
		// Refresh player instance.
		clan.getClanMember(activeChar.getObjectId()).setPlayerInstance(activeChar);
		
		final SystemMessage msg = SystemMessage.getSystemMessage(SystemMessageId.CLAN_MEMBER_S1_LOGGED_IN).addPcName(activeChar);
		final PledgeShowMemberListUpdate update = new PledgeShowMemberListUpdate(activeChar);
		
		// Send packet to others members.
		for (L2PcInstance member : clan.getOnlineMembers())
		{
			if (member == activeChar)
				continue;
			
			member.sendPacket(msg);
			member.sendPacket(update);
		}
	}
	
	private static void notifySponsorOrApprentice(L2PcInstance activeChar)
	{
		if (activeChar.getSponsor() != 0)
		{
			L2PcInstance sponsor = L2World.getInstance().getPlayer(activeChar.getSponsor());
			if (sponsor != null)
				sponsor.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.YOUR_APPRENTICE_S1_HAS_LOGGED_IN).addPcName(activeChar));
		}
		else if (activeChar.getApprentice() != 0)
		{
			L2PcInstance apprentice = L2World.getInstance().getPlayer(activeChar.getApprentice());
			if (apprentice != null)
				apprentice.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.YOUR_SPONSOR_S1_HAS_LOGGED_IN).addPcName(activeChar));
		}
	}
	
	private static void loadTutorial(L2PcInstance player)
	{
		QuestState qs = player.getQuestState("Tutorial");
		if (qs != null)
			qs.getQuest().notifyEvent("UC", null, player);
	}
	
	@Override
	protected boolean triggersOnActionRequest()
	{
		return false;
	}
}