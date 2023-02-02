package com.superboard.onbrd.member.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;
import static com.superboard.onbrd.member.service.PasswordProperties.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.member.dto.PasswordChangeDueExtendResponse;
import com.superboard.onbrd.member.dto.PasswordChangeDueResponse;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.entity.Password;
import com.superboard.onbrd.member.repository.PasswordRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PasswordServiceImpl implements PasswordService {
	private final PasswordRepository passwordRepository;

	@Override
	public void createPassword(Password password) {
		passwordRepository.save(password);
	}

	@Override
	public void resetPassword(Member member, String encodedPassword) {
		Password password = findVerifiedOneByMember(member);
		password.resetPassword(encodedPassword);
	}

	@Override
	@Transactional(readOnly = true)
	public PasswordChangeDueResponse getPasswordChangeDue(Member member) {
		Password password = findVerifiedOneByMember(member);

		LocalDateTime changeDue = password.getModifiedAt().plusDays(PASSWORD_CHANGE_PERIOD_DAYS);
		if (member.getPasswordChangeDueExtended()) {
			changeDue = changeDue.plusDays(PASSWORD_CHANGE_PERIOD_DAYS);
		}

		String passwordChangeDue = changeDue.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		Boolean overdue = LocalDateTime.now().isAfter(changeDue);

		return PasswordChangeDueResponse.of(passwordChangeDue, member.getPasswordChangeDueExtended(), overdue);
	}

	@Override
	public PasswordChangeDueExtendResponse extendPasswordChangeDue(Member member) {
		if (member.getPasswordChangeDueExtended()) {
			throw new BusinessLogicException(PASSWORD_CHANGE_DUE_ALREADY_EXTENDED);
		}

		member.extendChangeDue();
		Password password = findVerifiedOneByMember(member);
		String extendedChangeDue = password.getModifiedAt()
			.plusDays(2 * PASSWORD_CHANGE_PERIOD_DAYS)
			.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

		return PasswordChangeDueExtendResponse.from(extendedChangeDue);
	}

	@Override
	@Transactional(readOnly = true)
	public Password findVerifiedOneByMember(Member member) {
		Optional<Password> passwordOrNull = passwordRepository.findByMember(member);
		if (passwordOrNull.isEmpty()) {
			throw new BusinessLogicException(PASSWORD_NOT_FOUND);
		}

		return passwordOrNull.get();
	}
}
