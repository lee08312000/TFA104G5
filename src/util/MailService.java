package util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.company.model.CompanyService;
import com.mallOrder.model.MallOrderService;
import com.mallOrder.model.MallOrderVO;

public class MailService {
	
	// 設定傳送郵件:至收信人的Email信箱,Email主旨,Email內容
	public void sendMailByMallOrder(String to, String subject, List<Integer> mallOrderIdList) {
		MallOrderService mallOrderSvc = new MallOrderService();
		CompanyService companySvc = new CompanyService();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			   // 設定使用SSL連線至 Gmail smtp Server
			   Properties props = new Properties();
			   props.put("mail.smtp.host", "smtp.gmail.com");
			   props.put("mail.smtp.socketFactory.port", "465");
			   props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			   props.put("mail.smtp.auth", "true");
			   props.put("mail.smtp.port", "465");

	       // ●設定 gmail 的帳號 & 密碼 (將藉由你的Gmail來傳送Email)
	       // ●須將myGmail的【安全性較低的應用程式存取權】打開
		     final String myGmail = "john08312000@gmail.com"; // 換成自己的gmail
		     final String myGmail_password = "zz96323323";
			   Session session = Session.getInstance(props, new Authenticator() {
				   protected PasswordAuthentication getPasswordAuthentication() {
					   return new PasswordAuthentication(myGmail, myGmail_password);
				   }
			   });

			   Message message = new MimeMessage(session);
			   message.setFrom(new InternetAddress(myGmail));
			   message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
			  
			   //設定信中的主旨  
			   message.setSubject(subject);
			   //設定信中的內容 
			   String mallOrderStr = "";
			   for (Integer mallOrderId : mallOrderIdList) {
				   MallOrderVO mallOrderVO = mallOrderSvc.getOneMallOrder(mallOrderId);
				   mallOrderStr += 
						   "<tr>"
						   + "<td>" + mallOrderVO.getMallOrderId() + "</td>"
						   + "<td>" + companySvc.getOneCompany(mallOrderVO.getCompanyId()).getCompanyName() + "</td>"
						   + "<td>" + mallOrderVO.getMailOrderTotalAmount() + "</td>"
						   + "<td>" + mallOrderVO.getReceiverName() + "</td>"
						   + "<td>" + mallOrderVO.getReceiverPhone() + "</td>"
						   + "<td>" + mallOrderVO.getReceiverAddress() + "</td>"
						   + "<td>" + sdf.format(mallOrderVO.getMallOrderConfirmedTime()) + "</td>"
						   + "<td>" + (mallOrderVO.getMallOrderStatus().intValue() == 0 ? "處理中" : mallOrderVO.getMallOrderStatus().intValue() == 1 ? "已確認" : mallOrderVO.getMallOrderStatus().intValue() == 2 ? "已完成" : "異常狀態") + "</td>"
						   + "<td>" + (mallOrderVO.getMallOrderDeliveryStatus().intValue() == 0 ? "未發貨" : mallOrderVO.getMallOrderDeliveryStatus().intValue() == 1 ? "已發貨" : mallOrderVO.getMallOrderDeliveryStatus().intValue() == 2 ? "已收貨" : "異常狀態") + "</td>"
						   + "</tr>";
			   }
			   String orgerTable = 
					   "<html><head><style>table,th,tr,td{border: 1px solid black; border-collapse: collapse;}</style></head>"
					   + "<body>"
					   +"<h2>訂單已成立，訂單資訊如下:</h2>"
					   + "<table>"
					   + "<tr>"
					   + "<th>訂單編號</th>" 
					   + "<th>廠商名稱</th>"
					   + "<th>總金額</th>"
					   + "<th>收件人姓名</th>"
					   + "<th>收件人電話</th>"
					   + "<th>收件人地址</th>"
					   + "<th>成立時間</th>"
					   + "<th>訂單狀態</th>"
					   + "<th>物流狀態</th>"
					   + "</tr>"
					   + mallOrderStr
					   + "</table>"
					   + "</body>"
					   + "</html>"; 
			   
			   message.setContent(orgerTable, "text/html; charset=UTF-8");
			   
			   Transport.send(message);
			   System.out.println("傳送成功!");
	     }catch (MessagingException e){
		     System.out.println("傳送失敗!");
		     e.printStackTrace();
	     }
	}
	
	
	// 設定傳送郵件:至收信人的Email信箱,Email主旨,Email內容
	public void sendMail(String to, String subject, String messageText) {
			
	   try {
		   // 設定使用SSL連線至 Gmail smtp Server
		   Properties props = new Properties();
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.socketFactory.port", "465");
		   props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.port", "465");

       // ●設定 gmail 的帳號 & 密碼 (將藉由你的Gmail來傳送Email)
       // ●須將myGmail的【安全性較低的應用程式存取權】打開
	     final String myGmail = "john08312000@gmail.com"; // 換成自己的gmail
	     final String myGmail_password = "zz96323323";
		   Session session = Session.getInstance(props, new Authenticator() {
			   protected PasswordAuthentication getPasswordAuthentication() {
				   return new PasswordAuthentication(myGmail, myGmail_password);
			   }
		   });

		   Message message = new MimeMessage(session);
		   message.setFrom(new InternetAddress(myGmail));
		   message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
		  
		   //設定信中的主旨  
		   message.setSubject(subject);
		   //設定信中的內容 
		   message.setText(messageText);

		   Transport.send(message);
		   System.out.println("傳送成功!");
     }catch (MessagingException e){
	     System.out.println("傳送失敗!");
	     e.printStackTrace();
     }
   }
	
	 public static void main (String args[]){

      String to = "john08312000@gmail.com";
      
      String subject = "密碼通知";
      
      String ch_name = "David";
      String passRandom = "111";
      String messageText = "Hello! " + ch_name + " 請謹記此密碼: " + passRandom + "\n" +" (已經啟用)"; 
       
      MailService mailService = new MailService();
//      mailService.sendMail(to, subject, messageText);
      
      
      mailService.sendMailByMallOrder(to, "Camping Paradise-商城訂單成立", Arrays.asList(71,72));

   }


}
