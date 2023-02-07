package com.superboard.onbrd.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthCodeSendingResponse {
	@Schema(description = "인증 코드 확인시 사용되는 클라이언트 식별키")
	private String clientKey;
}
