package com.superboard.onbrd.review.repository;

import static com.superboard.onbrd.boardgame.entity.QBoardgame.*;
import static com.superboard.onbrd.global.entity.OrderBy.*;
import static com.superboard.onbrd.global.util.PagingUtil.*;
import static com.superboard.onbrd.member.entity.QMember.*;
import static com.superboard.onbrd.review.entity.QComment.*;
import static com.superboard.onbrd.review.entity.QReview.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.admin.dto.AdminReviewDetail;
import com.superboard.onbrd.global.dto.OnbrdSliceInfo;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.global.entity.PageBasicEntity;
import com.superboard.onbrd.review.dto.review.ReviewByBoardgameIdResponse;
import com.superboard.onbrd.review.dto.review.ReviewGetParameterDto;
import com.superboard.onbrd.review.dto.review.ReviewHomeByFavoriteCount;

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
	public ReviewByBoardgameIdResponse searchReviewsByBoardgameId(ReviewGetParameterDto params) {
		List<ReviewByBoardgameIdResponse.ReviewCard> reviewCards = getReviewCards(params);

		Boolean hasNext = getHasNext(reviewCards, params.getLimit());

		List<Long> reviewIds = reviewCards.stream()
			.map(ReviewByBoardgameIdResponse.ReviewCard::getId)
			.collect(Collectors.toList());

		Map<Long, List<ReviewByBoardgameIdResponse.CommentCard>> reviewIdCommentCardListMap =
			getReviewIdCommentCardListMap(reviewIds);

		reviewCards.forEach(
			reviewCard -> reviewCard.setComments(
				reviewIdCommentCardListMap.get(reviewCard.getId())
			)
		);

		return new ReviewByBoardgameIdResponse(hasNext, reviewCards);
	}

	private List<ReviewByBoardgameIdResponse.ReviewCard> getReviewCards(ReviewGetParameterDto params) {
		return queryFactory
			.select(Projections.fields(ReviewByBoardgameIdResponse.ReviewCard.class,
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
	}

	private Map<Long, List<ReviewByBoardgameIdResponse.CommentCard>>
	getReviewIdCommentCardListMap(List<Long> reviewIds) {
		List<Tuple> tuples = queryFactory
			.select(comment.review.id, Projections.fields(ReviewByBoardgameIdResponse.CommentCard.class,
				comment.id,
				comment.content,
				comment.isHidden,
				comment.createdAt,
				member.id.as("writer_id"),
				member.nickname,
				member.profileCharacter
			))
			.from(comment)
			.join(comment.writer, member)
			.where(comment.review.id.in(reviewIds))
			.fetch();

		return tuples.stream()
			.collect(
				Collectors.groupingBy(
					tuple -> tuple.get(comment.review.id),
					Collectors.mapping(tuple -> tuple.get(1, ReviewByBoardgameIdResponse.CommentCard.class),
						Collectors.toList())
				));
	}

	@Override
	public List<ReviewHomeByFavoriteCount> selectRecommandReviewList(PageBasicEntity pageBasicEntity) {
		return queryFactory.select(
				Projections.fields(ReviewHomeByFavoriteCount.class, review.id,
					review.images, review.content, member.nickname, member.level, boardgame.name, review.likeCount
				))
			.from(review)
			.join(review.writer, member)
			.join(review.boardgame, boardgame)
			.orderBy(review.likeCount.desc())
			.limit(pageBasicEntity.getLimit()).fetch();
	}
}
