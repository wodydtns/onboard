package com.superboard.onbrd.member.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;
import static com.superboard.onbrd.member.service.PasswordProperties.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.global.exception.ExceptionCode;
import com.superboard.onbrd.member.dto.password.PasswordChangeDueExtendResponse;
import com.superboard.onbrd.member.dto.password.PasswordChangeDueResponse;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.entity.Password;
import com.superboard.onbrd.member.repository.PasswordRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PasswordServiceImpl implements PasswordService {
	private final PasswordRepository passwordRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void createPassword(Password password) {
		passwordRepository.save(password);
	}

	@Override
	public void resetPassword(Member member, String encodedPassword) {
		Password password = findVerifiedOneByMember(member);

		if (passwordEncoder.matches(encodedPassword, password.getEncodedPassword())) {
			throw new BusinessLogicException(SAME_AS_PREVIOUS_PASSWORD);
		}

		password.resetPassword(encodedPassword);
	}

	@Override
	@Transactional(readOnly = true)
	public PasswordChangeDueResponse getPasswordChangeDue(Member member) {
		checkMemberHavingPassword(member);

		Password password = findVerifiedOneByMember(member);

		LocalDateTime changeDue = password.getModifiedAt()
			.plusDays((long)PASSWORD_CHANGE_PERIOD_DAYS * member.getPasswordChangeDelayCount());

		checkPasswordChangeOverdue(changeDue, LocalDateTime.now());

		String passwordChangeDue = changeDue.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

		return PasswordChangeDueResponse.from(passwordChangeDue);
	}

	@Override
	public PasswordChangeDueExtendResponse extendPasswordChangeDue(Member member) {
		checkMemberHavingPassword(member);

		member.delayPasswordChange();

		Password password = findVerifiedOneByMember(member);
		String extendedChangeDue = password.getModifiedAt()
			.plusDays((long)PASSWORD_CHANGE_PERIOD_DAYS * member.getPasswordChangeDelayCount())
			.toString();

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

	@Override
	@Transactional(readOnly = true)
	public void validatePassword(String rawRequest, String encodedActual) {
		if (!passwordEncoder.matches(rawRequest, encodedActual)) {
			throw new BusinessLogicException(ExceptionCode.INVALID_PASSWORD);
		}
	}

	private void checkPasswordChangeOverdue(LocalDateTime changeDue, LocalDateTime now) {
		if (changeDue.isAfter(now)) {
			throw new BusinessLogicException(PASSWORD_CHANGE_OVERDUE);
		}
	}

	private void checkMemberHavingPassword(Member member) {
		if (member.isSocial()) {
			throw new BusinessLogicException(SOCIAL_MEMBER_NOT_HAVING_PASSWORD);
		}
	}
}
