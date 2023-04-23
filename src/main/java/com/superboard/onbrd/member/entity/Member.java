package com.superboard.onbrd.member.entity;

import static com.superboard.onbrd.member.entity.ActivityPoint.*;
import static com.superboard.onbrd.member.entity.MemberLevel.*;
import static com.superboard.onbrd.member.entity.MemberRole.*;
import static com.superboard.onbrd.member.entity.MemberStatus.*;
import static java.time.temporal.ChronoUnit.*;

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
import com.superboard.onbrd.oauth2.dto.OauthSignUpRequest;

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
	private int passwordChangeDelayCount = 1;
	@Column
	private LocalDateTime lastVisitAt = LocalDateTime.now();
	@Column
	private int serialVisitDays = 0;
	@Column
	private int totalAttendDays = 0;

	public static Member from(SignUpRequest request) {
		return new Member(
			request.getEmail(), request.getNickname(), request.getProfileCharacter());
	}

	public static Member fromOauth(OauthSignUpRequest request) {
		Member member = new Member();

		member.email = request.getEmail();
		member.nickname = request.getNickname();
		member.profileCharacter = request.getProfileCharacter();
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

	public void updateLevel(MemberLevel level) {
		this.level = level;
	}

	public void increasePoint(int point) {
		this.point += point;
	}

	public void attendAt(LocalDateTime dateTime) {
		if (lastVisitAt.isAfter(dateTime)) {
			return;
		}

		int diff = (int)DAYS.between(lastVisitAt, dateTime);

		switch (diff) {
			case 0:
				lastVisitAt = dateTime;
				break;

			case 1:
				lastVisitAt = dateTime;
				serialVisitDays++;
				totalAttendDays++;
				increasePoint(ATTENDANCE.getPoint());
				break;

			default:
				lastVisitAt = dateTime;
				serialVisitDays = 1;
				totalAttendDays++;
				increasePoint(ATTENDANCE.getPoint());
				break;
		}
	}

	public void grantAuthority(MemberRole role) {
		this.role = role;
	}

	public void withdraw() {
		this.status = WITHDRAWN;
	}

	public void suspend() {
		this.status = SUSPENDED;
	}

	public void kick() {
		this.status = KICKED;
	}

	public void integrate() {
		this.isSocial = true;
	}

	private Member(String email, String nickname, String profileCharacter) {
		this.email = email;
		this.nickname = nickname;
		this.profileCharacter = profileCharacter;
	}
}
