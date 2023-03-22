package com.superboard.onbrd.auth.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;
import static com.superboard.onbrd.member.entity.MemberStatus.*;

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
import com.superboard.onbrd.auth.util.AuthCodeMailProvider;
import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.global.exception.OnbrdAssert;
import com.superboard.onbrd.mail.dto.MailSendingEvent;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.entity.Password;
import com.superboard.onbrd.member.service.MemberService;
import com.superboard.onbrd.member.service.PasswordService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final MemberService memberService;
	private final PasswordService passwordService;
	private final TokenService tokenService;
	private final AuthCodeMailProvider authCodeMailProvider;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	public SignInResultDto signIn(SignInRequest request) {
		Member member = memberService.findVerifiedOneByEmail(request.getEmail());
		Password password = passwordService.findVerifiedOneByMember(member);
		passwordService.validatePassword(request.getPassword(), password.getEncodedPassword());

		OnbrdAssert.state(member.getStatus() == SUSPENDED, SUSPENDED_MEMBER);
		OnbrdAssert.state(member.getStatus() == WITHDRAWN, WITHDRAWN_MEMBER);
		OnbrdAssert.state(member.getStatus() == KICKED, KICKED_MEMBER);

		TokenDto tokens = tokenService.issueTokens(member);

		return new SignInResultDto(member.getId(), tokens);
	}

	@Override
	public void signOut(String email) {
		Member member = memberService.findVerifiedOneByEmail(email);
		tokenService.breakRefreshToken(member);
	}

	@Override
	public TokenDto reissueTokens(String refreshToken) {
		tokenService.checkRefreshTokenExpired(refreshToken);

		Token token = tokenService.findVerifiedOneByRefreshToken(refreshToken);
		Member member = memberService.findVerifiedOneById(token.getId());

		return tokenService.issueTokens(member);
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

	@Override
	public String issuePasswordResetToken() {
		return tokenService.issuePasswordResetToken();
	}

	@Override
	public void validateResetToken(String resetToken) {
		tokenService.validateResetToken(resetToken);
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
}
