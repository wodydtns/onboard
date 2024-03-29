package com.superboard.onbrd.oauth2.dto;

import com.superboard.onbrd.oauth2.entity.OauthProvider;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OauthSignInRequest {
	private String email;
	private String oauthId;
	private OauthProvider provider;
}
