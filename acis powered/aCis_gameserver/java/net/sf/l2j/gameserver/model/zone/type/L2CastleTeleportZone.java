package net.sf.l2j.gameserver.model.zone.type;

import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.zone.L2ZoneType;
import net.sf.l2j.gameserver.model.zone.ZoneId;
import net.sf.l2j.util.Rnd;

/**
 * A castle teleporter zone used for Mass Gatekeepers
 * @author Kerberos
 */
public class L2CastleTeleportZone extends L2ZoneType
{
	private final int[] _spawnLoc;
	private int _castleId;
	
	public L2CastleTeleportZone(int id)
	{
		super(id);
		
		_spawnLoc = new int[5];
	}
	
	@Override
	public void setParameter(String name, String value)
	{
		if (name.equals("castleId"))
			_castleId = Integer.parseInt(value);
		else if (name.equals("spawnMinX"))
			_spawnLoc[0] = Integer.parseInt(value);
		else if (name.equals("spawnMaxX"))
			_spawnLoc[1] = Integer.parseInt(value);
		else if (name.equals("spawnMinY"))
			_spawnLoc[2] = Integer.parseInt(value);
		else if (name.equals("spawnMaxY"))
			_spawnLoc[3] = Integer.parseInt(value);
		else if (name.equals("spawnZ"))
			_spawnLoc[4] = Integer.parseInt(value);
		else
			super.setParameter(name, value);
	}
	
	@Override
	protected void onEnter(L2Character character)
	{
		character.setInsideZone(ZoneId.NO_SUMMON_FRIEND, true);
	}
	
	@Override
	protected void onExit(L2Character character)
	{
		character.setInsideZone(ZoneId.NO_SUMMON_FRIEND, false);
	}
	
	@Override
	public void onDieInside(L2Character character)
	{
	}
	
	@Override
	public void onReviveInside(L2Character character)
	{
	}
	
	public void oustAllPlayers()
	{
		if (_characterList.isEmpty())
			return;
		
		for (L2PcInstance player : getKnownTypeInside(L2PcInstance.class))
		{
			if (player.isOnline())
				player.teleToLocation(Rnd.get(_spawnLoc[0], _spawnLoc[1]), Rnd.get(_spawnLoc[2], _spawnLoc[3]), _spawnLoc[4], 0);
		}
	}
	
	public int getCastleId()
	{
		return _castleId;
	}
	
	/**
	 * Get the spawn locations
	 * @return
	 */
	public int[] getSpawn()
	{
		return _spawnLoc;
	}
}