package com.superboard.onbrd.review.repository;

import com.superboard.onbrd.admin.dto.AdminCommentDetail;
import com.superboard.onbrd.global.dto.OnbrdPageRequest;
import com.superboard.onbrd.global.dto.OnbrdPageResponse;

public interface CustomCommentRepository {
	OnbrdPageResponse<AdminCommentDetail> getAdminComments(OnbrdPageRequest params);

	void selectOauthIdForPushMessage(long createdId);
}
