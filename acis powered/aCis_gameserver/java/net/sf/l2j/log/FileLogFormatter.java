package net.sf.l2j.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import net.sf.l2j.util.StringUtil;

public class FileLogFormatter extends Formatter
{
	private static final String CRLF = "\r\n";
	private static final String tab = "\t";
	private final SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss,SSS");
	
	@Override
	public String format(LogRecord record)
	{
		return StringUtil.concat(dateFmt.format(new Date(record.getMillis())), tab, record.getLevel().getName(), tab, String.valueOf(record.getThreadID()), tab, record.getLoggerName(), tab, record.getMessage(), CRLF);
	}
}
