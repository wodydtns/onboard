package com.superboard.onbrd.auth.controller;

import static com.superboard.onbrd.auth.util.AuthProperties.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.auth.dto.AuthCodeCheckRequest;
import com.superboard.onbrd.auth.dto.AuthCodeSendingResponse;
import com.superboard.onbrd.auth.dto.PasswordCheckRequest;
import com.superboard.onbrd.auth.dto.SignInRequest;
import com.superboard.onbrd.auth.dto.SignInResponse;
import com.superboard.onbrd.auth.dto.TokenDto;
import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.auth.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AuthController {
	private final AuthService authService;

	@PostMapping("/sign-in")
	public ResponseEntity<Long> signIn(@RequestBody SignInRequest request, HttpServletResponse response) {
		SignInResponse signInResponse = authService.signIn(request);
		setTokensToHeader(response, signInResponse.getAccessToken(), signInResponse.getRefreshToken());

		return ResponseEntity.ok(signInResponse.getId());
	}

	@PatchMapping("/sign-out")
	public ResponseEntity<Void> signOut(HttpServletRequest request) {
		String accessToken = request.getHeader(AUTH_HEADER).substring(7);
		authService.signOut(accessToken);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/token-reissue")
	public ResponseEntity<Void> reissueTokens(HttpServletRequest request, HttpServletResponse response) {
		TokenDto dto = new TokenDto(
			getAccessTokenFromHeader(request), getRefreshTokenFromHeader(request));

		TokenDto reissued = authService.reissueTokens(dto);
		setTokensToHeader(response, reissued.getAccessToken(), reissued.getRefreshToken());

		return ResponseEntity.ok().build();
	}

	@PostMapping("/password-check")
	public ResponseEntity<Void> reconfirmPassword(
		@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody PasswordCheckRequest request) {

		request.setEmail(memberDetails.getEmail());
		authService.reconfirmPassword(request);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/code")
	public ResponseEntity<AuthCodeSendingResponse> sendAuthCodeMail(@RequestParam String email) {
		AuthCodeSendingResponse response = authService.sendAuthCodeMail(email);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/code-check")
	public ResponseEntity<Void> checkAuthCode(@RequestBody AuthCodeCheckRequest request) {
		authService.checkAuthCode(request);

		return ResponseEntity.ok().build();
	}

	private void setTokensToHeader(HttpServletResponse response, String accessToken, String refreshToken) {
		response.setHeader(AUTH_HEADER, TOKEN_TYPE + " " + accessToken);
		response.setHeader(REFRESH_TOKEN_HEADER, refreshToken);
	}

	private String getAccessTokenFromHeader(HttpServletRequest request) {

		return request.getHeader(AUTH_HEADER).substring(AUTH_HEADER_BEGIN_INDEX);
	}

	private String getRefreshTokenFromHeader(HttpServletRequest request) {
		return request.getHeader(REFRESH_TOKEN_HEADER);
	}
}
