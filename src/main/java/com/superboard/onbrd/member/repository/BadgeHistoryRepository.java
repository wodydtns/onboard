package com.superboard.onbrd.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.member.entity.BadgeHistory;

public interface BadgeHistoryRepository extends JpaRepository<BadgeHistory, Long> {
}
