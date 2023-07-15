package com.superboard.onbrd.member.repository;

import static com.superboard.onbrd.boardgame.entity.QBoardGame.*;
import static com.superboard.onbrd.boardgame.entity.QFavoriteBoardgame.*;
import static com.superboard.onbrd.global.util.PagingUtil.*;
import static com.superboard.onbrd.member.entity.QMember.*;
import static com.superboard.onbrd.review.entity.QReview.*;
import static com.superboard.onbrd.tag.entity.QFavoriteTag.*;
import static com.superboard.onbrd.tag.entity.QTag.*;
import static com.superboard.onbrd.boardgame.entity.QBoardgameLike.*;

import java.util.List;

import com.superboard.onbrd.boardgame.entity.BoardgameLike;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.global.dto.OnbrdSliceInfo;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.member.dto.mypage.MypageGetDto;
import com.superboard.onbrd.member.dto.mypage.MypageGetMoreDto;
import com.superboard.onbrd.member.dto.mypage.MypageMoreBoardGameDetail;
import com.superboard.onbrd.member.dto.mypage.MypageMoreReviewDetail;
import com.superboard.onbrd.member.dto.mypage.MypageResponse;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MypageRepositoryImpl implements MypageRepository {
	private final JPAQueryFactory queryFactory;

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
	public OnbrdSliceResponse<MypageMoreReviewDetail> getMoreReviews(MypageGetMoreDto params) {
		List<MypageMoreReviewDetail> content = queryFactory
			.select(Projections.fields(MypageMoreReviewDetail.class,
				review.id,
				boardGame.id.as("boardGameId"),
				boardGame.name.as("boardGameName"),
				boardGame.image.as("boardGameImage")
			))
			.from(review)
			.join(review.boardgame, boardGame)
			.where(review.writer.email.eq(params.getEmail()), review.isHidden.isFalse())
			.orderBy(review.id.desc())
			.offset(params.getOffset())
			.limit(params.getLimit() + 1)
			.fetch();

		OnbrdSliceInfo pageInfo = getSliceInfo(content, params.getLimit());

		return new OnbrdSliceResponse<>(pageInfo, content);
	}

	// favoriteBoardgame -> boardgameLike 변경
	// 사유 : 보드게임 좋아요 리스트를 쌓는 table을 boardgameLike로 변경
	@Override
	public OnbrdSliceResponse<MypageMoreBoardGameDetail> getMoreFavoriteBoardgames(MypageGetMoreDto params) {
		List<MypageMoreBoardGameDetail> content = queryFactory
			.select(Projections.fields(MypageMoreBoardGameDetail.class,
				boardGame.id,
				boardGame.name,
				boardGame.image
			))
			.from(boardgameLike)
			.join(boardgameLike.boardGame, boardGame)
			.where(boardgameLike.member.email.eq(params.getEmail()))
			.orderBy(boardgameLike.id.desc())
			.offset(params.getOffset())
			.limit(params.getLimit() + 1)
			.fetch();

		OnbrdSliceInfo pageInfo = getSliceInfo(content, params.getLimit());
		return new OnbrdSliceResponse<>(pageInfo, content);
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
				tag.id,
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
				boardGame.id.as("boardGameId"),
				boardGame.name.as("boardGameName"),
				boardGame.image.as("boardGameImage")
			))
			.from(review)
			.join(review.boardgame, boardGame)
			.where(review.writer.email.eq(email), review.isHidden.isFalse())
			.orderBy(review.id.desc())
			.limit(reviewCount)
			.fetch();
	}

	private List<MypageResponse.BoardgameCard> getFavoriteBoardgames(String email, int boardgameCount) {
		return queryFactory
			.select(Projections.fields(MypageResponse.BoardgameCard.class,
				boardGame.id,
				boardGame.name,
				boardGame.image
			))
			.from(favoriteBoardgame)
			.join(favoriteBoardgame.boardgame, boardGame)
			.where(favoriteBoardgame.member.email.eq(email))
			.orderBy(favoriteBoardgame.id.desc())
			.limit(boardgameCount)
			.fetch();
	}
}
