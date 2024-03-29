package com.superboard.onbrd.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.admin.dto.AdminInquiryDetail;
import com.superboard.onbrd.admin.dto.InquiryAnswerCommand;
import com.superboard.onbrd.admin.dto.InquiryAnswerRequest;
import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.inquiry.entity.Inquiry;
import com.superboard.onbrd.inquiry.service.InquiryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Admin")
@RestController
@RequestMapping("/api/v1/admin/inquiries")
@RequiredArgsConstructor
@Validated
public class AdminInquiryController {
	private final InquiryService inquiryService;

	@Tag(name = "Admin")
	@GetMapping
	public ResponseEntity<OnbrdSliceResponse<AdminInquiryDetail>> getInquiries(
		@ModelAttribute OnbrdSliceRequest params) {
		params.rebaseToZero();

		OnbrdSliceResponse<AdminInquiryDetail> response = inquiryService.getAdminInquiries(params);

		return ResponseEntity.ok(response);
	}

	@Tag(name = "Admin")
	@PatchMapping("/{id}")
	public ResponseEntity<Long> answerInquiry(
		@PathVariable Long id,
		@RequestBody InquiryAnswerRequest request,
		@AuthenticationPrincipal MemberDetails memberDetails) {

		Inquiry answered = inquiryService.answerInquiry(
			InquiryAnswerCommand.of(id, request, memberDetails.getEmail()));

		return ResponseEntity.ok(answered.getId());
	}
}
