package miniprojet;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class JavaEmailSender {
 
    private static String emailAddressTo = new String();
    private String msgSubject = new String();
    private String msgText = new String();

    final String USER_NAME = "miniprojet49@gmail.com";   //User name of the Goole(gmail) account
    final String PASSSWORD = "miniprojetjava";  //Password of the Goole(gmail) account
    final String FROM_ADDRESS = "miniprojet49@gmail.com";  //From addresss
 
    public JavaEmailSender() {
    }

    @SuppressWarnings("static-access")
	public JavaEmailSender(String mail) {
    	this.emailAddressTo=mail;
	}

	public static void main(String[] args) {
      JavaEmailSender email = new JavaEmailSender();
      JavaEmailSender email2=new JavaEmailSender();
     //Sending test email
      email2.createAndSendEmail(emailAddressTo, "Mail d’alerte", "L'Etudiant est absent");
     /* email.createAndSendEmail("miniprojet49@gmail.com", "Test email subject",
      "Congratulations !!! \nThis is test email sent by java class.");*/
    }

    public void createAndSendEmail(String emailAddressTo, String msgSubject, String msgText) {
        JavaEmailSender.emailAddressTo = emailAddressTo;
        this.msgSubject = msgSubject;
        this.msgText = msgText;
        sendEmailMessage();
    }
 
    @SuppressWarnings("static-access")
	private void sendEmailMessage() {
     
     //Create email sending properties
     Properties props = new Properties();
     props.put("mail.smtp.auth", "true");
     props.put("mail.smtp.starttls.enable", "true");
     props.put("mail.smtp.host", "smtp.gmail.com");
     props.put("mail.smtp.port", "587");
  
    Session session = Session.getInstance(props,
    new javax.mail.Authenticator() {
    protected PasswordAuthentication getPasswordAuthentication() {
    return new PasswordAuthentication("miniprojet49@gmail.com", "xndtcmtlltwbtumw"
    		+ "");
   }
    });

  try {

     Message message = new MimeMessage(session);
     message.setFrom(new InternetAddress("miniprojet49@gmail.com")); //Set from address of the email
     message.setContent(msgText,"text/html"); //set content type of the email
     
    message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(this.emailAddressTo/*"miniprojet49@gmail.com"*/)); //Set email recipient
    
    message.setSubject("Mail d’alerte"); //Set email message subject
    Transport.send(message); //Send email message

   System.out.println("sent email successfully!");

  } catch (MessagingException e) {
       throw new RuntimeException(e);
  }
    }

    @SuppressWarnings("static-access")
	public void setEmailAddressTo(String emailAddressTo) {
        this.emailAddressTo = emailAddressTo;
    }

    public void setSubject(String subject) {
        this.msgSubject = subject;
    }

    public void setMessageText(String msgText) {
        this.msgText = msgText;
    }
 
}

