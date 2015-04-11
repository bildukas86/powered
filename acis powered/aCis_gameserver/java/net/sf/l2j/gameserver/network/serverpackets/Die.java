package net.sf.l2j.gameserver.network.serverpackets;

import Extensions.Events.Phoenix.EventManager;

import net.sf.l2j.gameserver.instancemanager.CastleManager;
import net.sf.l2j.gameserver.model.L2Clan;
import net.sf.l2j.gameserver.model.L2SiegeClan;
import net.sf.l2j.gameserver.model.actor.L2Attackable;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.entity.Castle;

public class Die extends L2GameServerPacket
{
	private final int _charObjId;
	private final boolean _fake;
	
	private boolean _sweepable;
	private boolean _allowFixedRes;
	private L2Clan _clan;
	L2Character _activeChar;
	private boolean _event;
	
	public Die(L2Character cha)
	{
		_activeChar = cha;
		_charObjId = cha.getObjectId();
		_fake = !cha.isDead();
		
		if (cha instanceof L2PcInstance)
		{
			L2PcInstance player = (L2PcInstance) cha;
			_allowFixedRes = player.getAccessLevel().allowFixedRes();
			_clan = player.getClan();
			_event = EventManager.getInstance().isRegistered(cha);
		}
		else if (cha instanceof L2Attackable)
			_sweepable = ((L2Attackable) cha).isSpoiled();
	}
	
	@Override
	protected final void writeImpl()
	{
		if (_fake)
			return;
		
		writeC(0x06);
		writeD(_charObjId);
		if (_event)
		{
			writeD(0x00);
			writeD(0x00);
			writeD(0x00);
			writeD(0x00);
		}
		else
		{
			writeD(0x01); // to nearest village
			
			if (_clan != null)
			{
				L2SiegeClan siegeClan = null;
				boolean isInDefense = false;
				
				Castle castle = CastleManager.getInstance().getCastle(_activeChar);
				if (castle != null && castle.getSiege().isInProgress())
				{
					// siege in progress
					siegeClan = castle.getSiege().getAttackerClan(_clan);
					if (siegeClan == null && castle.getSiege().checkIsDefender(_clan))
						isInDefense = true;
				}
				
				writeD(_clan.hasHideout() ? 0x01 : 0x00); // to hide away
				writeD(_clan.hasCastle() || isInDefense ? 0x01 : 0x00); // to castle
				writeD(siegeClan != null && !isInDefense && !siegeClan.getFlags().isEmpty() ? 0x01 : 0x00); // to siege HQ
			}
			else
			{
				writeD(0x00); // to hide away
				writeD(0x00); // to castle
				writeD(0x00); // to siege HQ
			}
		}
		
		writeD(_sweepable ? 0x01 : 0x00); // sweepable (blue glow)
		writeD(_allowFixedRes ? 0x01 : 0x00); // FIXED
	}
}