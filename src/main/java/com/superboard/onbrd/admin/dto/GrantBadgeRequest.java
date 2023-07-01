package com.superboard.onbrd.admin.dto;

import java.util.Set;

import com.superboard.onbrd.member.entity.Badge;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GrantBadgeRequest {
	private Set<Badge> badges;
}
