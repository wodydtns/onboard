package com.superboard.onbrd.review.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.review.dto.ReviewByBoardgameIdResponse;
import com.superboard.onbrd.review.dto.ReviewCreateDto;
import com.superboard.onbrd.review.dto.ReviewGetParameterDto;
import com.superboard.onbrd.review.dto.ReviewGetRequest;
import com.superboard.onbrd.review.dto.ReviewPatchRequest;
import com.superboard.onbrd.review.dto.ReviewPostRequest;
import com.superboard.onbrd.review.dto.ReviewUpdateDto;
import com.superboard.onbrd.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/boardgames/{boardgameId}/reviews")
@RequiredArgsConstructor
@Validated
public class ReviewController {
	private final ReviewService reviewService;

	@GetMapping
	public ResponseEntity<ReviewByBoardgameIdResponse> getReviews(
		@PathVariable Long boardgameId, @ModelAttribute ReviewGetRequest request) {
		ReviewGetParameterDto params = ReviewGetParameterDto.of(boardgameId, request);

		ReviewByBoardgameIdResponse response = reviewService.getReviewsByBoardgameId(params);

		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<Long> postReview(@AuthenticationPrincipal MemberDetails memberDetails,
		@PathVariable Long boardgameId, ReviewPostRequest request) {
		ReviewCreateDto dto = ReviewCreateDto.of(memberDetails.getEmail(), boardgameId, request);

		Long createdId = reviewService.crewateReview(dto).getId();

		return ResponseEntity.status(HttpStatus.CREATED).body(createdId);
	}

	@PatchMapping("/{reviewId}")
	public ResponseEntity<Long> patchReview(@PathVariable Long reviewId, ReviewPatchRequest request) {
		ReviewUpdateDto dto = ReviewUpdateDto.of(reviewId, request);

		Long updatedId = reviewService.updateReview(dto).getId();

		return ResponseEntity.ok(updatedId);
	}

	@DeleteMapping("/{reviewId}")
	public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
		reviewService.deleteReviewById(reviewId);

		return ResponseEntity.noContent().build();
	}
}
