package com.superboard.onbrd.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.service.MemberService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "관리자 권한 부여")
@RestController
@RequestMapping("/api/v1/members/{id}/authority")
@RequiredArgsConstructor
public class AuthorityController {
	private final MemberService memberService;

	@Tag(name = "관리자 권한 부여")
	@PatchMapping
	public ResponseEntity<Long> grantAdminAuthority(@PathVariable Long id) {
		Member granted = memberService.grantAdminAuthority(id);

		return ResponseEntity.ok(granted.getId());
	}
}
