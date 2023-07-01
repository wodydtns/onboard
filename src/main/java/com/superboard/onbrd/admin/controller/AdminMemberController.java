package com.superboard.onbrd.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.admin.dto.AdminMemberDetail;
import com.superboard.onbrd.admin.dto.GrantBadgeRequest;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.service.MemberService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Admin")
@RestController
@RequestMapping("/api/v1/admin/members")
@RequiredArgsConstructor
public class AdminMemberController {
	private final MemberService memberService;

	@Tag(name = "Admin")
	@GetMapping("/{id}")
	public ResponseEntity<AdminMemberDetail> getMemberDetail(@PathVariable Long id) {
		AdminMemberDetail response = memberService.getAdminMemberDetail(id);

		return ResponseEntity.ok(response);
	}

	@Tag(name = "Admin")
	@PatchMapping("/{id}/suspend")
	public ResponseEntity<Long> suspendMember(@PathVariable Long id) {
		Member suspended = memberService.suspendMember(id);

		return ResponseEntity.ok(suspended.getId());
	}

	@Tag(name = "Admin")
	@PatchMapping("/{id}/kick")
	public ResponseEntity<Long> kickMember(@PathVariable Long id) {
		Member kicked = memberService.kickMember(id);

		return ResponseEntity.ok(kicked.getId());
	}

	@Tag(name = "Admin")
	@PatchMapping("/{id}/badges")
	public ResponseEntity<Long> grantBadges(@PathVariable Long id, @RequestBody GrantBadgeRequest request) {
		Member granted = memberService.grantBadges(id, request.getBadges());

		return ResponseEntity.ok(granted.getId());
	}
}
