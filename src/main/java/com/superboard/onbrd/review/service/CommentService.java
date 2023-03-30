package com.superboard.onbrd.review.service;

import com.superboard.onbrd.admin.dto.AdminCommentDetail;
import com.superboard.onbrd.global.dto.OnbrdPageRequest;
import com.superboard.onbrd.global.dto.OnbrdPageResponse;
import com.superboard.onbrd.review.dto.comment.CommentCreateDto;
import com.superboard.onbrd.review.dto.comment.CommentUpdateDto;
import com.superboard.onbrd.review.entity.Comment;

public interface CommentService {
	OnbrdPageResponse<AdminCommentDetail> getAdminComment(OnbrdPageRequest params);

	Comment createComment(CommentCreateDto dto);

	Comment updateComment(CommentUpdateDto dto);

	Comment hideComment(Long id);

	void deleteCommentById(Long id);

	Comment findVerifiedOneById(Long id);
}
