package com.superboard.onbrd.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.admin.dto.AdminMemberDetail;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/members")
@RequiredArgsConstructor
public class AdminMemberController {
	private final MemberService memberService;

	@GetMapping("/{id}")
	public ResponseEntity<AdminMemberDetail> getMemberDetail(@PathVariable Long id) {
		AdminMemberDetail response = memberService.getAdminMemberDetail(id);

		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{id}/suspend")
	public ResponseEntity<Long> suspendMember(@PathVariable Long id) {
		Member suspended = memberService.suspendMember(id);

		return ResponseEntity.ok(suspended.getId());
	}

	@PatchMapping("/{id}/kick")
	public ResponseEntity<Long> kickMember(@PathVariable Long id) {
		Member kicked = memberService.kickMember(id);

		return ResponseEntity.ok(kicked.getId());
	}
}
