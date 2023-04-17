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
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.member.dto.mypage.FavoriteTagResetRequest;
import com.superboard.onbrd.member.dto.mypage.MypageGetDto;
import com.superboard.onbrd.member.dto.mypage.MypageGetMoreDto;
import com.superboard.onbrd.member.dto.mypage.MypageMoreBoardgameDetail;
import com.superboard.onbrd.member.dto.mypage.MypageMoreRequest;
import com.superboard.onbrd.member.dto.mypage.MypageMoreReviewDetail;
import com.superboard.onbrd.member.dto.mypage.MypageRequest;
import com.superboard.onbrd.member.dto.mypage.MypageResponse;
import com.superboard.onbrd.member.dto.mypage.ProfileUpdateRequest;
import com.superboard.onbrd.member.dto.password.PasswordChangeRequest;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.service.MemberService;
import com.superboard.onbrd.member.service.MypageService;
import com.superboard.onbrd.tag.service.FavoriteTagService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Mypage")
@RestController
@RequestMapping("/api/v1/members/mypage")
@RequiredArgsConstructor
public class MypageController {
	private final MemberService memberService;
	private final MypageService mypageService;
	private final FavoriteTagService favoriteTagService;

	@Tag(name = "Mypage")
	@ApiOperation(value = "마이페이지 조회")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@GetMapping
	public ResponseEntity<MypageResponse> getMyPage(
		@AuthenticationPrincipal MemberDetails memberDetails, @ModelAttribute MypageRequest request) {
		MypageGetDto params = MypageGetDto.of(memberDetails.getEmail(), request);

		MypageResponse response = mypageService.getMypage(params);

		return ResponseEntity.ok(response);
	}

	@Tag(name = "Mypage")
	@ApiOperation(value = "작성 리뷰 더보기")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@GetMapping("/reviews")
	public ResponseEntity<OnbrdSliceResponse<MypageMoreReviewDetail>> getMoreReviews(
		@AuthenticationPrincipal MemberDetails memberDetails, @ModelAttribute MypageMoreRequest request) {

		MypageGetMoreDto params = MypageGetMoreDto.of(memberDetails.getEmail(), request);

		OnbrdSliceResponse<MypageMoreReviewDetail> response = mypageService.getMoreReviews(params);

		return ResponseEntity.ok(response);
	}

	@Tag(name = "Mypage")
	@ApiOperation(value = "관심 보드게임 더보기")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@GetMapping("/favorite-boardgames")
	public ResponseEntity<OnbrdSliceResponse<MypageMoreBoardgameDetail>> getMoreFavoriteBoardGames(
		@AuthenticationPrincipal MemberDetails memberDetails, @ModelAttribute MypageMoreRequest request) {

		MypageGetMoreDto params = MypageGetMoreDto.of(memberDetails.getEmail(), request);

		OnbrdSliceResponse<MypageMoreBoardgameDetail> response = mypageService.getMoreFavoriteBoardgames(params);

		return ResponseEntity.ok(response);
	}

	@Tag(name = "Mypage")
	@ApiOperation(value = "프로필 업데이트")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@PatchMapping("/profile")
	public ResponseEntity<Void> updateProfile(
		@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody ProfileUpdateRequest request) {
		mypageService.updateProfile(memberDetails.getEmail(), request);

		return ResponseEntity.ok().build();
	}

	@Tag(name = "Mypage")
	@ApiOperation(value = "비밀번호 변경")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@PatchMapping("/password")
	public ResponseEntity<Void> changePassword(
		@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody PasswordChangeRequest request) {
		mypageService.changePassword(memberDetails.getEmail(), request.getPassword());

		return ResponseEntity.ok().build();
	}

	@Tag(name = "Mypage")
	@ApiOperation(value = "관심태그 재설정")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@PatchMapping("/favorite-tags")
	public ResponseEntity<Void> resetFavoriteTags(
		@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody FavoriteTagResetRequest request) {
		Member member = memberService.findVerifiedOneByEmail(memberDetails.getEmail());
		favoriteTagService.resetFavoriteTags(member, request.getTagIds());

		return ResponseEntity.ok().build();
	}

	@Tag(name = "Mypage")
	@ApiOperation(value = "탈퇴")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@PatchMapping("/withdrawal")
	public ResponseEntity<Void> withdraw(@AuthenticationPrincipal MemberDetails memberDetails) {
		mypageService.withDrawByEmail(memberDetails.getEmail());

		return ResponseEntity.ok().build();
	}
}
