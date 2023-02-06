package com.superboard.onbrd.review.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.review.service.CommentLikeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/boardgames/{boardgameId}/reviews/{reviewId}/Comments/{commentId}/likes")
@RequiredArgsConstructor
public class CommentLikeController {
	private final CommentLikeService commentLikeService;

	@PostMapping
	public ResponseEntity<Long> likeCommentOrCancelLike
		(@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable Long commentId) {
		commentLikeService.createCommentLikeOrDeleteIfExist(memberDetails.getEmail(), commentId);

		return ResponseEntity.ok(commentId);
	}
}
