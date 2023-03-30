package com.superboard.onbrd.oauth2.dto;

import com.superboard.onbrd.oauth2.entity.OauthProvider;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OauthIntegrateRequest {
	private String email;
	private String oauthId;
	private OauthProvider provider;
}
