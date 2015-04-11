package net.sf.l2j.gameserver.skills.funcs;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.itemcontainer.Inventory;
import net.sf.l2j.gameserver.skills.Env;
import net.sf.l2j.gameserver.skills.Stats;
import net.sf.l2j.gameserver.skills.basefuncs.Func;

public class FuncPDefMod extends Func
{
	static final FuncPDefMod _fpa_instance = new FuncPDefMod();
	
	public static Func getInstance()
	{
		return _fpa_instance;
	}
	
	private FuncPDefMod()
	{
		super(Stats.POWER_DEFENCE, 0x20, null, null);
	}
	
	@Override
	public void calc(Env env)
	{
		if (env.getCharacter() instanceof L2PcInstance)
		{
			final L2PcInstance player = env.getPlayer();
			final boolean hasMagePDef = (player.getClassId().isMage() || player.getClassId().getId() == 0x31); // orc mystics are a special case
			
			if (player.getInventory().getPaperdollItem(Inventory.PAPERDOLL_HEAD) != null)
				env.subValue(12);
			if (player.getInventory().getPaperdollItem(Inventory.PAPERDOLL_CHEST) != null)
				env.subValue((hasMagePDef) ? 15 : 31);
			if (player.getInventory().getPaperdollItem(Inventory.PAPERDOLL_LEGS) != null)
				env.subValue((hasMagePDef) ? 8 : 18);
			if (player.getInventory().getPaperdollItem(Inventory.PAPERDOLL_GLOVES) != null)
				env.subValue(8);
			if (player.getInventory().getPaperdollItem(Inventory.PAPERDOLL_FEET) != null)
				env.subValue(7);
		}
		
		env.mulValue(env.getCharacter().getLevelMod());
	}
}