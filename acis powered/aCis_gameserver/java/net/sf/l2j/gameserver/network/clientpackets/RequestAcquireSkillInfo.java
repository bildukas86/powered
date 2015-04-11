package net.sf.l2j.gameserver.network.clientpackets;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.datatables.SkillTreeTable;
import net.sf.l2j.gameserver.model.L2PledgeSkillLearn;
import net.sf.l2j.gameserver.model.L2RebirthSkillLearn;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.L2SkillLearn;
import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.AcquireSkillInfo;

public class RequestAcquireSkillInfo extends L2GameClientPacket
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
		if (_skillId <= 0 || _skillLevel <= 0)
			return;
		
		final L2PcInstance activeChar = getClient().getActiveChar();
		if (activeChar == null)
			return;
		
		final L2Npc trainer = activeChar.getCurrentFolkNPC();
		if (trainer == null)
			return;
		
		if (!activeChar.isInsideRadius(trainer, L2Npc.INTERACTION_DISTANCE, false, false) && !activeChar.isGM())
			return;
		
		final L2Skill skill = SkillTable.getInstance().getInfo(_skillId, _skillLevel);
		if (skill == null)
			return;
		
		switch (_skillType)
		{
			// Trance's Rebirth skills.
			case 0:
				int skillLvl = activeChar.getSkillLevel(_skillId);
				if (skillLvl >= _skillLevel)
					return;
				
				if (Math.max(skillLvl, 0) + 1 != _skillLevel)
					return;
				
				if (!trainer.getTemplate().canTeach(activeChar.getSkillLearningClassId()))
					return;
					
				for (L2RebirthSkillLearn rsl : SkillTreeTable.getInstance().getAvailableRebirthSkills(activeChar, activeChar.getSkillLearningClassId()))
				{
					if (rsl.getId() == _skillId && rsl.getLevel() == _skillLevel)
					{
						AcquireSkillInfo asi = new AcquireSkillInfo(_skillId, _skillLevel, rsl.getCostSp(), 0);
						if (Config.LIFE_CRYSTAL_NEEDED && rsl.getItemId() != 0)
							asi.addRequirement(1, rsl.getItemId(), 1, 0);
						sendPacket(asi);
						break;
					}
				}
				break;
			// Common skills 
			case 1:
				skillLvl = activeChar.getSkillLevel(_skillId);
				if (skillLvl >= _skillLevel)
					return;
				
				if (Math.max(skillLvl, 0) + 1 != _skillLevel)
					return;
				
				for (L2SkillLearn sl : SkillTreeTable.getInstance().getAvailableFishingDwarvenCraftSkills(activeChar))
				{
					if (sl.getId() == _skillId && sl.getLevel() == _skillLevel)
					{
						AcquireSkillInfo asi = new AcquireSkillInfo(_skillId, _skillLevel, sl.getSpCost(), 1);
						asi.addRequirement(4, sl.getIdCost(), sl.getCostCount(), 0);
						sendPacket(asi);
						break;
					}
				}
				break;
			// Pledge skills.
			case 2:
				if (!activeChar.isClanLeader())
					return;
				
				for (L2PledgeSkillLearn psl : SkillTreeTable.getInstance().getAvailablePledgeSkills(activeChar))
				{
					if (psl.getId() == _skillId && psl.getLevel() == _skillLevel)
					{
						AcquireSkillInfo asi = new AcquireSkillInfo(skill.getId(), skill.getLevel(), psl.getRepCost(), 2);
						if (Config.LIFE_CRYSTAL_NEEDED && psl.getItemId() != 0)
							asi.addRequirement(1, psl.getItemId(), 1, 0);
						sendPacket(asi);
						break;
					}
				}
				break;
		}
	}
}