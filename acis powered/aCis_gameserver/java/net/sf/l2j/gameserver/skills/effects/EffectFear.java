package net.sf.l2j.gameserver.skills.effects;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.ai.CtrlIntention;
import net.sf.l2j.gameserver.geoengine.PathFinding;
import net.sf.l2j.gameserver.model.L2CharPosition;
import net.sf.l2j.gameserver.model.L2Effect;
import net.sf.l2j.gameserver.model.Location;
import net.sf.l2j.gameserver.model.actor.instance.L2NpcInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PetInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2SiegeFlagInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2SiegeSummonInstance;
import net.sf.l2j.gameserver.skills.Env;
import net.sf.l2j.gameserver.templates.skills.L2EffectFlag;
import net.sf.l2j.gameserver.templates.skills.L2EffectType;

/**
 * Implementation of the Fear Effect
 * @author littlecrow
 */
public class EffectFear extends L2Effect
{
	public static final int FEAR_RANGE = 500;
	
	public EffectFear(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.FEAR;
	}
	
	@Override
	public boolean onStart()
	{
		if (getEffected() instanceof L2PcInstance && getEffector() instanceof L2PcInstance)
		{
			switch (getSkill().getId())
			{
				case 1376:
				case 1169:
				case 65:
				case 1092:
				case 98:
				case 1272:
				case 1381:
				case 763:
					break;
				default:
					return false;
			}
		}
		
		if (getEffected() instanceof L2NpcInstance || getEffected() instanceof L2SiegeFlagInstance || getEffected() instanceof L2SiegeSummonInstance)
			return false;
		
		if (getEffected().isAfraid())
			return false;
		
		getEffected().startFear();
		onActionTime();
		return true;
	}
	
	@Override
	public void onExit()
	{
		getEffected().stopFear(true);
	}
	
	@Override
	public boolean onActionTime()
	{
		int posX = getEffected().getX() + (((getEffected().getX() > getEffector().getX()) ? 1 : -1) * FEAR_RANGE);
		int posY = getEffected().getY() + (((getEffected().getY() > getEffector().getY()) ? 1 : -1) * FEAR_RANGE);
		int posZ = getEffected().getZ();
		
		if (Config.GEODATA > 0)
		{
			Location destiny = PathFinding.getInstance().canMoveToTargetLoc(getEffected().getX(), getEffected().getY(), getEffected().getZ(), posX, posY, posZ);
			posX = destiny.getX();
			posY = destiny.getY();
		}
		
		if (!(getEffected() instanceof L2PetInstance))
			getEffected().setRunning();
		
		getEffected().getAI().setIntention(CtrlIntention.MOVE_TO, new L2CharPosition(posX, posY, posZ, 0));
		return true;
	}
	
	@Override
	public int getEffectFlags()
	{
		return L2EffectFlag.FEAR.getMask();
	}
}