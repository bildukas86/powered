package net.sf.l2j.gameserver.network.serverpackets;

import java.util.List;

import net.sf.l2j.gameserver.instancemanager.CastleManorManager.CropProcure;
import net.sf.l2j.gameserver.model.L2Manor;

/**
 * Format: ch cddd[ddddcdcdcd] c - id (0xFE) h - sub id (0x1D) c d - manor id d d - size [ d - crop id d - residual buy d - start buy d - buy price c - reward type d - seed level c - reward 1 items d - reward 1 item id c - reward 2 items d - reward 2 item id ]
 * @author l3x
 */
public class ExShowCropInfo extends L2GameServerPacket
{
	private final List<CropProcure> _crops;
	private final int _manorId;
	
	public ExShowCropInfo(int manorId, List<CropProcure> crops)
	{
		_manorId = manorId;
		_crops = crops;
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0xFE);
		writeH(0x1D);
		writeC(0);
		writeD(_manorId);
		writeD(0);
		if (_crops == null)
		{
			writeD(0);
			return;
		}
		
		writeD(_crops.size());
		for (CropProcure crop : _crops)
		{
			writeD(crop.getId());
			writeD(crop.getAmount());
			writeD(crop.getStartAmount());
			writeD(crop.getPrice());
			writeC(crop.getReward());
			writeD(L2Manor.getInstance().getSeedLevelByCrop(crop.getId()));
			writeC(1);
			writeD(L2Manor.getInstance().getRewardItem(crop.getId(), 1));
			writeC(1);
			writeD(L2Manor.getInstance().getRewardItem(crop.getId(), 2));
		}
	}
}