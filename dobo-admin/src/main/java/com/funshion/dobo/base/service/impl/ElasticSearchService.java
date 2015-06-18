package com.funshion.dobo.base.service.impl;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

import com.funshion.dobo.base.service.AbstractService;


@Service
public class ElasticSearchService extends AbstractService {
	private TransportClient transportClient;
	
	public  Client connection(String host,int port){
		transportClient = new TransportClient();
		Client client = transportClient
				.addTransportAddress(new InetSocketTransportAddress(host, port)) ;
		return client;
	}
	
	public String searchES(HttpServletRequest request){
		Client client = connection("172.16.12.110",9300);
		SearchResponse response ;
			response = client.prepareSearch()
					.setQuery(QueryBuilders.queryStringQuery(request.getParameter("keyword")))
					.execute()
					.actionGet();
			return format(response.toString());
	}
	
	public String format(String response){
		JSONObject jsonObject = JSONObject.fromObject(response);
		JSONArray jsonArray = jsonObject.getJSONObject("hits").getJSONArray("hits");
		return jsonArray.toString();
	}
	
}