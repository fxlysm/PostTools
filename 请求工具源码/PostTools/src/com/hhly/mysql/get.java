package com.hhly.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class get {

	public static String mysql_url = "jdbc:mysql://192.168.74.161:3306/pay?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useCompression=true&useSSL=false";
	public static String mysql_Account = "pay";
	public static String mysql_password = "oRcl_1234";

	/**
	 * 一般数据查询
	 * 
	 * @param command
	 * @param purpose
	 * @return
	 */
	public static String key(String command, String purpose) {
		String keyString = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn;
			conn = DriverManager.getConnection(mysql_url, mysql_Account, mysql_password); //
			Statement stmt = conn.createStatement(); // 创建Statement对象
			// System.out.println("mysql command:"+command);
			ResultSet rs = stmt.executeQuery(command);// 创建数据对象
			while (rs.next()) {
				keyString = rs.getString(purpose);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return keyString;
	}

	/**
	 * 一般数据查询
	 * 
	 * @param command
	 * @param purpose
	 * @return
	 */
	public static String signtype(String purpose) {
		String keyString = "";
		String command="SELECT input_charset FROM cp_keys WHERE cp_id='"+purpose+"'";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn;
			conn = DriverManager.getConnection(mysql_url, mysql_Account, mysql_password); //
			Statement stmt = conn.createStatement(); // 创建Statement对象
			// System.out.println("mysql command:"+command);
			ResultSet rs = stmt.executeQuery(command);// 创建数据对象
			while (rs.next()) {
				keyString = rs.getString(purpose);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return keyString;
	}
	
	
	
	 public static Map<String, String> GetValue(String cp_id){
			Map<String, String> cacheMap = new HashMap<String, String>();
			String sql_command="SELECT * FROM cp_keys WHERE cp_id="+cp_id;
//			System.out.println("Command:"+sql_command);
			try{
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection conn;
	            conn = DriverManager.getConnection(mysql_url,mysql_Account,mysql_password);  //
	            Statement stmt = conn.createStatement(); //创建Statement对象
	            ResultSet rs = stmt.executeQuery(sql_command);//创建数据对象
	                while (rs.next()){
	                	cacheMap.put("cp_id", rs.getString("cp_id"));
	                	cacheMap.put("sign_type", rs.getString("sign_type"));
	                	cacheMap.put("pay_key", rs.getString("pay_key"));	                	
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




public static void main(String[] args) {
	
	System.out.println(GetValue("660002"));
}
}
