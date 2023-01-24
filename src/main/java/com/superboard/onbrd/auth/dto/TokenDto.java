package com.superboard.onbrd.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenDto {
	private String accessToken;
	private String refreshToken;
}
