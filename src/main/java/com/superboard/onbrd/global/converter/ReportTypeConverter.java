package com.superboard.onbrd.global.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.superboard.onbrd.report.entity.ReportType;

@Converter
public class ReportTypeConverter implements AttributeConverter<ReportType, String> {
	@Override
	public String convertToDatabaseColumn(ReportType attribute) {
		return attribute.name();
	}

	@Override
	public ReportType convertToEntityAttribute(String dbData) {
		return ReportType.valueOf(dbData);
	}
}
