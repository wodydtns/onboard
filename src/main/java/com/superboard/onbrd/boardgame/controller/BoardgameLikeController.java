package com.superboard.onbrd.boardgame.controller;

import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.boardgame.service.BoardGameService;
import com.superboard.onbrd.boardgame.service.BoardgameLikeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/boardgames/{boardGameId}//likes")
@RequiredArgsConstructor
public class BoardgameLikeController {

    // TODO : 테스트 필요 & 버그 픽스
    private final BoardgameLikeService boardgameLikeService;

    private final BoardGameService boardGameService;
    @ApiOperation(value = "보드게임 좋아요/좋아요 취소")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요/좋아요 취소된 보드게임 ID 응답",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Long.class), examples = {@ExampleObject(value = "1")})),
            @ApiResponse(responseCode = "404")
    })
    @PostMapping
    public ResponseEntity<Long> likeBoardGameOrCancelLike (@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable Long boardGameId) {
        boardgameLikeService.createBoardgameLikeOrDeleteIfExist(memberDetails.getEmail(),boardGameId);

        return ResponseEntity.ok(boardGameId);
    }
}
