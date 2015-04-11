package custom.ChangeSex;

import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.quest.Quest;
import net.sf.l2j.gameserver.model.quest.QuestState;
import net.sf.l2j.gameserver.instancemanager.QuestManager;

public class ChangeSex extends Quest
{
	// Npc
	private static final int NpcId = 13; // Custom Npc
	// Item
	private static final int ItemId = 57; // Adena
	
	public ChangeSex(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(NpcId);
		addFirstTalkId(NpcId);
		addTalkId(NpcId);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = player.getQuestState(getName());
		
		if (event.equalsIgnoreCase("change_sex"))
		{
			if (st.getQuestItemsCount(ItemId) >= 1)
			{
				st.takeItems(ItemId, 1);
				player.getAppearance().setSex(player.getAppearance().getSex() ? false : true);
				player.sendMessage("Your gender has been changed!");
				player.broadcastUserInfo();
				player.decayMe();
				player.spawnMe(player.getX(), player.getY(), player.getZ());
				return null;
			}
			else
				return "49297-no.htm";
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			Quest q = QuestManager.getInstance().getQuest(getName());
			st = q.newQuestState(player);
		}
		return "49297.htm";
	}
	
	public static void main(String[] args)
	{
		new ChangeSex(-1, "ChangeSex", "custom");
	}
}