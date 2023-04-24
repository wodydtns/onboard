package com.superboard.onbrd.review.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.superboard.onbrd.admin.dto.AdminReviewDetail;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.global.entity.PageBasicEntity;
import com.superboard.onbrd.review.dto.review.ReviewByBoardgameDetail;
import com.superboard.onbrd.review.dto.review.ReviewByFavoriteCountDetail;
import com.superboard.onbrd.review.dto.review.ReviewCreateDto;
import com.superboard.onbrd.review.dto.review.ReviewGetParameterDto;
import com.superboard.onbrd.review.dto.review.ReviewUpdateDto;
import com.superboard.onbrd.review.entity.Review;

public interface ReviewService {
	OnbrdSliceResponse<AdminReviewDetail> getAdminReviews(OnbrdSliceRequest params);

	OnbrdSliceResponse<ReviewByBoardgameDetail> getReviewsByBoardgameId(ReviewGetParameterDto params);

	Review crewateReview(ReviewCreateDto dto, List<String> images);

	Review updateReview(ReviewUpdateDto dto, List<String> images);

	Review hideReview(Long id);

	void deleteReviewById(Long id);

	Review findVerifiedOneById(Long id);

	OnbrdSliceResponse<ReviewByFavoriteCountDetail> selectRecommandReviewList(PageBasicEntity pageBasicEntity);
}
