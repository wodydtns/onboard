package com.superboard.onbrd.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.admin.dto.AdminInquiryDetail;
import com.superboard.onbrd.admin.dto.InquiryAnswerCommand;
import com.superboard.onbrd.admin.dto.InquiryAnswerRequest;
import com.superboard.onbrd.global.dto.OnbrdPageRequest;
import com.superboard.onbrd.global.dto.OnbrdPageResponse;
import com.superboard.onbrd.inquiry.entity.Inquiry;
import com.superboard.onbrd.inquiry.service.InquiryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/inquiries")
@RequiredArgsConstructor
@Validated
public class AdminInquiryController {
	private final InquiryService inquiryService;

	@GetMapping
	public ResponseEntity<OnbrdPageResponse<AdminInquiryDetail>> getInquiries(OnbrdPageRequest params) {
		params.rebasePageNumberToZero();

		OnbrdPageResponse<AdminInquiryDetail> response = inquiryService.getAdminInquiries(params);

		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Long> answerInquiry(@PathVariable Long id, @RequestBody InquiryAnswerRequest request) {
		Inquiry answered = inquiryService.answerInquiry(
			InquiryAnswerCommand.of(id, request));

		return ResponseEntity.ok(answered.getId());
	}
}
