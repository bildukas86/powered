package events.SuperMonster;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.datatables.ItemTable;
import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.itemcontainer.PcInventory;
import net.sf.l2j.gameserver.model.quest.Quest;
import net.sf.l2j.gameserver.network.serverpackets.StatusUpdate;

public class SuperMonster extends Quest
{
	public SuperMonster()
	{
		super(-1, "SuperMonster", "custom");
		
		if (Config.ENABLE_SUPER_MONSTER)
		{
			for (int mobs : Config.SUPER_MONSTERS_IDS)
				addKillId(mobs);
		}
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		if (Config.SM_REWARD_PARTY && player.getParty() != null)
		{
			for (L2PcInstance members : player.getParty().getPartyMembers())
			{
				members.sendMessage("Congratulations! You killed The SuperMonster!");
				
				if (Config.SM_GIVE_ITEM)
					rewardWinner(members);
				
				if (Config.SM_REWARD_PARTY_HERO && !player.isHero())
				{
					members.setHero(true);
					members.sendMessage("You are now hero untill relogin!");
				}
				
				if (Config.SM_REWARD_PARTY_NOBLE && !members.isNoble())
				{
					members.setNoble(true, true);
					members.sendMessage("You have become noblesse!");
				}
				
				members.broadcastUserInfo();
			}
		}
		else
		{
			player.sendMessage("Congratulations! You killed The SuperMonster!");
			
			if (Config.SM_GIVE_ITEM)
				rewardWinner(player);
			
			if (Config.SM_GIVE_HERO && !player.isHero())
			{
				player.setHero(true);
			}
			
			if (Config.SM_GIVE_NOBLE && !player.isNoble())
			{
				player.setNoble(true, true);
			}
			
			player.broadcastUserInfo();
		}
		
		return null;
	}
	
	static void rewardWinner(L2PcInstance player)
	{
		// Check for nullpointer
		if (player == null)
			return;
		
		// Iterate over all rewards
		for (int[] reward : Config.SM_ITEM_REWARD)
		{
			PcInventory inv = player.getInventory();
			
			// Check for stackable item, non stackabe items need to be added one by one
			if (ItemTable.getInstance().createDummyItem(reward[0]).isStackable())
			{
				inv.addItem("SuperMonster", reward[0], reward[1], player, player);
			}
			else
			{
				for (int i = 0; i < reward[1]; ++i)
				{
					inv.addItem("SuperMonster", reward[0], 1, player, player);
				}
			}
		}
		
		StatusUpdate statusUpdate = new StatusUpdate(player);
		statusUpdate.addAttribute(StatusUpdate.CUR_LOAD, player.getCurrentLoad());
		player.sendPacket(statusUpdate);
	}
	
	public static void main(String args[])
	{
		new SuperMonster();
	}
}