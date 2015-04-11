package net.sf.l2j.gameserver.network.serverpackets;

import net.sf.l2j.gameserver.model.actor.L2Character;

/**
 * @author Maktakien
 */
public class OnVehicleCheckLocation extends L2GameServerPacket
{
	private final L2Character _boat;
	
	public OnVehicleCheckLocation(L2Character boat)
	{
		_boat = boat;
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0x5b);
		writeD(_boat.getObjectId());
		writeD(_boat.getX());
		writeD(_boat.getY());
		writeD(_boat.getZ());
		writeD(_boat.getHeading());
	}
}