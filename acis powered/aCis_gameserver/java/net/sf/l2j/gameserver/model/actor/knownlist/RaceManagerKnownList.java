package net.sf.l2j.gameserver.model.actor.knownlist;

import net.sf.l2j.gameserver.instancemanager.games.MonsterRace;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2RaceManagerInstance;
import net.sf.l2j.gameserver.network.serverpackets.DeleteObject;

public class RaceManagerKnownList extends NpcKnownList
{
	public RaceManagerKnownList(L2RaceManagerInstance activeChar)
	{
		super(activeChar);
	}
	
	@Override
	public boolean addKnownObject(L2Object object)
	{
		if (!super.addKnownObject(object))
			return false;
		
		if (object instanceof L2PcInstance)
			((L2PcInstance) object).sendPacket(MonsterRace.getInstance().getRacePacket());
		
		return true;
	}
	
	@Override
	public boolean removeKnownObject(L2Object object)
	{
		if (!super.removeKnownObject(object))
			return false;
		
		if (object instanceof L2PcInstance)
		{
			final L2PcInstance player = ((L2PcInstance) object);
			for (int i = 0; i < 8; i++)
				player.sendPacket(new DeleteObject(MonsterRace.getInstance().getMonsters()[i]));
		}
		return true;
	}
	
	@Override
	public L2RaceManagerInstance getActiveChar()
	{
		return (L2RaceManagerInstance) super.getActiveChar();
	}
}