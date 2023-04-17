package com.superboard.onbrd.member.service;

import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.member.dto.mypage.*;
import com.superboard.onbrd.member.dto.mypage.MypageMoreBoardGameDetail;

public interface MypageService {
	MypageResponse getMypage(MypageGetDto params);

	OnbrdSliceResponse<MypageMoreReviewDetail> getMoreReviews(MypageGetMoreDto params);

	OnbrdSliceResponse<MypageMoreBoardGameDetail> getMoreFavoriteBoardgames(MypageGetMoreDto params);

	void updateProfile(String email, ProfileUpdateRequest request);

	void changePassword(String email, String password);

	void withDrawByEmail(String email);
}
