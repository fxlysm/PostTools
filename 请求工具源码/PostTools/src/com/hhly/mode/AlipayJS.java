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

public class AlipayJS {
	public static String NormalPost(String posturl, String service, String cpId, String totalFee, String cpOrderId,
			String mchCreateIp, String notifyUrl, String body, String nonceStr, String buyerLogonId, String buyerId,
			String key,String signtype) {
//		Map<String, String> dd = get.GetValue(cpId);
		String sign2="",sign="";
//		String paykey=dd.get("pay_key");
		String body2 = "";
		try {
			body2 = URLEncoder.encode(body, "UTF-8");
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
		// cacheMap.put("buyerLogonId", buyerLogonId);
		cacheMap.put("buyerId", buyerId);
		

		// 过滤map
		Map<String, String> params = SignUtils.paraFilter2(cacheMap);
		if (params.containsKey("sign"))
			params.remove("sign");
		StringBuilder buf = new StringBuilder((params.size() + 1) * 10);

		SignUtils.buildPayParams(buf, params, false);
		String preStr = buf.toString();

		System.out.println("原字符串: " + preStr);

//		String sign = MD5.sign(preStr, "&key=" + key, "utf-8");
//		System.out.println("sign: " + sign);
//		String sign2 = Tools.shift(sign);
		// System.out.println("Sign: " + sign2);

		Map<String, String> map2 = new HashMap<>();
		map2.put("serviceName", service);
		map2.put("cpId", cpId);
		map2.put("cpOrderId", cpOrderId);
		map2.put("mchCreateIp", mchCreateIp);
		map2.put("body", body2);
		map2.put("totalFee", totalFee);
		map2.put("notifyUrl", notifyUrl);
		map2.put("nonceStr", nonceStr);
		// cacheMap.put("buyerLogonId", buyerLogonId);
		map2.put("buyerId", buyerId);

		// 过滤map
		Map<String, String> params2 = SignUtils.paraFilter2(map2);
		if (params2.containsKey("sign"))
			params2.remove("sign");
		StringBuilder buf2 = new StringBuilder((params2.size() + 1) * 10);

		SignUtils.buildPayParams(buf2, params2, false);
		String preStr2 = buf2.toString();

		System.out.println("转后的字符串: " + preStr2);

		if("MD5".equals(signtype)){
			
			 sign2 = MD5.sign(preStr, "&key=" + key, "utf-8");
			sign = Tools.shift(sign2);
		}else {
			
			try {
				sign =URLEncoder.encode(RSA.signRSA256(preStr, key, "utf-8"),"UTF-8") ;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
		String param2 = preStr2 + "&sign=" + sign;
		System.out.println("Sent Post: " + param2);
		String ss = PostTools.sendPost(posturl, param2);
		return ss;
	}
}
