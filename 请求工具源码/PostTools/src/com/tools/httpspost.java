package com.tools;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.hhly.PostTools;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class httpspost extends JFrame {

	private JPanel contentPane;
	private JTextField textField_posturl;
	private JComboBox comboBox_posttype;
	private JComboBox comboBox_conettype;
	private JTextArea textArea_parameter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					httpspost frame = new httpspost();
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
	public httpspost() {
		setTitle("https请求参数工具");
		setResizable(false);
		String connecttype[]={"https","http"};
		String posttype[] = { "POST", "GET" };
		setBounds(100, 100, 554, 458);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUrl = new JLabel("URL:");
		lblUrl.setBounds(10, 10, 54, 15);
		contentPane.add(lblUrl);

		textField_posturl = new JTextField();
		textField_posturl.setBounds(65, 7, 463, 21);
		contentPane.add(textField_posturl);
		textField_posturl.setColumns(10);
		textField_posturl.setText("https://192.168.70.87:9444/service");

		comboBox_posttype = new JComboBox(posttype);
		comboBox_posttype.setBounds(232, 38, 60, 21);
		contentPane.add(comboBox_posttype);
		comboBox_posttype.setMaximumRowCount(2);

		comboBox_conettype = new JComboBox(connecttype);
		comboBox_conettype.setBounds(75, 38, 71, 21);
		contentPane.add(comboBox_conettype);
		comboBox_conettype.setMaximumRowCount(2);

		JLabel label = new JLabel("请求方式：");
		label.setBounds(165, 41, 69, 15);
		contentPane.add(label);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 91, 518, 126);
		contentPane.add(scrollPane);

		textArea_parameter = new JTextArea();
		textArea_parameter.setTabSize(20);
		textArea_parameter.setRows(5);
		textArea_parameter.setLineWrap(true);
		textArea_parameter.setAutoscrolls(true);
		textArea_parameter.setColumns(8);
		textArea_parameter.setLineWrap(true);
		scrollPane.setViewportView(textArea_parameter);

		JLabel label_1 = new JLabel("参数：");
		label_1.setBounds(10, 73, 54, 15);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("返回：");
		label_2.setBounds(10, 223, 54, 15);
		contentPane.add(label_2);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 248, 518, 136);
		contentPane.add(scrollPane_1);

		JTextArea textArea_backstr = new JTextArea();
		textArea_backstr.setTabSize(20);
		textArea_backstr.setRows(5);
		textArea_backstr.setLineWrap(true);
		textArea_backstr.setAutoscrolls(true);
		textArea_backstr.setColumns(8);
		textArea_backstr.setLineWrap(true);
		scrollPane_1.setViewportView(textArea_backstr);

		JButton button_sent = new JButton("发送");
		button_sent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String posturl=textField_posturl.getText().toString();
				String parameter=textArea_parameter.getText().toString();
				String Connecttype=(String) comboBox_conettype.getSelectedItem();
				String type="";
//				String posttype=(String) comboBox_posttype.getSelectedItem();
//				if(posttype[0].equals(comboBox_posttype.getSelectedItem())){
//					type="POST";
//				}else {
//					type="GET";
//				}
//				
//				if(!posturl.isEmpty()||posturl.length()!=0){
//					if(!parameter.isEmpty()||parameter.length()!=0){
//						if(connecttype[0].equals(connecttype)){
//							//http
//							String poststrString=PostTools.httpRequest(posturl, type, parameter);
//							textArea_backstr.setText(poststrString);
//						}else {
//							//https
//							String poststrString=PostTools.httpsRequest(posturl, type, parameter);
//							textArea_backstr.setText(poststrString);
//						}
//						
//					}else {
//						
//						JOptionPane.showMessageDialog(null, "参数不能为空", "提示", JOptionPane.ERROR_MESSAGE);
//					}
//				}else {
//					JOptionPane.showMessageDialog(null, "URL不能为空", "提示", JOptionPane.ERROR_MESSAGE);
//				}
				
				String poststrString=PostTools.httpsRequest(posturl, "POST", parameter);
				textArea_backstr.setText(poststrString);

			}
		});
		button_sent.setBounds(435, 387, 93, 23);
		contentPane.add(button_sent);

		JLabel lblNewLabel = new JLabel("连接方式");
		lblNewLabel.setBounds(10, 41, 54, 15);
		contentPane.add(lblNewLabel);

	}
}
