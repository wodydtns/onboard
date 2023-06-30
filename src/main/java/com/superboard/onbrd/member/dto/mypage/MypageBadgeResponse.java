package com.superboard.onbrd.member.dto.mypage;

import java.util.SortedSet;

import com.superboard.onbrd.member.entity.Badge;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MypageBadgeResponse {
	private SortedSet<Badge> badges;
	private SortedSet<Badge> newBadges;
}
