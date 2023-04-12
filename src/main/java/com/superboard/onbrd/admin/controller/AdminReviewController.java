package com.superboard.onbrd.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.admin.dto.AdminReviewDetail;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.review.entity.Review;
import com.superboard.onbrd.review.service.ReviewService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Tag(name = "Admin")
@RestController
@Getter
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/reviews")
public class AdminReviewController {
	private final ReviewService reviewService;

	@Tag(name = "Admin")
	@GetMapping
	public ResponseEntity<OnbrdSliceResponse<AdminReviewDetail>> getReviews(@ModelAttribute OnbrdSliceRequest params) {
		OnbrdSliceResponse<AdminReviewDetail> response = reviewService.getAdminReviews(params);

		return ResponseEntity.ok(response);
	}

	@Tag(name = "Admin")
	@PatchMapping("/{id}")
	public ResponseEntity<Long> hideReview(@PathVariable Long id) {
		Review hidden = reviewService.hideReview(id);

		return ResponseEntity.ok(hidden.getId());
	}
}
