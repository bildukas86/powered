package net.sf.l2j.gameserver.model.quest;

/**
 * @author Hasha
 *
 */
public enum QuestEventType
{
	ON_FIRST_TALK(false), // control the first dialog shown by NPCs when they are clicked (some quests must override the default npc action)
	QUEST_START(true), // onTalk action from start npcs
	ON_TALK(true), // onTalk action from npcs participating in a quest
	ON_ATTACK(true), // onAttack action triggered when a mob gets attacked by someone
	ON_ATTACK_ACT(true), // onAttackAct event is triggered when a mob attacks someone
	ON_KILL(true), // onKill action triggered when a mob gets killed.
	ON_SPAWN(true), // onSpawn action triggered when an NPC is spawned or respawned.
	ON_SKILL_SEE(true), // NPC or Mob saw a person casting a skill (regardless what the target is).
	ON_FACTION_CALL(true), // NPC or Mob saw a person casting a skill (regardless what the target is).
	ON_AGGRO_RANGE_ENTER(true), // a person came within the Npc/Mob's range
	ON_SPELL_FINISHED(true), // on spell finished action when npc finish casting skill
	ON_ENTER_ZONE(true), // on zone enter
	ON_EXIT_ZONE(true); // on zone exit
	
	// control whether this event type is allowed for the same npc template in multiple quests
	// or if the npc must be registered in at most one quest for the specified event
	private boolean _allowMultipleRegistration;
	
	QuestEventType(boolean allowMultipleRegistration)
	{
		_allowMultipleRegistration = allowMultipleRegistration;
	}
	
	public boolean isMultipleRegistrationAllowed()
	{
		return _allowMultipleRegistration;
	}
}