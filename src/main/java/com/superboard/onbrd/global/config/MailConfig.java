package com.superboard.onbrd.global.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

	@Bean
	public JavaMailSender MailService() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		javaMailSender.setHost("smtp.naver.com");
		javaMailSender.setUsername("wodydtns");
		javaMailSender.setPassword("flsdl$Qkqn8");
		javaMailSender.setDefaultEncoding("UTF-8");
		javaMailSender.setPort(465);

		javaMailSender.setJavaMailProperties(getMailProperties());
		javaMailSender.setDefaultEncoding("UTF-8");
		return javaMailSender;
	}

	private Properties getMailProperties() {
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtp"); // 프로토콜 설정
		properties.setProperty("mail.smtp.auth", "true"); // smtp 인증
		properties.setProperty("mail.smtp.starttls.enable", "true"); // smtp strattles 사용
		properties.setProperty("mail.debug", "true"); // 디버그 사용
		properties.setProperty("mail.smtp.ssl.trust", "smtp.naver.com"); // ssl 인증 서버 (smtp 서버명)
		properties.setProperty("mail.smtp.ssl.enable", "true"); // ssl 사용

		return properties;
	}
}
