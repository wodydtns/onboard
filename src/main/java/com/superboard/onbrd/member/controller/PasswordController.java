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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/passwords")
@RequiredArgsConstructor
@Validated
public class PasswordController {
	private final MemberService memberService;
	private final PasswordService passwordService;

	@GetMapping("/deadline")
	public ResponseEntity<?> getPasswordChangeDeadline(@AuthenticationPrincipal MemberDetails memberDetails) {
		Member member = memberService.findVerifiedOneByEmail(memberDetails.getEmail());
		PasswordChangeDueResponse response = passwordService.getPasswordChangeDue(member);

		return ResponseEntity.ok(response);
	}

	@PatchMapping("/deadline")
	public ResponseEntity<PasswordChangeDueExtendResponse> extendPasswordChangeDue(
		@AuthenticationPrincipal MemberDetails memberDetails) {

		Member member = memberService.findVerifiedOneByEmail(memberDetails.getEmail());
		PasswordChangeDueExtendResponse response = passwordService.extendPasswordChangeDue(member);

		return ResponseEntity.ok(response);
	}

	@PatchMapping
	public ResponseEntity<Void> resetPassword(@RequestBody PasswordResetRequest request) {
		Member member = memberService.findVerifiedOneByEmail(request.getEmail());
		passwordService.resetPassword(member, request.getPassword());

		return ResponseEntity.ok(null);
	}
}
