package com.superboard.onbrd.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.entity.MemberRole;
import com.superboard.onbrd.member.service.MemberService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "관리자 권한 부여")
@RestController
@RequestMapping("/api/v1/members/authority")
@RequiredArgsConstructor
public class AuthorityController {
	private final MemberService memberService;

	@Tag(name = "관리자 권한 부여")
	@PatchMapping
	public ResponseEntity<String> grantAdminAuthority(@AuthenticationPrincipal MemberDetails memberDetails) {
		String loginEmail = memberDetails.getEmail();
		Member granted = memberService.findVerifiedOneByEmail(loginEmail);

		granted.grantAuthority(MemberRole.ROLE_ADMIN);

		return ResponseEntity.ok(loginEmail);
	}
}
