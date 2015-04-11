package net.sf.l2j.gameserver.network.clientpackets;

import net.sf.l2j.gameserver.model.L2Party;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;

/**
 * @author -Wooden-
 */
public final class RequestExOustFromMPCC extends L2GameClientPacket
{
	private String _name;
	
	@Override
	protected void readImpl()
	{
		_name = readS();
	}
	
	@Override
	protected void runImpl()
	{
		final L2PcInstance activeChar = getClient().getActiveChar();
		if (activeChar == null)
			return;
		
		final L2PcInstance target = L2World.getInstance().getPlayer(_name);
		if (target == null)
		{
			activeChar.sendPacket(SystemMessageId.TARGET_CANT_FOUND);
			return;
		}
		
		if (activeChar.equals(target))
		{
			activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
			return;
		}
		
		final L2Party playerParty = activeChar.getParty();
		final L2Party targetParty = target.getParty();
		
		if (playerParty != null && playerParty.isInCommandChannel() && targetParty != null && targetParty.isInCommandChannel() && playerParty.getCommandChannel().getChannelLeader().equals(activeChar))
		{
			targetParty.getCommandChannel().removeParty(targetParty);
			targetParty.broadcastToPartyMembers(SystemMessage.getSystemMessage(SystemMessageId.DISMISSED_FROM_COMMAND_CHANNEL));
			
			// check if CC has not been canceled
			if (playerParty.isInCommandChannel())
				playerParty.getCommandChannel().broadcastToChannelMembers(SystemMessage.getSystemMessage(SystemMessageId.S1_PARTY_DISMISSED_FROM_COMMAND_CHANNEL).addPcName(targetParty.getLeader()));
		}
		else
			activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
	}
}