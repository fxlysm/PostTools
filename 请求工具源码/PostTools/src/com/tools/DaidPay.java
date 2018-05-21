package com.tools;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.defray.mode.Yilian;
import com.defray.util.tradePayeeUtil;
import com.hhly.StringData;
import com.hhly.Tools;
import com.hhly.Util.JsonUtil;
import com.hhly.sign.CheckSign;

import ch.ubique.inieditor.IniEditor;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.awt.event.ActionEvent;

public class DaidPay extends JFrame {

	private JPanel contentPane;
	private JTextField textField_posturl,textField_noturl;
	private JTextArea textArea_message,textArea_pivkey;
	private JTextField textField_cpid;
	private JComboBox comboBox_env,comboBox_paytype;
	/****
	 * 以下为参数
	 */
	String env[] = { "测试", "预发布", "线上", "开发环境", "本地" };
	String servertype[]={"hhly.unified.defray.pay","hhly.unified.defray.query","hhly.unified.defray.account"};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DaidPay frame = new DaidPay();
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
	public DaidPay() {
		setTitle("代付请求工具V1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 721, 571);
		contentPane = new JPanel();
		contentPane.setToolTipText("代付接口请求工具");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 685, 496);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("参数", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblPosturl = new JLabel("请求地址：");
		lblPosturl.setBounds(10, 39, 61, 15);
		panel.add(lblPosturl);
		
		textField_posturl = new JTextField();
		textField_posturl.setBounds(81, 36, 578, 21);
		panel.add(textField_posturl);
		textField_posturl.setColumns(10);
		
		JLabel lblCpid = new JLabel("  CPID：");
		lblCpid.setBounds(402, 11, 54, 15);
		panel.add(lblCpid);
		
		textField_cpid = new JTextField();
		textField_cpid.setText("660001");
		textField_cpid.setBounds(451, 10, 66, 21);
		panel.add(textField_cpid);
		textField_cpid.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 89, 639, 129);
		panel.add(scrollPane);
		
	 textArea_pivkey = new JTextArea();
	 textArea_pivkey.setTabSize(20);
	 textArea_pivkey.setRows(5);
	 textArea_pivkey.setLineWrap(true);
	 textArea_pivkey.setAutoscrolls(true);
	 textArea_pivkey.setColumns(8);
	 textArea_pivkey.setLineWrap(true);
		scrollPane.setViewportView(textArea_pivkey);
		
		JLabel lblPivkey = new JLabel("PivKey:");
		lblPivkey.setBounds(10, 64, 54, 15);
		panel.add(lblPivkey);
		
		JLabel lblMessage = new JLabel("Message:");
		lblMessage.setBounds(10, 228, 54, 15);
		panel.add(lblMessage);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(20, 253, 639, 190);
		panel.add(scrollPane_1);
		
		textArea_message = new JTextArea();
		scrollPane_1.setViewportView(textArea_message);
		
		this.comboBox_env = new JComboBox(env);
		comboBox_env.setBounds(81, 10, 90, 21);
		comboBox_env.setMaximumRowCount(3);
		panel.add(comboBox_env);
		
		JLabel label_1 = new JLabel("  环 境：");
		label_1.setBounds(10, 8, 54, 15);
		panel.add(label_1);
		
		comboBox_paytype = new JComboBox(servertype);
		comboBox_env.setMaximumRowCount(3);
		comboBox_paytype.setBounds(220, 10, 172, 21);
		panel.add(comboBox_paytype);
		
		JLabel label_2 = new JLabel("类型：");
		label_2.setBounds(181, 11, 54, 15);
		panel.add(label_2);
		
		JPanel panel_setting = new JPanel();
		tabbedPane.addTab("设置", null, panel_setting, null);
		panel_setting.setLayout(null);
		
		JLabel label = new JLabel("通知地址：");
		label.setBounds(10, 10, 61, 15);
		panel_setting.add(label);
		
		textField_noturl = new JTextField();
		textField_noturl.setBounds(81, 7, 589, 21);
		panel_setting.add(textField_noturl);
		textField_noturl.setColumns(10);
		
		JButton button_sent = new JButton("发送");
		button_sent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String oldString=textArea_message.getText();
				String servername = (String) comboBox_paytype.getSelectedItem();
				String posturl=textField_posturl.getText().toString();
				String cpId=textField_cpid.getText().toString();
				String nonceStr=Tools.getRandomString(8);
				String notifyUrl=textField_noturl.getText().toString();
				String tradePayeeJson=tradePayeeUtil.GettradePayeeJson();
				String key=textArea_pivkey.getText().toString();
				String cpOrderId="dft"+Tools.getStringDate("yyyyMMddHHmmss")+Tools.getCode(6, 0);
				String cofString="servername:"+servername+"\ncpId:"+cpId+"\nnonceStr:"+nonceStr+"\ntradePayeeJson:"+tradePayeeJson+"\ncpOrderId:"+cpOrderId;
				if("hhly.unified.defray.pay".equals(servername)){
					//代付下单
				String poststrString=	Yilian.PayPost(posturl, servername, cpId, nonceStr, cpOrderId, notifyUrl, tradePayeeJson, null, null, null, null, key);
				textArea_message.setText(cofString+"\n返回结果:\n"+poststrString+"\n****************\n"+oldString);
				
				Map<String, String> camap = JsonUtil.jsonToMap(poststrString);
				if(camap.containsKey("sign")){
					boolean checkresult=CheckSign.verify(poststrString, "RSA", key);
					JOptionPane.showMessageDialog(null, "返回参数验签结果："+checkresult, "提示", JOptionPane.ERROR_MESSAGE);
				}else {
					
				}
				
				}else if ("hhly.unified.defray.query".equals(servername)) {
					String cpOId = JOptionPane.showInputDialog("请输入所查询的cpOrderId:");
					String batchNo=Tools.getCode(8, 0);
					if (!cpOId.equals("") || !cpOId.isEmpty() || cpOId.length() != 0) {
						String poststrString=	Yilian.QueryPost(posturl, servername, cpId, nonceStr, cpOId, null,batchNo, null, null, key);
						textArea_message.setText(cofString+"\n返回结果:\n"+poststrString+"\n****************\n"+oldString);
						Map<String, String> camap = JsonUtil.jsonToMap(poststrString);
						if(camap.containsKey("sign")){
							boolean checkresult=CheckSign.verify(poststrString, "RSA", key);
							JOptionPane.showMessageDialog(null, "返回参数验签结果："+checkresult, "提示", JOptionPane.ERROR_MESSAGE);
						}else {
							
						}
					}else {
						JOptionPane.showMessageDialog(null, "输入为空", "提示", JOptionPane.ERROR_MESSAGE);
					}
					
					
				}else if ("hhly.unified.defray.account".equals(servername)) {
					
					String batchNo=Tools.getCode(8, 0);
					String poststrString=	Yilian.balanceQueryPost(posturl, servername, cpId, nonceStr, cpOrderId,batchNo, null, null, key);
					textArea_message.setText(cofString+"\n返回结果:\n"+poststrString+"\n****************\n"+oldString);
					Map<String, String> camap = JsonUtil.jsonToMap(poststrString);
					if(camap.containsKey("sign")){
						boolean checkresult=CheckSign.verify(poststrString, "RSA", key);
						JOptionPane.showMessageDialog(null, "返回参数验签结果："+checkresult, "提示", JOptionPane.ERROR_MESSAGE);
					}else {
						
					}
				}
				
			}
		});
		button_sent.setBounds(579, 510, 93, 23);
		contentPane.add(button_sent);
		
		JButton button_close = new JButton("关闭");
		button_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		button_close.setBounds(20, 510, 93, 23);
		contentPane.add(button_close);
		
		
		ini();
		controls_listener();
	}
	
	/***
	 * 数据初始化
	 */
	public void ini() {
		String path = System.getProperty("user.dir");
		textField_posturl.setText(StringData.daifu_url_test);
		textField_noturl.setText(StringData.notifyUrl_test);
		//以下为加载KEY信息
		IniEditor ini = new IniEditor();
		try {
			ini.load(new File(path+"/key.ini"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String privatekey = ini.get("signkey", "RSAPrivateKey");
		textArea_pivkey.setText(privatekey);
	}
	
	
	public void controls_listener(){
		/***
		 * 控件监控
		 */
		textField_cpid.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		comboBox_env.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (env[0].equals(e.getItem())) {
					textField_posturl.setText(StringData.daifu_url_test);
					textField_noturl.setText(StringData.notifyUrl_test);
				}else if (env[1].equals(e.getItem())) {
					textField_posturl.setText(StringData.daifu_url_online);
					textField_noturl.setText(StringData.notifyUrl_online);
				}else if (env[2].equals(e.getItem())) {
					textField_posturl.setText(StringData.daifu_url_online);
					textField_noturl.setText(StringData.notifyUrl_online);
				}else if (env[3].equals(e.getItem())) {
					textField_posturl.setText(StringData.daifu_url_online);
					textField_noturl.setText(StringData.notifyUrl_online);
				}else {
					textField_posturl.setText("");
					textField_noturl.setText("");
				}
			}
		});
	}
}
