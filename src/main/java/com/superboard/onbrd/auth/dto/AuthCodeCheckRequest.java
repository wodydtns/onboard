package com.superboard.onbrd.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthCodeCheckRequest {
	private String clientKey;
	private String authCode;
}
