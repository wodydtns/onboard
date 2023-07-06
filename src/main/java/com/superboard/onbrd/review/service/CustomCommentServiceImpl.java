package com.superboard.onbrd.review.service;

import com.superboard.onbrd.home.dto.PushMessageResponse;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.notification.entity.NotificationType;
import com.superboard.onbrd.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import com.superboard.onbrd.review.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

import com.superboard.onbrd.notification.entity.Notification;

@Service
@RequiredArgsConstructor
public class CustomCommentServiceImpl implements CustomCommentService {

	private final CommentRepository commentRepository;

	private final NotificationRepository notificationRepository;

	@Override
	public PushMessageResponse selectOauthIdForPushMessage(long createdId) {
		return commentRepository.selectOauthIdForPushMessage(createdId);
	}

	@Override
	public Long createNotification(Member member, PushMessageResponse payload) {
		Notification notification = Notification.from(member, NotificationType.NEW_COMMENT,payload);
		Notification NotificationResult = notificationRepository.save(notification);
		return NotificationResult.getId();
	}


}
