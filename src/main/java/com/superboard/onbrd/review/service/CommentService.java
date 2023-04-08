package com.superboard.onbrd.review.service;

import com.superboard.onbrd.admin.dto.AdminCommentDetail;
import com.superboard.onbrd.global.dto.OnbrdListResponse;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.review.dto.comment.CommentCreateDto;
import com.superboard.onbrd.review.dto.comment.CommentDetail;
import com.superboard.onbrd.review.dto.comment.CommentUpdateDto;
import com.superboard.onbrd.review.entity.Comment;

public interface CommentService {
	OnbrdSliceResponse<AdminCommentDetail> getAdminComment(OnbrdSliceRequest params);

	Comment createComment(CommentCreateDto dto);

	Comment updateComment(CommentUpdateDto dto);

	Comment hideComment(Long id);

	void deleteCommentById(Long id);

	Comment findVerifiedOneById(Long id);

	OnbrdListResponse<CommentDetail> getCommentsByReviewId(Long reviewId);
}
