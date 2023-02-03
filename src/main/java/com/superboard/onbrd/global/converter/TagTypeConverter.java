package com.superboard.onbrd.global.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.superboard.onbrd.tag.entity.TagType;

@Converter
public class TagTypeConverter implements AttributeConverter<TagType, String> {
	@Override
	public String convertToDatabaseColumn(TagType attribute) {
		return attribute.name();
	}

	@Override
	public TagType convertToEntityAttribute(String dbData) {
		return TagType.valueOf(dbData.toUpperCase());
	}
}
