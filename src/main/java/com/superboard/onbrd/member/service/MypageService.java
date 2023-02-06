package com.superboard.onbrd.member.service;

import com.superboard.onbrd.member.dto.mypage.MypageGetDto;
import com.superboard.onbrd.member.dto.mypage.MypageGetMoreDto;
import com.superboard.onbrd.member.dto.mypage.MypageMoreBoardgameResponse;
import com.superboard.onbrd.member.dto.mypage.MypageMoreReviewResponse;
import com.superboard.onbrd.member.dto.mypage.MypageResponse;
import com.superboard.onbrd.member.dto.mypage.ProfileUpdateRequest;

public interface MypageService {
	MypageResponse getMypage(MypageGetDto params);

	MypageMoreReviewResponse getMoreReviews(MypageGetMoreDto params);

	MypageMoreBoardgameResponse getMoreFavoriteBoardgames(MypageGetMoreDto params);

	void updateProfile(String email, ProfileUpdateRequest request);

	void changePassword(String email, String password);

	void withDrawByEmail(String email);
}
