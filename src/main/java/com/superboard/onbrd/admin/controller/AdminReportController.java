package com.superboard.onbrd.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.superboard.onbrd.admin.dto.AdminReportDetail;
import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.report.entity.Report;
import com.superboard.onbrd.report.service.ReportService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Tag(name = "Admin")
@Getter
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/comments")
public class AdminReportController {
	private final ReportService reportService;

	@GetMapping("/reports")
	public ResponseEntity<OnbrdSliceResponse<AdminReportDetail>> getReports(@ModelAttribute OnbrdSliceRequest params) {
		OnbrdSliceResponse<AdminReportDetail> response = reportService.getAdminReports(params);

		return ResponseEntity.ok(response);
	}

	@Tag(name = "Admin")
	@PatchMapping("/reports/{id}")
	public ResponseEntity<Long> resolveReport(
		@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable Long id) {
		Report resolved = reportService.resolveReport(id, memberDetails.getEmail());

		return ResponseEntity.ok(resolved.getId());
	}
}
