package net.sf.l2j.gameserver.datatables;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.l2j.gameserver.model.item.ArmorSet;
import net.sf.l2j.gameserver.templates.StatsSet;
import net.sf.l2j.gameserver.xmlfactory.XMLParser;

/**
 * @author godson, Luno, nBd
 */
public class ArmorSetsTable
{
	private static Logger _log = Logger.getLogger(ArmorSetsTable.class.getName());
	
	private final Map<Integer, ArmorSet> _armorSets = new HashMap<>();
	
	public static ArmorSetsTable getInstance()
	{
		return SingletonHolder._instance;
	}
	
	protected ArmorSetsTable()
	{
		try
		{
			File f = new File("./data/xml/armorsets.xml");
			if (!f.exists())
			{
				_log.warning("ArmorSetsTable: armorsets.xml is missing in data folder.");
				return;
			}
			
			XMLParser parser = new XMLParser(f, "armorset");
			List<StatsSet> sets = parser.parseDocument();
			
			if (!sets.isEmpty())
			{
				for (StatsSet set : sets)
				{
					int chest = set.getInteger("chest", 0);
					int legs = set.getInteger("legs", 0);
					int head = set.getInteger("head", 0);
					int gloves = set.getInteger("gloves", 0);
					int feet = set.getInteger("feet", 0);
					int skill_id = set.getInteger("skill_id", 0);
					int shield = set.getInteger("shield", 0);
					int shield_skill_id = set.getInteger("shield_skill_id", 0);
					int enchant6skill = set.getInteger("enchant6skill", 0);
					_armorSets.put(chest, new ArmorSet(chest, legs, head, gloves, feet, skill_id, shield, shield_skill_id, enchant6skill));
				}
			}
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "ArmorSetsTable: Error loading armorsets.xml", e);
		}
		_log.info("ArmorSetsTable: Loaded " + _armorSets.size() + " armor sets.");
	}
	
	public boolean setExists(int chestId)
	{
		return _armorSets.containsKey(chestId);
	}
	
	public ArmorSet getSet(int chestId)
	{
		return _armorSets.get(chestId);
	}
	
	private static class SingletonHolder
	{
		protected static final ArmorSetsTable _instance = new ArmorSetsTable();
	}
}