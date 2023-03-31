package com.superboard.onbrd.review.repository;

import static com.superboard.onbrd.auth.entity.QToken.*;
import static com.superboard.onbrd.global.entity.OrderBy.*;
import static com.superboard.onbrd.member.entity.QMember.*;
import static com.superboard.onbrd.review.entity.QComment.*;
import static com.superboard.onbrd.review.entity.QReview.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.admin.dto.AdminCommentDetail;
import com.superboard.onbrd.global.dto.OnbrdPageInfo;
import com.superboard.onbrd.global.dto.OnbrdPageRequest;
import com.superboard.onbrd.global.dto.OnbrdPageResponse;
import com.superboard.onbrd.global.entity.OrderBy;
import com.superboard.onbrd.global.util.FCMUtil;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomCommentRepositoryImpl implements CustomCommentRepository {

	private final JPAQueryFactory queryFactory;

	private final FCMUtil fcmUtil;

	@Override
	public OnbrdPageResponse<AdminCommentDetail> getAdminComments(OnbrdPageRequest params) {
		OrderBy orderBy = COMMENT_NEWEST;

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
			.orderBy(orderBy.getOrderSpecifiers())
			.offset(params.getOffset())
			.limit(params.getPageSize())
			.fetch();

		long totalElements = queryFactory
			.select(comment.count())
			.from(comment)
			.fetchFirst();

		OnbrdPageInfo pageInfo = OnbrdPageInfo.of(params, content, totalElements, orderBy);

		return new OnbrdPageResponse<>(pageInfo, content);
	}

	@Override
	public void selectOauthIdForPushMessage(long createdId) {
		Long memberId = queryFactory.select(member.id).from(comment)
			.join(comment.review, review)
			.join(review.writer, member)
			.where(comment.id.eq(createdId))
			.fetchOne();
		String refreshToken = queryFactory.select(token.refreshToken)
			.from(token)
			.where(token.id.eq(memberId))
			.fetchOne();

		try {
			fcmUtil.sendAndroidMessage(refreshToken, "FCM title", "fcm Body");
		} catch (FirebaseMessagingException e) {
			throw new RuntimeException(e);
		}
	}
}