package com.hhly.sign;

import java.net.URLDecoder;
import java.util.Map;

import com.hhly.Key;
import com.hhly.MD5;
import com.hhly.Tools;
import com.hhly.Util.JsonUtil;
import com.hhly.mysql.MySqlUtil;
import com.hhly.mysql.get;

public class CheckSign {
//	static String getsign=null;
//	static String cpId=null;

	public static boolean verify(String json,String signtype,String key){
		 System.out.println("\nsigntype："+signtype+"\nKEY:"+key);
		
		Map<String, String> camap = JsonUtil.jsonToMap(json);
		
		   Map<String, String> params = SignUtils.paraFilter2(camap);
		   String	getsign=camap.get("sign");
		String	cpId=camap.get("cpId");
		System.out.println("MAP："+camap);
		 if (params.containsKey("sign")){
	            params.remove("sign");}
	        StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
	        SignUtils.buildPayParams(buf, params, false);
	        String preStr = buf.toString();
//	        String str=URLDecoder.decode(preStr);
	        System.out.println("\n预期签名字串："+preStr);
	        
	        if("RSA".equals(signtype)){
	        	if(RSA.verifyRSA256(preStr, getsign,Key.platformPublicKey,"utf-8")){
		               System.out.println("签名验证结果： SUCCESS");
		               return true;
		            }else {
		            	System.out.println("签名验证结果：FAIL");
		            	return false;
		            }
	        	
			}else {
				System.out.println("key:"+key);
				String sign=MD5.sign(preStr, "&key=" + key, "utf-8");
				System.out.println("接收到sign："+getsign);
				System.out.println("生成sign："+sign);
				if(getsign.equals(sign)||getsign.equals(Tools.shift(sign))){
					 System.out.println("签名验证结果： SUCCESS");
					 return true;
				}else {
	            	System.out.println("签名验证结果：FAIL");
	            	return false;
	            }
			}
			
	       
//			return false;
		   
	}
	
	
	public static boolean verify_refound_req(String json){
//		ObjectMapper mapper = new ObjectMapper();  
//		Map<String,Object> productMap = mapper.readValue(json);//转成map  
		
		return false;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
}
