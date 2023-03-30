package com.superboard.onbrd.member.repository;

import static com.superboard.onbrd.member.entity.QMember.*;
import static com.superboard.onbrd.review.entity.QComment.*;
import static com.superboard.onbrd.review.entity.QReview.*;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.admin.dto.AdminMemberDetail;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomMemberRepositoryImpl implements CustomMemberRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public AdminMemberDetail getAdminMemberDetail(Long id) {
		AdminMemberDetail memberDetail = queryFactory
			.select(Projections.fields(AdminMemberDetail.class,
				member.id,
				member.email,
				member.nickname,
				member.profileCharacter,
				member.level,
				member.point,
				member.status,
				member.createdAt.as("signedUpAt"),
				member.lastVisitAt
			))
			.from(member)
			.where(member.id.eq(id))
			.fetchFirst();

		List<AdminMemberDetail.ReviewDetail> reviews = queryFactory
			.select(Projections.fields(AdminMemberDetail.ReviewDetail.class,
				review.id,
				review.content,
				review.createdAt,
				review.boardgame.id.as("boardgameId"),
				review.boardgame.name.as("boardgameName")
			))
			.from(review)
			.where(review.writer.id.eq(id))
			.orderBy(review.id.desc())
			.fetch();

		List<AdminMemberDetail.CommentDetail> comments = queryFactory
			.select(Projections.fields(AdminMemberDetail.CommentDetail.class,
				comment.id,
				comment.content,
				comment.createdAt,
				comment.review.id.as("reviewId"),
				comment.review.boardgame.id.as("boardgameId"),
				comment.review.boardgame.name.as("boardgameName")
			))
			.from(comment)
			.where(comment.writer.id.eq(id))
			.orderBy(comment.id.desc())
			.fetch();

		memberDetail.setReviews(reviews);
		memberDetail.setComments(comments);

		return memberDetail;
	}
}
