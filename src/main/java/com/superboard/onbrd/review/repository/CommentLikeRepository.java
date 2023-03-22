package com.superboard.onbrd.review.repository;

import java.util.Optional;

import com.superboard.onbrd.review.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.review.entity.CommentLike;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
	Optional<CommentLike> findByMemberAndComments(Member member, Comments comments);
	
}
