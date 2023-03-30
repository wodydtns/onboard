package com.superboard.onbrd.review.repository;

import java.util.List;

import com.superboard.onbrd.admin.dto.AdminReviewDetail;
import com.superboard.onbrd.global.dto.OnbrdPageRequest;
import com.superboard.onbrd.global.dto.OnbrdPageResponse;
import com.superboard.onbrd.global.entity.PageBasicEntity;
import com.superboard.onbrd.review.dto.review.ReviewByBoardgameIdResponse;
import com.superboard.onbrd.review.dto.review.ReviewGetParameterDto;
import com.superboard.onbrd.review.dto.review.ReviewHomeByFavoriteCount;

public interface CustomReviewRepository {
	OnbrdPageResponse<AdminReviewDetail> getAdminReviews(OnbrdPageRequest params);

	ReviewByBoardgameIdResponse searchReviewsByBoardgameId(ReviewGetParameterDto params);

	List<ReviewHomeByFavoriteCount> selectRecommandReviewList(PageBasicEntity pageBasicEntity);
}
