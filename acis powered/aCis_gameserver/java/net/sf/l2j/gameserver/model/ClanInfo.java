package net.sf.l2j.gameserver.model;

/**
 * @author UnAfraid
 */
public class ClanInfo
{
	private final L2Clan _clan;
	private final int _total;
	private final int _online;
	
	public ClanInfo(final L2Clan clan)
	{
		_clan = clan;
		_total = clan.getMembersCount();
		_online = clan.getOnlineMembersCount();
	}
	
	public L2Clan getClan()
	{
		return _clan;
	}
	
	public int getTotal()
	{
		return _total;
	}
	
	public int getOnline()
	{
		return _online;
	}
}