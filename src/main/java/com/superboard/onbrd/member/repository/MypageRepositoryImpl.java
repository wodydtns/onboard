package com.superboard.onbrd.member.repository;

import static com.superboard.onbrd.boardgame.entity.QBoardgame.*;
import static com.superboard.onbrd.boardgame.entity.QFavoriteBoardgame.*;
import static com.superboard.onbrd.member.entity.QMember.*;
import static com.superboard.onbrd.review.entity.QReview.*;
import static com.superboard.onbrd.tag.entity.QFavoriteTag.*;
import static com.superboard.onbrd.tag.entity.QTag.*;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.global.util.SliceUtil;
import com.superboard.onbrd.member.dto.mypage.MypageGetDto;
import com.superboard.onbrd.member.dto.mypage.MypageGetMoreDto;
import com.superboard.onbrd.member.dto.mypage.MypageMoreBoardgameResponse;
import com.superboard.onbrd.member.dto.mypage.MypageMoreReviewResponse;
import com.superboard.onbrd.member.dto.mypage.MypageResponse;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MypageRepositoryImpl implements MypageRepository {
	private final JPAQueryFactory queryFactory;
	private final SliceUtil sliceUtil;

	@Override
	public MypageResponse getMypage(MypageGetDto params) {
		final String email = params.getEmail();

		MypageResponse response = getMypageMemberInfo(email);

		List<MypageResponse.TagCard> favoriteTags = getFavoriteTags(email);
		List<MypageResponse.ReviewCard> myReviews = getMyReviews(email, params.getReviewCount());
		List<MypageResponse.BoardgameCard> favoriteBoardgames =
			getFavoriteBoardgames(email, params.getBoardgameCount());

		response.setFavoriteTags(favoriteTags);
		response.setMyReviews(myReviews);
		response.setFavoriteBoardgames(favoriteBoardgames);

		return response;
	}

	@Override
	public MypageMoreReviewResponse getMoreReviews(MypageGetMoreDto params) {
		List<MypageMoreReviewResponse.ReviewCard> myReviews = queryFactory
			.select(Projections.fields(MypageMoreReviewResponse.ReviewCard.class,
				review.id,
				review.images
			))
			.from(review)
			.where(review.writer.email.eq(params.getEmail()))
			.orderBy(review.id.desc())
			.offset(params.getOffset())
			.limit(params.getLimit() + 1)
			.fetch();

		Boolean hasNext = sliceUtil.getHasNext(myReviews, params.getLimit());

		return new MypageMoreReviewResponse(hasNext, myReviews);
	}

	@Override
	public MypageMoreBoardgameResponse getMoreFavoriteBoardgames(MypageGetMoreDto params) {
		List<MypageMoreBoardgameResponse.BoardGameCard> favoriteBoardGames = queryFactory
			.select(Projections.fields(MypageMoreBoardgameResponse.BoardGameCard.class,
				boardgame.id,
				boardgame.image
			))
			.from(favoriteBoardgame)
			.join(favoriteBoardgame.boardgame, boardgame)
			.where(favoriteBoardgame.member.email.eq(params.getEmail()))
			.orderBy(favoriteBoardgame.id.desc())
			.offset(params.getOffset())
			.limit(params.getLimit() + 1)
			.fetch();

		Boolean hasNext = sliceUtil.getHasNext(favoriteBoardGames, params.getLimit());

		return new MypageMoreBoardgameResponse(hasNext, favoriteBoardGames);
	}

	private MypageResponse getMypageMemberInfo(String email) {
		return queryFactory
			.select(Projections.bean(MypageResponse.class,
				member.nickname,
				member.profileCharacter,
				member.level,
				member.point
			))
			.from(member)
			.where(member.email.eq(email))
			.fetchFirst();
	}

	private List<MypageResponse.TagCard> getFavoriteTags(String email) {
		return queryFactory
			.select(Projections.fields(MypageResponse.TagCard.class,
				tag.name,
				tag.type
			))
			.from(favoriteTag)
			.join(favoriteTag.tag, tag)
			.where(favoriteTag.member.email.eq(email))
			.fetch();
	}

	private List<MypageResponse.ReviewCard> getMyReviews(String email, int reviewCount) {
		return queryFactory
			.select(Projections.fields(MypageResponse.ReviewCard.class,
				review.id,
				review.images
			))
			.from(review)
			.join(review.writer, member)
			.where(member.email.eq(email))
			.orderBy(review.id.desc())
			.limit(reviewCount)
			.fetch();
	}

	private List<MypageResponse.BoardgameCard> getFavoriteBoardgames(String email, int boardgameCount) {
		return queryFactory
			.select(Projections.fields(MypageResponse.BoardgameCard.class,
				boardgame.id,
				boardgame.image
			))
			.from(favoriteBoardgame)
			.join(favoriteBoardgame.boardgame, boardgame)
			.where(favoriteBoardgame.member.email.eq(email))
			.orderBy(favoriteBoardgame.id.desc())
			.limit(boardgameCount)
			.fetch();
	}
}
