package net.sf.l2j.gameserver.model.multisell;

public class Ingredient
{
	private int _itemId, _itemCount, _enchantmentLevel;
	private boolean _isTaxIngredient, _maintainIngredient;
	
	public Ingredient(int itemId, int itemCount, boolean isTaxIngredient, boolean maintainIngredient)
	{
		this(itemId, itemCount, 0, isTaxIngredient, maintainIngredient);
	}
	
	public Ingredient(int itemId, int itemCount, int enchantmentLevel, boolean isTaxIngredient, boolean mantainIngredient)
	{
		setItemId(itemId);
		setItemCount(itemCount);
		setEnchantmentLevel(enchantmentLevel);
		setIsTaxIngredient(isTaxIngredient);
		setMaintainIngredient(mantainIngredient);
	}
	
	public Ingredient(Ingredient e)
	{
		_itemId = e.getItemId();
		_itemCount = e.getItemCount();
		_enchantmentLevel = e.getEnchantmentLevel();
		_isTaxIngredient = e.isTaxIngredient();
		_maintainIngredient = e.getMaintainIngredient();
	}
	
	public void setItemId(int itemId)
	{
		_itemId = itemId;
	}
	
	public int getItemId()
	{
		return _itemId;
	}
	
	public void setItemCount(int itemCount)
	{
		_itemCount = itemCount;
	}
	
	public int getItemCount()
	{
		return _itemCount;
	}
	
	public void setEnchantmentLevel(int enchantmentLevel)
	{
		_enchantmentLevel = enchantmentLevel;
	}
	
	public int getEnchantmentLevel()
	{
		return _enchantmentLevel;
	}
	
	public void setIsTaxIngredient(boolean isTaxIngredient)
	{
		_isTaxIngredient = isTaxIngredient;
	}
	
	public boolean isTaxIngredient()
	{
		return _isTaxIngredient;
	}
	
	public void setMaintainIngredient(boolean maintainIngredient)
	{
		_maintainIngredient = maintainIngredient;
	}
	
	public boolean getMaintainIngredient()
	{
		return _maintainIngredient;
	}
}
