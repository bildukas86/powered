package net.sf.l2j.gameserver.handler.chathandlers;

import java.util.Collection;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.datatables.MapRegionTable;
import net.sf.l2j.gameserver.handler.IChatHandler;
import net.sf.l2j.gameserver.model.BlockList;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;

public class ChatShout implements IChatHandler
{
	private static boolean _chatDisabled = false;
	
	private static final int[] COMMAND_IDS =
	{
		1
	};
	
	/**
	 * Handle chat type 'shout'
	 * @see net.sf.l2j.gameserver.handler.IChatHandler#handleChat(int, net.sf.l2j.gameserver.model.actor.instance.L2PcInstance, java.lang.String, java.lang.String)
	 */
	@Override
	public void handleChat(int type, L2PcInstance activeChar, String target, String text)
	{
		if (isChatDisabled() && !activeChar.isGM())
		{
			activeChar.sendPacket(SystemMessageId.GM_NOTICE_CHAT_DISABLED);
			return;
		}
		
		if (Config.ENABLE_SHOUT_CHAT_FOR_PVPS && activeChar.getPvpKills() < Config.SHOUT_CHAT_PVPS)
		{
			activeChar.sendMessage("You need at least " + Config.SHOUT_CHAT_PVPS + " pvps in order to use this chat.");
			return;
		}
		
		if (Config.ENABLE_SHOUT_CHAT_FOR_LEVEL && !(activeChar.getLevel() > Config.SHOUT_CHAT_LEVEL))
		{
			activeChar.sendMessage("You must be above level " + Config.SHOUT_CHAT_LEVEL + " to use global chat.");
			return;
		}
		
		CreatureSay cs = new CreatureSay(activeChar.getObjectId(), type, activeChar.getName(), text);
		Collection<L2PcInstance> pls = L2World.getInstance().getAllPlayers().values();
		
		if (Config.DEFAULT_GLOBAL_CHAT.equalsIgnoreCase("on") || (Config.DEFAULT_GLOBAL_CHAT.equalsIgnoreCase("gm") && activeChar.isGM()))
		{
			int region = MapRegionTable.getMapRegion(activeChar.getX(), activeChar.getY());
			for (L2PcInstance player : pls)
			{
				if (region == MapRegionTable.getMapRegion(player.getX(), player.getY()) && !BlockList.isBlocked(player, activeChar) && player.getInstanceId() == activeChar.getInstanceId())
					player.sendPacket(cs);
			}
		}
		else if (Config.DEFAULT_GLOBAL_CHAT.equalsIgnoreCase("global"))
		{
			for (L2PcInstance player : pls)
			{
				if (!BlockList.isBlocked(player, activeChar))
					player.sendPacket(cs);
			}
		}
	}
	
	/**
	 * @return Returns the chatDisabled.
	 */
	public static boolean isChatDisabled()
	{
		return _chatDisabled;
	}
	
	/**
	 * @param chatDisabled The chatDisabled to set.
	 */
	public static void setIsChatDisabled(boolean chatDisabled)
	{
		_chatDisabled = chatDisabled;
	}
	
	/**
	 * Returns the chat types registered to this handler
	 * @see net.sf.l2j.gameserver.handler.IChatHandler#getChatTypeList()
	 */
	@Override
	public int[] getChatTypeList()
	{
		return COMMAND_IDS;
	}
}