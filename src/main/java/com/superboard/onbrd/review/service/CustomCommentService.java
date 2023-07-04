package com.superboard.onbrd.review.service;

import com.superboard.onbrd.member.entity.Member;

public interface CustomCommentService {
    public String selectOauthIdForPushMessage(long createdId);

    void createNotification(Member member, String payload) ;
}
