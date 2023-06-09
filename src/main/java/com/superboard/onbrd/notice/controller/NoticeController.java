package com.superboard.onbrd.notice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.admin.service.NoticeService;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.notice.dto.NoticeDetail;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController {
	private final NoticeService noticeService;

	@GetMapping
	public ResponseEntity<OnbrdSliceResponse<NoticeDetail>> getNotices(@ModelAttribute OnbrdSliceRequest request) {
		request.rebaseToZero();

		OnbrdSliceResponse<NoticeDetail> response = noticeService.getNotices(request);

		return ResponseEntity.ok(response);
	}
}
