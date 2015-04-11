package net.sf.l2j.gameserver.taskmanager.tasks;

import net.sf.l2j.gameserver.Shutdown;
import net.sf.l2j.gameserver.taskmanager.TaskManager.ExecutedTask;
import net.sf.l2j.gameserver.taskmanager.models.Task;

/**
 * @author Layane
 */
public final class TaskRestart extends Task
{
	public static final String NAME = "restart";
	
	/*
	 * (non-Javadoc)
	 * @see net.sf.l2j.gameserver.tasks.Task#getName()
	 */
	@Override
	public String getName()
	{
		return NAME;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.sf.l2j.gameserver.tasks.Task#onTimeElapsed(net.sf.l2j.gameserver.tasks.TaskManager.ExecutedTask)
	 */
	@Override
	public void onTimeElapsed(ExecutedTask task)
	{
		Shutdown handler = new Shutdown(Integer.valueOf(task.getParams()[2]), true);
		handler.start();
	}
	
}
