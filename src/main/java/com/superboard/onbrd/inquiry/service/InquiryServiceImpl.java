package com.superboard.onbrd.inquiry.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.inquiry.dto.InquiryCreateCommand;
import com.superboard.onbrd.inquiry.dto.InquiryGetResponse;
import com.superboard.onbrd.inquiry.dto.InquiryMyListResponse;
import com.superboard.onbrd.inquiry.dto.InquiryUpdateCommand;
import com.superboard.onbrd.inquiry.entity.Inquiry;
import com.superboard.onbrd.inquiry.repository.InquiryRepository;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {
	private final InquiryRepository inquiryRepository;
	private final MemberService memberService;

	@Override
	public InquiryMyListResponse getMyInquiries(String email) {
		return inquiryRepository.getMyInquiries(email);
	}

	@Override
	@Transactional(readOnly = true)
	public InquiryGetResponse getInquiryResponse(Long id) {
		return inquiryRepository.getInquiryResponse(id);
	}

	@Override
	public Inquiry createInquiry(InquiryCreateCommand command) {
		Member member = memberService.findVerifiedOneByEmail(command.getEmail());
		Inquiry created = Inquiry.of(member, command);

		return inquiryRepository.save(created);
	}

	@Override
	public Long updateInquiry(InquiryUpdateCommand command) {
		Inquiry found = findVerifiedOneById(command.getId());

		found.updateTitle(command.getTitle());
		found.updateContent(command.getContent());

		return found.getId();
	}

	@Override
	public Inquiry findVerifiedOneById(Long id) {
		Optional<Inquiry> inquiryOptional = inquiryRepository.findById(id);

		return inquiryOptional
			.orElseThrow(() -> new BusinessLogicException(INQUIRY_NOT_FOUND));
	}
}
