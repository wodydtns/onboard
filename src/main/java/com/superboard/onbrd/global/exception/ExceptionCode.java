package com.superboard.onbrd.global.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
	DUPLICATED_EMAIL(CONFLICT, "DUPLICATED_EMAIL"),
	DUPLICATED_NICKNAME(CONFLICT, "DUPLICATED_NICKNAME"),
	MEMBER_NOT_FOUND(NOT_FOUND, "MEMBER_NOT_FOUND"),

	PASSWORD_NOT_FOUND(NOT_FOUND, "PASSWORD_NOT_FOUND"),
	SAME_AS_PREVIOUS_PASSWORD(CONFLICT, "SAME_AS_PREVIOUS_PASSWORD"),
	PASSWORD_CHANGE_DUE_ALREADY_EXTENDED(CONFLICT, "PASSWORD_CHANGE_DUE_ALREADY_EXTENDED"),

	INVALID_PASSWORD(UNAUTHORIZED, "INVALID_PASSWORD"),
	INVALID_AUTH_CODE(UNAUTHORIZED, "INVALID_AUTH_CODE"),
	SIGN_OUT_ACCESS_TOKEN(UNAUTHORIZED, "SIGN_OUT_TOKEN"),
	EXPIRED_REFRESH_TOKEN(UNAUTHORIZED, "EXPIRED_REFRESH_TOKEN"),

	MAIL_SENDING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "MAIL_SENDING_FAILED"),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SEVER_ERROR");

	private final HttpStatus status;
	private final String message;
}
