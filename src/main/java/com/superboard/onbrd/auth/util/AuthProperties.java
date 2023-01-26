package com.superboard.onbrd.auth.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthProperties {
	public static final String AUTH_HEADER = "Authorization";
	public static final String REFRESH_TOKEN_HEADER = "RefreshToken";
	public static final String TOKEN_TYPE = "Bearer";
	public static final int AUTH_HEADER_BEGIN_INDEX = 7;
}
