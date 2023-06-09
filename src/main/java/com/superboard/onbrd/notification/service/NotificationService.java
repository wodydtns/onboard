package com.superboard.onbrd.notification.service;

import com.superboard.onbrd.notification.dto.command.NotificationCheckCommand;
import com.superboard.onbrd.notification.entity.Notification;

public interface NotificationService {
	Notification findVerifiedOneById(Long id);

	Long check(NotificationCheckCommand command);
}
