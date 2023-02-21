package com.superboard.onbrd.oauth2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.superboard.onbrd.member.entity.Member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OauthID {
	@Id
	@Column(name = "member_id")
	private Long id;
	@Column(nullable = false)
	private String oauthId;
	@OneToOne
	@MapsId
	@JoinColumn(name = "member_id")
	private Member member;

	public static OauthID of(Member member, String oauthId) {
		OauthID oauthID = new OauthID();
		oauthID.member = member;
		oauthID.oauthId = oauthId;

		return oauthID;
	}
}
