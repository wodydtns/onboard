package com.superboard.onbrd.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.superboard.onbrd.review.dto.review.ReviewByBoardgameIdResponse;
import com.superboard.onbrd.review.dto.review.ReviewCreateDto;
import com.superboard.onbrd.review.dto.review.ReviewGetParameterDto;
import com.superboard.onbrd.review.dto.review.ReviewHomeByFavoriteCount;
import com.superboard.onbrd.review.dto.review.ReviewUpdateDto;
import com.superboard.onbrd.review.entity.Review;

public interface ReviewService {
	ReviewByBoardgameIdResponse getReviewsByBoardgameId(ReviewGetParameterDto params);

	Review crewateReview(ReviewCreateDto dto);

	Review updateReview(ReviewUpdateDto dto);

	void deleteReviewById(Long id);

	Review findVerifiedOneById(Long id);
	
	public Page<ReviewHomeByFavoriteCount> selectRecommandReviewList(Pageable pageable);
}
