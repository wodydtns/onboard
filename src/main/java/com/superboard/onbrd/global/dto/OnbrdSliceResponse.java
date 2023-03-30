package com.superboard.onbrd.global.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class OnbrdSliceResponse<T> {
	private OnbrdSliceInfo sliceInfo;
	private List<T> content;
}
