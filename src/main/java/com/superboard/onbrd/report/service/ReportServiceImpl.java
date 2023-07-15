package com.superboard.onbrd.report.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.admin.dto.AdminReportDetail;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.service.MemberService;
import com.superboard.onbrd.report.dto.ReportCreateCommand;
import com.superboard.onbrd.report.entity.Report;
import com.superboard.onbrd.report.entity.ReportType;
import com.superboard.onbrd.report.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
	private final ReportRepository reportRepository;
	private final MemberService memberService;

	@Override
	public OnbrdSliceResponse<AdminReportDetail> getAdminReports(OnbrdSliceRequest params) {
		return reportRepository.getAdminReports(params);
	}

	@Override
	public Report createReport(ReportCreateCommand command) {
		Member whistleblower = memberService.findVerifiedOneByEmail(command.getWhistleblowerEmail());

		Report report = Report.of(whistleblower, command);

		return reportRepository.save(report);
	}

	@Override
	public Report resolveReport(Long id, String email) {
		Report report = findVerifiedOneById(id);
		Member admin = memberService.findVerifiedOneByEmail(email);

		report.resolve(admin);

		return report;
	}

	@Override
	public Report findVerifiedOneById(Long id) {
		Optional<Report> reportOptional = reportRepository.findById(id);

		return reportOptional
			.orElseThrow(() -> new BusinessLogicException(REPORT_NOT_FOUND));
	}

	@Override
	public void deleteIfExist(ReportType type, Long postId) {
		List<Report> reports = reportRepository.findAllByTypeAndPostId(type, postId);

		reportRepository.deleteAllInBatch(reports);
	}
}
