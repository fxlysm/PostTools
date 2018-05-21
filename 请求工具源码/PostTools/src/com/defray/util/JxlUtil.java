package com.defray.util;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.hhly.JsonUtil;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;

public class JxlUtil {
	
	
	
	
	
	public static String[]  Readexcel(){
		String[]  trade = null;
		String type="";
		String str="";
		jxl.Workbook readwb = null;
		String path = System.getProperty("user.dir");
		System.out.println(path);
		try{
			// 构建Workbook对象, 只读Workbook对象
			// 直接从本地文件创建Workbook
			InputStream instream = new FileInputStream(path+"/excel/defray.xls");
			readwb = Workbook.getWorkbook(instream);
			// Sheet的下标是从0开
			// 获取第一张Sheet表
			Sheet readsheet = readwb.getSheet(0);
			// 获取Sheet表中所包含的总列数
			int rsColumns = readsheet.getColumns();
			// 获取Sheet表中所包含的总行数
			int rsRows = readsheet.getRows();
			trade = new String[rsRows-1];
			// 获取指定单元格的对象引用
			for (int i = 1; i < rsRows; i++)
			{
				Cell cellpayeeName = readsheet.getCell(0, i);//*收款方姓名
				Cell cellpayeeBankId = readsheet.getCell(1, i);//*收款方银行账号
				Cell cellpayeeBankProvince = readsheet.getCell(2, i);//*开户行所在省
				Cell cellpayeeBankCity=readsheet.getCell(3, i);//*开户行所在市
				Cell cellpayeeBankName = readsheet.getCell(4, i);//*开户行名称
				Cell cellcollectionBankNmae= readsheet.getCell(5, i);//*收款方银行名称
				Cell celltotal=readsheet.getCell(6, i);//*金额
				Cell cellpaytype=readsheet.getCell(7,i);//*支付方式(0是对私，1是对公)
				Cell cellcporderId=readsheet.getCell(8,i);//商户订单号
				Cell cellCpmark=readsheet.getCell(9,i);//商户备注
				Cell cellcollectionid=readsheet.getCell(10,i);//*收款方证件号
				Cell cellcollectionphone=readsheet.getCell(11,i);//*收款方手机号
				
				String payeeName=cellpayeeName.getContents();
				String payeeBankId=cellpayeeBankId.getContents();
				String payeeBankProvince=cellpayeeBankProvince.getContents();
				String payeeBankCity=cellpayeeBankCity.getContents();
				String payeeBankName=cellpayeeBankName.getContents();
				String collectionBankNmae=cellcollectionBankNmae.getContents();
				String total=celltotal.getContents();
				String paytype=cellpaytype.getContents();
				String cporderId=cellcporderId.getContents();
				String Cpmark=cellCpmark.getContents();
				String collectionid=cellcollectionid.getContents();
				String collectionphone=cellcollectionphone.getContents();
				if("0".equals(paytype)){//*支付方式(0是对私，1是对公)--->对私户=P；对公户=C",
					type="P";
				}else if ("1".equals(paytype)) {
					type="C";
				}
				

				String logs="收款方姓名:"+payeeName+"   收款方银行账号:"+payeeBankId+"  开户行所在省:"+payeeBankProvince+"  开户行所在市:"+payeeBankCity+"  开户行名称:"+payeeBankName
						+"  收款方银行名称:"+collectionBankNmae+"  交易金额:"+total+"  支付方式:"+paytype+"  商户订单号："+cporderId+"  商户备注:"+Cpmark
						+"  收款方证件号:"+collectionid+"  收款方手机号:"+collectionphone
						;
				System.out.println(logs);
				
//				JSONObject jsonObject = new JSONObject();
//				jsonObject.put("bankName", collectionBankNmae);
//				jsonObject.put("branchName",payeeBankName );
//				jsonObject.put("accountType", type);
//				jsonObject.put("accountNo", payeeBankId);
//				jsonObject.put("accountName", payeeName);
//				jsonObject.put("idNo", collectionid);
//				jsonObject.put("phone",collectionphone);
//				jsonObject.put("totalFee", total);
//				
//				str=jsonObject.toString();
				
				
				Map<String, String> comMap=new HashMap<String, String>();
		
				comMap.put("bankName", collectionBankNmae);
				comMap.put("branchName", payeeBankName);
				comMap.put("accountType", type);
				comMap.put("accountNo", payeeBankId);
				comMap.put("accountName", payeeName);
				comMap.put("idNo", collectionid);
				comMap.put("phone", collectionphone);
				comMap.put("totalFee", total);
				
				String jsonstr=JsonUtil.toJson(comMap);
				

				
				trade[(i-1)]=jsonstr;

			}


		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			readwb.close();

		}
		return trade;

	}

}
