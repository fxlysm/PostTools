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



public class CloseOrder {
	public static String ClosePost(String posturl,String serviceName,String cpId,String cpOrderId,String nonceStr,String inputCharset,String signType,String key,String signtype){
		Map<String, String> cacheMap = new HashMap<String, String>();
	
		String sign2="",sign="";
		cacheMap.put("serviceName", serviceName);
		cacheMap.put("cpId", cpId);
		cacheMap.put("inputCharset", inputCharset);
		cacheMap.put("signType", signType);
		cacheMap.put("cpOrderId", cpOrderId);//商户退款单号
		cacheMap.put("nonceStr", nonceStr);

		
		// 过滤map
				Map<String, String> params = SignUtils.paraFilter2(cacheMap);
				if (params.containsKey("sign"))
					params.remove("sign");
				StringBuilder buf = new StringBuilder((params.size() + 1) * 10);

				SignUtils.buildPayParams(buf, params, false);
				String preStr = buf.toString();
				System.out.println("preStr: "+preStr);
				
				if ("RSA".equals(signType)) {
					try {
						sign =URLEncoder.encode(RSA.signRSA256(preStr, key, "utf-8"),"UTF-8") ;
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					 sign2 = MD5.sign(preStr, "&key=" + key, "utf-8");
						sign = Tools.shift(sign2);
				}
				
				



		    	String param=preStr+"&sign="+sign;
		    	System.out.println("param: "+param);
		    	String ss=PostTools.sendPost(posturl, param);
//		    	System.out.println(ss);
		    	return ss;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
