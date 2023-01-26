package com.superboard.onbrd.auth.util;

import static com.superboard.onbrd.auth.util.AuthCodeMailProperties.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import com.superboard.onbrd.mail.dto.MailSendingEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthCodeMailProvider {
	private final SecureRandom sran;
	private final ITemplateEngine templateEngine;

	public MailSendingEvent buildAuthCodeMail(String email, String code, LocalDateTime expiredAt) {
		Map<String, String> contents = new HashMap<>();
		contents.put(AUTH_CODE_KEY, code);
		contents.put(AUTH_CODE_EXPIRATION_KEY,
			expiredAt.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss")));

		String content = buildContent(contents);

		return MailSendingEvent.builder()
			.receiver(email)
			.title(AUTH_CODE_MAIL_TITLE)
			.content(content)
			.build();
	}

	@Cacheable(value = "authCodes", key = "#clientKey")
	public String getAuthCode(String clientKey) {
		StringBuffer code = new StringBuffer();

		for (int index = 0; index < AUTH_CODE_LENGTH; index++) {
			appendNextCase(code);
		}

		return code.toString();
	}

	@CacheEvict(value = "authCodes", key = "#clientKey")
	public void removeAuthCodeFromCache(String clientKey) {
	}

	private void appendNextCase(StringBuffer code) {
		switch (sran.nextInt(AUTH_CODE_CASES)) {
			case AUTH_CODE_NUMBER_CASE:
				code.append(sran.nextInt(10));
				break;
			case AUTH_CODE_UPPER_CASE:
				code.append((char)(sran.nextInt(26) + 65));
				break;
			case AUTH_CODE_LOWER_CASE:
				code.append((char)(sran.nextInt(26) + 97));
				break;
		}
	}

	private String buildContent(Map<String, String> contents) {
		Context context = new Context();

		contents.keySet().forEach(
			key -> context.setVariable(key, contents.get(key))
		);

		return templateEngine.process(AUTH_CODE_MAIL_TEMPLATE, context);
	}
}
