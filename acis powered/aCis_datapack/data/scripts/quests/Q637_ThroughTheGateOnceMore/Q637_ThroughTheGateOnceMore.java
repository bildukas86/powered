package quests.Q637_ThroughTheGateOnceMore;

import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.quest.Quest;
import net.sf.l2j.gameserver.model.quest.QuestState;

public class Q637_ThroughTheGateOnceMore extends Quest
{
	private static final String qn = "Q637_ThroughTheGateOnceMore";
	
	// NPC
	private static final int FLAURON = 32010;
	
	// Items
	private static final int FADED_VISITOR_MARK = 8065;
	private static final int NECROMANCER_HEART = 8066;
	
	// Reward
	private static final int PAGAN_MARK = 8067;
	
	public Q637_ThroughTheGateOnceMore()
	{
		super(637, qn, "Through the Gate Once More");
		
		setItemsIds(NECROMANCER_HEART);
		
		addStartNpc(FLAURON);
		addTalkId(FLAURON);
		
		addKillId(21565, 21566, 21567);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		if (st == null)
			return htmltext;
		
		if (event.equalsIgnoreCase("32010-04.htm"))
		{
			st.setState(STATE_STARTED);
			st.set("cond", "1");
			st.playSound(QuestState.SOUND_ACCEPT);
		}
		else if (event.equalsIgnoreCase("32010-10.htm"))
			st.exitQuest(true);
		
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(qn);
		String htmltext = getNoQuestMsg();
		if (st == null)
			return htmltext;
		
		switch (st.getState())
		{
			case STATE_CREATED:
				if (player.getLevel() < 73 || !st.hasQuestItems(FADED_VISITOR_MARK))
					htmltext = "32010-01a.htm";
				else if (st.hasQuestItems(PAGAN_MARK))
					htmltext = "32010-00.htm";
				else
					htmltext = "32010-01.htm";
				break;
			
			case STATE_STARTED:
				if (st.getInt("cond") == 2)
				{
					if (st.getQuestItemsCount(NECROMANCER_HEART) == 10)
					{
						htmltext = "32010-06.htm";
						st.takeItems(FADED_VISITOR_MARK, 1);
						st.takeItems(NECROMANCER_HEART, -1);
						st.giveItems(PAGAN_MARK, 1);
						st.giveItems(8273, 10);
						st.playSound(QuestState.SOUND_FINISH);
						st.exitQuest(true);
					}
					else
						st.set("cond", "1");
				}
				else
					htmltext = "32010-05.htm";
				break;
		}
		
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		L2PcInstance partyMember = getRandomPartyMember(player, npc, "1");
		if (partyMember == null)
			return null;
		
		QuestState st = partyMember.getQuestState(qn);
		
		if (st.dropItems(NECROMANCER_HEART, 1, 10, 400000))
			st.set("cond", "2");
		
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q637_ThroughTheGateOnceMore();
	}
}