package com.superboard.onbrd.oauth2.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.auth.dto.SignInResultDto;
import com.superboard.onbrd.auth.dto.TokenDto;
import com.superboard.onbrd.auth.service.TokenService;
import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.service.MemberService;
import com.superboard.onbrd.oauth2.dto.OauthSignInRequest;
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
	public SignInResultDto signIn(OauthSignInRequest request) {
		String email = request.getEmail();

		Optional<Member> memberOptional = memberService.findByEmail(email);

		if (memberOptional.isPresent()) {
			Member member = memberOptional.get();

			checkMemberIsSocial(member);

			OauthID oauthID = findVerifiedOauthIdByOauthSignRequest(member, request);

			checkOauthIDValid(oauthID, request.getOauthId());

			TokenDto tokens = tokenService.issueTokens(member);

			return new SignInResultDto(member.getId(), tokens);
		}

		Member member = createOauthMember(request);
		TokenDto tokens = tokenService.issueTokens(member);

		return new SignInResultDto(member.getId(), tokens);
	}

	@Override
	public Member createOauthMember(OauthSignInRequest request) {
		Member member = memberService.createMember(Member.fromOauth(request.getEmail()));

		OauthID oauthID = OauthID.of(member, request.getOauthId());
		oauthIDRepository.save(oauthID);

		return member;
	}

	@Override
	public OauthID findVerifiedOauthIdByOauthSignRequest(Member member, OauthSignInRequest request) {
		Optional<OauthID> oauthIDOptional = oauthIDRepository.findByMember(member);

		return oauthIDOptional
			.orElseGet(() -> oauthIDRepository.save(OauthID.of(member, request.getOauthId())));
	}

	private void checkMemberIsSocial(Member member) {
		if (!member.getIsSocial()) {
			throw new BusinessLogicException(OAUTH_MEMBER_NOT_SOCIAL_ALREADY_SIGN_UP);
		}
	}

	private void checkOauthIDValid(OauthID oauthID, String oauthId) {
		if (!oauthId.equals(oauthID.getOauthId())) {
			throw new BusinessLogicException(INVALID_OAUTH_ID);
		}
	}
}
