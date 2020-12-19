package com.example.vigil;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

	public static void sendEmail(String reci, String body) throws Exception {
		// TODO Auto-generated method stub

		
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		String account = "iiitahvac@gmail.com";
		String password = "hvac/iiita";
		
		Session session = Session.getInstance(properties, new Authenticator() {
			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("iiitahvac@gmail.com", "hvac/iiita");
				
			}
			
		
		});
		
		String recipent = reci;
		Message message = prepareMessage(session, account, recipent, body) ;
				
		Transport.send(message);
		
	}

	private static Message prepareMessage(Session session, String account, String recipent, String body) {
		// TODO Auto-generated method stub
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(account));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipent));
			//message.setRecipient(Message.RecipientType.CC, new InternetAddress("IIT2019121@iiita.ac.in"));
			//message.setRecipient(Message.RecipientType.CC, new InternetAddress("IIT2019140@iiita.ac.in"));
			//message.setRecipient(Message.RecipientType.CC, new InternetAddress("IIT2019181@iiita.ac.in"));
			message.setSubject("Alert");

			String htmlMessage = body;
			message.setContent(htmlMessage , "text/html");
			return message;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
