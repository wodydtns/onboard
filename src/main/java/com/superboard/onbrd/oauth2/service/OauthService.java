package com.superboard.onbrd.oauth2.service;

import com.superboard.onbrd.auth.dto.SignInResult;
import com.superboard.onbrd.oauth2.dto.OauthIntegrateRequest;
import com.superboard.onbrd.oauth2.dto.OauthSignInRequest;
import com.superboard.onbrd.oauth2.dto.OauthSignUpRequest;
import com.superboard.onbrd.oauth2.entity.OauthID;

public interface OauthService {
	SignInResult signIn(OauthSignInRequest request);

	Long signUp(OauthSignUpRequest request);

	Long integrate(OauthIntegrateRequest request);

	OauthID findVerifiedOneByMemberId(Long memberId);
}
