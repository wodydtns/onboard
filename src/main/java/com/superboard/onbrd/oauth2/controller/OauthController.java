package com.superboard.onbrd.oauth2.controller;

import static com.superboard.onbrd.auth.util.AuthProperties.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.auth.dto.SignInResultDto;
import com.superboard.onbrd.auth.dto.TokenDto;
import com.superboard.onbrd.oauth2.dto.OauthSignInRequest;
import com.superboard.onbrd.oauth2.service.OauthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/oauth")
@RequiredArgsConstructor
public class OauthController {
	private final OauthService oauthService;

	@PostMapping("/sign-in")
	public ResponseEntity<Long> signIn(@RequestBody OauthSignInRequest request) {
		SignInResultDto dto = oauthService.signIn(request);
		HttpHeaders headers = addTokensToHeader(dto.getTokens());

		return ResponseEntity.ok()
			.headers(headers)
			.build();
	}

	private HttpHeaders addTokensToHeader(TokenDto tokens) {
		HttpHeaders headers = new HttpHeaders();

		headers.add(AUTH_HEADER, TOKEN_TYPE + " " + tokens.getAccessToken());
		headers.add(REFRESH_TOKEN_HEADER, tokens.getRefreshToken());

		return headers;
	}
}
