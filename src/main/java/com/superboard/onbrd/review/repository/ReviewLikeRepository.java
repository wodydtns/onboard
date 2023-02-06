package com.superboard.onbrd.review.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.review.entity.Review;
import com.superboard.onbrd.review.entity.ReviewLike;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
	Optional<ReviewLike> findByMemberAndReview(Member member, Review review);
}
