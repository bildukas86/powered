package net.sf.l2j.gameserver.model.actor.instance;

import net.sf.l2j.gameserver.model.actor.L2Attackable;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.knownlist.FriendlyMobKnownList;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;

/**
 * This class represents Friendly Mobs lying over the world. These friendly mobs should only attack players with karma > 0 and it is always aggro, since it just attacks players with karma
 */
public class L2FriendlyMobInstance extends L2Attackable
{
	public L2FriendlyMobInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void initKnownList()
	{
		setKnownList(new FriendlyMobKnownList(this));
	}
	
	@Override
	public final FriendlyMobKnownList getKnownList()
	{
		return (FriendlyMobKnownList) super.getKnownList();
	}
	
	@Override
	public boolean isAutoAttackable(L2Character attacker)
	{
		if (attacker instanceof L2PcInstance)
			return ((L2PcInstance) attacker).getKarma() > 0;
		
		return false;
	}
	
	@Override
	public boolean isAggressive()
	{
		return true;
	}
}