package com.hhly.mode;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.hhly.MD5;
import com.hhly.PostTools;
import com.hhly.StringData;
import com.hhly.Tools;
import com.hhly.mysql.get;
import com.hhly.sign.RSA;
import com.hhly.sign.SignUtils;


public class JDQuickPay {
	public static String Quickpay_sign(String posturl,String service,String cpId,String cpOrderId,String notifyUrl,String totalFee,String nonceStr,String key,String signtype) throws UnsupportedEncodingException{
//		Map<String, String> dd = get.GetValue(cpId);
		String sign2="",sign="";
		int i=2;
		String bank=StringData.bank[i];
		String no=StringData.no[i];
		String name=StringData.name[i];
//				URLEncoder.encode(StringData.name[i],"UTF-8");
		
		String idNo=StringData.idNo[i];
		String phone=StringData.phone[i];
		String idType="I"; 
		
    	Map<String, String> cacheMap = new HashMap<String, String>();
    	
    	cacheMap.put("serviceName", service);
    	cacheMap.put("cpId", cpId);

    	cacheMap.put("nonceStr", nonceStr);
    	cacheMap.put("cpOrderId", cpOrderId);
    	cacheMap.put("notifyUrl", notifyUrl);
    	//http://pay.1332255.com/payCenterMaven/uloPay/uloPayNotify  线上
    	//http://pay.1332255.com/pay/gateway/test/trade/pay
    	//http://pay.13322.com/pay/swiftpass/weixinApp/appNotify
    	cacheMap.put("bank", bank);
    	cacheMap.put("no", no);//持卡人支付卡号
//    	cacheMap.put("exp", no);//持卡人信用卡有效期
//    	cacheMap.put("cvv2", "cvv2");//持卡人信用卡校验码
    	cacheMap.put("name", name);
    	cacheMap.put("idType", idType);
    	cacheMap.put("idNo", idNo);
    	cacheMap.put("phone", phone);
    	cacheMap.put("totalFee", totalFee);
    	cacheMap.put("currency", "CNY");
//    	cacheMap.put("attach", Tools.getRandomString(8));
//    	cacheMap.put("limitTime", "5");
//    	cacheMap.put("platform", "4");//运行平台类型(1:PC 2:H5 3:Android 4:Ios 5:公众号)
    	cacheMap.put("type", "D");//信用卡：C / 借记卡：D
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
		}else {
			
			try {
				sign =URLEncoder.encode(RSA.signRSA256(preStr, key, "utf-8"),"UTF-8") ;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

   	String param=preStr+"&sign="+sign;
    	System.out.println("param: "+param);
    	String ss=PostTools.sendPost(posturl, param);
//    	System.out.println(ss);
    	return ss;
    }
	
	
	
	
	
	
	
	
	public static String Quickpay_sentpost(String posturl,String service,String cpId,String cpOrderId,String totalFee,String code,String tranId,String key){
		Map<String, String> dd = get.GetValue(cpId);
		String sign2="",sign="";
		int i=2;
		String bank=StringData.bank[i];
		String no=StringData.no[i];
		String name=StringData.name[i];
		String idNo=StringData.idNo[i];
		String phone=StringData.phone[i];
		String idType="I";
    	Map<String, String> cacheMap = new HashMap<String, String>();
    	
    	cacheMap.put("serviceName", service);
    	cacheMap.put("cpId", cpId);

    	cacheMap.put("nonceStr", Tools.getRandomString(8));
    	cacheMap.put("cpOrderId", cpOrderId);

    	cacheMap.put("bank", bank);
    	cacheMap.put("no", no);
    	cacheMap.put("name", name);
    	cacheMap.put("idType", idType);
    	cacheMap.put("idNo", idNo);
    	cacheMap.put("phone", phone);
    	cacheMap.put("totalFee", totalFee);
    	cacheMap.put("currency", "CNY");
    	cacheMap.put("code", code);
    	cacheMap.put("transId", tranId);
    	cacheMap.put("type", "D");
    	cacheMap.put("notifyUrl", "http://192.168.74.163:5656/payCenterApi/handlerNotify");
    	cacheMap.put("mchCreateIp", Tools.GetIp());
    	
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
		}else {
			
			try {
				sign =URLEncoder.encode(RSA.signRSA256(preStr, key, "utf-8"),"UTF-8") ;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

   	String param=preStr+"&sign="+sign;
    	System.out.println("param: "+param);
    	String ss=PostTools.sendPost(posturl, param);
//    	System.out.println(ss);
    	return ss;
    }
	
	
}
