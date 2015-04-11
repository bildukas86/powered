package net.sf.l2j.gameserver.model;

import net.sf.l2j.gameserver.model.actor.template.NpcTemplate.AIType;

/**
 * Model used for NPC AI related attributes.
 * @author ShanSoft from L2JTW.
 */
public class L2NpcAIData
{
	private AIType _aiType = AIType.DEFAULT;
	
	private int _ssCount;
	private int _ssRate;
	private int _spsCount;
	private int _spsRate;
	
	private int _aggroRange;
	private String[] _clans;
	private int _clanRange;
	private int[] _ignoredIds;
	
	private boolean _canMove;
	private boolean _isSeedable;
	
	public void setAi(String ai)
	{
		for (AIType type : AIType.values())
		{
			if (ai.equalsIgnoreCase(type.name()))
			{
				_aiType = type;
				return;
			}
		}
		_aiType = AIType.DEFAULT;
	}
	
	public void setSsCount(int ssCount)
	{
		_ssCount = ssCount;
	}
	
	public void setSsRate(int ssRate)
	{
		_ssRate = ssRate;
	}
	
	public void setSpsCount(int spsCount)
	{
		_spsCount = spsCount;
	}
	
	public void setSpsRate(int spsRate)
	{
		_spsRate = spsRate;
	}
	
	public void setAggro(int val)
	{
		_aggroRange = val;
	}
	
	public void setClans(String[] clans)
	{
		_clans = clans;
	}
	
	public void setClanRange(int clanRange)
	{
		_clanRange = clanRange;
	}
	
	public void setIgnoredIds(int[] ignoredIds)
	{
		_ignoredIds = ignoredIds;
	}
	
	public void setCanMove(boolean canMove)
	{
		_canMove = canMove;
	}
	
	public void setSeedable(boolean isSeedable)
	{
		_isSeedable = isSeedable;
	}
	
	public AIType getAiType()
	{
		return _aiType;
	}
	
	public int getSsCount()
	{
		return _ssCount;
	}
	
	public int getSsRate()
	{
		return _ssRate;
	}
	
	public int getSpsCount()
	{
		return _spsCount;
	}
	
	public int getSpsRate()
	{
		return _spsRate;
	}
	
	public int getAggroRange()
	{
		return _aggroRange;
	}
	
	public String[] getClans()
	{
		return _clans;
	}
	
	public int getClanRange()
	{
		return _clanRange;
	}
	
	public int[] getIgnoredIds()
	{
		return _ignoredIds;
	}
	
	public boolean canMove()
	{
		return _canMove;
	}
	
	public boolean isSeedable()
	{
		return _isSeedable;
	}
}