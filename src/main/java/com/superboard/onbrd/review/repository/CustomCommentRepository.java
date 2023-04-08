package com.superboard.onbrd.review.repository;

import com.superboard.onbrd.admin.dto.AdminCommentDetail;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;

public interface CustomCommentRepository {
	OnbrdSliceResponse<AdminCommentDetail> getAdminComments(OnbrdSliceRequest params);

	void selectOauthIdForPushMessage(long createdId);
}
