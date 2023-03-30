package com.superboard.onbrd.report.service;

import com.superboard.onbrd.admin.dto.AdminReportDetail;
import com.superboard.onbrd.global.dto.OnbrdPageRequest;
import com.superboard.onbrd.global.dto.OnbrdPageResponse;
import com.superboard.onbrd.report.dto.ReportCreateCommand;
import com.superboard.onbrd.report.entity.Report;

public interface ReportService {
	OnbrdPageResponse<AdminReportDetail> getAdminReports(OnbrdPageRequest params);

	Report createReport(ReportCreateCommand command);

	Report resolveReport(Long id, String email);

	Report findVerifiedOneById(Long id);
}
