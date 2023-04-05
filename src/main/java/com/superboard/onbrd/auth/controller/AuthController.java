package com.superboard.onbrd.auth.controller;

import static com.superboard.onbrd.auth.util.AuthProperties.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.auth.dto.AuthCodeCheckRequest;
import com.superboard.onbrd.auth.dto.AuthCodeSendingResponse;
import com.superboard.onbrd.auth.dto.PasswordCheckCommand;
import com.superboard.onbrd.auth.dto.PasswordCheckRequest;
import com.superboard.onbrd.auth.dto.SignInRequest;
import com.superboard.onbrd.auth.dto.SignInResult;
import com.superboard.onbrd.auth.dto.TokenDto;
import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.auth.service.AuthService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Authentication", description = "인증")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
	private final AuthService authService;

	@Tag(name = "Authentication")
	@ApiOperation(value = "로그인")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "로그인 성공/로그인한 멤버 ID 응답",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class),
				examples = {@ExampleObject(value = "1")}),
			headers = {
				@Header(name = "Authorization", description = "AccessToken", schema = @Schema(implementation = String.class)),
				@Header(name = "RefreshToken", schema = @Schema(implementation = String.class))}),
		@ApiResponse(responseCode = "401", description = "잘못된 비밀번호"),
		@ApiResponse(responseCode = "404", description = "가입되지 않은 이메일")
	})
	@PostMapping("/sign-in")
	public ResponseEntity<Long> signIn(@RequestBody SignInRequest request) {
		SignInResult signInResult = authService.signIn(request);

		HttpHeaders headers = addTokensToHeader(signInResult.getTokens());

		return ResponseEntity.ok()
			.headers(headers)
			.body(signInResult.getId());
	}

	@Tag(name = "Authentication")
	@ApiOperation(value = "로그아웃")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@ApiResponse(responseCode = "200", description = "로그아웃 성공")
	@PatchMapping("/sign-out")
	public ResponseEntity<Void> signOut(@AuthenticationPrincipal MemberDetails memberDetails) {
		authService.signOut(memberDetails.getEmail());

		return ResponseEntity.ok().build();
	}

	@Tag(name = "Authentication")
	@ApiOperation(value = "토큰 재발급")
	@ApiImplicitParams(value = {
		@ApiImplicitParam(paramType = "header", name = "RefreshToken", required = true, dataTypeClass = String.class)
	})
	@ApiResponse(responseCode = "200", description = "토큰 재발급 성공",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)),
		headers = {
			@Header(name = "Authorization", description = "AccessToken", schema = @Schema(implementation = String.class)),
			@Header(name = "RefreshToken", schema = @Schema(implementation = String.class))})
	@GetMapping("/token-reissue")
	public ResponseEntity<Void> reissueTokens(@RequestHeader("RefreshToken") String refreshToken) {
		TokenDto reissued = authService.reissueTokens(refreshToken);

		HttpHeaders headers = addTokensToHeader(reissued);

		return ResponseEntity.ok()
			.headers(headers)
			.build();
	}

	@Tag(name = "Authentication")
	@ApiOperation(value = "비밀번호 재확인")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "비밀번호 일치"),
		@ApiResponse(responseCode = "401", description = "비밀번호 불일치")
	})
	@PostMapping("/password-check")
	public ResponseEntity<Void> reconfirmPassword(
		@AuthenticationPrincipal MemberDetails memberDetails,
		@RequestBody PasswordCheckRequest request) {

		authService.reconfirmPassword(
			PasswordCheckCommand.of(memberDetails.getEmail(), request));

		return ResponseEntity.ok().build();
	}

	@Tag(name = "Authentication")
	@ApiOperation(value = "인증 코드 메일 발송")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AuthCodeSendingResponse.class)))
	@GetMapping("/code")
	public ResponseEntity<AuthCodeSendingResponse> sendAuthCodeMail(@RequestParam String email) {
		AuthCodeSendingResponse response = authService.sendAuthCodeMail(email);

		return ResponseEntity.ok(response);
	}

	@Tag(name = "Authentication")
	@ApiOperation(value = "인증 코드 검증")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "인증 코드 일치"),
		@ApiResponse(responseCode = "401", description = "인증 코드 불일치")
	})
	@PostMapping("/code-check")
	public ResponseEntity<Void> checkAuthCode(@RequestBody AuthCodeCheckRequest request) {
		authService.checkAuthCode(request);

		return ResponseEntity.ok().build();
	}

	private HttpHeaders addTokensToHeader(TokenDto tokens) {
		HttpHeaders headers = new HttpHeaders();

		headers.add(AUTH_HEADER, TOKEN_TYPE + " " + tokens.getAccessToken());
		headers.add(REFRESH_TOKEN_HEADER, tokens.getRefreshToken());

		return headers;
	}
}
