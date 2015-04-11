package net.sf.l2j.gameserver.model.actor.knownlist;

import net.sf.l2j.gameserver.model.actor.L2Playable;

public class PlayableKnownList extends CharKnownList
{
	public PlayableKnownList(L2Playable activeChar)
	{
		super(activeChar);
	}
	
	@Override
	public L2Playable getActiveChar()
	{
		return (L2Playable) super.getActiveChar();
	}
}