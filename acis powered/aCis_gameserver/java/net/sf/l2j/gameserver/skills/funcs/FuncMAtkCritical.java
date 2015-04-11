package net.sf.l2j.gameserver.skills.funcs;

import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.skills.Env;
import net.sf.l2j.gameserver.skills.Formulas;
import net.sf.l2j.gameserver.skills.Stats;
import net.sf.l2j.gameserver.skills.basefuncs.Func;

public class FuncMAtkCritical extends Func
{
	static final FuncMAtkCritical _fac_instance = new FuncMAtkCritical();
	
	public static Func getInstance()
	{
		return _fac_instance;
	}
	
	private FuncMAtkCritical()
	{
		super(Stats.MCRITICAL_RATE, 0x30, null, null);
	}
	
	@Override
	public void calc(Env env)
	{
		final L2Character player = env.getCharacter();
		if (player instanceof L2PcInstance)
		{
			if (player.getActiveWeaponInstance() != null)
				env.mulValue(Formulas.WITbonus[player.getWIT()]);
		}
		else
			env.mulValue(Formulas.WITbonus[player.getWIT()]);
	}
}