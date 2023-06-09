package com.superboard.onbrd.notification.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.global.exception.ExceptionCode;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.notification.dto.command.NotificationCheckCommand;
import com.superboard.onbrd.notification.entity.Notification;
import com.superboard.onbrd.notification.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
	private final NotificationRepository notificationRepository;

	@Override
	@Transactional(readOnly = true)
	public Notification findVerifiedOneById(Long id) {

		return notificationRepository.findById(id)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOTIFICATION_NOT_FOUND));
	}

	@Override
	public Long check(NotificationCheckCommand command) {
		Notification notification = findVerifiedOneById(command.getNotificationId());
		validateRequester(notification.getReceiver(), command.getEmail());

		notification.check();

		return notification.getId();
	}

	private void validateRequester(Member receiver, String requesterEmail) {
		if (!receiver.hasEmail(requesterEmail)) {
			throw new AccessDeniedException("FORBIDDEN");
		}
	}
}
