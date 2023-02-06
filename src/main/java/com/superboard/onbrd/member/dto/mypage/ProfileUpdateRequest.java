package com.superboard.onbrd.member.dto.mypage;

import javax.validation.Valid;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Valid
public class ProfileUpdateRequest {
	private String nickname;
	private String profileCharacter;
}
