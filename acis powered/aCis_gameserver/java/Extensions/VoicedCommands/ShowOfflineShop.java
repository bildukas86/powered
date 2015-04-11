package Extensions.VoicedCommands;

import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class ShowOfflineShop implements IVoicedCommandHandler
{
	private static final String[] commands =
	{
		"showoffshop"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String params)
	{
		playersInOfflineShop(activeChar);
		return true;
	}
	
	private static void playersInOfflineShop(L2PcInstance activeChar)
	{
		int playerInShop = 0;
		
		for (L2PcInstance p : L2World.getInstance().getAllPlayers().values())
		{
			if (p.getIsOfflineShop())
				playerInShop++;
		}
		
		activeChar.sendMessage("[Offline Shop(s)]: " + playerInShop);
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return commands;
	}
}