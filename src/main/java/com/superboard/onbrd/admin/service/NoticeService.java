package com.superboard.onbrd.admin.service;

import com.superboard.onbrd.admin.dto.NoticeCreateCommand;
import com.superboard.onbrd.admin.dto.NoticeUpdateCommand;
import com.superboard.onbrd.admin.entity.Notice;

public interface NoticeService {
	Notice createNotice(NoticeCreateCommand command);

	Notice updateNotice(NoticeUpdateCommand command);

	void deleteNotice(Long id);

	Notice findVerifiedOneById(Long id);
}
