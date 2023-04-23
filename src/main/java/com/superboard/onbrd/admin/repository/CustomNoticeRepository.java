package com.superboard.onbrd.admin.repository;

import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.notice.dto.NoticeDetail;

public interface CustomNoticeRepository {
	OnbrdSliceResponse<NoticeDetail> getNotices(OnbrdSliceRequest request);
}
