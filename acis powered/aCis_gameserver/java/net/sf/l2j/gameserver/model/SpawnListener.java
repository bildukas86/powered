package net.sf.l2j.gameserver.model;

import net.sf.l2j.gameserver.model.actor.L2Npc;

public interface SpawnListener
{
	public void npcSpawned(L2Npc npc);
}