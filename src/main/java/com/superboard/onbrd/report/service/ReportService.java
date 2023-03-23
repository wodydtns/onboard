package com.superboard.onbrd.report.service;

import com.superboard.onbrd.report.dto.ReportCreateCommand;
import com.superboard.onbrd.report.entity.Report;

public interface ReportService {
	Report createReport(ReportCreateCommand command);
}
