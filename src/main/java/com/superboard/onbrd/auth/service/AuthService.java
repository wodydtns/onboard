package com.superboard.onbrd.auth.service;

import com.superboard.onbrd.auth.dto.AuthCodeCheckRequest;
import com.superboard.onbrd.auth.dto.AuthCodeSendingResponse;
import com.superboard.onbrd.auth.dto.PasswordCheckCommand;
import com.superboard.onbrd.auth.dto.SignInRequest;
import com.superboard.onbrd.auth.dto.SignInResult;
import com.superboard.onbrd.auth.dto.TokenDto;

public interface AuthService {
	SignInResult signIn(SignInRequest request);

	void signOut(String email);

	TokenDto reissueTokens(String refreshToken);

	void reconfirmPassword(PasswordCheckCommand command);

	AuthCodeSendingResponse sendAuthCodeMail(String email);

	void checkAuthCode(AuthCodeCheckRequest request);

	String issuePasswordResetToken();

	void validateResetToken(String resetToken);
}
