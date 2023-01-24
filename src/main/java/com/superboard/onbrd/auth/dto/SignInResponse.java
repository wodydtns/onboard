package com.superboard.onbrd.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInResponse {
	private Long id;
	private String accessToken;
	private String refreshToken;
}
