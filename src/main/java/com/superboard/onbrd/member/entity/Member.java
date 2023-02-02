package com.superboard.onbrd.member.entity;

import static com.superboard.onbrd.member.entity.MemberLevel.*;
import static com.superboard.onbrd.member.entity.MemberRole.*;
import static com.superboard.onbrd.member.entity.MemberStatus.*;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.superboard.onbrd.global.entity.BaseEntity;
import com.superboard.onbrd.member.dto.SignUpRequest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false, updatable = false)
	private String email;
	@Column(nullable = false)
	private String nickname;
	@Column
	private String profileCharacter;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private MemberLevel level = DEFAULT;
	@Column(nullable = false)
	private int point = 0;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private MemberStatus status = ACTIVE;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private MemberRole role = ROLE_USER;
	@Column(nullable = false)
	private Boolean passwordChangeDueExtended = false;
	@Column
	private LocalDateTime lastVisit;

	public static Member from(SignUpRequest request) {
		return new Member(
			request.getEmail(), request.getNickname(), request.getProfileCharacter());
	}

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}

	public void updateProfileCharacter(String profileCharacter) {
		this.profileCharacter = profileCharacter;
	}

	public void extendChangeDue() {
		this.passwordChangeDueExtended = true;
	}

	public void withdraw() {
		this.status = WITHDRAWN;
	}

	private Member(String email, String nickname, String profileCharacter) {
		this.email = email;
		this.nickname = nickname;
		this.profileCharacter = profileCharacter;
	}
}
