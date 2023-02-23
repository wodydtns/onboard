package com.superboard.onbrd.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.superboard.onbrd.review.dto.review.ReviewByBoardgameIdResponse;
import com.superboard.onbrd.review.dto.review.ReviewGetParameterDto;
import com.superboard.onbrd.review.dto.review.ReviewHomeByFavoriteCount;

public interface CustomReviewRepository {
	ReviewByBoardgameIdResponse searchReviewsByBoardgameId(ReviewGetParameterDto params);
	
	public Page<ReviewHomeByFavoriteCount> selectRecommandReviewList(Pageable pageable);
}
