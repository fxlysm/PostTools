package com.defray.util;

import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;

public class tradePayeeUtil {
	

	
	public static String GettradePayeeJson() {
		String[] trade = JxlUtil.Readexcel();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(int i = 0; i < trade.length; i++){
		 sb. append(trade[i]);
		 if(i!=trade.length-1){
			 sb.append(",");
		 }
		 
		}
		
		sb.append("]");
		String s = sb.toString();
//		System.out.println(s);
		return s;
	}
	
	public static String CreatJsonStr(){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", "liuyong");
		jsonObject.put("age", "30");
		return jsonObject.toString();
	}
	
	
	public static void main(String[] args) {
        // TODO Auto-generated method stub
//        System.out.println("输入需要的数组大小：");
//        Scanner scan=new Scanner(System.in);
//        int n = scan.nextInt();//接受输入的数组大小
//        int[]arr=new int[n];//定义一个大小为刚输入的n的数组
//        System.out.println("输入数组的每个元素：");
//        for(int i=0;i<arr.length;i++)
//            arr[i]=scan.nextInt();//依次输入元素到arr[i]
//        System.out.println("数组的元素依次为：");
//        for(int i=0;i<arr.length;i++)
//            System.out.print(arr[i]+"\t");
		System.out.println(CreatJsonStr());
		System.out.println(GettradePayeeJson());
    }
	
	
	
	
	public String Read() {
		
		
		
		return "";
		
	}

}
