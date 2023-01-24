package com.superboard.onbrd.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.global.exception.ExceptionCode;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(username)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

		return MemberDetails.from(member);
	}
}
