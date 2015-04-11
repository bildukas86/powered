package net.sf.l2j.gameserver.model;

import java.util.List;

/**
 * @author malyelfik
 */
public class TowerSpawn
{
	private final int _npcId;
	private final Location _location;
	private List<Integer> _zoneList;
	private int _upgradeLevel;
	
	public TowerSpawn(int npcId, Location location)
	{
		_location = location;
		_npcId = npcId;
	}
	
	public TowerSpawn(int npcId, Location location, List<Integer> zoneList)
	{
		_location = location;
		_npcId = npcId;
		_zoneList = zoneList;
	}
	
	public int getId()
	{
		return _npcId;
	}
	
	public Location getLocation()
	{
		return _location;
	}
	
	public List<Integer> getZoneList()
	{
		return _zoneList;
	}
	
	public void setUpgradeLevel(int level)
	{
		_upgradeLevel = level;
	}
	
	public int getUpgradeLevel()
	{
		return _upgradeLevel;
	}
}