package com.company.model;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailService {
 // 設定傳送郵件:至收信人的Email信箱,Email內容
 public void sendMail(String toEmail,String account,String active,String realContextPath) {
   
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
      final String myGmail = "ginny880214@gmail.com";
      final String myGmail_password = "lolipopcandy1225";
     Session session = Session.getInstance(props, new Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
       return new PasswordAuthentication(myGmail, myGmail_password);
      }
     });

     Message message = new MimeMessage(session);
     message.setFrom(new InternetAddress(myGmail));
     message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toEmail));
    
     //設定信中的主旨  
     message.setSubject("歡迎您註冊露營樂~~請由此郵件開通帳號!!");
     //設定信中的內容 
     String content = "<html><head></head><body>"
//    +"<a href='http://www.w3school.com.cn'>請點選此連結開通帳號</a>"   
       + "<a href=" + realContextPath + "/Company/VendorLoginServlet?action="+active+"&account="+account+">請點選此連結開通帳號</a>"
       + "</body></html>";
     message.setContent(content, "text/html;charset=UTF-8");

     Transport.send(message);
     System.out.println("傳送成功!");
     }catch (MessagingException e){
      System.out.println("傳送失敗!");
      e.printStackTrace();
     }
   }
}