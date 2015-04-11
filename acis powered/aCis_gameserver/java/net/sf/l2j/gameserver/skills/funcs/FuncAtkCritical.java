package net.sf.l2j.gameserver.skills.funcs;

import net.sf.l2j.gameserver.model.actor.L2Summon;
import net.sf.l2j.gameserver.skills.Env;
import net.sf.l2j.gameserver.skills.Formulas;
import net.sf.l2j.gameserver.skills.Stats;
import net.sf.l2j.gameserver.skills.basefuncs.Func;

public class FuncAtkCritical extends Func
{
	static final FuncAtkCritical _fac_instance = new FuncAtkCritical();
	
	public static Func getInstance()
	{
		return _fac_instance;
	}
	
	private FuncAtkCritical()
	{
		super(Stats.CRITICAL_RATE, 0x09, null, null);
	}
	
	@Override
	public void calc(Env env)
	{
		if (!(env.getCharacter() instanceof L2Summon))
			env.mulValue(Formulas.DEXbonus[env.getCharacter().getDEX()]);
		
		env.mulValue(10);
		
		env.setBaseValue(env.getValue());
	}
}
