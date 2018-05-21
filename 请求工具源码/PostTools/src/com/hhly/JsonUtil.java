package com.hhly;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {
	static JSONObject jsonObj;

	public static String ReadJsonObj(String key, String jsonString) {

		jsonObj = new JSONObject(jsonString);
		System.out.println(jsonObj.get(key));

		return (String) jsonObj.get(key);
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

	public static String ReadJDJsonObj(String key, String jsonString) {

		JSONObject json = new JSONObject(jsonString);
		// JSONObject json = JSONObject.fromObject(str);
		// logger.debug("json: "+json);
		JSONObject stateJson = json.getJSONObject("stateVO");
		int code = Integer.parseInt(stateJson.getString(key));

		return (String) jsonObj.get(key);
	}

	public static void OpenBrow(String url) {
		java.net.URI uri = java.net.URI.create(url);
		java.awt.Desktop dp = java.awt.Desktop.getDesktop();
		if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
			try {
				dp.browse(uri);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 获取系统默认浏览器打开链接
		}
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
		        jsonBuffer.append("\""+key+"\":\""+value+"\"");
		        if(it.hasNext()){
		             jsonBuffer.append(",");
		        }
		    }
		    jsonBuffer.append("}");
		    return jsonBuffer.toString();
		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String object = "{\"code\":\"0000\",\"desc\":\"成功\",\"tradeType\":\"V\",\"transId\":\"1139718596696793\",\"totalFee\":\"1\",\"currency\":\"CNY\"}";
		ReadJsonObj("transId", object);
	}

}
