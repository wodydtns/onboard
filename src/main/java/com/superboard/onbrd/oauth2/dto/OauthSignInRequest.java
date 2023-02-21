package com.superboard.onbrd.oauth2.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OauthSignInRequest {
	private String email;
	private String oauthId;
}
