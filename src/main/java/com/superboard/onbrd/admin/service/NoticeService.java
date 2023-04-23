package com.superboard.onbrd.admin.service;

import com.superboard.onbrd.admin.dto.NoticeCreateCommand;
import com.superboard.onbrd.admin.dto.NoticeUpdateCommand;
import com.superboard.onbrd.admin.entity.Notice;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.notice.dto.NoticeDetail;

public interface NoticeService {
	Notice createNotice(NoticeCreateCommand command);

	Notice updateNotice(NoticeUpdateCommand command);

	void deleteNotice(Long id);

	Notice findVerifiedOneById(Long id);

	OnbrdSliceResponse<NoticeDetail> getNotices(OnbrdSliceRequest request);
}
