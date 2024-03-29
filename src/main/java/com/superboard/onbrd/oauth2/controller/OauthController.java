package com.superboard.onbrd.oauth2.controller;

import static com.superboard.onbrd.auth.util.AuthProperties.*;
import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.auth.dto.SignInResult;
import com.superboard.onbrd.auth.dto.TokenDto;
import com.superboard.onbrd.oauth2.dto.OauthIntegrateRequest;
import com.superboard.onbrd.oauth2.dto.OauthSignInRequest;
import com.superboard.onbrd.oauth2.dto.OauthSignUpRequest;
import com.superboard.onbrd.oauth2.service.OauthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/oauth")
@RequiredArgsConstructor
@Validated
public class OauthController {
	private final OauthService oauthService;

	@PostMapping("/sign-in")
	public ResponseEntity<Long> signIn(@RequestBody OauthSignInRequest request) {
		SignInResult result = oauthService.signIn(request);
		HttpHeaders headers = addTokensToHeader(result.getTokens());

		return ResponseEntity.ok()
			.headers(headers)
			.body(result.getId());
	}

	@PostMapping("/sign-up")
	public ResponseEntity<Long> signUp(@RequestBody OauthSignUpRequest request) {
		Long createdId = oauthService.signUp(request);

		return ResponseEntity.status(CREATED)
			.body(createdId);
	}

	@PatchMapping("/integrate")
	public ResponseEntity<Long> integrateSocial(@RequestBody OauthIntegrateRequest request) {
		Long integratedId = oauthService.integrate(request);

		return ResponseEntity.ok(integratedId);
	}

	private HttpHeaders addTokensToHeader(TokenDto tokens) {
		HttpHeaders headers = new HttpHeaders();

		headers.add(AUTH_HEADER, TOKEN_TYPE + " " + tokens.getAccessToken());
		headers.add(REFRESH_TOKEN_HEADER, tokens.getRefreshToken());

		return headers;
	}
}
