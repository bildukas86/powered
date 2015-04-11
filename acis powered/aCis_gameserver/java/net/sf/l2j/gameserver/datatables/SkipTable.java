package net.sf.l2j.gameserver.datatables;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.sf.l2j.gameserver.xmlfactory.XMLDocumentFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class SkipTable
{
	private static final Logger _log = Logger.getLogger(SkipTable.class.getName());
	
	private static final List<Integer> _skip = new ArrayList<>();
	
	public static SkipTable getInstance()
	{
		return SingletonHolder._instance;
	}
	
	public SkipTable()
	{
		load();
	}
	
	private static void load()
	{
		try
		{
			File f = new File("./data/xml/skipping_items.xml");
			Document doc = XMLDocumentFactory.getInstance().loadDocument(f);
			
			Node n = doc.getFirstChild();
			for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
			{
				if (d.getNodeName().equalsIgnoreCase("item"))
				{
					int itemId = Integer.parseInt(d.getAttributes().getNamedItem("id").getNodeValue());
					_skip.add(itemId);
				}
			}
		}
		catch (Exception e)
		{
			_log.warning("SkipTable: Error parsing SkipItems.xml " + e);
		}
		
		_log.info("SkipTable: Loaded " + _skip.size() + " skipping item(s).");
	}
	
	public void reload()
	{
		_skip.clear();
		load();
	}
	
	public static boolean isSkipped(int itemId)
	{
		return _skip.contains(itemId);
	}
	
	private static class SingletonHolder
	{
		protected static final SkipTable _instance = new SkipTable();
	}
}