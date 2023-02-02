package com.superboard.onbrd.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthCodeSendingResponse {
	private String clientKey;
}
