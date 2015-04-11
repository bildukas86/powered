package teleports.ElrokiTeleporters;

import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.quest.Quest;

public class ElrokiTeleporters extends Quest
{
	public ElrokiTeleporters(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(32111, 32112);
		addTalkId(32111, 32112);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		if (npc.getNpcId() == 32111)
		{
			if (player.isInCombat())
				return "32111-no.htm";
			
			player.teleToLocation(4990, -1879, -3178, 0);
		}
		else
			player.teleToLocation(7557, -5513, -3221, 0);
		
		return null;
	}
	
	public static void main(String[] args)
	{
		new ElrokiTeleporters(-1, "ElrokiTeleporters", "teleports");
	}
}