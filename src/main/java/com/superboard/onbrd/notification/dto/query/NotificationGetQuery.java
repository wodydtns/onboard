package com.superboard.onbrd.notification.dto.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationGetQuery {
	private String email;
	private long offset;
	private int limit;
}
