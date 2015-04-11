package ai.group;

import ai.AbstractNpcAI;

import net.sf.l2j.gameserver.model.actor.L2Attackable;
import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.quest.QuestEventType;

public class SearchingMaster extends AbstractNpcAI
{
	private static final int[] mobs =
	{
		20965,
		20966,
		20967,
		20968,
		20969,
		20970,
		20971,
		20972,
		20973
	};
	
	public SearchingMaster(String name, String descr)
	{
		super(name, descr);
		registerMobs(mobs, QuestEventType.ON_ATTACK);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance player, int damage, boolean isPet)
	{
		if (player == null)
			return null;
		
		attack(((L2Attackable) npc), player);
		return super.onAttack(npc, player, damage, isPet);
	}
	
	public static void main(String[] args)
	{
		new SearchingMaster(SearchingMaster.class.getSimpleName(), "ai/group");
	}
}