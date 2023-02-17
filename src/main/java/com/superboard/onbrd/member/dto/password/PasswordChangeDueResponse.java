package com.superboard.onbrd.member.dto.password;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordChangeDueResponse {
	@Schema(description = "비밀번호 변경 기한", example = "2023-05-04T17:19:10")
	private String passwordChangeDue;

	public static PasswordChangeDueResponse from(String passwordChangeDue) {
		PasswordChangeDueResponse passwordChangeDueResponse = new PasswordChangeDueResponse();
		passwordChangeDueResponse.passwordChangeDue = passwordChangeDue;

		return passwordChangeDueResponse;
	}
}
