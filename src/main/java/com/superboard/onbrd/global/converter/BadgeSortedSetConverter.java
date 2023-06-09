package com.superboard.onbrd.global.converter;

import java.util.SortedSet;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.superboard.onbrd.member.entity.Badge;

import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class BadgeSortedSetConverter implements AttributeConverter<SortedSet<Badge>, String> {
	private final ObjectMapper objectMapper;

	@Override
	public String convertToDatabaseColumn(SortedSet<Badge> attribute) {
		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public SortedSet<Badge> convertToEntityAttribute(String dbData) {

		try {
			return objectMapper.readValue(dbData, new TypeReference<SortedSet<Badge>>() {
			});
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
