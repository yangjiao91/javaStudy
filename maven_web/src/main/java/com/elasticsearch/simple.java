package com.elasticsearch;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
abstract

public class simple{
	private static TransportClient transportClient;
	
	public static  Client connection(String host,int port){
		transportClient = new TransportClient();
		Client client = transportClient
				.addTransportAddress(new InetSocketTransportAddress(host, port)) ;
		return client;
	}
	
	public static void main(String args[]){
		Client client = connection("172.16.12.110",9300);
		SearchResponse response ;
			response = client.prepareSearch("library")
//					.setQuery(QueryBuilders.queryStringQuery("history"))
					.execute()
					.actionGet();
			System.out.println(format(response.toString()));
	}
	
	public static List<String> format(String response){
		JSONObject jsonObject = JSONObject.fromObject(response);
		JSONArray jsonArray = jsonObject.getJSONObject("hits").getJSONArray("hits");
		System.out.println(jsonArray.toString());
		List<String> list = new ArrayList();
		for (Object obj:jsonArray ){
			list.add(obj.toString());
		}
		return list;
	}
	
}