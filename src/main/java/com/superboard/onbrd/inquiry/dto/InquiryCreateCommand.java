package com.superboard.onbrd.inquiry.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InquiryCreateCommand {
	private String title;
	private String content;
	private String email;

	public static InquiryCreateCommand of(String email, InquiryPostRequest request) {
		InquiryCreateCommand command = new InquiryCreateCommand();

		command.title = request.getTitle();
		command.content = request.getContent();
		command.email = email;

		return command;
	}
}
