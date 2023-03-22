package com.superboard.onbrd.review.service;

import com.superboard.onbrd.review.dto.comment.CommentCreateDto;
import com.superboard.onbrd.review.dto.comment.CommentUpdateDto;
import com.superboard.onbrd.review.entity.Comments;

public interface CommentService {
	Comments createComments(CommentCreateDto dto);

	Comments updateComments(CommentUpdateDto dto);

	void deleteCommentsById(Long id);

	Comments findVerifiedOneById(Long id);
}
