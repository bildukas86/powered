package net.sf.l2j.gameserver.handler;

import Extensions.VoicedCommands.AioOnline;
import Extensions.VoicedCommands.BankingCmd;
import Extensions.VoicedCommands.BossInfo;
import Extensions.VoicedCommands.BuffCommand;
import Extensions.VoicedCommands.CastleManagersVCmd;
import Extensions.VoicedCommands.EventJoin;
import Extensions.VoicedCommands.FarmPvp;
import Extensions.VoicedCommands.GearScoreCmd;
import Extensions.VoicedCommands.GoToCastle;
import Extensions.VoicedCommands.MailCmd;
import Extensions.VoicedCommands.Online;
import Extensions.VoicedCommands.Pin;
import Extensions.VoicedCommands.Res;
import Extensions.VoicedCommands.ShowOfflineShop;
import Extensions.VoicedCommands.StriderRaceCMD;
import Extensions.VoicedCommands.Survey;
import Extensions.VoicedCommands.User;
import Extensions.VoicedCommands.Version;
import Extensions.VoicedCommands.ViewDetails;
import Extensions.VoicedCommands.VoteRewardMe;
import Extensions.VoicedCommands.WhoAmI;
import Extensions.VoicedCommands.xMas;

import java.util.HashMap;
import java.util.Map;

import net.sf.l2j.Config;

public class VoicedCommandHandler
{
	private final Map<Integer, IVoicedCommandHandler> _datatable = new HashMap<>();
	
	public static VoicedCommandHandler getInstance()
	{
		return SingletonHolder._instance;
	}
	
	protected VoicedCommandHandler()
	{
		if (Config.ENABLE_WHO_AM_I_VC)
			registerHandler(new WhoAmI());
		if (Config.ONLINE_PLAYERS)
			registerHandler(new Online());
		if (Config.ENABLE_PIN_SYSTEM)
			registerHandler(new Pin());
		if (Config.BANKING_SYSTEM_ENABLED)
			registerHandler(new BankingCmd());
		if (Config.VOTE_BUFF_ENABLED)
			registerHandler(new VoteRewardMe());
		if (Config.ENABLE_PERSONAL_MENU_SYSTEM)
			registerHandler(new User());
		if (Config.GEARSCORE_ENABLED)
			registerHandler(new GearScoreCmd());
		if (Config.VIEWDETAILS_ENABLED)
			registerHandler(new ViewDetails());
		if (Config.ENABLE_VERSION_COMMAND)
			registerHandler(new Version());
		if (Config.ENABLE_MAIL_SYSTEM)
			registerHandler(new MailCmd());
		if (Config.RES_COMMAND)
			registerHandler(new Res());
		if (Config.REGISTER_SIEGE_BY_COMMAND)
			registerHandler(new CastleManagersVCmd());
		if (Config.ENABLE_BOSSINFO_VC)
			registerHandler(new BossInfo());
		if (Config.GO_TO_CASTLE_COMMAND_ENABLE)
			registerHandler(new GoToCastle());
		if (Config.XMAS_COMMAND_ENABLE)
			registerHandler(new xMas());
		if (Config.SHOW_AIO_ENABLE)
			registerHandler(new AioOnline());
		if (Config.SHOW_OFFLINE_SHOP)
			registerHandler(new ShowOfflineShop());
		if (Config.ENABLE_BUFF_COMMAND)
			registerHandler(new BuffCommand());
		
		registerHandler(new FarmPvp());
		registerHandler(new Survey());
		registerHandler(new EventJoin());
		registerHandler(new StriderRaceCMD());
	}
	
	public void registerHandler(IVoicedCommandHandler handler)
	{
		for (String id : handler.getVoicedCommandList())
			_datatable.put(id.hashCode(), handler);
	}
	
	public IVoicedCommandHandler getVoicedCommandHandler(String voicedCommand)
	{
		String command = voicedCommand;
		if (voicedCommand.indexOf(" ") != -1)
			command = voicedCommand.substring(0, voicedCommand.indexOf(" "));
		return _datatable.get(command.hashCode());
	}
	
	public int size()
	{
		return _datatable.size();
	}
	
	private static class SingletonHolder
	{
		protected static final VoicedCommandHandler _instance = new VoicedCommandHandler();
	}
}