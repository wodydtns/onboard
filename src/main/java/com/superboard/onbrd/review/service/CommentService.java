package com.superboard.onbrd.review.service;

import com.superboard.onbrd.review.dto.CommentCreateDto;
import com.superboard.onbrd.review.dto.CommentUpdateDto;
import com.superboard.onbrd.review.entity.Comment;

public interface CommentService {
	Comment createComment(CommentCreateDto dto);

	Comment updateComment(CommentUpdateDto dto);

	void deleteCommentById(Long id);

	Comment findVerifiedOneById(Long id);
}
