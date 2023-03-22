package com.superboard.onbrd.review.service;

import com.superboard.onbrd.review.repository.CustomCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomCommentServiceImpl implements CustomCommentService{

    private final CustomCommentRepository customCommentRepository;
    @Override
    public void selectOauthIdForPushMessage(long createdId) {
        customCommentRepository.selectOauthIdForPushMessage(createdId);
    }
}
