package com.superboard.onbrd.review.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superboard.onbrd.review.dto.review.ReviewHomeByFavoriteCount;
import com.superboard.onbrd.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewHomeController {
	
	private final ReviewService reviewService;

	@GetMapping("/recommandReview")
	public Page<ReviewHomeByFavoriteCount> selectRecommandReviewList(Pageable pageable){
		return reviewService.selectRecommandReviewList(pageable);
	}
}
