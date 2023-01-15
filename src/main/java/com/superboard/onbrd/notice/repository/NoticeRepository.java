package com.superboard.onbrd.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.notice.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
