package net.sf.l2j.gameserver.network.serverpackets;

import net.sf.l2j.gameserver.model.L2Clan.RankPrivs;
import net.sf.l2j.gameserver.model.L2ClanMember;

public class PledgePowerGradeList extends L2GameServerPacket
{
	private final RankPrivs[] _privs;
	private final L2ClanMember[] _members;
	
	public PledgePowerGradeList(RankPrivs[] privs, L2ClanMember[] members)
	{
		_privs = privs;
		_members = members;
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0xFE);
		writeH(0x3b);
		writeD(_privs.length);
		for (RankPrivs _priv : _privs)
		{
			writeD(_priv.getRank());
			
			int count = 0;
			for (L2ClanMember member : _members)
			{
				if (member.getPowerGrade() == _priv.getRank())
					count++;
			}
			writeD(count);
		}
	}
}