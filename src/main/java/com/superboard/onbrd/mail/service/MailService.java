package com.superboard.onbrd.mail.service;

import com.superboard.onbrd.mail.dto.MailSendingEvent;

public interface MailService {
	void sendMail(MailSendingEvent dto);
}
