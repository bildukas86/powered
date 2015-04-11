/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.l2j.gameserver.buffer;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * @author Erlandys
 *
 */
public class BufferParser
{

	private static final Logger _log = Logger.getLogger(BufferParser.class.getName());
	public ArrayList<Buff> buffs;
	public ArrayList<String> buffTypes;

	private BufferParser()
	{
		buffs = new ArrayList<>();
		buffTypes = new ArrayList<>();
		load();
		L2Buffer.getInstance();
	}

	public ArrayList<Buff> getBuffs()
	{
		return buffs;
	}

	public ArrayList<String> getBuffTypes()
	{
		return buffTypes;
	}

	public Buff getBuffByIndex(int index)
	{
		return buffs.get(index);
	}

	public Buff getBuffByBuffId(int buffId)
	{
		for (Buff buff : buffs)
			if (buff.getId() == buffId)
				return buff;
		return null;
	}

	public void load()
	{
		try
		{
			_log.info(getClass().getSimpleName()+": Initializing");
			buffs.clear();
			buffTypes.clear();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setIgnoringComments(true);
			File file = new File("data/Buffer.xml");
			Document doc = null;

			if (file.exists())
			{
				try
				{
					doc = factory.newDocumentBuilder().parse(file);
				}
				catch (Exception e)
				{
					_log.log(Level.WARNING, "Could not parse Buffer.xml file: " + e.getMessage(), e);
					return;
				}

				Node n = doc.getFirstChild();
				int buffCount = 0;
				for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
				{
					if (d.getNodeName().equalsIgnoreCase("category"))
					{
						String type = d.getAttributes().getNamedItem("type").getNodeValue();
						for (Node m = d.getFirstChild(); m != null; m = m.getNextSibling())
						{
							if (m.getNodeName().equalsIgnoreCase("buff"))
							{
								int buffId = Integer.parseInt(m.getAttributes().getNamedItem("buffId").getNodeValue());
								int level = 99;
								if (m.getAttributes().getNamedItem("level") != null)
									level = Integer.parseInt(m.getAttributes().getNamedItem("level").getNodeValue());
								String name = m.getAttributes().getNamedItem("name").getNodeValue();
								String information = m.getAttributes().getNamedItem("information").getNodeValue();
								String buffSet = "None";
								if (m.getAttributes().getNamedItem("buffSet") != null)
									buffSet = m.getAttributes().getNamedItem("buffSet").getNodeValue();
								buffs.add(new Buff(buffCount, buffId, level, name, type, information, buffSet));
								buffCount++;
							}
						}
						buffTypes.add(type);
					}
				}
			}
			_log.info(getClass().getSimpleName()+": Successfully loaded " + buffs.size() + " buffs.");
		}
		catch(Exception e){
			_log.log(Level.WARNING, "[Buffer] something is wrong when loading buffs: " + e.getMessage(), e);}
	}

	public static class Buff
	{
		public int id, level, index;
		public String name, type, information, buffSet;

		public Buff(int _index, int _id, int _level, String _name, String _type, String _information, String _buffSet)
		{
			index = _index;
			id = _id;
			level = _level;
			name = _name;
			type = _type;
			information = _information;
			buffSet = _buffSet;
		}

		public int getIndex()
		{
			return index;
		}

		public int getId()
		{
			return id;
		}

		public int getLevel()
		{
			return level;
		}

		public String getName()
		{
			return name;
		}

		public String getType()
		{
			return type;
		}

		public String getInformation()
		{
			return information;
		}

		public String getBuffSet()
		{
			return buffSet;
		}
	}
	public static final BufferParser getInstance()
	{
		return SingletonHolder._instance;
	}
	
	@SuppressWarnings("synthetic-access")
	private static class SingletonHolder
	{
		protected static final BufferParser _instance = new BufferParser();
	}
}
