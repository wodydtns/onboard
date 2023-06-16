package com.superboard.onbrd.admin.dto;

import java.time.LocalDateTime;

import com.superboard.onbrd.report.entity.ReportType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminReportDetail {
	private Long id;
	private ReportType type;
	private Long postId;
	private String content;
	private Boolean isHidden;
	private LocalDateTime createdAt;
	private Long boardGameId;
	private String boardGameTitle;
	private Long writerId;
	private String writerNickname;
	private Long whistleblowerId;
	private String whistleblowerNickname;
	private Boolean isResolved;
	private LocalDateTime reportedAt;
}
