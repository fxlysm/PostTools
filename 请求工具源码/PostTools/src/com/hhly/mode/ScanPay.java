package com.hhly.mode;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.hhly.MD5;
import com.hhly.PostTools;
import com.hhly.Tools;
import com.hhly.mysql.get;
import com.hhly.sign.RSA;
import com.hhly.sign.SignUtils;



public class ScanPay {
	
	public static String NormalPost(String posturl, String service, String cpId, String totalFee, String cpOrderId,
			String  mchCreateIp, String notifyUrl, String body, String nonceStr,String privatekey,String signtype) {
		
		String sign2="",sign="";
//		String key=dd.get("pay_key");
		
		String  body2="";
		try {
			  body2=URLEncoder.encode(body,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String, String> cacheMap = new HashMap<String, String>();
		cacheMap.put("serviceName", service);
		cacheMap.put("cpId", cpId);
		cacheMap.put("cpOrderId", cpOrderId);
		cacheMap.put("mchCreateIp", mchCreateIp);
		cacheMap.put("body", body);
		cacheMap.put("totalFee", totalFee);
		cacheMap.put("notifyUrl", notifyUrl);
		cacheMap.put("nonceStr", nonceStr);
//		cacheMap.put("appId", appId);
//		cacheMap.put("deviceInfo", "2665");

		// 过滤map
		Map<String, String> params = SignUtils.paraFilter2(cacheMap);
		if (params.containsKey("sign"))
			params.remove("sign");
		StringBuilder buf = new StringBuilder((params.size() + 1) * 10);

		SignUtils.buildPayParams(buf, params, false);
		String preStr = buf.toString();
		
		System.out.println("原字符串: " + preStr);
		if("RSA".equals(signtype)){
			try {
				sign =URLEncoder.encode(RSA.signRSA256(preStr, privatekey, "utf-8"),"UTF-8") ;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if ("MD5".equals(signtype)) {
			 sign = MD5.sign(preStr, "&key=" + privatekey, "utf-8");
		}
		
//		Map<String, String> dd = get.GetValue(cpId);
//		if("MD5".equals(dd.get("sign_type"))){
//			
//			
//			sign = Tools.shift(sign2);
//		}else {
//			
//			
//		}
	
		
		System.out.println("sign: " + sign);

//		System.out.println("Sign: " + sign2);
	/***
	 * 上面主要是生成签名	
	 */
		
		Map<String, String> map2=new HashMap<>();
		
		map2.put("serviceName", service);
		map2.put("cpId", cpId);
		map2.put("cpOrderId", cpOrderId);
		map2.put("mchCreateIp", mchCreateIp);
		map2.put("body", body2);
		map2.put("totalFee", totalFee);
		map2.put("notifyUrl", notifyUrl);
		map2.put("nonceStr", nonceStr);
		
		
		// 过滤map
		Map<String, String> params2 = SignUtils.paraFilter2(map2);
				if (params2.containsKey("sign"))
					params2.remove("sign");
				StringBuilder buf2 = new StringBuilder((params2.size() + 1) * 10);
				
			
				SignUtils.buildPayParams(buf2, params2, false);
				String preStr2 = buf2.toString();
		
				System.out.println("转后的字符串: " + preStr2);

		String param2 = preStr2 + "&sign=" + sign;
		System.out.println("Sent Post: " + param2);
//		String ss = PostTools.sendPost(posturl, param2);
		String ss=PostTools.httpRequest(posturl, "POST", param2);
		
//		System.out.println(ss);
		
		return ss;
	}
}
