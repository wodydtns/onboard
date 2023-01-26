package com.superboard.onbrd.mail.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MailSendingEvent {
	private String receiver;
	private String title;
	private String content;
}
