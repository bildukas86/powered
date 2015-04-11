package net.sf.l2j.gameserver.util;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.network.L2GameClient;

/**
 * Collection of flood protectors for single player.
 */
public final class FloodProtectors
{
	private final FloodProtectorAction _rollDice;
	private final FloodProtectorAction _heroVoice;
	private final FloodProtectorAction _subclass;
	private final FloodProtectorAction _dropItem;
	private final FloodProtectorAction _serverBypass;
	private final FloodProtectorAction _multiSell;
	private final FloodProtectorAction _manufacture;
	private final FloodProtectorAction _manor;
	private final FloodProtectorAction _sendMail;
	private final FloodProtectorAction _characterSelect;
	private final FloodProtectorAction _macro;
	private final FloodProtectorAction _AioVipVoice;
	
	/**
	 * Creates new instance of FloodProtectors.
	 * @param client client for which the collection of flood protectors is being created.
	 */
	public FloodProtectors(final L2GameClient client)
	{
		super();
		_rollDice = new FloodProtectorAction(client, Config.FLOOD_PROTECTOR_ROLL_DICE);
		_heroVoice = new FloodProtectorAction(client, Config.FLOOD_PROTECTOR_HERO_VOICE);
		_subclass = new FloodProtectorAction(client, Config.FLOOD_PROTECTOR_SUBCLASS);
		_dropItem = new FloodProtectorAction(client, Config.FLOOD_PROTECTOR_DROP_ITEM);
		_serverBypass = new FloodProtectorAction(client, Config.FLOOD_PROTECTOR_SERVER_BYPASS);
		_multiSell = new FloodProtectorAction(client, Config.FLOOD_PROTECTOR_MULTISELL);
		_manufacture = new FloodProtectorAction(client, Config.FLOOD_PROTECTOR_MANUFACTURE);
		_manor = new FloodProtectorAction(client, Config.FLOOD_PROTECTOR_MANOR);
		_sendMail = new FloodProtectorAction(client, Config.FLOOD_PROTECTOR_SENDMAIL);
		_characterSelect = new FloodProtectorAction(client, Config.FLOOD_PROTECTOR_CHARACTER_SELECT);
		_macro = new FloodProtectorAction(client, Config.FLOOD_PROTECTOR_MACRO);
		_AioVipVoice = new FloodProtectorAction(client, Config.FLOOD_PROTECTOR_AIO_VIP_VOICE);
	}
	
	public FloodProtectorAction getRollDice()
	{
		return _rollDice;
	}
	
	public FloodProtectorAction getHeroVoice()
	{
		return _heroVoice;
	}
	
	public FloodProtectorAction getSubclass()
	{
		return _subclass;
	}
	
	public FloodProtectorAction getDropItem()
	{
		return _dropItem;
	}
	
	public FloodProtectorAction getServerBypass()
	{
		return _serverBypass;
	}
	
	public FloodProtectorAction getMultiSell()
	{
		return _multiSell;
	}
	
	public FloodProtectorAction getManufacture()
	{
		return _manufacture;
	}
	
	public FloodProtectorAction getManor()
	{
		return _manor;
	}
	
	public FloodProtectorAction getSendMail()
	{
		return _sendMail;
	}
	
	public FloodProtectorAction getCharacterSelect()
	{
		return _characterSelect;
	}
	
	public FloodProtectorAction getMacro()
	{
		return _macro;
	}
	
	public FloodProtectorAction getAioVipVoice()
	{
		return _AioVipVoice;
	}
}