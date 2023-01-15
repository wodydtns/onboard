package com.superboard.onbrd.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.review.entity.ReviewLike;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
}
