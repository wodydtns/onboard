package com.superboard.onbrd.inquiry.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InquiryMyListResponse {
	private List<InquiryCard> inquiries;

	@Getter
	@NoArgsConstructor
	public static class InquiryCard {
		private Long id;
		private String title;
		private String content;
		private Boolean isAnswered;
		private LocalDateTime createdAt;
	}

	public static InquiryMyListResponse from(List<InquiryCard> inquiries) {
		InquiryMyListResponse response = new InquiryMyListResponse();

		response.inquiries = inquiries;

		return response;
	}
}
