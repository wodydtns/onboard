package com.superboard.onbrd.auth.service;

import com.superboard.onbrd.auth.dto.TokenDto;
import com.superboard.onbrd.auth.entity.Token;
import com.superboard.onbrd.member.entity.Member;

public interface TokenService {
	Token findVerifiedOneByMember(Member member);

	Token findVerifiedOneByRefreshToken(String refreshToken);

	TokenDto issueTokens(Member member);

	void breakRefreshToken(Member member);
}
