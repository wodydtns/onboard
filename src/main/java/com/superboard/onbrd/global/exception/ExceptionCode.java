package com.superboard.onbrd.global.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
	DUPLICATED_EMAIL(CONFLICT, "DUPLICATED_EMAIL"),
	NOT_SIGNED_EMAIL(CONFLICT, "NOT_SIGNED_EMAIL"),
	DUPLICATED_NICKNAME(CONFLICT, "DUPLICATED_NICKNAME"),
	MEMBER_NOT_FOUND(NOT_FOUND, "MEMBER_NOT_FOUND"),

	PASSWORD_NOT_FOUND(NOT_FOUND, "PASSWORD_NOT_FOUND"),
	SAME_AS_PREVIOUS_PASSWORD(CONFLICT, "SAME_AS_PREVIOUS_PASSWORD"),
	SOCIAL_MEMBER_NOT_HAVING_PASSWORD(BAD_REQUEST, "SOCIAL_MEMBER_DOES_NOT_HAVE_A_PASSWORD"),
	PASSWORD_CHANGE_OVERDUE(CONFLICT, "PASSWORD_CHANGE_OVERDUE"),

	INVALID_PASSWORD(UNAUTHORIZED, "INVALID_PASSWORD"),
	INVALID_AUTH_CODE(UNAUTHORIZED, "INVALID_AUTH_CODE"),
	SIGN_OUT_ACCESS_TOKEN(UNAUTHORIZED, "SIGN_OUT_TOKEN"),
	INVALID_REFRESH_TOKEN(UNAUTHORIZED, "INVALID_REFRESH_TOKEN"),
	EXPIRED_REFRESH_TOKEN(UNAUTHORIZED, "EXPIRED_REFRESH_TOKEN"),
	EXPIRED_RESET_TOKEN(UNAUTHORIZED, "EXPIRED_RESET_TOKEN"),

	OAUTH_MEMBER_NOT_SOCIAL_ALREADY_SIGN_UP(CONFLICT, "OAUTH_MEMBER_NOT_SOCIAL_ALREADY_SIGN_UP"),
	INVALID_OAUTH_ID(UNAUTHORIZED, "INVALID_OAUTH_ID"),

	MAIL_SENDING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "MAIL_SENDING_FAILED"),

	BOARDGAME_NOT_FOUND(NOT_FOUND, "BOARDGAME_NOT_FOUND"),

	REVIEW_NOT_FOUND(NOT_FOUND, "REVIEW_NOT_FOUND"),

	LIKE_OWN_REVIEW_NOT_PERMITTED(CONFLICT, "LIKE_OWN_REVIEW_NOT_PERMITTED"),

	COMMENT_NOT_FOUND(NOT_FOUND, "COMMENT_NOT_FOUND"),

	LIKE_OWN_COMMENT_NOT_PERMITTED(CONFLICT, "LIKE_OWN_COMMENT_NOT_PERMITTED"),

	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SEVER_ERROR");

	private final HttpStatus status;
	private final String message;
}
