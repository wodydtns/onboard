package com.superboard.onbrd.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordChangeDueResponse {
	private String passwordChangeDue;
	private Boolean changeDueExtended;
	private Boolean overdue;

	public static PasswordChangeDueResponse of(String passwordChangeDue, Boolean changeDueExtended, Boolean overdue) {
		PasswordChangeDueResponse passwordChangeDueResponse = new PasswordChangeDueResponse();
		passwordChangeDueResponse.passwordChangeDue = passwordChangeDue;
		passwordChangeDueResponse.changeDueExtended = changeDueExtended;
		passwordChangeDueResponse.overdue = overdue;

		return passwordChangeDueResponse;
	}

}
