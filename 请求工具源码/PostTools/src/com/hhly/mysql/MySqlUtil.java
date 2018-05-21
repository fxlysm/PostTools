package com.hhly.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class MySqlUtil {

	
	
	 public static String get_pay_key(String CpId){
		 String keyString=MySqlString.getKey("pay_key", "cp_keys", "cp_id="+CpId);
		 return keyString;
	 }
	 
	 

	 
}
