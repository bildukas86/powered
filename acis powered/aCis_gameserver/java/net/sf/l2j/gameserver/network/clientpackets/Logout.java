package net.sf.l2j.gameserver.network.clientpackets;

import Extensions.Events.RandomFight;
import Extensions.Events.StriderRace;
import Extensions.Events.Phoenix.EventManager;

import net.sf.l2j.gameserver.instancemanager.SevenSignsFestival;
import net.sf.l2j.gameserver.model.L2Party;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.zone.ZoneId;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.taskmanager.AttackStanceTaskManager;

public final class Logout extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
	}
	
	@Override
	protected void runImpl()
	{
		final L2PcInstance player = getClient().getActiveChar();
		if (player == null)
			return;
		
		if (player.getActiveEnchantItem() != null)
		{
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (StriderRace._players.contains(player))
		{
			player.sendMessage("You may not exit during Strider Race event.");
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (player.isProcessingTransaction())
		{
			player.sendMessage("You may not exit while offer trade.");
			return;
		}
		
		if (player.isLocked())
		{
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (player.isInsideZone(ZoneId.NO_RESTART))
		{
			player.sendPacket(SystemMessageId.NO_LOGOUT_HERE);
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (RandomFight.players.contains(player))
		{
			player.sendMessage("You can't logout when you are in random fight event.");
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (AttackStanceTaskManager.getInstance().get(player) && !player.isGM())
		{
			player.sendPacket(SystemMessageId.CANT_LOGOUT_WHILE_FIGHTING);
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (EventManager.getInstance().isRegistered(player) && !EventManager.getInstance().getBoolean("restartAllowed"))
		{
			player.sendMessage("You cannot logout while you are a participant in an event.");
			sendPacket(new ActionFailed());
			return;
		}
		
		// Prevent player from logging out if they are a festival participant and it is in progress,
		// otherwise notify party members that the player is not longer a participant.
		if (player.isFestivalParticipant())
		{
			if (SevenSignsFestival.getInstance().isFestivalInitialized())
			{
				player.sendMessage("You cannot log out while you are a participant in a festival.");
				player.sendPacket(ActionFailed.STATIC_PACKET);
				return;
			}
			L2Party playerParty = player.getParty();
			
			if (playerParty != null)
				player.getParty().broadcastToPartyMembers(SystemMessage.sendString(player.getName() + " has been removed from the upcoming festival."));
		}
		
		// Menu
		if (player.getOnOffEffects() == true)
			player.setOnOffEffects(false);
		if (player.getTradeRefusal() == true)
			player.setTradeRefusal(false);
		if (player.isInRefusalMode() == true)
			player.setInRefusalMode(false);
		if (player.getExpSpRefusal() == true)
			player.setExpSpRefusal(false);
		if (player.getUnlimitedArrowsSS() == true)
			player.setUnlimitedArrowsSS(false);
		if (player.getExchangeRefusal() == true)
			player.setExchangeRefusal(false);
		if (player.getSsEffects() == true)
			player.setSsEffects(false);
		
		// Remove player from Boss Zone
		player.removeFromBossZone();
		
		player.logout();
	}
}