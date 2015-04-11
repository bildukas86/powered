package net.sf.l2j.gameserver.network.serverpackets;

import java.util.ArrayList;
import java.util.List;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.partymatching.PartyMatchRoom;
import net.sf.l2j.gameserver.model.partymatching.PartyMatchRoomList;

public class PartyMatchList extends L2GameServerPacket
{
	private final L2PcInstance _cha;
	private final int _loc;
	private final int _lim;
	private final List<PartyMatchRoom> _rooms;
	
	public PartyMatchList(L2PcInstance player, int auto, int location, int limit)
	{
		_cha = player;
		_loc = location;
		_lim = limit;
		_rooms = new ArrayList<>();
	}
	
	@Override
	protected final void writeImpl()
	{
		if (getClient().getActiveChar() == null)
			return;
		
		for (PartyMatchRoom room : PartyMatchRoomList.getInstance().getRooms())
		{
			if (room.getMembers() < 1 || room.getOwner() == null || !room.getOwner().isOnline() || room.getOwner().getPartyRoom() != room.getId())
			{
				PartyMatchRoomList.getInstance().deleteRoom(room.getId());
				continue;
			}
			
			if (_loc > 0 && _loc != room.getLocation())
				continue;
			
			if (_lim == 0 && ((_cha.getLevel() < room.getMinLvl()) || (_cha.getLevel() > room.getMaxLvl())))
				continue;
			
			_rooms.add(room);
		}
		
		int count = 0;
		int size = _rooms.size();
		
		writeC(0x96);
		if (size > 0)
			writeD(1);
		else
			writeD(0);
		
		writeD(_rooms.size());
		while (size > count)
		{
			writeD(_rooms.get(count).getId());
			writeS(_rooms.get(count).getTitle());
			writeD(_rooms.get(count).getLocation());
			writeD(_rooms.get(count).getMinLvl());
			writeD(_rooms.get(count).getMaxLvl());
			writeD(_rooms.get(count).getMembers());
			writeD(_rooms.get(count).getMaxMembers());
			writeS(_rooms.get(count).getOwner().getName());
			count++;
		}
	}
}