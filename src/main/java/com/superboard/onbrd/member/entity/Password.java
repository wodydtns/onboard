package com.superboard.onbrd.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.superboard.onbrd.global.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password extends BaseEntity {
	@Id
	@Column(name = "member_id")
	private Long id;
	@Column
	private String encodedPassword;
	@OneToOne
	@MapsId
	@JoinColumn(name = "member_id")
	private Member member;

	public static Password of(Member member, String encodedPassword) {
		Password password = new Password();

		password.member = member;
		password.encodedPassword = encodedPassword;

		return password;
	}

	public void resetPassword(String encodedPassword) {
		this.encodedPassword = encodedPassword;
	}
}
