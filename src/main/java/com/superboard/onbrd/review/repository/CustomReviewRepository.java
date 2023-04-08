package com.superboard.onbrd.review.repository;

import com.superboard.onbrd.admin.dto.AdminReviewDetail;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.global.entity.PageBasicEntity;
import com.superboard.onbrd.review.dto.review.ReviewByBoardgameDetail;
import com.superboard.onbrd.review.dto.review.ReviewByFavoriteCountDetail;
import com.superboard.onbrd.review.dto.review.ReviewGetParameterDto;

public interface CustomReviewRepository {
	OnbrdSliceResponse<AdminReviewDetail> getAdminReviews(OnbrdSliceRequest params);

	OnbrdSliceResponse<ReviewByBoardgameDetail> searchReviewsByBoardgameId(ReviewGetParameterDto params);

	OnbrdSliceResponse<ReviewByFavoriteCountDetail> selectRecommandReviewList(PageBasicEntity pageBasicEntity);
}
