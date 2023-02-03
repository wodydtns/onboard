package com.superboard.onbrd.global.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.superboard.onbrd.member.entity.MemberRole;

@Converter
public class MemberRoleConverter implements AttributeConverter<MemberRole, String> {
	@Override
	public String convertToDatabaseColumn(MemberRole attribute) {
		return attribute.name();
	}

	@Override
	public MemberRole convertToEntityAttribute(String dbData) {
		return MemberRole.valueOf(dbData);
	}
}
