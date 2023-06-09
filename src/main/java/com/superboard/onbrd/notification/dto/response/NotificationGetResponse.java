package com.superboard.onbrd.notification.dto.response;

import java.time.LocalDateTime;

import com.superboard.onbrd.notification.entity.NotificationType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationGetResponse {
	private Long id;
	private NotificationType notificationType;
	private String payload;
	private Boolean isChecked;
	private LocalDateTime pushedAt;
}
