package net.sf.l2j.gameserver.skills.funcs;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.skills.Env;
import net.sf.l2j.gameserver.skills.Stats;
import net.sf.l2j.gameserver.skills.basefuncs.Func;

public class FuncHennaMEN extends Func
{
	static final FuncHennaMEN _fh_instance = new FuncHennaMEN();
	
	public static Func getInstance()
	{
		return _fh_instance;
	}
	
	private FuncHennaMEN()
	{
		super(Stats.STAT_MEN, 0x10, null, null);
	}
	
	@Override
	public void calc(Env env)
	{
		final L2PcInstance player = env.getPlayer();
		if (player != null)
			env.addValue(player.getHennaStatMEN());
	}
}