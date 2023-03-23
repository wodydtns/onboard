package com.superboard.onbrd.admin.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.admin.dto.NoticeCreateCommand;
import com.superboard.onbrd.admin.dto.NoticePatchRequest;
import com.superboard.onbrd.admin.dto.NoticePostRequest;
import com.superboard.onbrd.admin.dto.NoticeUpdateCommand;
import com.superboard.onbrd.admin.service.NoticeService;
import com.superboard.onbrd.auth.entity.MemberDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/notices")
@RequiredArgsConstructor
@Validated
public class AdminNoticeController {
	private final NoticeService noticeService;

	@PostMapping
	public ResponseEntity<Long> postNotice(
		@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody NoticePostRequest request) {
		String adminEmail = memberDetails.getEmail();
		Long createdId = noticeService.createNotice(new NoticeCreateCommand(adminEmail, request)).getId();

		return ResponseEntity.status(CREATED).body(createdId);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Long> patchNotice(@PathVariable Long id, @RequestBody NoticePatchRequest request) {
		Long updatedId = noticeService.updateNotice(new NoticeUpdateCommand(id, request)).getId();

		return ResponseEntity.ok(updatedId);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteNotice(@PathVariable Long id) {
		noticeService.deleteNotice(id);

		return ResponseEntity.noContent().build();
	}
}
