package com.superboard.onbrd.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthCodeSendingRequest {
	private String email;
	private String clientKey;
}
