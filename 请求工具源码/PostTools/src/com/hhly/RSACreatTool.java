package com.hhly;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

import com.hhly.Util.SaveFileUtil;
import com.hhly.sign.RSA;

import javax.swing.JTextArea;
import java.awt.ScrollPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.util.Map;
import java.awt.event.ActionEvent;

public class RSACreatTool extends JFrame {

	private JPanel contentPane;
	private JTextArea textArea_publickey ;
	private JTextArea textArea_privatekey;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RSACreatTool frame = new RSACreatTool();
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
	public RSACreatTool() {
		setTitle("RSA生成工具");
		setResizable(false);
		setBounds(100, 100, 492, 491);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 29, 466, 102);
		contentPane.add(scrollPane_1);
		
		textArea_publickey = new JTextArea();
		textArea_publickey.setTabSize(20);
		textArea_publickey.setRows(5);
		textArea_publickey.setLineWrap(true);
		textArea_publickey.setAutoscrolls(true);
		textArea_publickey.setColumns(8);
		textArea_publickey.setLineWrap(true);
		scrollPane_1.setViewportView(textArea_publickey);
		
		JLabel label = new JLabel("公钥");
		label.setBounds(10, 4, 54, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("私钥");
		label_1.setBounds(10, 142, 54, 15);
		contentPane.add(label_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 167, 466, 252);
		contentPane.add(scrollPane);
		
		textArea_privatekey = new JTextArea();
		textArea_privatekey.setColumns(10);
		textArea_privatekey.setTabSize(20);
		textArea_privatekey.setRows(5);
		textArea_privatekey.setLineWrap(true);
		textArea_privatekey.setAutoscrolls(true);
		
		textArea_privatekey.setLineWrap(true);
		scrollPane.setViewportView(textArea_privatekey);
		
		JButton btnrsa = new JButton("生成RSA");
		btnrsa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Map<String, Object> ff = RSA.genKeyPairRSA256();
				// Object RSAPublicKey=ff.get("RSAPublicKey");
				// Object RSAPrivateKey=ff.get("RSAPrivateKey");
				String pubString = null;
				String priString = null;
				try {
					pubString = RSA.getPublicKey(ff);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					priString = RSA.getPrivateKey(ff);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				textArea_privatekey.setText(priString);
				textArea_publickey.setText(pubString);
			}
		});
		btnrsa.setBounds(351, 433, 93, 23);
		contentPane.add(btnrsa);
		
		JButton button = new JButton("关闭");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				RSACreatTool.this.setVisible(false);
			}
		});
		button.setBounds(10, 433, 93, 23);
		contentPane.add(button);
		
		JButton btnrsa_1 = new JButton("保存RSA");
		btnrsa_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String RSAPublicKey=textArea_publickey.getText().toString();
				String RSAPrivateKey=textArea_privatekey.getText().toString();
				if(RSAPrivateKey.isEmpty()||RSAPrivateKey.length()==0||RSAPublicKey.isEmpty()||RSAPublicKey.length()==0){
					JOptionPane.showMessageDialog(null, "公钥或私钥为空无法保存，请先生成！", "提示", JOptionPane.ERROR_MESSAGE);
				}else {
					JFileChooser fc = new JFileChooser();
					fc.setDialogType(JFileChooser.FILES_ONLY);
					fc.setDialogTitle("选择文件路径");
					fc.setMultiSelectionEnabled(false);
					fc.showSaveDialog(fc);
					if (fc.getSelectedFile()==null) {
						JOptionPane.showMessageDialog(null, "请选择文件路径及填写文件路径", "提示", JOptionPane.ERROR_MESSAGE);
					}else {
						String path=fc.getSelectedFile().getPath();
						System.out.println("path"+path);
						
						SaveFileUtil.Tofile("[signkey]\nRSAPublicKey = " + RSAPublicKey + "\nRSAPrivateKey = " + RSAPrivateKey, path);
					}
					
				}
				
			}
		});
		btnrsa_1.setBounds(136, 433, 93, 23);
		contentPane.add(btnrsa_1);
	}
}
