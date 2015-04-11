package net.sf.l2j.gameserver.model.actor.instance;

import Extensions.Events.StriderRace;

import net.sf.l2j.gameserver.ai.CtrlIntention;
import net.sf.l2j.gameserver.datatables.SpawnTable;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.L2Spawn;
import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.MyTargetSelected;
import net.sf.l2j.gameserver.network.serverpackets.ValidateLocation;

public class L2StriderRaceEndInstance extends L2NpcInstance
{
	public L2StriderRaceEndInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void onAction(L2PcInstance player)
	{
		
		if (this != player.getTarget())
		{
			player.setTarget(this);
			MyTargetSelected mts = new MyTargetSelected(getObjectId(), 0);
			player.sendPacket(mts);
			player.sendPacket(new ValidateLocation(this));
		}
		else
		{
			if (!canInteract(player))
			{
				player.getAI().setIntention(CtrlIntention.INTERACT, this);
			}
			else
			{
				if (StriderRace.EventState == StriderRace.State.RACING && StriderRace._players.contains(player))
				{
					StriderRace.endRace(player);
					L2Object obj = player.getTarget();
					if (obj != null && obj instanceof L2Npc)
					{
						L2Npc target = (L2Npc) obj;
						target.deleteMe();
						
						L2Spawn spawn = target.getSpawn();
						if (spawn != null)
						{
							spawn.stopRespawn();
							
							SpawnTable.getInstance().deleteSpawn(spawn, true);
						}
					}
				}
			}
		}
		
		player.sendPacket(ActionFailed.STATIC_PACKET);
	}
}