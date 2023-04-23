package com.superboard.onbrd.member.service;

import java.util.Optional;

import com.superboard.onbrd.admin.dto.AdminMemberDetail;
import com.superboard.onbrd.member.dto.member.SignUpRequest;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.oauth2.dto.OauthSignUpRequest;

public interface MemberService {
	Member signUp(SignUpRequest request);

	Member signUpFromOauth(OauthSignUpRequest request);

	Member findVerifiedOneById(Long id);

	Member findVerifiedOneByEmail(String email);

	void checkMemberStatus(Member member);

	void checkDuplicatedNickname(String nickname);

	void checkDuplicatedEmail(String email);

	void checkEmailExists(String email);

	void deleteMemberById(Long id);

	Optional<Member> findByEmail(String email);

	Member suspendMember(Long id);

	Member kickMember(Long id);

	AdminMemberDetail getAdminMemberDetail(Long id);

	Member grantAdminAuthority(Long id);
}
