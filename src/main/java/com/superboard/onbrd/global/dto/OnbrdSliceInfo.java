package com.superboard.onbrd.global.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OnbrdSliceInfo {
	private Boolean hasNext;

	public static OnbrdSliceInfo from(Boolean hasNext) {
		OnbrdSliceInfo info = new OnbrdSliceInfo();
		info.hasNext = hasNext;

		return info;
	}
}
