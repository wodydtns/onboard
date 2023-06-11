package com.superboard.onbrd.review.repository;

import static com.superboard.onbrd.boardgame.entity.QBoardGame.*;
import static com.superboard.onbrd.global.entity.OrderBy.*;
import static com.superboard.onbrd.global.util.PagingUtil.*;
import static com.superboard.onbrd.member.entity.QMember.*;
import static com.superboard.onbrd.review.entity.QReview.*;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.admin.dto.AdminReviewDetail;
import com.superboard.onbrd.global.dto.OnbrdSliceInfo;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.review.dto.review.ReviewByBoardgameDetail;
import com.superboard.onbrd.review.dto.review.ReviewByFavoriteCountDetail;
import com.superboard.onbrd.review.dto.review.ReviewGetParameterDto;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomReviewRepositoryImpl implements CustomReviewRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public OnbrdSliceResponse<AdminReviewDetail> getAdminReviews(OnbrdSliceRequest params) {
		List<AdminReviewDetail> content = queryFactory
			.select(Projections.fields(AdminReviewDetail.class,
				review.id,
				review.content,
				review.isHidden,
				review.createdAt,
				review.writer.id.as("writerId"),
				review.writer.nickname,
				review.boardgame.id.as("boardgameId"),
				review.boardgame.name.as("boardgameName")
			))
			.from(review)
			.orderBy(REVIEW_NEWEST.getOrderSpecifiers())
			.offset(params.getOffset())
			.limit(params.getLimit() + 1)
			.fetch();

		OnbrdSliceInfo pageInfo = getSliceInfo(content, params.getLimit());

		return new OnbrdSliceResponse<>(pageInfo, content);
	}

	@Override
	public OnbrdSliceResponse<ReviewByBoardgameDetail> searchReviewsByBoardgameId(ReviewGetParameterDto params) {
		List<ReviewByBoardgameDetail> content = queryFactory
			.select(Projections.fields(ReviewByBoardgameDetail.class,
				review.id,
				review.grade,
				review.content,
				review.images,
				review.likeCount,
				review.isHidden,
				review.createdAt,
				member.id.as("writer_id"),
				member.nickname,
				member.profileCharacter
			))
			.from(review)
			.join(review.writer, member)
			.where(review.boardgame.id.eq(params.getBoardgameId()))
			.orderBy(params.getOrderBy().getOrderSpecifiers())
			.offset(params.getOffset())
			.limit(params.getLimit() + 1)
			.fetch();
		OnbrdSliceInfo pageInfo = getSliceInfo(content, params.getLimit());

		return new OnbrdSliceResponse<>(pageInfo, content);
	}

	@Override
	public OnbrdSliceResponse<ReviewByFavoriteCountDetail> selectRecommandReviewList(OnbrdSliceRequest request) {
		List<ReviewByFavoriteCountDetail> content = queryFactory
			.select(Projections.fields(ReviewByFavoriteCountDetail.class,
				review.id,
				review.images,
				review.content,
				member.nickname.as("writerNickname"),
				member.level.as("writerLevel"),
				boardGame.name.as("boardGameTitle"),
				review.likeCount
			))
			.from(review)
			.join(review.writer, member)
			.join(review.boardgame, boardGame)
			.orderBy(review.likeCount.desc())
			.offset(request.getOffset())
			.limit(request.getLimit() + 1)
			.fetch();

		OnbrdSliceInfo pageInfo = getSliceInfo(content, request.getLimit());

		return new OnbrdSliceResponse<>(pageInfo, content);
	}
}
