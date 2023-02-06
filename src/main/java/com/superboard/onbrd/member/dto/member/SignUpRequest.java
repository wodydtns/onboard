package com.superboard.onbrd.member.dto.member;

import java.util.List;

import javax.validation.Valid;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Valid
public class SignUpRequest {
	private String email;
	private String password;
	private String nickname;
	private String profileCharacter;
	private List<Long> tagIds;
}
