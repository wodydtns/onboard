package com.superboard.onbrd.oauth2.dto;

import com.superboard.onbrd.auth.dto.TokenDto;
import com.superboard.onbrd.member.entity.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OauthSignUpResult {
	private Long id;
	private TokenDto tokens;

	public static OauthSignUpResult of(Member member, TokenDto tokens) {
		OauthSignUpResult result = new OauthSignUpResult();

		result.id = member.getId();
		result.tokens = tokens;

		return result;
	}
}
