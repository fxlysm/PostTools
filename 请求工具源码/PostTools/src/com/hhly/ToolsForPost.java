package com.hhly;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextArea;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.hhly.Util.SaveFileUtil;
import com.hhly.mode.AlipayJS;
import com.hhly.mode.BaiDuScanPay;
import com.hhly.mode.CloseOrder;
import com.hhly.mode.JDAPPPay;
import com.hhly.mode.JDQuickPay;
import com.hhly.mode.JDScanPay;
import com.hhly.mode.Micropay;
import com.hhly.mode.Refound;
import com.hhly.mode.ScanPay;
import com.hhly.mode.WechatAppPay;
import com.hhly.mode.WxoatradePay;
import com.hhly.mysql.MySqlString;
import com.hhly.mysql.MySqlUtil;
import com.hhly.mysql.get;
import com.hhly.sign.CheckSign;
import com.hhly.sign.RSA;
import com.tools.ComP;
import com.tools.httppost;
import com.tools.httpspost;

import ch.ubique.inieditor.IniEditor;

import javax.swing.JToolBar;
import javax.swing.SpinnerModel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JScrollBar;
import java.awt.ScrollPane;

public class ToolsForPost extends JFrame {

	private JPanel contentPane;
	private JTextField textField_cpId;
	private JTextField text_gateway;
	private JTextField text_noticeurl;
	private JTextArea textField_paykey;
	private JTextArea textArea_message;
	private JComboBox comboBox = null;
	private JComboBox comboBox_1 = null;
	private JComboBox comboBox_signtype = null;
	private JCheckBox chckbxBody_1 = null;
	private JCheckBox chckbxBody = null;
	private JPanel panel_codeUrl = null;
	public Image image;
	private JButton btnNewButton_erweima;
	private JTextField totalfee_min;
	private JTextField totalfee_max;
	private ScrollPane scrollPane_key;
	private ScrollPane ScrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ToolsForPost frame = new ToolsForPost();
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
	public ToolsForPost() {
		setResizable(false);
		setTitle("聚合支付---请求测试工具V1.2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 648, 658);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// JScrollPane scroll = new JScrollPane(textArea_message);
		// 把定义的JTextArea放到JScrollPane里面去
		// 分别设置水平和垂直滚动条自动出现
		// scroll.setHorizontalScrollBarPolicy(
		// JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// scroll.setVerticalScrollBarPolicy(
		// JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		textField_cpId = new JTextField();
		textField_cpId.setText("660002");
		textField_cpId.setBounds(266, 2, 84, 21);
		contentPane.add(textField_cpId);
		textField_cpId.setColumns(10);

		JLabel text_cpid_d = new JLabel("CPID:");
		text_cpid_d.setBounds(232, 5, 43, 15);
		contentPane.add(text_cpid_d);

		JLabel lblStrings = new JLabel("message");
		lblStrings.setBounds(18, 255, 54, 15);
		contentPane.add(lblStrings);

		JLabel label = new JLabel("接口地址：");
		label.setBounds(10, 33, 62, 15);
		contentPane.add(label);

		text_gateway = new JTextField();
		text_gateway.setText("http://192.168.74.163:2225/gateway");
		text_gateway.setColumns(10);
		text_gateway.setBounds(87, 30, 535, 21);
		contentPane.add(text_gateway);

		JLabel label_1 = new JLabel("通知地址：");
		label_1.setBounds(10, 58, 67, 15);
		contentPane.add(label_1);

		text_noticeurl = new JTextField();
		text_noticeurl.setText("http://192.168.74.163:5656/payCenterApi/handlerNotify");
		text_noticeurl.setColumns(10);
		text_noticeurl.setBounds(87, 59, 535, 21);
		contentPane.add(text_noticeurl);

		JLabel lblServername = new JLabel("ServerName");
		lblServername.setBounds(5, 93, 72, 15);
		contentPane.add(lblServername);

		JButton Button_sent = new JButton("发送");
		Button_sent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String body = "";
				String totalFee = "";
				String signtype = "";
				String posturlString = text_gateway.getText().toString();
				String noticeurlString = text_noticeurl.getText().toString();

				String cpid = textField_cpId.getText().toString();

				String servername = (String) comboBox.getSelectedItem();
				String ss = textArea_message.getText().toString() + "\n";

				// if ("线上".equals((String)
				// comboBox_1.getSelectedItem())||"预发布".equals((String)
				// comboBox_1.getSelectedItem())) {
				// signtype="RSA";
				//
				// } else {
				// signtype="MD5";
				// }

				if ("RSA".equals(comboBox_signtype.getSelectedItem())) {
					signtype = "RSA";

				} else {
					signtype = "MD5";

				}

				System.out.println("signtype" + signtype);
				String totalfee_minString = totalfee_min.getText().toString();
				String totalfee_maxString = totalfee_max.getText().toString();

				String min = totalfee_minString;
				String max = totalfee_maxString;
				totalFee = String.valueOf(Tools.gettotal(min, max));

				String mchCreateIp = Tools.GetIp();
				String cpOrderId = "hb" +
				// Tools.getSpecialcharacters(10)+Tools.getCode(4, 0);
				Tools.getStringDate("yyyyMMddHHmmss") + Tools.getCode(4, 0);
				// if()
				//
				if (chckbxBody.isSelected()) {
					body = Tools.getSpecialcharacters(16) + Tools.getRandomString(5);
					System.out.println(body + "\n");

				} else {
					body = Tools.getRandomString(8);
				}

				// if(chckbxRsa_type.isSelected()){
				// signtype="RSA";
				// }else {
				//
				// }

				String nonceStr = Tools.getRandomString(8);
				String key = textField_paykey.getText().toString();
				if (key.isEmpty() || key.equals("")) {
					JOptionPane.showMessageDialog(null, "支付Key为空", "提示", JOptionPane.ERROR_MESSAGE);
				} else {
					//
					// String privatekey = "";
					// Map<String, String> ff = get.GetValue(cpid);
					// if ("RSA".equals(ff.get("sign_type"))) {
					// if (cpid.equals("660026")) {
					// privatekey = Key.cp_660026;
					// }
					//
					// // JOptionPane.showInputDialog("请输入私钥:");
					// }
					// MySqlUtil.get_pay_key(cpid);
					String tradeName = Tools.getRandomString(8);
					// String buyerLogonId=Tools.getRandomString(6);
					String buyerId = "fxlysm@126.com";
					// textArea_message.setText(ss + "Key" + key + "\n");
					String logString = textArea_message.getText().toString();

					if ("hhly.pay.weixin.native".equals(servername)) {

						String sss = ScanPay.NormalPost(posturlString, servername, cpid, totalFee, cpOrderId,
								mchCreateIp, noticeurlString, body, nonceStr, key, signtype);
						// textArea_message.setText("微信扫码支付：\n" + "Key:" + key +
						// "\n" + "\ntotalFee:" + totalFee + "\n" + sss);
						textArea_message.setText("微信扫码支付: " + servername + "\ntotalFee:" + totalFee + "\nmchCreateIp:"
								+ mchCreateIp + "\ncpOrderId:" + cpOrderId + "\nnoticeurlString: " + noticeurlString
								+ "\nbody :" + body + "\nnonceStr: " + nonceStr + "\n" + sss + "\n返回验签结果："
								+ CheckSign.verify(sss, signtype, key) + "\n\n***********************\n" + logString);
						String url = JsonUtil.ReadJsonObj("codeImgUrl", sss);
						JsonUtil.OpenBrow(url);

					} else if ("hhly.pay.alipay.native".equals(servername)) {
						String sss = ScanPay.NormalPost(posturlString, servername, cpid, totalFee, cpOrderId,
								mchCreateIp, noticeurlString, body, nonceStr, key, signtype);
						// textArea_message.setText("支付宝扫码支付：\n" + "Key:" + key
						// + "\n" + "\ntotalFee:" + totalFee + "\n" + sss);
						//
						textArea_message.setText("支付宝扫码支付: " + servername + "\ntotalFee:" + totalFee + "\nmchCreateIp:"
								+ mchCreateIp + "\ncpOrderId:" + cpOrderId + "\nnoticeurlString: " + noticeurlString
								+ "\nbody :" + body + "\nnonceStr: " + nonceStr + "\n" + sss + "\n返回验签结果："
								+ CheckSign.verify(sss, signtype, key) + "\n\n***********************\n" + logString);

						String url = JsonUtil.ReadJsonObj("codeImgUrl", sss);
						JsonUtil.OpenBrow(url);
					}else if ("hhly.pay.baidu.native".equals(servername)) {
//						String sss = ScanPay.NormalPost(posturlString, servername, cpid, totalFee, cpOrderId,
//								mchCreateIp, noticeurlString, body, nonceStr, key, signtype);
						String str=BaiDuScanPay.PayPost(posturlString, servername, cpid, totalFee, cpOrderId, mchCreateIp, noticeurlString, body, nonceStr, "CNY", key, signtype);
					
						textArea_message.setText("百度扫码支付: " + servername + "\ntotalFee:" + totalFee + "\nmchCreateIp:"
								+ mchCreateIp + "\ncpOrderId:" + cpOrderId + "\nnoticeurlString: " + noticeurlString
								+ "\nbody :" + body + "\nnonceStr: " + nonceStr + "\n" + str + "\n返回验签结果："
								+ CheckSign.verify(str, signtype, key) + "\n\n***********************\n" + logString);

						String url = JsonUtil.ReadJsonObj("codeImgUrl", str);
						JsonUtil.OpenBrow(url);
					}
					
					else if ("hhly.pay.tenpay.native".equals(servername)) {
						String sss = ScanPay.NormalPost(posturlString, servername, cpid, totalFee, cpOrderId,
								mchCreateIp, noticeurlString, body, nonceStr, key, signtype);
						// textArea_message.setText("QQ扫码支付：\n" + "Key:" + key +
						// "\n" + "\ntotalFee:" + totalFee + "\n" + sss);
						textArea_message.setText("QQ扫码支付: " + servername + "\ntotalFee:" + totalFee + "\nmchCreateIp:"
								+ mchCreateIp + "\ncpOrderId:" + cpOrderId + "\nnoticeurlString: " + noticeurlString
								+ "\nbody :" + body + "\nnonceStr: " + nonceStr + "\n" + sss + "\n返回验签结果："
								+ CheckSign.verify(sss, signtype, key) + "\n\n***********************\n" + logString);
						String url = JsonUtil.ReadJsonObj("codeImgUrl", sss);
						JsonUtil.OpenBrow(url);
					} else if ("hhly.pay.jdpay.native".equals(servername)) {
						String postString = JDScanPay.NormalPost(posturlString, servername, cpid, totalFee, cpOrderId,
								noticeurlString, nonceStr, tradeName, "CNY", "0", mchCreateIp, key,signtype);

						String ssd = postString.replace("\\", "");

						textArea_message.setText("京东扫码支付为: " + servername + "\ntotalFee:" + totalFee + "\nmchCreateIp:"
								+ mchCreateIp + "\ncpOrderId:" + cpOrderId + "\nnoticeurlString: " + noticeurlString
								+ "\nbody :" + body + "\nnonceStr: " + nonceStr + "\n" + postString + "\n返回验签结果："
								+ CheckSign.verify(ssd, signtype, key) + "\n\n***********************\n" + logString
						// +"\n\nqrCode:"+url
						);
						String s2 = ssd.substring(1, ssd.length() - 1);
						String url = JsonUtil.ReadJsonObj("payUrl", ssd);
						JsonUtil.OpenBrow(url);
					} else if ("hhly.pay.jdpay.app".equals(servername)) {
						String postString = JDAPPPay.NormalPost(posturlString, servername, cpid, totalFee, cpOrderId,
								noticeurlString, nonceStr, tradeName, "CNY", "0", mchCreateIp, key,signtype);

						String ssd = postString.replace("\\", "");

						textArea_message.setText("京东APP支付为: " + servername + "\ntotalFee:" + totalFee + "\nmchCreateIp:"
								+ mchCreateIp + "\ncpOrderId:" + cpOrderId + "\nnoticeurlString: " + noticeurlString
								+ "\nbody :" + body + "\nnonceStr: " + nonceStr + "\n" + postString + "\n返回验签结果："
								+ CheckSign.verify(ssd, signtype, key) + "\n\n***********************\n" + logString
						// +"\n\nqrCode:"+url
						);
						
					} 
					
					else if ("hhly.pay.alipay.jspay".equals(servername)) {
						String postString = AlipayJS.NormalPost(posturlString, servername, cpid, totalFee, cpOrderId,
								mchCreateIp, noticeurlString, body, nonceStr, null, buyerId, key,signtype);
						// textArea_message.setText(
						// "支付宝服务窗支付：\n" + "Key:" + key + "\n" + "\ntotalFee:" +
						// totalFee + "\n" + postString);
						textArea_message.setText("支付宝服务窗支付: " + servername + "\ntotalFee:" + totalFee + "\nmchCreateIp:"
								+ mchCreateIp + "\ncpOrderId:" + cpOrderId + "\nnoticeurlString: " + noticeurlString
								+ "\nbody :" + body + "\nnonceStr: " + nonceStr + "\n" + postString + "\n返回验签结果："
								+ CheckSign.verify(postString, signtype, key) + "\n\n***********************\n"
								+ logString);
					} else if ("hhly.pay.weixin.raw.app".equals(servername)) {
						String poststr = WechatAppPay.Wechat_AppPay_sentpost(posturlString, servername, cpid, totalFee,
								cpOrderId, mchCreateIp, body, noticeurlString, key,signtype);
						// textArea_message.setText(
						// "微信APP支付【原生态】：\n" + "Key:" + key + "\n" +
						// "\ntotalFee:" + totalFee + "\n" + poststr);
						textArea_message.setText("微信APP支付【原生态】: " + servername + "\ntotalFee:" + totalFee
								+ "\nmchCreateIp:" + mchCreateIp + "\ncpOrderId:" + cpOrderId + "\nnoticeurlString: "
								+ noticeurlString + "\nbody :" + body + "\nnonceStr: " + nonceStr + "\n" + poststr
								+ "\n返回验签结果：" + CheckSign.verify(poststr, signtype, key)
								+ "\n\n***********************\n" + logString);
					} else if ("hhly.unified.trade.pay".equals(servername)) {
						String poststr = WechatAppPay.Wechat_AppPay_sentpost(posturlString, servername, cpid, totalFee,
								cpOrderId, mchCreateIp, body, noticeurlString, key,signtype);
						// textArea_message.setText(
						// "微信APP支付【非原生态】：\n" + "Key:" + key + "\n" +
						// "\ntotalFee:" + totalFee + "\n" + poststr);
						textArea_message.setText("微信APP支付【非原生态】: " + servername + "\ntotalFee:" + totalFee
								+ "\nmchCreateIp:" + mchCreateIp + "\ncpOrderId:" + cpOrderId + "\nnoticeurlString: "
								+ noticeurlString + "\nbody :" + body + "\nnonceStr: " + nonceStr + "\n" + poststr
								+ "\n返回验签结果：" + CheckSign.verify(poststr, signtype, key)
								+ "\n\n***********************\n" + logString);
					} else if ("hhly.pay.weixin.wap".equals(servername)) {
						String sss = ScanPay.NormalPost(posturlString, servername, cpid, totalFee, cpOrderId,
								mchCreateIp, noticeurlString, body, nonceStr, key, signtype);
						// textArea_message.setText("微信扫码支付：\n" + "Key:" + key +
						// "\n" + "\ntotalFee:" + totalFee + "\n" + sss);
						textArea_message.setText("微信H5支付: " + servername + "\ntotalFee:" + totalFee + "\nmchCreateIp:"
								+ mchCreateIp + "\ncpOrderId:" + cpOrderId + "\nnoticeurlString: " + noticeurlString
								+ "\nbody :" + body + "\nnonceStr: " + nonceStr + "\n" + sss + "\n返回验签结果："
								+ CheckSign.verify(sss, signtype, key) + "\n\n***********************\n" + logString);

					} else if ("微信公众号支付".equals(servername)) {
						String subOpenid = "o7yPywj0SEDb-AV3LfvkuZVu-Toc";
						String subAppid = "wx34d321d8ff64d7a4";
						String sss = WxoatradePay.NormalPost(posturlString, "hhly.weixin.oa.trade.pay", cpid, totalFee,
								cpOrderId, mchCreateIp, noticeurlString, body, nonceStr, subOpenid, noticeurlString,
								subAppid, key,signtype);
						textArea_message.setText("微信公众号支付: " + "hhly.weixin.oa.trade.pay" + "\ntotalFee:" + totalFee
								+ "\nmchCreateIp:" + mchCreateIp + "\ncpOrderId:" + cpOrderId + "\nnoticeurlString: "
								+ noticeurlString + "\nbody :" + body + "\nnonceStr: " + nonceStr + "\nsubOpenid:"
								+ subOpenid + "\nsubAppid:" + subAppid + "\n" + sss + "\n返回验签结果："
								+ CheckSign.verify(sss, signtype, key) + "\n\n***********************\n" + logString);

					} else if ("微信刷卡支付".equals(servername)) {
						String paycode = JOptionPane.showInputDialog("请微信--付款支付码:");
						if (!paycode.equals("") || !paycode.isEmpty() || paycode.length() != 0) {
							// !paycode.equals("") ||
							// !paycode.isEmpty()||paycode.length()!=0
							String sss = Micropay.WechatPost(posturlString, "hhly.pay.weixin.micropay", cpid, totalFee,
									cpOrderId, mchCreateIp, noticeurlString, body, nonceStr, noticeurlString, paycode,
									key,signtype);

							textArea_message.setText("微信刷卡支付: " + "hhly.pay.weixin.micropay" + "\ntotalFee:" + totalFee
									+ "\nmchCreateIp:" + mchCreateIp + "\ncpOrderId:" + cpOrderId
									+ "\nnoticeurlString: " + noticeurlString + "\nbody :" + body + "\nnonceStr: "
									+ nonceStr + "\nAutoCode:" + paycode + "\n" + sss + "\n返回验签结果："
									+ CheckSign.verify(sss, signtype, key) + "\n\n***********************\n"
									+ logString);

						} else {
							JOptionPane.showMessageDialog(null, "输入为空", "提示", JOptionPane.ERROR_MESSAGE);
						}
					} else if ("支付宝刷卡支付".equals(servername)) {
						String paycode = JOptionPane.showInputDialog("请支付宝--付款支付码:");
						if (!paycode.equals("") || !paycode.isEmpty() || paycode.length() != 0) {
							String sss = Micropay.WechatPost(posturlString, "hhly.pay.alipay.micropay", cpid, totalFee,
									cpOrderId, mchCreateIp, noticeurlString, body, nonceStr, noticeurlString, paycode,
									key,signtype);
							textArea_message.setText("支付宝刷卡支付: " + "hhly.pay.alipay.micropay" + "\ntotalFee:" + totalFee
									+ "\nmchCreateIp:" + mchCreateIp + "\ncpOrderId:" + cpOrderId
									+ "\nnoticeurlString: " + noticeurlString + "\nbody :" + body + "\nnonceStr: "
									+ nonceStr + "\nAutoCode:" + paycode + "\n" + sss + "\n返回验签结果："
									+ CheckSign.verify(sss, signtype, key) + "\n\n***********************\n"
									+ logString);
						} else {
							JOptionPane.showMessageDialog(null, "输入为空", "提示", JOptionPane.ERROR_MESSAGE);
						}
					}

					else if ("京东快捷支付".equals(servername)) {

						String poString = "";
						try {
							poString = JDQuickPay.Quickpay_sign(posturlString, "hhly.express.trade.sign", cpid,
									cpOrderId, noticeurlString, totalFee, nonceStr, key,signtype);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						textArea_message.setText(poString);
						String ssd = poString.replace("\\", "");
						String s2 = ssd.substring(1, ssd.length() - 1);
						System.out.println("s2:" + s2);
						String transId = JsonUtil.ReadJsonObj("transId", ssd);
						System.out.println("transId:" + transId);
						String inputValue = JOptionPane.showInputDialog("请输入手机验证码:");
						textArea_message.setText("签约---返回字段：\n" + poString);
						if (inputValue.equals("") || inputValue.isEmpty() || inputValue.length() == 0) {
							JOptionPane.showMessageDialog(null, "短信验证码为空", "提示", JOptionPane.ERROR_MESSAGE);
						} else {
							int i = 2;
							// textArea_message.setText(inputValue);

							String ssw = JDQuickPay.Quickpay_sentpost(posturlString, "hhly.express.trade.pay", cpid,
									cpOrderId, totalFee, inputValue, transId, key);

							textArea_message.setText(textArea_message.getText().toString()
									+ "\n*****************************************\n支付---返回字段\n" + ssw);
						}

					} else if ("退款请求".equals(servername)) {
						String trans_id = JOptionPane.showInputDialog("请输入transId:");
						if (trans_id.equals("") || trans_id.isEmpty() || trans_id.length() == 0) {
							JOptionPane.showMessageDialog(null, "输入transId为空", "提示", JOptionPane.ERROR_MESSAGE);
						} else {

							Map<String, String> dd = MySqlString.GetValue_Refound(trans_id);
							String totalFeestr = dd.get("TOTAL_FEE");
							// String cpidstr=dd.get("");
							String outRefundNo = Tools.getStringDate("yyyyMMddHHmmss") + Tools.getCode(6, 0);
							String opUserId = "liuyong";
							String refundReson = Tools.getRandomString(8);
							textArea_message.setText("查询结果如下：\n" + dd);
							// String refoundString =
							// Refound.Refund_pay(posturlString,
							// "hhly.unified.trade.refund", cpid,
							// cpOrderId, trans_id, totalFeestr, totalFeestr,
							// outRefundNo, opUserId, nonceStr,
							// refundReson, noticeurlString, key);

							String refoundString = Refound.Refund_pay(posturlString, "hhly.unified.trade.refund", cpid,
									null, trans_id, totalfee_min.getText().toString(),
									totalfee_max.getText().toString(), outRefundNo, opUserId, nonceStr, refundReson,
									noticeurlString, key,signtype);

							textArea_message.setText(textArea_message.getText().toString()
									+ "\n*****************************************\n退款请求---返回字段\n" + refoundString
									+ "\n返回验签结果：" + CheckSign.verify(refoundString, signtype, key));

						}
					} else if ("退款查询".equals(servername)) {
						String trans_id = JOptionPane.showInputDialog("请输入transId:");
						if (trans_id.equals("") || trans_id.isEmpty() || trans_id.length() == 0) {
							JOptionPane.showMessageDialog(null, "输入transId为空", "提示", JOptionPane.ERROR_MESSAGE);
						} else {

							String refoundquery = Refound.Refund_query(posturlString, StringData.SERVER_REFOUND_QUERY,
									cpid, null, trans_id, null, null, nonceStr, null, null, key,signtype);
							textArea_message.setText(textArea_message.getText().toString()
									+ "\n*****************************************\n退款查询---返回字段\n" + refoundquery
									+ logString);
							// +"\n返回验签结果："+CheckSign.verify(refoundquery)
							// textArea_message.setText("\n返回验签结果："+CheckSign.verify(refoundquery)+logString);
						}

					} else if ("关闭订单".equals(servername)) {
						String close_cpOrderId = JOptionPane.showInputDialog("请输入cpOrderId:");
						if (cpOrderId.equals("") || cpOrderId.isEmpty() || cpOrderId.length() == 0) {
							JOptionPane.showMessageDialog(null, "输入cpOrderId为空", "提示", JOptionPane.ERROR_MESSAGE);
						} else {

							String orderclose = CloseOrder.ClosePost(posturlString, StringData.SERVER_CLOSE_ORDER, cpid,
									close_cpOrderId, nonceStr, null, null, key, signtype);

							textArea_message.setText("关闭订单\n" + textArea_message.getText().toString()
									+ "\n*****************************************\n关闭订单---返回字段\n" + orderclose
									+ "\n返回验签结果：" + CheckSign.verify(orderclose, signtype, key));

						}
					}

					else {
						textArea_message.setText("暂未加入");
					}
				}

			}
		});
		Button_sent.setBounds(538, 576, 67, 23);
		contentPane.add(Button_sent);

		String nations[] = { "hhly.pay.weixin.native", "hhly.pay.alipay.native", "hhly.pay.tenpay.native","hhly.pay.jdpay.native","hhly.pay.baidu.native",
				"hhly.pay.weixin.raw.app", "hhly.unified.trade.pay", "hhly.pay.jdpay.app","hhly.pay.alipay.jspay", 
				"hhly.pay.weixin.wap", "微信公众号支付", "微信刷卡支付", "支付宝刷卡支付", "京东快捷支付", "退款请求", "退款查询", "关闭订单" };
		String env[] = { "测试", "预发布", "线上", "开发环境", "本地" };
		String signtype[] = { "RSA", "MD5" };
		this.comboBox = new JComboBox(nations);

		comboBox.setBounds(87, 90, 199, 21);
		contentPane.add(comboBox);
		comboBox.setMaximumRowCount(10);

		JLabel label_2 = new JLabel("环   境：");
		label_2.setBounds(10, 5, 61, 15);
		contentPane.add(label_2);

		this.comboBox_1 = new JComboBox(env);
		comboBox_1.setBounds(87, 2, 135, 21);
		contentPane.add(comboBox_1);
		comboBox_1.setMaximumRowCount(3);

		comboBox_signtype = new JComboBox(signtype);
		comboBox_signtype.setBounds(430, 2, 62, 21);
		contentPane.add(comboBox_signtype);
		comboBox_signtype.setMaximumRowCount(2);

		this.chckbxBody = new JCheckBox("Body填充特殊字符");
		chckbxBody.setBounds(498, 1, 121, 23);
		contentPane.add(chckbxBody);

		JLabel lblkey_text = new JLabel("Sign Key:");
		lblkey_text.setBounds(10, 118, 54, 15);
		contentPane.add(lblkey_text);

		JButton btn_exit = new JButton("退出");
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btn_exit.setBounds(18, 576, 67, 23);
		contentPane.add(btn_exit);

		JLabel lblTotalfee = new JLabel("TotalFee范围");
		lblTotalfee.setBounds(298, 90, 84, 15);
		contentPane.add(lblTotalfee);

		totalfee_min = new JTextField();
		totalfee_min.setText("100");
		totalfee_min.setBounds(385, 87, 43, 21);
		contentPane.add(totalfee_min);
		totalfee_min.setColumns(10);

		JLabel label_3 = new JLabel("~");
		label_3.setBounds(427, 93, 12, 15);
		contentPane.add(label_3);

		totalfee_max = new JTextField();
		totalfee_max.setText("500000");
		totalfee_max.setColumns(10);
		totalfee_max.setBounds(438, 90, 54, 21);
		contentPane.add(totalfee_max);

		JLabel label_4 = new JLabel("分");
		label_4.setBounds(502, 90, 26, 15);
		contentPane.add(label_4);

		textField_paykey = new JTextArea();
		textField_paykey.setTabSize(20);
		textField_paykey.setRows(5);
		textField_paykey.setLineWrap(true);
		textField_paykey.setAutoscrolls(true);
		textField_paykey.setColumns(8);
		textField_paykey.setLineWrap(true);
		textField_paykey.setBounds(74, 133, 546, 82);

		// contentPane.add(textField_paykey);

		scrollPane_key = new ScrollPane();
		scrollPane_key.add(textField_paykey);
		scrollPane_key.setBounds(18, 144, 604, 105);
		contentPane.add(scrollPane_key);

		textArea_message = new JTextArea();
		textArea_message.setTabSize(20);
		textArea_message.setColumns(10);
		textArea_message.setRows(5);
		textArea_message.setBounds(18, 272, 596, 188);
		textArea_message.setLineWrap(true);// 激活自动换行功能
		textArea_message.setAutoscrolls(true);
		textField_paykey.setLineWrap(true);
		// contentPane.add(textArea_message);
		// textArea_message.setTabSize(20);
		textArea_message.setColumns(5);
		textArea_message.setRows(5);
		textArea_message.setLineWrap(true);// 激活自动换行功能
		textArea_message.setAutoscrolls(true);

		ScrollPane = new ScrollPane();
		ScrollPane.setBounds(10, 276, 612, 294);
		ScrollPane.add(textArea_message);
		contentPane.add(ScrollPane);

		JButton btnras = new JButton("生成RSA");
		btnras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Map<String, Object> ff = RSA.genKeyPairRSA256();
				// Object RSAPublicKey=ff.get("RSAPublicKey");
				// Object RSAPrivateKey=ff.get("RSAPrivateKey");
				String pubString = null;
				try {
					pubString = RSA.getPublicKey(ff);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String priString = null;
				try {
					priString = RSA.getPrivateKey(ff);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				SaveFileUtil.ToTXT("[signkey]\nRSAPublicKey = " + pubString + "\nRSAPrivateKey = " + priString);
				JOptionPane.showMessageDialog(null, "生成RSA签名对成功，请查看key.ini文件", "提示", JOptionPane.ERROR_MESSAGE);
			}
		});
		btnras.setBounds(110, 576, 93, 23);
		contentPane.add(btnras);

		JLabel lblSigntype = new JLabel("SignType:");
		lblSigntype.setBounds(360, 5, 63, 15);
		contentPane.add(lblSigntype);

		// JPanel panelOutput;
		//
		// panelOutput = new JPanel();
		// panelOutput.add(new JScrollPane(textField_paykey));
		// JScrollPane scroll = new JScrollPane(textField_paykey);
		// //把定义的JTextArea放到JScrollPane里面去
		//
		// //分别设置水平和垂直滚动条自动出现
		// scroll.setHorizontalScrollBarPolicy(
		// JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// scroll.setVerticalScrollBarPolicy(
		// JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		comboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				switch (arg0.getStateChange()) {
				case ItemEvent.SELECTED:
					System.out.println("选中" + arg0.getItem());
					break;
				case ItemEvent.DESELECTED:
					System.out.println("取消选中" + arg0.getItem());
					break;
				}

			}
		});

		comboBox_signtype.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				
				chanshuselect(env[0],signtype[0]);
			}
		});

		comboBox_1.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (env[0].equals(e.getItem())) {
					text_gateway.setText("http://192.168.74.163:2225/gateway");
					text_noticeurl.setText("http://192.168.74.163:5656/payCenterApi/handlerNotify");
					totalfee_min.setText("100");
					totalfee_max.setText("500000");
//					String cpid = textField_cpId.getText().toString();
//					textField_paykey.setText(MySqlUtil.get_pay_key(cpid));

				} else if (env[2].equals(e.getItem())) {
					text_gateway.setText("http://pay.13322.com/pay/gateway");
					text_noticeurl.setText("http://pay.1332255.com/payCenterMaven/uloPay/uloPayNotify");
					// textField_paykey.setVisible(true);
					// lblkey_text.setVisible(true);
					totalfee_min.setText("1");
					totalfee_max.setText("1");


				} else if (env[1].equals(e.getItem())) {
					text_gateway.setText("http://pay.aggregatepay.com/pay/gateway");
					text_noticeurl.setText("http://pay.1332255.com/payCenterMaven/uloPay/uloPayNotify");
					totalfee_min.setText("1");
					totalfee_max.setText("1");
				} else if (env[3].equals(e.getItem())) {
					text_gateway.setText("http://192.168.74.168:2225/gateway");
					text_noticeurl.setText("http://192.168.74.163:5656/payCenterApi/handlerNotify");
					totalfee_min.setText("100");
					totalfee_max.setText("500000");
				} else if (env[4].equals(e.getItem())) {
					text_gateway.setText("http://127.0.0.1:2225/gateway");
					text_noticeurl.setText("http://192.168.74.163:5656/payCenterApi/handlerNotify");
					totalfee_min.setText("100");
					totalfee_max.setText("500000");
				} else {
					textField_paykey.setText("");
				}
			}
		});

		if (env[0].equals((String) comboBox_1.getSelectedItem())) {
			// lblkey_text.setVisible(false);
			String cpid = textField_cpId.getText().toString();
			textField_paykey.setText(MySqlUtil.get_pay_key(cpid));
		} else {
			// textField_paykey.setVisible(true);
			// lblkey_text.setVisible(true);
			textField_paykey.setText("");
		}

		textField_cpId.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		textField_cpId.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

				chanshuselect(env[0],signtype[0]);

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

				chanshuselect(env[0],signtype[0]);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		}

		);

		JMenu jm = new JMenu("文件"); // 创建JMenu菜单对象
		JMenu menu2 = new JMenu("工具"); // 创建JMenu菜单对象
		JMenu menu3 = new JMenu("代付"); // 创建JMenu菜单对象
		JMenu menu4 = new JMenu("鉴权"); // 创建JMenu菜单对象
		JMenuItem t1 = new JMenuItem("关于"); // 菜单项
		t1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"本工具为自动发送支付请求，目前合入支付宝、微信、QQ、京东扫码支付，APP支付等\n本工具自动获取KEY【测试直接读到mysql，线上设定11个测试商户号】\n ", "使用说明",
						JOptionPane.ERROR_MESSAGE);

			}
		});
		JMenuItem t2 = new JMenuItem("退出");// 菜单项
		t2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.exit(0);// 这里是整个程序都关闭了

			}
		});

		JMenuItem t3 = new JMenuItem("RSA生成工具");// 菜单项
		t3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				RSACreatTool rsaCreatTool = new RSACreatTool();
				rsaCreatTool.setVisible(true);

			}
		});
		JMenuItem t4 = new JMenuItem("Sign生成工具");// 菜单项
		t4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreatSign creatSign=new CreatSign();
				creatSign.setVisible(true);

			}
		});
		
		JMenuItem t5 = new JMenuItem("对帐生成工具");// 菜单项
		t5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				CreatSign creatSign=new CreatSign();
//				creatSign.setVisible(true);
				ComP comP= new ComP();
				comP.setVisible(true);

			}
		});
		
		JMenuItem t6 = new JMenuItem("http参数请求工具");// 菜单项
		t6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				CreatSign creatSign=new CreatSign();
//				creatSign.setVisible(true);
				httppost httppost=new httppost();
				httppost.setVisible(true);

			}
		});

		JMenuItem t7 = new JMenuItem("https参数请求工具");// 菜单项
		t7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				CreatSign creatSign=new CreatSign();
//				creatSign.setVisible(true);
				httpspost httpspost =new httpspost();
				httpspost.setVisible(true);

			}
		});
		
		
		JMenuItem menu8 = new JMenuItem("代付接口工具");// 菜单项
		menu8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				httpspost httpspost =new httpspost();
				httpspost.setVisible(true);

			}
		});
		JMenuItem menu9 = new JMenuItem("鉴权接口工具");// 菜单项
		menu9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				httpspost httpspost =new httpspost();
				httpspost.setVisible(true);

			}
		});
		
		jm.add(t1); // 将菜单项目添加到菜单
		jm.add(t2); // 将菜单项目添加到菜单
		menu2.add(t3);
		menu2.add(t4);
		menu2.add(t5);
		menu2.add(t6);
		menu2.add(t7);
		menu3.add(menu8);
		menu4.add(menu9);
		JMenuBar br = new JMenuBar(); // 创建菜单工具栏
		br.add(jm); // 将菜单增加到菜单工具栏
		br.add(menu2);
		br.add(menu3);
		br.add(menu4);
		this.setJMenuBar(br); // 为 窗体设置 菜单工具栏
		
		
		
		
	}
	
	public final  void chanshuselect(String env0,String signtype0){

		// TODO Auto-generated method stub
		System.out.println((String) comboBox_signtype.getSelectedItem());
		String cpid = textField_cpId.getText().toString();
		if (env0.equals((String) comboBox_1.getSelectedItem())) {// 若为测试环境
			
			Map<String, String> dd = get.GetValue(cpid);
			if ("RSA".equals(dd.get("sign_type"))) {// 如果签名方式为RSA
				IniEditor ini = new IniEditor();
				try {
					ini.load(new File("key.ini"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String privatekey = ini.get("signkey", "RSAPrivateKey");
				textField_paykey.setText(privatekey);
			} else {// 如果签名方式为MD5
				textField_paykey.setText(dd.get("pay_key"));
			}
		} else {// 若为非测试环境

			if (signtype0.equals((String) comboBox_signtype.getSelectedItem())) {
				IniEditor ini = new IniEditor();
				try {
					ini.load(new File("key.ini"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String privatekey = ini.get("signkey", "RSAPrivateKey");
				textField_paykey.setText(privatekey);
			} else {//
				
				if (cpid.equals("1")) {
					textField_paykey.setText("42a17e8d3cbadec679d0833b20a9eaf2");

				} else if (cpid.equals("2")) {
					textField_paykey.setText("75a3ad1f3f584f5b6e153f040685c91d");

				} else if (cpid.equals("660001")) {
					textField_paykey.setText("4aa8c684bdd32958fdf5864a4bb1819e");

				} else if (cpid.equals("660002")) {
					textField_paykey.setText("134f3368f38a37e959af132c6973d71e");
				} else if (cpid.equals("660003")) {
					textField_paykey.setText("7f0642c24f04f0e64d491f2e95e698fc");
				} else if (cpid.equals("660004")) {
					textField_paykey.setText("1bf357ee0f2873f96ecc1f8e57c6d172");
				} else if (cpid.equals("660005")) {
					textField_paykey.setText("ca866e30bedf1faa742108f30fefe4fd");
				} else if (cpid.equals("660006")) {
					textField_paykey.setText("cdaf3274b6222cf20e62b926bf6362b6");
				} else if (cpid.equals("660007")) {
					textField_paykey.setText("e1dfc8b74ed621c6b7206ce999dee635");
				} else if (cpid.equals("660008")) {
					textField_paykey.setText("4402b5292dfbac25940ebd8185f64c3f");
				} else if (cpid.equals("660009")) {
					textField_paykey.setText("4fbe00e0d51ac5690b225be2c10119cc");
				} else if (cpid.equals("660010")) {
					textField_paykey.setText("e3bb47c6d103a1e273c5a7742353e0bd");

				} else if (cpid.equals("660011")) {
					textField_paykey.setText("89b9ccd1f1be918843b255094b96d9ac");
					JOptionPane.showMessageDialog(null, "此CPID为乐游娱乐商户号，请谨慎使用", "提示", JOptionPane.ERROR_MESSAGE);

				} else if (cpid.equals("660012")) {
					textField_paykey.setText("2ff2546dbfe3dba9d7211822ea5d22d7");
					JOptionPane.showMessageDialog(null, "此CPID为华海律正商户号，请谨慎使用", "提示", JOptionPane.ERROR_MESSAGE);

				} else if (cpid.equals("660013")) {
					textField_paykey.setText("890de5ef8582031eb597c1d22ab2c3eb");
					JOptionPane.showMessageDialog(null, "此CPID为华体星空商户号，请谨慎使用", "提示", JOptionPane.ERROR_MESSAGE);

				}

			}
		}

	
	}
}
