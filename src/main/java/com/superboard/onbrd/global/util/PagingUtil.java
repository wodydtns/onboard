package com.superboard.onbrd.global.util;

import java.util.List;

import com.superboard.onbrd.global.dto.OnbrdSliceInfo;

public class PagingUtil {
	public static Boolean getHasNext(List<?> content, int limit) {
		if (content.size() > limit) {
			content.remove(limit);
			return true;
		}

		return false;
	}

	public static OnbrdSliceInfo getSliceInfo(List<?> content, int limit) {
		return OnbrdSliceInfo.from(
			getHasNext(content, limit));
	}
}
