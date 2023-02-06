package com.superboard.onbrd.member.repository;

import com.superboard.onbrd.member.dto.mypage.MypageGetDto;
import com.superboard.onbrd.member.dto.mypage.MypageGetMoreDto;
import com.superboard.onbrd.member.dto.mypage.MypageMoreBoardgameResponse;
import com.superboard.onbrd.member.dto.mypage.MypageMoreReviewResponse;
import com.superboard.onbrd.member.dto.mypage.MypageResponse;

public interface MypageRepository {
	MypageResponse getMypage(MypageGetDto params);

	MypageMoreReviewResponse getMoreReviews(MypageGetMoreDto params);

	MypageMoreBoardgameResponse getMoreFavoriteBoardgames(MypageGetMoreDto params);
}
