package com.superboard.onbrd.admin.dto;

import lombok.Getter;

@Getter
public class NoticeUpdateCommand {
	private Long id;
	private String title;
	private String content;

	public NoticeUpdateCommand(Long id, NoticePatchRequest request) {
		this.id = id;
		this.title = request.getTitle();
		this.content = request.getContent();
	}
}
