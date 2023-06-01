package com.superboard.onbrd.inquiry.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class InquiryGetQuery {
	private final String email;
	private final long offset;
	private final int limit;
}
