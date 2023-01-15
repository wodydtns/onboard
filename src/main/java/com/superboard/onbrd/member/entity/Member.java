package com.superboard.onbrd.member.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.superboard.onbrd.global.entity.BaseEntity;

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
	private String password;
	@Column(nullable = false)
	private String nickname;
	@Column
	private String profileCharacter;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private MemberLevel level;
	@Column(nullable = false)
	private int point;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private MemberStatus status;
	@Column
	private LocalDateTime lastVisit;
}
