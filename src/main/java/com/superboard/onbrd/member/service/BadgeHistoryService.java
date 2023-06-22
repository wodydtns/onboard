package com.superboard.onbrd.member.service;

import com.superboard.onbrd.member.dto.mypage.MypageBadgeResponse;

public interface BadgeHistoryService {

	MypageBadgeResponse findBadges(String email);
}
