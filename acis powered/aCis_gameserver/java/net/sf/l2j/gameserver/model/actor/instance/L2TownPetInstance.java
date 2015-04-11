package net.sf.l2j.gameserver.model.actor.instance;

import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.ai.CtrlIntention;
import net.sf.l2j.gameserver.model.L2CharPosition;
import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.MoveToPawn;
import net.sf.l2j.util.Rnd;

public class L2TownPetInstance extends L2NpcInstance
{
	int randomX, randomY, spawnX, spawnY;
	
	public L2TownPetInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
		setRunning();
		
		ThreadPoolManager.getInstance().scheduleAiAtFixedRate(new RandomWalkTask(), 1000, 10000);
	}
	
	@Override
	public void onAction(L2PcInstance player)
	{
		// Set the target of the L2PcInstance player
		if (player.getTarget() != this)
			player.setTarget(this);
		else
		{
			if (!canInteract(player))
				player.getAI().setIntention(CtrlIntention.INTERACT, this);
			else
			{
				// Rotate the player to face the instance
				player.sendPacket(new MoveToPawn(player, this, L2Npc.INTERACTION_DISTANCE));
				
				// Send ActionFailed to the player in order to avoid he stucks
				player.sendPacket(ActionFailed.STATIC_PACKET);
			}
		}
	}
	
	@Override
	public void onSpawn()
	{
		super.onSpawn();
		spawnX = getX();
		spawnY = getY();
	}
	
	public class RandomWalkTask implements Runnable
	{
		@Override
		public void run()
		{
			randomX = spawnX + Rnd.get(150) - 75;
			randomY = spawnY + Rnd.get(150) - 75;
			
			if ((randomX != getX()) && (randomY != getY()))
				getAI().setIntention(CtrlIntention.MOVE_TO, new L2CharPosition(randomX, randomY, getZ(), 0));
		}
	}
}