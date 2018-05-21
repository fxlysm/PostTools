package com.tools;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;

public class verifyTools extends JFrame {

	private JPanel contentPane;
	private JTextField textField,textField_cpid,textField_nocurl;
	private JComboBox comboBox_env,comboBox_type;
	private JTextArea textArea_pivkey,textArea_message;
	
	String env[] = { "测试", "预发布", "线上", "开发环境", "本地" };
	String servertype[]={"hhly.unified.defray.pay","hhly.unified.defray.query","hhly.unified.defray.account"};
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					verifyTools frame = new verifyTools();
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
	public verifyTools() {
		setEnabled(false);
		setTitle("鉴权工具");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 793, 595);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 763, 511);
		contentPane.add(tabbedPane);
		
		JPanel panel_pay = new JPanel();
		panel_pay.setToolTipText("参数");
		tabbedPane.addTab("参数", null, panel_pay, null);
		panel_pay.setLayout(null);
		
		JLabel label = new JLabel("请求地址：");
		label.setBounds(0, 39, 67, 15);
		panel_pay.add(label);
		
		textField = new JTextField();
		textField.setBounds(77, 36, 671, 21);
		panel_pay.add(textField);
		textField.setColumns(10);
		
		JLabel label_1 = new JLabel("环境");
		label_1.setBounds(13, 10, 54, 15);
		panel_pay.add(label_1);
		
		comboBox_env = new JComboBox(env);
		comboBox_env.setBounds(76, 7, 78, 21);
		comboBox_env.setMaximumRowCount(3);
		panel_pay.add(comboBox_env);
		
		JLabel label_2 = new JLabel("类型：");
		label_2.setBounds(174, 10, 54, 15);
		panel_pay.add(label_2);
		
		comboBox_type = new JComboBox(servertype);
		comboBox_type.setMaximumRowCount(3);
		comboBox_type.setBounds(208, 7, 104, 21);
		panel_pay.add(comboBox_type);
		
		JLabel lblCpid = new JLabel("CpId:");
		lblCpid.setBounds(322, 10, 54, 15);
		panel_pay.add(lblCpid);
		
		textField_cpid = new JTextField();
		textField_cpid.setBounds(361, 7, 66, 21);
		panel_pay.add(textField_cpid);
		textField_cpid.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 89, 738, 160);
		panel_pay.add(scrollPane);
		
		textArea_pivkey = new JTextArea();
		scrollPane.setViewportView(textArea_pivkey);
		
		JLabel lblPivkey = new JLabel("私钥：");
		lblPivkey.setBounds(10, 64, 54, 15);
		panel_pay.add(lblPivkey);
		
		JLabel lblMessage = new JLabel("LOG：");
		lblMessage.setBounds(13, 252, 54, 15);
		panel_pay.add(lblMessage);
		
		textArea_message = new JTextArea();
		textArea_message.setBounds(13, 280, 719, 192);
		panel_pay.add(textArea_message);
		
		JPanel panel_setting = new JPanel();
		panel_setting.setToolTipText("设置");
		tabbedPane.addTab("设置", null, panel_setting, null);
		panel_setting.setLayout(null);
		
		JLabel label_3 = new JLabel("通知地址：");
		label_3.setBounds(10, 10, 65, 15);
		panel_setting.add(label_3);
		
		textField_nocurl = new JTextField();
		textField_nocurl.setBounds(85, 7, 650, 21);
		panel_setting.add(textField_nocurl);
		textField_nocurl.setColumns(10);
		
		JButton button = new JButton("发送");
		button.setBounds(680, 531, 93, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("关闭");
		button_1.setBounds(10, 531, 93, 23);
		contentPane.add(button_1);
	}
	
	
	

}
