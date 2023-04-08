package com.superboard.onbrd.report.repository;

import com.superboard.onbrd.admin.dto.AdminReportDetail;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;

public interface CustomReportRepository {
	OnbrdSliceResponse<AdminReportDetail> getAdminReports(OnbrdSliceRequest params);
}
