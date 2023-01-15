package com.superboard.onbrd.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.review.entity.CommentLike;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
}
