package com.superboard.onbrd.member.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.auth.entity.Token;
import com.superboard.onbrd.auth.repository.TokenRepository;
import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.member.dto.MemberUpdateRequest;
import com.superboard.onbrd.member.dto.SignUpRequest;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenRepository tokenRepository;

	@Transactional
	@Override
	public Member signUp(SignUpRequest request) {
		checkEmailDuplicated(request.getEmail());
		request.setPassword(
			passwordEncoder.encode(request.getPassword())
		);

		Member member = memberRepository.save(Member.from(request));
		tokenRepository.save(Token.of(member));

		return member;
	}

	@Transactional
	@Override
	public Member updateMember(MemberUpdateRequest request) {
		Member member = findVerifiedOneById(request.getMemberId());

		Optional.ofNullable(request.getPassword())
			.ifPresent(password -> member.resetPassword(passwordEncoder.encode(password)));

		Optional.ofNullable(request.getNickname())
			.ifPresent(member::updateNickname);

		Optional.ofNullable(request.getProfileCharacter())
			.ifPresent(member::updateProfileCharacter);

		return member;
	}

	@Override
	public Member findVerifiedOneById(Long id) {
		return memberRepository.findById(id)
			.orElseThrow(
				() -> new BusinessLogicException(MEMBER_NOT_FOUND)
			);
	}

	@Override
	public Member findVerifiedOneByEmail(String email) {
		return memberRepository.findByEmail(email)
			.orElseThrow(
				() -> new BusinessLogicException(MEMBER_NOT_FOUND)
			);
	}

	@Override
	public void checkDuplicatedNickname(String nickname) {
		memberRepository.findByNickname(nickname).ifPresent(
			member -> {
				throw new BusinessLogicException(DUPLICATED_NICKNAME,
					String.format("Nickname %s is duplicated", nickname));
			});
	}

	@Override
	public void checkEmailExists(String email) {
		if (!isEmailExists(email)) {
			throw new BusinessLogicException(MEMBER_NOT_FOUND);
		}
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
