package com.superboard.onbrd.member.repository;

import java.util.Optional;

import com.superboard.onbrd.member.entity.BadgeHistory;

public interface CustomBadgeHistoryRepository {

	BadgeHistory findLastBadgeHistory(String email);

	Optional<BadgeHistory> findLastCheckedBadgeHistory(String email);
}
