package com.superboard.onbrd.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.admin.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
