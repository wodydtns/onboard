package com.superboard.onbrd.member.controller;

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
import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.auth.service.AuthService;
import com.superboard.onbrd.member.dto.password.PasswordChangeDueExtendResponse;
import com.superboard.onbrd.member.dto.password.PasswordChangeDueResponse;
import com.superboard.onbrd.member.dto.password.PasswordResetRequest;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.service.MemberService;
import com.superboard.onbrd.member.service.PasswordService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Password")
@RestController
@RequestMapping("/api/v1/passwords")
@RequiredArgsConstructor
@Validated
public class PasswordController {
	private final MemberService memberService;
	private final PasswordService passwordService;
	private final AuthService authService;

	@Tag(name = "Password")
	@ApiOperation(value = "비밀번호 변경 기한 조회")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@GetMapping("/deadline")
	public ResponseEntity<PasswordChangeDueResponse> getPasswordChangeDeadline(
		@AuthenticationPrincipal MemberDetails memberDetails) {
		Member member = memberService.findVerifiedOneByEmail(memberDetails.getEmail());
		PasswordChangeDueResponse response = passwordService.getPasswordChangeDue(member);

		return ResponseEntity.ok(response);
	}

	@Tag(name = "Password")
	@ApiOperation(value = "비밀번호 변경 기한 연장")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@PatchMapping("/deadline")
	public ResponseEntity<PasswordChangeDueExtendResponse> extendPasswordChangeDue(
		@AuthenticationPrincipal MemberDetails memberDetails) {

		Member member = memberService.findVerifiedOneByEmail(memberDetails.getEmail());
		PasswordChangeDueExtendResponse response = passwordService.extendPasswordChangeDue(member);

		return ResponseEntity.ok(response);
	}

	@Tag(name = "Password")
	@ApiOperation(value = "비밀번호 재설정 인증 코드 메일 발송")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AuthCodeSendingResponse.class)))
	@GetMapping("/code")
	public ResponseEntity<AuthCodeSendingResponse> sendAuthCodeMailForPasswordReset(@RequestParam String email) {
		memberService.checkEmailExists(email);
		AuthCodeSendingResponse response = authService.sendAuthCodeMail(email);

		return ResponseEntity.ok(response);
	}

	@Tag(name = "Password")
	@ApiOperation(value = "비밀번호 재설정 인증 코드 검증")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "인증 코드 일치",
			content = @Content(schema = @Schema(implementation = String.class))),
		@ApiResponse(responseCode = "401", description = "인증 코드 불일치")
	})
	@PostMapping("/code-check")
	public ResponseEntity<String> checkAuthCodeForPasswordReset(@RequestBody AuthCodeCheckRequest request) {
		authService.checkAuthCode(request);

		String resetToken = authService.issuePasswordResetToken();

		return ResponseEntity.ok(resetToken);
	}

	@Tag(name = "Password")
	@ApiOperation(value = "비밀번호 재설정")
	@PatchMapping
	public ResponseEntity<Void> resetPassword(@RequestBody PasswordResetRequest request) {
		authService.validateResetToken(request.getResetToken());

		Member member = memberService.findVerifiedOneByEmail(request.getEmail());
		passwordService.resetPassword(member, request.getPassword());

		return ResponseEntity.ok().build();
	}
}
