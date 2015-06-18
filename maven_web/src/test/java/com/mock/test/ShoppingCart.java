package com.mock.test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart{
	
	public String name;
	public Store store = null;
	public List<Item> items = new ArrayList<Item>();
	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public List<Item> getItems(){
		return items;
	}
	public void setItems(List<Item> items){
		this.items = items;
	}
	public void addItem(Item item){
		items.add(item);
	}
	public void setStore(Store store){
		this.store = store;
	}
	public Store getStore(){
		return (this.store);
	}
	public Double calculateTotal(){
		
		Double total = 0.0 ;
		for (Item item : this.items){
			total += (store.getPrice(item.getName())*item.getQuantity());
		}
		DecimalFormat decim = new DecimalFormat("0.00");
		Double price = Double.parseDouble(decim.format(total));
		return price ;
	}



}

