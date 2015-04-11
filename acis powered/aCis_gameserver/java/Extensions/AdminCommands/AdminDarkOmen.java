// Decompiled by DJ v3.12.12.99 Copyright 2015 Atanas Neshkov  Date: 4/2/2015 11:46:30 AM
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   AdminDarkOmen.java

package Extensions.AdminCommands;

import Extensions.Events.DarkOmenEventManager;
import net.sf.l2j.gameserver.Announcements;
import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class AdminDarkOmen
    implements IAdminCommandHandler
{

    public AdminDarkOmen()
    {
    }

    public boolean useAdminCommand(String command, L2PcInstance activeChar)
    {
        if(command.startsWith("admin_darkopen"))
        {
            DarkOmenEventManager.darkOmenRunning = true;
            Announcements.announceToAll("Catacomb of Dark Omen event is now open! Go to the special gatekeeper in Gludin and teleport in.");
            Announcements.announceToAll("Xp and drop rates are doubled inside. Good Luck.");
            DarkOmenEventManager.spawnMonsters();
        } else
        if(command.startsWith("admin_darkclose"))
        {
            DarkOmenEventManager.darkOmenRunning = false;
            Announcements.announceToAll("Dark Omen Event is now over. Thanks for joining.");
            DarkOmenEventManager.despawnMnosters();
        }
        return true;
    }

    public String[] getAdminCommandList()
    {
        return ADMIN_COMMANDS;
    }

    private static final String ADMIN_COMMANDS[] = {
        "admin_darkopen", "admin_darkclose"
    };

}
