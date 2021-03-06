package net.sf.l2j.gameserver.model.zone.type;

import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.zone.L2SpawnZone;
import net.sf.l2j.gameserver.model.zone.ZoneId;
import net.sf.l2j.gameserver.network.SystemMessageId;

/**
 * An arena
 * @author durgus
 */
public class L2ArenaZone extends L2SpawnZone
{
	public L2ArenaZone(int id)
	{
		super(id);
	}
	
	@Override
	protected void onEnter(L2Character character)
	{
		if (character instanceof L2PcInstance)
		{
			if (!character.isInsideZone(ZoneId.PVP))
				((L2PcInstance) character).sendPacket(SystemMessageId.ENTERED_COMBAT_ZONE);
		}
		
		character.setInsideZone(ZoneId.PVP, true);
		character.setInsideZone(ZoneId.NO_SUMMON_FRIEND, true);
	}
	
	@Override
	protected void onExit(L2Character character)
	{
		character.setInsideZone(ZoneId.PVP, false);
		character.setInsideZone(ZoneId.NO_SUMMON_FRIEND, false);
		
		if (character instanceof L2PcInstance)
		{
			if (!character.isInsideZone(ZoneId.PVP))
				((L2PcInstance) character).sendPacket(SystemMessageId.LEFT_COMBAT_ZONE);
		}
	}
	
	@Override
	public void onDieInside(L2Character character)
	{
	}
	
	@Override
	public void onReviveInside(L2Character character)
	{
	}
}