package com.superboard.onbrd.report.repository;

import com.superboard.onbrd.admin.dto.AdminReportDetail;
import com.superboard.onbrd.global.dto.OnbrdPageRequest;
import com.superboard.onbrd.global.dto.OnbrdPageResponse;

public interface CustomReportRepository {
	OnbrdPageResponse<AdminReportDetail> getAdminReports(OnbrdPageRequest params);
}
