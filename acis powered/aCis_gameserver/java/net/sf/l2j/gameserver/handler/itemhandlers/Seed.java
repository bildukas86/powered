package net.sf.l2j.gameserver.handler.itemhandlers;

import net.sf.l2j.gameserver.datatables.MapRegionTable;
import net.sf.l2j.gameserver.handler.IItemHandler;
import net.sf.l2j.gameserver.instancemanager.CastleManorManager;
import net.sf.l2j.gameserver.model.L2Manor;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.actor.L2Attackable;
import net.sf.l2j.gameserver.model.actor.L2Playable;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.holder.SkillHolder;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;

/**
 * @author l3x
 */
public class Seed implements IItemHandler
{
	@Override
	public void useItem(L2Playable playable, ItemInstance item, boolean forceUse)
	{
		if (!(playable instanceof L2PcInstance))
			return;
		
		if (CastleManorManager.getInstance().isDisabled())
			return;
		
		final L2Object tgt = playable.getTarget();
		
		if (!(tgt instanceof L2Attackable) || !((L2Attackable) tgt).isSeedable())
		{
			playable.sendPacket(SystemMessageId.THE_TARGET_IS_UNAVAILABLE_FOR_SEEDING);
			return;
		}
		
		final L2Attackable target = (L2Attackable) tgt;
		if (target.isDead() || target.isSeeded())
		{
			playable.sendPacket(SystemMessageId.INCORRECT_TARGET);
			return;
		}
		
		final int seedId = item.getItemId();
		if (areaValid(seedId, MapRegionTable.getAreaCastle(playable.getX(), playable.getY())))
		{
			target.setSeeded(seedId, playable.getObjectId());
			final SkillHolder[] skills = item.getEtcItem().getSkills();
			if (skills != null)
			{
				if (skills[0] == null)
					return;
				
				playable.useMagic(skills[0].getSkill(), false, false);
			}
		}
		else
			playable.sendPacket(SystemMessageId.THIS_SEED_MAY_NOT_BE_SOWN_HERE);
	}
	
	private static boolean areaValid(int seedId, int castleId)
	{
		return L2Manor.getInstance().getCastleIdForSeed(seedId) == castleId;
	}
}