package com.mock.test ; 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;


public class HttpRequestTest{
	String url = "http://172.16.12.110:9200/_cat/indices" ;
	String  param = "v";
	ElasticSearchIndex esIndex = new ElasticSearchIndex() ;
	private Map<String, Object> map = new HashMap<String, Object>();
	
	@Test
	public void main(){
//		List<ElasticSearchIndex> list =  findESListAutoPaging() ;
		String result = sendGet("http://172.16.12.110:9200/_status?");
		System.out.println(result);
//		JSONArray array = JSONArray.fromObject(result);
		JSONObject jsonObject = JSONObject.fromObject(result).getJSONObject("indices");
		Set<String> set = jsonObject.keySet();
		for (String key : set) {
			System.out.println(key);
			jsonObject.getJSONObject(key).getJSONObject("index").get("size_in_bytes");
			System.out.println(jsonObject.getJSONObject(key).getJSONObject("docs").get("num_docs"));
		}
	}
	
//	@Test
//	public void findESListAutoPaging() {
//		List<String> list = sendGet(url,param);
//		List<ElasticSearchIndex> result = new ArrayList<ElasticSearchIndex>();
//		for(int i = 0;i<list.size();i++){
//			System.out.println(list.get(i));
//			esIndex.strSplit(list.get(i));
//
//			result.add(esIndex);
//		}
//
//	}
	
		@Test
		public void param() throws Exception{
//			response.setContentType("text/html;charset=utf-8");
			JSONObject object = new JSONObject();
			object.element("a", "hello");
			JSONObject json = JSONObject.fromObject(object); 
//			response.getWriter().write(json.toString());
//			response.getWriter().flush();
			System.out.println(object);
		
	}

	public String sendGet(String url){
		BufferedReader in = null;
		String result = "" ;
		try{
			String urlNameString = url;
			URL realUrl = new URL(urlNameString);
			URLConnection connection = realUrl.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.connect();
			
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while((line=in.readLine()) != null){
				result += line ;
			}
			
		}catch (Exception e){
			System.out.println("发送get请求异常: " + e);
			e.printStackTrace();
		}
		finally{
			try{
				if(in != null){
					in.close();
				}
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
		return result;
	}
}