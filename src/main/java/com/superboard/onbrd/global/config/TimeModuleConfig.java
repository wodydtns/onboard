package com.superboard.onbrd.global.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Configuration
public class TimeModuleConfig {

	@Bean
	public JavaTimeModule javaTimeModule() {
		JavaTimeModule timeModule = new JavaTimeModule();

		LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(
			DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'z'"));

		timeModule.addSerializer(LocalDateTime.class, localDateTimeSerializer);

		return timeModule;
	}
}
