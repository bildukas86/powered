package net.sf.l2j.gameserver.network.serverpackets;

import java.util.ArrayList;
import java.util.List;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.partymatching.PartyMatchRoom;
import net.sf.l2j.gameserver.model.partymatching.PartyMatchRoomList;
import net.sf.l2j.gameserver.model.partymatching.PartyMatchWaitingList;

/**
 * @author Gnacik
 */
public class ExListPartyMatchingWaitingRoom extends L2GameServerPacket
{
	private final L2PcInstance _activeChar;
	@SuppressWarnings("unused")
	private final int _page;
	private final int _minlvl;
	private final int _maxlvl;
	private final int _mode;
	private final List<L2PcInstance> _members;
	
	public ExListPartyMatchingWaitingRoom(L2PcInstance player, int page, int minlvl, int maxlvl, int mode)
	{
		_activeChar = player;
		_page = page;
		_minlvl = minlvl;
		_maxlvl = maxlvl;
		_mode = mode;
		_members = new ArrayList<>();
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0xfe);
		writeH(0x35);
		
		// If the mode is 0 and the activeChar isn't the PartyRoom leader, return an empty list.
		if (_mode == 0)
		{
			// Retrieve the activeChar PartyMatchRoom
			PartyMatchRoom _room = PartyMatchRoomList.getInstance().getRoom(_activeChar.getPartyRoom());
			if (!_room.getOwner().equals(_activeChar))
			{
				writeD(0);
				writeD(0);
				return;
			}
		}
		
		for (L2PcInstance cha : PartyMatchWaitingList.getInstance().getPlayers())
		{
			// Don't add yourself in the list
			if (cha == null || cha == _activeChar)
				continue;
			
			if (!cha.isPartyWaiting())
			{
				PartyMatchWaitingList.getInstance().removePlayer(cha);
				continue;
			}
			
			if ((cha.getLevel() < _minlvl) || (cha.getLevel() > _maxlvl))
				continue;
			
			_members.add(cha);
		}
		
		int _count = 0;
		int _size = _members.size();
		
		writeD(1);
		writeD(_size);
		while (_size > _count)
		{
			writeS(_members.get(_count).getName());
			writeD(_members.get(_count).getActiveClass());
			writeD(_members.get(_count).getLevel());
			_count++;
		}
	}
}