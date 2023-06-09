package com.superboard.onbrd.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.admin.dto.AdminCommentDetail;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.review.entity.Comment;
import com.superboard.onbrd.review.service.CommentService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Tag(name = "Admin")
@RestController
@Getter
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/comments")
public class AdminCommentController {
	private final CommentService commentService;

	@Tag(name = "Admin")
	@GetMapping
	public ResponseEntity<OnbrdSliceResponse<AdminCommentDetail>> getComments(
		@ModelAttribute OnbrdSliceRequest params) {
		params.rebaseToZero();

		OnbrdSliceResponse<AdminCommentDetail> response = commentService.getAdminComment(params);

		return ResponseEntity.ok(response);
	}

	@Tag(name = "Admin")
	@PatchMapping("/{id}")
	public ResponseEntity<Long> hideComment(@PathVariable Long id) {
		Comment hidden = commentService.hideComment(id);

		return ResponseEntity.ok(hidden.getId());
	}
}
