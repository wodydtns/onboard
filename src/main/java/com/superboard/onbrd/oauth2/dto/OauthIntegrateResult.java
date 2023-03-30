package com.superboard.onbrd.oauth2.dto;

import com.superboard.onbrd.auth.dto.TokenDto;
import com.superboard.onbrd.member.entity.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OauthIntegrateResult {
	private Long id;
	private TokenDto tokens;

	public static OauthIntegrateResult of(Member member, TokenDto tokens) {
		OauthIntegrateResult result = new OauthIntegrateResult();

		result.id = member.getId();
		result.tokens = tokens;

		return result;
	}
}
