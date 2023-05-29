package com.superboard.onbrd.member.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.member.dto.member.MemberInfoResponse;
import com.superboard.onbrd.member.dto.member.SignUpRequest;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.service.MemberService;
import com.superboard.onbrd.tag.service.FavoriteTagService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Member", description = "비로그인")
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Validated
public class MemberController {
	private final MemberService memberService;
	private final FavoriteTagService favoriteTagService;

	@Tag(name = "Member")
	@ApiOperation(value = "회원가입")
	@ApiResponse(responseCode = "201", description = "생성된 회원 ID 응답")
	@ResponseStatus(CREATED)
	@PostMapping("/sign-up")
	public ResponseEntity<Long> signUp(@RequestBody SignUpRequest request) {
		Member created = memberService.signUp(request);

		return ResponseEntity.status(CREATED).body(created.getId());
	}

	@Tag(name = "Member")
	@ApiOperation(value = "닉네임 중복확인")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "409", description = "해당 닉네임을 갖는 회원 존재")
	})
	@GetMapping("/nickname-check")
	public ResponseEntity<String> checkDuplicatedNickname(@RequestParam String nickname) {
		memberService.checkDuplicatedNickname(nickname);

		return ResponseEntity.ok(nickname);
	}

	@Tag(name = "Member")
	@ApiOperation(value = "이메일 중복확인")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "409", description = "해당 이메일로 가입한 회원 존재")
	})
	@GetMapping("/mail-check")
	public ResponseEntity<String> checkEmailExists(@RequestParam String email) {
		memberService.checkDuplicatedEmail(email);

		return ResponseEntity.ok(email);
	}

	@Tag(name = "Member")
	@ApiOperation(value = "로그인 회원 정보 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "403"),
		@ApiResponse(responseCode = "404")
	})
	@GetMapping("/me")
	public ResponseEntity<MemberInfoResponse> getMemberLevel(@AuthenticationPrincipal MemberDetails memberDetails) {
		String loginEmail = memberDetails.getEmail();
		Member member = memberService.findVerifiedOneByEmail(loginEmail);

		return ResponseEntity.ok(MemberInfoResponse.toDto(member));
	}

	@Tag(name = "Member")
	@ApiOperation(value = "테스트용 임시 회원삭제")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204"),
		@ApiResponse(responseCode = "404")
	})
	@ResponseStatus(NO_CONTENT)
	@DeleteMapping("/{memberId}")
	public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {
		memberService.deleteMemberById(memberId);

		return ResponseEntity.noContent().build();
	}
}
