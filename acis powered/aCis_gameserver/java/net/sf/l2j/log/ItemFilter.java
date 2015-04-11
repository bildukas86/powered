package net.sf.l2j.log;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

import net.sf.l2j.gameserver.model.item.instance.ItemInstance;

/**
 * @author Advi
 */
public class ItemFilter implements Filter
{
	// By default, filter some common consumables
	private final String _excludeProcess = "Consume";
	private final String _excludeItemType = "Arrow, Shot, Herb";
	
	@Override
	public boolean isLoggable(LogRecord record)
	{
		if (!"item".equals(record.getLoggerName()))
			return false;
		
		if (_excludeProcess != null)
		{
			String[] messageList = record.getMessage().split(":");
			if (messageList.length < 2 || !_excludeProcess.contains(messageList[1]))
				return true;
		}
		
		if (_excludeItemType != null)
		{
			ItemInstance item = ((ItemInstance) record.getParameters()[0]);
			if (!_excludeItemType.contains(item.getItemType().toString()))
				return true;
		}
		return (_excludeProcess == null && _excludeItemType == null);
	}
}