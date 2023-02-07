package com.superboard.onbrd.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.member.dto.password.PasswordChangeDueExtendResponse;
import com.superboard.onbrd.member.dto.password.PasswordChangeDueResponse;
import com.superboard.onbrd.member.dto.password.PasswordResetRequest;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.service.MemberService;
import com.superboard.onbrd.member.service.PasswordService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
	@ApiOperation(value = "비밀번호 재설정")
	@PatchMapping
	public ResponseEntity<Void> resetPassword(@RequestBody PasswordResetRequest request) {
		Member member = memberService.findVerifiedOneByEmail(request.getEmail());
		passwordService.resetPassword(member, request.getPassword());

		return ResponseEntity.ok().build();
	}
}
