package net.sf.l2j.gameserver.network.serverpackets;

/**
 * This class makes runImpl() and writeImpl() abstract for custom classes outside of this package
 * @author galun
 */
public abstract class AbstractServerBasePacket extends L2GameServerPacket
{
	@Override
	abstract public void runImpl();
	
	@Override
	abstract protected void writeImpl();
}