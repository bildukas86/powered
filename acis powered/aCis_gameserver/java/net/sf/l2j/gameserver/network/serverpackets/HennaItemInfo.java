package net.sf.l2j.gameserver.network.serverpackets;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.item.Henna;

public class HennaItemInfo extends L2GameServerPacket
{
	private final L2PcInstance _activeChar;
	private final Henna _henna;
	
	public HennaItemInfo(Henna henna, L2PcInstance player)
	{
		_henna = henna;
		_activeChar = player;
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0xe3);
		writeD(_henna.getSymbolId()); // symbol Id
		writeD(_henna.getDyeId()); // item id of dye
		writeD(Henna.getAmountDyeRequire()); // total amount of dye required
		writeD(_henna.getPrice()); // total amount of adenas required to draw symbol
		writeD(1); // able to draw or not 0 is false and 1 is true
		writeD(_activeChar.getAdena());
		
		writeD(_activeChar.getINT()); // current INT
		writeC(_activeChar.getINT() + _henna.getStatINT()); // equip INT
		writeD(_activeChar.getSTR()); // current STR
		writeC(_activeChar.getSTR() + _henna.getStatSTR()); // equip STR
		writeD(_activeChar.getCON()); // current CON
		writeC(_activeChar.getCON() + _henna.getStatCON()); // equip CON
		writeD(_activeChar.getMEN()); // current MEM
		writeC(_activeChar.getMEN() + _henna.getStatMEN()); // equip MEM
		writeD(_activeChar.getDEX()); // current DEX
		writeC(_activeChar.getDEX() + _henna.getStatDEX()); // equip DEX
		writeD(_activeChar.getWIT()); // current WIT
		writeC(_activeChar.getWIT() + _henna.getStatWIT()); // equip WIT
	}
}