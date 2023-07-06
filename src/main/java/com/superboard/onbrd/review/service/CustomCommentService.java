package com.superboard.onbrd.review.service;

import com.superboard.onbrd.home.dto.PushMessageResponse;
import com.superboard.onbrd.member.entity.Member;

public interface CustomCommentService {
    public PushMessageResponse selectOauthIdForPushMessage(long createdId);

    Long createNotification(Member member, PushMessageResponse payload) ;
}
