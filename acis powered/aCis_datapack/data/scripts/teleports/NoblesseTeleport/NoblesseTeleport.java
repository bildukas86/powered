package teleports.NoblesseTeleport;

import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.quest.Quest;

public class NoblesseTeleport extends Quest
{
	public NoblesseTeleport(int questid, String name, String descr)
	{
		super(questid, name, descr);
		
		addStartNpc(30006, 30059, 30080, 30134, 30146, 30177, 30233, 30256, 30320, 30540, 30576, 30836, 30848, 30878, 30899, 31275, 31320, 31964);
		addTalkId(30006, 30059, 30080, 30134, 30146, 30177, 30233, 30256, 30320, 30540, 30576, 30836, 30848, 30878, 30899, 31275, 31320, 31964);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		if (player.isNoble())
			return "noble.htm";
		
		return "nobleteleporter-no.htm";
	}
	
	public static void main(String[] args)
	{
		new NoblesseTeleport(-1, "NoblesseTeleport", "teleports");
	}
}