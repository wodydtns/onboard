package com.superboard.onbrd.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.report.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long>, CustomReportRepository {
}
