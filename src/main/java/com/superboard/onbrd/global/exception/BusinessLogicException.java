package com.superboard.onbrd.global.exception;

import com.superboard.onbrd.global.entity.OnbrdStatus;

import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException {
	private final ExceptionCode exceptionCode;

	public BusinessLogicException(ExceptionCode exceptionCode) {
		super(exceptionCode.getMessage());
		this.exceptionCode = exceptionCode;
	}

	public BusinessLogicException(ExceptionCode exceptionCode, String message) {
		super(message);
		this.exceptionCode = exceptionCode;
	}

	public OnbrdStatus getStatus() {
		return exceptionCode.getStatus();
	}
}
