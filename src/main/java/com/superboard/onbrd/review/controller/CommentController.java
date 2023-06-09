package com.superboard.onbrd.review.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.review.dto.comment.CommentCreateDto;
import com.superboard.onbrd.review.dto.comment.CommentDetail;
import com.superboard.onbrd.review.dto.comment.CommentPatchRequest;
import com.superboard.onbrd.review.dto.comment.CommentPostRequest;
import com.superboard.onbrd.review.dto.comment.CommentUpdateDto;
import com.superboard.onbrd.review.service.CommentService;
import com.superboard.onbrd.review.service.CustomCommentService;

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
@RequestMapping("/api/v1/boardgames/{boardgameId}/reviews/{reviewId}/comments")
@RequiredArgsConstructor
@Validated
public class CommentController {
	private final CommentService commentService;

	private final CustomCommentService customCommentService;

	@Tag(name = "Comment")
	@ApiOperation(value = "댓글 작성")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "생성 댓글 ID 응답",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = Long.class), examples = {@ExampleObject(value = "1")})),
		@ApiResponse(responseCode = "404")
	})
	@ResponseStatus(CREATED)
	@PostMapping
	public ResponseEntity<Long> postComment(@AuthenticationPrincipal MemberDetails memberDetails,
		@PathVariable Long reviewId, @RequestBody CommentPostRequest request) {
		CommentCreateDto dto = CommentCreateDto.of(memberDetails.getEmail(), reviewId, request);

		Long createdId = commentService.createComment(dto).getId();

		customCommentService.selectOauthIdForPushMessage(createdId);

		return ResponseEntity.status(CREATED).body(createdId);
	}

	@Tag(name = "Comment")
	@ApiOperation(value = "댓글 수정")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "수정 댓글 ID 응답",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = Long.class), examples = {@ExampleObject(value = "1")})),
		@ApiResponse(responseCode = "404")
	})
	@PatchMapping("/{commentId}")
	public ResponseEntity<Long> patchComment(@PathVariable Long commentId, @RequestBody CommentPatchRequest request) {
		CommentUpdateDto dto = CommentUpdateDto.of(commentId, request);

		Long updatedId = commentService.updateComment(dto).getId();

		return ResponseEntity.ok(updatedId);
	}

	@Tag(name = "Comment")
	@ApiOperation(value = "댓글 삭제")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "댓글 삭제"),
		@ApiResponse(responseCode = "404")
	})
	@ResponseStatus(NO_CONTENT)
	@DeleteMapping("/{commentId}")
	public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
		commentService.deleteCommentById(commentId);

		return ResponseEntity.noContent().build();
	}

	@Tag(name = "Comment")
	@ApiOperation(value = "리뷰별 댓글 목록 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "404")
	})
	@GetMapping
	public ResponseEntity<OnbrdSliceResponse<CommentDetail>> getCommentsByReviewId(
		@PathVariable Long reviewId, @ModelAttribute OnbrdSliceRequest request) {
		request.rebaseToZero();

		OnbrdSliceResponse<CommentDetail> response = commentService.getCommentsByReviewId(reviewId, request);

		return ResponseEntity.ok(response);
	}
}
