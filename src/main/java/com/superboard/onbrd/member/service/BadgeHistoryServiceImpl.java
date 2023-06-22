package com.superboard.onbrd.member.service;

import java.util.SortedSet;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.member.dto.mypage.MypageBadgeResponse;
import com.superboard.onbrd.member.entity.Badge;
import com.superboard.onbrd.member.entity.BadgeHistory;
import com.superboard.onbrd.member.repository.BadgeHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BadgeHistoryServiceImpl implements BadgeHistoryService {
	private final BadgeHistoryRepository badgeHistoryRepository;

	@Override
	public MypageBadgeResponse findBadges(String email) {
		BadgeHistory lastBadgeHistory = badgeHistoryRepository.findLastBadgeHistory(email);
		BadgeHistory lastCheckedBadgeHistory = badgeHistoryRepository.findLastCheckedBadgeHistory(email);

		SortedSet<Badge> badges = lastBadgeHistory.getCurrentBadges();
		SortedSet<Badge> newBadges = lastBadgeHistory.getDifferentialBadges(lastCheckedBadgeHistory);

		lastBadgeHistory.check();

		return new MypageBadgeResponse(badges, newBadges);
	}
}
