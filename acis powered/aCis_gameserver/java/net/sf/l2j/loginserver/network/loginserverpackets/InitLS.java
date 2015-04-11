package net.sf.l2j.loginserver.network.loginserverpackets;

import net.sf.l2j.loginserver.L2LoginServer;
import net.sf.l2j.loginserver.network.serverpackets.ServerBasePacket;

/**
 * @author -Wooden-
 */
public class InitLS extends ServerBasePacket
{
	// ID 0x00
	// format
	// d proto rev
	// d key size
	// b key
	
	public InitLS(byte[] publickey)
	{
		writeC(0x00);
		writeD(L2LoginServer.PROTOCOL_REV);
		writeD(publickey.length);
		writeB(publickey);
	}
	
	@Override
	public byte[] getContent()
	{
		return getBytes();
	}
}
