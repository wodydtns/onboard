package com.superboard.onbrd.review.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.review.service.ReviewLikeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/boardgames/{boardgameId}/reviews/{reviewId}/likes")
@RequiredArgsConstructor
public class ReviewLikeController {
	private final ReviewLikeService reviewLikeService;

	@PostMapping
	public ResponseEntity<Long> likeReviewOrCancelLike(
		@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable Long reviewId) {
		reviewLikeService.createReviewLikeOrDeleteIfExist(memberDetails.getEmail(), reviewId);

		return ResponseEntity.ok(reviewId);
	}
}
