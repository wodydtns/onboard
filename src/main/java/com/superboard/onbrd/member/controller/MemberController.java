package com.superboard.onbrd.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.member.dto.FavoriteTagResetRequest;
import com.superboard.onbrd.member.dto.MemberUpdateRequest;
import com.superboard.onbrd.member.dto.PasswordChangeRequest;
import com.superboard.onbrd.member.dto.SignUpRequest;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.entity.MemberLevel;
import com.superboard.onbrd.member.service.MemberService;
import com.superboard.onbrd.tag.service.FavoriteTagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Validated
public class MemberController {
	private final MemberService memberService;
	private final FavoriteTagService favoriteTagService;

	@PostMapping("/sign-up")
	public ResponseEntity<Long> signUp(@RequestBody SignUpRequest request) {
		Member created = memberService.signUp(request);
		if (request.getTagIds() != null) {
			favoriteTagService.createdFavoriteTags(created, request.getTagIds());
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(created.getId());
	}

	@GetMapping("/nickname-check")
	public ResponseEntity<String> checkDuplicatedNickname(@RequestParam String nickname) {
		memberService.checkDuplicatedNickname(nickname);

		return ResponseEntity.ok(nickname);
	}

	@GetMapping("/mail-check")
	public ResponseEntity<String> checkEmailExists(@RequestParam String email) {
		memberService.checkEmailExists(email);

		return ResponseEntity.ok(email);
	}

	@PatchMapping("/{memberId}/info")
	public ResponseEntity<Long> updateMemberInfo(
		@PathVariable Long memberId, @RequestBody MemberUpdateRequest request) {
		request.setMemberId(memberId);
		Member updated = memberService.updateMember(request);

		return ResponseEntity.ok(updated.getId());
	}

	@PatchMapping("/{memberId}/favorite-tags")
	public ResponseEntity<Void> resetFavoriteTags(
		@PathVariable Long memberId, @RequestBody FavoriteTagResetRequest request) {
		favoriteTagService.resetFavoriteTags(memberId, request.getTagIds());

		return ResponseEntity.ok(null);
	}

	@PatchMapping("/{memberId}/password")
	public ResponseEntity<Void> changePassword(
		@PathVariable Long memberId, @RequestBody PasswordChangeRequest request) {
		request.setMemberId(memberId);
		memberService.changePassword(request);

		return ResponseEntity.ok(null);
	}

	// @GetMapping("/{memberId}/level")
	// public ResponseEntity<MemberLevel> getMemberLevel(@PathVariable Long memberId) {
	// 	Member member = memberService.findVerifiedOneById(memberId);
	//
	// 	return ResponseEntity.ok(member.getLevel());
	// }

	@PatchMapping("/{memberId}/withdrawal")
	public ResponseEntity<MemberLevel> withdraw(@PathVariable Long memberId) {
		memberService.withDraw(memberId);

		return ResponseEntity.ok().build();
	}
}
