package net.sf.l2j.gameserver.network.loginserverpackets;

public class KickPlayer extends LoginServerBasePacket
{
	
	private final String _account;
	
	/**
	 * @param decrypt
	 */
	public KickPlayer(byte[] decrypt)
	{
		super(decrypt);
		_account = readS();
	}
	
	/**
	 * @return Returns the account.
	 */
	public String getAccount()
	{
		return _account;
	}
	
}