package com.superboard.onbrd.notice.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeDetail {
	private Long id;
	private String title;
	private String content;
	private String admin;
	private LocalDateTime createdAt;
}
