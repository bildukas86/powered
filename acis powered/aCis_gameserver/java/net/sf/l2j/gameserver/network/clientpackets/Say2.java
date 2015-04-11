package net.sf.l2j.gameserver.network.clientpackets;

import Extensions.Events.RandomFight;
import Extensions.Events.Phoenix.EventManager;
import Extensions.Vip.VIPEngine;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.handler.ChatHandler;
import net.sf.l2j.gameserver.handler.IChatHandler;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;
import net.sf.l2j.gameserver.network.serverpackets.SocialAction;
import net.sf.l2j.gameserver.util.IllegalPlayerAction;
import net.sf.l2j.gameserver.util.Util;

public final class Say2 extends L2GameClientPacket
{
	private static boolean _chatDisabled = false;
	private static Logger _logChat = Logger.getLogger("chat");
	private static String[] anticfg =
	{
		"///cfg",
		"//cfg",
		"/cfg",
	};
	
	public final static int ALL = 0;
	public final static int SHOUT = 1; // !
	public final static int TELL = 2;
	public final static int PARTY = 3; // #
	public final static int CLAN = 4; // @
	public final static int GM = 5;
	public final static int PETITION_PLAYER = 6;
	public final static int PETITION_GM = 7;
	public final static int TRADE = 8; // +
	public final static int ALLIANCE = 9; // $
	public final static int ANNOUNCEMENT = 10;
	public final static int BOAT = 11;
	public final static int L2FRIEND = 12;
	public final static int MSNCHAT = 13;
	public final static int PARTYMATCH_ROOM = 14;
	public final static int PARTYROOM_COMMANDER = 15; // (Yellow)
	public final static int PARTYROOM_ALL = 16; // (Red)
	public final static int HERO_VOICE = 17;
	public final static int AIO_VIP_VOICE = 18;
	
	private final static String[] CHAT_NAMES =
	{
		"ALL",
		"SHOUT",
		"TELL",
		"PARTY",
		"CLAN",
		"GM",
		"PETITION_PLAYER",
		"PETITION_GM",
		"TRADE",
		"ALLIANCE",
		"ANNOUNCEMENT", // 10
		"BOAT",
		"WILLCRASHCLIENT:)",
		"FAKEALL?",
		"PARTYMATCH_ROOM",
		"PARTYROOM_COMMANDER",
		"PARTYROOM_ALL",
		"HERO_VOICE",
		"AIO_VIP_VOICE"
	};
	
	private static final String[] WALKER_COMMAND_LIST =
	{
		"USESKILL",
		"USEITEM",
		"BUYITEM",
		"SELLITEM",
		"SAVEITEM",
		"LOADITEM",
		"MSG",
		"DELAY",
		"LABEL",
		"JMP",
		"CALL",
		"RETURN",
		"MOVETO",
		"NPCSEL",
		"NPCDLG",
		"DLGSEL",
		"CHARSTATUS",
		"POSOUTRANGE",
		"POSINRANGE",
		"GOHOME",
		"SAY",
		"EXIT",
		"PAUSE",
		"STRINDLG",
		"STRNOTINDLG",
		"CHANGEWAITTYPE",
		"FORCEATTACK",
		"ISMEMBER",
		"REQUESTJOINPARTY",
		"REQUESTOUTPARTY",
		"QUITPARTY",
		"MEMBERSTATUS",
		"CHARBUFFS",
		"ITEMCOUNT",
		"FOLLOWTELEPORT"
	};
	
	private String _text;
	private int _type;
	private String _target;
	
	@Override
	protected void readImpl()
	{
		_text = readS();
		_type = readD();
		_target = (_type == TELL) ? readS() : null;
	}
	
	@Override
	protected void runImpl()
	{
		L2PcInstance activeChar = getClient().getActiveChar();
		if (activeChar == null)
			return;
		
		if (isChatDisabled() && !activeChar.isGM())
		{
			activeChar.sendPacket(SystemMessageId.GM_NOTICE_CHAT_DISABLED);
			return;
		}
		
		if (_type < 0 || _type >= CHAT_NAMES.length)
		{
			_log.warning("Say2: Invalid type: " + _type + " Player : " + activeChar.getName() + " text: " + _text);
			activeChar.sendPacket(ActionFailed.STATIC_PACKET);
			activeChar.logout();
			return;
		}
		
		if ((activeChar.isDead()) && (Config.DISABLE_DEAD_CHAT))
		{
			activeChar.sendMessage("You cant talk while you are dead.");
			return;
		}
		
		if ((activeChar.isInOlympiadMode()) && (Config.DISABLE_OLYMPIAD_CHAT))
		{
			activeChar.sendMessage("You cant talk in olympiad.");
			return;
		}
		
		if (activeChar.isSubmitingPin())
		{
			activeChar.sendMessage("Unable to do any action while PIN is not submitted");
			return;
		}
		
		if (_text.isEmpty())
		{
			_log.warning(activeChar.getName() + ": sending empty text. Possible packet hack.");
			activeChar.sendPacket(ActionFailed.STATIC_PACKET);
			activeChar.logout();
			return;
		}
		
		if (Config.ENABLE_SAY_SOCIAL_ACTIONS && !activeChar.isAlikeDead() && !activeChar.isDead())
		{
			if ((_text.equalsIgnoreCase("hello") || _text.equalsIgnoreCase("hey") || _text.equalsIgnoreCase("aloha") || _text.equalsIgnoreCase("alo") || _text.equalsIgnoreCase("ciao") || _text.equalsIgnoreCase("hi")) && (!activeChar.isRunning() || !activeChar.isAttackingNow() || !activeChar.isCastingNow()))
				activeChar.broadcastPacket(new SocialAction(activeChar, 2));
			
			if ((_text.equalsIgnoreCase("lol") || _text.equalsIgnoreCase("haha") || _text.equalsIgnoreCase("xaxa") || _text.equalsIgnoreCase("ghgh") || _text.equalsIgnoreCase("jaja")) && (!activeChar.isRunning() || !activeChar.isAttackingNow() || !activeChar.isCastingNow()))
				activeChar.broadcastPacket(new SocialAction(activeChar, 10));
			
			if ((_text.equalsIgnoreCase("yes") || _text.equalsIgnoreCase("si") || _text.equalsIgnoreCase("yep")) && (!activeChar.isRunning() || !activeChar.isAttackingNow() || !activeChar.isCastingNow()))
				activeChar.broadcastPacket(new SocialAction(activeChar, 6));
			
			if ((_text.equalsIgnoreCase("no") || _text.equalsIgnoreCase("nop") || _text.equalsIgnoreCase("nope")) && (!activeChar.isRunning() || !activeChar.isAttackingNow() || !activeChar.isCastingNow()))
				activeChar.broadcastPacket(new SocialAction(activeChar, 5));
			
		}
		
		if (_text.length() >= 100)
			return;
		
		if (Config.ALLOW_AIO_AND_VIP_CHAT && (activeChar.isAio() || VIPEngine.getInstance().isVip(activeChar)))
		{
			if (_text.startsWith(">"))
				for (L2PcInstance p : L2World.getInstance().getAllPlayers().values())
				{
					p.sendPacket(new CreatureSay(0, Say2.AIO_VIP_VOICE, activeChar.getName(), _text));
					return;
				}
			else
			{
				activeChar.sendMessage("This chat can be used only by VIP & AiO characters of the server.");
			}
		}
		
		if (Config.L2WALKER_PROTECTION && _type == TELL && checkBot(_text))
		{
			Util.handleIllegalPlayerAction(activeChar, "Client Emulator Detect: " + activeChar.getName() + " is using L2Walker.", Config.DEFAULT_PUNISH);
			return;
		}
		
		if (!activeChar.isGM() && _type == ANNOUNCEMENT)
		{
			Util.handleIllegalPlayerAction(activeChar, activeChar.getName() + " tried to announce without GM statut.", IllegalPlayerAction.PUNISH_BROADCAST);
			_log.warning(activeChar.getName() + " tried to use announcements without GM statut.");
			return;
		}
		
		if (activeChar.isChatBanned() || (activeChar.isInJail() && !activeChar.isGM()))
		{
			activeChar.sendPacket(SystemMessageId.CHATTING_PROHIBITED);
			return;
		}
		
		checkRandomFight(this._text, activeChar);
		if ((this._text.equalsIgnoreCase(".join_rf")) || (this._text.equalsIgnoreCase(".leave_rf")))
		{
			return;
		}
		
		if (_type == TELL || _type == SHOUT || _type == TRADE || _type == HERO_VOICE || _type == PARTY)
		{
			for (int k = 0; k < anticfg.length; k++)
			{
				if (_text.equalsIgnoreCase(anticfg[k]))
				{
					activeChar.sendMessage("You can't use word: " + anticfg[k]);
					return;
				}
			}
		}
		
		if (_type == PETITION_PLAYER && activeChar.isGM())
			_type = PETITION_GM;
		
		if (Config.LOG_CHAT)
		{
			LogRecord record = new LogRecord(Level.INFO, _text);
			record.setLoggerName("chat");
			
			if (_type == TELL)
				record.setParameters(new Object[]
				{
					CHAT_NAMES[_type],
					"[" + activeChar.getName() + " to " + _target + "]"
				});
			else
				record.setParameters(new Object[]
				{
					CHAT_NAMES[_type],
					"[" + activeChar.getName() + "]"
				});
			
			_logChat.log(record);
		}
		
		_text = _text.replaceAll("\\\\n", "");
		
		if (EventManager.getInstance().isRegistered(activeChar) && !EventManager.getInstance().getCurrentEvent().onSay(_type, activeChar, _text))
		{
			activeChar.sendMessage("You cannot talk right now.");
			return;
		}
		
		IChatHandler handler = ChatHandler.getInstance().getChatHandler(_type);
		if (handler != null)
			handler.handleChat(_type, activeChar, _target, _text);
		else
			_log.warning(activeChar.getName() + " tried to use unregistred chathandler type: " + _type + ".");
	}
	
	private static boolean checkBot(String text)
	{
		for (String botCommand : WALKER_COMMAND_LIST)
		{
			if (text.startsWith(botCommand))
				return true;
		}
		return false;
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
	
	static void checkRandomFight(String text, L2PcInstance player)
	{
		if (text.equalsIgnoreCase(".join_rf"))
		{
			if (RandomFight.players.contains(player))
			{
				player.sendMessage("You have already registed to the event.");
				return;
			}
			if (RandomFight.state == RandomFight.State.INACTIVE)
			{
				return;
			}
			if (RandomFight.state != RandomFight.State.REGISTER)
			{
				player.sendMessage("Event has already started.");
				return;
			}
			RandomFight.players.add(player);
			player.sendMessage("You registed to the event!!");
			return;
		}
		if (text.equalsIgnoreCase(".leave_rf"))
		{
			if (!RandomFight.players.contains(player))
			{
				player.sendMessage("You haven't registed to the event.");
				return;
			}
			if (RandomFight.state == RandomFight.State.INACTIVE)
			{
				return;
			}
			if (RandomFight.state != RandomFight.State.REGISTER)
			{
				player.sendMessage("Event has already started.");
				return;
			}
			RandomFight.players.remove(player);
			player.sendMessage("You unregisted from the  event!!");
		}
	}
	
	@Override
	protected boolean triggersOnActionRequest()
	{
		return false;
	}
}