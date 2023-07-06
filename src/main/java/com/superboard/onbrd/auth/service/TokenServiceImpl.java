package com.superboard.onbrd.auth.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.auth.dto.TokenDto;
import com.superboard.onbrd.auth.entity.Token;
import com.superboard.onbrd.auth.repository.TokenRepository;
import com.superboard.onbrd.auth.util.JwtTokenProvider;
import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.member.entity.Member;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenServiceImpl implements TokenService {
	private final TokenRepository tokenRepository;
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public Token findVerifiedOneByMember(Member member) {
		Optional<Token> tokenOptional = tokenRepository.findById(member.getId());

		return tokenOptional
			.orElseGet(() -> tokenRepository.save(Token.from(member)));
	}

	@Override
	public Token findVerifiedOneByRefreshToken(String refreshToken) {
		Optional<Token> tokenOptional = tokenRepository.findByRefreshToken(refreshToken);

		return tokenOptional
			.orElseThrow(() -> new BusinessLogicException(INVALID_REFRESH_TOKEN));
	}

	@Override
	public TokenDto issueTokens(Member member) {
		Token token = findVerifiedOneByMember(member);

		String accessToken = jwtTokenProvider.createAccessToken(member.getEmail(), member.getRole().name());
		String refreshToken = jwtTokenProvider.createRefreshToken(member.getEmail());

		token.setRefreshToken(refreshToken);

		return new TokenDto(accessToken, refreshToken);
	}

	@Override
	@Transactional(readOnly = true)
	public void checkRefreshTokenExpired(String refreshToken) {
		try {
			jwtTokenProvider.getExpiredAt(refreshToken);

		} catch (ExpiredJwtException e) {
			e.printStackTrace();
			throw new BusinessLogicException(EXPIRED_REFRESH_TOKEN);
		}
	}

	@Override
	public void breakRefreshToken(Member member) {
		Token token = findVerifiedOneByMember(member);
		token.setRefreshToken(null);
	}

	@Override
	public void createToken(Token token) {
		Optional<Token> tokenOptional = tokenRepository.findById(token.getMember().getId());
		if(tokenOptional.isPresent()){
			Token updateToken = tokenOptional.get();
			updateToken.setMember(token.getMember());
			updateToken.setAndroidPushToken(token.getAndroidPushToken());
			updateToken.setApplePushToken(token.getApplePushToken());
			tokenRepository.save(updateToken);
		}else{
			tokenRepository.save(token);
		}
	}

	@Override
	public String issuePasswordResetToken() {
		return jwtTokenProvider.createResetToken();
	}

	@Override
	public void validateResetToken(String resetToken) {
		try {
			jwtTokenProvider.getExpiredAt(resetToken);

		} catch (ExpiredJwtException e) {
			e.printStackTrace();
			throw new BusinessLogicException(EXPIRED_RESET_TOKEN);
		}
	}
}
