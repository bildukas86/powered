package Extensions.AdminCommands;

import java.util.StringTokenizer;

import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.SkillCoolTime;

public class AdminReuse implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_reuse",
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		
		StringTokenizer st = new StringTokenizer(command);
		st.nextToken();
		if (command.startsWith("admin_reuse"))
		{
			if (activeChar.getTarget() != null)
			{
				for (L2Skill skill : ((L2Character) activeChar.getTarget()).getAllSkills())
				{
					((L2Character) activeChar.getTarget()).enableSkill(skill);
				}
				((L2PcInstance) activeChar.getTarget()).sendSkillList();
				((L2PcInstance) activeChar.getTarget()).sendPacket(new SkillCoolTime((L2PcInstance) activeChar.getTarget()));
			}
			else
			{
				for (L2Skill skill : activeChar.getAllSkills())
				{
					activeChar.enableSkill(skill);
				}
				activeChar.sendSkillList();
				activeChar.sendPacket(new SkillCoolTime(activeChar));
				
			}
		}
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}