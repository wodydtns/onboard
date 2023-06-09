package com.superboard.onbrd.notification.dto.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationCheckCommand {
	private String email;
	private Long notificationId;
}
