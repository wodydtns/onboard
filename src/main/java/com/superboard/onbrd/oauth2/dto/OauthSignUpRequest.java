package com.superboard.onbrd.oauth2.dto;

import java.util.List;

import com.superboard.onbrd.oauth2.entity.OauthProvider;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OauthSignUpRequest {
	private String email;
	private String oauthId;
	private OauthProvider provider;
	private String nickname;
	private String profileCharacter;
	@Schema(description = "관심태그 ID 리스트")
	private List<Long> tagIds;
}
