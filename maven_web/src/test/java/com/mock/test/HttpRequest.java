package com.mock.test ;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
//import java.net.URLDecoder;
//import java.net.URLEncoder;

public class HttpRequest{
	
	public  List sendGet(String url,String param){
		String result = "" ;
		List<String> list = new ArrayList<String>();
		BufferedReader in = null;
		try{
			String urlNameString = url+ "?" + param;
			URL realUrl = new URL(urlNameString);
			URLConnection connection = realUrl.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.connect();
			
			Map<String, List<String>> map = connection.getHeaderFields();
			
			for (String key : map.keySet()){
				System.out.println(key+"--->"+map.get(key));
			}
			
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;

			while((line=in.readLine()) != null){
//				String str = line + "\r\n";
				list.add(line);
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
		return list;
	}
	public  String sendPost(String url , String param){
		PrintWriter out = null ;
		BufferedReader in = null;
		String result = "" ;
		try{
			String urlNameString = url+ "?" + param;
			URL realUrl = new URL(urlNameString);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			
			out = new PrintWriter(conn.getOutputStream());
			out.print(param);
			out.flush();
			
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while((line=in.readLine()) != null){
				result += line;
			}
			
		}catch (Exception e){
			System.out.println("发送post请求异常: " + e);
			e.printStackTrace();
		}
		finally{
			try{
				if(out != null){
					out.close();
				}
				if(in != null){
					in.close();
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
		return result;
	}
	
}