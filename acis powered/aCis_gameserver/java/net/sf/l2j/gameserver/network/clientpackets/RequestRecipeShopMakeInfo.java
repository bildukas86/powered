package net.sf.l2j.gameserver.network.clientpackets;

import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.RecipeShopItemInfo;

/**
 * cdd
 */
public final class RequestRecipeShopMakeInfo extends L2GameClientPacket
{
	private int _playerObjectId, _recipeId;
	
	@Override
	protected void readImpl()
	{
		_playerObjectId = readD();
		_recipeId = readD();
	}
	
	@Override
	protected void runImpl()
	{
		final L2PcInstance player = getClient().getActiveChar();
		if (player == null)
			return;
		
		final L2PcInstance shop = L2World.getInstance().getPlayer(_playerObjectId);
		if (shop == null || shop.getPrivateStoreType() != L2PcInstance.STORE_PRIVATE_MANUFACTURE)
			return;
		
		player.sendPacket(new RecipeShopItemInfo(shop, _recipeId));
	}
}