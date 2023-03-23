package com.superboard.onbrd.inquiry.dto;

import javax.validation.Valid;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Valid
public class InquiryPatchRequest {
	private String title;
	private String content;
}
