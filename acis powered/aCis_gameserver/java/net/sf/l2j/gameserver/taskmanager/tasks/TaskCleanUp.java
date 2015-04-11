package net.sf.l2j.gameserver.taskmanager.tasks;

import net.sf.l2j.gameserver.taskmanager.TaskManager.ExecutedTask;
import net.sf.l2j.gameserver.taskmanager.models.Task;

/**
 * @author Tempy
 */
public final class TaskCleanUp extends Task
{
	public static final String NAME = "CleanUp";
	
	@Override
	public String getName()
	{
		return NAME;
	}
	
	@Override
	public void onTimeElapsed(ExecutedTask task)
	{
		System.runFinalization();
		System.gc();
	}
}
