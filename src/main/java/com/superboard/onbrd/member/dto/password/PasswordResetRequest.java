package com.superboard.onbrd.member.dto.password;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordResetRequest {
	private String email;
	private String password;
}
