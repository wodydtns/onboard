package com.superboard.onbrd.auth.dto;

import javax.validation.Valid;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Valid
public class SignInRequest {
	private String email;
	private String password;
}
