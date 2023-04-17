package com.superboard.onbrd.review.repository;

import static com.superboard.onbrd.auth.entity.QToken.*;
import static com.superboard.onbrd.global.entity.OrderBy.*;
import static com.superboard.onbrd.global.util.PagingUtil.*;
import static com.superboard.onbrd.member.entity.QMember.*;
import static com.superboard.onbrd.review.entity.QComment.*;
import static com.superboard.onbrd.review.entity.QReview.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.admin.dto.AdminCommentDetail;
import com.superboard.onbrd.global.dto.OnbrdListResponse;
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
				comment.review.boardGame.id.as("boardgameId"),
				comment.review.boardGame.name.as("boardgameName")
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
	public OnbrdListResponse<CommentDetail> getCommentsByReviewId(Long reviewId) {
		List<CommentDetail> content = queryFactory
			.select(Projections.fields(CommentDetail.class,
				comment.id,
				comment.content,
				comment.isHidden,
				comment.createdAt,
				comment.writer.id.as("writerId"),
				comment.writer.profileCharacter,
				comment.writer.nickname
			))
			.from(comment)
			.where(comment.review.id.eq(reviewId))
			.orderBy(comment.id.desc())
			.fetch();

		return new OnbrdListResponse<>(content);
	}

	@Override
	public void selectOauthIdForPushMessage(long createdId) {
		Long memberId = queryFactory.select(member.id).from(comment)
			.join(comment.review, review)
			.join(review.writer, member)
			.where(comment.id.eq(createdId))
			.fetchOne();
		String androidPushToken = queryFactory.select(token.androidPushToken)
			.from(token)
			.where(token.id.eq(memberId))
			.fetchOne();

		try {
			fcmUtil.sendAndroidMessage(androidPushToken, "FCM title", "fcm Body");
		} catch (FirebaseMessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
