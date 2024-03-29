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
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.review.dto.review.ReviewByBoardgameDetail;
import com.superboard.onbrd.review.dto.review.ReviewCreateDto;
import com.superboard.onbrd.review.dto.review.ReviewGetParameterDto;
import com.superboard.onbrd.review.dto.review.ReviewGetRequest;
import com.superboard.onbrd.review.dto.review.ReviewPatchRequest;
import com.superboard.onbrd.review.dto.review.ReviewPostRequest;
import com.superboard.onbrd.review.dto.review.ReviewUpdateDto;
import com.superboard.onbrd.review.service.ReviewLikeService;
import com.superboard.onbrd.review.service.ReviewService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Review")
@RestController
@RequestMapping("/api/v1/boardgames/{boardgameId}/reviews")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ReviewController {
	private final ReviewService reviewService;
	private final ReviewLikeService reviewLikeService;

	@Tag(name = "Review")
	@ApiOperation(value = "보드게임별 리뷰 목록 조회 / REVIEW_NEWEST: 리뷰 최신순, REVIEW_MOST_LIKE: 리뷰 좋아요 많은순")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "404")})
	@GetMapping
	public ResponseEntity<OnbrdSliceResponse<ReviewByBoardgameDetail>> getReviews(
		@PathVariable Long boardgameId, @ModelAttribute ReviewGetRequest request,
		@AuthenticationPrincipal MemberDetails memberDetails) {

		log.error("" + request.getOffset());

		request.rebaseToZero();

		log.error("" + request.getOffset());

		ReviewGetParameterDto params = ReviewGetParameterDto.of(boardgameId, request);

		OnbrdSliceResponse<ReviewByBoardgameDetail> response = reviewService.getReviewsByBoardgameId(params);

		try {
			String email = memberDetails.getEmail();
			response.getContent()
				.forEach(review -> review.setIsLiked(
					reviewLikeService.isLikedBy(email, review.getId())));
		} catch (NullPointerException e) {
			response.getContent()
				.forEach(review -> review.setIsLiked(false));
		}

		return ResponseEntity.ok(response);
	}

	@Tag(name = "Review")
	@ApiOperation(value = "리뷰 작성")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "생성 리뷰 ID 응답", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class), examples = {
			@ExampleObject(value = "1")})),
		@ApiResponse(responseCode = "404")})
	@ResponseStatus(CREATED)
	@PostMapping()
	public ResponseEntity<Long> postReview(@AuthenticationPrincipal MemberDetails memberDetails,
		@PathVariable Long boardgameId, @RequestBody ReviewPostRequest request) {

		ReviewCreateDto dto = ReviewCreateDto.of(memberDetails.getEmail(), boardgameId, request);

		Long createdId = reviewService.crewateReview(dto, request.getImages()).getId();

		return ResponseEntity.status(CREATED).body(createdId);
	}

	@Tag(name = "Review")
	@ApiOperation(value = "리뷰 수정")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "수정 리뷰 ID 응답", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class), examples = {
			@ExampleObject(value = "1")})),
		@ApiResponse(responseCode = "404")})
	@PatchMapping(path = "/{reviewId}")
	public ResponseEntity<Long> patchReview(@PathVariable Long reviewId, @RequestBody ReviewPatchRequest request) {

		ReviewUpdateDto dto = ReviewUpdateDto.of(reviewId, request);

		Long updatedId = reviewService.updateReview(dto, request.getImages()).getId();

		return ResponseEntity.ok(updatedId);
	}

	@Tag(name = "Review")
	@ApiOperation(value = "리뷰 삭제")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer ...", required = true, dataTypeClass = String.class)
	@ApiResponses(value = {@ApiResponse(responseCode = "204", description = "리뷰 삭제"),
		@ApiResponse(responseCode = "404")})
	@ResponseStatus(NO_CONTENT)
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
		reviewService.deleteReviewById(reviewId);

		return ResponseEntity.noContent().build();
	}
}
