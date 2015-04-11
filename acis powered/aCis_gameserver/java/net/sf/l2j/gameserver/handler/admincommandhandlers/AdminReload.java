package net.sf.l2j.gameserver.handler.admincommandhandlers;

import Extensions.Balancer.BalanceLoad;

import java.io.File;
import java.util.StringTokenizer;

import javax.script.ScriptException;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.cache.CrestCache;
import net.sf.l2j.gameserver.cache.HtmCache;
import net.sf.l2j.gameserver.datatables.AdminCommandAccessRights;
import net.sf.l2j.gameserver.datatables.DoorTable;
import net.sf.l2j.gameserver.datatables.ItemTable;
import net.sf.l2j.gameserver.datatables.MultisellData;
import net.sf.l2j.gameserver.datatables.NpcTable;
import net.sf.l2j.gameserver.datatables.NpcWalkerRoutesTable;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.datatables.SpawnTable;
import net.sf.l2j.gameserver.datatables.TeleportLocationTable;
import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.instancemanager.CursedWeaponsManager;
import net.sf.l2j.gameserver.instancemanager.DayNightSpawnManager;
import net.sf.l2j.gameserver.instancemanager.QuestManager;
import net.sf.l2j.gameserver.instancemanager.RaidBossSpawnManager;
import net.sf.l2j.gameserver.instancemanager.SevenSigns;
import net.sf.l2j.gameserver.instancemanager.ZoneManager;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.scripting.L2ScriptEngineManager;

public class AdminReload implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_reload",
		"admin_script_load"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_reload"))
		{
			StringTokenizer st = new StringTokenizer(command);
			st.nextToken();
			
			final String type = st.nextToken();
			try
			{
				if (type != null)
				if (type.equals("multisell"))
				{
					MultisellData.getInstance().reload();
					activeChar.sendMessage("Multisells has been reloaded.");
				}
				else if (type.equals("doors"))
				{
					DoorTable.getInstance().reload();
					activeChar.sendMessage("Door data reloaded.");
				}
				else if (type.equals("teleport"))
				{
					TeleportLocationTable.getInstance().reload();
					activeChar.sendMessage("Teleport locations has been reloaded.");
				}
				else if (type.equals("npc") || type.equals("npcs"))
				{
					NpcTable.getInstance().reloadAllNpc();
					activeChar.sendMessage("All NPCs have been reloaded.");
				}
				else if (type.equals("respawn_npc") || type.equals("respawn_npcs"))
				{
					for (L2PcInstance player : L2World.getInstance().getAllPlayers().values())
						player.sendPacket(SystemMessageId.NPC_SERVER_NOT_OPERATING);
					RaidBossSpawnManager.getInstance().cleanUp();
					DayNightSpawnManager.getInstance().cleanUp();
					L2World.getInstance().deleteVisibleNpcSpawns();
					for (L2PcInstance player : L2World.getAllGMs())
						player.sendMessage("NPC Unspawn completed!");
					// make sure all spawns are deleted
					RaidBossSpawnManager.getInstance().cleanUp();
					DayNightSpawnManager.getInstance().cleanUp();
					L2World.getInstance().deleteVisibleNpcSpawns();
					// now respawn all
					NpcTable.getInstance().reloadAllNpc();
					SpawnTable.getInstance().reloadAll();
					RaidBossSpawnManager.getInstance().reloadBosses();
					SevenSigns.getInstance().spawnSevenSignsNPC();
					for (L2PcInstance player : L2World.getAllGMs())
						player.sendMessage("NPC Respawn completed!");
					activeChar.sendMessage("All NPCs have been reloaded.");
				}
				else if (type.equals("zone"))
				{
					ZoneManager.getInstance().reload();
					activeChar.sendMessage("All Zones have been reloaded.");
				}
				else if (type.equals("html") || type.equals("htm"))
				{
					HtmCache.getInstance().reload();
					activeChar.sendMessage("The HTM cache has been reloaded.");
				}
				else if (type.equals("crest"))
				{
					CrestCache.load();
					activeChar.sendMessage("Crests have been reloaded.");
				}
				else if (command.equals("fix_crest"))
				{
					CrestCache.convertOldPledgeFiles();
					activeChar.sendMessage("Cache[Crest]: crests fixed.");
				}
				else if (type.equals("item") || type.equals("items"))
				{
					ItemTable.getInstance().reload();
					activeChar.sendMessage("Item table has been reloaded.");
				}
				else if (type.equals("access"))
				{
					AdminCommandAccessRights.getInstance().reload();
					activeChar.sendMessage("Access Rights have been reloaded.");
				}
				else if (type.equals("npcwalkers"))
				{
					NpcWalkerRoutesTable.getInstance().reload();
					activeChar.sendMessage("All NPC walker routes have been reloaded.");
				}
				else if (type.startsWith("quests"))
				{
					if (st.hasMoreTokens())
					{
						int qId = Integer.parseInt(st.nextToken());
						if (QuestManager.getInstance().reload(qId))
							activeChar.sendMessage("Quest " + qId + " has been reloaded.");
						else
							activeChar.sendMessage("Quest " + qId + " failed reloading.");
					}
					else
						activeChar.sendMessage("Usage : //reload quest questNumber.");
					
				}
				else if (type.equals("configs") || type.equals("config"))
				{
					Config.load();
					activeChar.sendMessage("Server Configs has been Reloaded.");
				}
				else if (type.equals("cw") || type.equals("cursed"))
				{
					CursedWeaponsManager.getInstance().reload();
					activeChar.sendMessage("Cursed Weapons has been reloaded.");
				}
				else if (type.equals("balancer") || type.equals("balance"))
				{
					BalanceLoad.LoadEm();
					activeChar.sendMessage("Balance stats for classes has been reloaded.");
				}
				else if (type.equals("skill") || type.equals("skills"))
				{
					SkillTable.getInstance().reload();
					activeChar.sendMessage("Skills' XMLs have been reloaded.");
				}
				else if (type.equals("scripts"))
				{
					QuestManager.getInstance().reloadAllQuests();
					QuestManager.getInstance().report();
					activeChar.sendMessage("All scripts have been reloaded.");
				}
				// else if (type.startsWith("handler"))
				// {
				// File file = new File(L2ScriptEngineManager.SCRIPT_FOLDER, "handlers/MasterHandler.java");
				// try
				// {
				// L2ScriptEngineManager.getInstance().executeScript(file);
				// activeChar.sendMessage("All handlers have been reloaded");
				// }
				// catch (ScriptException e)
				// {
				// L2ScriptEngineManager.getInstance().reportScriptFileError(file, e);
				// activeChar.sendMessage("There was an error while loading handlers.");
				// }
				// sendReloadPage(activeChar);
				// }
				// This provides a way to load new scripts without having to reboot the server.
				// If a script is already loaded, quest_reload should be used.
				else if (command.startsWith("admin_script_load"))
				{
					String[] parts = command.split(" ");
					if (parts.length < 2)
						activeChar.sendMessage("Example: //script_load quests/questFolder/filename.ext");
					else
					{
						File file = new File(L2ScriptEngineManager.SCRIPT_FOLDER, parts[1]);
						if (file.isFile())
						{
							try
							{
								L2ScriptEngineManager.getInstance().executeScript(file);
							}
							catch (ScriptException e)
							{
								activeChar.sendMessage("Failed loading: " + parts[1]);
								L2ScriptEngineManager.reportScriptFileError(file, e);
							}
							catch (Exception e)
							{
								activeChar.sendMessage("Failed loading: " + parts[1]);
							}
						}
						else
							activeChar.sendMessage("Current file hasn't been found: " + parts[1]);
					}
					
				}
			}
			catch (Exception e)
			{
				activeChar.sendMessage("An error occured while reloading " + type + " !");
				activeChar.sendMessage("Usage: //reload <multisell|doors|teleport|npc|npcs|respawn_npc>");
				activeChar.sendMessage("Usage: //reload <respawn_npcs|zone|htm|html|crest|fix_crest>");
				activeChar.sendMessage("Usage: //reload <item|items|access|npcwalkers|quests|config>");
				activeChar.sendMessage("Usage: //reload <configs|cursed|cw|balancer|balance>");
				activeChar.sendMessage("Usage: //reload <skill|skills|scripts|admin_script_load>");
			}
		}
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}