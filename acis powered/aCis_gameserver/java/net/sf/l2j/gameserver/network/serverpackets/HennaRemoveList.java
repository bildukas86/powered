package net.sf.l2j.gameserver.network.serverpackets;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.item.Henna;

public class HennaRemoveList extends L2GameServerPacket
{
	private final L2PcInstance _player;
	
	public HennaRemoveList(L2PcInstance player)
	{
		_player = player;
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0xe5);
		writeD(_player.getAdena());
		writeD(_player.getHennaEmptySlots());
		writeD(Math.abs(_player.getHennaEmptySlots() - 3));
		
		for (int i = 1; i <= 3; i++)
		{
			Henna henna = _player.getHenna(i);
			if (henna != null)
			{
				writeD(henna.getSymbolId());
				writeD(henna.getDyeId());
				writeD(Henna.getAmountDyeRequire() / 2);
				writeD(henna.getPrice() / 5);
				writeD(0x01);
			}
		}
	}
}