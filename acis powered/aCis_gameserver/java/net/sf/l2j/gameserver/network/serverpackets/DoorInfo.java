package net.sf.l2j.gameserver.network.serverpackets;

import net.sf.l2j.gameserver.model.actor.instance.L2DoorInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class DoorInfo extends L2GameServerPacket
{
	private final L2DoorInstance _door;
	private final L2PcInstance _activeChar;
	
	public DoorInfo(L2DoorInstance door, L2PcInstance activeChar)
	{
		_door = door;
		_activeChar = activeChar;
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0x4c);
		writeD(_door.getObjectId());
		writeD(_door.getDoorId());
		writeD(_door.isAutoAttackable(_activeChar) ? 0 : 1);
	}
}