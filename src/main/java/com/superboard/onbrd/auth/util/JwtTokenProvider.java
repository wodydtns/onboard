package com.superboard.onbrd.auth.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.superboard.onbrd.auth.entity.MemberDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {
	private final String CLAIM_EMAIL = "email";
	private final String CLAIM_AUTHORITY = "authority";

	@Value("${jwt.secret-key}")
	private String secretKey;
	@Value("${jwt.access-token-expiration-minutes}")
	private int accessTokenExpirationMinutes;
	@Value("${jwt.refresh-token-expiration-minutes}")
	private int refreshTokenExpirationMinutes;

	@PostConstruct
	protected void init() {
		secretKey = Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	public Date getTokenExpiration(int expirationMinutes) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, expirationMinutes);

		return now.getTime();
	}

	public LocalDateTime getExpiredAt(String jws) {
		Key key = getKeyFromBase64EncodedSecretKey(secretKey);

		Date expiration = Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(jws)
			.getBody().getExpiration();

		return expiration.toInstant()
			.atZone(ZoneId.systemDefault())
			.toLocalDateTime();
	}

	public String accessTokenAssembly(Map<String, Object> claims, String subject, Date expiration) {
		Key key = getKeyFromBase64EncodedSecretKey(secretKey);

		return Jwts.builder()
			.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
			.setClaims(claims)
			.setSubject(subject)
			.setIssuedAt(Calendar.getInstance().getTime())
			.setExpiration(expiration)
			.signWith(key)
			.compact();
	}

	public String createAccessToken(String email, String role) {
		Map<String, Object> claims = createClaims(email, role);
		Date expiration = getTokenExpiration(accessTokenExpirationMinutes);

		return accessTokenAssembly(claims, email, expiration);
	}

	public String refreshTokenAssembly(String subject, Date expiration) {
		Key key = getKeyFromBase64EncodedSecretKey(secretKey);

		return Jwts.builder()
			.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
			.setSubject(subject)
			.setIssuedAt(Calendar.getInstance().getTime())
			.setExpiration(expiration)
			.signWith(key)
			.compact();
	}

	public String createRefreshToken(String email) {
		Date expiration = getTokenExpiration(refreshTokenExpirationMinutes);

		return refreshTokenAssembly(email, expiration);
	}

	public UserDetails parseToken(String jws) {
		Claims body = getClaims(jws).getBody();

		String email = body.getSubject();
		String role = body.get(CLAIM_AUTHORITY, String.class);

		return MemberDetails.of(email, role);
	}

	public Jws<Claims> getClaims(String jws) {
		Key key = getKeyFromBase64EncodedSecretKey(secretKey);

		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(jws);
	}

	public String parseEmail(String jws) {
		return getClaims(jws).getBody().getSubject();
	}

	public boolean validate(String accessToken) {
		try {
			getClaims(accessToken);
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			log.error("Invalid_JWT_SIGNATURE");
		} catch (ExpiredJwtException e) {
			log.error("EXPIRED_JWT_TOKEN");
		} catch (UnsupportedJwtException e) {
			log.error("UNSUPPORTED_JWT_TOKEN");
		} catch (IllegalArgumentException e) {
			log.error("INVALID_JWT_TOKEN");
		}
		return false;
	}

	private Key getKeyFromBase64EncodedSecretKey(String base64EncodedSecretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);

		return Keys.hmacShaKeyFor(keyBytes);
	}

	private Map<String, Object> createClaims(String email, String authority) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_EMAIL, email);
		claims.put(CLAIM_AUTHORITY, authority);

		return claims;
	}
}
