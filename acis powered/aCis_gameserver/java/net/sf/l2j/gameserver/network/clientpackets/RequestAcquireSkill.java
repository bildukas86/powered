package net.sf.l2j.gameserver.network.clientpackets;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.datatables.SkillTreeTable;
import net.sf.l2j.gameserver.model.L2PledgeSkillLearn;
import net.sf.l2j.gameserver.model.L2RebirthSkillLearn;
import net.sf.l2j.gameserver.model.L2ShortCut;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.L2SkillLearn;
import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2FishermanInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2NpcInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2VillageMasterInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ExStorageMaxCount;
import net.sf.l2j.gameserver.network.serverpackets.ShortCutRegister;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;

public class RequestAcquireSkill extends L2GameClientPacket
{
	private int _skillId;
	private int _skillLevel;
	private int _skillType;
	
	@Override
	protected void readImpl()
	{
		_skillId = readD();
		_skillLevel = readD();
		_skillType = readD();
	}
	
	@Override
	protected void runImpl()
	{
		// Not valid skill data, return.
		if (_skillId <= 0 || _skillLevel <= 0)
			return;
		
		// Incorrect player, return.
		final L2PcInstance activeChar = getClient().getActiveChar();
		if (activeChar == null)
			return;
		
		// Incorrect npc, return.
		final L2Npc trainer = activeChar.getCurrentFolkNPC();
		if (trainer == null)
			return;
		
		// Distance check for player <-> npc.
		if (!activeChar.isInsideRadius(trainer, L2Npc.INTERACTION_DISTANCE, false, false) && !activeChar.isGM())
			return;
		
		// Skill doesn't exist, return.
		final L2Skill skill = SkillTable.getInstance().getInfo(_skillId, _skillLevel);
		if (skill == null)
			return;
		
		// Set learn class.
		activeChar.setSkillLearningClassId(activeChar.getClassId());
		
		boolean exists = false;
		
		// Types.
		switch (_skillType)
		{
			case 0: // Trance's Rebirth skills.
				// Player already has such skill with same or higher level.
				int skillLvl = activeChar.getSkillLevel(_skillId);
				if (skillLvl >= _skillLevel)
					return;
				
				// Requested skill must be 1 level higher than existing skill.
				if (Math.max(skillLvl, 0) + 1 != _skillLevel)
					return;
				
				int spCost = 0, idItem = 0;
				
				// Find skill information.
				for (L2RebirthSkillLearn rsl : SkillTreeTable.getInstance().getAvailableRebirthSkills(activeChar, activeChar.getSkillLearningClassId()))
				{
					// Skill found.
					if (rsl.getId() == _skillId && rsl.getLevel() == _skillLevel)
					{
						exists = true;
						idItem = rsl.getItemId();
						spCost = rsl.getCostSp();
						break;
					}
				}
				
				// No skill found, return.
				if (!exists)
					return;
				
				// Not enought SP.
				if (activeChar.getSp() < spCost)
				{
					activeChar.sendPacket(SystemMessageId.NOT_ENOUGH_SP_TO_LEARN_SKILL);
					L2NpcInstance.showSkillList(activeChar, trainer, activeChar.getSkillLearningClassId());
					return;
				}
				
				// Get item and try to consume it.
				if (idItem > 0)
				{
					if (!activeChar.destroyItemByItemId("SkillLearn", idItem, 1, trainer, false))
					{
						activeChar.sendPacket(SystemMessageId.ITEM_MISSING_TO_LEARN_SKILL);
						L2NpcInstance.showSkillList(activeChar, trainer, activeChar.getSkillLearningClassId());
						return;
					}
				}
				
				// Consume SP.
				activeChar.removeExpAndSp(0, spCost);
				
				// Add skill new skill.
				activeChar.addSkill(skill, true);
				activeChar.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.LEARNED_SKILL_S1).addSkillName(skill));
				
				// Update player and return.
				updateShortCuts(activeChar);
				activeChar.sendSkillList();
				L2NpcInstance.showSkillList(activeChar, trainer, activeChar.getSkillLearningClassId());
				break;
			
			case 1: // Common skills.
				skillLvl = activeChar.getSkillLevel(_skillId);
				if (skillLvl >= _skillLevel)
					return;
				
				if (Math.max(skillLvl, 0) + 1 != _skillLevel)
					return;
				
				int costId = 0;
				int costCount = 0;
				
				for (L2SkillLearn sl : SkillTreeTable.getInstance().getAvailableFishingDwarvenCraftSkills(activeChar))
				{
					if (sl.getId() == _skillId && sl.getLevel() == _skillLevel)
					{
						exists = true;
						costId = sl.getIdCost();
						costCount = sl.getCostCount();
						break;
					}
				}
				
				if (!exists)
					return;
				
				if (!activeChar.destroyItemByItemId("Consume", costId, costCount, trainer, true))
				{
					activeChar.sendPacket(SystemMessageId.ITEM_MISSING_TO_LEARN_SKILL);
					L2FishermanInstance.showFishSkillList(activeChar);
					return;
				}
				
				activeChar.addSkill(skill, true);
				activeChar.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.LEARNED_SKILL_S1).addSkillName(skill));
				
				if (_skillId >= 1368 && _skillId <= 1372)
					activeChar.sendPacket(new ExStorageMaxCount(activeChar));
				
				updateShortCuts(activeChar);
				activeChar.sendSkillList();
				L2FishermanInstance.showFishSkillList(activeChar);
				break;
			
			case 2: // Pledge skills.
				if (!activeChar.isClanLeader())
					return;
				
				int itemId = 0;
				int repCost = 0;
				
				for (L2PledgeSkillLearn psl : SkillTreeTable.getInstance().getAvailablePledgeSkills(activeChar))
				{
					if (psl.getId() == _skillId && psl.getLevel() == _skillLevel)
					{
						exists = true;
						itemId = psl.getItemId();
						repCost = psl.getRepCost();
						break;
					}
				}
				
				if (!exists)
					return;
				
				if (activeChar.getClan().getReputationScore() < repCost)
				{
					activeChar.sendPacket(SystemMessageId.ACQUIRE_SKILL_FAILED_BAD_CLAN_REP_SCORE);
					L2VillageMasterInstance.showPledgeSkillList(activeChar);
					return;
				}
				
				if (Config.LIFE_CRYSTAL_NEEDED)
				{
					if (!activeChar.destroyItemByItemId("Consume", itemId, 1, trainer, true))
					{
						activeChar.sendPacket(SystemMessageId.ITEM_MISSING_TO_LEARN_SKILL);
						L2VillageMasterInstance.showPledgeSkillList(activeChar);
						return;
					}
				}
				
				activeChar.getClan().takeReputationScore(repCost);
				activeChar.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.S1_DEDUCTED_FROM_CLAN_REP).addNumber(repCost));
				
				activeChar.getClan().addNewSkill(skill);
				
				L2VillageMasterInstance.showPledgeSkillList(activeChar);
				return;
		}
	}
	
	private void updateShortCuts(L2PcInstance player)
	{
		if (_skillLevel > 1)
		{
			for (L2ShortCut sc : player.getAllShortCuts())
			{
				if (sc.getId() == _skillId && sc.getType() == L2ShortCut.TYPE_SKILL)
				{
					L2ShortCut newsc = new L2ShortCut(sc.getSlot(), sc.getPage(), L2ShortCut.TYPE_SKILL, _skillId, _skillLevel, 1);
					player.sendPacket(new ShortCutRegister(newsc));
					player.registerShortCut(newsc);
				}
			}
		}
	}
}