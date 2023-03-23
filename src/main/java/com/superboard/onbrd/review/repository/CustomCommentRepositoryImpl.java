package com.superboard.onbrd.review.repository;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.global.util.FCMUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import static com.superboard.onbrd.member.entity.QMember.*;
import static com.superboard.onbrd.review.entity.QReview.*;
import static com.superboard.onbrd.review.entity.QComments.*;
import static com.superboard.onbrd.auth.entity.QToken.*;
@Repository
@RequiredArgsConstructor
public class CustomCommentRepositoryImpl implements CustomCommentRepository{

    private final JPAQueryFactory queryFactory;

    private final FCMUtil fcmUtil;

    @Override
    public void selectOauthIdForPushMessage(long createdId) {
        Long memberId = queryFactory.select(member.id).from(comments)
                .join(comments.review, review)
                .join(review.writer, member)
                .where(comments.id.eq(createdId))
                .fetchOne();
        String refreshToken = queryFactory.select(token.refreshToken).from(token).where(token.id.eq(memberId)).fetchOne();

        try {
            fcmUtil.sendAndroidMessage(refreshToken,"FCM title","fcm Body");
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
