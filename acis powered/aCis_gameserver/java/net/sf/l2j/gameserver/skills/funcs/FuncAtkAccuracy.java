package net.sf.l2j.gameserver.skills.funcs;

import net.sf.l2j.gameserver.model.actor.L2Summon;
import net.sf.l2j.gameserver.skills.Env;
import net.sf.l2j.gameserver.skills.Stats;
import net.sf.l2j.gameserver.skills.basefuncs.Func;

public class FuncAtkAccuracy extends Func
{
	static final FuncAtkAccuracy _faa_instance = new FuncAtkAccuracy();
	
	public static Func getInstance()
	{
		return _faa_instance;
	}
	
	private FuncAtkAccuracy()
	{
		super(Stats.ACCURACY_COMBAT, 0x10, null, null);
	}
	
	@Override
	public void calc(Env env)
	{
		final int level = env.getCharacter().getLevel();
		
		env.addValue((Math.sqrt(env.getCharacter().getDEX()) * 6) + level);
		
		if (env.getCharacter() instanceof L2Summon)
			env.addValue((level < 60) ? 4 : 5);
	}
}