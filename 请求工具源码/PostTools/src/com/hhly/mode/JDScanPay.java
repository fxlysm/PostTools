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

public class JDScanPay {
	public static String NormalPost(String posturl, String service, String cpId, String totalFee, String cpOrderId,
			 String notifyUrl, String nonceStr, String tradeName,String currency,String orderType,String mchCreateIp,String key,String signtype) {
		Map<String, String> dd = get.GetValue(cpId);
		String sign2="",sign="";
		
		Map<String, String> cacheMap = new HashMap<String, String>();
		cacheMap.put("serviceName", service);
		cacheMap.put("cpId", cpId);
		cacheMap.put("nonceStr", nonceStr);
		cacheMap.put("cpOrderId", cpOrderId);
		cacheMap.put("notifyUrl", notifyUrl);
		cacheMap.put("totalFee", totalFee);
		cacheMap.put("orderType", orderType);
		cacheMap.put("currency", currency);
		cacheMap.put("mchCreateIp", mchCreateIp);
		cacheMap.put("body", Tools.getRandomString(8));


		// 过滤map
		Map<String, String> params = SignUtils.paraFilter2(cacheMap);
		if (params.containsKey("sign"))
			params.remove("sign");
		StringBuilder buf = new StringBuilder((params.size() + 1) * 10);

		SignUtils.buildPayParams(buf, params, false);
		String preStr = buf.toString();
		System.out.println("preStr: " + preStr);

//		String sign = MD5.sign(preStr, "&key=" + key, "utf-8");
//		System.out.println("sign: " + sign);
//		String sign2 = Tools.shift(sign);
//		System.out.println("Sign: " + sign2);

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
		String param = preStr + "&sign=" + sign;
		System.out.println("Sign Strings: " + param);
		String ss = PostTools.sendPost(posturl, param);
//		System.out.println(ss);
		
		return ss;
	}
}
