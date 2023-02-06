package com.superboard.onbrd.review.repository;

import com.superboard.onbrd.review.dto.ReviewByBoardgameIdResponse;
import com.superboard.onbrd.review.dto.ReviewGetParameterDto;

public interface CustomReviewRepository {
	ReviewByBoardgameIdResponse searchReviewsByBoardgameId(ReviewGetParameterDto params);
}
