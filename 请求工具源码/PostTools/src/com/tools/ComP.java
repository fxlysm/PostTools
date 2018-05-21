package com.tools;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import com.hhly.RSACreatTool;
import com.hhly.Util.SaveFileUtil;
import com.hhly.mysql.ReadFromMysql;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;

public class ComP extends JFrame {

	private JPanel contentPane;
	private JTextField textField_xls_path;
	private JTextArea textArea_log;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ComP frame = new ComP();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ComP() {
		setTitle("帐单对比工具");
		setResizable(false);
		setBounds(100, 100, 602, 510);
		contentPane = new JPanel();
		contentPane.setToolTipText("对帐工具");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblXls = new JLabel("Xls路径：");
		lblXls.setBounds(10, 10, 67, 15);
		contentPane.add(lblXls);
		
		
		textField_xls_path = new JTextField();
		textField_xls_path.setBounds(87, 7, 415, 21);
		contentPane.add(textField_xls_path);
		textField_xls_path.setColumns(10);
		
		JButton button_xls_choose = new JButton("选  择");
		button_xls_choose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc = new JFileChooser();
				
				fc.setDialogType(JFileChooser.FILES_ONLY);
				fc.setDialogTitle("选择文件路径");
				fc.setMultiSelectionEnabled(false);
				fc.showSaveDialog(fc);
				if (fc.getSelectedFile()==null) {
					JOptionPane.showMessageDialog(null, "请选择从商户下载的帐单流水XLS文件路径及填写文件路径", "提示", JOptionPane.ERROR_MESSAGE);
				}else {
					String path=fc.getSelectedFile().getPath();
					textField_xls_path.setText(path);
					System.out.println("path"+path);
					
				
				}
				
				
			}
		});
		button_xls_choose.setBounds(512, 6, 74, 23);
		contentPane.add(button_xls_choose);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 35, 555, 395);
		contentPane.add(scrollPane);
		
		textArea_log = new JTextArea();
		scrollPane.setViewportView(textArea_log);
		
		JButton button_start = new JButton("开始");
		button_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String path=textField_xls_path.getText().toString();
				
				if(path.isEmpty()||path.length()==0){
					JOptionPane.showMessageDialog(null, "XLS文件路径为空，请正确添加", "提示", JOptionPane.ERROR_MESSAGE);
				}else {
					
					
					
					
					jxl.Workbook readwb = null;

					try

					{

						// 构建Workbook对象, 只读Workbook对象

						// 直接从本地文件创建Workbook

						
						
						InputStream instream = new FileInputStream(path);

						readwb = Workbook.getWorkbook(instream);

						// Sheet的下标是从0开始

						// 获取第一张Sheet表

						Sheet readsheet = readwb.getSheet(0);

						// 获取Sheet表中所包含的总列数

						int rsColumns = readsheet.getColumns();

						// 获取Sheet表中所包含的总行数

						int rsRows = readsheet.getRows();

						// 获取指定单元格的对象引用

						for (int i = 1; i < rsRows; i++)

						{
							Cell cellordertime = readsheet.getCell(0, i);//交易时间
							Cell cellcpOrderId = readsheet.getCell(1, i);//商户订单号
							Cell celltrandid = readsheet.getCell(2, i);//平台订单号
							Cell cellchan_order_id=readsheet.getCell(3, i);//第三方订单号
							Cell cellpaytype = readsheet.getCell(4, i);//支付类型
							Cell cellstatus= readsheet.getCell(5, i);//交易状态
							Cell celltotal=readsheet.getCell(7, i);//交易金额
							Cell cellcurrency=readsheet.getCell(9,i);//币种
							
							String ordertime=cellordertime.getContents();
							String cpOrderId=cellcpOrderId.getContents();
							String trandid=celltrandid.getContents();
							String chan_order_id=cellchan_order_id.getContents();
							String paytype=cellpaytype.getContents();
							String status=cellstatus.getContents();
							String totalfee=celltotal.getContents();
							String currency=cellcurrency.getContents();
							
//							
//							System.out.println("cpOrderId【商户订单号】:"+cpOrderId );
//							System.out.println("trandid【平台订单号】:"+trandid );
//							System.out.println("chan_order_id【第三方订单号】:"+chan_order_id);
//							System.out.println("chantype【支付类型】:"+paytype );
//							System.out.println("status【交易状态】:"+status );
//							System.out.println("totalfee【交易金额】:"+totalfee );
//							System.out.println("currency【币种】:"+currency );
							
							Map<String, String> comMap=new HashMap<String, String>();
							comMap.put("ordertime", ordertime);
							comMap.put("cpOrderId", cpOrderId);
							comMap.put("trandid", trandid);
							comMap.put("chan_order_id", chan_order_id);
							if("微信扫码".equals(paytype)){
								comMap.put("paytype", "2");
							}else if ("支付宝扫码".equals(paytype)) {
								comMap.put("paytype", "1");
							}
							
							comMap.put("status", status);
							comMap.put("totalfee", totalfee);
							if("人民币".equals(currency)){
								comMap.put("currency", "CNY");
							}
							
							String log=textArea_log.getText().toString();
							System.out.println(ReadFromMysql.Check(comMap));
							String result=ReadFromMysql.Check(comMap);
							String str="交易时间:"+ordertime+"   商户订单号:"+cpOrderId+"  平台订单号:"+trandid+"  第三方订单号:"+chan_order_id+"  支付类型:"+paytype+"  交易状态:"+status+"  交易金额:"+totalfee+"  币种:"+currency+"  验证结果："+result+"\n";
							SaveFileUtil.Tofile(str, "checklog.txt");
							textArea_log.setText(str+log);


						}


					} catch (Exception e) {

						e.printStackTrace();

					} finally {

						readwb.close();

					}
					
				}
				
				
				
				
				
				
				
				
				
				
				
			}
		});
		button_start.setBounds(471, 449, 93, 23);
		contentPane.add(button_start);
		
		JButton button_close = new JButton("关  闭");
		button_close.setToolTipText("对帐工具");
		button_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ComP.this.setVisible(false);
			}
		});
		button_close.setBounds(332, 449, 93, 23);
		contentPane.add(button_close);
		
		JButton button = new JButton("说明");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "1.进入商户系统导出交易流水\n2.更改文件格式为xls类型。\n3.不要增减字段", "提示", JOptionPane.ERROR_MESSAGE);
			}
		});
		button.setBounds(30, 449, 67, 23);
		contentPane.add(button);
	}
}

