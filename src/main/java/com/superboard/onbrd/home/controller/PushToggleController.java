package com.superboard.onbrd.home.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.auth.dto.PushTokenDto;
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

@RestController
@RequestMapping("/api/v1/pushToggle")
@RequiredArgsConstructor
@Api(tags = {"사용자 푸시 사용 여부 Controller"})
public class PushToggleController {

	private final TokenService tokenService;
	private final PushToggleService pushToggleService;

	@GetMapping
	@ApiOperation(value = "사용자 푸시 알림 사용 여부")
	public ResponseEntity<PushToggleDto> getPushToggle(long id) {
		PushToggleDto response = pushToggleService.getPushToggle(id);

		return ResponseEntity.ok(response);
	}

	@PatchMapping
	@ApiOperation(value = "사용자 푸시 알림 사용 여부 업데이트")
	public ResponseEntity<Long> patchPushToggle(@RequestPart PushTogglePatchRequest request) {

		PushToggleDto params = PushToggleDto.of(request);
		Long updateId = pushToggleService.updatePushToggle(params);

		return ResponseEntity.ok(updateId);
	}

	@PostMapping("/createPushToken")
	@ApiOperation(value = "사용자 푸시 토큰 생성")
	@ApiResponses({
		@ApiResponse(code = 200, message = "토큰 생성 성공"),
		@ApiResponse(code = 500, message = "토큰 생성 실패")
	})
	public ResponseEntity<Void> createPushToken(@RequestBody PushTokenDto pushTokenDto) {
		Token pushToken = Token.validateToken(pushTokenDto);
		tokenService.createToken(pushToken);
		return ResponseEntity.ok().build();
	}

}
