package net.sf.l2j.gameserver.model.base;

/**
 * This class defines all races that a player can choose.
 */
public enum Race
{
	Human(1),
	Elf(1.5),
	DarkElf(1.5),
	Orc(0.9),
	Dwarf(0.8);
	
	private final double _breathMultiplier;
	
	private Race(double breathMultiplier)
	{
		_breathMultiplier = breathMultiplier;
	}
	
	/**
	 * @return the breath multiplier.
	 */
	public double getBreathMultiplier()
	{
		return _breathMultiplier;
	}
}