package com.superboard.onbrd.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordCheckCommand {
	private String email;
	private String password;

	public static PasswordCheckCommand of(String email, PasswordCheckRequest request) {
		PasswordCheckCommand command = new PasswordCheckCommand();

		command.email = email;
		command.password = request.getPassword();

		return command;
	}
}
