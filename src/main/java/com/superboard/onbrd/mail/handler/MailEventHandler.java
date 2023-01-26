package com.superboard.onbrd.mail.handler;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.superboard.onbrd.mail.dto.MailSendingEvent;
import com.superboard.onbrd.mail.service.MailService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailEventHandler {
	private final MailService mailService;

	@EventListener(MailSendingEvent.class)
	public void sendMail(MailSendingEvent event) {
		mailService.sendMail(event);
	}
}
