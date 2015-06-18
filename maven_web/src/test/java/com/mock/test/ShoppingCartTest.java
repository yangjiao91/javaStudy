package com.mock.test;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ShoppingCartTest{
	public ShoppingCart cart = null;
	public Store storeMock = null;
	
	@Before
	public void initialize(){
		cart = new ShoppingCart();
		storeMock = EasyMock.createMock(Store.class);
		cart.setStore(storeMock);
	}
	
	@Test
	public void testShoppingCart(){
		
		EasyMock.expect(storeMock.getPrice("Mead Spiral Bound Notebook, College Rule")).andReturn(5);
		EasyMock.expect(storeMock.getPrice("Kindle Fire HD")).andReturn(8);
		
		EasyMock.replay(storeMock);
		
		Item item1 = new Item("Mead Spiral Bound Notebook, College Rule", 3);
		Item item2 = new Item("Kindle Fire HD",1);
		
		cart.addItem(item1);
		cart.addItem(item2);
		
		double total = cart.calculateTotal();
		
		System.out.println("Total price of items in shopping cart: $"+total);
		Assert.assertEquals("Result", 23.0, total);
		
	}
	
	@After
	public void cleanup(){
		cart = null ;
		storeMock = null;
	}
	

}