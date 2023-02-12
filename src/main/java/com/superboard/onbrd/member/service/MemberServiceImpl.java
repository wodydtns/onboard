package com.superboard.onbrd.member.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.auth.entity.Token;
import com.superboard.onbrd.auth.repository.TokenRepository;
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
	private final TokenRepository tokenRepository;

	@Override
	public Member signUp(SignUpRequest request) {
		checkEmailDuplicated(request.getEmail());
		String encodedPassword = passwordEncoder.encode(request.getPassword());

		Member member = memberRepository.save(Member.from(request));
		passwordService.createPassword(Password.of(member, encodedPassword));
		tokenRepository.save(Token.from(member));

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
	public void checkEmailExists(String email) {
		memberRepository.findByEmail(email).ifPresent(
			member -> {
				throw new BusinessLogicException(DUPLICATED_EMAIL,
					String.format("Email %s is duplicated", email));
			}
		);
	}

	@Override
	public void deleteMemberById(Long id) {
		Member member = findVerifiedOneById(id);
		memberRepository.delete(member);
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
