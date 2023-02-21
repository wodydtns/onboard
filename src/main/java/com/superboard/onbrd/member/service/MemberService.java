package com.superboard.onbrd.member.service;

import java.util.Optional;

import com.superboard.onbrd.member.dto.member.SignUpRequest;
import com.superboard.onbrd.member.entity.Member;

public interface MemberService {
	Member signUp(SignUpRequest request);

	Member findVerifiedOneById(Long id);

	Member findVerifiedOneByEmail(String email);

	void checkDuplicatedNickname(String nickname);

	void checkEmailExists(String email);

	void deleteMemberById(Long id);

	Optional<Member> findByEmail(String email);

	Member createMember(Member member);
}
