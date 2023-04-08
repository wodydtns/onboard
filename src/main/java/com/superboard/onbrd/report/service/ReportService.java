package com.superboard.onbrd.report.service;

import com.superboard.onbrd.admin.dto.AdminReportDetail;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.report.dto.ReportCreateCommand;
import com.superboard.onbrd.report.entity.Report;

public interface ReportService {
	OnbrdSliceResponse<AdminReportDetail> getAdminReports(OnbrdSliceRequest params);

	Report createReport(ReportCreateCommand command);

	Report resolveReport(Long id, String email);

	Report findVerifiedOneById(Long id);
}
