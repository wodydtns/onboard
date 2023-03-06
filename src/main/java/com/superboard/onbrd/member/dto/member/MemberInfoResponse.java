package com.superboard.onbrd.member.dto.member;

import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.entity.MemberLevel;
import com.superboard.onbrd.member.entity.MemberRole;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberInfoResponse {
	private Long id;
	private String email;
	private String nickname;
	private String profileCharacter;
	private MemberLevel level;
	private int point;
	private MemberRole role;
	private Boolean isSocial;

	public static MemberInfoResponse toDto(Member member) {
		MemberInfoResponse dto = new MemberInfoResponse();

		dto.id = member.getId();
		dto.email = member.getEmail();
		dto.nickname = member.getNickname();
		dto.profileCharacter = member.getProfileCharacter();
		dto.level = member.getLevel();
		dto.point = member.getPoint();
		dto.role = member.getRole();
		dto.isSocial = member.getIsSocial();

		return dto;
	}
}
