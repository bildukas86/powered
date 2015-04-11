package net.sf.l2j.gameserver.taskmanager.tasks;

import net.sf.l2j.gameserver.datatables.ClanTable;
import net.sf.l2j.gameserver.taskmanager.TaskManager;
import net.sf.l2j.gameserver.taskmanager.TaskManager.ExecutedTask;
import net.sf.l2j.gameserver.taskmanager.models.Task;
import net.sf.l2j.gameserver.taskmanager.models.TaskTypes;

public class TaskClansLadder extends Task
{
	public static final String NAME = "clans_ladder";
	
	@Override
	public String getName()
	{
		return NAME;
	}
	
	@Override
	public void onTimeElapsed(ExecutedTask task)
	{
		ClanTable.getInstance().refreshClansLadder(true);
	}
	
	@Override
	public void initializate()
	{
		super.initializate();
		TaskManager.addUniqueTask(NAME, TaskTypes.TYPE_GLOBAL_TASK, "1", "00:05:00", "");
	}
}