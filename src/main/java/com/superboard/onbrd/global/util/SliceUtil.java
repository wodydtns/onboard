package com.superboard.onbrd.global.util;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class SliceUtil {
	public Boolean getHasNext(List<?> cards, int limit) {
		if (cards.size() > limit) {
			cards.remove(limit);
			return true;
		}

		return false;
	}
}
