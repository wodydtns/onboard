package com.superboard.onbrd.review.service;

import org.springframework.stereotype.Service;

import com.superboard.onbrd.review.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomCommentServiceImpl implements CustomCommentService {

	private final CommentRepository commentRepository;

	@Override
	public void selectOauthIdForPushMessage(long createdId) {
		commentRepository.selectOauthIdForPushMessage(createdId);
	}
}
