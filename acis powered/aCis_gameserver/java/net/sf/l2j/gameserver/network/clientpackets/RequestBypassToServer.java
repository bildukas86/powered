package net.sf.l2j.gameserver.network.clientpackets;

import Extensions.Balancer.Balancer;
import Extensions.Balancer.BalancerEdit;
import Extensions.CCB.Manager.PartyMatchingBBSManager;
import Extensions.Events.Phoenix.EventManager;
import Extensions.Events.Phoenix.Engines.EventBuffer;
import Extensions.Events.Phoenix.Engines.EventStats;
import Extensions.Menu.Menu;
import Extensions.Menu.Security.AccountManager;
import Extensions.Menu.Security.ChangeMailDelay;
import Extensions.Menu.Security.HtmlHolder;
import Extensions.Menu.Security.MailDelay;
import Extensions.Menu.Security.SecMailDelay;
import Extensions.Protection.L2AntiBot;
import Extensions.StartUpSystem.StartupSystem;
import Extensions.Vip.VIPEngine;
import Extensions.VoicedCommands.BuffCommand;
import Extensions.VoicedCommands.Survey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import java.util.logging.Level;

import net.sf.l2j.Config;
import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.communitybbs.CommunityBoard;
import net.sf.l2j.gameserver.datatables.AdminCommandAccessRights;
import net.sf.l2j.gameserver.datatables.ItemTable;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.handler.AdminCommandHandler;
import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.handler.VoicedCommandHandler;
import net.sf.l2j.gameserver.model.BlockList;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.L2Party;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2OlympiadManagerInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.entity.Hero;
import net.sf.l2j.gameserver.model.olympiad.OlympiadManager;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.AskJoinParty;
import net.sf.l2j.gameserver.network.serverpackets.ExShowScreenMessage;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.util.GMAudit;

public final class RequestBypassToServer extends L2GameClientPacket
{
	private String _command;
	
	@Override
	protected void readImpl()
	{
		_command = readS();
	}
	
	@Override
	protected void runImpl()
	{
		final L2PcInstance activeChar = getClient().getActiveChar();
		if (activeChar == null)
			return;
		
		if (!getClient().getFloodProtectors().getServerBypass().tryPerformAction(_command))
			return;
		
		if (_command.isEmpty())
		{
			_log.info(activeChar.getName() + " sent an empty requestBypass packet.");
			activeChar.logout();
			return;
		}
		
		try
		{
			if (_command.startsWith("admin_"))
			{
				String command = _command.split(" ")[0];
				
				IAdminCommandHandler ach = AdminCommandHandler.getInstance().getAdminCommandHandler(command);
				if (ach == null)
				{
					if (activeChar.isGM())
						activeChar.sendMessage("The command " + command.substring(6) + " doesn't exist.");
					
					//_log.warning("No handler registered for admin command '" + command + "'");
					return;
				}
				
				if (!AdminCommandAccessRights.getInstance().hasAccess(command, activeChar.getAccessLevel()))
				{
					activeChar.sendMessage("You don't have the access rights to use this command.");
					_log.warning(activeChar.getName() + " tried to use admin command " + command + " without proper Access Level.");
					return;
				}
				
				if (Config.GMAUDIT)
					GMAudit.auditGMAction(activeChar.getName() + " [" + activeChar.getObjectId() + "]", _command, (activeChar.getTarget() != null ? activeChar.getTarget().getName() : "no-target"));
				
				ach.useAdminCommand(_command, activeChar);
			}
			else if (_command.startsWith("player_help "))
				playerHelp(activeChar, _command.substring(12));
			else if ((this._command.startsWith("voice")) && (this._command.charAt(6) == '.') && (this._command.length() > 7))
			{
				int endOfCommand = this._command.indexOf(" ", 7);
				String vparams;
				String vc;
				if (endOfCommand > 0)
				{
					vc = this._command.substring(7, endOfCommand).trim();
					vparams = this._command.substring(endOfCommand).trim();
				}
				else
				{
					vc = this._command.substring(7).trim();
					vparams = null;
				}
				if (vc.length() > 0)
				{
					IVoicedCommandHandler vch = VoicedCommandHandler.getInstance().getVoicedCommandHandler(vc);
					if (vch != null)
					{
						vch.useVoicedCommand(vc, activeChar, vparams);
					}
				}
			}
			else if (_command.startsWith("npc_"))
			{
				if (!activeChar.validateBypass(_command))
					return;
				
				int endOfId = _command.indexOf('_', 5);
				String id;
				if (endOfId > 0)
					id = _command.substring(4, endOfId);
				else
					id = _command.substring(4);
				
				try
				{
					final L2Object object = L2World.getInstance().findObject(Integer.parseInt(id));
					
					if (object != null && object instanceof L2Npc && endOfId > 0 && ((L2Npc) object).canInteract(activeChar))
						((L2Npc) object).onBypassFeedback(activeChar, _command.substring(endOfId + 1));
					
					activeChar.sendPacket(ActionFailed.STATIC_PACKET);
				}
				catch (NumberFormatException nfe)
				{
				}
			}
			// Navigate throught Manor windows
			else if (_command.startsWith("manor_menu_select?"))
			{
				L2Object object = activeChar.getTarget();
				if (object instanceof L2Npc)
					((L2Npc) object).onBypassFeedback(activeChar, _command);
			}
			else if (_command.startsWith("bbs_") || _command.startsWith("_bbs") || _command.startsWith("_friend") || _command.startsWith("_mail") || _command.startsWith("_block"))
			{
				CommunityBoard.getInstance().handleCommands(getClient(), _command);
			}
			else if (_command.startsWith("Quest "))
			{
				if (!activeChar.validateBypass(_command))
					return;
				
				String[] str = _command.substring(6).trim().split(" ", 2);
				if (str.length == 1)
					activeChar.processQuestEvent(str[0], "");
				else
					activeChar.processQuestEvent(str[0], str[1]);
			}
			else if (_command.startsWith("_match"))
			{
				String params = _command.substring(_command.indexOf("?") + 1);
				StringTokenizer st = new StringTokenizer(params, "&");
				int heroclass = Integer.parseInt(st.nextToken().split("=")[1]);
				int heropage = Integer.parseInt(st.nextToken().split("=")[1]);
				int heroid = Hero.getInstance().getHeroByClass(heroclass);
				if (heroid > 0)
					Hero.getInstance().showHeroFights(activeChar, heroclass, heroid, heropage);
			}
			else if (_command.startsWith("_diary"))
			{
				String params = _command.substring(_command.indexOf("?") + 1);
				StringTokenizer st = new StringTokenizer(params, "&");
				int heroclass = Integer.parseInt(st.nextToken().split("=")[1]);
				int heropage = Integer.parseInt(st.nextToken().split("=")[1]);
				int heroid = Hero.getInstance().getHeroByClass(heroclass);
				if (heroid > 0)
					Hero.getInstance().showHeroDiary(activeChar, heroclass, heroid, heropage);
			}
			// Customs
			// Survey
			else if (_command.startsWith("Survey_"))
			{
				Survey.handleCommands(getClient(), _command);
			}
			// .buff command
			else if (_command.startsWith("buffCommandFight"))
			{
				BuffCommand.getFullBuff(activeChar, false);
			}
			else if (_command.startsWith("buffCommandMage"))
			{
				BuffCommand.getFullBuff(activeChar, true);
			}
			else if (_command.startsWith("buffCommand") && BuffCommand.check(activeChar))
			{
				String idBuff = _command.substring(12);
				int parseIdBuff = Integer.parseInt(idBuff);
				SkillTable.getInstance().getInfo(parseIdBuff, SkillTable.getInstance().getMaxLevel(parseIdBuff)).getEffects(activeChar, activeChar);
				BuffCommand.showHtml(activeChar);
			}
			else if (_command.startsWith("cancelBuffs") && BuffCommand.check(activeChar))
			{
				activeChar.stopAllEffectsExceptThoseThatLastThroughDeath();
				BuffCommand.showHtml(activeChar);
			}
			// Balancer Menu
			else if (_command.startsWith("bp_balance "))
			{
				String bp = _command.substring(11);
				StringTokenizer st = new StringTokenizer(bp);
				if (st.countTokens() != 1)
					return;
				int classId = Integer.parseInt(st.nextToken());
				
				Balancer.sendBalanceWindow(classId, activeChar);
			}
			else if (_command.startsWith("bp_add"))
			{
				String bp = _command.substring(7);
				StringTokenizer st = new StringTokenizer(bp);
				
				if (st.countTokens() != 3)
					return;
				String stat = st.nextToken();
				int classId = Integer.parseInt(st.nextToken()), value = Integer.parseInt(st.nextToken());
				BalancerEdit.editStat(stat, classId, value, true);
				Balancer.sendBalanceWindow(classId, activeChar);
			}
			
			else if (_command.startsWith("bp_rem"))
			{
				String bp = _command.substring(7);
				StringTokenizer st = new StringTokenizer(bp);
				
				if (st.countTokens() != 3)
					return;
				String stat = st.nextToken();
				int classId = Integer.parseInt(st.nextToken()), value = Integer.parseInt(st.nextToken());
				BalancerEdit.editStat(stat, classId, value, false);
				Balancer.sendBalanceWindow(classId, activeChar);
			}
			// Pin
			else if (_command.startsWith("submitpin"))
			{
				String value = _command.substring(9);
				StringTokenizer s = new StringTokenizer(value, " ");
				int _pin = activeChar.getPin();
				
				if (activeChar.getPincheck())
				{
					_pin = Integer.parseInt(s.nextToken());
					
					if (Integer.toString(_pin).length() != 4)
					{
						activeChar.sendMessage("You have to fill the pin box with 4 numbers.Not more, not less.");
						return;
					}
					try (Connection con = L2DatabaseFactory.getInstance().getConnection())
					{
						PreparedStatement statement = con.prepareStatement("UPDATE characters SET pin=? WHERE obj_Id=?");
						
						statement.setInt(1, _pin);
						statement.setInt(2, activeChar.getObjectId());
						statement.execute();
						statement.close();
						activeChar.setPincheck(false);
						activeChar.updatePincheck();
						activeChar.sendMessage("You successfully submitted your pin code.You will need it in order to login.");
						activeChar.sendMessage("Your Pin Code is: " + _pin);
					}
					catch (Exception e)
					{
						_log.warning("could not set char first login:" + e);
					}
				}
			}
			else if (_command.startsWith("enterpin"))
			{
				String value = _command.substring(8);
				StringTokenizer s = new StringTokenizer(value, " ");
				int dapin = 0;
				int pin = 0;
				dapin = Integer.parseInt(s.nextToken());
				try (Connection con = L2DatabaseFactory.getInstance().getConnection())
				{
					PreparedStatement statement = con.prepareStatement("SELECT pin FROM characters WHERE obj_Id=?");
					statement.setInt(1, activeChar.getObjectId());
					ResultSet rset = statement.executeQuery();
					while (rset.next())
						pin = rset.getInt("pin");
					if (pin == dapin)
					{
						activeChar.sendMessage("Pin Code Authenticated Successfully.You are now free to move.");
						activeChar.setIsImmobilized(false);
						activeChar.setIsSubmitingPin(false);
					}
					else
					{
						activeChar.sendMessage("Pin Code does not match with the submitted one.You will now get disconnected!");
						wait(2);
						activeChar.logout();
					}
				}
				catch (Exception e)
				{
					activeChar.sendMessage("The Pin Code must be 4 numbers.");
				}
			}
			// Menu Panel
			else if (_command.equals("user") || _command.equals("menu"))// back (User)
				Menu.sendUserPage(activeChar);
			else if (_command.equals("panel"))// back (User)
				Menu.sendUserPanelPage(activeChar);
			else if (_command.equals("info"))// back (Info)
				Menu.sendUserPanelInfoPage(activeChar);
			else if (_command.equals("server"))// back (Server)
				Menu.sendUserPanelInfoServerPage(activeChar);
			else if (_command.startsWith("submenu "))// info and sub-menus
				Menu.sendUserPanelSubmenuPages(activeChar, _command.substring(12));
			else if (_command.equals("trade"))
			{
				if (activeChar.getTradeRefusal() == false)
				{
					activeChar.setTradeRefusal(true);
					activeChar.sendMessage("Trade refusal enabled!");
					ExShowScreenMessage message1 = new ExShowScreenMessage("Trade refusal mode is now enabled!", 4000);
					activeChar.sendPacket(message1);
				}
				else
				{
					activeChar.setTradeRefusal(false);
					activeChar.sendMessage("Trade refusal disabled!");
					ExShowScreenMessage message1 = new ExShowScreenMessage("Trade refusal mode is now disabled!", 4000);
					activeChar.sendPacket(message1);
				}
				Menu.sendUserPanelPage(activeChar);
			}
			else if (_command.equals("exchange"))
			{
				if (activeChar.getExchangeRefusal() == false)
				{
					activeChar.setExchangeRefusal(true);
					activeChar.sendMessage("Exchange refusal enabled!");
					ExShowScreenMessage message1 = new ExShowScreenMessage("Exchange refusal mode is now enabled!", 4000);
					activeChar.sendPacket(message1);
				}
				else
				{
					activeChar.setExchangeRefusal(false);
					activeChar.sendMessage("Exchange refusal disabled!");
					ExShowScreenMessage message1 = new ExShowScreenMessage("Exchange refusal mode is now disabled!", 4000);
					activeChar.sendPacket(message1);
				}
				Menu.sendUserPanelPage(activeChar);
			}
			else if (_command.equals("exp"))
			{
				if (activeChar.getExpSpRefusal() == false)
				{
					activeChar.sendMessage("Exp/SP refusal enabled!");
					ExShowScreenMessage message1 = new ExShowScreenMessage("Exp/sp refusal mode is now enabled!", 4000);
					activeChar.sendPacket(message1);
					activeChar.setExpSpRefusal(true);
				}
				else
				{
					activeChar.sendMessage("Exp/SP refusal disabled!");
					ExShowScreenMessage message1 = new ExShowScreenMessage("Exp/sp refusal mode is now disabled!", 4000);
					activeChar.sendPacket(message1);
					activeChar.setExpSpRefusal(false);
				}
				Menu.sendUserPanelPage(activeChar);
			}
			else if (_command.equals("pm"))
			{
				if (activeChar.isInRefusalMode() == false)
				{
					activeChar.setInRefusalMode(true);
					activeChar.sendMessage("Pm refusal mode enabled!");
					ExShowScreenMessage message1 = new ExShowScreenMessage("Pm refusal mode is now enabled!", 4000);
					activeChar.sendPacket(message1);
				}
				else
				{
					activeChar.setInRefusalMode(false);
					activeChar.sendMessage("Pm refusal mode disabled!");
					ExShowScreenMessage message1 = new ExShowScreenMessage("Pm refusal mode is now disabled!", 4000);
					activeChar.sendPacket(message1);
				}
				Menu.sendUserPanelPage(activeChar);
			}
			else if (_command.equals("buff"))
			{
				if (activeChar.isBuffProtected() == false)
				{
					activeChar.setIsBuffProtected(true);
					activeChar.sendMessage("Buff refusal mode enabled!");
					ExShowScreenMessage message1 = new ExShowScreenMessage("Buff refusal mode is now enabled!", 4000);
					activeChar.sendPacket(message1);
				}
				else
				{
					activeChar.setIsBuffProtected(false);
					activeChar.sendMessage("Buff refusal mode disabled!");
					ExShowScreenMessage message1 = new ExShowScreenMessage("Buff refusal mode is now disabled!", 4000);
					activeChar.sendPacket(message1);
				}
				Menu.sendUserPanelPage(activeChar);
			}
			else if (_command.equals("effects"))
			{
				if (activeChar.getSsEffects() == false)
				{
					activeChar.setSsEffects(true);
					activeChar.sendMessage("World effects blocked!");
					ExShowScreenMessage message1 = new ExShowScreenMessage("World effects blocked!", 4000);
					activeChar.sendPacket(message1);
				}
				else
				{
					activeChar.setSsEffects(false);
					activeChar.sendMessage("World effects are unblocked!");
					ExShowScreenMessage message1 = new ExShowScreenMessage("World effects are unblocked!", 4000);
					activeChar.sendPacket(message1);
				}
				Menu.sendUserPanelPage(activeChar);
			}
			else if (_command.equals("autoss"))
			{
				if (VIPEngine.getInstance().getAllVips().contains(activeChar.getObjectId()))
				{
					if (activeChar.getUnlimitedArrowsSS() == false)
					{
						activeChar.setUnlimitedArrowsSS(true);
						activeChar.sendMessage("Unlimited SS & Arrows mode enabled.");
						ExShowScreenMessage message1 = new ExShowScreenMessage("Unlimited SS & Arrows mode enabled.", 4000);
						activeChar.sendPacket(message1);
					}
					else
					{
						activeChar.setUnlimitedArrowsSS(false);
						activeChar.sendMessage("Unlimited SS & Arrows mode disabled.");
						ExShowScreenMessage message1 = new ExShowScreenMessage("Unlimited SS & Arrows mode disabled.", 4000);
						activeChar.sendPacket(message1);
					}
				}
				else
				{
					activeChar.sendMessage("Only ViP's can use this function!");
					ExShowScreenMessage message1 = new ExShowScreenMessage("Only ViP's can use this function!", 4000);
					activeChar.sendPacket(message1);
				}
				Menu.sendUserPanelPage(activeChar);
			}
			else if (_command.equals("security")) // Main Window
				HtmlHolder.showHtmlWindow(activeChar);
			else if (_command.startsWith("submitemail")) // If player has not enter email
			{
				try
				{
					String value = _command.substring(11);
					StringTokenizer s = new StringTokenizer(value, " ");
					String email1 = null;
					// String email2 = null;
					
					try
					{
						email1 = s.nextToken();
						// email2 = s.nextToken();
						try
						{
							try (Connection con = L2DatabaseFactory.getInstance().getConnection())
							{
								PreparedStatement statement = con.prepareStatement("UPDATE characters SET email=? WHERE obj_Id=?");
								statement.setString(1, email1);
								statement.setInt(2, activeChar.getObjectId());
								statement.execute();
								statement.close();
							}
							catch (Exception e)
							{
							}
							activeChar.sendMessage("We successfully added your email " + email1 + " to our database.");
							AccountManager.setHasSubEmail(activeChar);
						}
						catch (Exception e)
						{
							activeChar.sendMessage("Something went wrong.");
							HtmlHolder.mainHtml(activeChar);
						}
					}
					catch (Exception e)
					{
						activeChar.sendMessage("Something went wrong.");
						HtmlHolder.mainHtml(activeChar);
					}
				}
				catch (Exception e)
				{
					activeChar.sendMessage("Something went wrong.");
					HtmlHolder.mainHtml(activeChar);
				}
			}
			else if (_command.startsWith("generateCode")) // Create new 4 digit code sends on database & email
			{
				HtmlHolder.sendingHtml(activeChar);
				ThreadPoolManager.getInstance().scheduleGeneral(new MailDelay(activeChar), 1000);
			}
			else if (_command.startsWith("submitcode")) // checks if the 4 digit code is correct
			{
				try
				{
					String value = _command.substring(10);
					StringTokenizer s = new StringTokenizer(value, " ");
					int _code = 0;
					int _dbcode = AccountManager.getCode(activeChar);
					
					try
					{
						_code = Integer.parseInt(s.nextToken());
						
						if (Integer.toString(_code).length() != 4)
						{
							activeChar.sendMessage("You have to fill the pin box with 4 numbers.Not more, not less.");
							return;
						}
						
						if (_code == _dbcode)
							HtmlHolder.changepasshtml(activeChar);
						else
						{
							activeChar.sendMessage("The code we sent and the code you submitted does not match. You are not able to change your pass. Try again.");
						}
					}
					
					catch (Exception e)
					{
						activeChar.sendMessage("The Code must be 4 numbers.");
						HtmlHolder.mainHtml(activeChar);
					}
				}
				catch (Exception e)
				{
					activeChar.sendMessage("The Code must be 4 numbers.");
					HtmlHolder.mainHtml(activeChar);
				}
			}
			else if (_command.startsWith("change_password")) // Change the password
			{
				StringTokenizer st = new StringTokenizer(_command);
				st.nextToken();
				String newPass = null;
				String repeatNewPass = null;
				try
				{
					if (st.hasMoreTokens())
					{
						newPass = st.nextToken();
						repeatNewPass = st.nextToken();
					}
					else
					{
						activeChar.sendMessage("Please fill in all the blanks before requesting for a password change.");
						return;
					}
					AccountManager.changePassword(newPass, repeatNewPass, activeChar);
				}
				catch (StringIndexOutOfBoundsException e)
				{
				}
			}
			else if (_command.startsWith("addSec")) // Add security question
			{
				if (AccountManager.hasSubSec(activeChar))
					activeChar.sendMessage("You have already submitted a security question");
				else
					HtmlHolder.addSecHtml(activeChar);
			}
			else if (_command.startsWith("submitanswer")) // Submit answer of the security question
			{
				try
				{
					String value = _command.substring(12);
					StringTokenizer s = new StringTokenizer(value, " ");
					String answer = null;
					try
					{
						answer = s.nextToken();
						try (Connection con = L2DatabaseFactory.getInstance().getConnection())
						{
							PreparedStatement statement = con.prepareStatement("UPDATE characters SET answer=? WHERE obj_Id=?");
							statement.setString(1, answer);
							statement.setInt(2, activeChar.getObjectId());
							statement.execute();
							statement.close();
						}
						catch (Exception e)
						{
						}
						activeChar.sendMessage("We successfully added your security answer to our database.");
						AccountManager.setHasSubSec(activeChar);
						HtmlHolder.mainHtml(activeChar);
					}
					catch (Exception e)
					{
						activeChar.sendMessage("Something went wrong.");
						HtmlHolder.mainHtml(activeChar);
					}
				}
				catch (Exception e)
				{
					activeChar.sendMessage("Something went wrong.");
					HtmlHolder.mainHtml(activeChar);
				}
			}
			else if (_command.startsWith("forgSec")) // Request answer and send to player email
			{
				AccountManager.forgSec(activeChar);
			}
			else if (_command.startsWith("changeSec"))
			{
				if (!AccountManager.hasSubSec(activeChar))
				{
					activeChar.sendMessage("You have not submitted a security question");
					HtmlHolder.mainHtml(activeChar);
				}
				else
				{
					HtmlHolder.sendingHtml(activeChar);
					ThreadPoolManager.getInstance().scheduleGeneral(new SecMailDelay(activeChar), 3000);
				}
			}
			else if (_command.startsWith("generateCode"))
			{
				if (!AccountManager.hasSubSec(activeChar))
				{
					activeChar.sendMessage("You have not submitted a security question.");
				}
				else
				{
					HtmlHolder.sendingHtml(activeChar);
					ThreadPoolManager.getInstance().scheduleGeneral(new SecMailDelay(activeChar), 3000);
				}
			}
			else if (_command.startsWith("changeEmail"))
			{
				HtmlHolder.sendingHtml(activeChar);
				ThreadPoolManager.getInstance().scheduleGeneral(new ChangeMailDelay(activeChar), 3000);
			}
			else if (_command.startsWith("forgPass"))
			{
				if (AccountManager.hasSubSec(activeChar))
					HtmlHolder.forgPassHtml(activeChar);
				else
					HtmlHolder.addSecHtml(activeChar);
			}
			else if (_command.startsWith("submitseccode"))
			{
				try
				{
					String value = _command.substring(13);
					StringTokenizer s = new StringTokenizer(value, " ");
					int _code = 0;
					int _dbcode = AccountManager.getSecCode(activeChar);
					
					try
					{
						_code = Integer.parseInt(s.nextToken());
						
						if (Integer.toString(_code).length() != 4)
						{
							activeChar.sendMessage("You have to fill the pin box with 4 numbers.Not more, not less.");
							HtmlHolder.mainHtml(activeChar);
							return;
						}
						
						if (_code == _dbcode)
							HtmlHolder.successchangehtml(activeChar);
						else
						{
							activeChar.sendMessage("The code we sent and the code you submitted does not match. You are not able to change your security answer. Try again.");
							HtmlHolder.mainHtml(activeChar);
						}
					}
					catch (Exception e)
					{
						activeChar.sendMessage("The Code must be 4 numbers.");
						HtmlHolder.mainHtml(activeChar);
					}
				}
				catch (Exception e)
				{
					activeChar.sendMessage("The Code must be 4 numbers.");
					HtmlHolder.mainHtml(activeChar);
				}
			}
			else if (_command.startsWith("change_sec"))
			{
				StringTokenizer st = new StringTokenizer(_command);
				st.nextToken();
				String newSec = null;
				String repeatNewSec = null;
				try
				{
					if (st.hasMoreTokens())
					{
						newSec = st.nextToken();
						repeatNewSec = st.nextToken();
					}
					else
					{
						activeChar.sendMessage("Please fill in all the blanks before requesting for a answer change.");
						return;
					}
					AccountManager.changeAnswer(newSec, repeatNewSec, activeChar);
				}
				catch (StringIndexOutOfBoundsException e)
				{
				}
			}
			else if (_command.startsWith("resetPass"))
			{
				StringTokenizer st = new StringTokenizer(_command);
				st.nextToken();
				
				String acc = activeChar.getAccountName();
				String cha = activeChar.getName();
				try
				{
					if (st.hasMoreTokens())
					{
						AccountManager.ans = st.nextToken();
					}
					else
					{
						activeChar.sendMessage("Please type your secret answer to use password reset.");
						return;
					}
					AccountManager.resetPass(acc, cha, activeChar);
				}
				catch (StringIndexOutOfBoundsException e)
				{
				}
			}
			else if (_command.startsWith("change_email"))
			{
				StringTokenizer st = new StringTokenizer(_command);
				st.nextToken();
				String newMail = null;
				String repeatNewMail = null;
				try
				{
					if (st.hasMoreTokens())
					{
						newMail = st.nextToken();
						repeatNewMail = st.nextToken();
					}
					else
					{
						activeChar.sendMessage("Please fill in all the blanks before requesting for a email change.");
						return;
					}
					AccountManager.changeEmail(newMail, repeatNewMail, activeChar);
					HtmlHolder.showHtmlWindow(activeChar);
				}
				catch (StringIndexOutOfBoundsException e)
				{
				}
			}
			else if (_command.startsWith("submitchangecode"))
			{
				try
				{
					String value = _command.substring(16);
					StringTokenizer s = new StringTokenizer(value, " ");
					int _code = 0;
					int _dbcode = AccountManager.getMailCode(activeChar);
					
					try
					{
						_code = Integer.parseInt(s.nextToken());
						
						if (Integer.toString(_code).length() != 4)
						{
							activeChar.sendMessage("You have to fill the pin box with 4 numbers.Not more, not less.");
							return;
						}
						if (_code == _dbcode)
							HtmlHolder.changeemailhtml(activeChar);
						else
						{
							activeChar.sendMessage("The code we sent and the code you submitted does not match. You are not able to change your email. Try again.");
							HtmlHolder.mainHtml(activeChar);
						}
					}
					catch (Exception e)
					{
						activeChar.sendMessage("The Code must be 4 numbers.");
						HtmlHolder.mainHtml(activeChar);
					}
				}
				catch (Exception e)
				{
					activeChar.sendMessage("The Code must be 4 numbers.");
					HtmlHolder.mainHtml(activeChar);
				}
			}
			// Request npc
			else if (_command.startsWith("showreq"))
			{
				StringTokenizer st = new StringTokenizer(_command);
				st.nextToken();
				String id = st.nextToken();
				StringBuilder sb = new StringBuilder();
				sb.append("<html><title>Requester NPC</title><body>");
				try (Connection con = L2DatabaseFactory.getInstance().getConnection())
				{
					PreparedStatement statement = con.prepareStatement("SELECT * FROM requests WHERE id=?");
					statement.setString(1, id);
					statement.execute();
					ResultSet result = statement.executeQuery();
					String info;
					String title;
					String name;
					String type;
					while (result.next())
					{
						info = result.getString(5);
						title = result.getString(3);
						name = result.getString(2);
						type = result.getString(4);
						sb.append("<br><br><br><center>Character Name : " + name + "</center>");
						sb.append("<br><br><center>Request Title : " + title + "</center>");
						sb.append("<br><br><center>Reaquest Info : " + info + "</center>");
						sb.append("<br><br><center>Request Type : " + type + "</center>");
						sb.append("</body></html>");
					}
					statement.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				NpcHtmlMessage msg = new NpcHtmlMessage(6);
				msg.setHtml(sb.toString());
				activeChar.sendPacket(msg);
			}
			else if (_command.startsWith("addreq"))
			{
				StringTokenizer st = new StringTokenizer(_command);
				st.nextToken();
				String title = st.nextToken();
				String desc = " ";
				String type = st.nextToken();
				while (st.hasMoreTokens())
				{
					desc = desc + " " + st.nextToken() + " ";
				}
				try (Connection con = L2DatabaseFactory.getInstance().getConnection())
				{
					PreparedStatement statement = con.prepareStatement("INSERT INTO requests VALUES ('0',?,?,?,?)");
					
					statement.setString(1, activeChar.getName());
					statement.setString(2, title);
					statement.setString(3, type);
					statement.setString(4, desc);
					statement.execute();
					statement.close();
					
					activeChar.sendMessage("Request added successfully.");
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			// mail
			else if (_command.startsWith("sendMsg"))
			{
				StringTokenizer st = new StringTokenizer(_command);
				st.nextToken();
				String to = st.nextToken();
				String title = st.nextToken();
				String message = "";
				
				while (st.hasMoreTokens())
					message = message + st.nextToken() + " ";
				
				if (to.equalsIgnoreCase(activeChar.getName()))
				{
					activeChar.sendMessage("You cannot send a message to yourself.");
					return;
				}
				if (to.equalsIgnoreCase("") || message.equalsIgnoreCase(""))
				{
					activeChar.sendMessage("You have to fill all the fields.");
					return;
				}
				if (title.equalsIgnoreCase(""))
					title = "(No Subject)";
				
				try (Connection con = L2DatabaseFactory.getInstance().getConnection())
				{
					PreparedStatement statement = con.prepareStatement("INSERT INTO mails VALUES ('0',?,?,?,?)");
					statement.setString(1, activeChar.getName());
					statement.setString(2, to);
					statement.setString(3, title);
					statement.setString(4, message);
					statement.execute();
					activeChar.sendMessage("Your message has been sent.");
					statement.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					_log.log(Level.SEVERE, e.getMessage(), e);
				}
			}
			else if (_command.startsWith("delMsg"))
			{
				StringTokenizer st = new StringTokenizer(_command);
				st.nextToken();
				int messageId = Integer.parseInt(st.nextToken());
				
				try (Connection con = L2DatabaseFactory.getInstance().getConnection())
				{
					PreparedStatement statement = con.prepareStatement("DELETE FROM mails WHERE id=?");
					statement.setInt(1, messageId);
					statement.execute();
					activeChar.sendMessage("The message has been deleted.");
					statement.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					_log.log(Level.SEVERE, e.getMessage(), e);
				}
			}
			// AntiBot
			else if (_command.startsWith("antibot"))
			{
				if (_command.length() >= 9)
				{
					String texto = _command.substring(8);
					
					StringTokenizer st = new StringTokenizer(texto);
					try
					{
						String catpcha = null;
						
						if (st.hasMoreTokens())
						{
							catpcha = st.nextToken();
						}
						
						L2AntiBot.getInstance().checkCode(activeChar, catpcha);
					}
					catch (Exception e)
					{
						activeChar.sendMessage("A problem happened during the reading of captcha!");
						_log.log(Level.WARNING, "", e);
					}
				}
				else
				{
					L2AntiBot.getInstance().checkCode(activeChar, "FAIL");
				}
			}
			// startup system
			else if (_command.startsWith("Startup_"))
				StartupSystem.handleCommands(getClient(), _command.substring(8));
			// party matching
			else if (_command.startsWith("partyMatchingInvite"))
			{
				try
				{
					String targetName = _command.substring(20);
					L2PcInstance receiver = L2World.getInstance().getPlayer(targetName);
					SystemMessage sm;
					
					if (receiver == null)
					{
						activeChar.sendPacket(SystemMessageId.FIRST_SELECT_USER_TO_INVITE_TO_PARTY);
						return;
					}
					
					if ((receiver.getClient() == null) || receiver.getClient().isDetached())
					{
						activeChar.sendMessage("Player is in offline mode.");
						return;
					}
					
					if (receiver.isInParty())
					{
						sm = SystemMessage.getSystemMessage(SystemMessageId.S1_IS_ALREADY_IN_PARTY);
						sm.addString(receiver.getName());
						activeChar.sendPacket(sm);
						return;
					}
					
					if (BlockList.isBlocked(receiver, activeChar))
					{
						sm = SystemMessage.getSystemMessage(SystemMessageId.S1_HAS_ADDED_YOU_TO_IGNORE_LIST);
						sm.addCharName(receiver);
						activeChar.sendPacket(sm);
						return;
					}
					
					if (receiver == activeChar)
					{
						activeChar.sendPacket(SystemMessageId.YOU_HAVE_INVITED_THE_WRONG_TARGET);
						return;
					}
					
					if (receiver.isCursedWeaponEquipped() || activeChar.isCursedWeaponEquipped())
					{
						receiver.sendPacket(SystemMessageId.INCORRECT_TARGET);
						return;
					}
					
					if (receiver.isInJail() || activeChar.isInJail())
					{
						activeChar.sendMessage("You cannot invite a player while is in Jail.");
						return;
					}
					
					if ((receiver.isInOlympiadMode() != activeChar.isInOlympiadMode()) || (receiver.getOlympiadGameId() != activeChar.getOlympiadGameId()) || (receiver.getOlympiadSide() != activeChar.getOlympiadSide()))
					{
						activeChar.sendMessage("A player cannot send party or friend invitations while is participating in olympiad.");
						return;
					}
					
					sm = SystemMessage.getSystemMessage(SystemMessageId.YOU_INVITED_S1_TO_PARTY);
					sm.addCharName(receiver);
					activeChar.sendPacket(sm);
					
					if (!activeChar.isInParty())
					{
						createNewParty(receiver, activeChar);
					}
					else
					{
						if (activeChar.getParty().isInDimensionalRift())
						{
							activeChar.sendMessage("You cannot invite a player when you are in the Dimensional Rift.");
						}
						else
						{
							addTargetToParty(receiver, activeChar);
						}
					}
					PartyMatchingBBSManager.refresh(receiver);
					PartyMatchingBBSManager.refresh(activeChar);
				}
				catch (StringIndexOutOfBoundsException e)
				{
					e.printStackTrace();
				}
			}
			else if (_command.startsWith("pmatch"))
				PartyMatchingBBSManager.getInstance().parseCmd(_command, activeChar);
			// Phoenix
			else if (_command.startsWith("eventvote "))
				EventManager.getInstance().addVote(activeChar, Integer.parseInt(_command.substring(10)));
			else if (_command.startsWith("eventstats "))
			{
				try
				{
					EventStats.getInstance().showHtml(Integer.parseInt(_command.substring(11)), activeChar);
				}
				catch (Exception e)
				{
					activeChar.sendMessage("Currently there are no statistics to show.");
				}
			}
			else if (_command.startsWith("eventstats_show "))
				EventStats.getInstance().showPlayerStats(Integer.parseInt(_command.substring(16)), activeChar);
			else if (_command.equals("eventbuffershow"))
				EventBuffer.getInstance().showHtml(activeChar);
			else if (_command.startsWith("eventbuffer "))
			{
				EventBuffer.getInstance().changeList(activeChar, Integer.parseInt(_command.substring(12, _command.length() - 2)), (Integer.parseInt(_command.substring(_command.length() - 1)) == 0 ? false : true));
				EventBuffer.getInstance().showHtml(activeChar);
			}
			else if (_command.startsWith("eventinfo "))
			{
				int eventId = Integer.valueOf(_command.substring(10));
				
				NpcHtmlMessage html = new NpcHtmlMessage(0);
				html.setFile("data/html/mods/EventInfo/" + eventId + ".htm");
				html.replace("%amount%", String.valueOf(EventManager.getInstance().getInt(eventId, "rewardAmmount")));
				html.replace("%item%", ItemTable.getInstance().createDummyItem(EventManager.getInstance().getInt(eventId, "rewardId")).getItemName());
				html.replace("%minlvl%", String.valueOf(EventManager.getInstance().getInt(eventId, "minLvl")));
				html.replace("%maxlvl%", String.valueOf(EventManager.getInstance().getInt(eventId, "maxLvl")));
				html.replace("%time%", String.valueOf(EventManager.getInstance().getInt(eventId, "matchTime") / 60));
				html.replace("%players%", String.valueOf(EventManager.getInstance().getInt(eventId, "minPlayers")));
				html.replace("%url%", EventManager.getInstance().getString("siteUrl"));
				html.replace("%buffs%", EventManager.getInstance().getBoolean(eventId, "removeBuffs") ? "Self" : "Full");
				activeChar.sendPacket(html);
				activeChar.sendPacket(ActionFailed.STATIC_PACKET);
			}
			else if (_command.startsWith("event_vote"))
				EventManager.getInstance().addVote(activeChar, Integer.parseInt(_command.substring(11)));
			else if (_command.equals("event_register"))
				EventManager.getInstance().registerPlayer(activeChar);
			else if (_command.equals("event_unregister"))
				EventManager.getInstance().unregisterPlayer(activeChar);
			
			// /Customs
			
			else if (_command.startsWith("arenachange")) // change
			{
				final boolean isManager = activeChar.getCurrentFolkNPC() instanceof L2OlympiadManagerInstance;
				if (!isManager)
				{
					// Without npc, command can be used only in observer mode on arena
					if (!activeChar.inObserverMode() || activeChar.isInOlympiadMode() || activeChar.getOlympiadGameId() < 0)
						return;
				}
				
				if (OlympiadManager.getInstance().isRegisteredInComp(activeChar))
				{
					activeChar.sendPacket(SystemMessageId.WHILE_YOU_ARE_ON_THE_WAITING_LIST_YOU_ARE_NOT_ALLOWED_TO_WATCH_THE_GAME);
					return;
				}
				
				if (EventManager.getInstance().players.contains(activeChar))
				{
					activeChar.sendMessage("You can not observe games while registered for an event!");
					return;
				}
				
				final int arenaId = Integer.parseInt(_command.substring(12).trim());
				activeChar.enterOlympiadObserverMode(arenaId);
			}
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "Bad RequestBypassToServer: ", _command);
		}
	}
	
	private static void playerHelp(L2PcInstance activeChar, String path)
	{
		if (path.indexOf("..") != -1)
			return;
		
		final StringTokenizer st = new StringTokenizer(path);
		final String[] cmd = st.nextToken().split("#");
		
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		html.setFile("data/html/help/" + cmd[0]);
		if (cmd.length > 1)
			html.setItemId(Integer.parseInt(cmd[1]));
		html.disableValidation();
		activeChar.sendPacket(html);
	}
	
	private static void addTargetToParty(L2PcInstance receiver, L2PcInstance requestor)
	{
		final L2Party party = requestor.getParty();
		
		if (!party.isLeader(requestor))
		{
			requestor.sendPacket(SystemMessageId.ONLY_LEADER_CAN_INVITE);
			return;
		}
		
		if (party.getMemberCount() >= 9)
		{
			requestor.sendPacket(SystemMessageId.PARTY_FULL);
			return;
		}
		
		if (party.getPendingInvitation() && !party.isInvitationRequestExpired())
		{
			requestor.sendPacket(SystemMessageId.WAITING_FOR_ANOTHER_REPLY);
			return;
		}
		
		if (!receiver.isProcessingRequest())
		{
			requestor.onTransactionRequest(receiver);
			receiver.sendPacket(new AskJoinParty(requestor.getName(), party.getLootDistribution()));
			party.setPendingInvitation(true);
		}
		else
		{
			SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_IS_BUSY_TRY_LATER);
			sm.addString(receiver.getName());
			requestor.sendPacket(sm);
		}
	}
	
	private static void createNewParty(L2PcInstance receiver, L2PcInstance requestor)
	{
		if (!receiver.isProcessingRequest())
		{
			requestor.setParty(new L2Party(requestor, 1));
			
			requestor.onTransactionRequest(receiver);
			receiver.sendPacket(new AskJoinParty(requestor.getName(), 1));
			requestor.getParty().setPendingInvitation(true);
		}
		else
		{
			requestor.sendPacket(SystemMessageId.WAITING_FOR_ANOTHER_REPLY);
		}
	}
}