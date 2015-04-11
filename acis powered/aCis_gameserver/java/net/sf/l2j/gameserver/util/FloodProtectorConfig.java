package net.sf.l2j.gameserver.util;

/**
 * Flood protector configuration
 * @author fordfrog
 */
public final class FloodProtectorConfig
{
	public String FLOOD_PROTECTOR_TYPE;
	
	public int FLOOD_PROTECTION_INTERVAL;
	
	public boolean LOG_FLOODING;
	
	public int PUNISHMENT_LIMIT;
	
	public String PUNISHMENT_TYPE;
	
	public int PUNISHMENT_TIME;
	
	/**
	 * Creates new instance of FloodProtectorConfig.
	 * @param floodProtectorType {@link #FLOOD_PROTECTOR_TYPE}
	 */
	public FloodProtectorConfig(final String floodProtectorType)
	{
		super();
		FLOOD_PROTECTOR_TYPE = floodProtectorType;
	}
}