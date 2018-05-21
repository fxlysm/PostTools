package com.hhly;



import java.awt.BorderLayout;
import java.net.URL;

import javax.swing.*;

public class ShowImage {
	public ShowImage() {
		JPanel panel=new JPanel(new BorderLayout());

		
		String urlString="C://Users//Public//Pictures//Sample Pictures//4.jpg";
		JLabel label=new JLabel(new ImageIcon(urlString));
		

		panel.add(label,BorderLayout.CENTER);
	
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panel,BorderLayout.CENTER);
		

//		this.setSize(400, 300);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.setTitle("显示图像");
//		this.setVisible(true);
	}
	private JFrame getContentPane() {
		// TODO Auto-generated method stub
		return null;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	  ShowImage showImage=new ShowImage();
	}
}
