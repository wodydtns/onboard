package com.superboard.onbrd.member.service;

import com.superboard.onbrd.member.dto.member.SignUpRequest;
import com.superboard.onbrd.member.entity.Member;

public interface MemberService {
	Member signUp(SignUpRequest request);

	Member findVerifiedOneById(Long id);

	Member findVerifiedOneByEmail(String email);

	void checkDuplicatedNickname(String nickname);

	void checkEmailExists(String email);
}
