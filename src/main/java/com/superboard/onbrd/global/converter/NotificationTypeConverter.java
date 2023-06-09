package com.superboard.onbrd.global.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.superboard.onbrd.notification.entity.NotificationType;

@Converter
public class NotificationTypeConverter implements AttributeConverter<NotificationType, String> {
	@Override
	public String convertToDatabaseColumn(NotificationType attribute) {
		return attribute.name();
	}

	@Override
	public NotificationType convertToEntityAttribute(String dbData) {
		return NotificationType.valueOf(dbData);
	}
}
