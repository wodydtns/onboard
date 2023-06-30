package com.superboard.onbrd.member.service;

import com.superboard.onbrd.member.dto.mypage.MypageBadgeResponse;
import com.superboard.onbrd.member.entity.Member;

public interface BadgeHistoryService {

	MypageBadgeResponse findBadges(String email);

	void createFrom(Member member);
}
