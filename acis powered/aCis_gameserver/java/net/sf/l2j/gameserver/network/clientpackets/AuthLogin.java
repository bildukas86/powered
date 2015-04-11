package net.sf.l2j.gameserver.network.clientpackets;

import net.sf.l2j.gameserver.LoginServerThread;
import net.sf.l2j.gameserver.LoginServerThread.SessionKey;
import net.sf.l2j.gameserver.network.serverpackets.L2GameServerPacket;

/**
 * loginName + keys must match what the loginserver used.
 */
public final class AuthLogin extends L2GameClientPacket
{
	private String _loginName;
	private int _playKey1;
	private int _playKey2;
	private int _loginKey1;
	private int _loginKey2;
	
	@Override
	protected void readImpl()
	{
		_loginName = readS().toLowerCase();
		_playKey2 = readD();
		_playKey1 = readD();
		_loginKey1 = readD();
		_loginKey2 = readD();
	}
	
	@Override
	protected void runImpl()
	{
		if (getClient().getAccountName() == null)
		{
			if (LoginServerThread.getInstance().addGameServerLogin(_loginName, getClient()))
			{
				getClient().setAccountName(_loginName);
				LoginServerThread.getInstance().addWaitingClientAndSendRequest(_loginName, getClient(), new SessionKey(_loginKey1, _loginKey2, _playKey1, _playKey2));
			}
			else
				getClient().close((L2GameServerPacket) null);
		}
	}
}