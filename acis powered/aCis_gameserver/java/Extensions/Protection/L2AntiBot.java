package Extensions.Protection;

import java.util.concurrent.ScheduledFuture;
import java.util.logging.Logger;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.clientpackets.Say2;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;
import net.sf.l2j.gameserver.network.serverpackets.ExShowScreenMessage;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.skills.AbnormalEffect;
import net.sf.l2j.util.Rnd;

public class L2AntiBot
{
	public static final Logger _log = Logger.getLogger(L2AntiBot.class.getName());
	private static L2AntiBot _instance;
	public static ScheduledFuture<?> _antiBotTask;
	
	private int _antibot;
	private static String _code = "";
	
	public L2AntiBot()
	{
	}
	
	public static L2AntiBot getInstance()
	{
		if (_instance == null)
			_instance = new L2AntiBot();
		return _instance;
	}
	
	private static final String[] _IMG =
	{
		"Numbers.Keys0",
		"Numbers.Keys1",
		"Numbers.Keys2",
		"Numbers.Keys3",
		"Numbers.Keys4",
		"Numbers.Keys5",
		"Numbers.Keys6",
		"Numbers.Keys7",
		"Numbers.Keys8",
		"Numbers.Keys9"
	};
	
	public void antibot(L2Character player)
	{
		_antibot++;
		
		if (!((L2PcInstance) player).isGM() && _antibot >= Config.ANTIBOT_KILL_MOBS)
		{
			_antibot = 0;
			_antiBotTask = ThreadPoolManager.getInstance().scheduleGeneral(new StartAntiBotTask(player), Config.ANTIBOT_TIME_VOTE * 1000);
		}
	}
	
	public static class StartAntiBotTask implements Runnable
	{
		L2Character _player_cha;
		NpcHtmlMessage _npcHtmlMessage = new NpcHtmlMessage(0);
		
		protected StartAntiBotTask(L2Character player)
		{
			if (player != null)
			{
				_player_cha = player;
				
				_player_cha.setIsParalyzed(true);
				_player_cha.setIsInvul(true);
				_player_cha.startAbnormalEffect(AbnormalEffect.SLEEP);
				_player_cha.sendPacket(new ExShowScreenMessage("Have " + Config.ANTIBOT_TIME_VOTE + " seconds to confirm the Catpcha!", 10000));
				_player_cha.getActingPlayer().sendPacket(new CreatureSay(0, Say2.PARTY, "[AntiBot]", "Have " + Config.ANTIBOT_TIME_VOTE + " seconds to confirm the Catpcha!"));
				_npcHtmlMessage.setHtml(ShowHtml_Start(_player_cha));
				_player_cha.sendPacket(_npcHtmlMessage);
			}
		}
		
		@Override
		public void run()
		{
			if (!_player_cha.getActingPlayer().isInJail())
			{
				_player_cha.sendPacket(new CreatureSay(0, Say2.TELL, "[AntiBot]", "Your time limit has elapsed!"));
				_player_cha.getActingPlayer().increaseAttempt();
				
				if (_player_cha.getActingPlayer().getAttempt() >= 3)
				{
					_player_cha.setIsParalyzed(false);
					_player_cha.setIsInvul(false);
					_player_cha.stopAbnormalEffect(AbnormalEffect.SLEEP);
					_player_cha.getActingPlayer().setPunishLevel(L2PcInstance.PunishLevel.JAIL, Config.ANTIBOT_TIME_JAIL);
					_player_cha.sendPacket(new CreatureSay(0, Say2.TELL, "[AntiBot]", "Character " + _player_cha.getActingPlayer().getName() + " jailed for " + Config.ANTIBOT_TIME_JAIL + " minutes!"));
					_log.warning("[AntiBot] Character " + _player_cha.getName() + " jailed for " + Config.ANTIBOT_TIME_JAIL + " minutes!");
				}
				else
				{
					_antiBotTask = ThreadPoolManager.getInstance().scheduleGeneral(new StartAntiBotTask(_player_cha), Config.ANTIBOT_TIME_VOTE * 1000);
				}
			}
		}
	}
	
	public static String ShowHtml_Start(L2Character player)
	{/* @formatter:off */
		String htmltext = "" +
		"<html>" +
		"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"292\" height=\"350\" background=\"L2UInd_back_Pattern\">" +
		"<tr><td valign=\"top\" align=\"center\">" +
		"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" +
		"</table><br><br>" +
		"<center><img src=\"L2UI_CH3.herotower_deco\" width=256 height=32><br></center>" +
		"<center><font color=\"00C3FF\">" + player.getName() + "<font color=\"LEVEL\"> enter the captcha</center><br><br>" +
		"<center><font color=\"LEVEL\">you get " + (3 - player.getActingPlayer().getAttempt()) + " chances to complete the captcha</center><br><br>" +
		"<br>" +
		
		"<table>" +
		"<tr>";
		_code = "";
		for (int cont = 1; cont < 5; cont++)
		{
			int number = Rnd.get(_IMG.length - 1);
			_code += String.valueOf(number);
			
			htmltext += "<td><right><img src=\"" + _IMG[number] + "\" width=37 height=31 ></right></td>";
		}
		
		player.getActingPlayer().setCode(_code);
		htmltext += "</tr>" +
		"</table><br>" +
		
		"<center><edit type=\"captcha\" var=\"captcha\" width=\"150\"></center>" +
		"<br>" +
		"<center><button value=\"Confirm\" action=\"bypass -h antibot $captcha\" width=65 height=19 back=\"L2UI_ch3.smallbutton2_over\" fore=\"L2UI_ch3.smallbutton2\"><br1>" +
		"<img src=\"L2UI_CH3.herotower_deco\" width=256 height=32></center><br>" +
		"</html></body>";
		
		return htmltext;
	}/* @formatter:on */
	
	public static String ShowHtml_End(L2Character player)
	{/* @formatter:off */
		String htmltext = "" +
		"<html>" +
		"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"292\" height=\"350\" background=\"L2UI_CH3.refinewnd_back_Pattern\">" +
		"<tr><td valign=\"top\" align=\"center\">" +
		"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" +
		"</table><br><br>" +
		"<center><img src=\"L2UI_CH3.herotower_deco\" width=256 height=32><br></center>" +
		"<center><font color=\"00C3FF\">" + player.getName() + "<font color=\"LEVEL\"> FAIL enter the captcha</center><br><br>" +
		"<br><br>" +
		"<center><img src=\"L2UI_CH3.herotower_deco\" width=256 height=32></center><br>" +
		"</html></body>";
		return htmltext;
	}/* @formatter:on */
	
	// bypass
	public void checkCode(L2PcInstance player, String code)
	{
		if (code.equals(player.getCode()))
		{
			stopAntiBotTask();
			player.setCheckCode(true);
			player.resetAttemp();
			
			player.sendPacket(new CreatureSay(0, Say2.TELL, "[AntiBot]", "Congratulations, has passed control!"));
			player.setIsParalyzed(false);
			player.setIsInvul(false);
			player.stopAbnormalEffect(AbnormalEffect.SLEEP);
		}
		else
		{
			stopAntiBotTask();
			player.setCheckCode(false);
			player.increaseAttempt();
			
			_antiBotTask = ThreadPoolManager.getInstance().scheduleGeneral(new StartAntiBotTask(player), Config.ANTIBOT_TIME_VOTE * 1000);
		}
		
		if (player.getAttempt() >= 3)
		{
			stopAntiBotTask();
			player.resetAttemp();
			
			player.setIsParalyzed(false);
			player.setIsInvul(false);
			player.stopAbnormalEffect(AbnormalEffect.SLEEP);
			
			player.setPunishLevel(L2PcInstance.PunishLevel.JAIL, Config.ANTIBOT_TIME_JAIL);
			player.sendPacket(new CreatureSay(0, Say2.TELL, "[AntiBot]", "Character " + player.getName() + " jailed for " + Config.ANTIBOT_TIME_JAIL + " minutes!"));
			for (L2PcInstance allgms : L2World.getAllGMs())
				allgms.sendPacket(new CreatureSay(16, Say2.SHOUT, "[AntiBot]", "Character " + player.getName() + " jailed for " + Config.ANTIBOT_TIME_JAIL + " minutes!"));
		}
	}
	
	public static void stopAntiBotTask()
	{
		if (_antiBotTask != null)
		{
			_antiBotTask.cancel(false);
			_antiBotTask = null;
		}
	}
}