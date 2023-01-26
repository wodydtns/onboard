package com.superboard.onbrd.auth.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.auth.dto.AuthCodeCheckRequest;
import com.superboard.onbrd.auth.dto.AuthCodeSendingRequest;
import com.superboard.onbrd.auth.dto.PasswordCheckRequest;
import com.superboard.onbrd.auth.dto.SignInRequest;
import com.superboard.onbrd.auth.dto.SignInResponse;
import com.superboard.onbrd.auth.dto.TokenDto;
import com.superboard.onbrd.auth.entity.Token;
import com.superboard.onbrd.auth.repository.TokenRepository;
import com.superboard.onbrd.auth.util.AuthCodeMailProvider;
import com.superboard.onbrd.auth.util.JwtTokenProvider;
import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.global.exception.ExceptionCode;
import com.superboard.onbrd.mail.dto.MailSendingEvent;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final TokenRepository tokenRepository;
	private final AuthCodeMailProvider authCodeMailProvider;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	public SignInResponse signIn(SignInRequest request) {
		Member member = memberService.findVerifiedOneByEmail(request.getEmail());
		validatePassword(request.getPassword(), member.getPassword());

		Token token = tokenRepository.findByMemberId(member.getId()).get();

		String accessToken = jwtTokenProvider.createAccessToken(member.getEmail(), member.getRole().name());
		String refreshToken = jwtTokenProvider.createRefreshToken(member.getEmail());

		token.setRefreshToken(refreshToken);
		token.setRefreshTokenExpiredAt(
			jwtTokenProvider.getExpiredAt(refreshToken));

		return new SignInResponse(member.getId(), accessToken, refreshToken);
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
		LocalDateTime refreshTokenExpiredAt = jwtTokenProvider.getExpiredAt(dto.getRefreshToken());

		if (refreshTokenExpiredAt.isBefore(LocalDateTime.now())) {
			throw new BusinessLogicException(EXPIRED_REFRESH_TOKEN);
		}

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
	public void reconfirmPassword(PasswordCheckRequest request) {
		Member member = memberService.findVerifiedOneByEmail(request.getEmail());
		validatePassword(request.getPassword(), member.getPassword());
	}

	@Override
	public void sendAuthCodeMail(AuthCodeSendingRequest request) {
		String code = authCodeMailProvider.getAuthCode(request.getClientKey());
		MailSendingEvent mailSendingEvent = authCodeMailProvider.buildAuthCodeMail(
			request.getEmail(), code, LocalDateTime.now().plus(3, ChronoUnit.MINUTES));

		eventPublisher.publishEvent(mailSendingEvent);
	}

	@Override
	public void resendAuthCodeMail(AuthCodeSendingRequest request) {
		authCodeMailProvider.removeAuthCodeFromCache(request.getClientKey());
		sendAuthCodeMail(request);
	}

	@Override
	public void checkAuthCode(AuthCodeCheckRequest request) {
		validateAuthCode(authCodeMailProvider.getAuthCode(request.getClientKey()), request);
	}

	private void validatePassword(String rawRequest, String encodedActual) {
		if (!passwordEncoder.matches(rawRequest, encodedActual)) {
			throw new BusinessLogicException(ExceptionCode.INVALID_PASSWORD);
		}
	}

	private void validateAuthCode(String storedCode, AuthCodeCheckRequest request) {
		if (!storedCode.equals(request.getAuthCode())) {
			throw new BusinessLogicException(INVALID_AUTH_CODE);
		} else {
			authCodeMailProvider.removeAuthCodeFromCache(request.getClientKey());
		}
	}
}