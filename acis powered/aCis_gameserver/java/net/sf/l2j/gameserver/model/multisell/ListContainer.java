package net.sf.l2j.gameserver.model.multisell;

import java.util.ArrayList;
import java.util.List;

public class ListContainer
{
	private int _listId;
	private boolean _applyTaxes = false;
	private boolean _maintainEnchantment = false;
	
	List<Entry> _entries;
	
	public ListContainer()
	{
		_entries = new ArrayList<>();
	}
	
	public void setListId(int listId)
	{
		_listId = listId;
	}
	
	public int getListId()
	{
		return _listId;
	}
	
	public void setApplyTaxes(boolean applyTaxes)
	{
		_applyTaxes = applyTaxes;
	}
	
	public boolean getApplyTaxes()
	{
		return _applyTaxes;
	}
	
	public void setMaintainEnchantment(boolean maintainEnchantment)
	{
		_maintainEnchantment = maintainEnchantment;
	}
	
	public boolean getMaintainEnchantment()
	{
		return _maintainEnchantment;
	}
	
	public void addEntry(Entry e)
	{
		_entries.add(e);
	}
	
	public List<Entry> getEntries()
	{
		return _entries;
	}
}
