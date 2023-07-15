package com.superboard.onbrd.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.report.entity.Report;
import com.superboard.onbrd.report.entity.ReportType;

public interface ReportRepository extends JpaRepository<Report, Long>, CustomReportRepository {
	List<Report> findAllByTypeAndPostId(ReportType type, Long postId);
}
