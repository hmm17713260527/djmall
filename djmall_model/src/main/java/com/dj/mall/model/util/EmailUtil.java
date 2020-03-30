package com.dj.mall.model.util;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import java.util.Date;
import java.util.Properties;

public class EmailUtil {

	private static final String nickname = "hzm";
	private static final String username = "2101265151@qq.com";
	private static final String password = "cuekzsehpjyvbdij";
	private static final String protocol = "smtp";
	private static final String host = "smtp.qq.com";
	private static final String port = "25";




	public static boolean sendEmail(String to, String subject) {
		try {
			String content ="<a href=\"http://127.0.0.1:8081/admin/auth/user/toActivate/"+ to +"\">点击激活</a>";
			//System.out.println(content);
			Session session = createSession();
			session.setDebug(true);
			MimeMessage message = createMessage(session, to, subject, content);
			Transport.send(message);
			return true;
		} catch (Exception e) {
			System.out.println("�����ʼ��쳣==��");
			e.printStackTrace();
			return false;
		} finally {
			System.out.println("�ʼ����ͽ���...");
		}
	}
	public static Session createSession() {
		// ��װ���Բ���
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", protocol); // �ʼ������Э��
		props.setProperty("mail.smtp.host", host); // �ʼ��ķ�����
		props.setProperty("mail.smtp.port", port); // �ʼ��ļ����˿�
		props.setProperty("mail.smtp.auth", "true"); // �Ƿ���Ҫ��֤����ΪTRUE��ʹ����Ȩ�뷢���ʼ���Ҫ��֤
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		return session;
	}

	public static MimeMessage createMessage(Session session, String to,
			String subject, String content) throws Exception {
		MimeMessage message = new MimeMessage(session);

		if (!nickname.isEmpty()) {
			message.setFrom(new InternetAddress(MimeUtility.encodeText(nickname) + " <" + username + ">"));
		} else {
			message.setFrom(new InternetAddress(username));
		}
		message.setRecipients(Message.RecipientType.TO, to);
		message.setSentDate(new Date());
		message.setSubject(subject);
		message.setContent(content, "text/html; charset=UTF-8");
		message.saveChanges();
		return message;
	}
}
