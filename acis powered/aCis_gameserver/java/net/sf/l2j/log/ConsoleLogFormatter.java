package net.sf.l2j.log;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import net.sf.l2j.util.StringUtil;

public class ConsoleLogFormatter extends Formatter
{
	private static final String CRLF = "\r\n";
	
	@Override
	public String format(LogRecord record)
	{
		final StringBuilder output = new StringBuilder(500);
		StringUtil.append(output, record.getMessage(), CRLF);
		
		if (record.getThrown() != null)
		{
			try
			{
				StringUtil.append(output, record.getThrown().getMessage(), CRLF);
			}
			catch (Exception ex)
			{
			}
		}
		return output.toString();
	}
}