package com.defray.mode;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import com.hhly.PostTools;
import com.hhly.sign.RSA;
import com.hhly.sign.SignUtils;


public class Yilian {
	/***
	 * 代付接口类
	 * @author dell
	 *
	 */

		/**
		 * 单笔带付
		 * @param posturl
		 * @param serviceName
		 * @param cpId
		 * @param nonceStr
		 * @param cpOrderId
		 * @param notifyUrl
		 * @param tradePayeeJson
		 * @param attach
		 * @param timeStart
		 * @param inputCharset
		 * @param signType
		 * @param key
		 * @return
		 */
		public static String PayPost(String posturl, String serviceName, String cpId, String nonceStr, String cpOrderId,
				 String notifyUrl, String tradePayeeJson,String attach,String timeStart,String inputCharset,String signType,String key) {

			String sign2="",sign="";
			Map<String, String> cacheMap = new HashMap<String, String>();
			cacheMap.put("serviceName", serviceName);
			cacheMap.put("cpId", cpId);
			cacheMap.put("nonceStr", nonceStr);
			cacheMap.put("cpOrderId", cpOrderId);
			cacheMap.put("notifyUrl", notifyUrl);
			cacheMap.put("tradePayeeJson", tradePayeeJson);
			//以下为非必传
			cacheMap.put("attach", attach);
			cacheMap.put("timeStart", timeStart);
			cacheMap.put("inputCharset", inputCharset);
			cacheMap.put("signType", signType);

			// 过滤map
			Map<String, String> params = SignUtils.paraFilter2(cacheMap);
			if (params.containsKey("sign"))
				params.remove("sign");
			StringBuilder buf = new StringBuilder((params.size() + 1) * 10);

			SignUtils.buildPayParams(buf, params, false);
			String preStr = buf.toString();
			System.out.println("preStr: " + preStr);

				
				try {
					sign =URLEncoder.encode(RSA.signRSA256(preStr, key, "utf-8"),"UTF-8") ;
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			

				String param2 = preStr + "&sign=" + sign;
				System.out.println("Sent Post: " + param2);
				String ss=PostTools.httpRequest(posturl, "POST", param2);		
			return ss;
		}
		

		
		/***
		 * 代付查询接口类
		 * @param posturl
		 * @param serviceName
		 * @param cpId
		 * @param nonceStr
		 * @param cpOrderId
		 * @param transId
		 * @param inputCharset
		 * @param signType
		 * @param key
		 * @return
		 */
		public static String QueryPost(String posturl, String serviceName, String cpId, String nonceStr, String cpOrderId,String transId,String batchNo,
				 String inputCharset,String signType,String key) {		
			
			String sign2="",sign="";
			Map<String, String> cacheMap = new HashMap<String, String>();
			cacheMap.put("serviceName", serviceName);
			cacheMap.put("cpId", cpId);
			cacheMap.put("nonceStr", nonceStr);
			cacheMap.put("cpOrderId", cpOrderId);
			cacheMap.put("transId", transId);
			cacheMap.put("batchNo", batchNo);

			//以下为非必传
			cacheMap.put("inputCharset", inputCharset);
			cacheMap.put("signType", signType);


			// 过滤map
			Map<String, String> params = SignUtils.paraFilter2(cacheMap);
			if (params.containsKey("sign"))
				params.remove("sign");
			StringBuilder buf = new StringBuilder((params.size() + 1) * 10);

			SignUtils.buildPayParams(buf, params, false);
			String preStr = buf.toString();
			System.out.println("preStr: " + preStr);

				try {
					sign =URLEncoder.encode(RSA.signRSA256(preStr, key, "utf-8"),"UTF-8") ;
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		

				String param2 = preStr + "&sign=" + sign;
				System.out.println("Sent Post: " + param2);
				String ss=PostTools.httpRequest(posturl, "POST", param2);		
			return ss;
		}
		
		/***
		 * 代付余额查询方法
		 * @param posturl
		 * @param serviceName
		 * @param cpId
		 * @param nonceStr
		 * @param cpOrderId
		 * @param inputCharset
		 * @param signType
		 * @param key
		 * @return
		 */

		public static String balanceQueryPost(String posturl, String serviceName, String cpId, String nonceStr, String cpOrderId,String batchNo,
				 String inputCharset,String signType,String key) {			
			
			String sign2="",sign="";
			Map<String, String> cacheMap = new HashMap<String, String>();
			cacheMap.put("serviceName", serviceName);
			cacheMap.put("cpId", cpId);
			cacheMap.put("nonceStr", nonceStr);
			cacheMap.put("cpOrderId", cpOrderId);
			cacheMap.put("batchNo", batchNo);

			//以下为非必传
			cacheMap.put("inputCharset", inputCharset);
			cacheMap.put("signType", signType);


			// 过滤map
			Map<String, String> params = SignUtils.paraFilter2(cacheMap);
			if (params.containsKey("sign"))
				params.remove("sign");
			StringBuilder buf = new StringBuilder((params.size() + 1) * 10);

			SignUtils.buildPayParams(buf, params, false);
			String preStr = buf.toString();
			System.out.println("preStr: " + preStr);

				try {
					sign =URLEncoder.encode(RSA.signRSA256(preStr, key, "utf-8"),"UTF-8") ;
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		

				String param2 = preStr + "&sign=" + sign;
				System.out.println("Sent Post: " + param2);
				String ss=PostTools.httpRequest(posturl, "POST", param2);		
			return ss;
		}
		
		
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
