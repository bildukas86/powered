package Guard.network.clientpackets;

import net.sf.l2j.gameserver.network.L2GameClient;
import net.sf.l2j.gameserver.network.clientpackets.L2GameClientPacket;

public class GameGuardReply extends L2GameClientPacket
{
	private int _dx;

	@Override
	protected void readImpl()
	{
		_dx = readC();
	}

	@Override
	protected void runImpl()
	{
		L2GameClient client = getClient();
		if (_dx == 104)
			client.setGameGuardOk(true);
		else
			client.setGameGuardOk(false);

	}

	@Override
	public String getType()
	{
		return "[C] CB GameGuardReply";
	}
}