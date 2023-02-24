package com.superboard.onbrd.member.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.superboard.onbrd.auth.entity.Token;
import com.superboard.onbrd.auth.service.TokenService;
import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.member.dto.member.SignUpRequest;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.entity.Password;
import com.superboard.onbrd.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberRepository memberRepository;
	private final PasswordService passwordService;
	private final PasswordEncoder passwordEncoder;
	private final TokenService tokenService;

	@Override
	public Member signUp(SignUpRequest request) {
		checkEmailDuplicated(request.getEmail());
		String encodedPassword = passwordEncoder.encode(request.getPassword());

		Member member = memberRepository.save(Member.from(request));
		passwordService.createPassword(Password.of(member, encodedPassword));
		tokenService.createToken(Token.from(member));

		return member;
	}

	@Override
	@Transactional(readOnly = true)
	public Member findVerifiedOneById(Long id) {
		return memberRepository.findById(id)
			.orElseThrow(
				() -> new BusinessLogicException(MEMBER_NOT_FOUND)
			);
	}

	@Override
	@Transactional(readOnly = true)
	public Member findVerifiedOneByEmail(String email) {
		return memberRepository.findByEmail(email)
			.orElseThrow(
				() -> new BusinessLogicException(MEMBER_NOT_FOUND)
			);
	}

	@Override
	@Transactional(readOnly = true)
	public void checkDuplicatedNickname(String nickname) {
		memberRepository.findByNickname(nickname).ifPresent(
			member -> {
				throw new BusinessLogicException(DUPLICATED_NICKNAME,
					String.format("Nickname %s is duplicated", nickname));
			});
	}

	@Override
	@Transactional(readOnly = true)
	public void checkDuplicatedEmail(String email) {
		memberRepository.findByEmail(email).ifPresent(
			member -> {
				throw new BusinessLogicException(DUPLICATED_EMAIL,
					String.format("Email %s is duplicated", email));
			}
		);
	}

	@Override
	public void checkEmailExists(String email) {
		Assert.isTrue(memberRepository.findByEmail(email).isPresent(), "NOT_SIGNED_EMAIL");
	}

	@Override
	public void deleteMemberById(Long id) {
		Member member = findVerifiedOneById(id);
		memberRepository.delete(member);
	}

	@Override
	public Optional<Member> findByEmail(String email) {
		return memberRepository.findByEmail(email);
	}

	@Override
	public Member createMember(Member member) {
		Member created = memberRepository.save(member);
		tokenService.createToken(Token.from(created));

		return created;
	}

	private boolean isEmailExists(String email) {
		return memberRepository.findByEmail(email).isPresent();
	}

	private void checkEmailDuplicated(String email) {
		if (isEmailExists(email)) {
			throw new BusinessLogicException(DUPLICATED_EMAIL, String.format("Email %s is duplicated", email));
		}
	}
}
