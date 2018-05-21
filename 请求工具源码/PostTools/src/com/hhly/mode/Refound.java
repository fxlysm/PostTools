package com.hhly.mode;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.hhly.Key;
import com.hhly.MD5;
import com.hhly.PostTools;
import com.hhly.Tools;
import com.hhly.mysql.get;
import com.hhly.sign.RSA;
import com.hhly.sign.SignUtils;



public class Refound {
	/**
	 * 测试退款---参数为必传参数
	 * @param serviceName
	 * @param cpId
	 * @param cpOrderId
	 * @param transId
	 * @param totalFee
	 * @param refundFee
	 * @param outRefundNo
	 * @param opUserId
	 * @param nonceStr
	 * @param refundReson
	 * @param notifyUrl
	 * @return
	 */
		public static String Refund_pay(String posturl,String serviceName,String cpId,String cpOrderId,String transId,String totalFee,String refundFee,String outRefundNo,String opUserId,String nonceStr,String refundReson,String notifyUrl,String key,String signtype){
			Map<String, String> cacheMap = new HashMap<String, String>();
//			Map<String, String> dd = get.GetValue(cpId);
			String sign2="",sign="";
			cacheMap.put("serviceName", serviceName);
//			cacheMap.put("appId", appId);
			cacheMap.put("cpId", cpId);
			cacheMap.put("cpOrderId", cpOrderId);
			cacheMap.put("transId", transId);
			cacheMap.put("outRefundNo", outRefundNo);//商户退款单号

			cacheMap.put("totalFee", totalFee);
			cacheMap.put("refundFee", refundFee);
			cacheMap.put("refundReson", refundReson);
			cacheMap.put("opUserId", opUserId);
			cacheMap.put("notifyUrl", notifyUrl);
			cacheMap.put("nonceStr", nonceStr);
			cacheMap.put("mchCreateIp", Tools.GetIp());
			
			// 过滤map
					Map<String, String> params = SignUtils.paraFilter2(cacheMap);
					if (params.containsKey("sign"))
						params.remove("sign");
					StringBuilder buf = new StringBuilder((params.size() + 1) * 10);

					SignUtils.buildPayParams(buf, params, false);
					String preStr = buf.toString();
					System.out.println("preStr: "+preStr);

					if("MD5".equals(signtype)){
						
						 sign2 = MD5.sign(preStr, "&key=" + key, "utf-8");
						sign = Tools.shift(sign2);
						System.out.println("sign: "+sign);
						
					}else {
						try {
							sign =URLEncoder.encode(RSA.signRSA256(preStr, key, "utf-8"),"UTF-8") ;
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
//						 sign2 = MD5.sign(preStr, "&key=" + key, "utf-8");
//							sign = Tools.shift(sign2);
					}
//					String sign = MD5.sign(preStr, "&key=" + key, "utf-8");
					
//					String sign2=Tools.shift(sign);
					System.out.println("大写Sign: "+sign);

			    	String param=preStr+"&sign="+sign;
			    	System.out.println("param: "+param);
			    	String ss=PostTools.sendPost(posturl, param);
//			    	System.out.println(ss);
			    	return ss;
		}
		
		
		
		/**
		 * 测试退款查询---参数为必传参数
		 * @param serviceName
		 * @param cpId
		 * @param cpOrderId
		 * @param transId
		 * @param totalFee
		 * @param refundFee
		 * @param outRefundNo
		 * @param opUserId
		 * @param nonceStr
		 * @param refundReson
		 * @param notifyUrl
		 * @return
		 */
			public static String Refund_query(String posturl,String serviceName,String cpId,String cpOrderId,String transId,String outRefundNo,String refundId,String nonceStr,String inputCharset,String signType,String key,String signtype){
				Map<String, String> dd = get.GetValue(cpId);
				String sign2="",sign="";
				Map<String, String> cacheMap = new HashMap<String, String>();
				cacheMap.put("serviceName", serviceName);
				cacheMap.put("cpId", cpId);
				cacheMap.put("cpOrderId", cpOrderId);
				cacheMap.put("transId", transId);
				cacheMap.put("outRefundNo", outRefundNo);//商户退款单号
				cacheMap.put("refundId", refundId);
				cacheMap.put("nonceStr", nonceStr);
				cacheMap.put("inputCharset", inputCharset);
				cacheMap.put("signType", signType);
				// 过滤map
						Map<String, String> params = SignUtils.paraFilter2(cacheMap);
						if (params.containsKey("sign"))
							params.remove("sign");
						StringBuilder buf = new StringBuilder((params.size() + 1) * 10);

						SignUtils.buildPayParams(buf, params, false);
						String preStr = buf.toString();
						System.out.println("preStr: "+preStr);

						if("MD5".equals(dd.get("sign_type"))){
							
							 sign2 = MD5.sign(preStr, "&key=" + key, "utf-8");
							sign = Tools.shift(sign2);
							System.out.println("sign: "+sign);
							
						}else {
							try {
								sign =URLEncoder.encode(RSA.signRSA256(preStr, Key.cp_660026, "utf-8"),"UTF-8") ;
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						
//						String sign = MD5.sign(preStr, "&key=" + key, "utf-8");
//						System.out.println("sign: "+sign);
//						String sign2=Tools.shift(sign);
//						System.out.println("大写Sign: "+sign2);

				    	String param=preStr+"&sign="+sign;
				    	System.out.println("param: "+param);
				    	String ss=PostTools.sendPost(posturl, param);
//				    	System.out.println(ss);
				    	return ss;
			}
		
}
