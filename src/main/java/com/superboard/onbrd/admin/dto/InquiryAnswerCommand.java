package com.superboard.onbrd.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InquiryAnswerCommand {
	private Long id;
	private String answer;
	private String adminEmail;

	public static InquiryAnswerCommand of(Long id, InquiryAnswerRequest request, String adminEmail) {
		InquiryAnswerCommand command = new InquiryAnswerCommand();

		command.id = id;
		command.answer = request.getAnswer();
		command.adminEmail = adminEmail;

		return command;
	}
}
