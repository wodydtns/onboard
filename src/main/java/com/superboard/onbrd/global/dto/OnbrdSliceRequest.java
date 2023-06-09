package com.superboard.onbrd.global.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OnbrdSliceRequest {
	private long offset;
	private int limit;

	public void rebaseToZero() {
		if (offset > 0) {
			offset--;
		}
	}
}
