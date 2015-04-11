package net.sf.l2j.gameserver.handler.chathandlers;

import java.util.StringTokenizer;

import net.sf.l2j.gameserver.handler.IChatHandler;
import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.handler.VoicedCommandHandler;
import net.sf.l2j.gameserver.model.BlockList;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;

/**
 * A chat handler
 * @author durgus
 */
public class ChatAll implements IChatHandler
{
	private static boolean _chatDisabled = false;
		 
	private static final int[] COMMAND_IDS =
	{
		0
	};
	
	/**
	 * Handle chat type 'all'
	 * @see net.sf.l2j.gameserver.handler.IChatHandler#handleChat(int, net.sf.l2j.gameserver.model.actor.instance.L2PcInstance, java.lang.String, java.lang.String)
	 */
	@Override
	public void handleChat(int type, L2PcInstance activeChar, String params, String text)
	{
        if (text.startsWith("."))
        {
            StringTokenizer st = new StringTokenizer(text);
            IVoicedCommandHandler vch;
            String command = "";

            if (st.countTokens() > 1)
            {
                command = st.nextToken().substring(1);
                params = text.substring(command.length() + 2);
                vch = VoicedCommandHandler.getInstance().getVoicedCommandHandler(command);
            }
            else
            {
                command = text.substring(1);
                vch = VoicedCommandHandler.getInstance().getVoicedCommandHandler(command);
            }
            if (vch != null)
            {
                vch.useVoicedCommand(command, activeChar, params);
            }
        }
        else
        {
			if (isChatDisabled() && !activeChar.isGM())
			{
				activeChar.sendPacket(SystemMessageId.GM_NOTICE_CHAT_DISABLED);
				return;
			}
        	CreatureSay cs = new CreatureSay(activeChar.getObjectId(), type, activeChar.getName(), text);
    		
        	for (L2PcInstance player : activeChar.getKnownList().getKnownTypeInRadius(L2PcInstance.class, 1250))
    			if (activeChar.isInsideRadius(player, 1250, false, true) && !BlockList.isBlocked(player, activeChar))
    				player.sendPacket(cs);
    		activeChar.sendPacket(cs);
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
	
	@Override
	public int[] getChatTypeList()
	{
		return COMMAND_IDS;
	}
}