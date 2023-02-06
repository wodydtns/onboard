package com.superboard.onbrd.review.repository;

import com.superboard.onbrd.review.dto.review.ReviewByBoardgameIdResponse;
import com.superboard.onbrd.review.dto.review.ReviewGetParameterDto;

public interface CustomReviewRepository {
	ReviewByBoardgameIdResponse searchReviewsByBoardgameId(ReviewGetParameterDto params);
}
