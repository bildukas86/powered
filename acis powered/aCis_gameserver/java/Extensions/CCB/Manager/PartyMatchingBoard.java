package Extensions.CCB.Manager;

import Extensions.Utilities.ClassList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.datatables.ClanTable;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class PartyMatchingBoard
{
	L2PcInstance player;
	int changeColor = 0;
	
	private final StringBuilder partyMatchingList = new StringBuilder();
	
	public PartyMatchingBoard()
	{
		loadPartyMatchingMembers();
	}
	
	public void loadPartyMatchingMembers()
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT char_name, level, clanid, classid, base_class FROM characters WHERE partyMatchingStatus=1 AND online=1");
			ResultSet result = statement.executeQuery();
			while (result.next())
			{
				addPartyMatchingMember(result.getString("char_name"), result.getInt("level"), result.getInt("clanid"), result.getInt("classid"), result.getInt("base_class"));
			}
			
			result.close();
			statement.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void addPartyMatchingMember(String charName, int charLevel, int charClan, int charClassId, int charBaseClassId)
	{
		changeColor++;
		partyMatchingList.append("<table width=600 height=20 " + ((changeColor % 2) == 1 ? "" : "bgcolor=\"#171612\"") + "><tr>");
		partyMatchingList.append("<td fixwidth=140 align=center>" + charName + "</td>");
		partyMatchingList.append("<td fixwidth=30 align=center>" + charLevel + "</td>");
		partyMatchingList.append(charClan != 0 ? "<td fixwidth=140 align=center>" + ClanTable.getInstance().getClan(charClan).getName() + "</td>" : "<td fixwidth=140></td>");
		partyMatchingList.append("<td fixwidth=14 height=18 align=left><img src=\"L2UI_CH3." + getClassIcon(charClassId) + "\" width=12 height=12></td>");
		partyMatchingList.append("<td fixwidth=100 align=center>" + className(charClassId) + "</td>");
		partyMatchingList.append(charClassId != charBaseClassId ? "<td fixwidth=100 align=center>No</td>" : "<td fixwidth=100 align=center>Yes</td>");
		partyMatchingList.append("<td fixwidth=5></td>");
		partyMatchingList.append("<td fixwidth=90 align=center valign=middle><button action=\"bypass -h partyMatchingInvite " + charName + " \" value=\"Invite\" width=80 height=20 back=\"L2UI_ch3.Btn1_normalOn\" fore=\"L2UI_ch3.Btn1_normal\"></td>");
		partyMatchingList.append("</tr></table>");
		partyMatchingList.append("<img src=\"L2UI.SquareGray\" width=600 height=1>");
	}
	
	public String getClassIcon(int classId)
	{
		switch (classId)
		{
			case 0:
				return "party_styleicon1_1";
			case 1:
				return "party_styleicon1_1";
			case 2:
				return "party_styleicon1";
			case 3:
				return "party_styleicon1";
			case 4:
				return "party_styleicon1_1";
			case 5:
				return "party_styleicon3";
			case 6:
				return "party_styleicon3";
			case 7:
				return "party_styleicon1_1";
			case 8:
				return "party_styleicon1";
			case 9:
				return "party_styleicon2";
			case 10:
				return "party_styleicon1_2";
			case 11:
				return "party_styleicon1_2";
			case 12:
				return "party_styleicon5";
			case 13:
				return "party_styleicon5";
			case 14:
				return "party_styleicon7";
			case 15:
				return "party_styleicon1_2";
			case 16:
				return "party_styleicon6";
			case 17:
				return "party_styleicon6";
			case 18:
				return "party_styleicon1_1";
			case 19:
				return "party_styleicon1_1";
			case 20:
				return "party_styleicon3";
			case 21:
				return "party_styleicon4";
			case 22:
				return "party_styleicon1_1";
			case 23:
				return "party_styleicon1";
			case 24:
				return "party_styleicon2";
			case 25:
				return "party_styleicon1_2";
			case 26:
				return "party_styleicon1_2";
			case 27:
				return "party_styleicon5";
			case 28:
				return "party_styleicon7";
			case 29:
				return "party_styleicon6";
			case 30:
				return "party_styleicon6";
			case 31:
				return "party_styleicon1_1";
			case 32:
				return "party_styleicon1_1";
			case 33:
				return "party_styleicon3";
			case 34:
				return "party_styleicon4";
			case 35:
				return "party_styleicon1_1";
			case 36:
				return "party_styleicon1";
			case 37:
				return "party_styleicon2";
			case 38:
				return "party_styleicon1_2";
			case 39:
				return "party_styleicon1_2";
			case 40:
				return "party_styleicon5";
			case 41:
				return "party_styleicon7";
			case 42:
				return "party_styleicon6";
			case 43:
				return "party_styleicon6";
			case 44:
				return "party_styleicon1_1";
			case 45:
				return "party_styleicon1_1";
			case 46:
				return "party_styleicon1";
			case 47:
				return "party_styleicon1_1";
			case 48:
				return "party_styleicon1";
			case 49:
				return "party_styleicon1_2";
			case 50:
				return "party_styleicon1_2";
			case 51:
				return "party_styleicon6";
			case 52:
				return "party_styleicon6";
			case 53:
				return "party_styleicon1_1";
			case 54:
				return "party_styleicon1_1";
			case 55:
				return "party_styleicon1";
			case 56:
				return "party_styleicon1_1";
			case 57:
				return "party_styleicon1";
			case 88:
				return "party_styleicon1_3";
			case 89:
				return "party_styleicon1_3";
			case 90:
				return "party_styleicon3_3";
			case 91:
				return "party_styleicon3_3";
			case 92:
				return "party_styleicon2_3";
			case 93:
				return "party_styleicon1_3";
			case 94:
				return "party_styleicon5_3";
			case 95:
				return "party_styleicon5_3";
			case 96:
				return "party_styleicon7_3";
			case 97:
				return "party_styleicon6_3";
			case 98:
				return "party_styleicon6_3";
			case 99:
				return "party_styleicon3_3";
			case 100:
				return "party_styleicon4_3";
			case 101:
				return "party_styleicon1_3";
			case 102:
				return "party_styleicon2_3";
			case 103:
				return "party_styleicon5_3";
			case 104:
				return "party_styleicon7_3";
			case 105:
				return "party_styleicon6_3";
			case 106:
				return "party_styleicon3_3";
			case 107:
				return "party_styleicon4_3";
			case 108:
				return "party_styleicon1_3";
			case 109:
				return "party_styleicon2_3";
			case 110:
				return "party_styleicon5_3";
			case 111:
				return "party_styleicon7_3";
			case 112:
				return "party_styleicon6_3";
			case 113:
				return "party_styleicon1_3";
			case 114:
				return "party_styleicon1_3";
			case 115:
				return "party_styleicon6_3";
			case 116:
				return "party_styleicon6_3";
			case 117:
				return "party_styleicon1_3";
			case 118:
				return "party_styleicon1_3";
			case 123:
				return "party_styleicon1_1";
			case 124:
				return "party_styleicon1_1";
			case 125:
				return "party_styleicon1_1";
			case 126:
				return "party_styleicon1_1";
			case 127:
				return "party_styleicon1";
			case 128:
				return "party_styleicon1";
			case 129:
				return "party_styleicon1";
			case 130:
				return "party_styleicon2";
			case 131:
				return "party_styleicon1_3";
			case 132:
				return "party_styleicon1_3";
			case 133:
				return "party_styleicon1_3";
			case 134:
				return "party_styleicon2_3";
			case 135:
				return "party_styleicon4";
			case 136:
				return "party_styleicon4_3";
			default:
				return "party_styleicon1_1";
		}
	}
	
	private final static String className(int classId)
	{
		return String.valueOf(ClassList.className(classId));
	}
	
	public String loadPartyMatchingMembersList()
	{
		return partyMatchingList.toString();
	}
}