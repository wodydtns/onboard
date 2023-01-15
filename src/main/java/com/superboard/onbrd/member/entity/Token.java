package com.superboard.onbrd.member.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String androidPushToken;
	@Column
	private String applePushToken;
	@Column
	private String accessToken;
	@Column
	private String refreshToken;
	@Column
	private String oauthGrantToken;
	@Column
	private LocalDateTime accessTokenExpiredAt;
	@Column
	private LocalDateTime refreshTokenExpiredAt;
	@OneToOne
	@JoinColumn(name = "member_id")
	private Member member;
}
