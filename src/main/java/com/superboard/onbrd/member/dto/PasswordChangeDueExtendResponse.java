package com.superboard.onbrd.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordChangeDueExtendResponse {
	private String extendChangeDue;

	public static PasswordChangeDueExtendResponse from(String extendChangeDue) {
		PasswordChangeDueExtendResponse response = new PasswordChangeDueExtendResponse();
		response.extendChangeDue = extendChangeDue;

		return response;
	}
}
