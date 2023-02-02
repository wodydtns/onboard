package com.superboard.onbrd.member.service;

import com.superboard.onbrd.member.dto.PasswordChangeDueExtendResponse;
import com.superboard.onbrd.member.dto.PasswordChangeDueResponse;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.entity.Password;

public interface PasswordService {
	void createPassword(Password password);

	void resetPassword(Member member, String encodedPassword);

	PasswordChangeDueResponse getPasswordChangeDue(Member member);

	PasswordChangeDueExtendResponse extendPasswordChangeDue(Member member);

	Password findVerifiedOneByMember(Member member);
}
