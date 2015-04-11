package net.sf.l2j.gameserver.network.serverpackets;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class ObservationReturn extends L2GameServerPacket
{
	private final L2PcInstance _activeChar;
	
	public ObservationReturn(L2PcInstance observer)
	{
		_activeChar = observer;
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0xe0);
		writeD(_activeChar.getLastX());
		writeD(_activeChar.getLastY());
		writeD(_activeChar.getLastZ());
	}
}