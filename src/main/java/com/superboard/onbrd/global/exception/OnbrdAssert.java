package com.superboard.onbrd.global.exception;

import org.springframework.util.Assert;

public class OnbrdAssert extends Assert {
	public static void state(boolean expression, ExceptionCode exceptionCode) {
		if (!expression) {
			throw new BusinessLogicException(exceptionCode);
		}
	}

	public static void state(boolean expression, ExceptionCode exceptionCode, String msg) {
		if (!expression) {
			throw new BusinessLogicException(exceptionCode, msg);
		}
	}
}
