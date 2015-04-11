package net.sf.l2j.gameserver.model.actor.instance;

import java.util.StringTokenizer;

import net.sf.l2j.gameserver.ai.CtrlIntention;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;
import net.sf.l2j.gameserver.network.serverpackets.MoveToPawn;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public final class L2EffectBufferInstance extends L2NpcInstance
{
    public L2EffectBufferInstance(int objectId, NpcTemplate template)
    {
        super(objectId, template);
    }
    
    @Override
	public void onAction(L2PcInstance player)
	{
		// Set the target of the L2PcInstance player
		if (player.getTarget() != this)
			player.setTarget(this);
		else
		{
			// Check if the player is attackable (without a forced attack) and isn't dead
			if (isAutoAttackable(player) && !isAlikeDead())
			{
				// Check the height difference, this max heigth difference might need some tweaking
				if (Math.abs(player.getZ() - getZ()) < 400)
				{
					// Set the L2PcInstance Intention to ATTACK
					player.getAI().setIntention(CtrlIntention.ATTACK, this);
				}
				else
				{
					// Send ActionFailed (target is out of attack range) to the L2PcInstance player
					player.sendPacket(ActionFailed.STATIC_PACKET);
				}
			}
			else if (!isAutoAttackable(player))
			{
				// Calculate the distance between the L2PcInstance and the L2NpcInstance
				if (!canInteract(player))
				{
					// Notify the L2PcInstance AI with INTERACT
					player.getAI().setIntention(CtrlIntention.INTERACT, this);
				}
				else
				{
					// Rotate the player to face the instance
					player.sendPacket(new MoveToPawn(player, this, L2Npc.INTERACTION_DISTANCE));
					
					// Send ActionFailed to the player in order to avoid he stucks
					player.sendPacket(ActionFailed.STATIC_PACKET);
					
					// Send the freaking chat window
					showChatWindow(player);
				}
			}
			// Send a Server->Client ActionFailed to the L2PcInstance in order to avoid that the client wait another packet
			player.sendPacket(ActionFailed.STATIC_PACKET);
		}
	}
    
    @Override
    public void onBypassFeedback(L2PcInstance player, String command)
    {
    	if (player == null)
    		return;
    	
    	StringTokenizer st = new StringTokenizer(command, " ");
    	String actualCommand = st.nextToken();
    	
    	int buffid = 0, bufflevel = 1;
    	if (st.countTokens() == 2)
    	{
    		buffid = Integer.valueOf(st.nextToken());
    		bufflevel = Integer.valueOf(st.nextToken());
    	}
    	else if (st.countTokens() == 1)
    		buffid = Integer.valueOf(st.nextToken());
    	
    	if (actualCommand.equalsIgnoreCase("getbuff"))
    	{
    		if (buffid != 0 && !player.isDead())
    		{
    			SkillTable.getInstance().getInfo(buffid, bufflevel).getEffects(this, player);
    			broadcastPacket(new MagicSkillUse(this, player, buffid, bufflevel, 500, 0));
    			showChatWindow(player);
    		}
    	}
    	else if (actualCommand.equalsIgnoreCase("restore"))
    	{
    		if (!player.isDead())
    		{
    			player.setCurrentHp(player.getMaxHp());
    			broadcastPacket(new MagicSkillUse(this, player, 1258, 4, 500, 0));
        		showChatWindow(player);
    		}
    	}
    	else if (actualCommand.equalsIgnoreCase("cancel"))
    	{
    		if (!player.isDead())
    		{
    			player.stopAllEffects();
    			broadcastPacket(new MagicSkillUse(this, player, 1056, 12, 500, 0));
        		showChatWindow(player);
    		}
    	}
    	else
    		super.onBypassFeedback(player, command);
    }
    
    @Override
    public void showChatWindow(L2PcInstance player)
    {
    	// Send a Server->Client NpcHtmlMessage containing the text of the L2Npc to the L2PcInstance
    	NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
		html.setFile("data/html/mods/EffectBuffer/Buffer.htm");
		html.replace("%objectId%", getObjectId());
		player.sendPacket(html);
		
		// Send a Server->Client ActionFailed to the L2PcInstance in order to avoid that the client wait another packet
		player.sendPacket(ActionFailed.STATIC_PACKET);
    }
}