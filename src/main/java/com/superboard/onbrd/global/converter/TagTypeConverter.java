package com.superboard.onbrd.global.converter;

import static com.superboard.onbrd.tag.entity.TagType.*;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.superboard.onbrd.tag.entity.TagType;

@Converter
public class TagTypeConverter implements AttributeConverter<TagType, String> {
	private static final BiMap<String, TagType> biMap;

	static {
		biMap = HashBiMap.create();
		biMap.put("bestPlayer", BEST_PLAYER);
		biMap.put("age", AGE);
		biMap.put("playTime", PLAYTIME);
		biMap.put("categories", CATEGORY);
	}

	@Override
	public String convertToDatabaseColumn(TagType attribute) {
		return biMap.inverse().get(attribute);
	}

	@Override
	public TagType convertToEntityAttribute(String dbData) {
		return biMap.get(dbData);
	}
}
