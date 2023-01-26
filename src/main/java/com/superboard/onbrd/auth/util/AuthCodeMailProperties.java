package com.superboard.onbrd.auth.util;

public class AuthCodeMailProperties {
	public static final int AUTH_CODE_LENGTH = 6;
	public static final int AUTH_CODE_CASES = 2;
	public static final int AUTH_CODE_NUMBER_CASE = 0;
	public static final int AUTH_CODE_UPPER_CASE = 1;
	public static final int AUTH_CODE_LOWER_CASE = 2;

	public static final String AUTH_CODE_KEY = "code";
	public static final String AUTH_CODE_EXPIRATION_KEY = "expiration";

	public static final String AUTH_CODE_MAIL_TITLE = "Onbrd 인증 코드";
	public static final String AUTH_CODE_MAIL_TEMPLATE = "AuthCodeMail";
}
