package com.superboard.onbrd.inquiry.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InquiryGetResponse {
	private Long id;
	private String title;
	private String content;
	private Boolean isAnswered;
	private String answer;
	private LocalDateTime answeredAt;
	private String adminNickname;
	private LocalDateTime createdAt;
}
