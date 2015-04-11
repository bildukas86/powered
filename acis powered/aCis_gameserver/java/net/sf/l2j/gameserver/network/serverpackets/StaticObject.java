package net.sf.l2j.gameserver.network.serverpackets;

import net.sf.l2j.gameserver.model.actor.instance.L2StaticObjectInstance;

/**
 * format dd
 */
public class StaticObject extends L2GameServerPacket
{
	private final L2StaticObjectInstance _staticObject;
	
	public StaticObject(L2StaticObjectInstance StaticObject)
	{
		_staticObject = StaticObject;
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0x99);
		writeD(_staticObject.getStaticObjectId()); // staticObjectId
		writeD(_staticObject.getObjectId()); // objectId
	}
}