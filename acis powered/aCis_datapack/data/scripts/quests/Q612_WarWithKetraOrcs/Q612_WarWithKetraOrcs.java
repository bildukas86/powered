package quests.Q612_WarWithKetraOrcs;

import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.quest.Quest;
import net.sf.l2j.gameserver.model.quest.QuestState;

/**
 * The onKill section of that quest is directly written on Q611.
 */
public class Q612_WarWithKetraOrcs extends Quest
{
	private static final String qn = "Q612_WarWithKetraOrcs";
	
	// Items
	private static final int NEPENTHES_SEED = 7187;
	private static final int MOLAR_OF_KETRA_ORC = 7234;
	
	public Q612_WarWithKetraOrcs()
	{
		super(612, qn, "War with Ketra Orcs");
		
		setItemsIds(MOLAR_OF_KETRA_ORC);
		
		addStartNpc(31377); // Ashas Varka Durai
		addTalkId(31377);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		if (st == null)
			return htmltext;
		
		if (event.equalsIgnoreCase("31377-03.htm"))
		{
			st.setState(STATE_STARTED);
			st.set("cond", "1");
			st.playSound(QuestState.SOUND_ACCEPT);
		}
		else if (event.equalsIgnoreCase("31377-07.htm"))
		{
			if (st.getQuestItemsCount(MOLAR_OF_KETRA_ORC) >= 100)
			{
				st.playSound(QuestState.SOUND_ITEMGET);
				st.takeItems(MOLAR_OF_KETRA_ORC, 100);
				st.giveItems(NEPENTHES_SEED, 20);
			}
			else
				htmltext = "31377-08.htm";
		}
		else if (event.equalsIgnoreCase("31377-09.htm"))
		{
			st.takeItems(MOLAR_OF_KETRA_ORC, -1);
			st.exitQuest(true);
		}
		
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg();
		QuestState st = player.getQuestState(qn);
		if (st == null)
			return htmltext;
		
		switch (st.getState())
		{
			case STATE_CREATED:
				htmltext = (player.getLevel() >= 74 && player.isAlliedWithVarka()) ? "31377-01.htm" : "31377-02.htm";
				break;
			
			case STATE_STARTED:
				htmltext = (st.hasQuestItems(MOLAR_OF_KETRA_ORC)) ? "31377-04.htm" : "31377-05.htm";
				break;
		}
		
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q612_WarWithKetraOrcs();
	}
}