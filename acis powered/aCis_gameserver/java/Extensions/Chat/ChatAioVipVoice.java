package Extensions.Chat;

import Extensions.Vip.VIPEngine;

import java.util.Collection;

import net.sf.l2j.gameserver.handler.IChatHandler;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;

/**
 * A Custom chat handler
 */

public class ChatAioVipVoice implements IChatHandler
{
	private static final int[] COMMAND_IDS =
	{
		18
	};
	
	private static boolean _chatDisabled = false;
	
	@Override
	public void handleChat(int type, L2PcInstance activeChar, String target, String text)
	{
		if (isChatDisabled() && !activeChar.isGM())
		{
			activeChar.sendPacket(SystemMessageId.GM_NOTICE_CHAT_DISABLED);
			return;
		}
		
		if (activeChar.isAio() || VIPEngine.getInstance().isVip(activeChar))
		{
			if (!activeChar.getFloodProtectors().getAioVipVoice().tryPerformAction("AioVipVoice"))
				return;
			
			CreatureSay cs = new CreatureSay(activeChar.getObjectId(), type, activeChar.getName(), text);
			
			Collection<L2PcInstance> pls = L2World.getInstance().getAllPlayers().values();
			for (L2PcInstance player : pls)
				player.sendPacket(cs);
		}
	}
	
	public static boolean isChatDisabled()
	{
		return _chatDisabled;
	}
	
	public static void setIsChatDisabled(boolean chatDisabled)
	{
		_chatDisabled = chatDisabled;
	}
	
	@Override
	public int[] getChatTypeList()
	{
		return COMMAND_IDS;
	}
}