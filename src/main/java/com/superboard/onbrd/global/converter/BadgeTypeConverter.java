package com.superboard.onbrd.global.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.superboard.onbrd.member.entity.BadgeType;

@Converter
public class BadgeTypeConverter implements AttributeConverter<BadgeType, String> {
	@Override
	public String convertToDatabaseColumn(BadgeType attribute) {
		return attribute.name();
	}

	@Override
	public BadgeType convertToEntityAttribute(String dbData) {
		return BadgeType.valueOf(dbData);
	}
}
