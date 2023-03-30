package com.superboard.onbrd.member.dto.password;

import com.superboard.onbrd.member.entity.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordCreateCommand {
	private Member member;
	private String rawPassword;

	public static PasswordCreateCommand of(Member member, String rawPassword) {
		PasswordCreateCommand command = new PasswordCreateCommand();

		command.member = member;
		command.rawPassword = rawPassword;

		return command;
	}
}
