package net.sf.l2j.gameserver.network.clientpackets;

import net.sf.l2j.gameserver.model.L2Party;
import net.sf.l2j.gameserver.model.L2Party.MessageType;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.partymatching.PartyMatchRoom;
import net.sf.l2j.gameserver.model.partymatching.PartyMatchRoomList;
import net.sf.l2j.gameserver.network.serverpackets.ExClosePartyRoom;
import net.sf.l2j.gameserver.network.serverpackets.ExPartyRoomMember;
import net.sf.l2j.gameserver.network.serverpackets.PartyMatchDetail;

public final class RequestWithdrawParty extends L2GameClientPacket
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
		
		final L2Party party = player.getParty();
		if (party == null)
			return;
		
		if (player.isSubmitingPin())
		{
			player.sendMessage("Unable to do any action while PIN is not submitted");
			return;
		}
		
		if (party.isInDimensionalRift() && !party.getDimensionalRift().getRevivedAtWaitingRoom().contains(player))
			player.sendMessage("You can't exit party when you are in Dimensional Rift.");
		else
		{
			party.removePartyMember(player, MessageType.Left);
			
			if (player.isInPartyMatchRoom())
			{
				PartyMatchRoom _room = PartyMatchRoomList.getInstance().getPlayerRoom(player);
				if (_room != null)
				{
					player.sendPacket(new PartyMatchDetail(player, _room));
					player.sendPacket(new ExPartyRoomMember(player, _room, 0));
					player.sendPacket(ExClosePartyRoom.STATIC_PACKET);
					
					_room.deleteMember(player);
				}
				player.setPartyRoom(0);
				player.broadcastUserInfo();
			}
		}
	}
}
