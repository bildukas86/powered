package events.BlessingOfExperience;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.quest.Quest;
import net.sf.l2j.gameserver.model.quest.QuestState;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;

public class BlessingOfExperience extends Quest
{
	// Reuse between buffs
	int HOURS = Config.BLESSING_HOURS;
	
	// NPC
	int NPC_ID = Config.BLESSING_NPC_ID;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		
		if (event.equalsIgnoreCase("blessingOfExperience"))
		{
			long reuse = 0;
			String stReuse = st.get("reuse");
			if (stReuse != null)
			{
				reuse = Long.parseLong(stReuse);
			}
			
			if (reuse > System.currentTimeMillis())
			{
				long remainingTime = (reuse - System.currentTimeMillis()) / 1000;
				int hours = (int) (remainingTime / 3600);
				int minutes = (int) ((remainingTime % 3600) / 60);
				player.sendMessage("You must wait " + hours + " hour(s) " + minutes + " minute(s) before using the Blessing of Experience.");
			}
			else
			{
				npc.setTarget(player);
				npc.doCast(SkillTable.getInstance().getInfo(7065, 1));
				player.broadcastPacket(new MagicSkillUse(player, player, 5103, 1, 0, 0));
				st.setState(STATE_STARTED);
				st.set("reuse", String.valueOf(System.currentTimeMillis() + (HOURS * 3600000)));
				return "9011-1.htm";
			}
		}
		
		return "9011.htm";
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		if (player.getQuestState(getName()) == null)
		{
			newQuestState(player);
		}
		
		return "9011.htm";
	}
	
	public BlessingOfExperience(int id, String name, String descr)
	{
		super(id, name, descr);
		
		addStartNpc(NPC_ID);
		addFirstTalkId(NPC_ID);
		addTalkId(NPC_ID);
	}
	
	public static void main(String[] args)
	{
		new BlessingOfExperience(-1, BlessingOfExperience.class.getSimpleName(), "events");
	}
}