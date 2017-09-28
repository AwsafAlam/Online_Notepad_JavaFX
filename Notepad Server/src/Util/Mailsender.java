package Util;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Mailsender extends Thread{

    String  d_email = "awsafdrive@gmail.com",
            d_password = "myroll1505114",
            d_host = "smtp.gmail.com",
            d_port  = "465",
            m_subject,m_text;
    String m_to ,Username;
            
    
    public Mailsender(String mail , int i , String username) {
        m_to = mail;
        Username = username;
        if(i==0){ // Register
            m_subject = "Registration Complete";
            m_text = "<html dir=\"ltr\"><head></head><body contenteditable=\"true\"><p><font face=\"Segoe UI\"><font size=\"6\" color=\"#664db3\">Hello</font> , </font><font face=\"Century Gothic\" color=\"#cc3333\">Welcome to My Drive&nbsp;</font></p><p><font color=\"#333333\" face=\"Consolas\">You have just registered via this email account</font></p><p><font color=\"#333333\" face=\"Consolas\">Hope you enjoy the app.</font></p><p><font color=\"#333333\" face=\"Consolas\">With Regards,</font></p><p><font face=\"Candara\" color=\"#e6e64d\">Md Awsaf Alam</font></p><p><font face=\"Candara\" color=\"#e6e64d\">BUET CSE'15</font></p><p><font color=\"#333333\" face=\"Consolas\"><br></font></p></body></html>\n" +
"";
        }
        else if(i == 1){ // login
         m_subject = "Login Successful" ;  
         m_text = "<html dir=\"ltr\"><head></head><body contenteditable=\"true\"><p><font face=\"Segoe UI\"><font size=\"6\" color=\"#664db3\">Hello</font> , </font><font face=\"Century Gothic\" color=\"#cc3333\">Welcome to My Drive&nbsp;</font></p><p><font color=\"#333333\" face=\"Consolas\">Thank you for loging in again.</font></p><p><font color=\"#333333\" face=\"Consolas\">Hope you enjoy the app.</font></p><p><font color=\"#333333\" face=\"Consolas\">With Regards,</font></p><p><font face=\"Candara\" color=\"#e6e64d\">Md Awsaf Alam</font></p><p><font face=\"Candara\" color=\"#e6e64d\">BUET CSE'15</font></p><p><font color=\"#333333\" face=\"Consolas\"><br></font></p></body></html>\n" +
"";
        }
        
        
        
        
    }

    @Override
    public void run() {
    Properties props = new Properties();
        props.put("mail.smtp.user", d_email);
        props.put("mail.smtp.host", d_host);
        props.put("mail.smtp.port", d_port);
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.socketFactory.port", d_port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        SecurityManager security = System.getSecurityManager();

        try
        {
            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth);
            session.setDebug(true);
            MimeMessage msg = new MimeMessage(session);
//            msg.setText(m_text);
            msg.setContent(m_text,"text/html" );
            
            msg.setSubject(m_subject);
            msg.setFrom(new InternetAddress(d_email));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(m_to));
            Transport.send(msg);
        }
        catch (Exception mex)
        {
            mex.printStackTrace();
        } 
    
    
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator
    {
        public PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication(d_email, d_password);
        }
    }

    
}
