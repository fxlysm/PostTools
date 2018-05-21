package com.hhly.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;



public class MySqlString {
	
	public static String mysql_url = "jdbc:mysql://192.168.74.161:3306/pay?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useCompression=true&useSSL=false";
	public static String mysql_Account = "pay";
	public static String mysql_password = "oRcl_1234";
	 public static String getKey(String purpose ,String FROMString,String conditions){
			String keyString="";
			 try{
		            Class.forName("com.mysql.cj.jdbc.Driver");  
		            Connection conn;
		            conn = DriverManager.getConnection(mysql_url,mysql_Account,mysql_password);  //
		            Statement stmt = conn.createStatement(); //创建Statement对象
		            String commandString="SELECT "+purpose+" FROM "+FROMString+" where "+conditions;

		            ResultSet rs = stmt.executeQuery(commandString);//创建数据对象
		                while (rs.next()){
		                    keyString=rs.getString(purpose);
		                }
		                rs.close();
		                stmt.close();
		                conn.close();
		            }catch(Exception e)
		            {
		                e.printStackTrace();
		            }
			
			return keyString;
		}
	 
	 
	 
	 public static Map<String, String> GetValue_Refound(String trans_id){
			Map<String, String> cacheMap = new HashMap<String, String>();
			String sql_command="SELECT * FROM pay_trans_log WHERE TRANS_ID="+trans_id;
			System.out.println("Command:"+sql_command);
			try{
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection conn;
	            conn = DriverManager.getConnection(mysql_url,mysql_Account,mysql_password);  //
	            Statement stmt = conn.createStatement(); //创建Statement对象
	            ResultSet rs = stmt.executeQuery(sql_command);//创建数据对象
	                while (rs.next()){
	                	cacheMap.put("TRANS_ID", rs.getString("TRANS_ID"));
	                	cacheMap.put("CP_ID", rs.getString("CP_ID"));
	                	cacheMap.put("CP_ORDER_ID", rs.getString("CP_ORDER_ID"));
	                	cacheMap.put("TOTAL_FEE", rs.getString("TOTAL_FEE"));
	                	cacheMap.put("STATUS", rs.getString("STATUS"));
	                	cacheMap.put("CHAN_ID", rs.getString("CHAN_ID"));
	                	cacheMap.put("CHAN_ORDER_ID", rs.getString("CHAN_ORDER_ID"));
	                	
	                }
	                rs.close();
	                stmt.close();
	                conn.close();
	        }catch(Exception e)
	        {
	            e.printStackTrace();
	        }
//			System.out.println(cacheMap);
			return cacheMap;
		}
	 
	 
	 
	 /***
	  * 对帐读取
	  * @param trans_id
	  * @return
	  */
	 public static Map<String, String> GetValue(String trans_id){
			Map<String, String> cacheMap = new HashMap<String, String>();
			String sql_command="SELECT * FROM recon_third_data WHERE trans_id="+trans_id;
			System.out.println("Command:"+sql_command);
			try{
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection conn;
	            conn = DriverManager.getConnection(mysql_url,mysql_Account,mysql_password);  //
	            Statement stmt = conn.createStatement(); //创建Statement对象
	            ResultSet rs = stmt.executeQuery(sql_command);//创建数据对象
	                while (rs.next()){
	                	cacheMap.put("chan_order_id", rs.getString("chan_order_id"));
	                	cacheMap.put("cp_id_third", rs.getString("cp_id_third"));
	                	cacheMap.put("total_fee", rs.getString("total_fee"));
	                	cacheMap.put("trans_id", rs.getString("trans_id"));
	                	cacheMap.put("chan_id", rs.getString("chan_id"));
	                	cacheMap.put("currency",rs.getString("currency"));
	                	
	                	
	                }
	                rs.close();
	                stmt.close();
	                conn.close();
	        }catch(Exception e)
	        {
	            e.printStackTrace();
	        }
//			System.out.println(cacheMap);
			return cacheMap;
		}
}
