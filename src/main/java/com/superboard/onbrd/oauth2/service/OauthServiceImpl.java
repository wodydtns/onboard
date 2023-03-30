package com.superboard.onbrd.oauth2.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;
import static com.superboard.onbrd.global.exception.OnbrdAssert.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.auth.dto.SignInResult;
import com.superboard.onbrd.auth.dto.TokenDto;
import com.superboard.onbrd.auth.service.TokenService;
import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.service.MemberService;
import com.superboard.onbrd.oauth2.dto.OauthIntegrateRequest;
import com.superboard.onbrd.oauth2.dto.OauthIntegrateResult;
import com.superboard.onbrd.oauth2.dto.OauthSignInRequest;
import com.superboard.onbrd.oauth2.dto.OauthSignUpRequest;
import com.superboard.onbrd.oauth2.dto.OauthSignUpResult;
import com.superboard.onbrd.oauth2.entity.OauthID;
import com.superboard.onbrd.oauth2.repository.OauthIDRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OauthServiceImpl implements OauthService {
	private final OauthIDRepository oauthIDRepository;
	private final MemberService memberService;
	private final TokenService tokenService;

	@Override
	public SignInResult signIn(OauthSignInRequest request) {
		Member member = memberService.findVerifiedOneByEmail(request.getEmail());

		checkMemberIsSocial(member);

		OauthID oauthID = findVerifiedOneByMemberId(member.getId());

		checkOauthIDValid(oauthID, request);

		TokenDto tokens = tokenService.issueTokens(member);

		return new SignInResult(member.getId(), tokens);
	}

	@Override
	public OauthSignUpResult signUp(OauthSignUpRequest request) {
		Member created = memberService.signUpFromOauth(request);
		OauthID oauthID = oauthIDRepository.save(OauthID.of(created, request.getOauthId(), request.getProvider()));

		TokenDto tokens = tokenService.issueTokens(created);

		return OauthSignUpResult.of(created, tokens);
	}

	@Override
	public OauthIntegrateResult integrate(OauthIntegrateRequest request) {
		Member member = memberService.findVerifiedOneByEmail(request.getEmail());
		OauthID oauthID = oauthIDRepository.save(OauthID.of(member, request.getOauthId(), request.getProvider()));

		member.integrate();

		TokenDto tokens = tokenService.issueTokens(member);

		return OauthIntegrateResult.of(member, tokens);
	}

	@Override
	public OauthID findVerifiedOneByMemberId(Long memberId) {
		return oauthIDRepository.findById(memberId)
			.orElseThrow(() -> new BusinessLogicException(OAUTH_ID_NOT_FOUND));
	}

	private void checkMemberIsSocial(Member member) {
		state(member.getIsSocial(), NOT_SOCIAL_MEMBER);
	}

	private void checkOauthIDValid(OauthID oauthID, OauthSignInRequest request) {
		isTrue(
			request.getOauthId().equals(oauthID.getOauthId()) &&
				request.getProvider() == oauthID.getProvider(),
			INVALID_OAUTH_ID);
	}
}
