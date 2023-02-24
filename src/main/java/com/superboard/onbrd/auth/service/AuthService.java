package com.superboard.onbrd.auth.service;

import com.superboard.onbrd.auth.dto.AuthCodeCheckRequest;
import com.superboard.onbrd.auth.dto.AuthCodeSendingResponse;
import com.superboard.onbrd.auth.dto.PasswordCheckRequest;
import com.superboard.onbrd.auth.dto.SignInRequest;
import com.superboard.onbrd.auth.dto.SignInResultDto;
import com.superboard.onbrd.auth.dto.TokenDto;

public interface AuthService {
	SignInResultDto signIn(SignInRequest request);

	void signOut(String email);

	TokenDto reissueTokens(String refreshToken);

	void reconfirmPassword(PasswordCheckRequest request);

	AuthCodeSendingResponse sendAuthCodeMail(String email);

	void checkAuthCode(AuthCodeCheckRequest request);

	String issuePasswordResetToken();

	void validateResetToken(String resetToken);
}
