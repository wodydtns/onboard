package com.superboard.onbrd.auth.dto;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema
@Getter
@NoArgsConstructor
@Valid
public class SignInRequest {
	@Schema
	private String email;
	@Schema
	private String password;
}
