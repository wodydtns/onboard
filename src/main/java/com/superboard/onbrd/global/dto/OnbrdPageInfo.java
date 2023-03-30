package com.superboard.onbrd.global.dto;

import java.util.List;

import com.superboard.onbrd.global.entity.OrderBy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OnbrdPageInfo {
	private int page;
	private int size;
	private long totalPages;
	private int numberOfElements;
	private long totalElements;
	private OrderBy orderBy;

	public static OnbrdPageInfo of(OnbrdPageRequest params, List<?> content, long totalElements, OrderBy orderBy) {
		OnbrdPageInfo pageInfo = new OnbrdPageInfo();

		pageInfo.page = params.getPage() + 1;
		pageInfo.size = params.getSize();
		pageInfo.totalPages =
			content.isEmpty() ? 1 : (int)Math.ceil((double)totalElements / (double)params.getPageSize());
		pageInfo.numberOfElements = content.size();
		pageInfo.totalElements = totalElements;
		pageInfo.orderBy = orderBy;

		return pageInfo;
	}
}
