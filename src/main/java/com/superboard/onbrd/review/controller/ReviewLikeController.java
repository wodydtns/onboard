package com.superboard.onbrd.review.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.review.service.ReviewLikeService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Like")
@RestController
@RequestMapping("/api/v1/boardgames/{boardgameId}/reviews/{reviewId}/likes")
@RequiredArgsConstructor
public class ReviewLikeController {
	private final ReviewLikeService reviewLikeService;

	@Tag(name = "Like")
	@ApiOperation(value = "좋아요/좋아요 취소된 리뷰 ID 응답")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "좋아요/좋아요 취소된 리뷰 ID 응답",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = Long.class), examples = {@ExampleObject(value = "1")})),
		@ApiResponse(responseCode = "404")
	})
	@PostMapping
	public ResponseEntity<Long> likeReviewOrCancelLike(
		@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable Long reviewId) {
		reviewLikeService.createReviewLikeOrDeleteIfExist(memberDetails.getEmail(), reviewId);

		return ResponseEntity.ok(reviewId);
	}
}
