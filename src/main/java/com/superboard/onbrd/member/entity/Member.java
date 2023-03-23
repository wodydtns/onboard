package com.superboard.onbrd.member.entity;

import static com.superboard.onbrd.member.entity.MemberLevel.*;
import static com.superboard.onbrd.member.entity.MemberRole.*;
import static com.superboard.onbrd.member.entity.MemberStatus.*;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.superboard.onbrd.global.converter.MemberLevelConverter;
import com.superboard.onbrd.global.converter.MemberRoleConverter;
import com.superboard.onbrd.global.converter.MemberStatusConverter;
import com.superboard.onbrd.global.entity.BaseEntity;
import com.superboard.onbrd.member.dto.member.SignUpRequest;

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
	@Column(name = "member_level", nullable = false)
	@Convert(converter = MemberLevelConverter.class)
	private MemberLevel level = PLAYER;
	@Column(nullable = false)
	private int point = 0;
	@Column(nullable = false)
	@Convert(converter = MemberStatusConverter.class)
	private MemberStatus status = ACTIVE;
	@Column(nullable = false)
	@Convert(converter = MemberRoleConverter.class)
	private MemberRole role = ROLE_USER;
	@Column(nullable = false)
	private Boolean isSocial = false;
	@Column(nullable = false)
	private int passwordChangeDelayCount = 0;
	@Column
	private LocalDateTime lastVisitAt;

	public static Member from(SignUpRequest request) {
		return new Member(
			request.getEmail(), request.getNickname(), request.getProfileCharacter());
	}

	public static Member fromOauth(String email) {
		Member member = new Member();
		member.email = email;
		member.nickname = "default";
		member.isSocial = true;

		return member;
	}

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}

	public void updateProfileCharacter(String profileCharacter) {
		this.profileCharacter = profileCharacter;
	}

	public void delayPasswordChange() {
		passwordChangeDelayCount++;
	}

	public void resetPasswordChangeCount() {
		passwordChangeDelayCount = 0;
	}

	public void increasePoint(int point) {
		this.point += point;

	}

	public void updateLevel(MemberLevel level) {
		this.level = level;
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
