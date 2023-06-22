package com.superboard.onbrd.member.repository;

import com.superboard.onbrd.member.entity.BadgeHistory;

public interface CustomBadgeHistoryRepository {

	BadgeHistory findLastBadgeHistory(String email);

	BadgeHistory findLastCheckedBadgeHistory(String email);
}
