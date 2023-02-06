package com.superboard.onbrd.review.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.review.dto.CommentCreateDto;
import com.superboard.onbrd.review.dto.CommentPatchRequest;
import com.superboard.onbrd.review.dto.CommentPostRequest;
import com.superboard.onbrd.review.dto.CommentUpdateDto;
import com.superboard.onbrd.review.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/boardgames/{boardgameId}/reviews/{reviewId}/comments")
@RequiredArgsConstructor
@Validated
public class CommentController {
	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<Long> postComment(
		@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable Long reviewId, CommentPostRequest request) {
		CommentCreateDto dto = CommentCreateDto.of(memberDetails.getEmail(), reviewId, request);

		Long createdId = commentService.createComment(dto).getId();

		return ResponseEntity.status(CREATED).body(createdId);
	}

	@PatchMapping("/{commentId}")
	public ResponseEntity<Long> patchComment(@PathVariable Long commentId, CommentPatchRequest request) {
		CommentUpdateDto dto = CommentUpdateDto.of(commentId, request);

		Long updatedId = commentService.updateComment(dto).getId();

		return ResponseEntity.ok(updatedId);
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
		commentService.deleteCommentById(commentId);

		return ResponseEntity.noContent().build();
	}
}
