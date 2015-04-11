package net.sf.l2j.gameserver.model.actor.instance;

import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;

public class L2RiftInvaderInstance extends L2MonsterInstance
{
	// Not longer needed since rift monster targeting control now is handled by the room zones for any mob
	public L2RiftInvaderInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
}