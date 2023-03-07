package com.superboard.onbrd.admin.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.admin.dto.NoticeCreateCommand;
import com.superboard.onbrd.admin.dto.NoticeUpdateCommand;
import com.superboard.onbrd.admin.entity.Notice;
import com.superboard.onbrd.admin.repository.NoticeRepository;
import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
	private final NoticeRepository noticeRepository;
	private final MemberService memberService;

	@Override
	public Notice createNotice(NoticeCreateCommand command) {
		Member admin = memberService.findVerifiedOneByEmail(command.getAdminEmail());

		Notice notice = Notice.of(admin, command);

		return noticeRepository.save(notice);
	}

	@Override
	public Notice updateNotice(NoticeUpdateCommand command) {
		Notice notice = findVerifiedOneById(command.getId());

		notice.updateTitle(command.getTitle());
		notice.updateContent(command.getContent());

		return notice;
	}

	@Override
	public void deleteNotice(Long id) {
		Notice notice = findVerifiedOneById(id);

		noticeRepository.delete(notice);
	}

	@Override
	@Transactional(readOnly = true)
	public Notice findVerifiedOneById(Long id) {
		Optional<Notice> noticeOptional = noticeRepository.findById(id);

		return noticeOptional
			.orElseThrow(() -> new BusinessLogicException(NOTICE_NOT_FOUND));
	}
}
