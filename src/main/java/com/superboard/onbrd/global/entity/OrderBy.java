package com.superboard.onbrd.global.entity;

import static com.superboard.onbrd.boardgame.entity.QBoardgame.*;
import static com.superboard.onbrd.review.entity.QReview.*;

import com.querydsl.core.types.OrderSpecifier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderBy {
	//BOARDGAME_MOST_FAVORITE("보드게임_관심회원순", new OrderSpecifier<?>[] {boardgame.favoriteCount.desc()});
	// favorite count 임시 주석 처리
	//BOARDGAME_MOST_FAVORITE("보드게임_관심회원순", new OrderSpecifier<?>[] {boardgame.playerCount.desc()}),

	REVIEW_NEWEST("리뷰_최신순", new OrderSpecifier<?>[] {review.id.desc()}),
	REVIEW_MOST_LIKE("리뷰_좋아요순", new OrderSpecifier<?>[] {review.likeCount.desc(), review.id.desc()});

	private final String desc;
	private final OrderSpecifier<?>[] orderSpecifiers;
}
