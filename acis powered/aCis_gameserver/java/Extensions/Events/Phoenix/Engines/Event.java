package Extensions.Events.Phoenix.Engines;

import Extensions.Events.Phoenix.EventConfig;
import Extensions.Events.Phoenix.EventManager;
import Extensions.Events.Phoenix.Events.RaidBoss;

import java.util.ArrayList;
import java.util.Set;

import javolution.util.FastList;
import javolution.util.FastMap;

import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.datatables.NpcTable;
import net.sf.l2j.gameserver.datatables.SpawnTable;
import net.sf.l2j.gameserver.model.L2Effect;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.L2Party;
import net.sf.l2j.gameserver.model.L2Party.MessageType;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.L2Spawn;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.model.item.type.EtcItemType;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;
import net.sf.l2j.gameserver.network.serverpackets.ExShowScreenMessage;
import net.sf.l2j.gameserver.skills.AbnormalEffect;
import net.sf.l2j.gameserver.templates.skills.L2SkillType;
import net.sf.l2j.util.Rnd;

public abstract class Event
{
	protected static final int[] ITEMS =
	{
		6707,
		6709,
		6708,
		6710,
		6704,
		6701,
		6702,
		6703,
		6706,
		6705,
		6713,
		6714,
		6712,
		6711,
		6697,
		6688,
		6696,
		6691,
		7579,
		6695,
		6694,
		6689,
		6693,
		6690
	};
	public int eventId;
	protected EventConfig config = EventConfig.getInstance();
	public FastMap<Integer, EventTeam> teams;
	protected ThreadPoolManager tpm;
	protected ResurrectorTask resurrectorTask;
	public Clock clock;
	protected String scorebartext;
	protected int time;
	public int winnerTeam;
	protected int loserTeam;
	private FastMap<L2PcInstance, ArrayList<L2Skill>> summons;
	
	// TEAM-STATUS-SCORE
	public FastMap<L2PcInstance, int[]> players;
	
	protected class Clock implements Runnable
	{
		private int totalTime;
		
		public String getTime()
		{
			String mins = "" + time / 60;
			String secs = (time % 60 < 10 ? "0" + time % 60 : "" + time % 60);
			return mins + ":" + secs + "";
		}
		
		@Override
		public void run()
		{
			clockTick();
			
			if (time < totalTime)
			{
				scorebartext = getScorebar();
				if (scorebartext != "")
				{
					for (L2PcInstance player : getPlayerList())
						player.sendPacket(new ExShowScreenMessage(1, -1, 3, false, 1, 0, 0, false, 2000, false, scorebartext));
				}
			}
			
			if (time <= 0)
				schedule(1);
			else
			{
				time--;
				tpm.scheduleGeneral(clock, 1000);
			}
		}
		
		public void setTime(int t)
		{
			time = t;
		}
		
		public void startClock(int mt)
		{
			totalTime = mt - 2;
			time = mt;
			tpm.scheduleGeneral(clock, 1);
		}
	}
	
	protected class ResurrectorTask implements Runnable
	{
		private L2PcInstance player;
		
		public ResurrectorTask(L2PcInstance p)
		{
			player = p;
			ThreadPoolManager.getInstance().scheduleGeneral(this, 7000);
		}
		
		@Override
		public void run()
		{
			if (EventManager.getInstance().isRegistered(player))
			{
				player.doRevive();
				
				player.setCurrentCp(player.getMaxCp());
				player.setCurrentHp(player.getMaxHp());
				player.setCurrentMp(player.getMaxMp());
				teleportToTeamPos(player);
			}
		}
	}
	
	public Event()
	{
		teams = new FastMap<>();
		clock = new Clock();
		tpm = ThreadPoolManager.getInstance();
		players = new FastMap<>();
		summons = new FastMap<>();
		time = 0;
	}
	
	protected void clockTick()
	{
		
	}
	
	public void dropBomb(L2PcInstance player)
	{
		
	}
	
	public void onHit(L2PcInstance actor, L2PcInstance target)
	{
		
	}
	
	public void useCapture(L2PcInstance player, L2Npc base)
	{
		
	}
	
	protected void addToResurrector(L2PcInstance player)
	{
		new ResurrectorTask(player);
	}
	
	protected void announce(Set<L2PcInstance> list, String text)
	{
		for (L2PcInstance player : list)
			player.sendPacket(new CreatureSay(0, 18, "", "[Event] " + text));
	}
	
	public boolean canAttack(L2PcInstance player, L2Object target)
	{
		return true;
	}
	
	public int countOfPositiveStatus()
	{
		int count = 0;
		for (L2PcInstance player : getPlayerList())
			if (getStatus(player) >= 0)
				count++;
		
		return count;
	}
	
	protected void createNewTeam(int id, String name, int[] color, int[] startPos)
	{
		teams.put(id, new EventTeam(id, name, color, startPos));
	}
	
	public void createPartyOfTeam(int teamId)
	{
		int count = 0;
		L2Party party = null;
		
		FastList<L2PcInstance> list = new FastList<>();
		
		for (L2PcInstance p : players.keySet())
			if (getTeam(p) == teamId)
				list.add(p);
		
		for (L2PcInstance player : list)
		{
			if (count % 9 == 0 && list.size() - count != 1)
				party = new L2Party(player, 1);
			if (count % 9 < 9)
				player.joinParty(party);
			count++;
		}
	}
	
	public void divideIntoTeams(int number)
	{
		int i = 0;
		
		while (EventManager.getInstance().players.size() != 0)
		{
			i++;
			L2PcInstance player = EventManager.getInstance().players.get(Rnd.get(EventManager.getInstance().players.size()));
			
			// skip healers
			if (player.getClassId().getId() == 16 || player.getClassId().getId() == 97)
				continue;
			
			players.put(player, new int[]
			{
				i,
				0,
				0
			});
			EventManager.getInstance().players.remove(player);
			if (i == number)
				i = 0;
		}
		
		i = getPlayersOfTeam(1).size() > getPlayersOfTeam(2).size() ? 1 : 0;
		
		// healers here
		while (EventManager.getInstance().players.size() != 0)
		{
			i++;
			L2PcInstance player = EventManager.getInstance().players.get(Rnd.get(EventManager.getInstance().players.size()));
			
			players.put(player, new int[]
			{
				i,
				0,
				0
			});
			EventManager.getInstance().players.remove(player);
			if (i == number)
				i = 0;
		}
	}
	
	public void forceSitAll()
	{
		for (L2PcInstance player : players.keySet())
		{
			player.abortAttack();
			player.abortCast();
			player.setIsParalyzed(true);
			player.setIsInvul(true);
			player.startAbnormalEffect(AbnormalEffect.HOLD_2);
		}
	}
	
	public void forceStandAll()
	{
		for (L2PcInstance player : players.keySet())
		{
			player.stopAbnormalEffect(AbnormalEffect.HOLD_2);
			player.setIsInvul(false);
			player.setIsParalyzed(false);
		}
	}
	
	public void InvisAll()
	{
		for (L2PcInstance player : players.keySet())
		{
			player.abortAttack();
			player.abortCast();
			player.getAppearance().setInvisible();
		}
	}
	
	public void unInvisAll()
	{
		for (L2PcInstance player : players.keySet())
		{
			player.getAppearance().setVisible();
			player.broadcastCharInfo();
		}
	}
	
	public boolean getBoolean(String propName)
	{
		return config.getBoolean(eventId, propName);
	}
	
	public int[] getColor(String owner)
	{
		return config.getColor(eventId, owner);
	}
	
	public int getInt(String propName)
	{
		return config.getInt(eventId, propName);
	}
	
	public Set<L2PcInstance> getPlayerList()
	{
		return players.keySet();
	}
	
	public FastList<L2PcInstance> getPlayersOfTeam(int team)
	{
		FastList<L2PcInstance> list = new FastList<>();
		
		for (L2PcInstance player : getPlayerList())
			if (getTeam(player) == team)
				list.add(player);
		
		return list;
	}
	
	protected EventTeam getPlayersTeam(L2PcInstance player)
	{
		return teams.get(players.get(player)[0]);
	}
	
	public FastList<L2PcInstance> getPlayersWithStatus(int status)
	{
		FastList<L2PcInstance> list = new FastList<>();
		
		for (L2PcInstance player : getPlayerList())
			if (getStatus(player) == status)
				list.add(player);
		
		return list;
	}
	
	public L2PcInstance getPlayerWithMaxScore()
	{
		L2PcInstance max = players.head().getNext().getKey();
		
		for (L2PcInstance player : players.keySet())
			if (players.get(player)[2] > players.get(max)[2])
				max = player;
		
		return max;
	}
	
	public void unequip()
	{
		for (L2PcInstance player : players.keySet())
		{
			player.getInventory().unEquipItemInSlot(7);
			player.getInventory().unEquipItemInSlot(8);
		}
	}
	
	public int[] getPosition(String owner, int num)
	{
		return config.getPosition(eventId, owner, num);
	}
	
	public L2PcInstance getRandomPlayer()
	{
		FastList<L2PcInstance> temp = new FastList<>();
		for (L2PcInstance player : players.keySet())
			temp.add(player);
		
		return temp.get(Rnd.get(temp.size()));
	}
	
	protected L2PcInstance getRandomPlayerFromTeam(int team)
	{
		FastList<L2PcInstance> temp = new FastList<>();
		for (L2PcInstance player : players.keySet())
			if (getTeam(player) == team)
				temp.add(player);
		
		return temp.get(Rnd.get(temp.size()));
	}
	
	protected FastList<L2PcInstance> getPlayersFromTeamWithStatus(int team, int status)
	{
		FastList<L2PcInstance> players = getPlayersWithStatus(status);
		FastList<L2PcInstance> temp = new FastList<>();
		
		for (L2PcInstance player : players)
			if (getTeam(player) == team)
				temp.add(player);
		
		return temp;
	}
	
	protected L2PcInstance getRandomPlayerFromTeamWithStatus(int team, int status)
	{
		FastList<L2PcInstance> temp = getPlayersFromTeamWithStatus(team, status);
		return temp.get(Rnd.get(temp.size()));
	}
	
	public FastList<Integer> getRestriction(String type)
	{
		return config.getRestriction(eventId, type);
	}
	
	public int getScore(L2PcInstance player)
	{
		return players.get(player)[2];
	}
	
	protected int getStatus(L2PcInstance player)
	{
		return players.get(player)[1];
	}
	
	public String getString(String propName)
	{
		return config.getString(eventId, propName);
	}
	
	public int getTeam(L2PcInstance player)
	{
		return players.get(player)[0];
	}
	
	public int getWinnerTeam()
	{
		FastList<EventTeam> t = new FastList<>();
		
		for (EventTeam team : teams.values())
		{
			if (t.size() == 0)
			{
				t.add(team);
				continue;
			}
			
			if (team.getScore() > t.getFirst().getScore())
			{
				t.clear();
				t.add(team);
				continue;
			}
			
			if (team.getScore() == t.getFirst().getScore())
				t.add(team);
		}
		
		if (t.size() > 1)
			return 0;
		
		return t.getFirst().getId();
	}
	
	public void giveReward(FastList<L2PcInstance> players, int id, int ammount)
	{
		for (L2PcInstance player : players)
		{
			if (player == null)
				continue;
			
			player.addItem("Event", id, ammount, player, true);
			EventStats.getInstance().tempTable.get(player.getObjectId())[0] = 1;
		}
	}
	
	public void giveReward(L2PcInstance player, int id, int ammount)
	{
		EventStats.getInstance().tempTable.get(player.getObjectId())[0] = 1;
		player.addItem("Event", id, ammount, player, true);
	}
	
	public void increasePlayersScore(L2PcInstance player)
	{
		int old = getScore(player);
		setScore(player, old + 1);
		EventStats.getInstance().tempTable.get(player.getObjectId())[3] = EventStats.getInstance().tempTable.get(player.getObjectId())[3] + 1;
	}
	
	protected void msgToAll(String text)
	{
		for (L2PcInstance player : players.keySet())
			player.sendMessage(text);
	}
	
	public void onDie(L2PcInstance victim, L2Character killer)
	{
		EventStats.getInstance().tempTable.get(victim.getObjectId())[2] = EventStats.getInstance().tempTable.get(victim.getObjectId())[2] + 1;
	}
	
	public void onKill(L2Character victim, L2PcInstance killer)
	{
		EventStats.getInstance().tempTable.get(killer.getObjectId())[1] = EventStats.getInstance().tempTable.get(killer.getObjectId())[1] + 1;
	}
	
	public void onLogout(L2PcInstance player)
	{
		if (players.containsKey(player))
			removePlayer(player);
		
		player.setXYZ(EventManager.getInstance().positions.get(player)[0], EventManager.getInstance().positions.get(player)[1], EventManager.getInstance().positions.get(player)[2]);
		player.setTitle(EventManager.getInstance().titles.get(player));
		
		if (teams.size() == 1)
		{
			if (getPlayerList().size() == 1)
				endEvent();
		}
		else
		{
			int t = players.head().getNext().getValue()[0];
			for (L2PcInstance p : getPlayerList())
				if (getTeam(p) != t)
					return;
			
			endEvent();
		}
	}
	
	public boolean onSay(int type, L2PcInstance player, String text)
	{
		return true;
	}
	
	public boolean onTalkNpc(L2Npc npc, L2PcInstance player)
	{
		return false;
	}
	
	public boolean onUseItem(L2PcInstance player, ItemInstance item)
	{
		if (EventManager.getInstance().getRestriction("item").contains(item.getItemId()) || getRestriction("item").contains(item.getItemId()))
			return false;
		
		if (item.getItemType() == EtcItemType.POTION && !getBoolean("allowPotions"))
			return false;
		
		if (item.getItemType() == EtcItemType.SCROLL)
			return false;
		
		if (item.getItemType() == EtcItemType.PET_COLLAR)
			return false;
		
		return true;
	}
	
	public boolean onUseMagic(L2Skill skill)
	{
		if (EventManager.getInstance().getRestriction("skill").contains(skill.getId()) || getRestriction("skill").contains(skill.getId()))
			return false;
		
		if (skill.getSkillType() == L2SkillType.RESURRECT && !(this instanceof RaidBoss))
			return false;
		
		if (skill.getSkillType() == L2SkillType.SUMMON_FRIEND)
			return false;
		
		if (skill.getSkillType() == L2SkillType.RECALL)
			return false;
		
		if (skill.getSkillType() == L2SkillType.FAKE_DEATH)
			return false;
		
		return true;
	}
	
	protected void prepare(L2PcInstance player)
	{
		if (player.isDead())
			player.doRevive();
		
		if (player.isCastingNow())
			player.abortCast();
		
		player.getAppearance().setVisible();
		
		if (player.hasPet())
			player.getPet().unSummon(player);
		
		if (player.isMounted())
			player.dismount();
		
		if (getBoolean("removeBuffs"))
		{
			player.stopAllEffects();
			if (player.hasServitor())
				player.getPet().unSummon(player);
		}
		else
		{
			for (L2Effect e : player.getAllEffects())
				if (e.getStackType().equals("hero_buff"))
					e.exit();
			
			if (player.hasServitor())
			{
				ArrayList<L2Skill> summonBuffs = new ArrayList<>();
				
				for (L2Effect e : player.getPet().getAllEffects())
				{
					if (e.getStackType().equals("hero_buff"))
						e.exit();
					else
						summonBuffs.add(e.getSkill());
				}
				
				summons.put(player, summonBuffs);
			}
		}
		
		ItemInstance wpn = player.getActiveWeaponInstance();
		if (wpn != null && wpn.isHeroItem())
			player.useEquippableItem(wpn, false);
		
		if (player.getParty() != null)
		{
			L2Party party = player.getParty();
			party.removePartyMember(player, MessageType.Left);
		}
		
		int[] nameColor = getPlayersTeam(player).getTeamColor();
		player.getAppearance().setNameColor(nameColor[0], nameColor[1], nameColor[2]);
		player.setTitle("<- 0 ->");
		
		if (EventManager.getInstance().getBoolean("eventBufferEnabled"))
			EventBuffer.getInstance().buffPlayer(player);
		
		player.setCurrentHpMp(player.getMaxHp(), player.getMaxMp());
		player.setCurrentCp(player.getMaxCp());
		
		player.broadcastUserInfo();
	}
	
	public ArrayList<L2Skill> getSummonBuffs(L2PcInstance player)
	{
		return summons.get(player);
	}
	
	public void preparePlayers()
	{
		for (L2PcInstance player : players.keySet())
			prepare(player);
	}
	
	protected void removePlayer(L2PcInstance player)
	{
		players.remove(player);
	}
	
	public void reset()
	{
		players.clear();
		summons.clear();
		tpm.purge();
		winnerTeam = 0;
		
		for (EventTeam team : teams.values())
			team.setScore(0);
	}
	
	protected void selectPlayers(int teamId, int playerCount)
	{
		for (int i = 0; i < playerCount; i++)
		{
			L2PcInstance player = EventManager.getInstance().players.get(Rnd.get(EventManager.getInstance().players.size()));
			players.put(player, new int[]
			{
				teamId,
				0,
				0
			});
			EventManager.getInstance().players.remove(player);
		}
	}
	
	protected void setScore(L2PcInstance player, int score)
	{
		players.get(player)[2] = score;
		player.setTitle("<- " + score + " ->");
		player.broadcastUserInfo();
	}
	
	public void setStatus(L2PcInstance player, int status)
	{
		if (players.containsKey(player))
			players.get(player)[1] = status;
	}
	
	protected void setTeam(L2PcInstance player, int team)
	{
		players.get(player)[0] = team;
	}
	
	public L2Spawn spawnNPC(int xPos, int yPos, int zPos, int npcId)
	{
		final NpcTemplate template = NpcTable.getInstance().getTemplate(npcId);
		
		try
		{
			final L2Spawn spawn = new L2Spawn(template);
			spawn.setLocx(xPos);
			spawn.setLocy(yPos);
			spawn.setLocz(zPos);
			spawn.setHeading(0);
			spawn.setRespawnDelay(1);
			SpawnTable.getInstance().addNewSpawn(spawn, false);
			spawn.init();
			return spawn;
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	protected void teleportPlayer(L2PcInstance player, int[] coordinates)
	{
		player.teleToLocation(coordinates[0] + (Rnd.get(coordinates[3] * 2) - coordinates[3]), coordinates[1] + (Rnd.get(coordinates[3] * 2) - coordinates[3]), coordinates[2], 0);
	}
	
	public void teleportToTeamPos()
	{
		for (L2PcInstance player : players.keySet())
			teleportToTeamPos(player);
	}
	
	protected void teleportToTeamPos(L2PcInstance player)
	{
		int[] pos = getPosition(teams.get(getTeam(player)).getName(), 0);
		teleportPlayer(player, pos);
	}
	
	protected void unspawnNPC(L2Spawn npcSpawn)
	{
		if (npcSpawn == null)
			return;
		
		npcSpawn.getLastSpawn().deleteMe();
		npcSpawn.stopRespawn();
		SpawnTable.getInstance().deleteSpawn(npcSpawn, true);
	}
	
	public int numberOfTeams()
	{
		return teams.size();
	}
	
	public void sendMsg()
	{
		for (L2PcInstance player : getPlayerList())
			player.sendPacket(new ExShowScreenMessage(1, -1, 2, false, 0, 0, 0, false, 3000, false, getStartingMsg()));
	}
	
	public abstract void endEvent();
	protected abstract String getStartingMsg();
	protected abstract String getScorebar();
	public abstract void start();
	public abstract void showHtml(L2PcInstance player, int obj);
	protected abstract void schedule(int time);
}