package com.superboard.onbrd.member.repository;

import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.member.dto.mypage.MypageGetDto;
import com.superboard.onbrd.member.dto.mypage.MypageGetMoreDto;
import com.superboard.onbrd.member.dto.mypage.MypageMoreBoardGameDetail;
import com.superboard.onbrd.member.dto.mypage.MypageMoreReviewDetail;
import com.superboard.onbrd.member.dto.mypage.MypageResponse;

public interface MypageRepository {
	MypageResponse getMypage(MypageGetDto params);

	OnbrdSliceResponse<MypageMoreReviewDetail> getMoreReviews(MypageGetMoreDto params);

	OnbrdSliceResponse<MypageMoreBoardGameDetail> getMoreFavoriteBoardGames(MypageGetMoreDto params);
}
