package com.superboard.onbrd.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.notification.dto.command.NotificationCheckCommand;
import com.superboard.onbrd.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
	private final NotificationService notificationService;

	@PatchMapping("/{notificationId}")
	public ResponseEntity<Long> checkNotification(
		@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable Long notificationId) {
		Long checkedId =
			notificationService.check(new NotificationCheckCommand(memberDetails.getEmail(), notificationId));

		return ResponseEntity.ok(checkedId);
	}
}
