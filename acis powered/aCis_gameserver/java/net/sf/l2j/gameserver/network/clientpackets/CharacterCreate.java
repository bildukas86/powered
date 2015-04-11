package net.sf.l2j.gameserver.network.clientpackets;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.datatables.CharNameTable;
import net.sf.l2j.gameserver.datatables.CharTemplateTable;
import net.sf.l2j.gameserver.datatables.ItemTable;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.datatables.SkillTreeTable;
import net.sf.l2j.gameserver.idfactory.IdFactory;
import net.sf.l2j.gameserver.instancemanager.QuestManager;
import net.sf.l2j.gameserver.model.L2ShortCut;
import net.sf.l2j.gameserver.model.L2SkillLearn;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.actor.template.PcTemplate;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.model.item.kind.Item;
import net.sf.l2j.gameserver.model.itemcontainer.PcInventory;
import net.sf.l2j.gameserver.model.quest.Quest;
import net.sf.l2j.gameserver.network.serverpackets.CharCreateFail;
import net.sf.l2j.gameserver.network.serverpackets.CharCreateOk;
import net.sf.l2j.gameserver.network.serverpackets.CharSelectInfo;
import net.sf.l2j.gameserver.util.Util;

@SuppressWarnings("unused")
public final class CharacterCreate extends L2GameClientPacket
{
	
	private String _name;
	private int _race;
	private byte _sex;
	private int _classId;
	private int _int;
	private int _str;
	private int _con;
	private int _men;
	private int _dex;
	private int _wit;
	private byte _hairStyle;
	private byte _hairColor;
	private byte _face;
	
	@Override
	protected void readImpl()
	{
		_name = readS();
		_race = readD();
		_sex = (byte) readD();
		_classId = readD();
		_int = readD();
		_str = readD();
		_con = readD();
		_men = readD();
		_dex = readD();
		_wit = readD();
		_hairStyle = (byte) readD();
		_hairColor = (byte) readD();
		_face = (byte) readD();
	}
	
	@SuppressWarnings("null")
	@Override
	protected void runImpl()
	{
		if (_name.length() > 16)
		{
			sendPacket(new CharCreateFail(CharCreateFail.REASON_16_ENG_CHARS));
			return;
		}
		
		if (!Util.isValidPlayerName(_name))
		{
			sendPacket(new CharCreateFail(CharCreateFail.REASON_INCORRECT_NAME));
			return;
		}
		
		if (_face > 2 || _face < 0)
		{
			sendPacket(new CharCreateFail(CharCreateFail.REASON_CREATION_FAILED));
			return;
		}
		
		if (_hairStyle < 0 || (_sex == 0 && _hairStyle > 4) || (_sex != 0 && _hairStyle > 6))
		{
			sendPacket(new CharCreateFail(CharCreateFail.REASON_CREATION_FAILED));
			return;
		}
		
		if (_hairColor > 3 || _hairColor < 0)
		{
			sendPacket(new CharCreateFail(CharCreateFail.REASON_CREATION_FAILED));
			return;
		}
		
		L2PcInstance newChar = null;
		PcTemplate template = null;
		
		/*
		 * Since checks for duplicate names are done using SQL, lock must be held until data is written to DB as well.
		 */
		synchronized (CharNameTable.getInstance())
		{
			if (CharNameTable.accountCharNumber(getClient().getAccountName()) >= 7)
			{
				sendPacket(new CharCreateFail(CharCreateFail.REASON_TOO_MANY_CHARACTERS));
				return;
			}
			if (Config.FORBIDDEN_NAMES.length > 1)
				for (String st : Config.FORBIDDEN_NAMES)
					if (_name.toLowerCase().contains(st.toLowerCase()))
					{
						sendPacket(new CharCreateFail(CharCreateFail.REASON_CREATION_FAILED));
						return;
					}
			if (CharNameTable.doesCharNameExist(_name))
			{
				sendPacket(new CharCreateFail(CharCreateFail.REASON_NAME_ALREADY_EXISTS));
				return;
			}
			
			template = CharTemplateTable.getInstance().getTemplate(_classId);
			if (template == null || template.getClassBaseLevel() > 1)
			{
				sendPacket(new CharCreateFail(CharCreateFail.REASON_CREATION_FAILED));
				return;
			}
			
			int objectId = IdFactory.getInstance().getNextId();
			newChar = L2PcInstance.create(objectId, template, getClient().getAccountName(), _name, _hairStyle, _hairColor, _face, _sex != 0);
		}
		
		newChar.setCurrentCp(0);
		newChar.setCurrentHp(newChar.getMaxHp());
		newChar.setCurrentMp(newChar.getMaxMp());
		
		// send acknowledgement
		sendPacket(CharCreateOk.STATIC_PACKET);
		
		L2World.getInstance().storeObject(newChar);
		
		newChar.addAdena("Init", Config.STARTING_ADENA, null, false);
		if (Config.CUSTOM_SPAWN_CLASS)
		{
			if (newChar.isMageClass())
				newChar.setXYZInvisible(Config.SPANW_MAGE_X, Config.SPANW_MAGE_Y, Config.SPANW_MAGE_Z);
			else
				newChar.setXYZInvisible(Config.SPANW_FIGHTER_X, Config.SPANW_FIGHTER_Y, Config.SPANW_FIGHTER_Z);
		}
		else
		{
			newChar.setXYZInvisible(template.getSpawnX(), template.getSpawnY(), template.getSpawnZ());
		}
		
		for (int[] startingItems : Config.CUSTOM_STARTER_ITEMS)
		{
			if (newChar == null)
			{
				continue;
			}
			PcInventory inv = newChar.getInventory();
			if (ItemTable.getInstance().createDummyItem(startingItems[0]).isStackable())
			{
				inv.addItem("Starter Items", startingItems[0], startingItems[1], newChar, null);
			}
			else
			{
				for (int i = 0; i < startingItems[1]; i++)
				{
					inv.addItem("Starter Items", startingItems[0], 1, newChar, null);
				}
			}
		}
		
		if (Config.ALLOW_START_TITLE)
			newChar.setTitle(Config.START_TITLE);
		
		newChar.registerShortCut(new L2ShortCut(0, 0, 3, 2, -1, 1)); // attack shortcut
		newChar.registerShortCut(new L2ShortCut(3, 0, 3, 5, -1, 1)); // take shortcut
		newChar.registerShortCut(new L2ShortCut(10, 0, 3, 0, -1, 1)); // sit shortcut
		
		for (Item ia : template.getItems())
		{
			ItemInstance item = newChar.getInventory().addItem("Init", ia.getItemId(), 1, newChar, null);
			if (item.getItemId() == 5588) // tutorial book shortcut
				newChar.registerShortCut(new L2ShortCut(11, 0, 1, item.getObjectId(), -1, 1));
			
			if (item.isEquipable())
			{
				if (newChar.getActiveWeaponItem() == null || !(item.getItem().getType2() != Item.TYPE2_WEAPON))
					newChar.getInventory().equipItemAndRecord(item);
			}
		}
		
		if (Config.ALLOW_SANTA_HAT_ON_NEW_CHARACTERS)
		{
			ItemInstance item = newChar.getInventory().addItem("Init", 7836, 1, newChar, null);
			if (item.isEquipable())
			{
				if (newChar.getActiveWeaponItem() == null || !(item.getItem().getType2() != Item.TYPE2_ACCESSORY))
					newChar.getInventory().equipItemAndRecord(item);
			}
		}
		
		for (L2SkillLearn skill : SkillTreeTable.getInstance().getAvailableSkills(newChar, newChar.getClassId()))
		{
			newChar.addSkill(SkillTable.getInstance().getInfo(skill.getId(), skill.getLevel()), true);
			if (skill.getId() == 1001 || skill.getId() == 1177)
				newChar.registerShortCut(new L2ShortCut(1, 0, 2, skill.getId(), 1, 1));
			
			if (skill.getId() == 1216)
				newChar.registerShortCut(new L2ShortCut(9, 0, 2, skill.getId(), 1, 1));
		}
		
		if (!Config.DISABLE_TUTORIAL)
		{
			if (newChar.getQuestState("Tutorial") == null)
			{
				Quest q = QuestManager.getInstance().getQuest("Tutorial");
				if (q != null)
					q.newQuestState(newChar).setState(Quest.STATE_STARTED);
			}
		}
		
		newChar.setOnlineStatus(true, false);
		newChar.deleteMe();
		
		final CharSelectInfo cl = new CharSelectInfo(getClient().getAccountName(), getClient().getSessionId().playOkID1);
		getClient().getConnection().sendPacket(cl);
		getClient().setCharSelection(cl.getCharInfo());
	}
}