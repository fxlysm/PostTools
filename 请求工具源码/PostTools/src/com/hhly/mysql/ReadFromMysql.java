package com.hhly.mysql;

import java.math.BigDecimal;
import java.util.Map;



public class ReadFromMysql {

	public static  String Check(Map<String, String> map){
		String result="";
		System.out.println(map);
		String ordertime=map.get("ordertime");
		String cpOrderId=map.get("cpOrderId");
		String trandid=map.get("trandid");
		String chan_order_id=map.get("chan_order_id");
		String paytype=map.get("paytype");
		String status=map.get("status");
		String totalfee=map.get("totalfee");//读取的为小数字符串
		String currency=map.get("currency");
		BigDecimal t = new BigDecimal(totalfee).multiply(new BigDecimal(100));
//		double totfee=(Double.valueOf(totalfee.toString()))*100;
//		System.out.println("totfee:"+t.intValue());
//		int   fee   =   (new   Double(totfee)).intValue(); 
		String total_feeString=String.valueOf(t.intValue());
		System.out.println("TotalFee:"+total_feeString);
		
		
		if("成功".equals(status)){
//			System.out.println("订单状态为成功，将对其做数据比对");
			Map<String, String> ff = MySqlString.GetValue(trandid);
			System.out.println("从数库中读取到的数据："+ff);
			if(!ff.containsKey("chan_order_id")){
				result= "FAIL";
			}else {
				String  mysql_th3Id=ff.get("chan_order_id");//第三方编号
				String  mysql_paytype=ff.get("chan_id");//支付类型
				String  mysql_currency=ff.get("currency");
				String  mysql_totalfee=ff.get("total_fee");
				String mysql_trans_time=ff.get("trans_time");
//				double mysql_totalf=(double)(Integer.valueOf(mysql_totalfee_o).intValue()/100);
//				System.out.println("mysql_totalfee:"+mysql_totalf);
				if(chan_order_id.equals(mysql_th3Id)&&currency.equals(mysql_currency)&&total_feeString.equals(mysql_totalfee)&&paytype.equals(mysql_paytype)){
					System.out.println("验证结果PASS");
					result= "PASS";
				}else {
					System.out.println("验证结果FAIL");
					result= "FAIL";
				}
			}
			
			
		}else{
			System.out.println("订单状态为失败，不做处理");
		}
		return result;
	}
}
