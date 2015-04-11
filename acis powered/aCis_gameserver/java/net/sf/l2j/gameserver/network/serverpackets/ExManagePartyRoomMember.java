package net.sf.l2j.gameserver.network.serverpackets;

import net.sf.l2j.gameserver.datatables.MapRegionTable;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.partymatching.PartyMatchRoom;

/**
 * @author Gnacik Mode : 0 - add 1 - modify 2 - quit
 */
public class ExManagePartyRoomMember extends L2GameServerPacket
{
	private final L2PcInstance _activeChar;
	private final PartyMatchRoom _room;
	private final int _mode;
	
	public ExManagePartyRoomMember(L2PcInstance player, PartyMatchRoom room, int mode)
	{
		_activeChar = player;
		_room = room;
		_mode = mode;
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0xfe);
		writeH(0x10);
		writeD(_mode);
		writeD(_activeChar.getObjectId());
		writeS(_activeChar.getName());
		writeD(_activeChar.getActiveClass());
		writeD(_activeChar.getLevel());
		writeD(MapRegionTable.getClosestLocation(_activeChar.getX(), _activeChar.getY()));
		if (_room.getOwner().equals(_activeChar))
			writeD(1);
		else
		{
			if ((_room.getOwner().isInParty() && _activeChar.isInParty()) && (_room.getOwner().getParty().getPartyLeaderOID() == _activeChar.getParty().getPartyLeaderOID()))
				writeD(2);
			else
				writeD(0);
		}
	}
}