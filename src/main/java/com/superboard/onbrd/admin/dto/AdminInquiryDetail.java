package com.superboard.onbrd.admin.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminInquiryDetail {
	private Long id;
	private String title;
	private String content;
	private Long memberId;
	private String nickname;
	private LocalDateTime createdAt;
	private Boolean isAnswered;
	private String answer;
}
