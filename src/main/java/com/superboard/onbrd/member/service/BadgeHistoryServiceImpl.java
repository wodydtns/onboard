package com.superboard.onbrd.member.service;

import java.util.Optional;
import java.util.SortedSet;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.member.dto.mypage.MypageBadgeResponse;
import com.superboard.onbrd.member.entity.Badge;
import com.superboard.onbrd.member.entity.BadgeHistory;
import com.superboard.onbrd.member.entity.Member;
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
		Optional<BadgeHistory> lastCheckedBadgeHistoryOptional = badgeHistoryRepository.findLastCheckedBadgeHistory(
			email);

		SortedSet<Badge> badges = lastBadgeHistory.getCurrentBadges();
		SortedSet<Badge> newBadges = lastCheckedBadgeHistoryOptional
			.map(lastBadgeHistory::getDifferentialBadges)
			.orElse(badges);

		lastBadgeHistory.check();

		return new MypageBadgeResponse(badges, newBadges);
	}

	@Override
	public void createFrom(Member member) {

		badgeHistoryRepository.save(BadgeHistory.from(member));
	}
}
