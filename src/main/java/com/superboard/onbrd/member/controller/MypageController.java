package com.superboard.onbrd.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.member.dto.mypage.FavoriteTagResetRequest;
import com.superboard.onbrd.member.dto.mypage.MypageGetDto;
import com.superboard.onbrd.member.dto.mypage.MypageGetMoreDto;
import com.superboard.onbrd.member.dto.mypage.MypageMoreBoardgameResponse;
import com.superboard.onbrd.member.dto.mypage.MypageMoreRequest;
import com.superboard.onbrd.member.dto.mypage.MypageMoreReviewResponse;
import com.superboard.onbrd.member.dto.mypage.MypageRequest;
import com.superboard.onbrd.member.dto.mypage.MypageResponse;
import com.superboard.onbrd.member.dto.mypage.ProfileUpdateRequest;
import com.superboard.onbrd.member.dto.password.PasswordChangeRequest;
import com.superboard.onbrd.member.service.MypageService;
import com.superboard.onbrd.tag.service.FavoriteTagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members/mypage")
@RequiredArgsConstructor
public class MypageController {
	private final MypageService mypageService;
	private final FavoriteTagService favoriteTagService;

	@GetMapping
	public ResponseEntity<MypageResponse> getMyPage(
		@AuthenticationPrincipal MemberDetails memberDetails, @ModelAttribute MypageRequest request) {
		MypageGetDto params = MypageGetDto.of(memberDetails.getEmail(), request);

		MypageResponse response = mypageService.getMypage(params);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/reviews")
	public ResponseEntity<MypageMoreReviewResponse> getMoreReviews(
		@AuthenticationPrincipal MemberDetails memberDetails, @ModelAttribute MypageMoreRequest request) {
		MypageGetMoreDto params = MypageGetMoreDto.of(memberDetails.getEmail(), request);

		MypageMoreReviewResponse response = mypageService.getMoreReviews(params);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/favorite-boardgames")
	public ResponseEntity<MypageMoreBoardgameResponse> getMoreFavoriteBoardGames(
		@AuthenticationPrincipal MemberDetails memberDetails, @ModelAttribute MypageMoreRequest request) {
		MypageGetMoreDto params = MypageGetMoreDto.of(memberDetails.getEmail(), request);

		MypageMoreBoardgameResponse response = mypageService.getMoreFavoriteBoardgames(params);

		return ResponseEntity.ok(response);
	}

	@PatchMapping("/profile")
	public ResponseEntity<Void> updateProfile(
		@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody ProfileUpdateRequest request) {
		mypageService.updateProfile(memberDetails.getEmail(), request);

		return ResponseEntity.ok().build();
	}

	@PatchMapping("/password")
	public ResponseEntity<Void> changePassword(
		@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody PasswordChangeRequest request) {
		mypageService.changePassword(memberDetails.getEmail(), request.getPassword());

		return ResponseEntity.ok().build();
	}

	@PatchMapping("/favorite-tags")
	public ResponseEntity<Void> resetFavoriteTags(
		@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody FavoriteTagResetRequest request) {
		favoriteTagService.resetFavoriteTags(memberDetails.getEmail(), request.getTagIds());

		return ResponseEntity.ok().build();
	}

	@PatchMapping("/withdrawal")
	public ResponseEntity<Void> withdraw(@AuthenticationPrincipal MemberDetails memberDetails) {
		mypageService.withDrawByEmail(memberDetails.getEmail());

		return ResponseEntity.ok().build();
	}
}
