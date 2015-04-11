package net.sf.l2j.gameserver.taskmanager.tasks;

import java.io.File;
import java.util.logging.Logger;

import javax.script.ScriptException;

import net.sf.l2j.gameserver.scripting.L2ScriptEngineManager;
import net.sf.l2j.gameserver.taskmanager.TaskManager.ExecutedTask;
import net.sf.l2j.gameserver.taskmanager.models.Task;

/**
 * @author janiii
 */
public class TaskScript extends Task
{
	private static final Logger _log = Logger.getLogger(TaskScript.class.getName());
	public static final String NAME = "script";
	
	/**
	 * @see net.sf.l2j.gameserver.taskmanager.models.Task#getName()
	 */
	@Override
	public String getName()
	{
		return NAME;
	}
	
	/**
	 * @see net.sf.l2j.gameserver.taskmanager.models.Task#onTimeElapsed(net.sf.l2j.gameserver.taskmanager.TaskManager.ExecutedTask)
	 */
	@Override
	public void onTimeElapsed(ExecutedTask task)
	{
		File file = new File(L2ScriptEngineManager.SCRIPT_FOLDER, "cron/" + task.getParams()[2]);
		if (file.isFile())
		{
			try
			{
				L2ScriptEngineManager.getInstance().executeScript(file);
			}
			catch (ScriptException e)
			{
				_log.warning("Failed loading: " + task.getParams()[2]);
				L2ScriptEngineManager.reportScriptFileError(file, e);
			}
			catch (Exception e)
			{
				_log.warning("Failed loading: " + task.getParams()[2]);
			}
		}
		else
			_log.warning("File Not Found: " + task.getParams()[2]);
	}
}