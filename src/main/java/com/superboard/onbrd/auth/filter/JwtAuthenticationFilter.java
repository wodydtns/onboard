package com.superboard.onbrd.auth.filter;

import static com.superboard.onbrd.auth.util.AuthProperties.*;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.superboard.onbrd.auth.util.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

		try {
			String authHeader = request.getHeader(AUTH_HEADER);

			if (isInvalidAuthHeader(authHeader)) {
				filterChain.doFilter(request, response);
				return;
			}

			String accessToken = resolveToken(request);

			setAuthenticationToSecurityContext(accessToken);
			filterChain.doFilter(request, response);

		} catch (Exception e) {
			log.error("Exception Occurs: ", e);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	private boolean isInvalidAuthHeader(String authHeader) {
		return authHeader == null || !authHeader.startsWith(TOKEN_TYPE);
	}

	private String resolveToken(HttpServletRequest request) {
		return request
			.getHeader(AUTH_HEADER).substring(AUTH_HEADER_BEGIN_INDEX);
	}

	private void setAuthenticationToSecurityContext(String accessToken) {
		UserDetails memberDetails = jwtTokenProvider.parseToken(accessToken);
		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}
}
