package net.sf.l2j.log;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class ChatFilter implements Filter
{
	@Override
	public boolean isLoggable(LogRecord record)
	{
		return "chat".equals(record.getLoggerName());
	}
}