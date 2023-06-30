package com.superboard.onbrd.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.member.dto.mypage.MypageBadgeResponse;
import com.superboard.onbrd.member.service.BadgeHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members/mypage/badges")
@RequiredArgsConstructor
public class BadgeHistoryController {
	private final BadgeHistoryService badgeHistoryService;

	@GetMapping
	public ResponseEntity<MypageBadgeResponse> getBadges(@AuthenticationPrincipal MemberDetails memberDetails) {

		MypageBadgeResponse response = badgeHistoryService.findBadges(memberDetails.getEmail());

		return ResponseEntity.ok(response);
	}
}
