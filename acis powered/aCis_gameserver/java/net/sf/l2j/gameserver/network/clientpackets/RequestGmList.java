package net.sf.l2j.gameserver.network.clientpackets;

import net.sf.l2j.gameserver.datatables.GmListTable;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public final class RequestGmList extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
	}
	
	@Override
	protected void runImpl()
	{
		final L2PcInstance activeChar = getClient().getActiveChar();
		if (activeChar == null)
			return;
		
		GmListTable.getInstance().sendListToPlayer(activeChar);
	}
}