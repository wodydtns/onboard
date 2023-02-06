package com.superboard.onbrd.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>, CustomReviewRepository {
}
