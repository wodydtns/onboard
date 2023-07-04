package com.superboard.onbrd.review.service;

import com.superboard.onbrd.global.entity.FCMMessageDto;
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
	public String selectOauthIdForPushMessage(long createdId) {
		return commentRepository.selectOauthIdForPushMessage(createdId);
	}

	@Override
	public void createNotification(Member member, String payload) {
		Notification notification = Notification.from(member, NotificationType.NEW_COMMENT,payload);
		notificationRepository.save(notification);
	}


}
