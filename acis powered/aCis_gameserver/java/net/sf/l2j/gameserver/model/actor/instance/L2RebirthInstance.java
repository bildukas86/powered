package net.sf.l2j.gameserver.model.actor.instance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import net.sf.l2j.Config;
import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.model.base.Experience;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.network.serverpackets.StatusUpdate;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;

public final class L2RebirthInstance extends L2NpcInstance
{
	private static HashMap<Integer, Integer> _rebirthInfo = new HashMap<>();
	
	public L2RebirthInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		if (command.startsWith("performRebirth"))
		{
			// Maximum rebirth count. Return the player's current Rebirth Level.
			int currBirth = getRebirthLevel(player);
			if (currBirth >= Config.REBIRTH_MAX)
			{
				NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
				html.setFile("data/html/mods/Rebirth/rebirth-max.htm");
				
				player.sendPacket(html);
				return;
			}
			
			// Level requirement for a rebirth.
			if (player.getLevel() < Config.REBIRTH_MIN_LEVEL)
			{
				NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
				html.setFile("data/html/mods/Rebirth/rebirth-level.htm");
				
				player.sendPacket(html);
				return;
			}
			
			int itemId = 0, itemCount = 0, loopBirth = 0;
			for (String readItems : Config.REBIRTH_ITEMS)
			{
				String[] currItem = readItems.split(",");
				if (loopBirth == currBirth)
				{
					itemId = Integer.parseInt(currItem[0]);
					itemCount = Integer.parseInt(currItem[1]);
					break;
				}
				loopBirth++;
			}
			
			// Rewards the player with an item.
			rebirthItemReward(player, itemId, itemCount);
			
			// Check and see if its the player's first rebirth calling.
			boolean firstBirth = currBirth == 0;
			
			// Player meets requirements and starts Rebirth process.
			grantRebirth(player, (currBirth + 1), firstBirth);
		}
		else
			super.onBypassFeedback(player, command);
	}
	
	@Override
	public void showChatWindow(L2PcInstance player)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
		html.setFile("data/html/mods/Rebirth/rebirth.htm");
		html.replace("%objectId%", String.valueOf(getObjectId()));
		html.replace("%level%", +Config.REBIRTH_RETURN_TO_LEVEL);
		
		player.sendPacket(html);
	}
	
	/**
	 * Physically rewards player and resets status to nothing.
	 * @param player the player
	 * @param newBirthCount the new birth count
	 * @param firstBirth the first birth
	 */
	public void grantRebirth(L2PcInstance player, int newBirthCount, boolean firstBirth)
	{
		try
		{
			// Delevel.
			player.removeExpAndSp(player.getExp() - Experience.LEVEL[Config.REBIRTH_RETURN_TO_LEVEL], 0);
			
			// Back to the first class.
			player.setClassId(player.getClassId().getFirstClass().getId());
			
			// Send the Server->Client packet StatusUpdate with current HP, MP and CP to this L2PcInstance
			player.broadcastStatusUpdate();
			
			// Broadcast informations from a user to himself and his knownlist.
			player.broadcastUserInfo();
			
			// Remove the player's current skills.
			for (L2Skill skill : player.getAllSkills())
				player.removeSkill(skill);
			
			// Give all available skills to the player.
			player.giveAvailableSkills();
			
			// Update L2PcInstance stats in the characters table of the database.
			player.store();
			
			if (firstBirth)
				// Stores the player's information in the DB.
				storePlayerBirth(player);
			else
				// Updates the player's information in the DB.
				updatePlayerBirth(player, newBirthCount);
			
			// Displays a congratulation window to the player.
			NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
			html.setFile("data/html/mods/Rebirth/rebirth-successfully.htm");
			html.replace("%level%", +Config.REBIRTH_RETURN_TO_LEVEL);
			
			player.sendPacket(html);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Rewards the player with an item.
	 * @param player the player
	 * @param itemId : Identifier of the item.
	 * @param itemCount : Quantity of items to add.
	 */
	public static void rebirthItemReward(L2PcInstance player, int itemId, int itemCount)
	{
		// Incorrect amount.
		if (itemCount <= 0)
			return;
		
		final ItemInstance item = player.getInventory().addItem("Quest", itemId, itemCount, player, player);
		if (item == null)
			return;
		
		// Send message to the client.
		if (itemId == 57)
		{
			SystemMessage smsg = SystemMessage.getSystemMessage(SystemMessageId.EARNED_S1_ADENA);
			smsg.addItemNumber(itemCount);
			player.sendPacket(smsg);
		}
		else
		{
			if (itemCount > 1)
			{
				SystemMessage smsg = SystemMessage.getSystemMessage(SystemMessageId.EARNED_S2_S1_S);
				smsg.addItemName(itemId);
				smsg.addItemNumber(itemCount);
				player.sendPacket(smsg);
			}
			else
			{
				SystemMessage smsg = SystemMessage.getSystemMessage(SystemMessageId.EARNED_ITEM_S1);
				smsg.addItemName(itemId);
				player.sendPacket(smsg);
			}
		}
		
		// Send status update packet.
		StatusUpdate su = new StatusUpdate(player);
		su.addAttribute(StatusUpdate.CUR_LOAD, player.getCurrentLoad());
		player.sendPacket(su);
	}
	
	/**
	 * Return the player's current Rebirth Level.
	 * @param player the player
	 * @return the rebirth level
	 */
	public static int getRebirthLevel(L2PcInstance player)
	{
		int playerId = player.getObjectId();
		if (_rebirthInfo.get(playerId) == null)
			loadRebirthInfo(player);
		
		return _rebirthInfo.get(playerId);
	}
	
	/**
	 * Database caller to retrieve player's current Rebirth Level.
	 * @param player the player
	 */
	public static void loadRebirthInfo(L2PcInstance player)
	{
		int playerId = player.getObjectId(), rebirthCount = 0;
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			ResultSet rset;
			PreparedStatement statement = con.prepareStatement("SELECT * FROM `character_rebirths` WHERE playerId = ?");
			statement.setInt(1, playerId);
			rset = statement.executeQuery();
			
			while (rset.next())
			{
				rebirthCount = rset.getInt("rebirthCount");
			}
			
			rset.close();
			statement.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		_rebirthInfo.put(playerId, rebirthCount);
	}
	
	/**
	 * Stores the player's information in the DB.
	 * @param player the player
	 */
	public static void storePlayerBirth(L2PcInstance player)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("INSERT INTO `character_rebirths` (playerId,rebirthCount) VALUES (?,1)");
			statement.setInt(1, player.getObjectId());
			statement.execute();
			
			_rebirthInfo.put(player.getObjectId(), 1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates the player's information in the DB.
	 * @param player the player
	 * @param newRebirthCount the new rebirth count
	 */
	public static void updatePlayerBirth(L2PcInstance player, int newRebirthCount)
	{
		int playerId = player.getObjectId();
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("UPDATE `character_rebirths` SET rebirthCount = ? WHERE playerId = ?");
			statement.setInt(1, newRebirthCount);
			statement.setInt(2, playerId);
			statement.execute();
			
			_rebirthInfo.put(playerId, newRebirthCount);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}