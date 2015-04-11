package net.sf.l2j.gameserver.network.serverpackets;

import net.sf.l2j.gameserver.model.Location;

/**
 * @author Maktakien
 */
public class GetOnVehicle extends L2GameServerPacket
{
	private final int _charObjId;
	private final int _boatObjId;
	private final Location _pos;
	
	public GetOnVehicle(int charObjId, int boatObjId, Location pos)
	{
		_charObjId = charObjId;
		_boatObjId = boatObjId;
		_pos = pos;
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0x5C);
		writeD(_charObjId);
		writeD(_boatObjId);
		writeD(_pos.getX());
		writeD(_pos.getY());
		writeD(_pos.getZ());
	}
}