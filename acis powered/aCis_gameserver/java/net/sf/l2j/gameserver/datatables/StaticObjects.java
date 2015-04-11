package net.sf.l2j.gameserver.datatables;

import java.io.File;
import java.util.logging.Logger;

import net.sf.l2j.gameserver.idfactory.IdFactory;
import net.sf.l2j.gameserver.model.actor.instance.L2StaticObjectInstance;
import net.sf.l2j.gameserver.xmlfactory.XMLDocumentFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class StaticObjects
{
	private static Logger _log = Logger.getLogger(StaticObjects.class.getName());
	
	public static void load()
	{
		int count = 0;
		try
		{
			File f = new File("./data/xml/static_objects.xml");
			Document doc = XMLDocumentFactory.getInstance().loadDocument(f);
			
			Node n = doc.getFirstChild();
			for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
			{
				if (d.getNodeName().equalsIgnoreCase("staticobject"))
				{
					NamedNodeMap node = d.getAttributes();
					
					L2StaticObjectInstance obj = new L2StaticObjectInstance(IdFactory.getInstance().getNextId());
					obj.setType(Integer.valueOf(node.getNamedItem("type").getNodeValue()));
					obj.setStaticObjectId(Integer.valueOf(node.getNamedItem("id").getNodeValue()));
					obj.setXYZ(Integer.valueOf(node.getNamedItem("x").getNodeValue()), Integer.valueOf(node.getNamedItem("y").getNodeValue()), Integer.valueOf(node.getNamedItem("z").getNodeValue()));
					obj.setMap(node.getNamedItem("texture").getNodeValue(), Integer.valueOf(node.getNamedItem("map_x").getNodeValue()), Integer.valueOf(node.getNamedItem("map_y").getNodeValue()));
					obj.spawnMe();
					
					count++;
				}
			}
		}
		catch (Exception e)
		{
			_log.warning("StaticObject: Error while creating StaticObjects table: " + e);
		}
		_log.info("StaticObject: Loaded " + count + " templates.");
	}
}