package com.elasticsearch ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;

public class ESClientMain{
	public static   Client client ;
	private static  TransportClient transportClient;
	public static void main(String args[]){
		transportClient = new TransportClient();
		client = transportClient
	//	.addTransportAddress(new InetSocketTransportAddress("172.16.12.111", 9300))
        .addTransportAddress(new InetSocketTransportAddress("172.16.12.110", 9300)) ;
		SearchResponse response = client
				.prepareSearch()
				.setQuery(QueryBuilders.queryStringQuery("history"))
//				.addHighlightedField("emp")
//				.setHighlighterPreTags("<span style=\"color:red\">")
//				.setHighlighterPreTags("</span>")
				.execute()
				.actionGet();
		
		JSONObject jsonObject = JSONObject.fromObject(response.toString());
		JSONArray jsonArray = jsonObject.getJSONObject("hits").getJSONArray("hits");
		List<String> list = new ArrayList();
		for (Object obj:jsonArray ){
			list.add(obj.toString());
		}
//        List<String> list =  JSONArray.toList(jsonArray);  
        
	} 
	public static  Client conectionES(){
		transportClient = new TransportClient();
		client = transportClient
	//	.addTransportAddress(new InetSocketTransportAddress("172.16.12.111", 9300))
        .addTransportAddress(new InetSocketTransportAddress("172.16.12.110", 9300)) ;
		
		return client ;
	}
	
	@Test
	public void testSearch(){
		client = conectionES();
		SearchResponse response = client
				.prepareSearch()
				.setQuery(QueryBuilders.queryStringQuery("history"))
//				.addHighlightedField("emp")
//				.setHighlighterPreTags("<span style=\"color:red\">")
//				.setHighlighterPreTags("</span>")
				.execute()
				.actionGet();
//		JSONObject jsonArray = JSONObject.fromObject(response);  
//        List<String> list =  JSONArray.toList(jsonArray);  
		System.out.println(response);
        
	} 
	
	
	@Test
	public void testQueryString(){
		client = conectionES();
		SearchResponse response = client.prepareSearch()
				.setQuery(QueryBuilders.queryStringQuery("emp"))
				.execute()
				.actionGet();
		System.out.println(response);
		JSONObject jsonObject = JSONObject.fromObject(response);
		JSONArray jsonArray = jsonObject.getJSONObject("hits").getJSONArray("hits");
		System.out.println(jsonArray);
		for(int i=0;i<jsonArray.size();i++){
			jsonObject = jsonArray.getJSONObject(i);
			System.out.println(jsonObject);
		}

	} 
	
	@Test
	public void testMulitSearch(){
		client = conectionES();
		SearchResponse response = client.prepareSearch(".marvel-2015.05.19")
		        .setTypes()
		        .execute()
				.actionGet();
		//JSONObject jsonObject = JSONObject.fromObject(response);
		//jsonObject  = jsonObject.getJSONObject("hits").getJSONArray("hits").getJSONObject(2);
		//String id = jsonObject.getString("id");
		//String index = jsonObject.getString("index");
		//String type = jsonObject.getString("type");
		//String source = jsonObject.getString("source");
		System.out.println(response);
		
	}
	
	@Test
	public void testUpset() throws Exception, Exception{
		client = conectionES();
		IndexRequest indexRequest = new IndexRequest("dept", "employee1", "1")
				.source(XContentFactory.jsonBuilder().startObject()
						.field("empname", "emp1").endObject()
				);
		UpdateRequest updateRequest = new UpdateRequest("dept", "employee1", "1")
        	.doc(XContentFactory.jsonBuilder()
        			.startObject().field("empname", "emp1").endObject())
        	.upsert(indexRequest);              
		client.update(updateRequest).get();
		
		System.out.println("end");
		
	}
}