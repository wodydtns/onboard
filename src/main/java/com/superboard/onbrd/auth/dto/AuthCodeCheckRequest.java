package com.superboard.onbrd.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthCodeCheckRequest {
	@Schema(description = "인증 코드 발급 요청 시 응답으로 받은 clientKey")
	private String clientKey;
	@Schema(description = "메일로 받은 6자리 숫자 인증코드")
	private String authCode;
}
