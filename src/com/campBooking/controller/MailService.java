package com.campBooking.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailService {

	// 設定傳送郵件:至收信人的Email信箱,Email主旨,Email內容
	public void sendMail(String to, String subject, List<Map> orderlist,String name,String total,String campName,String date) {

		try {
			// 設定使用SSL連線至 Gmail smtp Server
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			// ●設定 gmail 的帳號 & 密碼 (將藉由你的Gmail來傳送Email)
			// ●須將myGmail的【安全性較低的應用程式存取權】打開
			final String myGmail = "zaku7343@gmail.com";
			final String myGmail_password = "7343zaku";
			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(myGmail, myGmail_password);
				}
			});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myGmail));
			Transport transport = session.getTransport();

			// 設定信中的主旨
			message.setSubject(subject);

			// 文字部份，注意 img src 部份要用 cid:接下面附檔的header
			MimeBodyPart textPart = new MimeBodyPart();
			StringBuffer html = new StringBuffer();
			html.append(
					"<body marginheight=\"0\" topmargin=\"0\" marginwidth=\"0\" style=\"margin: 0px; background-color: #f2f3f8;\" bgcolor=\"#eaeeef\" leftmargin=\"0\">\r\n"
							+ "    <!--100% body table-->\r\n"
							+ "    <table cellspacing=\"0\" border=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#f2f3f8\" style=\"@import url(https://fonts.googleapis.com/css?family=Rubik:300,400,500,700|Open+Sans:300,400,600,700); font-family: 'Open Sans', sans-serif;\">\r\n"
							+ "        <tr>\r\n" + "            <td>\r\n"
							+ "                <table style=\"background-color: #f2f3f8; max-width:670px; margin:0 auto;\" width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
							+ "                    <tr>\r\n"
							+ "                        <td style=\"height:80px;\">&nbsp;</td>\r\n"
							+ "                    </tr>\r\n" + "                    <tr>\r\n"
							+ "                        <td style=\"text-align:center;\">\r\n"
							+ "                            <a href=\"#\" title=\"logo\" target=\"_blank\">");
			html.append("<img width=\"100px\" title=\"logo\" alt=\"logo\" src='cid:image'/><br>");
			html.append(" </a>\r\n" + "                        </td>\r\n" + "                    </tr>\r\n"
					+ "                    <tr>\r\n" + "                        <td height=\"20px;\">&nbsp;</td>\r\n"
					+ "                    </tr>\r\n" + "                    <tr>\r\n"
					+ "                        <td>\r\n"
					+ "                            <table width=\"95%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width:600px; background:#fff; border-radius:3px; text-align:left;-webkit-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);-moz-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);box-shadow:0 6px 18px 0 rgba(0,0,0,.06);\">\r\n"
					+ "                                <tr>\r\n"
					+ "                                    <td style=\"padding:40px;\">\r\n"
					+ "                                        <table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
					+ "                                            <tr>\r\n"
					+ "                                                <td>\r\n"
					+ "                                                    <h1 style=\"color: #1e1e2d; font-weight: 500; margin: 0;margin-bottom: 10px; font-size: 32px;font-family:'Rubik',sans-serif;\">Hi"+name+",</h1>\r\n"
					+ "                                                    <p style=\"font-size:15px; color:#455056; line-height:10px; margin:8px 0 30px;\">預訂營地名稱:"+ campName+"</p>\r\n"
					+ "                                                    <p style=\"font-size:15px; color:#455056; line-height:10px; margin:8px 0 30px;\">您的訂購日期:"+date+"</p>\r\n"
					+ "                                                </td>\r\n"
					+ "                                            </tr>\r\n" + "\r\n" + "\r\n"
					+ "                                            <!-- 表格內容 -->\r\n"
					+ "                                            <tr>\r\n"
					+ "                                                <td>\r\n"
					+ "                                                    <table width=\"100%\" border=\"1\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
					+ "                                                        <tr>\r\n"
					+ "                                                            <th>營位名稱</th>\r\n"
					+ "                                                            <th>訂位帳數</th>\r\n"
					+ "                                                            <th>加購人頭數</th>\r\n"
					+ "                                                            <th>小計</th>\r\n" + "\r\n" + "\r\n"
					+ "\r\n" + "                                                        </tr>");
//訂單明細包裝
			for (Map map : orderlist) {
				html.append("<tr>");
				html.append("<td>" + map.get("areaName")+ "</td>");
				html.append("<td>" + map.get("orderSeat")+  "</td>");
				html.append("<td>" + map.get("personNum") + "</td>");
				html.append("<td>" + map.get("subtotal")+ "</td>");
				html.append("</tr>");
			}
//總金額計算
			html.append("<tr>");
			html.append("<td></td>");
			html.append("<td></td>");
			html.append("<td>小計</td>");
			html.append("<td>" + total + "</td>");
			html.append("</tr>");

			html.append("\r\n" + "</td>\r\n" + "</tr>\r\n" + "</table>\r\n"
					+ "                                    </td>\r\n" + "                                </tr>\r\n"
					+ "                            </table>\r\n" + "                        </td>\r\n"
					+ "                    </tr>\r\n" + "                    <tr>\r\n"
					+ "                        <td style=\"height:25px;\">&nbsp;</td>\r\n"
					+ "                    </tr>\r\n" + "                    <tr>\r\n"
					+ "                        <td style=\"text-align:center;\">\r\n"
					+ "                            <p style=\"font-size:14px; color:#455056bd; line-height:18px; margin:0 0 0;\">&copy; <strong>www.rakeshmandal.com</strong></p>\r\n"
					+ "                        </td>\r\n" + "                    </tr>\r\n"
					+ "                    <tr>\r\n"
					+ "                        <td style=\"height:80px;\">&nbsp;</td>\r\n"
					+ "                    </tr>\r\n" + "                </table>\r\n" + "            </td>\r\n"
					+ "        </tr>\r\n" + "    </table>\r\n" + "</body>");

			textPart.setContent(html.toString(), "text/html; charset=UTF-8");

			// 圖檔部份，注意 html 用 cid:image，則header要設<image>
			MimeBodyPart picturePart = new MimeBodyPart();
			File f = new File("C:\\Users\\Tibame_T14\\Desktop\\MyDemo\\demo6\\img\\icon\\chuba_logo.png");
			FileDataSource fds = new FileDataSource(f);
			picturePart.setDataHandler(new DataHandler(fds));
			picturePart.setFileName(fds.getName());
			picturePart.setHeader("Content-ID", "<image>");

			// 準備中
			Multipart email = new MimeMultipart();
			email.addBodyPart(textPart);
			email.addBodyPart(picturePart);

			message.setContent(email);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			transport.connect();
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();
			;

			System.out.println("email傳送成功!");
		} catch (Exception e) {
			System.out.println("email傳送失敗!");
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {

		String to = "ricky60607@gmail.com";

		String subject = "密碼通知";

		String ch_name = "peter1";
		String passRandom = "111";
		String messageText = "Hello! " + ch_name + " 請謹記此密碼: " + passRandom + "\n" + " (已經啟用)";

		MailService mailService = new MailService();
		List<Map> list=new ArrayList<Map>();
		Map<String, String> map=new HashMap<String, String>();
		map.put("areaName", "A區");
		map.put("orderSeat", "1帳");
		map.put("personNum", "1人");
		map.put("subtotal", "1000");
		list.add(map);
//		mailService.sendMail(to, subject,list);

	}

}
