package com.superboard.onbrd.member.service;

import com.superboard.onbrd.member.dto.MemberUpdateRequest;
import com.superboard.onbrd.member.dto.SignUpRequest;
import com.superboard.onbrd.member.entity.Member;

public interface MemberService {
	Member signUp(SignUpRequest request);

	Member updateMember(MemberUpdateRequest request);

	Member findVerifiedOneById(Long id);

	Member findVerifiedOneByEmail(String email);

	void checkDuplicatedNickname(String nickname);

	void checkEmailExists(String email);

	void withDraw(Long id);
}
