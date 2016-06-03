package com.exa.android.softkeyboard;

public class EnviarMail {

	
	
	
	public EnviarMail() {
		// TODO Auto-generated constructor stub
	}
	

	public boolean sendEmail(String to, String from,String setpass, String subject,                                          
            String message,String attachements) throws Exception {     
EmailConfig mail = new EmailConfig();
if (subject != null && subject.length() > 0) {
mail.setSubject(subject);
} else {
mail.setSubject("Subject");
}

if (message != null && message.length() > 0) {
mail.setBody(message);
} else {
mail.setBody("Message");
}

mail.setTo(to);
mail.setPassword(setpass);


if (attachements != null) {
      
mail.addAttachment(attachements);

}
return mail.send();
}

}





