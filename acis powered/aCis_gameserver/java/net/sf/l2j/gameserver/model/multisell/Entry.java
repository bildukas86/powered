package net.sf.l2j.gameserver.model.multisell;

import java.util.ArrayList;
import java.util.List;

public class Entry
{
	private int _entryId;
	
	private final List<Ingredient> _products = new ArrayList<>();
	private final List<Ingredient> _ingredients = new ArrayList<>();
	
	public void setEntryId(int entryId)
	{
		_entryId = entryId;
	}
	
	public int getEntryId()
	{
		return _entryId;
	}
	
	public void addProduct(Ingredient product)
	{
		_products.add(product);
	}
	
	public List<Ingredient> getProducts()
	{
		return _products;
	}
	
	public void addIngredient(Ingredient ingredient)
	{
		_ingredients.add(ingredient);
	}
	
	public List<Ingredient> getIngredients()
	{
		return _ingredients;
	}
}
