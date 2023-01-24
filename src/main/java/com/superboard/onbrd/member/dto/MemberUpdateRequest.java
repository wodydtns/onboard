package com.superboard.onbrd.member.dto;

import javax.validation.Valid;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Valid
public class MemberUpdateRequest {
	private Long memberId;
	private String password;
	private String nickname;
	private String profileCharacter;
}
