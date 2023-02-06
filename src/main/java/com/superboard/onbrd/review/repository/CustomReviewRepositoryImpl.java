package com.superboard.onbrd.review.repository;

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
import com.superboard.onbrd.global.util.SliceUtil;
import com.superboard.onbrd.review.dto.review.ReviewByBoardgameIdResponse;
import com.superboard.onbrd.review.dto.review.ReviewGetParameterDto;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomReviewRepositoryImpl implements CustomReviewRepository {
	private final JPAQueryFactory queryFactory;
	private final SliceUtil sliceUtil;

	@Override
	public ReviewByBoardgameIdResponse searchReviewsByBoardgameId(ReviewGetParameterDto params) {
		List<ReviewByBoardgameIdResponse.ReviewCard> reviewCards = getReviewCards(params);

		Boolean hasNext = sliceUtil.getHasNext(reviewCards, params.getLimit());

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
}
