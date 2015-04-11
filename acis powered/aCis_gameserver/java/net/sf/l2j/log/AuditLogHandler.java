package net.sf.l2j.log;

import java.io.IOException;
import java.util.logging.FileHandler;

/**
 * @author zabbix
 */
public class AuditLogHandler extends FileHandler
{
	public AuditLogHandler() throws IOException, SecurityException
	{
		super();
	}
}