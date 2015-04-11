package net.sf.l2j.gameserver.model.actor.instance;

import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;

/**
 * This class is here to avoid hardcoded IDs.<br>
 * It refers to mobs that can be attacked but can also be fed.<br>
 * This class is only used by handlers in order to check the correctness of the target.<br>
 * However, no additional tasks are needed, since they are all handled by scripted AI.
 */
public class L2FeedableBeastInstance extends L2MonsterInstance
{
	public L2FeedableBeastInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
}