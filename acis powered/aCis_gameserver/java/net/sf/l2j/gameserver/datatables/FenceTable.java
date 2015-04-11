package net.sf.l2j.gameserver.datatables;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.actor.instance.L2FenceInstance;

public class FenceTable
{
	public static ArrayList<L2FenceInstance> fences = new ArrayList<>();
	
	public static void addFence(L2FenceInstance fence)
	{
		fences.add(fence);
	}
	
	public static ArrayList<L2FenceInstance> getAllFences()
	{
		return fences;
	}
	
	public static void removeFence(L2FenceInstance fence)
	{
		if (fences.contains(fence))
			fences.remove(fence);
	}
	
	public static boolean canSeeTarget(L2Object source, int x, int y)
	{
		Collection<L2Object> objects = source.getKnownList().getKnownObjects();
		
		for (L2Object obj : objects)
		{
			if (obj instanceof L2FenceInstance)
			{
				L2FenceInstance fence = (L2FenceInstance) obj;
				
				if (fence.isBetween(source.getX(), source.getY(), x, y))
					return false;
			}
		}
		
		return true;
	}
	
	public static boolean canSeeTarget(int x, int y, int tx, int ty)
	{
		for (L2FenceInstance fence : fences)
		{
			if (fence.isBetween(x, y, tx, ty))
				return false;
		}
		
		return true;
	}
}