package com.superboard.onbrd.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.review.entity.Comments;

public interface CommentRepository extends JpaRepository<Comments, Long> {
}
