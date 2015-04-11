package net.sf.l2j.gameserver.handler;

import java.util.logging.Logger;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public interface IVoicedCommandHandler
{
	public static Logger _log = Logger.getLogger(IVoicedCommandHandler.class.getName());

	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String params);

	public String[] getVoicedCommandList();
}