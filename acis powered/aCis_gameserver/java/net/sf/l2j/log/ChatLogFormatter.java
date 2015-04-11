package net.sf.l2j.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import net.sf.l2j.util.StringUtil;

public class ChatLogFormatter extends Formatter
{
	private static final String CRLF = "\r\n";
	
	private final SimpleDateFormat dateFmt = new SimpleDateFormat("dd MMM H:mm:ss");
	
	@Override
	public String format(LogRecord record)
	{
		Object[] params = record.getParameters();
		final StringBuilder output = StringUtil.startAppend(30 + record.getMessage().length() + (params != null ? 10 * params.length : 0), "[", dateFmt.format(new Date(record.getMillis())), "] ");
		
		if (params != null)
		{
			for (Object p : params)
				StringUtil.append(output, String.valueOf(p), " ");
		}
		
		StringUtil.append(output, record.getMessage(), CRLF);
		
		return output.toString();
	}
}