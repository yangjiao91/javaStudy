package com.mock.test;

import java.util.ArrayList;
import java.util.List;

public class ElasticSearchIndex {
	
	public String health ="";
	public String status ="";
	public String index  ="";
	public String pri="";
	public String rep="";
	public String docs_count="";
	public String docs_deleted ="";
	public String store_size ="";
	public String pri_store_size="" ;
	
	public void strSplit(String str){
		str = str.replaceAll( "\\s+ ", " ");
		String[] strSplit= str.split(" ");
		List<String> result = new ArrayList<String>() ; 
		for (String tmp:strSplit)
		{
			tmp = tmp.trim();
			result.add(tmp);
		}
		this.health = result.get(0);
		this.status = result.get(1);
		this.index = result.get(2);
		this.pri = result.get(3);
		this.rep = result.get(4);
		this.docs_count = result.get(5);
		this.docs_deleted = result.get(6);
		this.store_size = result.get(7);
		this.pri_store_size = result.get(8);
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getPri() {
		return pri;
	}

	public void setPri(String pri) {
		this.pri = pri;
	}

	public String getRep() {
		return rep;
	}

	public void setRep(String rep) {
		this.rep = rep;
	}

	public String getDocs_count() {
		return docs_count;
	}

	public void setDocs_count(String docs_count) {
		this.docs_count = docs_count;
	}

	public String getDocs_deleted() {
		return docs_deleted;
	}

	public void setDocs_deleted(String docs_deleted) {
		this.docs_deleted = docs_deleted;
	}

	public String getStore_size() {
		return store_size;
	}

	public void setStore_size(String store_size) {
		this.store_size = store_size;
	}

	public String getPri_store_size() {
		return pri_store_size;
	}

	public void setPri_store_size(String pri_store_size) {
		this.pri_store_size = pri_store_size;
	}
	
	
	
}
