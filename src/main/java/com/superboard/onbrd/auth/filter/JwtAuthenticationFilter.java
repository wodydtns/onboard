package com.superboard.onbrd.auth.filter;

import static com.superboard.onbrd.auth.util.AuthProperties.*;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.superboard.onbrd.auth.repository.TokenRepository;
import com.superboard.onbrd.auth.util.JwtTokenProvider;
import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtTokenProvider jwtTokenProvider;
	private final TokenRepository tokenRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		try {
			String authHeader = request.getHeader(AUTH_HEADER);

			if (isInvalidAuthHeader(authHeader)) {
				filterChain.doFilter(request, response);
				return;
			}

			String accessToken = resolveToken(request);
			checkSignOutToken(accessToken);

			setAuthenticationToSecurityContext(accessToken);
			filterChain.doFilter(request, response);

		} catch (Exception e) {
			log.error("Exception Occurs {}", e);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	private boolean isInvalidAuthHeader(String authHeader) {
		return authHeader == null || !authHeader.startsWith(TOKEN_TYPE);
	}

	private String resolveToken(HttpServletRequest request) {
		return request.getHeader(AUTH_HEADER).substring(7);
	}

	private void checkSignOutToken(String accessToken) {
		String email = jwtTokenProvider.parseEmail(accessToken);
		tokenRepository.findSignOutAccessTokenByEmail(email).ifPresent(
			token -> {
				if (token.equals(accessToken)) {
					throw new BusinessLogicException(ExceptionCode.SIGN_OUT_ACCESS_TOKEN);
				}
			}
		);
	}

	private void setAuthenticationToSecurityContext(String accessToken) {
		UserDetails memberDetails = jwtTokenProvider.parseToken(accessToken);
		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}
}
