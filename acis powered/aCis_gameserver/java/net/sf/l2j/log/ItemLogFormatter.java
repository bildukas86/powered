package net.sf.l2j.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.util.StringUtil;

/**
 * @author Advi
 */
public class ItemLogFormatter extends Formatter
{
	private static final String CRLF = "\r\n";
	private final SimpleDateFormat dateFmt = new SimpleDateFormat("dd MMM H:mm:ss");
	
	@Override
	public String format(LogRecord record)
	{
		final Object[] params = record.getParameters();
		final StringBuilder output = StringUtil.startAppend(30 + record.getMessage().length() + params.length * 50, "[", dateFmt.format(new Date(record.getMillis())), "] ", record.getMessage());
		
		for (Object p : record.getParameters())
		{
			if (p == null)
				continue;
			output.append(", ");
			if (p instanceof ItemInstance)
			{
				ItemInstance item = (ItemInstance) p;
				StringUtil.append(output, "item ", String.valueOf(item.getObjectId()), ":");
				if (item.getEnchantLevel() > 0)
				{
					StringUtil.append(output, "+", String.valueOf(item.getEnchantLevel()), " ");
				}
				
				StringUtil.append(output, item.getItem().getName(), "(", String.valueOf(item.getCount()), ")");
			}
			// else if (p instanceof L2PcInstance)
			// output.append(((L2PcInstance)p).getName());
			else
				output.append(p.toString()/* + ":" + ((L2Object)p).getObjectId() */);
		}
		output.append(CRLF);
		
		return output.toString();
	}
}