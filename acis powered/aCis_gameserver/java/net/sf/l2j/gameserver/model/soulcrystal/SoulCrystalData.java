package net.sf.l2j.gameserver.model.soulcrystal;

public final class SoulCrystalData
{
	private final int _level;
	private final int _crystalItemId;
	private final int _stagedItemId;
	private final int _brokenItemId;
	
	public SoulCrystalData(int level, int crystalItemId, int stagedItemId, int brokenItemId)
	{
		_level = level;
		_crystalItemId = crystalItemId;
		_stagedItemId = stagedItemId;
		_brokenItemId = brokenItemId;
	}
	
	public int getLevel()
	{
		return _level;
	}
	
	public int getCrystalItemId()
	{
		return _crystalItemId;
	}
	
	public int getStagedItemId()
	{
		return _stagedItemId;
	}
	
	public int getBrokenItemId()
	{
		return _brokenItemId;
	}
}