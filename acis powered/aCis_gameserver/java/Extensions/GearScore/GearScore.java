package Extensions.GearScore;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.model.itemcontainer.Inventory;

public class GearScore
{
	public static int getGearScore(L2PcInstance activeChar)
	{
		int score = 0;
		score = getGearScoreRHand(activeChar) + getGearScoreHead(activeChar) + getGearScoreChest(activeChar) + getGearScoreGloves(activeChar) + getGearScoreBoots(activeChar) + getGearScoreREar(activeChar) + getGearScoreLEar(activeChar) + getGearScoreRRing(activeChar) + getGearScoreLRing(activeChar) + getGearScoreTattoo(activeChar) + getGearScoreNeck(activeChar);
		
		return score;
	}
	
	public static int getGearScoreRHand(L2PcInstance activeChar)
	{
		int score = 0;
		int enchscore = 0, augscore = 0, gradescore = 0;
		
		ItemInstance item = activeChar.getInventory().getPaperdollItem(Inventory.PAPERDOLL_RHAND);
		
		if (item == null)
			return 0;
		
		if (item.getAugmentation() != null)
			augscore = 500;
		else
			augscore = 0;
		
		switch (item.getItem().getCrystalType())
		{
			case NONE:
				gradescore = 0;
				break;
			case D:
				gradescore = 200;
				break;
			case C:
				gradescore = 400;
				break;
			case B:
				gradescore = 600;
				break;
			case A:
				gradescore = 800;
				break;
			case S:
				gradescore = 1000;
				break;
		}
		
		switch (item.getEnchantLevel())
		{
			case 0:
				enchscore = 0;
				break;
			case 1:
				enchscore = 100;
				break;
			case 2:
				enchscore = 100;
				break;
			case 3:
				enchscore = 100;
				break;
			case 4:
				enchscore = 150;
				break;
			case 5:
				enchscore = 200;
				break;
			case 6:
				enchscore = 350;
				break;
			case 7:
				enchscore = 400;
				break;
			case 8:
				enchscore = 450;
				break;
			case 9:
				enchscore = 500;
				break;
			case 10:
				enchscore = 550;
				break;
			case 11:
				enchscore = 600;
				break;
			case 12:
				enchscore = 650;
				break;
			case 13:
				enchscore = 700;
				break;
			case 14:
				enchscore = 750;
				break;
			case 15:
				enchscore = 800;
				break;
			case 16:
				enchscore = 850;
				break;
			case 17:
				enchscore = 900;
				break;
			case 18:
				enchscore = 950;
				break;
			case 19:
				enchscore = 1000;
				break;
			case 20:
				enchscore = 1050;
				break;
			case 21:
				enchscore = 1100;
				break;
			case 22:
				enchscore = 1150;
				break;
			case 23:
				enchscore = 1200;
				break;
			case 24:
				enchscore = 1250;
				break;
			case 25:
				enchscore = 1300;
				break;
		}
		
		score = augscore + enchscore + gradescore;
		return score;
	}
	
	public static int getGearScoreHead(L2PcInstance activeChar)
	{
		int score = 0;
		int enchscore = 0, augscore = 0, gradescore = 0;
		
		ItemInstance item = activeChar.getInventory().getPaperdollItem(Inventory.PAPERDOLL_HEAD);
		
		if (item == null)
			return 0;
		
		if (item.getAugmentation() != null)
			augscore = 500;
		else
			augscore = 0;
		
		switch (item.getItem().getCrystalType())
		{
			case NONE:
				gradescore = 0;
				break;
			case D:
				gradescore = 200;
				break;
			case C:
				gradescore = 400;
				break;
			case B:
				gradescore = 600;
				break;
			case A:
				gradescore = 800;
				break;
			case S:
				gradescore = 1000;
				break;
		}
		
		switch (item.getEnchantLevel())
		{
			case 0:
				enchscore = 0;
				break;
			case 1:
				enchscore = 100;
				break;
			case 2:
				enchscore = 100;
				break;
			case 3:
				enchscore = 100;
				break;
			case 4:
				enchscore = 150;
				break;
			case 5:
				enchscore = 200;
				break;
			case 6:
				enchscore = 350;
				break;
			case 7:
				enchscore = 400;
				break;
			case 8:
				enchscore = 450;
				break;
			case 9:
				enchscore = 500;
				break;
			case 10:
				enchscore = 550;
				break;
			case 11:
				enchscore = 600;
				break;
			case 12:
				enchscore = 650;
				break;
			case 13:
				enchscore = 700;
				break;
			case 14:
				enchscore = 750;
				break;
			case 15:
				enchscore = 800;
				break;
			case 16:
				enchscore = 850;
				break;
			case 17:
				enchscore = 900;
				break;
			case 18:
				enchscore = 950;
				break;
			case 19:
				enchscore = 1000;
				break;
			case 20:
				enchscore = 1050;
				break;
			case 21:
				enchscore = 1100;
				break;
			case 22:
				enchscore = 1150;
				break;
			case 23:
				enchscore = 1200;
				break;
			case 24:
				enchscore = 1250;
				break;
			case 25:
				enchscore = 1300;
				break;
		}
		
		score = augscore + enchscore + gradescore;
		return score;
	}
	
	public static int getGearScoreChest(L2PcInstance activeChar)
	{
		int score = 0;
		int enchscore = 0, augscore = 0, gradescore = 0;
		
		ItemInstance item = activeChar.getInventory().getPaperdollItem(Inventory.PAPERDOLL_CHEST);
		
		if (item == null)
			return 0;
		
		if (item.getAugmentation() != null)
			augscore = 500;
		else
			augscore = 0;
		
		switch (item.getItem().getCrystalType())
		{
			case NONE:
				gradescore = 0;
				break;
			case D:
				gradescore = 200;
				break;
			case C:
				gradescore = 400;
				break;
			case B:
				gradescore = 600;
				break;
			case A:
				gradescore = 800;
				break;
			case S:
				gradescore = 1000;
				break;
		}
		
		switch (item.getEnchantLevel())
		{
			case 0:
				enchscore = 0;
				break;
			case 1:
				enchscore = 100;
				break;
			case 2:
				enchscore = 100;
				break;
			case 3:
				enchscore = 100;
				break;
			case 4:
				enchscore = 150;
				break;
			case 5:
				enchscore = 200;
				break;
			case 6:
				enchscore = 350;
				break;
			case 7:
				enchscore = 400;
				break;
			case 8:
				enchscore = 450;
				break;
			case 9:
				enchscore = 500;
				break;
			case 10:
				enchscore = 550;
				break;
			case 11:
				enchscore = 600;
				break;
			case 12:
				enchscore = 650;
				break;
			case 13:
				enchscore = 700;
				break;
			case 14:
				enchscore = 750;
				break;
			case 15:
				enchscore = 800;
				break;
			case 16:
				enchscore = 850;
				break;
			case 17:
				enchscore = 900;
				break;
			case 18:
				enchscore = 950;
				break;
			case 19:
				enchscore = 1000;
				break;
			case 20:
				enchscore = 1050;
				break;
			case 21:
				enchscore = 1100;
				break;
			case 22:
				enchscore = 1150;
				break;
			case 23:
				enchscore = 1200;
				break;
			case 24:
				enchscore = 1250;
				break;
			case 25:
				enchscore = 1300;
				break;
		}
		
		score = augscore + enchscore + gradescore;
		return score;
	}
	
	public static int getGearScoreGloves(L2PcInstance activeChar)
	{
		int score = 0;
		int enchscore = 0, augscore = 0, gradescore = 0;
		
		ItemInstance item = activeChar.getInventory().getPaperdollItem(Inventory.PAPERDOLL_GLOVES);
		
		if (item == null)
			return 0;
		
		if (item.getAugmentation() != null)
			augscore = 500;
		else
			augscore = 0;
		
		switch (item.getItem().getCrystalType())
		{
			case NONE:
				gradescore = 0;
				break;
			case D:
				gradescore = 200;
				break;
			case C:
				gradescore = 400;
				break;
			case B:
				gradescore = 600;
				break;
			case A:
				gradescore = 800;
				break;
			case S:
				gradescore = 1000;
				break;
		}
		
		switch (item.getEnchantLevel())
		{
			case 0:
				enchscore = 0;
				break;
			case 1:
				enchscore = 100;
				break;
			case 2:
				enchscore = 100;
				break;
			case 3:
				enchscore = 100;
				break;
			case 4:
				enchscore = 150;
				break;
			case 5:
				enchscore = 200;
				break;
			case 6:
				enchscore = 350;
				break;
			case 7:
				enchscore = 400;
				break;
			case 8:
				enchscore = 450;
				break;
			case 9:
				enchscore = 500;
				break;
			case 10:
				enchscore = 550;
				break;
			case 11:
				enchscore = 600;
				break;
			case 12:
				enchscore = 650;
				break;
			case 13:
				enchscore = 700;
				break;
			case 14:
				enchscore = 750;
				break;
			case 15:
				enchscore = 800;
				break;
			case 16:
				enchscore = 850;
				break;
			case 17:
				enchscore = 900;
				break;
			case 18:
				enchscore = 950;
				break;
			case 19:
				enchscore = 1000;
				break;
			case 20:
				enchscore = 1050;
				break;
			case 21:
				enchscore = 1100;
				break;
			case 22:
				enchscore = 1150;
				break;
			case 23:
				enchscore = 1200;
				break;
			case 24:
				enchscore = 1250;
				break;
			case 25:
				enchscore = 1300;
				break;
		}
		
		score = augscore + enchscore + gradescore;
		return score;
	}
	
	public static int getGearScoreBoots(L2PcInstance activeChar)
	{
		int score = 0;
		int enchscore = 0, augscore = 0, gradescore = 0;
		
		ItemInstance item = activeChar.getInventory().getPaperdollItem(Inventory.PAPERDOLL_FEET);
		
		if (item == null)
			return 0;
		
		if (item.getAugmentation() != null)
			augscore = 500;
		else
			augscore = 0;
		
		switch (item.getItem().getCrystalType())
		{
			case NONE:
				gradescore = 0;
				break;
			case D:
				gradescore = 200;
				break;
			case C:
				gradescore = 400;
				break;
			case B:
				gradescore = 600;
				break;
			case A:
				gradescore = 800;
				break;
			case S:
				gradescore = 1000;
				break;
		}
		
		switch (item.getEnchantLevel())
		{
			case 0:
				enchscore = 0;
				break;
			case 1:
				enchscore = 100;
				break;
			case 2:
				enchscore = 100;
				break;
			case 3:
				enchscore = 100;
				break;
			case 4:
				enchscore = 150;
				break;
			case 5:
				enchscore = 200;
				break;
			case 6:
				enchscore = 350;
				break;
			case 7:
				enchscore = 400;
				break;
			case 8:
				enchscore = 450;
				break;
			case 9:
				enchscore = 500;
				break;
			case 10:
				enchscore = 550;
				break;
			case 11:
				enchscore = 600;
				break;
			case 12:
				enchscore = 650;
				break;
			case 13:
				enchscore = 700;
				break;
			case 14:
				enchscore = 750;
				break;
			case 15:
				enchscore = 800;
				break;
			case 16:
				enchscore = 850;
				break;
			case 17:
				enchscore = 900;
				break;
			case 18:
				enchscore = 950;
				break;
			case 19:
				enchscore = 1000;
				break;
			case 20:
				enchscore = 1050;
				break;
			case 21:
				enchscore = 1100;
				break;
			case 22:
				enchscore = 1150;
				break;
			case 23:
				enchscore = 1200;
				break;
			case 24:
				enchscore = 1250;
				break;
			case 25:
				enchscore = 1300;
				break;
		}
		
		score = augscore + enchscore + gradescore;
		return score;
	}
	
	public static int getGearScoreNeck(L2PcInstance activeChar)
	{
		int score = 0;
		int enchscore = 0, augscore = 0, gradescore = 0;
		
		ItemInstance item = activeChar.getInventory().getPaperdollItem(Inventory.PAPERDOLL_NECK);
		
		if (item == null)
			return 0;
		
		if (item.getAugmentation() != null)
			augscore = 500;
		else
			augscore = 0;
		
		switch (item.getItem().getCrystalType())
		{
			case NONE:
				gradescore = 0;
				break;
			case D:
				gradescore = 200;
				break;
			case C:
				gradescore = 400;
				break;
			case B:
				gradescore = 600;
				break;
			case A:
				gradescore = 800;
				break;
			case S:
				gradescore = 1000;
				break;
		}
		
		switch (item.getEnchantLevel())
		{
			case 0:
				enchscore = 0;
				break;
			case 1:
				enchscore = 100;
				break;
			case 2:
				enchscore = 100;
				break;
			case 3:
				enchscore = 100;
				break;
			case 4:
				enchscore = 150;
				break;
			case 5:
				enchscore = 200;
				break;
			case 6:
				enchscore = 350;
				break;
			case 7:
				enchscore = 400;
				break;
			case 8:
				enchscore = 450;
				break;
			case 9:
				enchscore = 500;
				break;
			case 10:
				enchscore = 550;
				break;
			case 11:
				enchscore = 600;
				break;
			case 12:
				enchscore = 650;
				break;
			case 13:
				enchscore = 700;
				break;
			case 14:
				enchscore = 750;
				break;
			case 15:
				enchscore = 800;
				break;
			case 16:
				enchscore = 850;
				break;
			case 17:
				enchscore = 900;
				break;
			case 18:
				enchscore = 950;
				break;
			case 19:
				enchscore = 1000;
				break;
			case 20:
				enchscore = 1050;
				break;
			case 21:
				enchscore = 1100;
				break;
			case 22:
				enchscore = 1150;
				break;
			case 23:
				enchscore = 1200;
				break;
			case 24:
				enchscore = 1250;
				break;
			case 25:
				enchscore = 1300;
				break;
		}
		
		score = augscore + enchscore + gradescore;
		return score;
	}
	
	public static int getGearScoreRRing(L2PcInstance activeChar)
	{
		int score = 0;
		int enchscore = 0, augscore = 0, gradescore = 0;
		
		ItemInstance item = activeChar.getInventory().getPaperdollItem(Inventory.PAPERDOLL_RFINGER);
		
		if (item == null)
			return 0;
		
		if (item.getAugmentation() != null)
			augscore = 500;
		else
			augscore = 0;
		
		switch (item.getItem().getCrystalType())
		{
			case NONE:
				gradescore = 0;
				break;
			case D:
				gradescore = 200;
				break;
			case C:
				gradescore = 400;
				break;
			case B:
				gradescore = 600;
				break;
			case A:
				gradescore = 800;
				break;
			case S:
				gradescore = 1000;
				break;
		}
		
		switch (item.getEnchantLevel())
		{
			case 0:
				enchscore = 0;
				break;
			case 1:
				enchscore = 100;
				break;
			case 2:
				enchscore = 100;
				break;
			case 3:
				enchscore = 100;
				break;
			case 4:
				enchscore = 150;
				break;
			case 5:
				enchscore = 200;
				break;
			case 6:
				enchscore = 350;
				break;
			case 7:
				enchscore = 400;
				break;
			case 8:
				enchscore = 450;
				break;
			case 9:
				enchscore = 500;
				break;
			case 10:
				enchscore = 550;
				break;
			case 11:
				enchscore = 600;
				break;
			case 12:
				enchscore = 650;
				break;
			case 13:
				enchscore = 700;
				break;
			case 14:
				enchscore = 750;
				break;
			case 15:
				enchscore = 800;
				break;
			case 16:
				enchscore = 850;
				break;
			case 17:
				enchscore = 900;
				break;
			case 18:
				enchscore = 950;
				break;
			case 19:
				enchscore = 1000;
				break;
			case 20:
				enchscore = 1050;
				break;
			case 21:
				enchscore = 1100;
				break;
			case 22:
				enchscore = 1150;
				break;
			case 23:
				enchscore = 1200;
				break;
			case 24:
				enchscore = 1250;
				break;
			case 25:
				enchscore = 1300;
				break;
		}
		
		score = augscore + enchscore + gradescore;
		return score;
	}
	
	public static int getGearScoreLRing(L2PcInstance activeChar)
	{
		int score = 0;
		int enchscore = 0, augscore = 0, gradescore = 0;
		
		ItemInstance item = activeChar.getInventory().getPaperdollItem(Inventory.PAPERDOLL_LFINGER);
		
		if (item == null)
			return 0;
		
		if (item.getAugmentation() != null)
			augscore = 500;
		else
			augscore = 0;
		
		switch (item.getItem().getCrystalType())
		{
			case NONE:
				gradescore = 0;
				break;
			case D:
				gradescore = 200;
				break;
			case C:
				gradescore = 400;
				break;
			case B:
				gradescore = 600;
				break;
			case A:
				gradescore = 800;
				break;
			case S:
				gradescore = 1000;
				break;
		}
		
		switch (item.getEnchantLevel())
		{
			case 0:
				enchscore = 0;
				break;
			case 1:
				enchscore = 100;
				break;
			case 2:
				enchscore = 100;
				break;
			case 3:
				enchscore = 100;
				break;
			case 4:
				enchscore = 150;
				break;
			case 5:
				enchscore = 200;
				break;
			case 6:
				enchscore = 350;
				break;
			case 7:
				enchscore = 400;
				break;
			case 8:
				enchscore = 450;
				break;
			case 9:
				enchscore = 500;
				break;
			case 10:
				enchscore = 550;
				break;
			case 11:
				enchscore = 600;
				break;
			case 12:
				enchscore = 650;
				break;
			case 13:
				enchscore = 700;
				break;
			case 14:
				enchscore = 750;
				break;
			case 15:
				enchscore = 800;
				break;
			case 16:
				enchscore = 850;
				break;
			case 17:
				enchscore = 900;
				break;
			case 18:
				enchscore = 950;
				break;
			case 19:
				enchscore = 1000;
				break;
			case 20:
				enchscore = 1050;
				break;
			case 21:
				enchscore = 1100;
				break;
			case 22:
				enchscore = 1150;
				break;
			case 23:
				enchscore = 1200;
				break;
			case 24:
				enchscore = 1250;
				break;
			case 25:
				enchscore = 1300;
				break;
		}
		
		score = augscore + enchscore + gradescore;
		return score;
	}
	
	public static int getGearScoreREar(L2PcInstance activeChar)
	{
		int score = 0;
		int enchscore = 0, augscore = 0, gradescore = 0;
		
		ItemInstance item = activeChar.getInventory().getPaperdollItem(Inventory.PAPERDOLL_REAR);
		
		if (item == null)
			return 0;
		
		if (item.getAugmentation() != null)
			augscore = 500;
		else
			augscore = 0;
		
		switch (item.getItem().getCrystalType())
		{
			case NONE:
				gradescore = 0;
				break;
			case D:
				gradescore = 200;
				break;
			case C:
				gradescore = 400;
				break;
			case B:
				gradescore = 600;
				break;
			case A:
				gradescore = 800;
				break;
			case S:
				gradescore = 1000;
				break;
		}
		
		switch (item.getEnchantLevel())
		{
			case 0:
				enchscore = 0;
				break;
			case 1:
				enchscore = 100;
				break;
			case 2:
				enchscore = 100;
				break;
			case 3:
				enchscore = 100;
				break;
			case 4:
				enchscore = 150;
				break;
			case 5:
				enchscore = 200;
				break;
			case 6:
				enchscore = 350;
				break;
			case 7:
				enchscore = 400;
				break;
			case 8:
				enchscore = 450;
				break;
			case 9:
				enchscore = 500;
				break;
			case 10:
				enchscore = 550;
				break;
			case 11:
				enchscore = 600;
				break;
			case 12:
				enchscore = 650;
				break;
			case 13:
				enchscore = 700;
				break;
			case 14:
				enchscore = 750;
				break;
			case 15:
				enchscore = 800;
				break;
			case 16:
				enchscore = 850;
				break;
			case 17:
				enchscore = 900;
				break;
			case 18:
				enchscore = 950;
				break;
			case 19:
				enchscore = 1000;
				break;
			case 20:
				enchscore = 1050;
				break;
			case 21:
				enchscore = 1100;
				break;
			case 22:
				enchscore = 1150;
				break;
			case 23:
				enchscore = 1200;
				break;
			case 24:
				enchscore = 1250;
				break;
			case 25:
				enchscore = 1300;
				break;
		}
		
		score = augscore + enchscore + gradescore;
		return score;
	}
	
	public static int getGearScoreLEar(L2PcInstance activeChar)
	{
		int score = 0;
		int enchscore = 0, augscore = 0, gradescore = 0;
		
		ItemInstance item = activeChar.getInventory().getPaperdollItem(Inventory.PAPERDOLL_LEAR);
		
		if (item == null)
			return 0;
		
		if (item.getAugmentation() != null)
			augscore = 500;
		else
			augscore = 0;
		
		switch (item.getItem().getCrystalType())
		{
			case NONE:
				gradescore = 0;
				break;
			case D:
				gradescore = 200;
				break;
			case C:
				gradescore = 400;
				break;
			case B:
				gradescore = 600;
				break;
			case A:
				gradescore = 800;
				break;
			case S:
				gradescore = 1000;
				break;
		}
		
		switch (item.getEnchantLevel())
		{
			case 0:
				enchscore = 0;
				break;
			case 1:
				enchscore = 100;
				break;
			case 2:
				enchscore = 100;
				break;
			case 3:
				enchscore = 100;
				break;
			case 4:
				enchscore = 150;
				break;
			case 5:
				enchscore = 200;
				break;
			case 6:
				enchscore = 350;
				break;
			case 7:
				enchscore = 400;
				break;
			case 8:
				enchscore = 450;
				break;
			case 9:
				enchscore = 500;
				break;
			case 10:
				enchscore = 550;
				break;
			case 11:
				enchscore = 600;
				break;
			case 12:
				enchscore = 650;
				break;
			case 13:
				enchscore = 700;
				break;
			case 14:
				enchscore = 750;
				break;
			case 15:
				enchscore = 800;
				break;
			case 16:
				enchscore = 850;
				break;
			case 17:
				enchscore = 900;
				break;
			case 18:
				enchscore = 950;
				break;
			case 19:
				enchscore = 1000;
				break;
			case 20:
				enchscore = 1050;
				break;
			case 21:
				enchscore = 1100;
				break;
			case 22:
				enchscore = 1150;
				break;
			case 23:
				enchscore = 1200;
				break;
			case 24:
				enchscore = 1250;
				break;
			case 25:
				enchscore = 1300;
				break;
		}
		
		score = augscore + enchscore + gradescore;
		return score;
	}
	
	public static int getGearScoreTattoo(L2PcInstance activeChar)
	{
		int score = 0;
		int enchscore = 0, augscore = 0, gradescore = 0;
		
		ItemInstance item = activeChar.getInventory().getPaperdollItem(Inventory.PAPERDOLL_UNDER);
		
		if (item == null)
			return 0;
		
		if (item.getAugmentation() != null)
			augscore = 500;
		else
			augscore = 0;
		
		switch (item.getItem().getCrystalType())
		{
			case NONE:
				gradescore = 0;
				break;
			case D:
				gradescore = 200;
				break;
			case C:
				gradescore = 400;
				break;
			case B:
				gradescore = 600;
				break;
			case A:
				gradescore = 800;
				break;
			case S:
				gradescore = 1000;
				break;
		}
		
		switch (item.getEnchantLevel())
		{
			case 0:
				enchscore = 0;
				break;
			case 1:
				enchscore = 100;
				break;
			case 2:
				enchscore = 100;
				break;
			case 3:
				enchscore = 100;
				break;
			case 4:
				enchscore = 150;
				break;
			case 5:
				enchscore = 200;
				break;
			case 6:
				enchscore = 350;
				break;
			case 7:
				enchscore = 400;
				break;
			case 8:
				enchscore = 450;
				break;
			case 9:
				enchscore = 500;
				break;
			case 10:
				enchscore = 550;
				break;
			case 11:
				enchscore = 600;
				break;
			case 12:
				enchscore = 650;
				break;
			case 13:
				enchscore = 700;
				break;
			case 14:
				enchscore = 750;
				break;
			case 15:
				enchscore = 800;
				break;
			case 16:
				enchscore = 850;
				break;
			case 17:
				enchscore = 900;
				break;
			case 18:
				enchscore = 950;
				break;
			case 19:
				enchscore = 1000;
				break;
			case 20:
				enchscore = 1050;
				break;
			case 21:
				enchscore = 1100;
				break;
			case 22:
				enchscore = 1150;
				break;
			case 23:
				enchscore = 1200;
				break;
			case 24:
				enchscore = 1250;
				break;
			case 25:
				enchscore = 1300;
				break;
		}
		
		score = augscore + enchscore + gradescore;
		return score;
	}
}