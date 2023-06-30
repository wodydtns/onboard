package com.superboard.onbrd.home.controller;

import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.auth.dto.PushTokenPostRequest;
import com.superboard.onbrd.auth.entity.Token;
import com.superboard.onbrd.auth.service.TokenService;
import com.superboard.onbrd.home.dto.PushToggleDto;
import com.superboard.onbrd.home.dto.PushTogglePatchRequest;
import com.superboard.onbrd.home.service.PushToggleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pushToggle")
@RequiredArgsConstructor
@Api(tags = {"사용자 푸시 사용 여부 Controller"})
public class PushToggleController {

	private final TokenService tokenService;
	private final PushToggleService pushToggleService;
	private final MemberService memberService;

	@GetMapping
	@ApiOperation(value = "사용자 푸시 알림 사용 여부")
	public ResponseEntity<PushToggleDto> getPushToggle(@AuthenticationPrincipal MemberDetails memberDetails) {
		Optional<Member> member = memberService.findByEmail(memberDetails.getEmail());
		PushToggleDto response = pushToggleService.getPushToggle(member.get().getId());

		return ResponseEntity.ok(response);
	}

	@PatchMapping
	@ApiOperation(value = "사용자 푸시 알림 사용 여부 업데이트")
	public ResponseEntity<Long> patchPushToggle(@RequestPart PushTogglePatchRequest request,@AuthenticationPrincipal MemberDetails memberDetails) {
		Optional<Member> member = memberService.findByEmail(memberDetails.getEmail());
		PushToggleDto params = PushToggleDto.of(request, member.get().getId());
		Long updateId = pushToggleService.updatePushToggle(params);

		return ResponseEntity.ok(updateId);
	}

	
	// FIXME : 로그인 시 자동으로 token row가 추가됨 -> token 생성은 update로 처리해야함
	@PostMapping("/createPushToken")
	@ApiOperation(value = "사용자 푸시 토큰 생성")
	@ApiResponses({
		@ApiResponse(code = 200, message = "토큰 생성 성공"),
		@ApiResponse(code = 500, message = "토큰 생성 실패")
	})
	public ResponseEntity<Void> createPushToken(@RequestBody PushTokenPostRequest pushTokenPostRequest, @AuthenticationPrincipal MemberDetails memberDetails) {
		Member member = memberService.findByEmail(memberDetails.getEmail())
				.orElseThrow(() -> new RuntimeException("Member not found"));
		pushTokenPostRequest.setMemberId(member.getId());
		Token pushToken = Token.validateToken(pushTokenPostRequest);
		tokenService.createToken(pushToken);
		return ResponseEntity.ok().build();
	}

}
