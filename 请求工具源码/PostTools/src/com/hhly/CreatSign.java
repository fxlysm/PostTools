package com.hhly;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.hhly.sign.RSA;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreatSign extends JFrame {

	private JPanel contentPane;
	private JTextField textField_md5key;
	private JTextArea textArea_md5str;
	private JTextArea textArea_md5sign;
	private JTextArea textArea_rsa_sign;
	private JTextArea textArea_rsa_key;
	private JTextArea textArea_rsa_str;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreatSign frame = new CreatSign();
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
	public CreatSign() {
		setResizable(false);
		setAutoRequestFocus(false);
		setTitle("Sign生成工具");
		setBounds(100, 100, 617, 588);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 581, 540);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setToolTipText("MD5签名");
		tabbedPane.addTab("MD5签名", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblKey = new JLabel("Key");
		lblKey.setBounds(10, 10, 54, 15);
		panel.add(lblKey);
		
		textField_md5key = new JTextField();
		textField_md5key.setBounds(55, 7, 494, 21);
		panel.add(textField_md5key);
		textField_md5key.setColumns(10);
		
		JLabel label = new JLabel("预签名字串");
		label.setBounds(10, 45, 94, 15);
		panel.add(label);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 70, 539, 242);
		panel.add(scrollPane);
		
		 textArea_md5str = new JTextArea();
		scrollPane.setViewportView(textArea_md5str);
		
		JButton btnsign = new JButton("生成Sign");
		btnsign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String keyString=textField_md5key.getText().toString();
				String str=textArea_md5str.getText().toString();
				if(keyString.isEmpty()||keyString.length()==0){
					JOptionPane.showMessageDialog(null,
							"Key 输入框为空，请填入", "提示",JOptionPane.ERROR_MESSAGE);
				}else {
					
					if(str.isEmpty()||str.length()==0){
						JOptionPane.showMessageDialog(null,
								"预签名输入框为空，请填入", "提示",JOptionPane.ERROR_MESSAGE);
					}else {
						String sign=MD5.sign(str, keyString, "UTF-8");
						textArea_md5sign.setText(sign);
					}
				}
				
			}
		});
		btnsign.setBounds(473, 468, 93, 23);
		panel.add(btnsign);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 368, 539, 90);
		panel.add(scrollPane_1);
		
		textArea_md5sign = new JTextArea();
		scrollPane_1.setViewportView(textArea_md5sign);
		
		JLabel lblSign = new JLabel("Sign:");
		lblSign.setBounds(20, 349, 54, 15);
		panel.add(lblSign);
		
		JButton button_1 = new JButton("关闭");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreatSign.this.setVisible(false);
			}
		});
		button_1.setBounds(11, 478, 93, 23);
		panel.add(button_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setToolTipText("RSA（256）签名");
		tabbedPane.addTab("RSA（256）签名", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel label_1 = new JLabel("预签名字符串");
		label_1.setBounds(20, 4, 100, 15);
		panel_1.add(label_1);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 24, 556, 104);
		panel_1.add(scrollPane_2);
		
		textArea_rsa_str = new JTextArea();
		scrollPane_2.setViewportView(textArea_rsa_str);
		
		JLabel label_2 = new JLabel("私钥");
		label_2.setBounds(20, 139, 54, 15);
		panel_1.add(label_2);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 154, 556, 132);
		panel_1.add(scrollPane_3);
		
		 textArea_rsa_key = new JTextArea();
		scrollPane_3.setViewportView(textArea_rsa_key);
		
		JLabel lblSign_1 = new JLabel("Sign");
		lblSign_1.setBounds(20, 293, 54, 15);
		panel_1.add(lblSign_1);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(10, 318, 556, 154);
		panel_1.add(scrollPane_4);
		
		textArea_rsa_sign = new JTextArea();
		scrollPane_4.setViewportView(textArea_rsa_sign);
		
		JButton btnsign_1 = new JButton("生成Sign");
		btnsign_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String keyString=textArea_rsa_key.getText().toString();
				String str=textArea_rsa_str.getText().toString();
				if(keyString.isEmpty()||keyString.length()==0){
					JOptionPane.showMessageDialog(null,
							"Key 输入框为空，请填入", "提示",JOptionPane.ERROR_MESSAGE);
				}else {
					
					if(str.isEmpty()||str.length()==0){
						JOptionPane.showMessageDialog(null,
								"预签名输入框为空，请填入", "提示",JOptionPane.ERROR_MESSAGE);
					}else {
					
						String sign=RSA.signRSA256(str, keyString, "UTF-8");
						textArea_rsa_sign.setText(sign);
					}
				}
				
			
			}
		});
		btnsign_1.setBounds(461, 478, 93, 23);
		panel_1.add(btnsign_1);
		
		JButton button = new JButton("关闭");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreatSign.this.setVisible(false);
			}
		});
		button.setBounds(10, 478, 93, 23);
		panel_1.add(button);
	}
}
