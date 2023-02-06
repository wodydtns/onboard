package com.superboard.onbrd.review.service;

public interface ReviewLikeService {
	void createReviewLikeOrDeleteIfExist(String email, Long reviewId);
}
