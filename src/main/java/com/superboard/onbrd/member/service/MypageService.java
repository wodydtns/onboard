package com.superboard.onbrd.member.service;

import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.member.dto.mypage.MypageGetDto;
import com.superboard.onbrd.member.dto.mypage.MypageGetMoreDto;
import com.superboard.onbrd.member.dto.mypage.MypageMoreBoardgameDetail;
import com.superboard.onbrd.member.dto.mypage.MypageMoreReviewDetail;
import com.superboard.onbrd.member.dto.mypage.MypageResponse;
import com.superboard.onbrd.member.dto.mypage.ProfileUpdateRequest;

public interface MypageService {
	MypageResponse getMypage(MypageGetDto params);

	OnbrdSliceResponse<MypageMoreReviewDetail> getMoreReviews(MypageGetMoreDto params);

	OnbrdSliceResponse<MypageMoreBoardgameDetail> getMoreFavoriteBoardgames(MypageGetMoreDto params);

	void updateProfile(String email, ProfileUpdateRequest request);

	void changePassword(String email, String password);

	void withDrawByEmail(String email);
}
