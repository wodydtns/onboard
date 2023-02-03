package com.superboard.onbrd.global.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.superboard.onbrd.member.entity.MemberLevel;

@Converter
public class MemberLevelConverter implements AttributeConverter<MemberLevel, String> {
	@Override
	public String convertToDatabaseColumn(MemberLevel attribute) {
		return attribute.name();
	}

	@Override
	public MemberLevel convertToEntityAttribute(String dbData) {
		return MemberLevel.valueOf(dbData);
	}
}
