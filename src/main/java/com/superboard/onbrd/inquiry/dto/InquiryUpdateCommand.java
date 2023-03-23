package com.superboard.onbrd.inquiry.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InquiryUpdateCommand {
	private Long id;
	private String title;
	private String content;

	public static InquiryUpdateCommand of(Long id, InquiryPatchRequest request) {
		InquiryUpdateCommand command = new InquiryUpdateCommand();

		command.id = id;
		command.title = request.getTitle();
		command.content = request.getContent();

		return command;
	}
}
