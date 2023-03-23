package com.superboard.onbrd.report.dto;

import com.superboard.onbrd.report.entity.ReportType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportCreateCommand {
	private ReportType type;
	private Long postId;
	private String whistleblowerEmail;

	public static ReportCreateCommand of(String email, ReportPostRequest request) {
		ReportCreateCommand command = new ReportCreateCommand();

		command.type = request.getType();
		command.postId = request.getPostId();
		command.whistleblowerEmail = email;

		return command;
	}
}
