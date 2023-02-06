package com.superboard.onbrd.review.service;

import com.superboard.onbrd.review.dto.ReviewByBoardgameIdResponse;
import com.superboard.onbrd.review.dto.ReviewCreateDto;
import com.superboard.onbrd.review.dto.ReviewGetParameterDto;
import com.superboard.onbrd.review.dto.ReviewUpdateDto;
import com.superboard.onbrd.review.entity.Review;

public interface ReviewService {
	ReviewByBoardgameIdResponse getReviewsByBoardgameId(ReviewGetParameterDto params);

	Review crewateReview(ReviewCreateDto dto);

	Review updateReview(ReviewUpdateDto dto);

	void deleteReviewById(Long id);

	Review findVerifiedOneById(Long id);
}
