package net.sf.l2j.gameserver.network.serverpackets;

import net.sf.l2j.gameserver.model.actor.instance.L2DoorInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class DoorStatusUpdate extends L2GameServerPacket
{
	private final L2DoorInstance _door;
	private final L2PcInstance _activeChar;
	
	public DoorStatusUpdate(L2DoorInstance door, L2PcInstance activeChar)
	{
		_door = door;
		_activeChar = activeChar;
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0x4d);
		writeD(_door.getObjectId());
		writeD(_door.isOpened() ? 0 : 1);
		writeD(_door.getDamage());
		writeD(_door.isAutoAttackable(_activeChar) ? 1 : 0);
		writeD(_door.getDoorId());
		writeD(_door.getMaxHp());
		writeD((int) _door.getCurrentHp());
	}
}