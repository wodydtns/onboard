package com.superboard.onbrd.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.superboard.onbrd.admin.dto.AdminCommentDetail;
import com.superboard.onbrd.admin.dto.AdminReportDetail;
import com.superboard.onbrd.admin.dto.AdminReviewDetail;
import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.global.dto.OnbrdPageRequest;
import com.superboard.onbrd.global.dto.OnbrdPageResponse;
import com.superboard.onbrd.report.entity.Report;
import com.superboard.onbrd.report.service.ReportService;
import com.superboard.onbrd.review.entity.Comment;
import com.superboard.onbrd.review.entity.Review;
import com.superboard.onbrd.review.service.CommentService;
import com.superboard.onbrd.review.service.ReviewService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	private final ReviewService reviewService;
	private final CommentService commentService;
	private final ReportService reportService;

	@GetMapping("/reviews")
	public ResponseEntity<OnbrdPageResponse<AdminReviewDetail>> getReviews(OnbrdPageRequest params) {
		OnbrdPageResponse<AdminReviewDetail> response = reviewService.getAdminReviews(params);

		return ResponseEntity.ok(response);
	}

	@PatchMapping("/reviews/{id}")
	public ResponseEntity<Long> hideReview(@PathVariable Long id) {
		Review hidden = reviewService.hideReview(id);

		return ResponseEntity.ok(hidden.getId());
	}

	@GetMapping("/comments")
	public ResponseEntity<OnbrdPageResponse<AdminCommentDetail>> getComments(OnbrdPageRequest params) {
		OnbrdPageResponse<AdminCommentDetail> response = commentService.getAdminComment(params);

		return ResponseEntity.ok(response);
	}

	@PatchMapping("/comments/{id}")
	public ResponseEntity<Long> hideComment(@PathVariable Long id) {
		Comment hidden = commentService.hideComment(id);

		return ResponseEntity.ok(hidden.getId());
	}

	@GetMapping("/reports")
	public ResponseEntity<OnbrdPageResponse<AdminReportDetail>> getReports(OnbrdPageRequest params) {
		OnbrdPageResponse<AdminReportDetail> response = reportService.getAdminReports(params);

		return ResponseEntity.ok(response);
	}

	@PatchMapping("/reports/{id}")
	public ResponseEntity<Long> resolveReport(
		@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable Long id) {
		Report resolved = reportService.resolveReport(id, memberDetails.getEmail());

		return ResponseEntity.ok(resolved.getId());
	}
}
