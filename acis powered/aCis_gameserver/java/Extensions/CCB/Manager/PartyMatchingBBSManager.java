package Extensions.CCB.Manager;

import Extensions.CCB.Manager.PartyMatchingBBSManager;

import net.sf.l2j.gameserver.cache.HtmCache;
import net.sf.l2j.gameserver.communitybbs.Manager.BaseBBSManager;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.ShowBoard;

public class PartyMatchingBBSManager extends BaseBBSManager
{
	@Override
	public void parseCmd(String command, L2PcInstance activeChar)
	{
		if (command.equals("pmatch"))
		{
			refresh(activeChar);
		}
		else
		{
			ShowBoard sb = new ShowBoard("<html><body><br><br><center>The command: " + command + " is not implemented yet.</center><br><br></body></html>", "101");
			activeChar.sendPacket(sb);
			activeChar.sendPacket(new ShowBoard(null, "102"));
			activeChar.sendPacket(new ShowBoard(null, "103"));
		}
	}
	
	public static void refresh(L2PcInstance activeChar)
	{
		PartyMatchingBoard partyMembers = new PartyMatchingBoard();
		String content = HtmCache.getInstance().getHtm("data/html/CommunityBoard/Custom/pmaching.htm").replace("%partyMatchingMembers%", partyMembers.loadPartyMatchingMembersList());
		
		if (content == null)
		{
			content = "<html><body><br><br><center>404 :File not found: 'data/html/CommunityBoard/Custom/pmaching.htm'</center></body></html>";
		}
		
		separateAndSend(content, activeChar);
	}
	
	@Override
	public void parseWrite(String ar1, String ar2, String ar3, String ar4, String ar5, L2PcInstance activeChar)
	{
		
	}
	
	public static PartyMatchingBBSManager getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final PartyMatchingBBSManager _instance = new PartyMatchingBBSManager();
	}
}