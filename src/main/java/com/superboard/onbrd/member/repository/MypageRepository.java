package com.superboard.onbrd.member.repository;

import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.member.dto.mypage.*;
import com.superboard.onbrd.member.dto.mypage.MypageMoreBoardGameDetail;

public interface MypageRepository {
	MypageResponse getMypage(MypageGetDto params);

	OnbrdSliceResponse<MypageMoreReviewDetail> getMoreReviews(MypageGetMoreDto params);

	OnbrdSliceResponse<MypageMoreBoardGameDetail> getMoreFavoriteBoardgames(MypageGetMoreDto params);
}
