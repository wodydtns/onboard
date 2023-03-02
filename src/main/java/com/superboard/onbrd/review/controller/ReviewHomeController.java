package com.superboard.onbrd.review.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.review.dto.review.ReviewHomeByFavoriteCount;
import com.superboard.onbrd.review.service.ReviewService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewHomeController {
	
	private final ReviewService reviewService;

	@ApiOperation(value = "추천 리뷰 목록")
	@GetMapping("/curation")
	public Page<ReviewHomeByFavoriteCount> selectRecommandReviewList(@PageableDefault(size = 5) Pageable pageable){
		return reviewService.selectRecommandReviewList(pageable);
	}
}
