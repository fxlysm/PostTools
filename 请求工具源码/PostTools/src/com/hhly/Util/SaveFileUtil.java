package com.hhly.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFileUtil {

	public static void ToTXT(String str){
		try{
//		      String data = " This content will append to the end of the file";

		      File file =new File("key.ini");

		      //if file doesnt exists, then create it
		      if(!file.exists()){
		       file.createNewFile();
		      }

		    //true = append file
		      FileWriter fileWritter = new FileWriter(file.getName(),true);
		             BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		             bufferWritter.write(str);
		             bufferWritter.close();

		         System.out.println("Done");

		     }catch(IOException e){
		      e.printStackTrace();
		     }

	}
	
	public static void Tofile(String str,String logpath){
		try{
//		      String data = " This content will append to the end of the file";

		      File file =new File(logpath);

		      //if file doesnt exists, then create it
		      if(!file.exists()){
		       file.createNewFile();
		      }

		    //true = append file
		      FileWriter fileWritter = new FileWriter(file.getName(),true);
		             BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		             bufferWritter.write(str);
		             bufferWritter.close();

		         System.out.println("Done");

		     }catch(IOException e){
		      e.printStackTrace();
		     }

	}
}
