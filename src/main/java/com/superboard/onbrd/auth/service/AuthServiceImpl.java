package com.superboard.onbrd.auth.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.auth.dto.AuthCodeCheckRequest;
import com.superboard.onbrd.auth.dto.AuthCodeSendingResponse;
import com.superboard.onbrd.auth.dto.PasswordCheckRequest;
import com.superboard.onbrd.auth.dto.SignInRequest;
import com.superboard.onbrd.auth.dto.SignInResultDto;
import com.superboard.onbrd.auth.dto.TokenDto;
import com.superboard.onbrd.auth.entity.Token;
import com.superboard.onbrd.auth.repository.TokenRepository;
import com.superboard.onbrd.auth.util.AuthCodeMailProvider;
import com.superboard.onbrd.auth.util.JwtTokenProvider;
import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.mail.dto.MailSendingEvent;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.entity.Password;
import com.superboard.onbrd.member.service.MemberService;
import com.superboard.onbrd.member.service.PasswordService;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final MemberService memberService;
	private final PasswordService passwordService;
	private final JwtTokenProvider jwtTokenProvider;
	private final TokenRepository tokenRepository;
	private final AuthCodeMailProvider authCodeMailProvider;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	public SignInResultDto signIn(SignInRequest request) {
		Member member = memberService.findVerifiedOneByEmail(request.getEmail());
		Password password = passwordService.findVerifiedOneByMember(member);
		passwordService.validatePassword(request.getPassword(), password.getEncodedPassword());

		Token token = tokenRepository.findByMemberId(member.getId()).get();

		String accessToken = jwtTokenProvider.createAccessToken(member.getEmail(), member.getRole().name());
		String refreshToken = jwtTokenProvider.createRefreshToken(member.getEmail());

		token.setRefreshToken(refreshToken);
		token.setRefreshTokenExpiredAt(
			jwtTokenProvider.getExpiredAt(refreshToken));

		return new SignInResultDto(member.getId(), accessToken, refreshToken);
	}

	@Override
	public void signOut(String accessToken) {
		String email = jwtTokenProvider.parseEmail(accessToken);
		LocalDateTime expiredAt = jwtTokenProvider.getExpiredAt(accessToken);

		Token token = tokenRepository.findByEmail(email);
		token.setRefreshToken(null);
		token.setRefreshTokenExpiredAt(null);

		if (expiredAt.isAfter(LocalDateTime.now())) {
			token.setSignOutAccessToken(accessToken);
		}
	}

	@Override
	public TokenDto reissueTokens(TokenDto dto) {
		checkRefreshTokenExpired(dto.getRefreshToken());

		String email = jwtTokenProvider.parseEmail(dto.getAccessToken());
		LocalDateTime accessTokenExpiredAt = jwtTokenProvider.getExpiredAt(dto.getAccessToken());

		Member member = memberService.findVerifiedOneByEmail(email);
		Token token = tokenRepository.findByEmail(email);

		String reissuedAccessToken = jwtTokenProvider.createAccessToken(email, member.getRole().name());
		String reissuedRefreshToken = jwtTokenProvider.createRefreshToken(email);
		LocalDateTime reissuedRefreshTokenExpiredAt = jwtTokenProvider.getExpiredAt(reissuedRefreshToken);

		if (accessTokenExpiredAt.isAfter(LocalDateTime.now())) {
			token.setSignOutAccessToken(dto.getAccessToken());
		}

		token.setRefreshToken(reissuedRefreshToken);
		token.setRefreshTokenExpiredAt(reissuedRefreshTokenExpiredAt);

		dto.setAccessToken(reissuedAccessToken);
		dto.setRefreshToken(reissuedRefreshToken);

		return dto;
	}

	@Override
	@Transactional(readOnly = true)
	public void reconfirmPassword(PasswordCheckRequest request) {
		Member member = memberService.findVerifiedOneByEmail(request.getEmail());
		Password password = passwordService.findVerifiedOneByMember(member);
		passwordService.validatePassword(request.getPassword(), password.getEncodedPassword());
	}

	@Override
	public AuthCodeSendingResponse sendAuthCodeMail(String email) {
		LocalDateTime now = LocalDateTime.now();
		String clientKey = getClientKey(email, now);
		String code = authCodeMailProvider.getAuthCode(clientKey);

		MailSendingEvent mailSendingEvent = authCodeMailProvider.buildAuthCodeMail(
			email, code, now.plus(3, ChronoUnit.MINUTES));
		eventPublisher.publishEvent(mailSendingEvent);

		return new AuthCodeSendingResponse(clientKey);
	}

	@Override
	public void checkAuthCode(AuthCodeCheckRequest request) {
		validateAuthCode(authCodeMailProvider.getAuthCode(request.getClientKey()), request);
	}

	private void validateAuthCode(String storedCode, AuthCodeCheckRequest request) {
		if (!storedCode.equals(request.getAuthCode())) {
			throw new BusinessLogicException(INVALID_AUTH_CODE);
		} else {
			authCodeMailProvider.removeAuthCodeFromCache(request.getClientKey());
		}
	}

	private String getClientKey(String email, LocalDateTime requestTime) {
		return String.valueOf((email + requestTime).hashCode());
	}

	private void checkRefreshTokenExpired(String refreshToken) {
		try {
			jwtTokenProvider.getExpiredAt(refreshToken);

		} catch (ExpiredJwtException e) {
			throw new BusinessLogicException(EXPIRED_REFRESH_TOKEN);
		}
	}
}
