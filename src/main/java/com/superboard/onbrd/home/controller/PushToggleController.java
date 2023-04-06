package com.superboard.onbrd.home.controller;

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
import oracle.ucp.proxy.annotation.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pushToggle")
@Api(tags = {"사용자 푸시 사용 여부 Controller"})
public class PushToggleController {

    private TokenService tokenService;
    private PushToggleService pushToggleService;

    @GetMapping
    @ApiOperation(value = "사용자 푸시 알림 사용 여부")
    public ResponseEntity<PushToggleDto> getPushToggle(long id){
        PushToggleDto response = pushToggleService.getPushToggle(id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping
    @ApiOperation(value = "사용자 푸시 알림 사용 여부 업데이트")
    public ResponseEntity<Long> patchPushToggle(@RequestPart PushTogglePatchRequest request) {

        PushToggleDto params =PushToggleDto.of(request);
        Long updateId = pushToggleService.updatePushToggle(params);
        
        return ResponseEntity.ok(updateId);
    }

    @PostMapping("/createPushToken")
    @ApiOperation(value = "사용자 푸시 토큰 생성")
    @ApiResponses({
            @ApiResponse(code = 200, message = "토큰 생성 성공"),
            @ApiResponse(code = 500, message = "토큰 생성 실패")
    })
    public ResponseEntity<Void> createPushToken(@RequestBody PushTokenDto pushTokenDto){
        Token pushToken = Token.builder()
                .androidPushToken(pushTokenDto.getAndroidPushToken())
                .applePushToken(pushTokenDto.getApplePushToken())
                .build();
        tokenService.createToken(pushToken);
        return ResponseEntity.ok().build();
    }


}
