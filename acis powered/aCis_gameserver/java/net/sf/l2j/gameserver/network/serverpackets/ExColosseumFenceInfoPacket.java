package net.sf.l2j.gameserver.network.serverpackets;

import net.sf.l2j.gameserver.model.actor.instance.L2FenceInstance;

public class ExColosseumFenceInfoPacket extends L2GameServerPacket
{
	private static final String _S__FE_03_EXCOLOSSEUMFENCEINFOPACKET = "[S] FE:03 ExColosseumFenceInfoPacket";
	private L2FenceInstance _fence;
	
	public ExColosseumFenceInfoPacket(L2FenceInstance fence)
	{
		_fence = fence;
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0xfe);
		writeH(0x09);
		
		writeD(_fence.getObjectId());
		writeD(_fence.getType());
		writeD(_fence.getX());
		writeD(_fence.getY());
		writeD(_fence.getZ());
		writeD(_fence.getWidth());
		writeD(_fence.getLength());
	}
	
	@Override
	public String getType()
	{
		return _S__FE_03_EXCOLOSSEUMFENCEINFOPACKET;
	}
}
