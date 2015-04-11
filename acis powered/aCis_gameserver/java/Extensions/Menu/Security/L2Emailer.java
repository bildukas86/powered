package Extensions.Menu.Security;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import net.sf.l2j.Config;

public class L2Emailer
{
	public static void sendL2Mail(String[] towho, String sub, String text) throws MessagingException
	{
		String host = "smtp.gmail.com";
		String from = Config.EMAIL_USER;
		String pass = Config.EMAIL_PASS;
		String[] email = towho;
		String subject = sub;
		String content = text;
		
		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		
		InternetAddress[] toAddress = new InternetAddress[email.length];
		
		for (int i = 0; i < email.length; i++)
		{
			toAddress[i] = new InternetAddress(email[i]);
		}
		
		for (int i = 0; i < toAddress.length; i++)
		{
			message.addRecipient(Message.RecipientType.TO, toAddress[i]);
		}
		
		message.setSubject(subject);
		message.setText(content);
		Transport transport = session.getTransport("smtp");
		transport.connect(host, from, pass);
		transport.sendMessage(message, message.getAllRecipients());
		System.out.println("An email was successfully sent to: " + AccountManager.email);
		transport.close();
	}
}