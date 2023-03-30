package com.superboard.onbrd.member.service;

import com.superboard.onbrd.member.dto.password.PasswordChangeDueExtendResponse;
import com.superboard.onbrd.member.dto.password.PasswordChangeDueResponse;
import com.superboard.onbrd.member.dto.password.PasswordCreateCommand;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.entity.Password;

public interface PasswordService {
	void createPassword(PasswordCreateCommand command);

	void resetPassword(Member member, String encodedPassword);

	PasswordChangeDueResponse getPasswordChangeDue(Member member);

	PasswordChangeDueExtendResponse extendPasswordChangeDue(Member member);

	Password findVerifiedOneByMember(Member member);

	void validatePassword(String rawRequest, String encodedActual);
}
