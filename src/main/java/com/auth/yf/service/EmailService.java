package com.auth.yf.service;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

import com.auth.yf.consts.Consts;
import com.sun.mail.smtp.SMTPTransport;

@Service
@Setter
@Getter
public class EmailService {

	@Value("${hostname}")
	private String host;

	public void sendNewPasswordEmail(String firstName, String email, String userName) throws MessagingException {
		Message message = createEmail(email, email, userName);
		SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(Consts.PROTOCOL);
		smtpTransport.connect(Consts.GMAIL_HOST, Consts.USERNAME, Consts.PASSWORD);
		smtpTransport.sendMessage(message, message.getAllRecipients());
		smtpTransport.close();
	}

	private Message createEmail(String firstName, String email, String userName) throws MessagingException {
		Message message = new MimeMessage(getEmailSession());
		message.setFrom(new InternetAddress(Consts.FROM));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
		message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(Consts.CC, false));
		message.setSubject("ユーザー登録");
		String text = firstName + "さん、以下のURLをクリックすると、本人確認が完了します。。\n\n URL: " + host + "/api/v1/auth/user/confirm/"
				+ userName;
		message.setText(text);
		message.setSentDate(new Date());
		message.saveChanges();
		return message;
	}

	private Session getEmailSession() {
		Properties properties = System.getProperties();
		properties.put(Consts.SMTP_HOST, Consts.GMAIL_HOST);
		properties.put(Consts.SMTP_AUTH, true);
		properties.put(Consts.SMTP_PORT, Consts.DEFOULT_PORT);
		properties.put(Consts.SMTP_TLS_ENABLED, true);
		properties.put(Consts.SMTP_TLS_REQUIRED, true);
		return Session.getInstance(properties, null);
	}
}
