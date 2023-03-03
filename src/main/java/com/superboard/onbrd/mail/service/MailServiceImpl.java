package com.superboard.onbrd.mail.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;
import static com.superboard.onbrd.mail.service.MailProperties.*;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.mail.dto.MailSendingEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
	private final JavaMailSender mailSender;

	@Override
	@Async
	public void sendMail(MailSendingEvent dto) {
		MimeMessagePreparator messagePreparator = getMimeMessagePreparatorFromMailDto(dto);

		try {
			mailSender.send(messagePreparator);
		} catch (MailException e) {
			throw new BusinessLogicException(MAIL_SENDING_FAILED);
		}
	}

	private MimeMessagePreparator getMimeMessagePreparatorFromMailDto(MailSendingEvent dto) {
		MimeMessagePreparator messagePreparator;

		messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, MAIL_DEFAULT_ENCODING);

			messageHelper.setTo(dto.getReceiver());
			messageHelper.setSubject(dto.getTitle());
			messageHelper.setText(dto.getContent(), true);
			messageHelper.addInline(LOGO_CID, new ClassPathResource(LOGO_IMAGE_PATH));
		};

		return messagePreparator;
	}
}
