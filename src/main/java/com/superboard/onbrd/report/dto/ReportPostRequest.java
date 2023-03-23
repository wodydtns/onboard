package com.superboard.onbrd.report.dto;

import com.superboard.onbrd.report.entity.ReportType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReportPostRequest {
	private ReportType type;
	private Long postId;
}
