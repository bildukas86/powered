package ai.group;

import ai.AbstractNpcAI;

import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class GatekeeperZombies extends AbstractNpcAI
{
	public GatekeeperZombies(String name, String descr)
	{
		super(name, descr);
		addAggroRangeEnterId(22136);
	}
	
	@Override
	public String onAggroRangeEnter(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		if (player.getInventory().hasAtLeastOneItem(8064, 8065, 8067))
			return null;
		
		return super.onAggroRangeEnter(npc, player, isPet);
	}
	
	public static void main(String[] args)
	{
		new GatekeeperZombies(GatekeeperZombies.class.getSimpleName(), "ai/group");
	}
}