package com.superboard.onbrd.oauth2.service;

import com.superboard.onbrd.auth.dto.SignInResultDto;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.oauth2.dto.OauthSignInRequest;
import com.superboard.onbrd.oauth2.entity.OauthID;

public interface OauthService {
	SignInResultDto signIn(OauthSignInRequest request);

	Member createOauthMember(OauthSignInRequest request);

	OauthID findVerifiedOauthIdByOauthSignRequest(Member member, OauthSignInRequest request);
}
