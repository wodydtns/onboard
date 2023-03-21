package com.superboard.onbrd.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {
	@ExceptionHandler(BusinessLogicException.class)
	public ResponseEntity<String> handleBusinessLogicException(BusinessLogicException e) {
		log.error("BusinessLogicException occurs: {}", e.getExceptionCode());

		return ResponseEntity.status(e.getStatus())
			.body(e.getMessage());
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
		e.printStackTrace();

		return ResponseEntity.internalServerError().body(e.getMessage());
	}
}
