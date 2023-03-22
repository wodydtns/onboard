package com.superboard.onbrd.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import static com.superboard.onbrd.member.entity.QMember.*;
import static com.superboard.onbrd.review.entity.QReview.*;
import static com.superboard.onbrd.review.entity.QComments.*;
import static com.superboard.onbrd.oauth2.entity.QOauthID.*;
@Repository
@RequiredArgsConstructor
public class CustomCommentRepositoryImpl implements CustomCommentRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public void selectOauthIdForPushMessage(long createdId) {
        queryFactory.select(member.id, oauthID.oauthId).from(member)
                .join(member,oauthID.member)
                .join(review.writer,member)
                .fetch();
    }
}
