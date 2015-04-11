package net.sf.l2j.gameserver.network.clientpackets;

import Extensions.Events.RandomFight;
import Extensions.Events.StriderRace;
import Extensions.Events.Phoenix.EventManager;

import net.sf.l2j.gameserver.instancemanager.SevenSignsFestival;
import net.sf.l2j.gameserver.model.L2Party;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.zone.ZoneId;
import net.sf.l2j.gameserver.network.L2GameClient;
import net.sf.l2j.gameserver.network.L2GameClient.GameClientState;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.CharSelectInfo;
import net.sf.l2j.gameserver.network.serverpackets.RestartResponse;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.taskmanager.AttackStanceTaskManager;

public final class RequestRestart extends L2GameClientPacket
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
			sendPacket(RestartResponse.valueOf(false));
			return;
		}
		
		if (player.isLocked())
		{
			sendPacket(RestartResponse.valueOf(false));
			return;
		}
		
		if (player.isInsideZone(ZoneId.NO_RESTART))
		{
			player.sendPacket(SystemMessageId.NO_RESTART_HERE);
			sendPacket(RestartResponse.valueOf(false));
			return;
		}
		
		if (player.isInStoreMode())
		{
			sendPacket(RestartResponse.valueOf(false));
			return;
		}
		
		if (RandomFight.players.contains(player))
		{
			player.sendMessage("You can't restart when you are in random fight event.");
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (player.isProcessingTransaction())
		{
			player.sendMessage("You can't restart while offer trade.");
			return;
		}
		
		if (AttackStanceTaskManager.getInstance().get(player) && !player.isGM())
		{
			player.sendPacket(SystemMessageId.CANT_RESTART_WHILE_FIGHTING);
			sendPacket(RestartResponse.valueOf(false));
			return;
		}
		
		if (EventManager.getInstance().isRegistered(player) && !EventManager.getInstance().getBoolean("restartAllowed"))
		{
			player.sendMessage("You cannot restart while you are a participant in an event.");
			sendPacket(new ActionFailed());
			return;
		}
		
		if (StriderRace._players.contains(player))
		{
			player.sendMessage("You may not restart during Strider Race event.");
			sendPacket(RestartResponse.valueOf(false));
			return;
		}
		
		// Prevent player from restarting if they are a festival participant and it is in progress,
		// otherwise notify party members that the player is not longer a participant.
		if (player.isFestivalParticipant())
		{
			if (SevenSignsFestival.getInstance().isFestivalInitialized())
			{
				sendPacket(RestartResponse.valueOf(false));
				return;
			}
			
			final L2Party playerParty = player.getParty();
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
		
		final L2GameClient client = getClient();
		
		// detach the client from the char so that the connection isnt closed in the deleteMe
		player.setClient(null);
		
		// removing player from the world
		player.deleteMe();
		
		client.setActiveChar(null);
		client.setState(GameClientState.AUTHED);
		
		sendPacket(RestartResponse.valueOf(true));
		
		// send char list
		final CharSelectInfo cl = new CharSelectInfo(client.getAccountName(), client.getSessionId().playOkID1);
		sendPacket(cl);
		client.setCharSelection(cl.getCharInfo());
	}
}