package net.sf.l2j.gameserver.model.actor.stat;

import net.sf.l2j.gameserver.model.actor.L2Vehicle;

public class VehicleStat extends CharStat
{
	private int _moveSpeed = 0;
	private int _rotationSpeed = 0;
	
	public VehicleStat(L2Vehicle activeChar)
	{
		super(activeChar);
	}
	
	@Override
	public int getMoveSpeed()
	{
		return _moveSpeed;
	}
	
	public final void setMoveSpeed(int speed)
	{
		_moveSpeed = speed;
	}
	
	public final int getRotationSpeed()
	{
		return _rotationSpeed;
	}
	
	public final void setRotationSpeed(int speed)
	{
		_rotationSpeed = speed;
	}
}