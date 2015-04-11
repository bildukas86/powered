package net.sf.l2j.gameserver.handler.admincommandhandlers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.Announcements;
import net.sf.l2j.gameserver.datatables.GmListTable;
import net.sf.l2j.gameserver.datatables.NpcTable;
import net.sf.l2j.gameserver.datatables.SpawnTable;
import net.sf.l2j.gameserver.datatables.FenceTable;
import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.idfactory.IdFactory;
import net.sf.l2j.gameserver.instancemanager.DayNightSpawnManager;
import net.sf.l2j.gameserver.instancemanager.RaidBossSpawnManager;
import net.sf.l2j.gameserver.instancemanager.SevenSigns;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.L2Spawn;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.L2WorldRegion;
import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2FenceInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.util.Broadcast;
import net.sf.l2j.util.StringUtil;

/**
 * This class handles following admin commands:<br>
 * - show_spawns = shows menu<br>
 * - spawn_index lvl = shows menu for monsters with respective level<br>
 * - spawn id = spawns monster id on target
 */
public class AdminSpawn implements IAdminCommandHandler
{
	public static Logger _log = Logger.getLogger(AdminSpawn.class.getName());
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_list_spawns",
		"admin_show_spawns",
		"admin_spawn",
		"admin_spawn_index",
		"admin_unspawnall",
		"admin_respawnall",
		"admin_spawn_reload",
		"admin_npc_index",
		"admin_spawn_once",
		"admin_show_npcs",
		"admin_spawnnight",
		"admin_spawnday",
		"admin_spawnfence",
		"admin_deletefence",
		"admin_listfence"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_list_spawns"))
		{
			int npcId = 0;
			
			try
			{
				String[] params = command.split(" ");
				Pattern pattern = Pattern.compile("[0-9]*");
				Matcher regexp = pattern.matcher(params[1]);
				
				if (regexp.matches())
					npcId = Integer.parseInt(params[1]);
				else
				{
					params[1] = params[1].replace('_', ' ');
					npcId = NpcTable.getInstance().getTemplateByName(params[1]).getNpcId();
				}
			}
			catch (Exception e)
			{
				// If the parameter wasn't ok, then take the current target.
				final L2Object target = activeChar.getTarget();
				if (target instanceof L2Npc)
					npcId = ((L2Npc) target).getNpcId();
			}
			
			// Load static Htm.
			NpcHtmlMessage adminReply = new NpcHtmlMessage(0);
			adminReply.setFile("data/html/admin/listspawns.htm");
			
			// Generate data.
			final StringBuilder replyMSG = new StringBuilder();
			
			int index = 0;
			String name = "", x, y, z;
			
			for (L2Spawn spawn : SpawnTable.getInstance().getSpawnTable())
			{
				if (npcId == spawn.getNpcId())
				{
					index++;
					name = spawn.getTemplate().getName();
					
					final L2Npc _npc = spawn.getLastSpawn();
					if (_npc != null)
					{
						x = String.valueOf(_npc.getX());
						y = String.valueOf(_npc.getY());
						z = String.valueOf(_npc.getZ());
					}
					else
					{
						x = String.valueOf(spawn.getLocx());
						y = String.valueOf(spawn.getLocy());
						z = String.valueOf(spawn.getLocz());
					}
					StringUtil.append(replyMSG, "<tr><td><a action=\"bypass -h admin_move_to ", x, " ", y, " ", z, "\">", String.valueOf(index), " - (", x, " ", y, " ", z, ")", "</a></td></tr>");
				}
			}
			
			if (index == 0)
			{
				adminReply.replace("%npcid%", "?");
				adminReply.replace("%list%", "<tr><td>The parameter you entered as npcId is invalid.</td></tr>");
			}
			else
			{
				adminReply.replace("%npcid%", name + " (" + npcId + ")");
				adminReply.replace("%list%", replyMSG.toString());
			}
			
			activeChar.sendPacket(adminReply);
		}
		else if (command.equals("admin_show_spawns"))
			AdminHelpPage.showHelpPage(activeChar, "spawns.htm");
		else if (command.startsWith("admin_spawn_index"))
		{
			StringTokenizer st = new StringTokenizer(command, " ");
			try
			{
				st.nextToken();
				int level = Integer.parseInt(st.nextToken());
				int from = 0;
				try
				{
					from = Integer.parseInt(st.nextToken());
				}
				catch (NoSuchElementException nsee)
				{
				}
				showMonsters(activeChar, level, from);
			}
			catch (Exception e)
			{
				AdminHelpPage.showHelpPage(activeChar, "spawns.htm");
			}
		}
		else if (command.equals("admin_show_npcs"))
			AdminHelpPage.showHelpPage(activeChar, "npcs.htm");
		else if (command.startsWith("admin_npc_index"))
		{
			StringTokenizer st = new StringTokenizer(command, " ");
			try
			{
				st.nextToken();
				String letter = st.nextToken();
				int from = 0;
				try
				{
					from = Integer.parseInt(st.nextToken());
				}
				catch (NoSuchElementException nsee)
				{
				}
				showNpcs(activeChar, letter, from);
			}
			catch (Exception e)
			{
				AdminHelpPage.showHelpPage(activeChar, "npcs.htm");
			}
		}
		
		else if (command.startsWith("admin_unspawnall"))
		{
			Broadcast.toAllOnlinePlayers(SystemMessage.getSystemMessage(SystemMessageId.NPC_SERVER_NOT_OPERATING));
			RaidBossSpawnManager.getInstance().cleanUp();
			DayNightSpawnManager.getInstance().cleanUp();
			L2World.getInstance().deleteVisibleNpcSpawns();
			GmListTable.broadcastMessageToGMs("NPCs' unspawn is now complete.");
		}
		else if (command.startsWith("admin_spawnday"))
			DayNightSpawnManager.getInstance().spawnDayCreatures();
		else if (command.startsWith("admin_spawnnight"))
			DayNightSpawnManager.getInstance().spawnNightCreatures();
		else if (command.startsWith("admin_respawnall") || command.startsWith("admin_spawn_reload"))
		{
			// make sure all spawns are deleted
			RaidBossSpawnManager.getInstance().cleanUp();
			DayNightSpawnManager.getInstance().cleanUp();
			L2World.getInstance().deleteVisibleNpcSpawns();
			// now respawn all
			NpcTable.getInstance().reloadAllNpc();
			SpawnTable.getInstance().reloadAll();
			RaidBossSpawnManager.getInstance().reloadBosses();
			SevenSigns.getInstance().spawnSevenSignsNPC();
			GmListTable.broadcastMessageToGMs("NPCs' respawn is now complete.");
		}
		else if (command.startsWith("admin_spawnfence"))
		{
			StringTokenizer st = new StringTokenizer(command, " ");
			try
			{
				st.nextToken();
				int type = Integer.parseInt(st.nextToken());
				int width = Integer.parseInt(st.nextToken());
				int length = Integer.parseInt(st.nextToken());
				int height = 1;
				if (st.hasMoreTokens())
					height = Math.min(Integer.parseInt(st.nextToken()), 3);
				for (int i = 0; i < height; i++)
				{
					L2FenceInstance fence = new L2FenceInstance(IdFactory.getInstance().getNextId(), type, width, length, activeChar.getX(), activeChar.getY());
					fence.spawnMe(activeChar.getX(), activeChar.getY(), activeChar.getZ());
					FenceTable.addFence(fence);
				}
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Usage: //spawnfence <type> <width> <length> [<height>]");
			}
		}
		else if (command.startsWith("admin_deletefence"))
		{
			StringTokenizer st = new StringTokenizer(command, " ");
			st.nextToken();
			try
			{
				L2Object fence = null;
				if (activeChar.getTarget() instanceof L2FenceInstance)
					fence = activeChar.getTarget();
				else if (st.hasMoreTokens())
				{
					L2Object object = L2World.getInstance().findObject(Integer.parseInt(st.nextToken()));
					if (object instanceof L2FenceInstance)
						fence = object;
				}
				if (fence != null)
				{
					L2WorldRegion region = fence.getWorldRegion();
					fence.decayMe();
					if (region != null)
						region.removeVisibleObject(fence);
					fence.getKnownList().removeAllKnownObjects();
					L2World.getInstance().removeObject(fence);
					activeChar.sendMessage("Deleted fence " + fence.getObjectId());
					if (fence instanceof L2FenceInstance)
						FenceTable.removeFence((L2FenceInstance) fence);
					if (st.hasMoreTokens())
						listFences(activeChar);
				}
				else
					throw new RuntimeException();
			}
			catch (Exception e)
			{
				activeChar.sendMessage("No fence targeted with shift+click or //deletefence <fence_objectId>");
			}
		}
		else if (command.startsWith("admin_listfence"))
			listFences(activeChar);
		else if (command.startsWith("admin_spawn"))
		{
			StringTokenizer st = new StringTokenizer(command, " ");
			try
			{
				String cmd = st.nextToken();
				String id = st.nextToken();
				int respawnTime = st.hasMoreTokens() ? Integer.parseInt(st.nextToken()) : 0;
				
				if (cmd.equalsIgnoreCase("admin_spawn_once"))
					spawn(activeChar, id, respawnTime, false);
				else
					spawn(activeChar, id, respawnTime, true);
			}
			catch (Exception e)
			{
				AdminHelpPage.showHelpPage(activeChar, "spawns.htm");
			}
		}
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
	private static void spawn(L2PcInstance activeChar, String monsterId, int respawnTime, boolean permanent)
	{
		L2Object target = activeChar.getTarget();
		if (target == null)
			target = activeChar;
		
		NpcTemplate template;
		
		if (monsterId.matches("[0-9]*")) // First parameter was an ID number
			template = NpcTable.getInstance().getTemplate(Integer.parseInt(monsterId));
		else
		// First parameter wasn't just numbers, so go by name not ID
		{
			monsterId = monsterId.replace('_', ' ');
			template = NpcTable.getInstance().getTemplateByName(monsterId);
		}
		
		try
		{
			L2Spawn spawn = new L2Spawn(template);
			spawn.setLocx(target.getX());
			spawn.setLocy(target.getY());
			spawn.setLocz(target.getZ());
			spawn.setHeading(activeChar.getHeading());
			spawn.setRespawnDelay(respawnTime);
			
			if (RaidBossSpawnManager.getInstance().getValidTemplate(spawn.getNpcId()) != null)
			{
				if (RaidBossSpawnManager.getInstance().isDefined(spawn.getNpcId()))
				{
					activeChar.sendMessage("You cannot spawn another instance of " + template.getName() + ".");
					return;
				}
				
				spawn.setRespawnMinDelay(43200);
				spawn.setRespawnMaxDelay(129600);
				RaidBossSpawnManager.getInstance().addNewSpawn(spawn, 0, 0, 0, permanent);
				if (Config.ANNOUNCE_TO_ALL_SPAWN_RB)
				{
					Announcements.announceToAll("Raid boss " + spawn.getTemplate().getName() + " has been spawned.");
				}
			}
			else
			{
				SpawnTable.getInstance().addNewSpawn(spawn, permanent);
				spawn.init();
			}
			
			if (!permanent)
				spawn.stopRespawn();
			
			activeChar.sendMessage("Spawned " + template.getName() + ".");
			
		}
		catch (Exception e)
		{
			activeChar.sendPacket(SystemMessageId.APPLICANT_INFORMATION_INCORRECT);
		}
	}
	
	private static void showMonsters(L2PcInstance activeChar, int level, int from)
	{
		final List<NpcTemplate> mobs = NpcTable.getInstance().getAllMonstersOfLevel(level);
		final int mobsCount = mobs.size();
		final StringBuilder tb = StringUtil.startAppend(500 + mobsCount * 80, "<html><title>Spawn Monster:</title><body><p> Level : ", Integer.toString(level), "<br>Total Npc's : ", Integer.toString(mobsCount), "<br>");
		
		// Loop
		int i = from;
		for (int j = 0; i < mobsCount && j < 50; i++, j++)
			StringUtil.append(tb, "<a action=\"bypass -h admin_spawn ", Integer.toString(mobs.get(i).getNpcId()), "\">", mobs.get(i).getName(), "</a><br1>");
		
		if (i == mobsCount)
			tb.append("<br><center><button value=\"Back\" action=\"bypass -h admin_show_spawns\" width=40 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></center></body></html>");
		else
			StringUtil.append(tb, "<br><center><button value=\"Next\" action=\"bypass -h admin_spawn_index ", Integer.toString(level), " ", Integer.toString(i), "\" width=40 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"><button value=\"Back\" action=\"bypass -h admin_show_spawns\" width=40 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></center></body></html>");
		
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		html.setHtml(tb.toString());
		activeChar.sendPacket(html);
	}
	
	private static void showNpcs(L2PcInstance activeChar, String starting, int from)
	{
		final List<NpcTemplate> mobs = NpcTable.getInstance().getAllNpcStartingWith(starting);
		final int mobsCount = mobs.size();
		final StringBuilder tb = StringUtil.startAppend(500 + mobsCount * 80, "<html><title>Spawn Monster:</title><body><p> There are ", Integer.toString(mobsCount), " Npcs whose name starts with ", starting, ":<br>");
		
		// Loop
		int i = from;
		for (int j = 0; i < mobsCount && j < 50; i++, j++)
			StringUtil.append(tb, "<a action=\"bypass -h admin_spawn ", Integer.toString(mobs.get(i).getNpcId()), "\">", mobs.get(i).getName(), "</a><br1>");
		
		if (i == mobsCount)
			tb.append("<br><center><button value=\"Back\" action=\"bypass -h admin_show_npcs\" width=40 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></center></body></html>");
		else
			StringUtil.append(tb, "<br><center><button value=\"Next\" action=\"bypass -h admin_npc_index ", starting, " ", Integer.toString(i), "\" width=40 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"><button value=\"Back\" action=\"bypass -h admin_show_npcs\" width=40 height=15 back=\"sek.cbui94\" fore=\"sek.cbui92\"></center></body></html>");
		
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		html.setHtml(tb.toString());
		activeChar.sendPacket(html);
	}
	
	private static void listFences(L2PcInstance activeChar)
	{
		StringBuilder tb = new StringBuilder();
		
		tb.append("<html><body>Total Fences: " + FenceTable.getAllFences().size() + "<br><br>");
		for (L2FenceInstance fence : FenceTable.getAllFences())
			tb.append("<a action=\"bypass -h admin_deletefence " + fence.getObjectId() + " 1\">Fence: " + fence.getObjectId() + " [" + fence.getX() + " " + fence.getY() + " " + fence.getZ() + "]</a><br>");
		tb.append("</body></html>");
		
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		html.setHtml(tb.toString());
		activeChar.sendPacket(html);
	}
}