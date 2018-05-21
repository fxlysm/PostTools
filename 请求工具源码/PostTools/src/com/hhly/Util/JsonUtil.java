package com.hhly.Util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

public class JsonUtil {
	private static Gson gson = null;
	
	static {
		if (gson == null) {
			gson = new Gson();
		}
	}
	/**
	 * 将json格式转换成map对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, String> jsonToMap(String jsonStr) {
		
		
		System.out.println("jsonStr"+jsonStr);
		Map<String, String> objMap = null;
		if (gson != null) {
			java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<Map<String, String>>() {
			}.getType();
			objMap = gson.fromJson(jsonStr, type);
		}
		return objMap;
	}
	


	  public static String getJsonValue(String JsonString, String JsonId) {

	        String JsonValue = "";
	        if (JsonString == null || JsonString.trim().length() < 1) {
	            return null;
	        }
	        try {
	            JSONObject obj1 = new JSONObject(JsonString);
	            JsonValue = (String) obj1.getString(JsonId);
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        return JsonValue;

	    }
	
	  
	  public static String toJson(Map<String,String> map){
		    Set<String> keys = map.keySet();
		    String key = "";
		    String value = "";
		    StringBuffer jsonBuffer = new StringBuffer();
		    jsonBuffer.append("{");    
		    for(Iterator<String> it = keys.iterator();it.hasNext();){
		        key =  (String)it.next();
		        value = map.get(key);
		        jsonBuffer.append(key+":"+value);
		        if(it.hasNext()){
		             jsonBuffer.append(",");
		        }
		    }
		    jsonBuffer.append("}");
		    return jsonBuffer.toString();
		}
}
