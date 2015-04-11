package net.sf.l2j.gameserver.model.actor.instance;

import net.sf.l2j.gameserver.ai.CtrlEvent;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.util.Rnd;

public class L2PenaltyMonsterInstance extends L2MonsterInstance
{
	private L2PcInstance _ptk;
	
	public L2PenaltyMonsterInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public L2Character getMostHated()
	{
		if (_ptk != null)
			return _ptk; // always attack only one person
			
		return super.getMostHated();
	}
	
	public void setPlayerToKill(L2PcInstance ptk)
	{
		if (Rnd.get(100) <= 80)
			broadcastNpcSay("Your bait was too delicious! Now, I will kill you!");
		
		_ptk = ptk;
		
		getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, _ptk, Rnd.get(1, 100));
	}
	
	@Override
	public boolean doDie(L2Character killer)
	{
		if (!super.doDie(killer))
			return false;
		
		if (Rnd.get(100) <= 75)
			broadcastNpcSay("I will tell fish not to take your bait!");
		
		return true;
	}
}