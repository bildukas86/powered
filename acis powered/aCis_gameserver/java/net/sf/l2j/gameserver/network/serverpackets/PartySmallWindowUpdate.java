package net.sf.l2j.gameserver.network.serverpackets;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class PartySmallWindowUpdate extends L2GameServerPacket
{
	private final L2PcInstance _member;
	
	public PartySmallWindowUpdate(L2PcInstance member)
	{
		_member = member;
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0x52);
		writeD(_member.getObjectId());
		writeS(_member.getName());
		
		writeD((int) _member.getCurrentCp()); // c4
		writeD(_member.getMaxCp()); // c4
		writeD((int) _member.getCurrentHp());
		writeD(_member.getMaxHp());
		writeD((int) _member.getCurrentMp());
		writeD(_member.getMaxMp());
		
		writeD(_member.getLevel());
		writeD(_member.getClassId().getId());
	}
}