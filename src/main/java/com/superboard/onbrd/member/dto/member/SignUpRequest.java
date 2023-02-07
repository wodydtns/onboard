package com.superboard.onbrd.member.dto.member;

import java.util.List;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.media.Schema;
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
	@Schema(description = "관심태그 ID 리스트")
	private List<Long> tagIds;
}
