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


/***
 *  微信公众号支付 ---支付类
 * @author lambert
 *
 */
public class WxoatradePay {

	public static String NormalPost(String posturl, String service, String cpId, String totalFee, String cpOrderId,
			String mchCreateIp, String notifyUrl, String body, String nonceStr, String subOpenid,String callbackUrl,String subAppid,String key,String signtype) {
		Map<String, String> dd = get.GetValue(cpId);
		String sign2="",sign="";
		Map<String, String> cacheMap = new HashMap<String, String>();
		cacheMap.put("serviceName", service);
		cacheMap.put("cpId", cpId);
		cacheMap.put("cpOrderId", cpOrderId);
		cacheMap.put("mchCreateIp", mchCreateIp);
		cacheMap.put("body", body);
		cacheMap.put("totalFee", totalFee);
		cacheMap.put("notifyUrl", notifyUrl);
		cacheMap.put("nonceStr", nonceStr);
		cacheMap.put("subOpenid", subOpenid);
		cacheMap.put("callbackUrl", callbackUrl);
		cacheMap.put("subAppid", subAppid);


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
	
	
	
	public static String NoNeed_parameter_post(String posturl, String service, String cpId, String totalFee, String cpOrderId,
			String mchCreateIp, String notifyUrl, String body, String nonceStr, String subOpenid,String callbackUrl,String subAppid,String key
			,String inputCharset,String signType,String currency,String attach,String limitCreditPay,String platform
			
			) {

		Map<String, String> cacheMap = new HashMap<String, String>();
		cacheMap.put("serviceName", service);
		cacheMap.put("cpId", cpId);
		cacheMap.put("cpOrderId", cpOrderId);
		cacheMap.put("mchCreateIp", mchCreateIp);
		cacheMap.put("body", body);
		cacheMap.put("totalFee", totalFee);
		cacheMap.put("notifyUrl", notifyUrl);
		cacheMap.put("nonceStr", nonceStr);
		cacheMap.put("subOpenid", subOpenid);
		cacheMap.put("callbackUrl", callbackUrl);
		cacheMap.put("subAppid", subAppid);


		cacheMap.put("inputCharset", inputCharset);
		cacheMap.put("signType", signType);
		cacheMap.put("currency", currency);
		cacheMap.put("attach", attach);
		cacheMap.put("limitCreditPay", limitCreditPay);
		cacheMap.put("platform", platform);
		// 过滤map
		Map<String, String> params = SignUtils.paraFilter2(cacheMap);
		if (params.containsKey("sign"))
			params.remove("sign");
		StringBuilder buf = new StringBuilder((params.size() + 1) * 10);

		SignUtils.buildPayParams(buf, params, false);
		String preStr = buf.toString();
		System.out.println("preStr: " + preStr);

		String sign = MD5.sign(preStr, "&key=" + key, "utf-8");
		System.out.println("sign: " + sign);
		String sign2 = Tools.shift(sign);
		System.out.println("Sign: " + sign2);

		String param = preStr + "&sign=" + sign2;
		System.out.println("Sign Strings: " + param);
		String ss = PostTools.sendPost(posturl, param);
//		System.out.println(ss);
		
		return ss;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	

	}

}
