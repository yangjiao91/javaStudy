package com.mock.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class Item {
	private String health ;
	private String status ;
	private String pri  ;
	private String rep  ;
	private String docs_count ;
	private String docs_deleted ;
	private String store_size ;
	private String pri_store_size  ;
	public Map<String, String> map = new HashMap<String, String>(); 
	public Map<String, String> map1 = new HashMap<String, String>();
	
	public void strSplit(){
		String str = "yellow open   .marvel-2015.05.27    1   1     188807            0    151.1mb        151.1mb ";
		str = str.replaceAll( "\\s+ ", " ");
		System.out.println(str); 
		String[] strSplit= str.split(" ");
		List<String> result = new ArrayList<String>() ; 
		result.add("hello");
		result.add("world");
		for (String tmp:strSplit)
		{
			tmp = tmp.trim();
			result.add(tmp);
		}

		map.put("health",result.get(0));
		map.put("status",result.get(1));
		map1.put("pri", result.get(2));
		map1.put("rep", result.get(3));
		
		System.out.println(result); 
	
	}

}