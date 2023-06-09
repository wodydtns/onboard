package com.superboard.onbrd.notification.repository;

import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.notification.dto.query.NotificationGetQuery;
import com.superboard.onbrd.notification.dto.response.NotificationGetResponse;

public interface CustomNotificationRepository {
	OnbrdSliceResponse<NotificationGetResponse> getNotifications(NotificationGetQuery query);
}
