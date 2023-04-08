package com.superboard.onbrd.review.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.superboard.onbrd.admin.dto.AdminReviewDetail;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.global.entity.PageBasicEntity;
import com.superboard.onbrd.review.dto.review.ReviewByBoardgameIdResponse;
import com.superboard.onbrd.review.dto.review.ReviewCreateDto;
import com.superboard.onbrd.review.dto.review.ReviewGetParameterDto;
import com.superboard.onbrd.review.dto.review.ReviewHomeByFavoriteCount;
import com.superboard.onbrd.review.dto.review.ReviewUpdateDto;
import com.superboard.onbrd.review.entity.Review;

public interface ReviewService {
	OnbrdSliceResponse<AdminReviewDetail> getAdminReviews(OnbrdSliceRequest params);

	ReviewByBoardgameIdResponse getReviewsByBoardgameId(ReviewGetParameterDto params);

	Review crewateReview(ReviewCreateDto dto, List<MultipartFile> files);

	Review updateReview(ReviewUpdateDto dto, List<MultipartFile> files);

	Review hideReview(Long id);

	void deleteReviewById(Long id);

	Review findVerifiedOneById(Long id);

	List<ReviewHomeByFavoriteCount> selectRecommandReviewList(PageBasicEntity pageBasicEntity);
}
