package com.superboard.onbrd.review.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.review.service.CommentLikeService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Comment")
@RestController
@RequestMapping("/api/v1/boardgames/{boardgameId}/reviews/{reviewId}/Comments/{commentId}/likes")
@RequiredArgsConstructor
public class CommentLikeController {
	private final CommentLikeService commentLikeService;

	@Tag(name = "Comment")
	@ApiOperation(value = "댓글 좋아요/좋아요 취소")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "좋아요/좋아요 취소된 댓글 ID 응답",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = Long.class), examples = {@ExampleObject(value = "1")})),
		@ApiResponse(responseCode = "404")
	})
	@PostMapping
	public ResponseEntity<Long> likeCommentOrCancelLike
		(@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable Long commentId) {
		commentLikeService.createCommentLikeOrDeleteIfExist(memberDetails.getEmail(), commentId);
		
		return ResponseEntity.ok(commentId);
	}
}
