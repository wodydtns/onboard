package com.superboard.onbrd.auth.dto;

import javax.validation.Valid;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Valid
public class PasswordCheckRequest {
	private String email;
	private String password;
}
