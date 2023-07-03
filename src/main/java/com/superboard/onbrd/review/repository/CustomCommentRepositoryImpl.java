package com.superboard.onbrd.review.repository;

import static com.superboard.onbrd.auth.entity.QToken.*;
import static com.superboard.onbrd.global.entity.OrderBy.*;
import static com.superboard.onbrd.global.util.PagingUtil.*;
import static com.superboard.onbrd.review.entity.QComment.*;
import static com.superboard.onbrd.review.entity.QReview.*;

import java.io.IOException;
import java.util.List;

import com.superboard.onbrd.global.entity.FCMMessageDto;
import com.superboard.onbrd.review.dto.comment.CommentPushMessage;
import org.springframework.stereotype.Repository;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.admin.dto.AdminCommentDetail;
import com.superboard.onbrd.global.dto.OnbrdSliceInfo;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.global.util.FCMUtil;
import com.superboard.onbrd.review.dto.comment.CommentDetail;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomCommentRepositoryImpl implements CustomCommentRepository {

    private final JPAQueryFactory queryFactory;

    private final FCMUtil fcmUtil;

    @Override
    public OnbrdSliceResponse<AdminCommentDetail> getAdminComments(OnbrdSliceRequest params) {
        List<AdminCommentDetail> content = queryFactory
                .select(Projections.fields(AdminCommentDetail.class,
                        comment.id,
                        comment.content,
                        comment.isHidden,
                        comment.createdAt,
                        comment.writer.id.as("writerId"),
                        comment.writer.nickname,
                        comment.review.id.as("reviewId"),
                        comment.review.boardgame.id.as("boardgameId"),
                        comment.review.boardgame.name.as("boardgameName")
                ))
                .from(comment)
                .orderBy(COMMENT_NEWEST.getOrderSpecifiers())
                .offset(params.getOffset())
                .limit(params.getLimit() + 1)
                .fetch();

        OnbrdSliceInfo pageInfo = getSliceInfo(content, params.getLimit());

        return new OnbrdSliceResponse<>(pageInfo, content);
    }

    @Override
    public OnbrdSliceResponse<CommentDetail> getCommentsByReviewId(Long reviewId, OnbrdSliceRequest request) {
        List<CommentDetail> content = queryFactory
                .select(Projections.fields(CommentDetail.class,
                        comment.id,
                        comment.content,
                        comment.isHidden,
                        comment.createdAt,
                        comment.writer.id.as("writerId"),
                        comment.writer.profileCharacter,
                        comment.writer.nickname,
                        comment.writer.level.as("writerLevel")
                ))
                .from(comment)
                .where(comment.review.id.eq(reviewId), comment.isHidden.isFalse())
                .orderBy(comment.id.desc())
                .offset(request.getOffset())
                .limit(request.getLimit() + 1)
                .fetch();

        OnbrdSliceInfo pageInfo = getSliceInfo(content, request.getLimit());

        return new OnbrdSliceResponse<>(pageInfo, content);
    }

    @Override
    public void selectOauthIdForPushMessage(long createdId) {
        CommentPushMessage commentPushMessage = queryFactory
                .select(Projections.fields(CommentPushMessage.class,
                        review.writer.id.as("writerId"),
                        review.boardgame.id.as("boardgameId")
                ))
                .from(comment)
                .join(comment.review, review)
                .where(comment.id.eq(createdId))
                .fetchOne();

        String androidPushToken = queryFactory.select(token.androidPushToken)
                .from(token)
                .where(token.id.eq(commentPushMessage.getWriterId()))
                .fetchOne();
        String title = "title";
        String body = "내가 작성한 리뷰에 댓글이 달렸어요!";
        try {
            String response = fcmUtil.sendMessageTo(androidPushToken,title,body,"NEW_COMMENT", String.valueOf(commentPushMessage.getBoardgameId()));
            System.out.println("Successfully sent message: " + response);
        } catch ( IOException e) {
            throw new RuntimeException(e);
        }
    }

}
