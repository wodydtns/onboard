package com.superboard.onbrd.review.repository;

import com.superboard.onbrd.admin.dto.AdminCommentDetail;
import com.superboard.onbrd.global.dto.OnbrdListResponse;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.review.dto.comment.CommentDetail;

public interface CustomCommentRepository {
	OnbrdSliceResponse<AdminCommentDetail> getAdminComments(OnbrdSliceRequest params);

	OnbrdListResponse<CommentDetail> getCommentsByReviewId(Long reviewId);

	void selectOauthIdForPushMessage(long createdId);
}
