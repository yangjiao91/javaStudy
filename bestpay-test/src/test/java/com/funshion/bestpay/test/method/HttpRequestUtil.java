package com.funshion.bestpay.test.method ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;


public class HttpRequestUtil{

	public JSONObject testUtil(String url,Map<String, String> map) throws Exception{
		LinkedHashMap<String, String> lmap = sortMap(map) ;
		String param = join(lmap,true);
		String  sign = signMD5(lmap,"987654321") ;
		param = param+"&sign="+sign ;
		String result = sendGet(url, param);
		JSONObject resultObj = JSONObject.fromObject(result)  ;  
		return resultObj;
	}
	
	public  String sendGet(String url,String param){
		String result = "" ;
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
				result += line;
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
	public  LinkedHashMap<String, String>  sortMap(Map<String, String> map){
        TreeMap<String, String> tm =  new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
        tm.putAll(map);
        return new LinkedHashMap<String, String>(tm);
    }
	
    public  String join(Map<String, String> map,boolean encode) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> e : map.entrySet()) {
            append(sb, e.getKey(), e.getValue(),encode);
        }
       
        return sb.toString();
    }
    
    private  void append(StringBuilder sb,String key,String value,boolean encode){
        if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
            return;
        }
        
        try {
            if(sb.length()>0){
                sb.append("&");
            }
            sb.append(key).append("=");
            if(encode){
                sb.append(URLEncoder.encode(value, "UTF-8"));
            }else{
                sb.append(value);
            }
        } catch (UnsupportedEncodingException e1) {
            throw new RuntimeException("不支持的编码", e1);
        }
    }
    public  String signMD5(LinkedHashMap<String, String> map,String privateKey) throws Exception {
        
        String toMd5=join(map,false);
        if(privateKey!=null){
            toMd5=toMd5+"&key="+privateKey;
        }
        return md5Encode(toMd5).toUpperCase();
    }
	public  String md5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return  hexValue.toString();
    }
	
	public  String getRandomString(int length) { //length表示生成字符串的长度
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());  
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 }   
}