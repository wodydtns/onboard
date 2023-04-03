package com.superboard.onbrd.home.controller;

import com.superboard.onbrd.home.dto.PushToggleDto;
import com.superboard.onbrd.home.dto.PushTogglePatchRequest;
import com.superboard.onbrd.home.service.PushToggleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pushToggle")
@Api(tags = {"사용자 푸시 사용 여부 Controller"})
public class PushToggleController {

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
}
