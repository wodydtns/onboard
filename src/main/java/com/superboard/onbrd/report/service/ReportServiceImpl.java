package com.superboard.onbrd.report.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.service.MemberService;
import com.superboard.onbrd.report.dto.ReportCreateCommand;
import com.superboard.onbrd.report.entity.Report;
import com.superboard.onbrd.report.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
	private final ReportRepository reportRepository;
	private final MemberService memberService;

	@Override
	public Report createReport(ReportCreateCommand command) {
		Member whistleblower = memberService.findVerifiedOneByEmail(command.getWhistleblowerEmail());

		Report report = Report.of(whistleblower, command);

		return reportRepository.save(report);
	}
}
