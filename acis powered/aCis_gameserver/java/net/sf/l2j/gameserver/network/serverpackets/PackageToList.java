package net.sf.l2j.gameserver.network.serverpackets;

import java.util.Map;

/**
 * Format: (c) d[dS] d: list size [ d: char ID S: char Name ]
 * @author -Wooden-
 */
public class PackageToList extends L2GameServerPacket
{
	private final Map<Integer, String> _players;
	
	public PackageToList(Map<Integer, String> players)
	{
		_players = players;
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0xC2);
		writeD(_players.size());
		for (int objId : _players.keySet())
		{
			writeD(objId);
			writeS(_players.get(objId));
		}
	}
}