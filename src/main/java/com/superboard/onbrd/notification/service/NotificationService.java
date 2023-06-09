package com.superboard.onbrd.notification.service;

import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.notification.dto.command.NotificationCheckCommand;
import com.superboard.onbrd.notification.dto.query.NotificationGetQuery;
import com.superboard.onbrd.notification.dto.response.NotificationGetResponse;
import com.superboard.onbrd.notification.entity.Notification;

public interface NotificationService {
	Notification findVerifiedOneById(Long id);

	Long check(NotificationCheckCommand command);

	OnbrdSliceResponse<NotificationGetResponse> getNotifications(NotificationGetQuery query);
}
