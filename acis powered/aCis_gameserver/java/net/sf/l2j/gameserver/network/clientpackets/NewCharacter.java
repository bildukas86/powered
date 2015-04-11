package net.sf.l2j.gameserver.network.clientpackets;

import net.sf.l2j.gameserver.datatables.CharTemplateTable;
import net.sf.l2j.gameserver.model.base.ClassId;
import net.sf.l2j.gameserver.network.serverpackets.CharTemplates;

public final class NewCharacter extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
	}
	
	@Override
	protected void runImpl()
	{
		CharTemplates ct = new CharTemplates();
		
		ct.addChar(CharTemplateTable.getInstance().getTemplate(0));
		ct.addChar(CharTemplateTable.getInstance().getTemplate(ClassId.fighter));
		ct.addChar(CharTemplateTable.getInstance().getTemplate(ClassId.mage));
		ct.addChar(CharTemplateTable.getInstance().getTemplate(ClassId.elvenFighter));
		ct.addChar(CharTemplateTable.getInstance().getTemplate(ClassId.elvenMage));
		ct.addChar(CharTemplateTable.getInstance().getTemplate(ClassId.darkFighter));
		ct.addChar(CharTemplateTable.getInstance().getTemplate(ClassId.darkMage));
		ct.addChar(CharTemplateTable.getInstance().getTemplate(ClassId.orcFighter));
		ct.addChar(CharTemplateTable.getInstance().getTemplate(ClassId.orcMage));
		ct.addChar(CharTemplateTable.getInstance().getTemplate(ClassId.dwarvenFighter));
		
		sendPacket(ct);
	}
}