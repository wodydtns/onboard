package com.superboard.onbrd.member.dto.password;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordChangeDueExtendResponse {
	@Schema(description = "연장된 비밀번호 변경 기한", example = "2023-08-04T17:19:10")
	private String extendChangeDue;

	public static PasswordChangeDueExtendResponse from(String extendChangeDue) {
		PasswordChangeDueExtendResponse response = new PasswordChangeDueExtendResponse();
		response.extendChangeDue = extendChangeDue;

		return response;
	}
}
