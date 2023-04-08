package com.superboard.onbrd.review.repository;

import java.util.List;

import com.superboard.onbrd.admin.dto.AdminReviewDetail;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.global.entity.PageBasicEntity;
import com.superboard.onbrd.review.dto.review.ReviewByBoardgameIdResponse;
import com.superboard.onbrd.review.dto.review.ReviewGetParameterDto;
import com.superboard.onbrd.review.dto.review.ReviewHomeByFavoriteCount;

public interface CustomReviewRepository {
	OnbrdSliceResponse<AdminReviewDetail> getAdminReviews(OnbrdSliceRequest params);

	ReviewByBoardgameIdResponse searchReviewsByBoardgameId(ReviewGetParameterDto params);

	List<ReviewHomeByFavoriteCount> selectRecommandReviewList(PageBasicEntity pageBasicEntity);
}
