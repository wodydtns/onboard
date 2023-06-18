package com.superboard.onbrd.global.converter;

import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class ImagesJsonConverter implements AttributeConverter<List<String>, String> {
	private final ObjectMapper objectMapper;

	@Override
	public String convertToDatabaseColumn(List<String> attribute) {
		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<String> convertToEntityAttribute(String dbData) {
		/*
		try {
			return objectMapper.readValue(dbData, new TypeReference<>() {
			});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
*/
		// Check for null을 통한 해소
		List<String> attribute = null;
		if (dbData != null) {  
			try {
				attribute = objectMapper.readValue(dbData, new TypeReference<List<String>>() {});
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return attribute;
	}
}
