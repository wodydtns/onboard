package com.superboard.onbrd.admin.dto;

import lombok.Getter;

@Getter
public class NoticeCreateCommand {
	private String adminEmail;
	private String title;
	private String content;

	public NoticeCreateCommand(String adminEmail, NoticePostRequest request) {
		this.adminEmail = adminEmail;
		this.title = request.getTitle();
		this.content = request.getContent();
	}
}
